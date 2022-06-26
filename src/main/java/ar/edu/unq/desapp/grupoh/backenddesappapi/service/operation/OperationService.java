package ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService implements IOperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    @Transactional
    @Override
    public Operation findById(Long operationId) throws OperationException {
        return operationRepository.findById(operationId).orElseThrow(() -> new OperationException("The operation does not exist"));
    }

    @Transactional
    @Override
    public List<Operation> findAll() {
        return this.operationRepository.findAll();
    }

    @Transactional
    @Override
    public Operation saveOperation(Operation operation) {
        return this.operationRepository.save(operation);
    }

    @Transactional
    @Override
    public void actionOperation(long operationId, OperationAction action, Long userId) throws OperationException {
        Operation operation = operationRepository.findById(operationId).orElseThrow(() -> new OperationException("The operation does not exist"));
        switch (action){
            case REALIZETRANSFER:
                operation = operation.awaitsConfirmation(userId);
                break;
            case CONFIRMRECEPTION:
                    operation = operation.completeOperation(userId);
                break;
            case CANCEL:
                operation = operation.cancelOperation(userId);
                break;
            default:
                throw new OperationException("The action is not recognized");
        }
        this.operationRepository.save(operation);
    }

    @Transactional
    @Override
    public int volumeOfOperations(String cryptoName) {
        List<Operation> operations = this.operationRepository.findAll();
        return (int) operations.stream().filter(operation -> operation.isComplete() && operation.getCrypto().getCrypto().name() == cryptoName).collect(Collectors.toList()).size();
    }
}
