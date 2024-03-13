package com.example.backend.exception;

import com.example.backend.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handleDuplicateKeyException(DuplicateKeyException duplicateKeyException) {
        return new ResponseWrapper(duplicateKeyException.getResultInfo(), duplicateKeyException.getKey());
    }

    @ExceptionHandler(InCorrectPinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handleInCorrectPinException(InCorrectPinException inCorrectPinException) {
        return new ResponseWrapper(inCorrectPinException.getResultInfo(), null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handleValidationException(ValidationException validationException) {
        return new ResponseWrapper(validationException.getResultInfo(), null);
    }

    @ExceptionHandler(KeyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseWrapper handleKeyNotFoundException(KeyNotFoundException keyNotFoundException) {
        return new ResponseWrapper(keyNotFoundException.getResultInfo(), null);
    }
}
