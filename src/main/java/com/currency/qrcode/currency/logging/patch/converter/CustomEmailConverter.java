package com.currency.qrcode.currency.logging.patch.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 内置email转换器，具体规则详见http://security.baidu.com/mysec/index.php/Home/Article/detail?id=177
 *
 * @author zhangmengqi
 */
public class CustomEmailConverter implements CustomConverter {

    private static final String emailPatten = "(\\w+([-+.]\\w+)*)@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private Pattern compile = Pattern.compile(emailPatten);
    private Pattern compileR2 = Pattern.compile(emailPatten);

    @Override
    public String convert(String fullMessage) {
        try {
            if (fullMessage != null && fullMessage.length() > 500) {
                StringBuffer maskEmailBuffer = new StringBuffer();
                Matcher matcher = compileR2.matcher(fullMessage);
                while (matcher.find()) {
                    String fullEmailString = matcher.group();
                    String emailPrefix = matcher.group(1);
                    String emailFlagString = fullEmailString.substring(emailPrefix.length());
                    if (emailPrefix.length() > 3) {
                        emailPrefix =
                                emailPrefix.substring(0, 2) + "***" + emailPrefix.substring(emailPrefix.length() - 1);
                    } else {
                        emailPrefix = emailPrefix.charAt(0) + "***";
                    }
                    matcher.appendReplacement(maskEmailBuffer, emailPrefix + emailFlagString);
                }
                matcher.appendTail(maskEmailBuffer);
                return maskEmailBuffer.toString();
            } else {
                StringBuffer maskEmailBuffer = new StringBuffer();
                Matcher matcher = compile.matcher(fullMessage);
                while (matcher.find()) {
                    String fullEmailString = matcher.group();
                    String emailPrefix = matcher.group(1);
                    String emailFlagString = fullEmailString.substring(emailPrefix.length());
                    if (emailPrefix.length() > 3) {
                        emailPrefix =
                                emailPrefix.substring(0, 2) + "***" + emailPrefix.substring(emailPrefix.length() - 1);
                    } else {
                        emailPrefix = emailPrefix.charAt(0) + "***";
                    }
                    matcher.appendReplacement(maskEmailBuffer, emailPrefix + emailFlagString);
                }
                matcher.appendTail(maskEmailBuffer);
                return maskEmailBuffer.toString();
            }
        } catch (Exception e) {
            // do nothing
        }
        return fullMessage;
    }

    @Override
    public String typeName() {
        return "email";
    }

}