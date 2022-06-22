package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;

public class ResponseBinance {
    private Crypto symbol;
    private Float price;

    public ResponseBinance(String symbol, Float price){
        this.setSymbol(Crypto.valueOf(symbol));
        this.setPrice(price);
    }

    public ResponseBinance(){}

    public Crypto getSymbol() {
        return symbol;
    }
    public void setSymbol(Crypto symbol) {
        this.symbol = symbol;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
}
