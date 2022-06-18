package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;

import javax.persistence.*;
@Entity
public class TransactionIntention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private TypeTransaction typeTransaction; //BUY OR SELL
    @Column(nullable = false)
    private double amount; //Amount of cryptocurrency available for buy/sell
    @Column(nullable = false)
    private float price; //Cotization
    @Column(nullable = false)
    private CryptoCurrency crypto;
    @Column(nullable = false)
    private User user;

    public TransactionIntention(TypeTransaction typeTransaction, double amount, float price, CryptoCurrency crypto, User user){
        this.typeTransaction = typeTransaction;
        this.amount          = amount;
        this.price           = price;
        this.crypto          = crypto;
        this.user            = user;
    }

    public TransactionIntention(){};

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
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

    public CryptoCurrency getCrypto() {
        return crypto;
    }

    public void setCrypto(CryptoCurrency crypto) {
        this.crypto = crypto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        public TransactionIntention.TransactionBuilder withCryptoCurrency(CryptoCurrency crypto){
            transaction.setCrypto(crypto);
            return this;
        }

        public TransactionIntention.TransactionBuilder withUser(User user){
            transaction.setUser(user);
            return this;
        }

        public TransactionIntention build() throws TransactionException {
            return new TransactionIntention(transaction.typeTransaction, transaction.amount, transaction.price, transaction.crypto, transaction.user);
        }
    }
}
