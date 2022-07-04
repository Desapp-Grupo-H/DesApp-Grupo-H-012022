package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
