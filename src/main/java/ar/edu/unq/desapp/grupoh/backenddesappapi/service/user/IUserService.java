package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IUserService {
    @Transactional
    List<User> findAll();

    @Transactional
    User findById(Long id) throws UserException;

    @Transactional
    User saveUser(UserDTO userDTO) throws UserException;

    @Transactional
    void deleteUser(Long id);

    @Transactional
    User findByEmail(String email)/*
    public void loginUser(){}

    public void logOut(){}*/;
}
