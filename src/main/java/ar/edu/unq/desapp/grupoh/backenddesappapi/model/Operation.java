package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import java.time.LocalDateTime;


public class Operation {

    private TransactionIntention saleIntention;

    private TransactionIntention buyIntention;

    private LocalDateTime dateStarted;

    private LocalDateTime dateCompleted;

    public void completeOperation(){
        saleIntention.getUserIntention().completedTransaction(0);
        buyIntention.getUserIntention().completedTransaction(0);
        dateCompleted = LocalDateTime.now();
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
