package com.currency.qrcode.currency.controller;

import com.currency.qrcode.currency.model.ApiResult;
import com.currency.qrcode.currency.service.CoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping(value = "/coin", produces = "application/json")
@Api(tags = "货币的价格")
@RestController
public class CoinController {

    @Autowired
    CoinService coinService;

    @ApiOperation(
            value = "请求BTC的价格",
            notes = "请求BTC的价格"
    )
    @GetMapping(value = "/BTC/price")
    public ApiResult getCoinInfo() {
        return ApiResult.ok(coinService.getBtcPriceInfo());
    }

    @ApiOperation(
            value = "根据地址获取ETH的数量",
            notes = "根据地址获取ETH的数量"
    )
    @GetMapping(value = "/eth/number")
    public ApiResult getEthNumber() {
        return ApiResult.ok(coinService.getEthNumber());
    }
}
