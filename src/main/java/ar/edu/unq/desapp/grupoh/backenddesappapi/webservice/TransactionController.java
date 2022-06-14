package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET})
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/transaction/{crypto}")
    public ResponseEntity<?> getCryptoBetween(@PathVariable CryptoCurrency crypto, @RequestBody RequestDto request){
        return ResponseEntity.ok(transactionService.find(crypto, request.getDate1(), request.getDate2()));
    }

    @GetMapping("/api/transaction/")
    public ResponseEntity<?> getActiveTransactions(){
        return ResponseEntity.ok(transactionService.findAllActive());
    }

}
