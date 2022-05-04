package ar.edu.unq.desapp.grupoh.backenddesappapi.repository;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
