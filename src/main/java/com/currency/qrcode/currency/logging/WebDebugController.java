package com.currency.qrcode.currency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangmengqi on 21/3/15.
 */
@Controller
@ConditionalOnExpression("${logging.has_web_debug_appender:false}")
public class WebDebugController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Value("${logging.log.maxLength:4096}")
    private int maxLength;

    @Value("${logging.log.desensitize_expression:}")
    private String desensitizeExpression;

    @ApiIgnore
    @RequestMapping("${logging.web_debug_path:/debug}")
    public void index(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/plain;charset=UTF-8");

        ServletOutputStream stream = response.getOutputStream();
        // TODO currentUser是怎么获取到的？没有知道存的代码
        String currentUser = getCurrentUser();
        WebDebugAppender.addWebLog(currentUser, stream, maxLength, desensitizeExpression);

        log.info("[{}] debug begin", currentUser);

        forceFlush(stream);
        response.flushBuffer();

        while (true) {
            try {
                Thread.sleep(1000);
                stream.print("");
                stream.flush();
            } catch (Exception ex) {
                log.info("[{}] debug finish ex = {}", currentUser, ex);
                break;
            }
            return;
        }
    }

    private void forceFlush(ServletOutputStream stream) throws IOException {
        for (int i = 0; i < 1000; i++) {
            stream.print(" ");
        }
    }

    private String getCurrentUser() {
        String currentUser = MDC.get(WebDebugAppender.CURRENT_USER);
        if (currentUser == null) {
            currentUser = WebDebugAppender.CURRENT_USER_DEFAULT;
        }
        return currentUser;
    }
}