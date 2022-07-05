package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.CryptoException;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoCurrencyTest {

    public LocalDateTime aTime = LocalDateTime.now();
    public CryptoName aCryptoName = CryptoName.AAVEUSDT;
    public float aPrice = 2.0f;

    public CryptoCurrency aCryptoCurrency() throws CryptoException {
        return CryptoCurrency
                .builder()
                .withCryptoCurrency(aCryptoName)
                .withDate(aTime)
                .withPrice(aPrice)
                .build();
    }

    @Test
    public void aCryptoCurrencyHasADateAPriceAndACryptoName() throws CryptoException {
        CryptoCurrency cryptoCurrency = aCryptoCurrency();
        assertEquals(aCryptoName, cryptoCurrency.getCryptoName());
        assertEquals(aTime, cryptoCurrency.getDate());
        assertEquals(aPrice, cryptoCurrency.getPrice());

    }

    @Test
    public void aCryptoCurrencyCanCompareItselfWithAFloatInRangeAnReturnsTrue() throws CryptoException {
        CryptoCurrency cryptoCurrency = aCryptoCurrency();
        float randomValueInRange = getRandomNumber(cryptoCurrency.getPrice()*(0.95f), cryptoCurrency.getPrice()*(1.05f));
        assertTrue(cryptoCurrency.compareQuotation(randomValueInRange));
    }

    @Test
    public void aCryptoCurrencyCanCompareItselfWithAFloatOutOfRangeAnReturnsFalse() throws CryptoException {
        CryptoCurrency cryptoCurrency = aCryptoCurrency();
        float randomValueOutRange = getRandomNumber(cryptoCurrency.getPrice() * (0.0f), cryptoCurrency.getPrice() * (0.94f));
        assertFalse(cryptoCurrency.compareQuotation(randomValueOutRange));
    }

    private float getRandomNumber(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }
}
