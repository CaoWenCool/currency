package com.currency.qrcode.currency.service;


import com.currency.qrcode.currency.model.request.ListingLatestRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinmarketCapService {

    @Value("${coinmarketcap.key:c27e5dce-7702-42b8-9f18-908234ee54c3}")
    public String apiKey;

    private static final String COINMARKETCAP_URL = "https://pro-api.coinmarketcap.com/v1/";
    private static final String COINMARKETCAP_LISTING_LATEST_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";


    public String getListingLatest(ListingLatestRequest request) {
        List<NameValuePair> paratmers = new ArrayList<>();
        paratmers.add(new BasicNameValuePair("start", request.getStart().toString()));
        paratmers.add(new BasicNameValuePair("limit", request.getLimit().toString()));
        paratmers.add(new BasicNameValuePair("price_min", request.getPriceMin().toString()));
        paratmers.add(new BasicNameValuePair("price_max", request.getPriceMax().toString()));
        paratmers.add(new BasicNameValuePair("market_cap_min", request.getMarketCapMin().toString()));
        paratmers.add(new BasicNameValuePair("market_cap_max", request.getMarketCapMax().toString()));
        paratmers.add(new BasicNameValuePair("volume_24h_min", request.getVolumn24hMin().toString()));
        paratmers.add(new BasicNameValuePair("volume_24h_max", request.getVolumn24hMax().toString()));
        paratmers.add(new BasicNameValuePair("circulating_supply_min", request.getCirculatingSupplyMin().toString()));
        paratmers.add(new BasicNameValuePair("circulating_supply_max", request.getCirculatingSupplyMax().toString()));
        paratmers.add(new BasicNameValuePair("percent_change_24h_min", request.getPercentChange24hMin().toString()));
        paratmers.add(new BasicNameValuePair("percent_change_24h_max", request.getPercentChange24hMax().toString()));
        paratmers.add(new BasicNameValuePair("convert", request.getConvert().toString()));
        paratmers.add(new BasicNameValuePair("convert_id", request.getConvertId()));
        paratmers.add(new BasicNameValuePair("sort", request.getSort()));
        paratmers.add(new BasicNameValuePair("sort_dir", request.getSortDir()));
        paratmers.add(new BasicNameValuePair("cryptocurrency_type", request.getCryptocurrencyType()));
        paratmers.add(new BasicNameValuePair("tag", request.getTag()));
        paratmers.add(new BasicNameValuePair("aux", request.getAux()));
        try {
            String result = makeAPICall(COINMARKETCAP_LISTING_LATEST_URL, paratmers);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return null;
    }

    public String getLatestPrice(String path) {
        String uri = COINMARKETCAP_URL + path;
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("id", "1"));
        paratmers.add(new BasicNameValuePair("slug", "bitcoin"));
        paratmers.add(new BasicNameValuePair("symbol", "BTC"));
        paratmers.add(new BasicNameValuePair("CMC_PRO_API_KEY", apiKey));

        String result = null;
        try {
            result = makeAPICall(uri, paratmers);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return result;
    }

    public String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        for (NameValuePair nameValuePair : parameters) {
            query.setParameter(nameValuePair.getName(), nameValuePair.getValue());
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
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
