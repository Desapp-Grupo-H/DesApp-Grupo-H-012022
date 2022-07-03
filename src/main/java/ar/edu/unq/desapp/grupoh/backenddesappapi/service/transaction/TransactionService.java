package ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TransactionStatus.ACTIVE;

@Service
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepositoryRepository) {
        this.transactionRepository = transactionRepositoryRepository;
    }

    @Transactional
    @Override
    public List<TransactionIntention> findAll(){
        List<TransactionIntention> transactions = this.transactionRepository.findAll();
        return transactions;
    }

    @Transactional
    @Override
    public TransactionIntention findById(Long id) throws TransactionException {
        TransactionIntention transaction = this.transactionRepository.findById(id).orElseThrow(() -> new TransactionException("The transaction does not exist"));//Exception);
        return transaction;
    }

    @Transactional
    @Override
    public TransactionIntention saveTransaction(TransactionIntention transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public List<TransactionIntention> findAllActive(){
        List<TransactionIntention> transactions = transactionRepository.findAll();
        transactions = transactions.stream().filter(transaction -> transaction.getStatus() == ACTIVE).collect(Collectors.toList());
        return transactions;
    }
}
