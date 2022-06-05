package com.currency.qrcode.currency.service;

import com.currency.qrcode.currency.model.response.ActivityTimeResponse;
import com.currency.qrcode.currency.util.TimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class BaseService {

    @Value("${opensea.url:https://testnets.opensea.io/collection/bitcoin-bets-may-2022}")
    private String openseaUrl;


    public ActivityTimeResponse getActivityInfo(){
        // 获取当前时间
        ActivityTimeResponse activityTimeResponse = new ActivityTimeResponse();
        StringBuffer sb = new StringBuffer();
        sb.append(TimeUtils.getMonthOfFirstDay());
        sb.append("-");
        sb.append(TimeUtils.getMonthOfLastOneDay());
        activityTimeResponse.setActivityTime(sb.toString());
        activityTimeResponse.setOpenseaUrl(openseaUrl);
        return activityTimeResponse;
    }


}
