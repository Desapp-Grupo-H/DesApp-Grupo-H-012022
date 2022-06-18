package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.TransactionRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepositoryRepository) {
        this.transactionRepository = transactionRepositoryRepository;
    }

    @Transactional
    public List<TransactionIntention> findAll(){
        return this.transactionRepository.findAll();
    }

    @Transactional
    public TransactionIntention findById(Long id) throws TransactionException {
        return this.transactionRepository.findById(id).orElseThrow(() -> new TransactionException("La Transaccion no existe"));//Exception);
    }

    @Transactional
    public TransactionIntention saveTransaction(TransactionIntention transaction) {
        return this.transactionRepository.save(transaction);
    }
}
