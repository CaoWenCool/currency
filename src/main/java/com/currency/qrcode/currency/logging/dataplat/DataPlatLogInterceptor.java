package com.currency.qrcode.currency.logging.dataplat;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zhangmengqi on 21/3/15.
 */
public class DataPlatLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return logByAnnotation(true, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logByAnnotation(false, handler);
    }

    private boolean logByAnnotation(boolean start, Object handler) {
        if (handler == null || !handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        DataPlatLog dataPlatLog = handlerMethod.getMethodAnnotation(DataPlatLog.class);
        String className = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();

        if (dataPlatLog == null) {
            // print default log
            printLog(start, className, methodName, LogUtil.OpIds.DISPOSE_REQ, LogUtil.NONE);
            return true;
        }
        boolean disabled = dataPlatLog.disabled();
        if (disabled) {
            // 业务自己加log
            return true;
        }

        String actionId = LogUtil.NONE.equals(dataPlatLog.actionId())
                ? methodName : dataPlatLog.actionId();
        String moduleId = LogUtil.NONE.equals(dataPlatLog.moduleId())
                ? className : dataPlatLog.moduleId();
        String operationId = LogUtil.NONE.equals(dataPlatLog.operationId())
                ? LogUtil.OpIds.DISPOSE_REQ : dataPlatLog.operationId();
        String logContent = dataPlatLog.logContent();

        // 注解方式加log
        printLog(start, moduleId, actionId, operationId, logContent );
        return true;
    }

    private void printLog(boolean start, String moduleId, String actionId, String operationId, String logContent) {
        if (start) {
            LogUtil.recordLog(new LogUtil.LogInfoModel(moduleId, actionId,
                    new Date(), operationId, logContent));
        } else {
            LogUtil.recordLog(new LogUtil.LogInfoModel(moduleId, actionId,
                    operationId, new Date(), logContent));
        }
    }

}
