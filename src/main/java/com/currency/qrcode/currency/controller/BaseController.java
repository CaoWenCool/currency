package com.currency.qrcode.currency.controller;

import com.currency.qrcode.currency.model.ApiResult;
import com.currency.qrcode.currency.service.BaseService;
import com.currency.qrcode.currency.service.OpeasenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping(value = "/base", produces = "application/json")
@Api(tags = "基础信息")
@RestController
public class BaseController {

    @Autowired
    BaseService baseService;

    @Autowired
    OpeasenService opeasenService;


    @ApiOperation(
            value = "获取活动基础信息",
            notes = "获取活动基础信息"
    )
    @GetMapping(value = "/info")
    public ApiResult getActivityTime() {
        return ApiResult.ok(baseService.getActivityInfo());
    }

    @ApiOperation(
            value = "获取活动基础信息",
            notes = "获取活动基础信息"
    )
    @GetMapping(value = "/init/image")
    public ApiResult initImage() {
        opeasenService.init();
        return ApiResult.ok();
    }


}
