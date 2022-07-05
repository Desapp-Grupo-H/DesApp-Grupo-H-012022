package ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class OperationDTO {

    @Getter
    @Setter
    @NotNull(message = "intention cannot be null")
    private TransactionIntention transactionIntention;

    @Getter
    @Setter
    @NotNull(message = "user cannot be null")
    private User userInitOperation;

    @Getter
    @Setter
    @NotNull(message = "amount cannot be null")
    private double amount;

    public Operation createOperation() {
        return new Operation(transactionIntention, userInitOperation, amount);
    }

    public static final class OperationDTOBuilder {
        private OperationDTO newOperationDTO;

        private OperationDTOBuilder() {
            newOperationDTO = new OperationDTO();
        }

        public OperationDTOBuilder withTransactionIntention(TransactionIntention intention) {
            newOperationDTO.setTransactionIntention(intention);
            return this;
        }

        public OperationDTOBuilder withAmount(double amount) {
            newOperationDTO.setAmount(amount);
            return this;
        }

        public OperationDTOBuilder withUser(User user) {
            newOperationDTO.setUserInitOperation(user);
            return this;
        }

        public OperationDTO build() {
            return newOperationDTO;
        }
    }

    public static OperationDTO.OperationDTOBuilder builder() {
        return new OperationDTO.OperationDTOBuilder();
    }
}
