package com.currency.qrcode.currency.controller;


import com.currency.qrcode.currency.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@Validated
@RequestMapping(value = "/coin", produces = "application/json")
@Api(tags = "加密数字货币")
@RestController
public class CoinController {


    @ApiOperation(
            value = "请求BTC的价格",
            notes = "请求BTC的价格"
    )
    @GetMapping(value = "/BTC/price")
    public ApiResult getCoinInfo() throws URISyntaxException {

        return ApiResult.ok();
    }
}
