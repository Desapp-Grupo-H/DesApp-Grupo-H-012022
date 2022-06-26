package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.CryptoCurrencyRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseBinance;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseUSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoCurrencyService implements ICryptoCurrencyService {

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    @Override
    public List<CryptoCurrency> findAll() {
        return cryptoCurrencyRepository.findAll();
    }

    @Transactional
    @Override
    public CryptoCurrency getCryptoCurrency(String cryptoName) {
        Crypto crypto = Crypto.valueOf(cryptoName);
        Float price = getBinanceResponse(cryptoName).getPrice() * getUSDResponse().getVenta();
        return new CryptoCurrency(crypto, price);
    }

    @Transactional
    @Override
    public List<CryptoCurrency> getLastTenCryptoCurrency(String crypto) {
        List<CryptoCurrency> cryptos = cryptoCurrencyRepository.findAll();
        return cryptos;
    }

    @Transactional
    @Override
    public List<CryptoCurrency> getLastCryptoCurrency() {
        return (List<CryptoCurrency>) Arrays.stream(Crypto.values()).map(crypto -> cryptoCurrencyRepository.findByCrypto(crypto));
    }

    @Transactional
    @Override
    public List<CryptoCurrency> updateAllCryptos() {
        List<CryptoCurrency> cryptoactiveList = new ArrayList<>();
        ResponseBinance[] binanceCryptoDTOS = getBatchCryptoPrice(List.of(Crypto.values()));
        Arrays.stream(binanceCryptoDTOS).forEach(bcrypto -> {
            CryptoCurrency crypto = binanceToModelCrypto(bcrypto);
            cryptoactiveList.add(crypto);
            cryptoCurrencyRepository.save(crypto);
        });

        return cryptoactiveList;
    }

    @Transactional
    private ResponseBinance getBinanceResponse(String cryptoName) {
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + cryptoName;
        return restTemplate.getForObject(url, ResponseBinance.class);
    }

    @Transactional
    private ResponseUSD getUSDResponse() {
        String url = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        return restTemplate.getForObject(url, ResponseUSD.class);
    }

    @Transactional
    private CryptoCurrency binanceToModelCrypto(ResponseBinance binanceCryptoDTO) {
        CryptoCurrency cryptoactive = new CryptoCurrency(binanceCryptoDTO.getSymbol(), Float.valueOf(binanceCryptoDTO.getPrice())
        );
        return cryptoCurrencyRepository.save(cryptoactive);
    }

    @Transactional
    private ResponseBinance[] getBatchCryptoPrice(List<Crypto> cryptoList) {
        RestTemplate restTemplate = new RestTemplate();
        String cryptoSymbols = cryptoList.stream()
                .map(crypto -> '\"' + crypto.name() + '\"')
                .collect(Collectors.joining(",", "[", "]"));

        return restTemplate.getForObject("https://api1.binance.com/api/v3/ticker/price?symbols=" + cryptoSymbols, ResponseBinance[].class);
    }
}
