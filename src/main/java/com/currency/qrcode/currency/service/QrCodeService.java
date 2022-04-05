package com.currency.qrcode.currency.service;

import com.alibaba.fastjson.JSONObject;
import com.currency.qrcode.currency.model.AliHttpConstant;
import com.currency.qrcode.currency.model.CurrencyEnum;
import com.currency.qrcode.currency.util.HttpUtils;
import com.currency.qrcode.currency.util.JsonConverUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeService {

    @Value("${app.code:89eb4dc0c73c42eda8bf2b9ae6df6ccf}")
    public String appCode;

    public Integer codeWidth = 300;

    public Integer codeHeight = 300;

    public void qrCodeGenerate(CurrencyEnum currencyEnum, Double money, String requireAddress, HttpServletResponse response) throws Exception {
        if (!CurrencyEnum.checkAddress(currencyEnum,requireAddress)){
            throw new Exception(currencyEnum.name() + "的地址错误！");
        }
        // 获取当前的价格
        BigDecimal count = this.getCount(currencyEnum,money);
        if(null == count){
            throw new Exception(currencyEnum.name() + "二维码生成异常");
        }
        // 生成二维码
        String msg = currencyEnum.getCurrency() + ":" + requireAddress + "?" + "amount=" + count;
        generateQrCode(msg,response,currencyEnum);

    }

    public void generateQrCode(String msg,HttpServletResponse response,CurrencyEnum currencyEnum) throws WriterException, IOException {
        Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(msg, BarcodeFormat.QR_CODE,codeWidth,codeHeight,hints);
        response.setHeader("Content-Type","application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=" + currencyEnum.name() + ".png");
        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    }


    public BigDecimal getCount(CurrencyEnum currencyEnum,Double money){
        String host = AliHttpConstant.HOST;
        String path = AliHttpConstant.QUERY_PRICE_URL;
        String method = HttpMethod.GET.name();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        String currencyName = currencyEnum.name();
        if(currencyEnum == CurrencyEnum.USDT_ERC20 || currencyEnum == CurrencyEnum.USDT_OMNI || currencyEnum == CurrencyEnum.USDT_TRC20){
            currencyName = "USDT";
        }
        querys.put("symbol", currencyName);
        querys.put("withks", "0");
        querys.put("withticks", "0");
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            Integer code = response.getStatusLine().getStatusCode();
            if(!code.equals(HttpStatus.OK.value())){
                return null;
            }
            String line = "";
            StringBuffer sb = new StringBuffer();
            BufferedReader URLinput = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = URLinput.readLine()) != null){
                sb.append(line);
            }
            JSONObject jsonObject = JsonConverUtil.fromJSON(sb.toString(), JSONObject.class);
            Map map = (Map) jsonObject.get("Obj");
            Double price = (Double) map.get("P");
            BigDecimal bd = new BigDecimal(money);
            BigDecimal bd2 = new BigDecimal(price);
            BigDecimal result = bd.divide(bd2,10, BigDecimal.ROUND_HALF_DOWN);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void setCodeSize(Integer width,Integer height){
        this.codeWidth = width;
        this.codeHeight = height;
    }

    public void setAppCode(String appCode){
        this.appCode = appCode;
    }

}
