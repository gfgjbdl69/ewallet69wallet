package com.gfg.ewallet69.wallet.repository;

import com.gfg.ewallet69.wallet.domain.Wallet;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Wallet findByUserId(Long userId);


    //Wallet save(Wallet wallet,Session session);
   // Wallet findByUserId(Long userId,Session session);

}
