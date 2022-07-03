package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TransactionStatus;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;

import javax.persistence.*;
@Entity
@Table(name = "transactionIntention")
public class TransactionIntention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private TypeTransaction typeTransaction; //BUY OR SELL
    @Column(nullable = false)
    private double amount; //Amount of cryptocurrency available for buy/sell
    @Column(nullable = false)
    private float price; //Quotation
    @Column(nullable = false)
    private CryptoName cryptoName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
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

    public TransactionIntention(){}

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }
    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }


    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public CryptoName getCrypto() {
        return cryptoName;
    }
    public void setCrypto(CryptoName cryptoName) {
        this.cryptoName = cryptoName;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public TransactionStatus getStatus(){ return status;}
    public void setStatus(TransactionStatus status){this.status = status;}

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
            transaction.setCrypto(cryptoName);
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

    public static TransactionIntention.TransactionBuilder builder() {
        return new TransactionIntention.TransactionBuilder();
    }


}
