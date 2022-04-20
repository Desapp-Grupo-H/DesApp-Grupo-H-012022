package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import java.time.LocalDateTime;


public class Operation {

    private TransactionIntention saleIntention;

    private TransactionIntention buyIntention;

    private LocalDateTime dateStarted = LocalDateTime.now();

    private LocalDateTime dateCompleted; //Formato de LocalDateTime "2022-04-19T22:39:10"

    public void completeOperation(){
        //Logica del tiempo puede ser mejor
        int diff = dateCompleted.getMinute() - dateStarted.getMinute();
        int points = (diff == 30) ? 10 : 5 ;
        saleIntention.getUserIntention().completedTransaction(points);
        buyIntention.getUserIntention().completedTransaction(points);
    }

    public TransactionIntention getSaleIntention() {
        return saleIntention;
    }

    public void setSaleIntention(TransactionIntention saleIntention) {
        this.saleIntention = saleIntention;
    }

    public TransactionIntention getBuyIntention() {
        return buyIntention;
    }

    public void setBuyIntention(TransactionIntention buyIntention) {
        this.buyIntention = buyIntention;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
