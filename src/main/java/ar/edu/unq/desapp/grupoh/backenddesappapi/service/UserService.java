package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import java.util.List;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) throws UserException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserException("The User does not exist"));//Exception);
    }

    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

   /* public User findByEmail(String email){
        return this.userRepository.findBy();
    }
    public void loginUser(){}

    public void logOut(){}*/
}
