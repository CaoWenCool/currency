package com.currency.qrcode.currency.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.currency.qrcode.currency.model.request.ListingLatestRequest;
import com.currency.qrcode.currency.model.response.CoinPriceResponse;
import com.currency.qrcode.currency.model.response.EthAddressResponse;
import com.currency.qrcode.currency.util.HttpsUtils;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class CoinService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${coinmarketcap.key:c27e5dce-7702-42b8-9f18-908234ee54c3}")
    public String apiKey;

    @Value("${eth.address:0x82d30797cc1b191dcdebc6c2befe820ec8efe9cb}")
    public String ethAddress;

    private static final String COINMARKETCAP_URL = "https://pro-api.coinmarketcap.com/v1";
    private static final String COINMARKETCAP_LISTING_LATEST_URL = COINMARKETCAP_URL + "/cryptocurrency/listings/latest";
    private static final String TOKEN_VIEW_URL = "http://freeapi.tokenview.com:8088/addr/b/eth/";

    public EthAddressResponse getEthNumber(){
        StringBuffer sb = new StringBuffer();
        sb.append(TOKEN_VIEW_URL);
        sb.append(ethAddress);
        String result = HttpsUtils.get(sb.toString(),String.class);
        logger.info("result " + result);
        Map jsonMap = JSONObject.parseObject(result, Map.class);
        BigDecimal data = BigDecimal.valueOf(Double.valueOf(String.valueOf(jsonMap.get("data"))));
        EthAddressResponse ethAddressResponse = new EthAddressResponse();
        ethAddressResponse.setAddress(ethAddress);
        ethAddressResponse.setBalance(data.setScale(4, BigDecimal.ROUND_HALF_UP));
        return ethAddressResponse;
    }

    public CoinPriceResponse getListingLatest(ListingLatestRequest request) throws URISyntaxException {
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
        String coinResult = getResult(query);
        JSONObject jsonObject = JSONObject.parseObject(coinResult, JSONObject.class);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject btcJSON = (JSONObject) jsonArray.get(0);
        JSONObject quote = btcJSON.getJSONObject("quote");
        JSONObject usd  = quote.getJSONObject("USD");
        BigDecimal price = (BigDecimal) usd.get("price");
        CoinPriceResponse coinPriceResponse = new CoinPriceResponse();
        coinPriceResponse.setPrice(price.setScale(4, BigDecimal.ROUND_HALF_UP));
        coinPriceResponse.setLowPrice(price.setScale(3, BigDecimal.ROUND_HALF_UP));
        coinPriceResponse.setHightPirce(price.setScale(3, BigDecimal.ROUND_HALF_UP));
        return coinPriceResponse;
    }

    private String getResult(URIBuilder query){
        try {
            String result = makeAPICall(query);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error("Error: Invalid URL " + e.toString());
        }
        return  null;
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
