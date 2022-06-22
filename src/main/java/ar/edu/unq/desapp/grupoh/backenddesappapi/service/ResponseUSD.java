package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import java.time.LocalDateTime;

public class ResponseUSD {
    private Float sale;
    private Float buy;
    private LocalDateTime date;

    public ResponseUSD(Float sale, Float buy, LocalDateTime date){
        setSale(sale);
        setBuy(buy);
        setDate(date);
    }

    public ResponseUSD(){}

    private void setDate(LocalDateTime fecha) { this.date = date;}
    public LocalDateTime getDate(){return this.date;}

    private void setBuy(Float buy) {this.buy = buy;}
    public Float getBuy(){return this.buy;}

    private void setSale(Float sale) {this.sale = sale;}
    public Float getSale(){return this.sale;}

}
