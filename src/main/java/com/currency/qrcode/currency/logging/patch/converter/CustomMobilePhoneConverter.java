package com.currency.qrcode.currency.logging.patch.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 内置phone转换器，具体规则详见 http://security.baidu.com/mysec/index.php/Home/Article/detail?id=177
 *
 * @author zhangmengqi
 */
public class CustomMobilePhoneConverter implements CustomConverter {
    // 格式为以下三种
    //
    // mobile" : "19793932877"
    // mobile\":\"19793932877\"
    // mobile":"19793932877"
    Pattern compile = Pattern.compile("\\d{11}");
    Pattern compileR2 = Pattern.compile("\\d{11}");

    @Override
    public String convert(String fullMessage) {
        if (fullMessage == null) {
            return null;
        }
        try {
            if (fullMessage.length() > 500) {
                Matcher matcher = compileR2.matcher(fullMessage);
                if (matcher.find()) {
                    String phoneNumString = matcher.group(0);
                    int start = matcher.start();
                    int end = matcher.end();
                    String startString = fullMessage.substring(0, start);
                    String endString = fullMessage.substring(end);
                    return startString + "*******" + phoneNumString.substring(phoneNumString.length() - 4) + endString;
                }
            } else {
                Matcher matcher = compile.matcher(fullMessage);
                if (matcher.find()) {
                    String phoneNumString = matcher.group(0);
                    int start = matcher.start();
                    int end = matcher.end();
                    String startString = fullMessage.substring(0, start);
                    String endString = fullMessage.substring(end);
                    return startString + "*******" + phoneNumString.substring(phoneNumString.length() - 4) + endString;
                }
            }
        } catch (Exception e) {
            // do nothing
        }
        return fullMessage;
    }

    @Override
    public String typeName() {
        return "mobile";
    }
}