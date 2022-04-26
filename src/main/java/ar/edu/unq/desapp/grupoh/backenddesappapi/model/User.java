package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators.Validator;

import javax.persistence.*;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name ;
    private String lastname;
    private String email;
    private String adress;
    private String password;
    private String wallet;

    private String cvu;
    private int transactionsPoints = 0;
    private int operationsSuccess  = 0;


    public User(String name, String lastname, String email, String adress, String password, String wallet, int transactionsPoints, int operationsSuccess) throws UserException {
        Validator.patternMatches(email);
        Validator.nameMatches(name);
        Validator.nameMatches(lastname);
        Validator.adressMatches(adress);
        Validator.walletMatches(wallet);
        Validator.walletMatches(wallet);
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = adress;
        this.password = password;
        this.wallet = wallet;
        this.transactionsPoints = transactionsPoints;
        this.operationsSuccess = operationsSuccess;
    }

    public User() {

    }

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public int getOperationsSuccess() {
        return operationsSuccess;
    }

    public void setOperationsSuccess(int operationsSuccess) {
        this.operationsSuccess = operationsSuccess;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public void completedTransaction(int points){
        transactionsPoints += points;
        operationsSuccess++;
    }

    public static final class UserBuilder {
        private final User user = new User();

        private UserBuilder() {
        }

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

        public UserBuilder withAdress(String adress){
            user.setAdress(adress);
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

        public UserBuilder withOperationsSuccess(int size){
            user.setOperationsSuccess(size);
            return this;
        }

        public User build() throws UserException {
            return new User(user.name, user.lastname, user.email, user.adress, user.password, user.wallet, user.transactionsPoints,user.operationsSuccess);
        }

    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }


}
