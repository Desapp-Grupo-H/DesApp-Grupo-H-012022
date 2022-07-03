package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;

import java.time.LocalDateTime;
import java.util.Objects;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import javax.persistence.*;

@Entity
@Table(name = "operation")
public class    Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionIntention_id")
    private TransactionIntention intention; //The intention selected by the user

    @Column(nullable = false)
    private CryptoName cryptoName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userInitOperation;//The user that initiates the operation

    @Column(nullable = false)
    private LocalDateTime dateStarted;

    private LocalDateTime dateCompleted; //LocalDateTime Format "2022-04-19T22:39:10"

    @Column(nullable = false)
    private OperationStatus status;

    @Column(nullable = false)
    private double amount;

    public Operation(TransactionIntention intention, User userInitOperation, double amount) {
        this.intention = intention;
        this.cryptoName = this.intention.getCrypto();
        this.userInitOperation = userInitOperation;
        this.amount = amount;
        this.dateStarted = LocalDateTime.now();
        this.status = ONGOING;
    }

    public Operation() {
    }

    public Operation completeOperation(User user, CryptoCurrency cryptoCurrency) throws OperationException{
        if (isWaitingFor(user.getId())){
            this.setDateCompleted(LocalDateTime.now());
            if (isInPriceRange(cryptoCurrency)){
                int diff = dateCompleted.getMinute() - dateStarted.getMinute();
                int points = (diff == 30) ? 10 : 5 ;
                intention.reduceAvailableAmount(this.amount);
                this.completeOperationSystem();
                this.getUserInitOperation().completedTransaction(points);
                this.getUserInitTransaction().completedTransaction(points);
            }else{
                this.cancelOperationSystem();
                throw new OperationException("Cannot complete Operation, price out of range");
            }
            return this;
        }else {
            throw new OperationException("Cannot confirm reception");
        }

    }

    private User getUserInitTransaction() {
        return this.getIntention().getUser();
    }

    public Operation cancelOperation(User user) throws OperationException { /*The user is the one that cancelled the transaction or nothing in case of a system cancellation*/
        if (this.canCancel(user)){
            user.cancelledTransaction();
            this.cancelOperationSystem();
        }else{throw new OperationException("Cannot cancel operation");}
        return this;
    }

    private boolean canCancel(User user) {
        return this.getUserInitTransaction().getId() == user.getId() || this.getUserInitOperation().getId() == user.getId();
    }

    private void cancelOperationSystem(){
        this.status = CANCELED;
    }
    private void completeOperationSystem(){
        this.status = DONE;
    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
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

    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return this.amount;
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

    public CryptoName getCrypto(){
        return this.cryptoName;
    }
    public void setCrypto(CryptoName cryptoName){
        this.cryptoName = cryptoName;
    }

    public User getUserTransactionOperationId() {return this.intention.getUser();}
    private boolean isInPriceRange(CryptoCurrency cryptoCurrency){
        return cryptoCurrency.compareQuotation(intention.getPrice());
    }

    public Operation awaitsConfirmation(User user) throws OperationException {
        if (this.status == ONGOING && Objects.equals(this.getUserInitOperation().getId(), user.getId())){
            this.setStatus(WAITING);
            return this;
        }else {
            throw new OperationException("Cannot realize transfer");
        }
    }

    public boolean isWaitingFor(Long userId) {
        return (this.status == WAITING && this.userInitOperation.getId() == userId);
    }

    public boolean isComplete() {
        return this.status == DONE;
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
        public OperationBuilder withAmount(double amount){
            operation.setAmount(amount);
            return this;
        }

        public Operation build(){
            return new Operation(operation.intention, operation.userInitOperation, operation.amount);
        }
    }

    public static OperationBuilder builder(){return new OperationBuilder();}
}
