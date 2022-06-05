package com.currency.qrcode.currency.logging.dataplat;

import java.lang.annotation.*;

/**
 * Created by zhangmengqi on 21/3/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  DataPlatLog {

    // 是否使用注解打印log
    boolean disabled() default false;

    // 代表在SERVICE_ID下面的哪个模块打印出来的日志
    String moduleId() default LogUtil.NONE;

    // 代表这个操作的目的，比如说是创建虚机，创建blb，创建eip或者组合创建blb + eip等等，该字段为选填，在日志分析系统内部，
    // 通过request id能够区分是何种操作，同时在后端模块内部除了request id，像action id这种都不容易全程打通。
    String actionId() default LogUtil.NONE;

    // 代表在某个SERVICE下面的某个module里面进行的操作
    String operationId() default LogUtil.NONE;

    // 日志正文
    String logContent() default LogUtil.NONE;
}
