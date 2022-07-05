package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.ITransactionService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;


import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.POST})
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/transactions")
    @LogExecutionTime
    public ResponseEntity<List<TransactionIntention>> getActiveTransactions(){
        try{
            List<TransactionIntention> activeTransactions = this.transactionService.findAllActive();
            return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/all")
    @LogExecutionTime
    public ResponseEntity<List<TransactionIntention>> getAllTransactions(){
        try{
            List<TransactionIntention> activeTransactions = this.transactionService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(activeTransactions);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/{id}")
    @LogExecutionTime
    public ResponseEntity<TransactionIntention> findById(@PathVariable Long id){
        try {
            TransactionIntention transaction = this.transactionService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch (TransactionException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/transactions")
    @LogExecutionTime //TODO Change user in dto to userId
    public ResponseEntity<?> register(@Valid @RequestBody TransactionDTO transactionDto){
        try {
            TransactionIntention transaction = transactionService.saveTransaction(transactionDto.createTransaction());
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }
}
