package com.currency.qrcode.currency.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceExceptionResponse> handle(HttpServletRequest request,HttpServletResponse response,Exception ex){
        ServiceExceptionResponse serviceExceptionResponse = new ServiceExceptionResponse();
        serviceExceptionResponse.setCode("400");
        serviceExceptionResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(serviceExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
