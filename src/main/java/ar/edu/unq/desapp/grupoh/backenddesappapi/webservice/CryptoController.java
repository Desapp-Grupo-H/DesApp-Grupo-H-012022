package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoController {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;


    @GetMapping("/cryptocurrency")
    public List<CryptoCurrency> index(){
        return cryptoCurrencyService.findAll();
    }

    @GetMapping("/cryptocurrency/{crypto}")
    public CryptoCurrency lastFor(@PathVariable String crypto){
        return cryptoCurrencyService.getCryptoCurrency(crypto);
    }

    @GetMapping("/cryptocurrency/last")
    public List<CryptoCurrency> last(){
        return cryptoCurrencyService.getLastCryptoCurrency();
    }

    @PostMapping("/cryptocurrency/update")
    public List<CryptoCurrency> updateAllCryptos(){
        return cryptoCurrencyService.updateAllCryptos();
    }

}
