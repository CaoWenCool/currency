package com.currency.qrcode.currency.daemon.task;

import com.currency.qrcode.currency.util.HttpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EthTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${eth.address:0x82d30797cc1b191dcdebc6c2befe820ec8efe9cb}")
    public String ethAddress;
    private static final String TOKEN_VIEW_URL = "http://freeapi.tokenview.com:8088/addr/b/eth/";
    public static BigDecimal ethBalance;

    @Async("asyncTaskExecutor")
    public void updateEthAddressBalance(){
        StringBuffer sb = new StringBuffer();
        sb.append(TOKEN_VIEW_URL);
        sb.append(ethAddress);
        Object result = HttpsUtils.get(sb.toString(),Object.class);
        String resultStr = result.toString();
        resultStr = resultStr.replace("{","");
        resultStr = resultStr.replace("}","");
        String[] strList = resultStr.split("=");
        Double dataDouble = Double.valueOf(strList[3]);
        ethBalance = new BigDecimal(dataDouble).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getEthBalance() {
        return ethBalance;
    }

    public static void setEthBalance(BigDecimal ethBalance) {
        EthTask.ethBalance = ethBalance;
    }


}
