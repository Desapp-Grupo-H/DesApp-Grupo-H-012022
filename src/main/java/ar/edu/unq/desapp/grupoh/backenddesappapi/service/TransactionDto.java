package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TransactionDto {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";

//TODO FIX THIS
    @NotNull(message = "name cannot be null")
    @Length(min = 3, max = 30, message = "The name field should have at least 3 letters and a maximum of 30")
    private TypeTransaction typeTransaction;

    @NotNull(message = "lastName cannot be null")
    @Length(min = 3, max = 30, message = "The lastname field should have at least 3 letters and a maximum of 30")
    private double amount;

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    private float price;

    @NotNull(message = "address cannot be null")
    @Length(min = 10, max = 30, message = "The address field should have at least 10 letters and a maximum of 30")
    private CryptoCurrency crypto;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    private User user;


    public TypeTransaction getType() {
        return typeTransaction;
    }
    public void setType(TypeTransaction typeTransaction) {
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
    public void setCryptoAddress(CryptoCurrency crypto) {
        this.crypto = crypto;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public TransactionIntention createTransaction() throws TransactionException {
        return new TransactionIntention(typeTransaction, amount, price, crypto, user);
    }

}
