package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import javax.persistence.*;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OneToOne
    private TransactionIntention intention; //Es la intencion que fue seleccionada por un usuario

    @ManyToOne
    private CryptoCurrency cryptoactive;
    @Column(nullable = false)
    private User userInitOperation;//El usuario que eligio la intencio puede ser como Comprador o Vendedor

    @Column(nullable = false)
    private LocalDateTime dateStarted;

    @Column(nullable = false)
    private LocalDateTime dateCompleted; //Formato de LocalDateTime "2022-04-19T22:39:10"

    @Column(nullable = false)
    private OperationStatus status;

    public Operation(TransactionIntention intention, User userInitOperation) throws OperationException{
        this.intention = intention;
        this.cryptoactive = this.intention.getCrypto();
        this.userInitOperation = userInitOperation;
        this.dateStarted = LocalDateTime.now();
        this.status = ONGOING;

    }

    public Operation() {
    }

    public void completeOperation(){
        this.setDateCompleted(LocalDateTime.now());
        if (isInPriceRange()){
            int diff = dateCompleted.getMinute() - dateStarted.getMinute();
            int points = (diff == 30) ? 10 : 5 ;
            intention.getUser().completedTransaction(points);
            userInitOperation.completedTransaction(points);
            this.completeOperationSystem();
        }else{
            this.cancelOperationSystem();
        }
    }

    public void cancelOperation(){ /*The user is the one that cancelled the transaction or nothing in case of a system cancellation*/
        userInitOperation.cancelledTransaction();
        intention.getUser().cancelledTransaction();
        this.cancelOperationSystem();
    }

    private void cancelOperationSystem(){
        this.status = CANCELED;
    }
    private void completeOperationSystem(){
        this.status = DONE;
    }

    public TransactionIntention getIntention() {
        return intention;
    }
    public void setIntention(TransactionIntention intention) {
        this.intention = intention;
    }

    public User getUserInitOperation() {
        return userInitOperation;
    }
    public void setUserInitOperation(User userInitOperation) {
        this.userInitOperation = userInitOperation;
    }


    public LocalDateTime getDateStarted() {
        return dateStarted;
    }
    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }
    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public OperationStatus getStatus() {
        return status;
    }
    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public CryptoCurrency getCrypto(){
        return this.intention.getCrypto();
    }

    public User getUserTransactionOperation() {return this.intention.getUser();}
    private boolean isInPriceRange(){
        intention.getCrypto().compareQuotation(intention.getPrice());
        return true;
    }

    public static final class OperationBuilder {
        private final Operation operation = new Operation();

        private OperationBuilder() {}

        public OperationBuilder withIntention(TransactionIntention intention){
            operation.setIntention(intention);
            return this;
        }
        public OperationBuilder withUserInitOperation(User user){
            operation.setUserInitOperation(user);
            return this;
        }

        public Operation build() throws OperationException {
            return new Operation(operation.intention, operation.userInitOperation);
        }
    }

    public static OperationBuilder builder(){return new OperationBuilder();}
}
