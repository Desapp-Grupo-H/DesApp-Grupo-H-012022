package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import java.time.LocalDateTime;


public class Operation {

    private TransactionIntention intention; //Es la intencion que fue seleccionada por un usuario

    private User userInitOperation;//El usuario que eligio la intencio puede ser como Comprador o Vendedor

    private LocalDateTime dateStarted = LocalDateTime.now();

    private LocalDateTime dateCompleted; //Formato de LocalDateTime "2022-04-19T22:39:10"

    public Operation() {
    }

    public void completeOperation(){
        //Logica del tiempo puede ser mejor
        if (isInPriceRange()){
            int diff = dateCompleted.getMinute() - dateStarted.getMinute();
            int points = (diff == 30) ? 10 : 5 ;
            intention.getUser().completedTransaction(points);
            userInitOperation.completedTransaction(points);
        }else{
            cancelOperationSystem();////???
            }
    }

    public void cancelOperation(User user){ /*The user is the one that cancelled the transaction or nothing in case of a system cancellation*/

        user.cancelledTransaction();
        //Validar la diferencia con la cotizacion del cripto correspondiente ?
        //
        //Luego asignar/revisar quien fue/como se cancelo la operacion
        //restar puntos a quien sea
        //TODO hacer la logica de cancelar la operacion
    }

    private void cancelOperationSystem(){

    }

    public TransactionIntention getIntention() {
        return intention;
    }

    public void setIntention(TransactionIntention intention) {
        this.intention = intention;
    }

    public User getUserInitOperation() {
        return userInitOperation;
    }

    public void setUserInitOperation(User userInitOperation) {
        this.userInitOperation = userInitOperation;
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

    public Crypto getCrypto(){
        return this.intention.getCrypto().getName();
    }
    private boolean isInPriceRange(){
        intention.getCrypto().compareCotization(intention.getPrice());
        return true;
    }
}
