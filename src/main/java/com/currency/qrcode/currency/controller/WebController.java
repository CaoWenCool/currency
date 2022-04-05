package com.currency.qrcode.currency.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Api(tags = "访问界面")
@RequestMapping("/web")
public class WebController {

    @RequestMapping("/index")
    @ApiOperation(
            value = "首页",
            notes = "首页"
    )
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
