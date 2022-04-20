package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.validators.EmailValidator;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(length = 30) //Anotaciones que sirven para limitar el campo a nivel BD
    @Size(min = 3 , max = 30) //Anotaciones que sirven para el min y max del campo
    private String name ;
    @Column(length = 30)
    @Size(min = 3 , max = 30)
    private String lastname;
    private String email;
    @Column(length = 30)
    @Size(min = 10 , max = 30)
    private String adress;
    private String password;
    @Column(length = 8)
    @Size(min = 8 , max = 8)
    private String wallet;
    private int transactionsPoints;
    private int operationsSuccess;

    public User(String name, String lastname, String email, String adress, String password, String wallet, int transactionsPoints, int operationsSuccess) {
        EmailValidator.patternMatches(email);
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

    public void completedTransaction(int points){
        transactionsPoints += points;
        operationsSuccess++;
    }


}
