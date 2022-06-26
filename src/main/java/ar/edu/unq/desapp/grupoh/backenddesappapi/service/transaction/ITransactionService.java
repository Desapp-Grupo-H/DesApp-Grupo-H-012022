package ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITransactionService {

    List<TransactionIntention> findAll();

    @Transactional
    TransactionIntention findById(Long id) throws TransactionException;

    @Transactional
    TransactionIntention saveTransaction(TransactionIntention transaction);

    @Transactional
    List<TransactionIntention> findAllActive();
}
