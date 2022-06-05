package com.currency.qrcode.currency.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.currency.qrcode.currency.daemon.task.BtcTask;
import com.currency.qrcode.currency.daemon.task.EthTask;
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
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class CoinService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${eth.address:0x82d30797cc1b191dcdebc6c2befe820ec8efe9cb}")
    public String ethAddress;

    public EthAddressResponse getEthNumber(){
        EthAddressResponse ethAddressResponse = new EthAddressResponse();
        StringBuffer sb = new StringBuffer();
        sb.append("<a href='https://etherscan.io/address/");
        sb.append(ethAddress);
        sb.append("'>");
        sb.append(ethAddress);
        sb.append("</a>");
        ethAddressResponse.setAddress(sb.toString());
        ethAddressResponse.setBalance(EthTask.getEthBalance());
        return ethAddressResponse;
    }

    public CoinPriceResponse getBtcPriceInfo() {
        CoinPriceResponse coinPriceResponse = new CoinPriceResponse();
        coinPriceResponse.setPrice(BtcTask.getPrice());
        coinPriceResponse.setLowPrice(BtcTask.getLowPrice());
        coinPriceResponse.setHightPirce(BtcTask.getHighPricre());
        return coinPriceResponse;
    }
}
