package com.gfg.ewallet69.wallet.service;

import com.gfg.ewallet69.wallet.domain.Wallet;
import com.gfg.ewallet69.wallet.service.resource.WalletResponse;
import com.gfg.ewallet69.wallet.service.resource.WalletTransactionRequest;

public interface WalletService {

    public void createWallet(Long userId);

    public Wallet deleteWallet(Long userId);

    public WalletResponse getWallet(Long userId);

    public boolean performTransaction(WalletTransactionRequest walletTransactionRequest);
}
