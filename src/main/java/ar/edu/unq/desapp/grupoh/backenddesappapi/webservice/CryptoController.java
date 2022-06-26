package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency.CryptoCurrencyService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Transactional
    @GetMapping("/cryptocurrency")
    @LogExecutionTime
    public List<CryptoCurrency> index(){
        return cryptoCurrencyService.findAll();
    }

    @Transactional
    @GetMapping("/cryptocurrency/{crypto}")
    @LogExecutionTime
    public CryptoCurrency lastFor(@PathVariable String crypto){
        return cryptoCurrencyService.getCryptoCurrency(crypto);
    }

    @Transactional
    @GetMapping("/cryptocurrency/last")
    @LogExecutionTime
    public List<CryptoCurrency> last(){
        return cryptoCurrencyService.getLastCryptoCurrency();
    }

    @Transactional
    @PostMapping("/cryptocurrency/update")
    @LogExecutionTime
    public List<CryptoCurrency> updateAllCryptos(){
        return cryptoCurrencyService.updateAllCryptos();
    }

}
