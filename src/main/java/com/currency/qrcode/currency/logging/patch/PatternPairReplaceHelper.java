package com.currency.qrcode.currency.logging.patch;

import com.currency.qrcode.currency.logging.patch.converter.CustomConverterFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.regex.Matcher;

/**
 * 日志替换
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class PatternPairReplaceHelper {

    /**
     * maxReplaceCount 最大循环次数，由于日志是同步的，防止正则表达式写的太随意导致正则回溯
     * <p>
     * 从而导致请求超时 待日志切换成异步(log4j2)后可以删除此逻辑
     *
     * @param originStringBuilder 原始串
     * @param patternPairs 模式匹配
     * @param maxReplaceCount 最大循环次数
     * @return 转换后串
     */
    public static String convertLog(StringBuilder originStringBuilder, List<PatternPair> patternPairs,
            int maxReplaceCount) {
        if (originStringBuilder == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(patternPairs)) {
            return originStringBuilder.toString();
        }
        StringBuffer tempBuffer = new StringBuffer(originStringBuilder);
        for (PatternPair patternPair : patternPairs) {
            if (tempBuffer.length() > 500) {
                tempBuffer = convertByRe2jMatcher(maxReplaceCount, tempBuffer, patternPair);
            } else {
                tempBuffer = convertByJavaMatcher(maxReplaceCount, tempBuffer, patternPair);
            }
        }
        return tempBuffer.toString();
    }

    private static StringBuffer convertByJavaMatcher(int maxReplaceCount, StringBuffer tempBuffer,
            PatternPair patternPair) {
        final Matcher matcher = patternPair.pattern.matcher(tempBuffer);
        boolean result = matcher.find();
        if (result) {
            StringBuffer replaceBuffer = new StringBuffer();
            int matchCount = 0;
            do {
                String replacement = patternPair.replacement;
                // 自定义convert替换
                boolean isReplaceByConvert = replaceByCustomConverter(CommonMatcherHelper.createByJavaMatch(matcher),
                        replaceBuffer, replacement);
                if (isReplaceByConvert == false) {
                    matcher.appendReplacement(replaceBuffer, replacement);
                }
                matchCount++;
                if (matchCount > maxReplaceCount) {
                    break;
                }
                result = matcher.find();
            } while (result);
            matcher.appendTail(replaceBuffer);
            tempBuffer = replaceBuffer;
        }
        return tempBuffer;
    }

    private static StringBuffer convertByRe2jMatcher(int maxReplaceCount, StringBuffer tempBuffer,
            PatternPair patternPair) {
        final Matcher matcher = patternPair.patternR2.matcher(tempBuffer);
        boolean result = matcher.find();
        if (result) {
            StringBuffer replaceBuffer = new StringBuffer();
            int matchCount = 0;
            do {
                String replacement = patternPair.replacement;
                boolean isReplaceByConvert = replaceByCustomConverter(CommonMatcherHelper.createByRe2jMatcher(matcher),
                        replaceBuffer, replacement);
                if (isReplaceByConvert == false) {
                    matcher.appendReplacement(replaceBuffer, replacement);
                }
                matchCount++;
                if (matchCount > maxReplaceCount) {
                    break;
                }
                result = matcher.find();
            } while (result);
            matcher.appendTail(replaceBuffer);
            tempBuffer = replaceBuffer;
        }
        return tempBuffer;
    }

    private static boolean replaceByCustomConverter(CommonMatcher matcher, StringBuffer replaceBuffer,
            String replacement) {
        boolean isReplaceByConvert = false;
        if (replacement.startsWith("#") && replacement.length() > 1) {
            String processType = replacement.substring(1);
            if (CustomConverterFactory.hasConvert(processType)) {
                String fullMessage = matcher.group0();
                String customReplacement = CustomConverterFactory.convert(processType, fullMessage);
                matcher.appendReplacement(replaceBuffer, customReplacement);
                isReplaceByConvert = true;
            }
        }
        return isReplaceByConvert;
    }

    private static interface CommonMatcher {

        String group0();

        void appendReplacement(StringBuffer replaceBuffer, String customReplacement);

    }

    private static class CommonMatcherHelper {

        static CommonMatcher createByJavaMatch(final Matcher matcher) {

            return new CommonMatcher() {
                @Override
                public String group0() {
                    return matcher.group(0);
                }

                @Override
                public void appendReplacement(StringBuffer replaceBuffer, String customReplacement) {
                    matcher.appendReplacement(replaceBuffer, customReplacement);
                }
            };
        }

        private static CommonMatcher createByRe2jMatcher(final Matcher matcher) {

            return new CommonMatcher() {
                @Override
                public String group0() {
                    return matcher.group(0);
                }

                @Override
                public void appendReplacement(StringBuffer replaceBuffer, String customReplacement) {
                    matcher.appendReplacement(replaceBuffer, customReplacement);
                }
            };
        }

    }
}
