package com.gfg.ewallet69.wallet.service.impl;

import com.gfg.ewallet69.wallet.domain.TransactionType;
import com.gfg.ewallet69.wallet.domain.Wallet;
import com.gfg.ewallet69.wallet.exception.WalletException;
import com.gfg.ewallet69.wallet.repository.WalletRepository;
import com.gfg.ewallet69.wallet.service.resource.WalletTransactionRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalletServiceImplTest {

    @Test
    void performTransactionTestDeposit() {
        WalletServiceImpl walletService=new WalletServiceImpl();
        WalletRepository walletRepository=mock(WalletRepository.class);
        walletService.setWalletRepository(walletRepository);

        Wallet wallet=new Wallet();
        wallet.setBalance(40.0);
        wallet.setUserId(102L);
        wallet.setId(1L);

        Wallet expectedWallet=new Wallet();
        expectedWallet.setBalance(50.0);
        expectedWallet.setUserId(102L);
        expectedWallet.setId(1L);

        WalletTransactionRequest walletTransactionRequest= new WalletTransactionRequest();
        walletTransactionRequest.setAmount(10.0);
        walletTransactionRequest.setReceiverId(102L);
        walletTransactionRequest.setSenderId(102L);
        walletTransactionRequest.setTransactionType(TransactionType.DEPOSIT.name());

        when(walletRepository.findByUserId(anyLong())).thenReturn(wallet);
        when(walletRepository.save(any(Wallet.class))).thenReturn(expectedWallet);

        walletService.performTransaction(walletTransactionRequest);

        assertNotNull(wallet);

        assertEquals(expectedWallet.getBalance(),wallet.getBalance());


    }

    @Test
    void performTransactionForInvalidWallet(){
        WalletServiceImpl walletService=new WalletServiceImpl();
        WalletRepository walletRepository=mock(WalletRepository.class);
        walletService.setWalletRepository(walletRepository);

        WalletTransactionRequest walletTransactionRequest= new WalletTransactionRequest();
        walletTransactionRequest.setAmount(10.0);
        walletTransactionRequest.setReceiverId(102L);
        walletTransactionRequest.setSenderId(102L);
        walletTransactionRequest.setTransactionType(TransactionType.DEPOSIT.name());

        when(walletRepository.findByUserId(anyLong())).thenReturn(null);

        assertThrows(WalletException.class,() -> walletService.performTransaction(walletTransactionRequest));

    }
}