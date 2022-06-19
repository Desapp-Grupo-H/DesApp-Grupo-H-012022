package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
