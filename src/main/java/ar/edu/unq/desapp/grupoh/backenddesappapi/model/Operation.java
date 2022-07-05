package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import javax.persistence.*;

@Entity
@Table(name = "operation")
@NoArgsConstructor
public class    Operation {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionIntention_id")
    private TransactionIntention intention; //The intention selected by the user

    @Getter @Setter
    @Column(nullable = false)
    private CryptoName cryptoName;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userInitOperation;//The user that initiates the operation

    @Getter @Setter
    @Column(nullable = false)
    private LocalDateTime dateStarted;

    @Getter @Setter
    private LocalDateTime dateCompleted; //LocalDateTime Format "2022-04-19T22:39:10"

    @Getter @Setter
    @Column(nullable = false)
    private OperationStatus status;

    @Getter @Setter
    @Column(nullable = false)
    private double amount;

    public Operation(TransactionIntention intention, User userInitOperation, double amount) {
        this.intention = intention;
        this.cryptoName = this.intention.getCryptoName();
        this.userInitOperation = userInitOperation;
        this.amount = amount;
        this.dateStarted = LocalDateTime.now();
        this.status = ONGOING;
    }

    public Operation completeOperation(User user, CryptoCurrency cryptoCurrency) throws OperationException{
        if (isWaitingFor(user)){
            this.setDateCompleted(LocalDateTime.now());
            if (isInPriceRange(cryptoCurrency)){
                int points = 5;
                if (dateCompleted.getYear() - dateStarted.getYear() == 0 && dateCompleted.getDayOfYear() - dateStarted.getDayOfYear() == 0 && dateCompleted.getHour() - dateStarted.getHour() == 0 && dateCompleted.getMinute() - dateStarted.getMinute() < 30f){
                    points = 10;
                }
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
        return this.intention.getUser();
    }

    public User getUserTransactionOperation() {
        return this.intention.getUser();
    }

    public Operation cancelOperation(User user) throws OperationException { /*The user is the one that cancelled the transaction or nothing in case of a system cancellation*/
        if (this.canCancel(user)){
            user.cancelledTransaction();
            this.cancelOperationSystem();
        }else{
            throw new OperationException("Cannot cancel operation");
        }
        return this;
    }

    private boolean canCancel(User user) {
        return Objects.equals(this.getUserInitTransaction().getId(), user.getId()) || Objects.equals(this.getUserInitOperation().getId(), user.getId());
    }

    private void cancelOperationSystem(){
        this.status = CANCELED;
    }
    private void completeOperationSystem(){
        this.status = DONE;
    }

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

    public boolean isWaitingFor(User user) {
        return (this.status == WAITING && Objects.equals(this.userInitOperation.getId(), user.getId()));
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
