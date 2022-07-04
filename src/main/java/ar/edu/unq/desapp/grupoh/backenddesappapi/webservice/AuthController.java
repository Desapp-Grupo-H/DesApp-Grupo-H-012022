package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;


import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.IUserService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.TokenDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserLoginDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.webservice.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @LogExecutionTime
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        TokenDTO tokenDTO = userService.login(userLoginDTO);
        if(tokenDTO == null)
            ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.OK).header("token", tokenDTO.token).build();
    }

    @PostMapping("/register")
    @LogExecutionTime
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDto) {
        try {
            User user = userService.saveUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
