package com.currency.qrcode.currency.database.mybatis;

import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;
import org.apache.ibatis.ognl.PropertyAccessor;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.util.Map;

/**
 * Copied and modified from {@link org.apache.ibatis.scripting.xmltags.DynamicContext}.ContextAccessor, injecting SQL
 * utils.
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/14 下午8:54
 */
@SuppressWarnings("rawtypes")
public class ContextMapPropertyAccessor implements PropertyAccessor  {

    private static final Map<String, Object> SQL_UTILS = ImmutableMap.of(
        "check", SqlCheckBean.INSTANCE,
        "escape", SqlEscapeBean.INSTANCE
    );

    @Override
    public Object getProperty(Map context, Object target, Object name) throws OgnlException {
        Object utils = SQL_UTILS.get(name);
        if (utils != null) {
            return utils;
        }

        Map map = (Map) target;

        Object result = map.get(name);
        if (map.containsKey(name) || result != null) {
            return result;
        }

        Object parameterObject = map.get(DynamicContext.PARAMETER_OBJECT_KEY);
        if (parameterObject instanceof Map) {
            return ((Map) parameterObject).get(name);
        }

        return null;
    }

    @Override
    public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException {
        @SuppressWarnings("unchecked")
        Map<Object, Object> map = (Map) target;
        map.put(name, value);
    }

    @Override
    public String getSourceAccessor(OgnlContext context, Object target, Object index) {
        return null;
    }

    @Override
    public String getSourceSetter(OgnlContext context, Object target, Object index) {
        return null;
    }
}
