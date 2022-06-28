package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ICryptoCurrencyService {
    @Transactional
    List<CryptoCurrency> findAll();

    @Transactional
    CryptoCurrency getCryptoCurrency(String cryptoName);

    @Transactional(readOnly = true)
    CryptoCurrency findCryptoValueByName(String name);

    @Transactional
    List<CryptoCurrency> getLastTenCryptoCurrency(String crypto);

    @Transactional
    List<CryptoCurrency> getLastCryptoCurrency();

    @Transactional
    List<CryptoCurrency> updateAllCryptos();

    @Transactional
    List<CryptoCurrency> cryptoBetween(String crypto, LocalDateTime starDate, LocalDateTime endDate);
}
