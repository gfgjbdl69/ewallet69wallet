package com.gfg.ewallet69.wallet.consumer;

import com.gfg.ewallet69.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserActionConsumer {
   /* @Value("${kafka.topic.user-created}")
    private String USER_CREATED_TOPIC;

    @Value("${kafka.topic.user-deleted}")
    private String USER_DELETED_TOPIC;
*/
    Logger logger= LoggerFactory.getLogger(UserActionConsumer.class);

    @Autowired
    WalletService walletService;

    @KafkaListener(topics="${kafka.topic.user-created}", groupId = "walletGrp")
    public void consumeUserCreated(String message){

        logger.info(String.format("Message received -> %s",message));
        walletService.createWallet(Long.valueOf(message));
    }

    @KafkaListener(topics="${kafka.topic.user-deleted}", groupId = "walletGrp")
    public void consumeUserDeleted(String message){

        logger.info(String.format("Message received -> %s",message));
        walletService.deleteWallet(Long.valueOf(message));
    }
}
