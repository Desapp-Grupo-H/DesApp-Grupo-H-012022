package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;

import javax.persistence.*;
@Entity
public class TransactionIntention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private TransactionIntention typeTransaction; //BUY OR SELL
    @Column(nullable = false)
    private double amount; //Cantidad de la criptos a vender/comprar
    @Column(nullable = false)
    private float price; //Cotizacion
    @Column(nullable = false)
    private CryptoCurrency crypto; //Chequear mas adelante si conviene solo que este el enum con los valores
    @Column(nullable = false)
    private User user;


    public TransactionIntention getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TransactionIntention typeTransaction) {
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

        /*public TransactionIntention.TransactionBuilder withTypeTransaction(TypeTransaction type){
            transaction.setTypeTransaction(type);
            return this;
        }

        public TransactionIntention build() throws TransactionException {
            return new TransactionIntention(transaction.typeTransaction, transaction.amount, transaction.price, transaction.crypto, transaction.user);
        }*/

    }
}
