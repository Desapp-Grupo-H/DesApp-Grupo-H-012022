package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.CryptoCurrencyRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CryptoCurrencyService {

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;
    private RestTemplate restTemplate = new RestTemplate();



    @Transactional
    public List<CryptoCurrency> findAll() {
        return cryptoCurrencyRepository.findAll();
    }



    @Transactional
    public CryptoCurrency getCryptoCurrency(Crypto crypto){
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + crypto.name();
        ResponseBinance responseBinance = restTemplate.getForObject(url, ResponseBinance.class);
        ResponseUSD responseUSD = getUSDToPesos();
        Float price = Float.valueOf(Objects.requireNonNull(responseBinance).getPrice()) * Float.valueOf(responseUSD.getVenta());
        return new CryptoCurrency(crypto, price);
    }
    @Transactional
    public ResponseUSD getUSDToPesos(){
        String url = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        return restTemplate.getForObject(url, ResponseUSD.class);
    }
}
