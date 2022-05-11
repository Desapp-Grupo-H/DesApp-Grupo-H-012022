package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*" ,methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws UserException {
        return ResponseEntity.status(200).body(this.userService.findById(id));
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) throws UserException {
        User user = userDto.createUser();
        userService.saveUser(user);
        return ResponseEntity.status(201).body(userDto);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws UserException {
        userService.deleteUser(id);
        return ResponseEntity.status(200).build();
    }
}
