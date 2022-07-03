package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.CryptoException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.CryptoCurrencyRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseBinance;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseUSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
//import redis.clients.jedis.Jedis;

@Service
@EnableScheduling
public class CryptoCurrencyService implements ICryptoCurrencyService {

    //private final Jedis jedis = new Jedis("localhost", 6379);
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
    public CryptoCurrency getCryptoCurrency(CryptoName cryptoName) {
        Float binancePrice = this.getBinanceResponse(cryptoName).getPrice();
        Float usdPrice = this.getUSDResponse().getVenta();
        Float price = binancePrice * usdPrice;
        return new CryptoCurrency(cryptoName, price);
    }

    @Transactional(readOnly = true)
    @Override
    public CryptoCurrency findCryptoValueByName(CryptoName cryptoName) {
        return cryptoCurrencyRepository.findAll()
                .stream()
                .filter(cryptoCurrencyCandidate -> cryptoCurrencyCandidate.getCrypto() == cryptoName)
                .collect(Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(CryptoCurrency::getDate)), Optional::get));
    }

    @Transactional
    @Override
    public List<CryptoCurrency> getLastTenCryptoCurrency(String crypto) {
        return cryptoCurrencyRepository.findAll();
    }

    @Transactional
    @Override
    public List<CryptoCurrency> getLastCryptoCurrency() { //Return a list with the last of each CryptoCurrency stored
        List<CryptoName> cryptoNames = Arrays.asList(CryptoName.values());
        return cryptoNames
                .stream()
                .map (crypto -> Collections.max(findByCrypto(crypto), Comparator.comparing(CryptoCurrency::getDate)))
                .collect(Collectors.toList()) ;
    }

    @Transactional
    @Override
    @Scheduled(cron = "0 0/10 * * * *")
    public List<CryptoCurrency> updateAllCryptos() {
        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
        ResponseBinance[] binanceCryptoDTOS = getBatchCryptoPrice(List.of(CryptoName.values()));
        Arrays.stream(binanceCryptoDTOS).forEach(binanceCrypto -> {
            try {
                CryptoCurrency crypto = binanceToModelCrypto(binanceCrypto);
                cryptoCurrencyList.add(crypto);
                //jedis.set(crypto.getCrypto().name(), String.valueOf(crypto.getPrice()));
                cryptoCurrencyRepository.save(crypto);
            } catch (CryptoException e) {
            }
        });
        return cryptoCurrencyList;
    }

    @Transactional
    @Override
    public List<CryptoCurrency> cryptoBetween(String cryptoName, LocalDateTime startDate, LocalDateTime endDate) {
        CryptoName crypto = CryptoName.valueOf(cryptoName);
        List<CryptoCurrency> cryptos = findByCrypto(crypto)
                .stream()
                .filter(cryptoCurrency -> cryptoCurrency.getDate().isBefore(ChronoLocalDateTime.from(endDate)) && cryptoCurrency.getDate().isAfter(ChronoLocalDateTime.from(startDate)))
                .collect(Collectors.toList());
        return cryptos;
    }

    private List<CryptoCurrency> findByCrypto(CryptoName cryptoName){
        return cryptoCurrencyRepository.findAll().stream().filter(cryptoCurrency -> cryptoCurrency.getCrypto() == cryptoName).collect(Collectors.toList());
    }

    private ResponseBinance getBinanceResponse(CryptoName cryptoName) {
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + cryptoName.name();
        return restTemplate.getForObject(url, ResponseBinance.class);
    }

    private ResponseUSD getUSDResponse() {
        String url = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        return restTemplate.getForObject(url, ResponseUSD.class);
    }

    private CryptoCurrency binanceToModelCrypto(ResponseBinance binanceCryptoDTO) throws CryptoException {
        CryptoCurrency cryptoCurrency = CryptoCurrency.builder()
                .withCryptoCurrency(binanceCryptoDTO.getSymbol())
                .withPrice(binanceCryptoDTO.getPrice())
                .withDate(LocalDateTime.now())
                .build();
        return cryptoCurrency;
    }

    private ResponseBinance[] getBatchCryptoPrice(List<CryptoName> cryptoNameList) {
        RestTemplate restTemplate = new RestTemplate();
        String cryptoSymbols = cryptoNameList.stream()
                .map(crypto -> '\"' + crypto.name() + '\"')
                .collect(Collectors.joining(",", "[", "]"));

        return restTemplate.getForObject("https://api1.binance.com/api/v3/ticker/price?symbols=" + cryptoSymbols, ResponseBinance[].class);
    }
}
