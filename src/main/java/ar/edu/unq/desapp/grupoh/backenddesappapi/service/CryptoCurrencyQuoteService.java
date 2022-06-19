package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class CryptoCurrencyQuoteService {


    private RestTemplate restTemplate = new RestTemplate();

    public CryptoCurrency getCryptoCurrency(Crypto crypto){
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + crypto.name();
        ResponseBinance responseBinance = restTemplate.getForObject(url, ResponseBinance.class);
        ResponseUSD responseUSD =getUSDToPesos();
        Float price = Float.valueOf(Objects.requireNonNull(responseBinance).getPrice()) * Float.valueOf(responseUSD.getSale());
        return new CryptoCurrency(crypto, price);
    }
    public ResponseUSD getUSDToPesos(){
        String url = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        return restTemplate.getForObject(url, ResponseUSD.class);
    }
}
