package com.gfg.ewallet69.wallet.service.resource;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletTransactionRequest {

    private Long senderId;
    private Long receiverId;
    private Double amount;
    private String description;
    private String transactionType;

}
