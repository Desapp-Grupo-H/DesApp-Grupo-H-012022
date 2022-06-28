package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.ITransactionService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;


import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<?> getActiveTransactions(){
        try{
            List<TransactionIntention> activeTransactions = transactionService.findAllActive();
            return ResponseEntity.ok(activeTransactions);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/all")
    @LogExecutionTime
    public ResponseEntity<?> getAllTransactions(){
        try{
            List<TransactionIntention> activeTransactions = transactionService.findAll();
            return ResponseEntity.ok(activeTransactions);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/transactions/{id}")
    @LogExecutionTime
    public ResponseEntity<?> findById(@PathVariable Long id) throws TransactionException {
        try {
            TransactionIntention transaction = this.transactionService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/transactions")
    @LogExecutionTime
    public ResponseEntity<?> register(@Valid @RequestBody TransactionDTO transactionDto) throws TransactionException {
        try {
            TransactionIntention transaction = transactionService.saveTransaction(transactionDto.createTransaction());
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
