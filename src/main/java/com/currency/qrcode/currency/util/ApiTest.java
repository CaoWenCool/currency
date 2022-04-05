package com.currency.qrcode.currency.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ApiTest {

    private static String apiKey = "c27e5dce-7702-42b8-9f18-908234ee54c3";
    // https://pro-api.coinmarketcap.com/v1/blockchain/statistics/latest

    public static void main(String[] args) throws IOException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890"  ;
// 对http开启代理
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);

//        URL url = new URL("https://pro.coinmarketcap.com/");
//        URLConnection connection = url.openConnection();
//        connection.connect();
//        InputStream inputStream = connection.getInputStream();        byte[] bytes = new byte[1024];        while (inputStream.read(bytes) >= 0) {
//            System.out.println(new String(bytes));
//        }

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("slug","bitcoin"));
        paratmers.add(new BasicNameValuePair("symbol","BTC"));
//        paratmers.add(new BasicNameValuePair("convert","ETH"));
        paratmers.add(new BasicNameValuePair("CMC_PRO_API_KEY",apiKey));

        try {
            String result = makeAPICall(uri, paratmers);
            System.out.println("------------------");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
    }

    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        for(NameValuePair nameValuePair:parameters){
            query.setParameter(nameValuePair.getName(),nameValuePair.getValue());
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
//        request.setHeader(HttpHeaders.ACCEPT_ENCODING, "deflate, gzip");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
            client.close();
        }

        return response_content;
    }
}
