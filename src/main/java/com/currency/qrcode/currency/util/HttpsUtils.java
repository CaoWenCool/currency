package com.currency.qrcode.currency.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpsUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String _GET = "GET";
    private static final String _POST = "POST";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;

    private static final Gson gson = new Gson();

    public static <T> T get(String url, Class<T> targetClazz) {
        return get(url, null, targetClazz);
    }

    public static <T> T get(String url, Map<String, String> params, Class<T> targetClazz) {
        return get(url, params, null, targetClazz);
    }

    /**
     * 表单格式提交
     */
    public static <T> T post(String url, String body, Class<T> targetClazz) {
        return toPost(url, body, null, null, targetClazz);
    }

    /**
     * 表单格式提交
     */
    public static <T> T post(String url, String body, Map<String, String> headers, Class<T> targetClazz) {
        return toPost(url, body, headers, null, targetClazz);
    }

    /**
     * JSON格式提交
     */
    public static <T> T doPost(String url, String body, Class<T> targetClazz) {
        return toPost(url, body, null, "application/json;charset=UTF-8", targetClazz);
    }

    /**
     * JSON格式提交
     */
    public static <T> T doPost(String url, String body, Map<String, String> headers, Class<T> targetClazz) {
        return toPost(url, body, headers, "application/json;charset=UTF-8", targetClazz);
    }

    /**
     * 初始化http请求参数
     */
    private static HttpURLConnection initHttp(String url, String method, Map<String, String> headers, String contentType) throws Exception {
        URL _url = new URL(url);
        HttpURLConnection http = (HttpURLConnection) _url.openConnection();
        // 连接超时
        http.setConnectTimeout(DEF_CONN_TIMEOUT);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(DEF_READ_TIMEOUT);
        http.setUseCaches(false);
        http.setRequestMethod(method);
        if (StringUtils.isEmpty(contentType)) {
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } else {
            http.setRequestProperty("Content-Type", contentType);
        }
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        return http;
    }

    public static <T> T get(String url, Map<String, String> params, Map<String, String> headers, Class<T> targetClazz) {
        T result = null;
        try {
            HttpURLConnection http = initHttp(initParams(url, params), _GET, headers, null);
            InputStream in = http.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String valueString = null;
            StringBuilder bufferRes = new StringBuilder();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            in.close();
            http.disconnect();// 关闭连接
            result = gson.fromJson(bufferRes.toString(), targetClazz);
        } catch (Exception e) {
            log.error("http请求异常：url={}, 异常信息={}", url, e.getMessage());
        }
        return result;
    }

    private static <T> T toPost(String url, String params, Map<String, String> headers, String contentType, Class<T> targetClazz) {
        T result = null;
        try {
            HttpURLConnection http = initHttp(url, _POST, headers, contentType);
            OutputStream out = http.getOutputStream();
            out.write(params.getBytes(DEFAULT_CHARSET));
            out.flush();
            out.close();

            InputStream in = http.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String valueString = null;
            StringBuilder bufferRes = new StringBuilder();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            in.close();
            http.disconnect();// 关闭连接
            result = gson.fromJson(bufferRes.toString(), targetClazz);
        } catch (Exception e) {
            log.error("http请求异常：url={}, 异常信息={}", url, e.getMessage());
        }
        return result;
    }

    /**
     * 功能描述: 构造请求参数
     */
    private static String initParams(String url, Map<String, String> params) throws Exception {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")) {
            sb.append("?");
        }
        sb.append(map2Url(params));
        return sb.toString();
    }

    /**
     * map构造url
     */
    private static String map2Url(Map<String, String> paramToMap) throws Exception {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuilder url = new StringBuilder();
        boolean isfist = true;
        for (Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                url.append(URLEncoder.encode(value, DEFAULT_CHARSET));
            }
        }
        return url.toString();
    }

    public static void main(String[] args) {
        System.out.println(get("http://freeapi.tokenview.com:8088/addr/b/eth/0x82d30797cc1b191dcdebc6c2befe820ec8efe9cb",Object.class));
    }



}
