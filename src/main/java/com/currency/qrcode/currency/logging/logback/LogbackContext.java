package com.currency.qrcode.currency.logging.logback;


import com.currency.qrcode.currency.logging.patch.CustomLogbackValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * logback环境配置
 *
 * @author liucunliang
 * @version 1.0.0
 * @since 1.0.0
 * @create 2021/1/13 下午5:22
 */
public class LogbackContext {

    static final Logger log = LoggerFactory.getLogger(LogbackContext.class);

    private static CustomLogbackValve customLogbackValve;

    private static final String fileNamePathValue = "${logging.access-config:classpath:logback-access.xml}";
    private static final String enableAccessLog = "${logging.has_access_log:true}";

    public static CustomLogbackValve getCustomLogbackValve() {
        return customLogbackValve;
    }

    public static void initCustomLogbackValve(ApplicationContext context) {
        Environment environment = context.getEnvironment();
        String resolvePlaceholders = environment.resolvePlaceholders(enableAccessLog);
        if (resolvePlaceholders == null || !"true".equals(resolvePlaceholders.trim())) {
            return;
        }

        final String tempFileName;
        try {
            String filename = environment.resolvePlaceholders(fileNamePathValue);
            // 由于logback-access的logbackValve只能读真实文件，所以将resource复制到临时文件，用于初始化
            String originalUrl = context.getResource(filename).getURL().toString();
            log.debug("logback-access originalUrl: {}", originalUrl);

            String fileSignature =
                    Integer.toString(originalUrl.hashCode()) + context.getResource(filename).contentLength();
            tempFileName = "baidu-xcloud-logback-access-" + fileSignature + ".xml";
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), tempFileName);
            log.debug("logback-access configFilePath: {}", path.toString());

            log.debug("copy logback-access.xml begin");
            try (InputStream inputStream = context.getResource(filename).getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            log.debug("copy logback-access.xml success");
        } catch (Exception e) {
            log.error("logback-access config error", e);
            return;
        }

        customLogbackValve = new CustomLogbackValve();
        customLogbackValve.setAsyncSupported(true);
        customLogbackValve.setFilename(tempFileName);
        customLogbackValve.initLogaccess();
    }

}
