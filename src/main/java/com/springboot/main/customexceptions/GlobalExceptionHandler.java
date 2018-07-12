package com.springboot.main.customexceptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.main.dto.Error;


@ControllerAdvice
@RequestMapping(produces = "application/json")
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Error> handle(ApplicationException ex){
        logger.trace("Global Controller is busy in handling");
        Error error = new Error(ex.getResponseStatusCode().getCode(), ex.getResponseStatusCode().getDescription());
        return new ResponseEntity<Error>(error, HttpStatus.OK);
    }
}
