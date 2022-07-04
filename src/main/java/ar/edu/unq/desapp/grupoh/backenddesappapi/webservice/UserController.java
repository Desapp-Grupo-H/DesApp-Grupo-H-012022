package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.IUserService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    @LogExecutionTime
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok().body(users);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/users/{id}")
    @LogExecutionTime
    public ResponseEntity<User> findById(@PathVariable Long id){
        try {
            User user = this.userService.findById(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/users/{id}")
    @LogExecutionTime
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
