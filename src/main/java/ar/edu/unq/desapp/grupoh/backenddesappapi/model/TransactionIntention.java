package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

public class TransactionIntention {

    public final String sellIntention = "SELL"; //Alternativa puede ser usar una clase abstracta y dos que hereden

    public final String buyIntention = "BUY";

    private double amount;

    private double price; //Cotizacion

    private Criptos cripto;

    private User user;

    public User getUserIntention() {
        return user;
    }

    public void setUserIntention(User userIntention) {
        this.user = userIntention;
    }

    public String getSellIntention() {
        return sellIntention;
    }

    public String getBuyIntention() {
        return buyIntention;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Criptos getCripto() {
        return cripto;
    }

    public void setCripto(Criptos cripto) {
        this.cripto = cripto;
    }
}
