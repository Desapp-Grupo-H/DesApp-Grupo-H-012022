package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.OperationStatus.DONE;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionIntention> find(CryptoCurrency crypto, LocalDateTime date1, LocalDateTime date2){
        return transactionRepository.findAllByStatusByIntentionCryptoByDateCompletedBetween(crypto, DONE, date1, date2);
    }

    public Object findAllActive() {
        return transactionRepository.findAllByStatus(ACTIVE);
    }
}
