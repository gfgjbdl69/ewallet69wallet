package com.gfg.ewallet69.wallet.service.impl;

import com.gfg.ewallet69.wallet.domain.Wallet;
import com.gfg.ewallet69.wallet.exception.WalletException;
import com.gfg.ewallet69.wallet.repository.WalletRepository;
import com.gfg.ewallet69.wallet.service.WalletService;
import com.gfg.ewallet69.wallet.service.resource.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private Logger logger= LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;

    @Override
    public void createWallet(Long userId) {
        try{
            Optional<Wallet> optionalWallet=walletRepository.findByUserId(userId);
            if(optionalWallet.isPresent()){
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
           Optional<Wallet> optionalWallet=walletRepository.findByUserId(userId);
           if(optionalWallet.isPresent()){
               Wallet wallet=optionalWallet.get();
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
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);
        if (optionalWallet.isPresent()) {
            logger.info("Wallet already exists for user: {}", userId);
            WalletResponse walletResponse = new WalletResponse(optionalWallet.get());
            return walletResponse;
        } else {
            throw new WalletException("EWALLET_WALLET_NOT_FOUND", "wallet not found for the user");
        }
    }

}
