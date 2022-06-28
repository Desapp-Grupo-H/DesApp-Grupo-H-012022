package ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.CryptoCurrencyRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseBinance;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.response.ResponseUSD;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTimeAspectAnnotation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

@Service
@EnableScheduling
public class CryptoCurrencyService implements ICryptoCurrencyService {

    private final Jedis jedis = new Jedis("localhost", 6379);
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

    @Transactional(readOnly = true)
    @Override
    public CryptoCurrency findCryptoValueByName(String name) {
        CryptoCurrency cryptoCurrency = new CryptoCurrency(Crypto.valueOf(name), Float.parseFloat(jedis.get(name)));
        return cryptoCurrency;
    }


    @Transactional
    @Override
    public List<CryptoCurrency> getLastTenCryptoCurrency(String crypto) {
        List<CryptoCurrency> cryptos = cryptoCurrencyRepository.findAll();
        return cryptos;
    }

    @Transactional
    @Override
    public List<CryptoCurrency> getLastCryptoCurrency() { //Return a list with the last of each CryptoCurrency stored
        List<Crypto> cryptos = Arrays.asList(Crypto.values());
        return cryptos
                .stream()
                .map (crypto -> Collections.max(findByCrypto(crypto), Comparator.comparing(c -> c.getDate())))
                .collect(Collectors.toList()) ;
    }

    @Transactional
    @Override
    @Scheduled(cron = "0 0/10 * * * *")
    public List<CryptoCurrency> updateAllCryptos() {
        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();
        ResponseBinance[] binanceCryptoDTOS = getBatchCryptoPrice(List.of(Crypto.values()));
        Arrays.stream(binanceCryptoDTOS).forEach(binanceCrypto -> {
            try {
                CryptoCurrency crypto = binanceToModelCrypto(binanceCrypto);
                cryptoCurrencyList.add(crypto);
                jedis.set(crypto.getCrypto().name(), String.valueOf(crypto.getPrice()));
                cryptoCurrencyRepository.save(crypto);
            } catch (UserException e) {
            }
        });
        return cryptoCurrencyList;
    }

    @Transactional
    @Override
    public List<CryptoCurrency> cryptoBetween(String cryptoName, LocalDateTime startDate, LocalDateTime endDate) {
        Crypto crypto = Crypto.valueOf(cryptoName);
        List<CryptoCurrency> cryptos = findByCrypto(crypto)
                .stream()
                .filter(cryptoCurrency -> cryptoCurrency.getDate().isBefore(ChronoLocalDateTime.from(endDate)) && cryptoCurrency.getDate().isAfter(ChronoLocalDateTime.from(startDate)))
                .collect(Collectors.toList());
        return cryptos;
    }

    @Transactional
    private List<CryptoCurrency> findByCrypto(Crypto crypto){
        return cryptoCurrencyRepository.findAll().stream().filter(cryptoCurrency -> cryptoCurrency.getCrypto() == crypto).collect(Collectors.toList());
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
    private CryptoCurrency binanceToModelCrypto(ResponseBinance binanceCryptoDTO) throws UserException {
        CryptoCurrency cryptoCurrency = CryptoCurrency.builder()
                .withCryptoCurrency(binanceCryptoDTO.getSymbol())
                .withPrice(binanceCryptoDTO.getPrice())
                .withDate(LocalDateTime.now())
                .build();
        return cryptoCurrency;
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
