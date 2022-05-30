package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.TransactionRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepositoryRepository) {
        this.transactionRepository = transactionRepositoryRepository;}

    public List<TransactionIntention> findAll(){
        return this.transactionRepository.findAll();
    }
    public TransactionIntention findById(Long id) throws TransactionException {
        return this.transactionRepository.findById(id).orElseThrow(() -> new TransactionException("La Transaccion no existe"));//Exception);
    }

    public TransactionIntention saveTransaction(TransactionIntention transaction) {
        return this.transactionRepository.save(transaction);
    }
}
