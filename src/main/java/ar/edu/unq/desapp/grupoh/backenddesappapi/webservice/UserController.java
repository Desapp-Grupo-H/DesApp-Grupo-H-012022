package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.IUserService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.POST})
public class UserController {
    @Autowired
    private IUserService userService;

    @Transactional
    @GetMapping("/users")
    @LogExecutionTime
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @Transactional
    @GetMapping("/users/{id}")
    @LogExecutionTime
    public ResponseEntity<?> findById(@PathVariable Long id) throws UserException {
        User user = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @Transactional
    @PostMapping("/users")
    @LogExecutionTime
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDto) throws UserException {
        User user = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    @Transactional
    @DeleteMapping("/users/{id}")
    @LogExecutionTime
    public ResponseEntity<?> delete(@PathVariable Long id) throws UserException {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
