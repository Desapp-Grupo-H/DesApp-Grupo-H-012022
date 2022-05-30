package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.TransactionIntention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<TransactionIntention,Long> {
}
