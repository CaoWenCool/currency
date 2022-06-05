package com.currency.qrcode.currency.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.currency.qrcode.currency.logging.patch.CustomLogPatternLayoutEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangmengqi on 21/3/15.
 */
public class WebDebugAppender extends AppenderBase<ILoggingEvent> {

    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_USER_DEFAULT = "AllUser";

    private static CustomLogPatternLayoutEncoder encoder;
    private static Map<String, CustomLogPatternLayoutEncoder> userEncoderMap = new ConcurrentHashMap<>();
    private static Map<String, OutputStream> outputStreamMap = new ConcurrentHashMap<>();

    public static void addWebLog(
            String userName, OutputStream stream, int maxLength, String desensitizeExpression) throws IOException {
        CustomLogPatternLayoutEncoder userEncoder = new CustomLogPatternLayoutEncoder();
        userEncoder.setContext(encoder.getContext());
        userEncoder.setCharset(encoder.getCharset());
        userEncoder.setPattern(encoder.getPattern());
        userEncoder.setMaxLength(maxLength);
        userEncoder.setDesensitizeExpression(desensitizeExpression);
        userEncoder.setImmediateFlush(true);
        userEncoder.start();

        userEncoderMap.put(userName, userEncoder);
        outputStreamMap.put(userName, stream);
    }

    @Override
    public void start() {
        if (encoder == null) {
            addError("No encoder set for the appender named [" + name + "].");
            return;
        }

        super.start();
    }

    public void append(ILoggingEvent event) {
        //        if (event.getLevel().levelInt < Level.INFO_INT)
        //            return;

        String currentUser = getCurrentUser(event);
        CustomLogPatternLayoutEncoder encoder = userEncoderMap.get(currentUser);
        if (encoder == null) {
            return;
        }
        OutputStream outputStream = outputStreamMap.get(currentUser);
        if (outputStream == null) {
            userEncoderMap.remove(currentUser);
            return;
        }
        byte[] bytes = encoder.encode(event);
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            encoder.stop();
            userEncoderMap.remove(currentUser);
            outputStreamMap.remove(currentUser);
        }
    }

    private String getCurrentUser(ILoggingEvent event) {
        String currentUser = event.getMDCPropertyMap().get(CURRENT_USER);
        if (currentUser == null) {
            currentUser = CURRENT_USER_DEFAULT;
        }
        return currentUser;
    }

    public CustomLogPatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(CustomLogPatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }
}