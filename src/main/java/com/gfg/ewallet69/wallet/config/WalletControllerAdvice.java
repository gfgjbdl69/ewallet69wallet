package com.gfg.ewallet69.wallet.config;

import com.gfg.ewallet69.wallet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WalletControllerAdvice {

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<?> handleUserException(WalletException exception){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("type",exception.getType());
        errorMap.put("message",exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
