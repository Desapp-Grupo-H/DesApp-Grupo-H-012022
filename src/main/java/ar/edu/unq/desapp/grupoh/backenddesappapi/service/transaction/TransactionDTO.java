package ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class    TransactionDTO {
// TODO FIX THIS

    @NotNull
    private long id;

    @NotNull(message = "the Transaction type cannot be null")
    private TypeTransaction typeTransaction;

    @NotNull(message = "The amount cannot be null")
    @DecimalMin(message = "The amount available must be bigger than 0", value = "1")
    private double amount;

    @NotNull(message = "The price must not be null")
    @DecimalMin(message = "The price must be bigger than 0.01", value = "0.01")
    private float price;

    @NotNull(message = "Crypto Currency must be specified")
    private CryptoName cryptoName;

    @NotNull(message = "The user must be specified")
    private User user;

    public long getId() {
        return id;
    }
    public void setId(long id) {
    }

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

    public CryptoName getCryptoName() {
        return this.cryptoName;
    }
    public void setCryptoName(CryptoName cryptoName) {
        this.cryptoName = cryptoName;
    }

    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public TransactionIntention createTransaction() {
        return new TransactionIntention(typeTransaction, amount, price, cryptoName, user);
    }
    public static final class TransactionDTOBuilder {
        private TransactionDTO newTransactionDTO;

        private TransactionDTOBuilder() {
            newTransactionDTO = new TransactionDTO();
        }

        public TransactionDTOBuilder withId(long id) {
            newTransactionDTO.setId(id);
            return this;
        }

        public TransactionDTOBuilder withTypeTransaction(TypeTransaction typeTransaction) {
            newTransactionDTO.setType(typeTransaction);
            return this;
        }

        public TransactionDTOBuilder withAmount(double amount) {
            newTransactionDTO.setAmount(amount);
            return this;
        }

        public TransactionDTOBuilder withPrice(long price) {
            newTransactionDTO.setPrice(price);
            return this;
        }

        public TransactionDTOBuilder withCryptoName(CryptoName cryptoName) {
            newTransactionDTO.setCryptoName(cryptoName);
            return this;
        }

        public TransactionDTOBuilder withUser(User user) {
            newTransactionDTO.setUser(user);
            return this;
        }

        public TransactionDTO build() {
            return newTransactionDTO;
        }
    }

    public static TransactionDTO.TransactionDTOBuilder builder() {
        return new TransactionDTO.TransactionDTOBuilder();
    }

}
