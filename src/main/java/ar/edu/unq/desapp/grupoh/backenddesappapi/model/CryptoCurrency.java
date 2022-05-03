package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import java.time.LocalDateTime;

public class CryptoCurrency {

    private Crypto crypto;
    private Float price;
    private LocalDateTime date;

    public Crypto getName() {
        return crypto;
    }

    public void setName(Crypto name) {
        this.crypto = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean compareCotization(float price){
      return ((price > (this.price*(0.95))&& price < (this.price*(1.05)))); //TODO poner el valor del rango en variables
    }


}
