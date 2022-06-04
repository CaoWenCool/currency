package com.currency.qrcode.currency.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public class Testutil {

    public static void main(String args[]){
        String strs = "{\\\"status\\\":{\\\"timestamp\\\":\\\"2022-06-04T02:47:26.321Z\\\",\\\"error_code\\\":0,\\\"error_message\\\":null,\\\"elapsed\\\":21,\\\"credit_count\\\":1,\\\"notice\\\":null,\\\"total_count\\\":10059},\\\"data\\\":[{\\\"id\\\":1,\\\"name\\\":\\\"Bitcoin\\\",\\\"symbol\\\":\\\"BTC\\\",\\\"slug\\\":\\\"bitcoin\\\",\\\"num_market_pairs\\\":9478,\\\"date_added\\\":\\\"2013-04-28T00:00:00.000Z\\\",\\\"tags\\\":[\\\"mineable\\\",\\\"pow\\\",\\\"sha-256\\\",\\\"store-of-value\\\",\\\"state-channel\\\",\\\"coinbase-ventures-portfolio\\\",\\\"three-arrows-capital-portfolio\\\",\\\"polychain-capital-portfolio\\\",\\\"binance-labs-portfolio\\\",\\\"blockchain-capital-portfolio\\\",\\\"boostvc-portfolio\\\",\\\"cms-holdings-portfolio\\\",\\\"dcg-portfolio\\\",\\\"dragonfly-capital-portfolio\\\",\\\"electric-capital-portfolio\\\",\\\"fabric-ventures-portfolio\\\",\\\"framework-ventures-portfolio\\\",\\\"galaxy-digital-portfolio\\\",\\\"huobi-capital-portfolio\\\",\\\"alameda-research-portfolio\\\",\\\"a16z-portfolio\\\",\\\"1confirmation-portfolio\\\",\\\"winklevoss-capital-portfolio\\\",\\\"usv-portfolio\\\",\\\"placeholder-ventures-portfolio\\\",\\\"pantera-capital-portfolio\\\",\\\"multicoin-capital-portfolio\\\",\\\"paradigm-portfolio\\\"],\\\"max_supply\\\":21000000,\\\"circulating_supply\\\":19057531,\\\"total_supply\\\":19057531,\\\"platform\\\":null,\\\"cmc_rank\\\":1,\\\"self_reported_circulating_supply\\\":null,\\\"self_reported_market_cap\\\":null,\\\"last_updated\\\":\\\"2022-06-04T02:47:00.000Z\\\",\\\"quote\\\":{\\\"USD\\\":{\\\"price\\\":29520.61091905405,\\\"volume_24h\\\":25910264562.3914,\\\"volume_change_24h\\\":-8.9523,\\\"percent_change_1h\\\":-0.24518624,\\\"percent_change_24h\\\":-3.37644049,\\\"percent_change_7d\\\":3.07236349,\\\"percent_change_30d\\\":-25.69762888,\\\"percent_change_60d\\\":-36.66303233,\\\"percent_change_90d\\\":-25.29376272,\\\"market_cap\\\":562589957728.811,\\\"market_cap_dominance\\\":46.3992,\\\"fully_diluted_market_cap\\\":619932829300.14,\\\"last_updated\\\":\\\"2022-06-04T02:47:00.000Z\\\"}}}]}";
        try {
            strs = strs.replace("\\","");
            System.out.println(strs);
            JSONObject jsonObject = JSONObject.parseObject(strs, JSONObject.class);
            System.out.println(jsonObject.get("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
