package ar.edu.unq.desapp.grupoh.backenddesappapi.service.operation;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.CryptoName;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationAction;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.OperationException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.OperationRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency.CryptoCurrencyService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.cryptoCurrency.ICryptoCurrencyService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.IUserService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OperationService implements IOperationService {

    @Autowired
    private IUserService userService;

    @Autowired
    @Lazy
    private ICryptoCurrencyService cryptoCurrencyService;

    @Autowired
    @Lazy
    private OperationRepository operationRepository;

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
    public Operation actionOperation(Long operationId, OperationAction action, Long userId) throws OperationException, UserException {
        Operation operation = operationRepository.findById(operationId).orElseThrow(() -> new OperationException("The operation does not exist"));
        User userOperated = userService.findById(userId);
        try{
            switch (action){
                case REALIZETRANSFER:
                    operation = operation.awaitsConfirmation(userOperated);
                    break;
                case CONFIRMRECEPTION:
                    CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrency(operation.getCryptoName());
                    operation = operation.completeOperation(userOperated, cryptoCurrency);
                    break;
                case CANCEL:
                    operation = operation.cancelOperation(userOperated);
                    break;
                default:
                    throw new OperationException("The action is not recognized");
            }
        }
        finally {
            return this.operationRepository.save(operation);
        }
    }

    @Transactional
    @Override
    public int volumeOfOperations(CryptoName cryptoName) {
        List<Operation> operations = findAll();
        return (int) operations
                .stream()
                .filter(operation -> operation.isComplete() && operation.getCryptoName().equals(cryptoName)).count();
    }
}
