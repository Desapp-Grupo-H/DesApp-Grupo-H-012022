package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

public class TransactionIntention {

    private TransactionIntention typeTransaction; //BUY OR SELL
    private double amount; //Cantidad de la criptos a vender/comprar

    private double price; //Cotizacion

    private Criptos cripto;

    private User user;

    public TransactionIntention getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TransactionIntention typeTransaction) {
        this.typeTransaction = typeTransaction;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
