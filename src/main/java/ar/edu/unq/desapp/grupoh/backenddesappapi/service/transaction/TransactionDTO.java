package ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransactionDTO {
// TODO FIX THIS
    @NotNull(message = "the Transaction type cannot be null")
    @Length(min = 3, max = 30, message = "The name field should have at least 3 letters and a maximum of 30")
    private TypeTransaction typeTransaction;

    @NotNull(message = "The amount cannot be null")
    @DecimalMin(message = "The amount available must be bigger than 0", value = "1")
    private double amount;

    @NotNull(message = "The price must not be null")
    @DecimalMin(message = "The price must be bigger than 0.01", value = "0.01")
    private float price;

    @NotNull(message = "Crypto Currency must be specified")
    private CryptoCurrency crypto;

    @NotNull(message = "The user must be specified")
    private User user;


    public TypeTransaction getType() {
        return this.typeTransaction;
    }
    public void setType(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public CryptoCurrency getCrypto() {
        return this.crypto;
    }
    public void setCryptoAddress(CryptoCurrency crypto) {
        this.crypto = crypto;
    }

    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public TransactionIntention createTransaction() {
        return new TransactionIntention(typeTransaction, amount, price, crypto, user);
    }

}
