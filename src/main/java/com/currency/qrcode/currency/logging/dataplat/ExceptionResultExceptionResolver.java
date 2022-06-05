package com.currency.qrcode.currency.logging.dataplat;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangmengqi on 21/3/15.
 */
public class ExceptionResultExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) {
        if (handler == null || !handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return null;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        DataPlatLog dataPlatLog = handlerMethod.getMethodAnnotation(DataPlatLog.class);
        String className = handlerMethod.getBeanType().getSimpleName();
        String methodName = handlerMethod.getMethod().getName();
        // 采用注解的参数
        if (dataPlatLog != null) {
            printFailedLogByAnnotation(dataPlatLog, methodName, className);
        } else {
            recordFailedLog(className, methodName);
        }
        return null;
    }

    private void printFailedLogByAnnotation(DataPlatLog dataPlatLog,
            String defaultActionName, String defaultModuleId) {
        String actionId = LogUtil.NONE.equals(dataPlatLog.actionId())
                ? defaultActionName : dataPlatLog.actionId();
        String moduleId = LogUtil.NONE.equals(dataPlatLog.moduleId())
                ? defaultModuleId : dataPlatLog.moduleId();
        recordFailedLog(moduleId, actionId);
    }

    private void recordFailedLog(String moduleId, String actionId) {
        LogUtil.recordLog(new LogUtil.LogInfoModel(moduleId, actionId, LogUtil.OpIds.ACTION_RESULT,
                LogUtil.Custom.FAIL_STATUS, LogUtil.NONE));
    }
}
