package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CryptoCurrencyDTO {

    private Long id;

    @NotNull
    private Crypto crypto;
    @NotNull
    private Float price;
    private LocalDateTime date;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Crypto getCrypto() {
        return crypto;
    }
    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
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

        public CryptoCurrencyDTOBuilder withCrypto(Crypto crypto) {
            newCryptoCurrencyDTO.setCrypto(crypto);
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
