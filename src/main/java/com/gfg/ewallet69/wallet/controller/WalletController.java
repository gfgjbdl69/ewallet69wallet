package com.gfg.ewallet69.wallet.controller;

import com.gfg.ewallet69.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("wallet/{user-id}")
    public ResponseEntity<?> getWallet(@PathVariable("user-id") Long userId){
        return new ResponseEntity<>(walletService.getWallet(userId), HttpStatus.OK);
    }
}
