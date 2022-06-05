/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.currency.qrcode.currency.logging.patch;

import ch.qos.logback.access.spi.IAccessEvent;
import ch.qos.logback.core.pattern.PatternLayoutEncoderBase;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定制access日志布局
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class CustomAccessPatternLayoutEncoder extends PatternLayoutEncoderBase<IAccessEvent> {

    private static final Pattern DESENSITIZE_PATTERN =
            Pattern.compile("`(.*?)`\\s*:\\s*`(.*?)`\\s*;|`(.*?)`\\s*:\\s*`(.*?)`");

    private int maxLength = 16 * 1024;

    /**
     * 最大循环次数，由于日志是同步的，防止正则表达式写的太随意导致正则回溯
     * 
     * 从而导致请求超时 待日志切换成异步(log4j2)后可以删除此逻辑
     * 
     */
    private int maxReplaceCount = 100;

    private String desensitizeExpression;

    private List<PatternPair> replace;

    public List<PatternPair> getReplace() {
        return replace;
    }

    public void setReplace(List<PatternPair> replace) {
        this.replace = replace;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getDesensitizeExpression() {
        return desensitizeExpression;
    }

    public void setDesensitizeExpression(String desensitizeExpression) {
        this.desensitizeExpression = desensitizeExpression;
        if (StringUtils.isNotBlank(desensitizeExpression)) {
            replace = new ArrayList<>();
            Matcher matcher = DESENSITIZE_PATTERN.matcher(desensitizeExpression);
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    replace.add(new PatternPair(Pattern.compile(matcher.group(1)),
                            Pattern.compile(matcher.group(1)), matcher.group(2)));
                } else {
                    replace.add(new PatternPair(Pattern.compile(matcher.group(3)),
                            Pattern.compile(matcher.group(3)), matcher.group(4)));
                }
            }
        }

    }

    public int getMaxReplaceCount() {
        return maxReplaceCount;
    }

    public void setMaxReplaceCount(int maxReplaceCount) {
        this.maxReplaceCount = maxReplaceCount;
    }

    @Override
    public void start() {
        CustomAccessPatternLayout patternLayout = new CustomAccessPatternLayout();
        patternLayout.setMaxLength(maxLength);
        patternLayout.setReplace(replace);
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }

}
