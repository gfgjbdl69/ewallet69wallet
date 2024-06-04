package com.gfg.ewallet69.wallet.controller;

import com.gfg.ewallet69.wallet.service.WalletService;
import com.gfg.ewallet69.wallet.service.resource.WalletTransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping("wallet/{user-id}")
    public ResponseEntity<?> getWallet(@PathVariable("user-id") Long userId){
        return new ResponseEntity<>(walletService.getWallet(userId), HttpStatus.OK);
    }

    @PostMapping("wallet/transaction")
    public ResponseEntity<Boolean> performTransaction(@RequestBody WalletTransactionRequest walletTransactionRequest){
        boolean success = walletService.performTransaction(walletTransactionRequest);
        if(success)
            return new ResponseEntity<>(success,HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(success,HttpStatus.BAD_REQUEST);
    }
}
