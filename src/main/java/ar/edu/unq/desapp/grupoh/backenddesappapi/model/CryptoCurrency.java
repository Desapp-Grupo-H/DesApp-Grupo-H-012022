package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.CryptoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptoCurrency")
@NoArgsConstructor
public class CryptoCurrency {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @NotNull
    private CryptoName cryptoName;
    @Getter @Setter
    @NotNull
    private Float price;
    @Getter @Setter
    private LocalDateTime date;
    public CryptoCurrency(CryptoName cryptoName, Float price){
        this.date = LocalDateTime.now();
        this.cryptoName = cryptoName;
        this.price = price;
    }

    public boolean compareQuotation(float price){
      return (price > (this.price*(0.95))&& price < (this.price*(1.05))); /*poner el valor del rango en variables*/
    }

    public static final class CryptoCurrencyBuilder {
        private final CryptoCurrency cryptoCurrency = new CryptoCurrency();

        private CryptoCurrencyBuilder() {}

        public CryptoCurrencyBuilder withCryptoCurrency(CryptoName cryptoName){
            cryptoCurrency.setCryptoName(cryptoName);
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
