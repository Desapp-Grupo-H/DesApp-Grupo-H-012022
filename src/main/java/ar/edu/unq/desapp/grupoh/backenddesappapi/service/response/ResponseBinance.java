package ar.edu.unq.desapp.grupoh.backenddesappapi.service.response;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ResponseBinance {
    @Getter @Setter
    private CryptoName symbol;
    @Getter @Setter
    private Float price;

    public ResponseBinance(String symbol, Float price){
        this.setSymbol(CryptoName.valueOf(symbol));
        this.setPrice(price);
    }
}
