package com.balance.exception;

import com.balance.response.ErrorDTO;
import com.balance.response.ErrorFieldsStatusDTO;
import com.balance.response.ErrorStatusDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(HandleHttpErrors.class)
    public ResponseEntity<ErrorDTO> handleHttpErrorException(HandleHttpErrors exception){
        if (exception.getBindingResult() != null){
            ErrorFieldsStatusDTO errorResponse = new ErrorFieldsStatusDTO();
            Map<String, String> errors = new HashMap<>();
            BindingResult bindingResult = exception.getBindingResult();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            errorResponse.setErrors(errors);

            errorResponse.setMessage(exception.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setResult("error");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        } else {
            ErrorStatusDTO errorResponse = new ErrorStatusDTO();
            errorResponse.setMessage(exception.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setResult("error");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorStatusDTO> handleEntityNotFoundException(EntityNotFoundException exception){
        ErrorStatusDTO errorResponse = new ErrorStatusDTO();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ResponseStatusDTO> handleGeneralException(Exception exception){
//        ResponseStatusDTO errorResponse = new ResponseStatusDTO();
//        errorResponse.setMessage("Internal Server Error");
//        errorResponse.setResult("error");
//        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
