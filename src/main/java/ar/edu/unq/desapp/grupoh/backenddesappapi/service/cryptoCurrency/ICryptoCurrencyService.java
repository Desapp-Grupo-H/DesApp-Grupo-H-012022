package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICryptoCurrencyService {
    @Transactional
    List<CryptoCurrency> findAll();

    @Transactional
    CryptoCurrency getCryptoCurrency(String cryptoName);

    @Transactional
    List<CryptoCurrency> getLastTenCryptoCurrency(String crypto);

    @Transactional
    List<CryptoCurrency> getLastCryptoCurrency();

    @Transactional
    List<CryptoCurrency> updateAllCryptos();
}
