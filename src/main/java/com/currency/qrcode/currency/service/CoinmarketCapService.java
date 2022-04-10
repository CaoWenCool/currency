package com.currency.qrcode.currency.service;


import com.currency.qrcode.currency.model.request.ListingLatestRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class CoinmarketCapService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${coinmarketcap.key:c27e5dce-7702-42b8-9f18-908234ee54c3}")
    public String apiKey;

    private static final String COINMARKETCAP_LATEST_PRICE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
    private static final String COINMARKETCAP_LISTING_LATEST_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";


    public String getListingLatest(ListingLatestRequest request) throws URISyntaxException {
        URIBuilder query = new URIBuilder(COINMARKETCAP_LISTING_LATEST_URL);
        if (null != request.getStart()) {
            query.setParameter("start", request.getStart().toString());
        }
        if (null != request.getLimit()) {
            query.setParameter("limit", request.getLimit().toString());
        }
        if (null != request.getPriceMin()) {
            query.setParameter("price_min", request.getPriceMin().toString());
        }
        if (null != request.getPriceMax()) {
            query.setParameter("price_max", request.getPriceMax().toString());
        }
        if (null != request.getMarketCapMin()) {
            query.setParameter("market_cap_min", request.getMarketCapMin().toString());
        }
        if (null != request.getMarketCapMax()) {
            query.setParameter("market_cap_max", request.getMarketCapMax().toString());
        }
        if (null != request.getVolumn24hMin()) {
            query.setParameter("volume_24h_min", request.getVolumn24hMin().toString());
        }
        if (null != request.getVolumn24hMax()) {
            query.setParameter("volume_24h_max", request.getVolumn24hMax().toString());
        }
        if (null != request.getCirculatingSupplyMin()) {
            query.setParameter("circulating_supply_min", request.getCirculatingSupplyMin().toString());
        }
        if (null != request.getCirculatingSupplyMax()) {
            query.setParameter("circulating_supply_max", request.getCirculatingSupplyMax().toString());
        }
        if (null != request.getPercentChange24hMin()) {
            query.setParameter("percent_change_24h_min", request.getPercentChange24hMin().toString());
        }
        if (null != request.getPercentChange24hMax()) {
            query.setParameter("percent_change_24h_max", request.getPercentChange24hMax().toString());
        }
        if (null != request.getConvert()) {
            query.setParameter("convert", request.getConvert());
        }
        if (null != request.getConvertId()) {
            query.setParameter("convert_id", request.getConvertId());
        }
        if (null != request.getSort()) {
            query.setParameter("sort", request.getSort());
        }
        if (null != request.getSortDir()) {
            query.setParameter("sort_dir", request.getSortDir());
        }
        if (null != request.getCryptocurrencyType()) {
            query.setParameter("cryptocurrency_type", request.getCryptocurrencyType());
        }
        if (null != request.getTag()) {
            query.setParameter("tag", request.getTag());
        }
        if (null != request.getAux()) {
            query.setParameter("aux", request.getAux());
        }

        try {
            logger.info("request info:" + query.getQueryParams().toString());
            String result = makeAPICall(query);
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

    public String getLatestPrice(String id, String symbol, String slug, String convert, String convertId, String aux,
                                 Boolean skipInvalid) throws URISyntaxException {
        URIBuilder query = new URIBuilder(COINMARKETCAP_LATEST_PRICE_URL);
        if (null != id) {
            query.setParameter("id", id);
        }
        if (null != symbol) {
            query.setParameter("symbol", symbol);
        }
        if (null != slug) {
            query.setParameter("slug", slug);
        }
        if (null != convert) {
            query.setParameter("convert", convert);
        }
        if (null != convertId) {
            query.setParameter("convert_id", convertId);
        }
        if (null != skipInvalid) {
            query.setParameter("skip_invalid", skipInvalid.toString());
        }
        try {
            logger.info("request info:" + query.getQueryParams().toString());
            String result = makeAPICall(query);
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

    public String makeAPICall(URIBuilder query)
            throws URISyntaxException, IOException {
        String response_content = "";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.setHeader(HttpHeaders.ACCEPT_ENCODING, "deflate,gzip");
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
