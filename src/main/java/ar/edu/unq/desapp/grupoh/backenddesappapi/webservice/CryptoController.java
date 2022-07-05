package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency.ICryptoCurrencyService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoController {

    @Autowired
    private ICryptoCurrencyService cryptoCurrencyService;

    @GetMapping("/cryptocurrency")
    @LogExecutionTime
    public List<CryptoCurrency> index(){
        return cryptoCurrencyService.findAll();
    }

    @GetMapping("/cryptocurrency/{cryptoName}")
    @LogExecutionTime
    public ResponseEntity<CryptoCurrency> lastFor(@PathVariable("cryptoName") CryptoName cryptoName){
        try {
            CryptoCurrency cryptoCurrency = cryptoCurrencyService.findCryptoValueByName(cryptoName);
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrency);
        }catch(Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cryptocurrency/last")
    @LogExecutionTime
    public ResponseEntity<List<CryptoCurrency>> last(){
        try {
            List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyService.getLastCryptoCurrency();
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencies);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cryptocurrency/update")
    @LogExecutionTime
    public ResponseEntity<List<CryptoCurrency>> updateAllCryptos(){
        try {
            List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyService.updateAllCryptos();
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencies);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cryptocurrency/{crypto}/between")
    @LogExecutionTime
    public ResponseEntity<List<CryptoCurrency>> cryptoCurrencyBetween(@PathVariable String crypto, @Valid @RequestBody DateRange dateRange){
        try {
            List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyService.cryptoBetween(crypto, dateRange.getStartDate(), dateRange.getEndDate());
            return ResponseEntity.status(HttpStatus.OK).body(cryptoCurrencies);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

