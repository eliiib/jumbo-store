package com.jumbo.nearestdistance.controller;

import com.jumbo.nearestdistance.controller.dto.ErrorContent;
import com.jumbo.nearestdistance.exception.BadRequestException;
import com.jumbo.nearestdistance.exception.LatAndLongNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorContent handle(BadRequestException e) {
        return ErrorContent.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(LatAndLongNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorContent handle(LatAndLongNotValidException e) {
        log.error("Latitude or Longitude value is out of valid boundary", e);
        return ErrorContent.builder()
                .message(e.getMessage())
                .build();
    }
}
