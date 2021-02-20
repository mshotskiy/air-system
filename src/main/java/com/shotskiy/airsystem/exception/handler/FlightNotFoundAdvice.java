package com.shotskiy.airsystem.exception.handler;

import com.shotskiy.airsystem.exception.FlightNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FlightNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String airplaneNotFoundHandler(FlightNotFoundException ex) {
        return ex.getMessage();
    }
}
