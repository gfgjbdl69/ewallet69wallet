package com.gfg.ewallet69.wallet.repository;

import com.gfg.ewallet69.wallet.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByUserId(Long userId);
}
