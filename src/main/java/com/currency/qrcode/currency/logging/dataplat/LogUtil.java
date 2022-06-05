package com.currency.qrcode.currency.logging.dataplat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 服务日志格式(无换行),打通requestID用,
 * [DATAPLATLOG-TRACE]
 * [REQUEST_TYPE=value REQUEST_ID=value SERVICE_ID=value TIMESTAMP=value action_id=value module_id=value step_id=value
 * operation_id=value operation_starttime=value operation_endtime=value custom=value] 日志正文
 * 时间格式:yyyy-MM-dd HH:mm:ss.SSS
 * Created by zhengmengqi on 21/3/15.
 */
@Component
public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    // 必须填写，只有REQUEST_TYPE等于相同的string才会被认为是一类日志从而收集到一起,注意对于目前我们的项目来说，
    // 该字段应该设置为REQUEST_TYPE='request_id'.
    public static final String REQUEST_TYPE_KEY = "REQUEST_TYPE";

    // 必须填写的字段，代表了本次请求的request id
    public static final String REQUEST_ID_KEY = "REQUEST_ID";

    // 必填字段，代表这个是哪个服务打印出来的日志，比如说是nova api或者neutron打印出来的，一般情况下一个进程可以理解成一个SERVICE
    public static final String SERVICE_ID_KEY = "SERVICE_ID";

    // 日志的时间戳，有可能会与operation_starttime && operation_endtime重复，这么设计还是为了后续的parse任务的简单，
    // 在同一个SERVICE内部打印的时间戳的格式务必一致
    public static final String TIMESTAMP_KEY = "TIMESTAMP";

    public static final String ACTION_ID_KEY = "action_id";
    public static final String MODULE_ID_KEY = "module_id";
    public static final String STEP_ID_KEY = "step_id";
    public static final String OPERATION_ID_KEY = "operation_id";
    public static final String OPERATION_STARTTIME_KEY = "operation_starttime";
    public static final String OPERATION_ENDTIME_KEY = "operation_endtime";
    public static final String CUSTOM = "custom";

    // 不同服务差异值
    public static final String REQUEST_TYPE_VALUE = "request_id";

    private static final String BASE_FORMAT = "[{}][{}={} {}={} {}={} {}={} "
            + "{}={} {}={} {}={} {}={} {}={} {}={} {}={}]{}";

    public static final String NONE = "NONE";

    // 线程变量缓存
    private static ThreadLocal<Map<String, String>> logContext = new ThreadLocal<>();

    private static void initContext() {
        Map<String, String> contextParams = new HashMap<>();
        logContext.set(contextParams);
    }

    public static String getCtxParam(String key) {
        if (logContext.get() == null) {
            initContext();
        }
        String value = logContext.get().get(key);
        return value == null ? "" : value;
    }

    public static void putCtxParam(String key, String value) {
        if (logContext.get() == null) {
            initContext();
        }
        logContext.get().put(key, value);
    }

    private static void destroyCtx() {
        logContext.remove();
    }

    /**
     * 打印日志
     * @param logInfoModel 日志模型
     */
    public static void recordLog(LogInfoModel logInfoModel) {
        if (logInfoModel == null) {
            return ;
        }
        String operationStartTime = formatTime(logInfoModel.getOperationStartTime());
        if (operationStartTime == null) {
            operationStartTime = NONE;
        }
        String operationEndTime = formatTime(logInfoModel.getOperationEndTime());
        if (operationEndTime == null) {
            operationEndTime = NONE;
        }

        LOGGER.info(BASE_FORMAT, LogInfoUtil.getLogTag(),
                REQUEST_TYPE_KEY, LogInfoUtil.getRequestType(),
                SERVICE_ID_KEY, LogInfoUtil.getServiceId(),
                TIMESTAMP_KEY, getCurrentTime(),
                ACTION_ID_KEY, logInfoModel.getActionId(),
                MODULE_ID_KEY, logInfoModel.getModuleId(),
                STEP_ID_KEY, logInfoModel.getStepId(),
                OPERATION_ID_KEY, logInfoModel.getOperationId(),
                OPERATION_STARTTIME_KEY, operationStartTime,
                OPERATION_ENDTIME_KEY, operationEndTime,
                CUSTOM, logInfoModel.getCustom(),
                logInfoModel.getLogContent()
        );
    }

    /**
     * 打印日志,含有语义变量
     * @param logInfoModel 日志可选信息model
     * @param phase 日志阶段
     */
    public static void recordLog(LogInfoModel logInfoModel, String phase) {
        switch (phase) {
            case Phase.BEGIN :
                if (logContext.get() == null) {
                    initContext();
                }
                break;
            case Phase.END :
                destroyCtx();
                LOGGER.debug("destroy log context params");
                break;
            default:
                break;
        }
        recordLog(logInfoModel);
    }

    public static String getCurrentTime() {
        return formatTime(new Date());
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return dateFormat.format(date);
    }

    /**
     * 阶段
     */
    public static class Phase {
        public static final String BEGIN = "begin";
        public static final String MIDDLE = "middle";
        public static final String END = "end";
    }

    /**
     * 关键步骤
     */
    public static class StepIds {
        // STEP_ID_KEY 对应value值
        public static final String REQ_ID_ORDER_ID = "requestid2orderid";
        public static final String ORDER_ID_REQ_ID = "orderid2requestid";
    }

    /**
     * 操作id
     */
    public static class OpIds {
        // operation 对应value值
        public static final String DISPOSE_REQ = "dispose_req";
        public static final String CALL_SERVICE = "call_service";
        public static final String OPERATE_DB = "operate_db";
        public static final String CHECK_PARAM = "check_param";
        public static final String ACTION_RESULT = "action_result";
    }

    /**
     * custom常量
     */
    public static class Custom {
        // 请求异常 用于标识请求失败
        public static final String FAIL_STATUS = "fail";
        public static final String SUCCEED_STATUS = "succeed";
    }

    /**
     * 日志附加信息
     */
    public static class LogInfoModel {

        // 代表在SERVICE_ID下面的哪个模块打印出来的日志
        private String moduleId;

        // 代表这个操作的目的，比如说是创建虚机，创建blb，创建eip或者组合创建blb + eip等等，该字段为选填，在日志分析系统内部，
        // 通过request id能够区分是何种操作，同时在后端模块内部除了request id，像action id这种都不容易全程打通。
        private String actionId;

        // 代表关键步骤，为后面parse正文日志提供提示性信息，比如一个request id通过订单执行器之后，
        // 就会被转化成多个order id（对应的对个order id会在日志正文中出现），step_id就可以设置为'requestid2orderid'
        private String stepId;

        // 代表在某个SERVICE下面的某个module里面进行的操作
        private String operationId;

        // 操作开始时间
        private Date operationStartTime;

        // 操作结束时间
        private Date operationEndTime;

        // 为step_id 提供辅助信息
        private String custom;

        // 日志正文
        private String logContent;

        public LogInfoModel() {
        }

        public LogInfoModel(String moduleId, String actionId, String stepId, String operationId,
                            Date operationStartTime, Date operationEndTime, String cuntom, String logContent) {
            this.moduleId = moduleId;
            this.actionId = actionId;
            this.stepId = stepId;
            this.operationId = operationId;
            this.operationStartTime = operationStartTime;
            this.operationEndTime = operationEndTime;
            this.custom = cuntom;
            this.logContent = logContent;
        }

        // 记录 stepId
        public LogInfoModel(String moduleId, String actionId, String stepId, String custom, String logContent) {
            this.moduleId = moduleId;
            this.actionId = actionId;
            this.stepId = stepId;
            this.operationId = NONE;
            this.operationStartTime = null;
            this.operationEndTime = null;
            this.custom = custom;
            this.logContent = logContent;
        }

        // 记录opId 以及 开始时间
        public LogInfoModel(String moduleId, String actionId, Date operationStartTime, String operationId,
                            String logContent) {
            this.moduleId = moduleId;
            this.actionId = actionId;
            this.stepId = NONE;
            this.operationId = operationId;
            this.operationStartTime = operationStartTime;
            this.operationEndTime = null;
            this.custom = NONE;
            this.logContent = logContent;
        }

        // 记录opId 以及 结束时间
        public LogInfoModel(String moduleId, String actionId, String operationId, Date operationEndTime,
                            String logContent) {
            this.moduleId = moduleId;
            this.actionId = actionId;
            this.stepId = NONE;
            this.operationId = operationId;
            this.operationStartTime = null;
            this.operationEndTime = operationEndTime;
            this.custom = NONE;
            this.logContent = logContent;
        }

        public String getActionId() {
            return StringUtils.isBlank(actionId) ? NONE : actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getModuleId() {
            return StringUtils.isBlank(moduleId) ? NONE : moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getStepId() {
            return StringUtils.isBlank(stepId) ? NONE : stepId;
        }

        public void setStepId(String stepId) {
            this.stepId = stepId;
        }

        public String getOperationId() {
            return StringUtils.isBlank(operationId) ? NONE : operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        public Date getOperationStartTime() {
            return operationStartTime;
        }

        public void setOperationStartTime(Date operationStartTime) {
            this.operationStartTime = operationStartTime;
        }

        public Date getOperationEndTime() {
            return operationEndTime;
        }

        public void setOperationEndTime(Date operationEndTime) {
            this.operationEndTime = operationEndTime;
        }

        public String getCustom() {
            return StringUtils.isBlank(custom) ? NONE : custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }

        public String getLogContent() {
            return StringUtils.isBlank(logContent) ? NONE : logContent;
        }

        public void setLogContent(String logContent) {
            this.logContent = logContent;
        }
    }
}
