package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;

public class ResponseBinance {
    private Crypto crypto;
    private Float price;

    public ResponseBinance(String crypto, Float price){
        this.setCrypto(Crypto.valueOf(crypto));
        this.setPrice(price);
    }

    public ResponseBinance(){}

    public Crypto getCrypto() {
        return crypto;
    }
    public void setCrypto(Crypto name) {
        this.crypto = name;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
}
