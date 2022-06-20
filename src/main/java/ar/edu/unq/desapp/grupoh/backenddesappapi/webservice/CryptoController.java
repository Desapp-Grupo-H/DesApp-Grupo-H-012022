package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.Crypto;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoController {

    private final CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public CryptoController(CryptoCurrencyService cryptoCurrencyService){
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @GetMapping("/cryptocurrency")
    public List<CryptoCurrency> index(){
        return cryptoCurrencyService.findAll();
    }

    @GetMapping("/cryptocurrency/{crypto}")
    public CryptoCurrency last(@PathVariable String crypto){
        return cryptoCurrencyService.getCryptoCurrency(Crypto.valueOf(crypto));
    }



}
