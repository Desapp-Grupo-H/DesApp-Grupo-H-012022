package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.OperationStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntentionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository  extends JpaRepository<TransactionIntention, Long> {

      List<TransactionIntention> findAllByStatusByIntentionCryptoByDateCompletedBetween(CryptoCurrency crypto, OperationStatus status, LocalDateTime date1, LocalDateTime date2);

      Object findAllByStatus(TransactionIntentionStatus active);
}
