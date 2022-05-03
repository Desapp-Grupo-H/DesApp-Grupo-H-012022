package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

public class TransactionIntention {

    public final String sellIntention = "SELL"; //Alternativa puede ser usar una clase abstracta y dos que hereden

    public final String buyIntention = "BUY";

    private double amount; //Cantidad de la criptos a vender/comprar

    private float price; //Cotizacion

    private CryptoCurrency crypto;

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
}
