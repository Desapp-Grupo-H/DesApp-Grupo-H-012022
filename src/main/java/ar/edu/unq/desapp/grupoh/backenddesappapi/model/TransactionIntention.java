package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

public class TransactionIntention {

    private TransactionIntention typeTransaction; //BUY OR SELL
    private double amount; //Cantidad de la criptos a vender/comprar

    private float price; //Cotizacion

    private CryptoCurrency crypto;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CryptoCurrency getCrypto() {
        return crypto;
    }

    public void setCrypto(CryptoCurrency crypto) {
        this.crypto = crypto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
