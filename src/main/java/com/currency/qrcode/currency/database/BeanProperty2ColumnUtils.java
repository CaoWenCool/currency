/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.database;

import com.currency.qrcode.currency.model.request.ListRequest;
import com.currency.qrcode.currency.model.request.OrderModel;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 实现 bean property 转数据库 column.
 *
 * @author liucunliang
 * @version 1.0.0
 * @create 2021/2/9 下午1:27
 * @since 1.0.0
 */
@Component
public class BeanProperty2ColumnUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanProperty2ColumnUtils.class);

    @Value("${xcloud_web_framework.mybatis.enable:false}")
    private Boolean use_mybatis;

    @Autowired
    ApplicationContext context;

    /**
     * 下划线转驼峰
     *
     * @param str  输入字符串
     * @param smallCamel 首字母是否小写
     * @return 驼峰形式字符串
     */
    public static String underlineToCamel(String str, boolean smallCamel) {
        if(StringUtils.isEmpty(str)) {
            return str;
        }

        StringBuffer sb = new StringBuffer();
        // 字母、数字、下划线
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ?
                          Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf("_");
            if(index > 0){
                sb.append(word.substring(1, index));
            }else{
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str 输入字符串
     * @param upperCase 下划线分割字符是否大写
     * @return 下划线形式字符串
     */
    public static String camelToUnderline(String str, boolean upperCase) {
        if(StringUtils.isEmpty(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            if (upperCase) {
                //统一都转大写
                sb.append(Character.toUpperCase(c));
            } else {
                //统一都转小写
                sb.append(Character.toLowerCase(c));
            }


        }
        return sb.toString();
    }

    /**
     * 将 ListRequest 实体内属性，转成数据库列
     *
     * @param request ListRequest 实体
     * @param clazz 数据库实体类
     * @return 转换后的 ListRequest 实体
     * @see ListRequest
     */
    public ListRequest property2Column(ListRequest request, Class<?> clazz) {
        List<OrderModel> orders = request.getOrders();
        if (ObjectUtils.isNotEmpty(orders)) {
            List<OrderModel> newOrders = new ArrayList<>();
            for (OrderModel order : orders) {
                OrderModel orderModel = new OrderModel();
                orderModel.setOrder(order.getOrder());
                orderModel.setOrderBy(property2Column(order.getOrderBy(), clazz));
                orderModel.setOrderByOrigin(order.getOrderBy());
                newOrders.add(orderModel);
            }
            request.setOrders(newOrders);
        }

        Map<String, Object> filterMap = request.getFilterMap();
        if (ObjectUtils.isNotEmpty(filterMap)) {
            Map<String, Object> newFilterMap = new HashMap<>();
            for (Object key : filterMap.keySet()) {
                String keywordType = (String) key;
                keywordType = property2Column(keywordType, clazz);
                newFilterMap.put(keywordType, filterMap.get(key));
            }
            request.setFilterMap(newFilterMap);
        }
        return request;
    }

    /**
     * 从 bean属性名转成数据库列名
     *
     * @param property 待转换属性名
     * @param clazz 实体类
     * @return 数据库列名
     */
    public String property2Column(String property, Class<?> clazz) {
        String column = null;
        if (use_mybatis) {
            column = getFromMybatis(property, clazz);
        }

        if (StringUtils.isEmpty(column)) {
            // 找不到统一转下划线
            column = camelToUnderline(property, false);
        }
        log.debug("{} -> {}", property, column);
        return column;
    }

    private String getFromMybatis(String property, Class<?> clazz) {
        ResultMap resultMap = getMybatisResultMap(clazz);
        if (ObjectUtils.isNotEmpty(resultMap)) {
            for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                if (resultMapping.getProperty().equals(property)) {
                    property = resultMapping.getColumn();
                    return property;
                }
            }
        }
        return null;
    }

    private ResultMap getMybatisResultMap(Class<?> clazz) {
        // 获取SqlSessionTemplate
        SqlSessionTemplate sqlSessionTemplate = context.getBean(SqlSessionTemplate.class);
        assert sqlSessionTemplate != null;
        // 关键在于这里，获取SqlSessionTemplate中的Configuration，这里面当前Sql session会话的所有参数
        // Configuration的getResultMap方法就可以获取指定的ResultMap，所以是该方法需要指定ResultMap的ID
        Configuration configuration = sqlSessionTemplate.getConfiguration();
        // 获取所有的ResultMap的名字：以xml的命名空间如：xx.xx.dao.StudentMapper加resultMap的id如：BaseResultMap组合：xx.xx.dao.StudentMapper
        // .BaseResultMap这样的全定名
        // 注意会存在一个默认的BaseResultMap,为上面那个的短名称，所以我们会拿到项目所有的ResultMap
        Collection<String> resultMapNames = configuration.getResultMapNames();

        // 利用Stream流快速筛查
        List<ResultMap> resultMaps = resultMapNames.parallelStream()
                                         // 要全定名不要短名
                                         .filter(name -> name.contains("."))
                                         // 根据全定名找到匹配的ResultMap
                                         .map(configuration::getResultMap)
                                         // 匹配xml中type属性和实体类一致的
                                         .filter(resultMap -> Objects.equals(resultMap.getType(), clazz))
                                         // 排序,按字段数量来；这里还是会有多个，为什么：比如上面的xml中就有两个ResultMap
                                         .sorted(Comparator.comparing(
                                             resultMap -> resultMap.getPropertyResultMappings().size()))
                                         .collect(Collectors.toList());
        // 翻转，resultMap包含的字段多的属性映射更全
        Collections.reverse(resultMaps);
        // 找出那个type属性一致的，其实这个list里面所有的resultMap属性都是一致的了，毕竟上面过滤了，只不过Stream过滤就算只有一个也是用list装的
        if (ObjectUtils.isNotEmpty(resultMaps)) {
            for (ResultMap resultMap : resultMaps) {
                Class<?> type = resultMap.getType();
                if (Objects.equals(type, clazz)) {
                    return resultMap;
                }
            }
        }
        return null;
    }
}
