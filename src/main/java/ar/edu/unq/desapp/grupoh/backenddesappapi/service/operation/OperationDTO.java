package ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.transaction.TransactionDTO;

import javax.validation.constraints.NotNull;

public class OperationDTO {

    @NotNull(message = "intention cannot be null")
    private TransactionIntention intention;
    @NotNull(message = "user cannot be null")
    private User userInitOperation;

    @NotNull(message = "amount cannot be null")
    private double amount;

    public TransactionIntention getTransactionIntention() {
        return this.intention;
    }
    public void setTransactionIntention(TransactionIntention intention) {
        this.intention = intention;
    }

    public User getUserInitOperation() {
        return this.userInitOperation;
    }
    public void setUserInitOperation(User userInitOperation) {
        this.userInitOperation = userInitOperation;
    }

    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Operation createOperation() {
        return new Operation(intention, userInitOperation, amount);
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
