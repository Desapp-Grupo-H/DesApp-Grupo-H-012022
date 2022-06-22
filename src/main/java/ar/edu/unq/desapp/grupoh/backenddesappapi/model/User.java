package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators.Validator;

import javax.persistence.*;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name ;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String address;
    private String password;
    @Column(nullable = false)//, unique = true
    private String wallet;
    @Column(nullable = false)//, unique = true
    private String cvu;
    private int transactionsPoints = 0;
    private int successfulOperations = 0;


    public User(String name, String lastname, String email, String address, String password, String wallet, String cvu) throws UserException {

        Validator.patternMatches(email);
        Validator.nameMatches(name);
        Validator.nameMatches(lastname);
        Validator.walletMatches(wallet);
        Validator.addressMatches(address);
        Validator.cvuMatches(cvu);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.wallet = wallet;
        this.cvu = cvu;
    }

    public User(){};

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getWallet() {
        return wallet;
    }
    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public int getTransactionsPoints() {
        return transactionsPoints;
    }
    public void setTransactionsPoints(int transactionsPoints) {
        this.transactionsPoints = transactionsPoints;
    }

    public int getSuccessfulOperations() {
        return successfulOperations;
    }

    public void setSuccessfulOperations(int successfulOperations) {
        this.successfulOperations = successfulOperations;
    }

    public String getCvu() {
        return cvu;
    }
    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public void completedTransaction(int points){
        transactionsPoints += points;
        successfulOperations++;
    }

    public void cancelledTransaction(){ /*For when the users cancells a transaction*/
        transactionsPoints -= 20;
    }

    public static final class UserBuilder {
        private final User user = new User();

        private UserBuilder() {}

        public UserBuilder withName(String name){
            user.setName(name);
            return this;
        }
        public UserBuilder withLastname(String lastName){
            user.setLastname(lastName);
            return this;
        }

        public UserBuilder withEmail(String email){
            user.setEmail(email);
            return this;
        }

        public UserBuilder withAddress(String address){
            user.setAddress(address);
            return this;
        }

        public UserBuilder withCvu(String cvu){
            user.setCvu(cvu);
            return this;
        }

        public UserBuilder withPassword(String password){
            user.setPassword(password);
            return this;
        }

        public UserBuilder withWallet(String wallet){
            user.setWallet(wallet);
            return this;
        }

        public UserBuilder withTransactionsPoints(int points){
            user.setTransactionsPoints(points);
            return this;
        }

        public UserBuilder withOperationsSuccess(int size) {
            user.setSuccessfulOperations(size);
            return this;
        }

        public User build() throws UserException {
            return new User(user.name, user.lastname, user.email, user.address, user.password, user.wallet, user.cvu);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }


}
