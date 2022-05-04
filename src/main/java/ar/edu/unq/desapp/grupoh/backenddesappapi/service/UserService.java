package ar.edu.unq.desapp.grupoh.backenddesappapi.service;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    public User findById(Long id) throws UserException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserException("El usuario no existe"));//Exception);
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
