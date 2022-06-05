package com.currency.qrcode.currency.logging.dataplat;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangmengqi on 21/3/15.
 */
public class LogInfoUtil {

    // 关键字，日志中必须存在带有该关键字的日志才会被数据平台抓取出来;
    public static final String LOG_TAG_PROPERTY_KEY = "xcloud.data.plat.log.tag";
    public static final String REQUEST_TYPE_PROPERTY_KEY = "xcloud.data.plat.log.reqeust.type";
    public static final String SERVICE_ID_PROPERTY_KEY = "xcloud.data.plat.log.service.name";

    public static final String LOG_TAG_VALUE_DEFAULT = "DATAPLATLOG-TRACE";
    public static final String REQUEST_TYPE_VALUE_DEFAULT = "request_id";
    public static final String SERVICE_ID_VALUE_DEFAULT = "console";

    public static String getLogTag() {
        String logTag = System.getProperty(LOG_TAG_PROPERTY_KEY);
        if (StringUtils.isBlank(logTag)) {
            return LOG_TAG_VALUE_DEFAULT;
        }
        return logTag;
    }

    public static String getRequestType() {
        String requestType = System.getProperty(REQUEST_TYPE_PROPERTY_KEY);
        if (StringUtils.isBlank(requestType)) {
            return REQUEST_TYPE_VALUE_DEFAULT;
        }
        return requestType;
    }

    public static String getServiceId() {
        String serviceId = System.getProperty(SERVICE_ID_PROPERTY_KEY);
        if (StringUtils.isBlank(serviceId)) {
            return SERVICE_ID_VALUE_DEFAULT;
        }
        return serviceId;
    }

}
