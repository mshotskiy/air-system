package com.shotskiy.airsystem.exception.handler;

import com.shotskiy.airsystem.exception.AirCompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AirCompanyNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AirCompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String airCompanyNotFoundHandler(AirCompanyNotFoundException ex) {
        return ex.getMessage();
    }

}
