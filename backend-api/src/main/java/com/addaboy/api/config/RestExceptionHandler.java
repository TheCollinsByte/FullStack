package com.addaboy.api.config;

import com.addaboy.api.dto.ErrorDto;
import com.addaboy.api.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    /**
     *  This method will surround every controller but will only execute if and app exception is thrown
     */
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(ErrorDto.builder().message(exception.getMessage()).build());
    }

}
