package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;

import java.time.LocalDateTime;

public class RequestDto {

    private CryptoCurrency cryptoCurrency;

    private LocalDateTime date1;

    private LocalDateTime date2;

    public CryptoCurrency getCryptoCurrency(){return cryptoCurrency;}
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency){this.cryptoCurrency = cryptoCurrency;}
    public LocalDateTime getDate1(){return date1;}
    public void setDate1(LocalDateTime date1){this.date1 = date1;}
    public LocalDateTime getDate2(){return date2;}
    public void setDate2(LocalDateTime date2){this.date1 = date2;}

}
