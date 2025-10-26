package com.example.tickr.tickr.exception;

import com.example.tickr.tickr.TickrResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ZoneId IST = ZoneId.of("Asia/Kolkata");

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<TickrResponse> handleApiRequestException(ApiRequestException e)
    {
        //1: Creating the payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
            e.getMessage(),
            badRequest.value(),
            badRequest.getReasonPhrase(),
            ZonedDateTime.now(IST)
        );

        //2: Return response entity
        return new ResponseEntity<>(new TickrResponse("Bad Request", apiException),
            badRequest);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TickrResponse> handleExceptionArgument(IllegalArgumentException ex, WebRequest webRequest)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
            ex.getMessage(),
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            ZonedDateTime.now(IST)
        );

        return new ResponseEntity<>(new TickrResponse("Bad Request", apiException),
            httpStatus);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<TickrResponse> handleExceptionState(IllegalStateException ex, WebRequest webRequest)
    {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
            ex.getMessage(),
            httpStatus.value(),
            httpStatus.getReasonPhrase(),
            ZonedDateTime.now(IST)
        );

        return new ResponseEntity<>(new TickrResponse("Bad Request", apiException),
            httpStatus);
    }
}