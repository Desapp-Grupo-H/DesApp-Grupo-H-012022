package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";

    @Getter @Setter
    @NotNull(message = "name cannot be null")
    @Length(min = 3, max = 30, message = "The name field should have at least 3 letters and a maximum of 30")
    private String name;

    @Getter @Setter
    @NotNull(message = "lastName cannot be null")
    @Length(min = 3, max = 30, message = "The lastname field should have at least 3 letters and a maximum of 30")
    private String lastName;

    @Getter @Setter
    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    private String email;

    @Getter @Setter
    @NotNull(message = "address cannot be null")
    @Length(min = 10, max = 30, message = "The address field should have at least 10 letters and a maximum of 30")
    private String address;

    @Getter @Setter
    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowercase, a uppercase, a special character")
    private String password;

    @Getter @Setter
    @NotNull(message = "cvu cannot be null")
    @Length(min = 22, max = 22, message = "The cvu field should have 22 length")
    @Pattern(regexp = digitsRegex, message = "The cvu field should only have digits")
    private String cvu;

    @Getter @Setter
    @NotNull(message = "walletAddress cannot be null")
    @Length(min = 8, max = 8, message = "The walletAddress field should have 8 length")
    @Pattern(regexp = digitsRegex, message = "The walletAddress field should only have digits")
    private String walletAddress;

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public static final class UserDTOBuilder {
        private UserDTO newUserDTO;

        private UserDTOBuilder() {
            newUserDTO = new UserDTO();
        }

        public UserDTOBuilder name(String name) {
            newUserDTO.setName(name);
            return this;
        }

        public UserDTOBuilder surname(String lastName) {
            newUserDTO.setLastName(lastName);
            return this;
        }

        public UserDTOBuilder email(String email) {
            newUserDTO.setEmail(email);
            return this;
        }

        public UserDTOBuilder address(String address) {
            newUserDTO.setAddress(address);
            return this;
        }

        public UserDTOBuilder password(String password) {
            newUserDTO.setPassword(password);
            return this;
        }

        public UserDTOBuilder cvu(String cvu) {
            newUserDTO.setCvu(cvu);
            return this;
        }

        public UserDTOBuilder walletAddress(String walletAddress) {
            newUserDTO.setWalletAddress(walletAddress);
            return this;
        }

        public UserDTO build() {
            return newUserDTO;
        }
    }
}