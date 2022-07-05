package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor
public class CryptoCurrencyDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotNull
    private CryptoName cryptoName;

    @Getter
    @Setter
    @NotNull
    private Float price;

    @Getter
    @Setter
    private LocalDateTime date;


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
            newCryptoCurrencyDTO.setCryptoName(cryptoName);
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
