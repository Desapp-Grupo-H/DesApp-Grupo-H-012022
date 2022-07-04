package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLoginDTO {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    private String email;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    private String password;

    public UserLoginDTO(){}
    public UserLoginDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email){this.email = email;}
    public String getEmail(){return this.email;}

    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}

}
