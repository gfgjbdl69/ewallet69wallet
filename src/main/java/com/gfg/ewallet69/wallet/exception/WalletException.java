package com.gfg.ewallet69.wallet.exception;

public class WalletException extends RuntimeException{

    private String type;
    private String message;

    public WalletException(String type,String message){
        this.type=type;
        this.message=message;
    }

    public String getType(){
        return type;
    }

    public String getMessage(){
        return message;
    }

}
