package com.gfg.ewallet69.wallet.service;

import com.gfg.ewallet69.wallet.domain.Wallet;
import com.gfg.ewallet69.wallet.service.resource.WalletResponse;

public interface WalletService {

    public void createWallet(Long userId);

    public Wallet deleteWallet(Long userId);

    public WalletResponse getWallet(Long userId);
}
