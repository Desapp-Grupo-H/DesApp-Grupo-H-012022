package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.security.JWTAuthorizationFilter;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;


@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    //private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();

    @Transactional
    @Override
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Transactional
    @Override
    public User findById(Long id) throws UserException {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new UserException("The User does not exist")
                );
    }

    @Transactional
    @Override
    public User saveUser(UserDTO userDTO) throws UserException {
            Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if(user.isPresent()){
            return null;
        }
        //String password = passwordEncoder.encode(userDTO.getPassword());
        //userDTO.setPassword(password);
        return this.userRepository.save(User.build(userDTO));
    }
    @Transactional
    @Override
    public TokenDTO login(UserLoginDTO userLoginDTO){
        Optional<User> user = userRepository.findByEmail(userLoginDTO.getEmail());
        if(!user.isPresent()){return null;}
        //if (passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())){
        if (Objects.equals(userLoginDTO.getPassword(), user.get().getPassword())){
            return new TokenDTO(JWTAuthorizationFilter.getJWTToken(userLoginDTO.getEmail()));
        }else{
            return null;
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User findByEmail(String email) throws UserException {
        try{
            return this.userRepository.findByEmail(email).get();
        }catch(NoSuchElementException exception){
            throw new UserException("The User does not exist");
        }
        //return findAll().stream().filter(user -> user.getEmail().equals(email)).findAny().orElseThrow(() -> new UserException("The User does not exist"));
    }
}
