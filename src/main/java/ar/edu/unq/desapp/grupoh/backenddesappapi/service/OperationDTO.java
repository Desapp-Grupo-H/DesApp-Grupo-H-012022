package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;

import javax.validation.constraints.NotNull;

public class OperationDTO {

    @NotNull(message = "intention cannot be null")
    private TransactionIntention intention;
    @NotNull(message = "user cannot be null")
    private User userInitOperation;

    public Operation createOperation() throws OperationException {
        return new Operation(intention, userInitOperation);
    }
}
