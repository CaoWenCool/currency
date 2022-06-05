/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging.patch;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;

import java.util.List;

/**
 * 定制日志布局
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class CustomLogPatternLayout extends PatternLayout {

    private int maxLength = 16 * 1024;

    private List<PatternPair> replace;

    /**
     * 最大循环次数，由于日志是同步的，防止正则表达式写的太随意导致正则回溯
     * <p>
     * 从而导致请求超时 待日志切换成异步(log4j2)后可以删除此逻辑
     */
    private int maxReplaceCount = 100;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<PatternPair> getReplace() {
        return replace;
    }

    public void setReplace(List<PatternPair> replace) {
        this.replace = replace;
    }

    public int getMaxReplaceCount() {
        return maxReplaceCount;
    }

    public void setMaxReplaceCount(int maxReplaceCount) {
        this.maxReplaceCount = maxReplaceCount;
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String source = super.doLayout(event);
        StringBuilder builder = null;
        if (source == null) {
            return "";
        }
        if (maxLength == 0) {
            return "";
        }
        if (source.length() > maxLength) {
            builder = new StringBuilder(maxLength + 2);
            builder.append(source, 0, maxLength - 1).append(CoreConstants.LINE_SEPARATOR);
        }
        if (replace != null) {
            if (builder == null) {
                builder = new StringBuilder(source);
            }
            return PatternPairReplaceHelper.convertLog(builder, replace, maxReplaceCount);
        }
        if (builder != null) {
            return builder.toString();
        } else {
            return source;
        }
    }

}
