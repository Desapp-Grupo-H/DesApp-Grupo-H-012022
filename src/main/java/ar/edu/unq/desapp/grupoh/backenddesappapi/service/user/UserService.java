package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import java.util.List;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional
    @Override
    public User findById(Long id) throws UserException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserException("The User does not exist"));//Exception);
    }

    @Transactional
    @Override
    public User saveUser(UserDTO userDTO) throws UserException {
        User user = User.builder()
                .withName(userDTO.getName())
                .withLastname(userDTO.getLastName())
                .withEmail(userDTO.getEmail())
                .withPassword(userDTO.getPassword())
                .withAddress(userDTO.getAddress())
                .withCvu(userDTO.getCvu())
                .withWallet(userDTO.getWalletAddress())
                .build();
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User findByEmail(String email){
        return findAll().stream().filter(user -> user.getEmail().equals(email)).findAny().orElse(null);
    }/*
    public void loginUser(){}

    public void logOut(){}*/
}
