package com.currency.qrcode.currency.service;

import com.currency.qrcode.currency.model.response.ActivityTimeResponse;
import com.currency.qrcode.currency.util.TimeUtils;
import org.springframework.stereotype.Service;


@Service
public class BaseService {


    public String getActivityTime(){
        // 获取当前时间
        StringBuffer sb = new StringBuffer();
        sb.append(TimeUtils.getMonthOfFirstDay());
        sb.append("-");
        sb.append(TimeUtils.getMonthOfLastOneDay());
        return sb.toString();
    }
}
