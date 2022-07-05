package ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.CryptoCurrency;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.TypeTransaction;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransactionDTO {

    @Getter
    @Setter
    @NotNull
    private long id;

    @Getter
    @Setter
    @NotNull(message = "the Transaction type cannot be null")
    private TypeTransaction typeTransaction;

    @Getter
    @Setter
    @NotNull(message = "The amount cannot be null")
    @DecimalMin(message = "The amount available must be bigger than 0", value = "1")
    private double amount;

    @Getter
    @Setter
    @NotNull(message = "The price must not be null")
    @DecimalMin(message = "The price must be bigger than 0.01", value = "0.01")
    private float price;

    @Getter
    @Setter
    @NotNull(message = "Crypto Currency must be specified")
    private CryptoName cryptoName;

    @Getter
    @Setter
    @NotNull(message = "The user must be specified")
    private User user;

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
            newTransactionDTO.setTypeTransaction(typeTransaction);
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
