package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators.Validator;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @NotNull(message = "name cannot be null")
    @Length(min = 3, max = 30, message = "The name field should have at least 3 letters and a maximum of 30")
    private String name;
    @Getter
    @NotNull(message = "lastName cannot be null")
    @Length(min = 3, max = 30, message = "The lastName field should have at least 3 letters and a maximum of 30")
    private String lastName;
    @Getter
    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    private String email;
    @Getter
    @NotNull(message = "address cannot be null")
    @Length(min = 10, max = 30, message = "The address field should have at least 10 letters and a maximum of 30")
    private String address;

    @Getter
    @Setter
    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowercase, a uppercase, a special character")
    private String password;
    @Getter
    @NotNull(message = "cvu cannot be null")
    @Length(min = 22, max = 22, message = "The cvu field should have 22 length")
    @Pattern(regexp = digitsRegex, message = "The cvu field should only have digits")
    private String cvu;

    @Getter
    @NotNull(message = "walletAddress cannot be null")
    @Length(min = 8, max = 8, message = "The walletAddress field should have 22 length")
    @Pattern(regexp = digitsRegex, message = "The walletAddress field should only have digits")
    private String wallet;
    @Getter @Setter
    private int transactionsPoints = 0;
    @Getter @Setter
    private int successfulOperations = 0;


    public User(String name, String lastName, String email, String address, String password, String wallet, String cvu) throws UserException {
        this.setName(name);
        this.setLastname(lastName);
        this.setEmail(email);
        this.setAddress(address);
        this.setPassword(password);
        this.setWallet(wallet);
        this.setCvu(cvu);
    }

    public static User build(UserDTO userDTO) throws UserException {
        return User.builder()
                .withName(userDTO.getName())
                .withLastname(userDTO.getLastName())
                .withEmail(userDTO.getEmail())
                .withPassword(userDTO.getPassword())
                .withAddress(userDTO.getAddress())
                .withCvu(userDTO.getCvu())
                .withWallet(userDTO.getWalletAddress())
                .build();
    }

    public void setName(String name) throws UserException {
        Validator.nameMatches(name);
        this.name = name;
    }

    public void setLastname(String lastName) throws UserException {
        Validator.nameMatches(lastName);
        this.lastName = lastName;
    }

    public void setEmail(String email) throws UserException {
        Validator.patternMatches(email);
        this.email = email;
    }

    public void setAddress(String address) throws UserException {
        Validator.addressMatches(address);
        this.address = address;
    }

    public void setWallet(String wallet) throws UserException {
        Validator.walletMatches(wallet);
        this.wallet = wallet;
    }

    public void setCvu(String cvu) throws UserException {
        Validator.cvuMatches(cvu);
        this.cvu = cvu;
    }

    public void completedTransaction(int points){
        this.transactionsPoints += points;
        this.successfulOperations++;
    }

    public void cancelledTransaction(){ /*For when the users cancel a transaction*/
        this.transactionsPoints -= 20;
    }

    public static final class UserBuilder {
        private final User user = new User();

        private UserBuilder() {}

        public UserBuilder withId(Long id){
            user.setId(id);
            return this;
        }

        public UserBuilder withName(String name) throws UserException {
            user.setName(name);
            return this;
        }

        public UserBuilder withLastname(String lastName) throws UserException {
            user.setLastname(lastName);
            return this;
        }

        public UserBuilder withEmail(String email) throws UserException {
            user.setEmail(email);
            return this;
        }

        public UserBuilder withAddress(String address) throws UserException {
            user.setAddress(address);
            return this;
        }

        public UserBuilder withCvu(String cvu) throws UserException {
            user.setCvu(cvu);
            return this;
        }

        public UserBuilder withPassword(String password){
            user.setPassword(password);
            return this;
        }

        public UserBuilder withWallet(String wallet) throws UserException {
            user.setWallet(wallet);
            return this;
        }

        public User build() throws UserException {
            return user;
        }
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }


}
