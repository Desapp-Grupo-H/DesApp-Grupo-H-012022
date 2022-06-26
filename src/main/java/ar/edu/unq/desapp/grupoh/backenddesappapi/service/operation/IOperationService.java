package ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOperationService {

    @Transactional
    Operation findById(Long operationId) throws OperationException;

    @Transactional
    List<Operation> findAll();

    @Transactional
    Operation saveOperation(Operation operation);

    @Transactional
    void actionOperation(long operationId, OperationAction action, Long userId) throws OperationException;

    @Transactional
    int volumeOfOperations(String cryptoName);
}
