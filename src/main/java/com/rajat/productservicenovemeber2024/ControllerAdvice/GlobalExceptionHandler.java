package com.rajat.productservicenovemeber2024.ControllerAdvice;

import com.rajat.productservicenovemeber2024.Exceptions.CategoryNotFoundException;
import com.rajat.productservicenovemeber2024.Exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException pe){
        ResponseEntity<String> response = new ResponseEntity<>(pe.getMessage(), HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e){
        ResponseEntity<String> response = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        return response;
    }

}
