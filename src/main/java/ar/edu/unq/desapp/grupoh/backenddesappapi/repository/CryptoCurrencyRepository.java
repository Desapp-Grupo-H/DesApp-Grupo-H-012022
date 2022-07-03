package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
}
