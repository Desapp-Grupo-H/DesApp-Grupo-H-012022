package ar.edu.unq.desapp.grupoh.backenddesappapi.service.response;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;

public class ResponseBinance {
    private CryptoName symbol;
    private Float price;

    public ResponseBinance(String symbol, Float price){
        this.setSymbol(CryptoName.valueOf(symbol));
        this.setPrice(price);
    }

    public ResponseBinance(){}

    public CryptoName getSymbol() {
        return symbol;
    }
    public void setSymbol(CryptoName symbol) {
        this.symbol = symbol;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
}
