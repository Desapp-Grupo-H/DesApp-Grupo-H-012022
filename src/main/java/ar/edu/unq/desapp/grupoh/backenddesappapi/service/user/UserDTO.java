package ar.edu.unq.desapp.grupoh.backenddesappapi.service.user;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";

    private long id;

    @NotNull(message = "name cannot be null")
    @Length(min = 3, max = 30, message = "The name field should have at least 3 letters and a maximum of 30")
    private String name;

    @NotNull(message = "lastName cannot be null")
    @Length(min = 3, max = 30, message = "The lastname field should have at least 3 letters and a maximum of 30")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    private String email;

    @NotNull(message = "address cannot be null")
    @Length(min = 10, max = 30, message = "The address field should have at least 10 letters and a maximum of 30")
    private String address;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    private String password;

    @NotNull(message = "cvu cannot be null")
    @Length(min = 22, max = 22, message = "The cvu field should have 22 length")
    @Pattern(regexp = digitsRegex, message = "The cvu field should only have digits")
    private String cvu;

    @NotNull(message = "walletAddress cannot be null")
    @Length(min = 8, max = 8, message = "The walletAddress field should have 8 length")
    @Pattern(regexp = digitsRegex, message = "The walletAddress field should only have digits")
    private String walletAddress;

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCvu() {
        return this.cvu;
    }
    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

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