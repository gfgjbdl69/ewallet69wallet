package com.gfg.ewallet69.wallet.service.impl;

import com.gfg.ewallet69.wallet.domain.Wallet;
import com.gfg.ewallet69.wallet.exception.WalletException;
import com.gfg.ewallet69.wallet.repository.WalletRepository;
import com.gfg.ewallet69.wallet.service.WalletService;
import com.gfg.ewallet69.wallet.service.resource.WalletResponse;
import com.gfg.ewallet69.wallet.service.resource.WalletTransactionRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class WalletServiceImpl implements WalletService {

    private Logger logger= LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void createWallet(Long userId) {
        try{
            Wallet userWallet=walletRepository.findByUserId(userId);
            if(Objects.nonNull(userWallet)){
                logger.info("Wallet already exists for user: {}",userId);
                return;
            }
            //REST call to user service and check if user exists.
            Wallet wallet=new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(0.0);
            walletRepository.save(wallet);

        }catch (Exception ex){
            logger.error("Exception while creating wallet: {} ",ex.getMessage());
        }
    }

    @Override
    public Wallet deleteWallet(Long userId) {
       try{
          Wallet wallet=walletRepository.findByUserId(userId);
           if(Objects.nonNull(wallet)){
               walletRepository.delete(wallet);
               return wallet;
           }else{
               logger.info("Wallet does not exist for user: {}",userId);
               return null;
           }
       }catch (Exception ex){
           logger.error("Exception while deleting wallet: {} ",ex.getMessage());
           return null;
       }
    }

    @Override
    public WalletResponse getWallet(Long userId) {
       Wallet wallet = walletRepository.findByUserId(userId);
        if (Objects.nonNull(wallet)) {
            logger.info("Wallet already exists for user: {}", userId);
            WalletResponse walletResponse = new WalletResponse(wallet);
            return walletResponse;
        } else {
            throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = WalletException.class,noRollbackFor = NullPointerException.class)
    public boolean performTransaction(WalletTransactionRequest walletTransactionRequest) {

        Wallet senderWallet = walletRepository.findByUserId(walletTransactionRequest.getSenderId());
        Wallet receiverWallet = walletRepository.findByUserId(walletTransactionRequest.getReceiverId());

        if(walletTransactionRequest.getTransactionType().equals("DEPOSIT")){
            updateWallet(receiverWallet,walletTransactionRequest.getAmount());
            return true;
        }
        else if(walletTransactionRequest.getTransactionType().equals("WITHDRAW")){
            updateWallet(receiverWallet,-1 * walletTransactionRequest.getAmount());
            return true;
        }
        else if(walletTransactionRequest.getTransactionType().equals("TRANSFER")) {
            try {
                if (Objects.isNull(senderWallet) || Objects.isNull(receiverWallet)) {
                    throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
                }
                handleTransaction(senderWallet, receiverWallet, walletTransactionRequest.getAmount());
                return true;
            } catch (WalletException exception) {

                logger.error("Exception while performing transaction: {} ", exception.getMessage());

                throw exception;
            }
        }
        else{
            throw  new WalletException("EWALLET_INVALID_TRANSACTION_TYPE","Invalid transaction type");
        }
    }

    private void updateWallet(Wallet wallet, Double amount) {
        wallet.setBalance(wallet.getBalance()+amount);
        walletRepository.save(wallet);
    }


    public void handleTransaction(Wallet senderWallet,Wallet receiverWallet,Double amount){
       try {
           Wallet senderCopy = new Wallet();
           BeanUtils.copyProperties(senderWallet, senderCopy);
           Wallet receiverCopy = new Wallet();
           BeanUtils.copyProperties(receiverWallet, receiverCopy);
           if (senderWallet.getBalance() < amount) {
               throw new WalletException("EWALLET_INSUFFICIENT_BALANCE", "Insufficient balance");
           }
           senderCopy.setBalance(senderWallet.getBalance() - amount);
           receiverCopy.setBalance(receiverWallet.getBalance() + amount);
           walletRepository.save(senderCopy);
           walletRepository.save(receiverCopy);
       }catch (WalletException ex){
           throw  ex;
       }

    }

    /***
     *
     * Transaction steps
     *
     * 1.
     * */


    /*Wallet senderCopy = new Wallet();
            BeanUtils.copyProperties(senderWallet, senderCopy);
            Wallet receiverCopy = new Wallet();
            BeanUtils.copyProperties(receiverWallet, receiverCopy);

            if (senderWallet.getBalance() < walletTransactionRequest.getAmount()) {
                throw new WalletException("EWALLET_INSUFFICIENT_BALANCE", "Insufficient balance");
            }
            senderWallet.setBalance(senderWallet.getBalance() - walletTransactionRequest.getAmount());
            receiverWallet.setBalance(receiverWallet.getBalance() + walletTransactionRequest.getAmount());
            walletRepository.save(senderWallet,session);
            if (true)
                throw new ArithmeticException("exception");
            walletRepository.save(receiverWallet,session);
            //session.flush();
            tx.commit();
            return true;*/

}
