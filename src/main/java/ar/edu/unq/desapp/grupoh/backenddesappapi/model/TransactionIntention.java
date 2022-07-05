package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TransactionStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "transactionIntention")
@NoArgsConstructor
public class TransactionIntention {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private TypeTransaction typeTransaction; //BUY OR SELL
    @Getter
    @Setter
    @Column(nullable = false)
    private double amount; //Amount of cryptocurrency available for buy/sell
    @Getter
    @Setter
    @Column(nullable = false)
    private float price; //Quotation
    @Getter
    @Setter
    @Column(nullable = false)
    private CryptoName cryptoName;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Getter
    @Setter
    @Column(nullable = false)
    private TransactionStatus status;

    public TransactionIntention(TypeTransaction typeTransaction, double amount, float price, CryptoName cryptoName, User user){
        this.typeTransaction = typeTransaction;
        this.amount          = amount;
        this.price           = price;
        this.cryptoName  = cryptoName;
        this.user            = user;
        this.status          = TransactionStatus.ACTIVE;
    }

    public void endIntention(){
        this.setStatus(TransactionStatus.INACTIVE);
    }

    public boolean isBuy(){
        return this.typeTransaction == TypeTransaction.BUY;
    }

    public boolean isSell(){
        return this.typeTransaction == TypeTransaction.SELL;
    }

    public void reduceAvailableAmount(double amount){
        setAmount(this.getAmount() - amount);
        if (this.amount == 0){
            endIntention();
        }
    }

    public static final class TransactionBuilder {

        private final TransactionIntention transaction = new TransactionIntention();

        private TransactionBuilder() {
        }

        public TransactionIntention.TransactionBuilder withTypeTransaction(TypeTransaction type){
            transaction.setTypeTransaction(type);
            return this;
        }

        public TransactionIntention.TransactionBuilder withAmount(double amount){
            transaction.setAmount(amount);
            return this;
        }

        public TransactionIntention.TransactionBuilder withPrice(float price){
            transaction.setPrice(price);
            return this;
        }

        public TransactionIntention.TransactionBuilder withCryptoCurrency(CryptoName cryptoName){
            transaction.setCryptoName(cryptoName);
            return this;
        }

        public TransactionIntention.TransactionBuilder withUser(User user){
            transaction.setUser(user);
            return this;
        }

        public TransactionIntention build() {
            return new TransactionIntention(transaction.typeTransaction, transaction.amount, transaction.price, transaction.cryptoName, transaction.user);
        }

        public TransactionBuilder withId(Long id) {
            transaction.setId(id);
            return this;
        }
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }
}
