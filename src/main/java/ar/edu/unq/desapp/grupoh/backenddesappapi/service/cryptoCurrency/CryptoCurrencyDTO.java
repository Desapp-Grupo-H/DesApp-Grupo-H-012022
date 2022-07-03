package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CryptoCurrencyDTO {

    private Long id;

    @NotNull
    private CryptoName cryptoName;
    @NotNull
    private Float price;
    private LocalDateTime date;


    public long getId() {
        return id;
    }
    public void setId(long id) {
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

    public static CryptoCurrencyDTO builder() {
        return new CryptoCurrencyDTO();
    }

    public static final class CryptoCurrencyDTOBuilder {
        private CryptoCurrencyDTO newCryptoCurrencyDTO;

        private CryptoCurrencyDTOBuilder() {
            newCryptoCurrencyDTO = new CryptoCurrencyDTO();
        }

        public CryptoCurrencyDTOBuilder withId(long id) {
            newCryptoCurrencyDTO.setId(id);
            return this;
        }

        public CryptoCurrencyDTOBuilder withCrypto(CryptoName cryptoName) {
            newCryptoCurrencyDTO.setCrypto(cryptoName);
            return this;
        }

        public CryptoCurrencyDTOBuilder withPrice(Float price) {
            newCryptoCurrencyDTO.setPrice(price);
            return this;
        }

        public CryptoCurrencyDTOBuilder withDate(LocalDateTime date) {
            newCryptoCurrencyDTO.setDate(date);
            return this;
        }

        public CryptoCurrencyDTO build() {
            return newCryptoCurrencyDTO;
        }
    }
}
