package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.TransactionException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Operation findById(Long operationId) throws OperationException {
        return operationRepository.findById(operationId).orElseThrow(() -> new OperationException("La Transaccion no existe"));
    }

    @Transactional
    public List<Operation> findAll() {
        return this.operationRepository.findAll();
    }

    @Transactional
    public Operation saveOperation(Operation operation) {
        return this.operationRepository.save(operation);
    }

    @Transactional
    public void acceptOperation(long operationId) throws OperationException {
        Operation operation = operationRepository.findById(operationId).orElseThrow(() -> new OperationException("The operation does not exist"));
        operation.completeOperation();
        this.operationRepository.save(operation);
    }

    @Transactional
    public void declineOperation(long operationId) throws OperationException{
        Operation operation = operationRepository.findById(operationId).orElseThrow(() -> new OperationException("The operation does not exist"));
        operation.cancelOperation();
        this.operationRepository.save(operation);
    }
}
