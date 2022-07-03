package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.CryptoException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptoCurrency")
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private CryptoName cryptoName;
    @NotNull
    private Float price;
    private LocalDateTime date;

    public CryptoCurrency(){};
    public CryptoCurrency(CryptoName cryptoName, Float price){
        this.setDate(LocalDateTime.now());
        this.setCrypto(cryptoName);
        this.setPrice(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CryptoName getCrypto() {
        return cryptoName;
    }
    public void setCrypto(CryptoName cryptoName) {
        this.cryptoName = cryptoName;
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

    public boolean compareQuotation(float price){
      return (price > (this.price*(0.95))&& price < (this.price*(1.05))); /*poner el valor del rango en variables*/
    }

    public static final class CryptoCurrencyBuilder {
        private final CryptoCurrency cryptoCurrency = new CryptoCurrency();

        private CryptoCurrencyBuilder() {}

        public CryptoCurrencyBuilder withCryptoCurrency(CryptoName cryptoName){
            cryptoCurrency.setCrypto(cryptoName);
            return this;
        }

        public CryptoCurrencyBuilder withPrice(float price) {
            cryptoCurrency.setPrice(price);
            return this;
        }

        public CryptoCurrencyBuilder withDate(LocalDateTime date) {
            cryptoCurrency.setDate(date);
            return this;
        }

        public CryptoCurrency build() throws CryptoException {
            return cryptoCurrency;
        }
    }

    public static CryptoCurrencyBuilder builder(){
        return new CryptoCurrencyBuilder();
    }
}
