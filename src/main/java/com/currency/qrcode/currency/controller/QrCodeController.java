package com.currency.qrcode.currency.controller;

import com.currency.qrcode.currency.model.ApiResult;
import com.currency.qrcode.currency.model.CurrencyEnum;
import com.currency.qrcode.currency.model.request.ListingLatestRequest;
import com.currency.qrcode.currency.service.CoinmarketCapService;
import com.currency.qrcode.currency.service.QrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

@Validated
@RequestMapping(value = "/currency", produces = "application/json")
@Api(tags = "加密数字货币")
@RestController
public class QrCodeController {

    @Autowired
    QrCodeService qrCodeService;

    @Autowired
    CoinmarketCapService coinmarketCapService;

//    @ApiOperation(
//            value = "二维码生成接口",
//            notes = "生成不同加密数字货币的二维码"
//    )
//    @GetMapping(value = "/qrCode")
//    public ApiResult generatorQrCode(
//            @ApiParam("币种")
//            @RequestParam CurrencyEnum currencyEnum,
//
//            @ApiParam("金额")
//            @RequestParam
//                    Double money,
//
//            @ApiParam("接收地址")
//            @RequestParam
//                    String requireAddress,
//            HttpServletResponse response) throws Exception {
//        qrCodeService.qrCodeGenerate(currencyEnum, money, requireAddress, response);
//
//        return null;
//    }


    @ApiOperation(
            value = "请求BTC的价格",
            notes = "请求BTC的价格"
    )
    @GetMapping(value = "/coin/info")
    public ApiResult getCoinInfo(
            @ApiParam("id")
            @RequestParam(required = false) String id,
            @ApiParam("slug")
            @RequestParam(required = false) String slug,
            @ApiParam("symbol")
            @RequestParam(required = false) String symbol,
            @ApiParam("address")
            @RequestParam(required = false) String address,
            @ApiParam("aux")
            @RequestParam(required = false) String aux) throws URISyntaxException {

        return ApiResult.ok(coinmarketCapService.getCoinInfo(id,slug,symbol,address,aux));
    }

    @ApiOperation(
            value = "请求BTC的价格",
            notes = "请求BTC的价格"
    )
    @GetMapping(value = "/BTC/price")
    public ApiResult getBTCPrice(@ApiParam("id")
                                 @RequestParam(required = false) String id,
                                 @ApiParam("symbol")
                                 @RequestParam(required = false) String symbol,
                                 @ApiParam("slug")
                                 @RequestParam(required = false) String slug,
                                 @ApiParam("convert")
                                 @RequestParam(required = false) String convert,
                                 @ApiParam("convertId")
                                 @RequestParam(required = false) String convertId,
                                 @ApiParam("aux")
                                 @RequestParam(required = false) String aux,
                                 @ApiParam("skipInvalid")
                                 @RequestParam(required = false) Boolean skipInvalid) throws URISyntaxException {
        return ApiResult.ok(coinmarketCapService.getLatestPrice(id, symbol, slug, convert, convertId, aux,
                skipInvalid));
    }


    @ApiOperation(
            value = "请求价格列表地址",
            notes = "请求价格列表地址"
    )
    @PostMapping(value = "/listing/latest")
    public ApiResult getLatestPriceList(@ApiParam("请求价格列表")
                                        @RequestBody ListingLatestRequest latestRequest) throws URISyntaxException {
        return ApiResult.ok(coinmarketCapService.getListingLatest(latestRequest));
    }


//    @ApiOperation(
//            value = "设置二维码的大小",
//            notes = "宽度与高度只能是整数"
//    )
//    @PostMapping(value = "/setCodeSize")
//    public ApiResult setCodeSize(
//            @ApiParam("二维码宽度")
//            @RequestParam Integer width,
//
//            @ApiParam("二维码高度")
//            @RequestParam Integer height) {
//
//        qrCodeService.setCodeSize(width, height);
//        return ApiResult.ok();
//    }

//    @ApiOperation(
//            value = "设置应用的授权码",
//            notes = "阿里云的应用授权码设置"
//    )
//    @PostMapping(value = "/setAppCode")
//    public ApiResult setAppCode(
//            @ApiParam("设置应用的授权码")
//            @RequestParam String appCope) {
//        qrCodeService.setAppCode(appCope);
//        return ApiResult.ok();
//    }

//    @ApiOperation(
//            value = "下载Apk",
//            notes = "下载Apk"
//    )
//    @GetMapping(value = "/download")
//    public void download(HttpServletResponse response) {
//        String fileName = "nuva.apk";
//        URL url = this.getClass().getClassLoader().getResource("file/nuva.apk");
//        File file = new File(url.getFile());
//        if (file.exists()) {
//            response.setContentType("application/force-download");
//            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null;
//            BufferedInputStream bis = null;
//            try {
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                OutputStream os = response.getOutputStream();
//                int i = bis.read(buffer);
//                while (i != -1) {
//                    os.write(buffer, 0, i);
//                    i = bis.read(buffer);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (fis != null) {
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//        }
//    }


}
