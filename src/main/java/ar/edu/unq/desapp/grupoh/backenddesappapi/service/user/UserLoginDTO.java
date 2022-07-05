package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import lombok.AllArgsConstructor;
import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor @NoArgsConstructor
public class UserLoginDTO {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    @Getter @Setter
    private String email;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    @Getter @Setter
    private String password;
}
