package ar.edu.unq.desapp.grupoh.backenddesappapi.model;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupoh.backenddesappapi.model.enums.OperationStatus.*;
import javax.persistence.*;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OneToOne
    private TransactionIntention intention; //Es la intencion que fue seleccionada por un usuario

    @ManyToOne
    private CryptoCurrency cryptoactive = intention.getCrypto();
    @Column(nullable = false)
    private User userInitOperation;//El usuario que eligio la intencio puede ser como Comprador o Vendedor

    @Column(nullable = false)
    private LocalDateTime dateStarted = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime dateCompleted; //Formato de LocalDateTime "2022-04-19T22:39:10"

    @Column(nullable = false)
    private OperationStatus status = ONGOING;

    public Operation() {
    }

    public void completeOperation(){
        //Logica del tiempo puede ser mejor
        if (isInPriceRange()){
            int diff = dateCompleted.getMinute() - dateStarted.getMinute();
            int points = (diff == 30) ? 10 : 5 ;
            intention.getUser().completedTransaction(points);
            userInitOperation.completedTransaction(points);
            this.completeOperationSystem();
        }else{
            cancelOperationSystem();
        }
    }

    public void cancelOperation(User user){ /*The user is the one that cancelled the transaction or nothing in case of a system cancellation*/
        //Validar la diferencia con la cotizacion del cripto correspondiente ?
        //Luego asignar/revisar quien fue/como se cancelo la operacion
        //restar puntos a quien sea
        //TODO hacer la logica de cancelar la operacion
        user.cancelledTransaction();
    }

    private void cancelOperationSystem(){
        this.status = CANCELED;
    }
    private void completeOperationSystem(){
        this.status = DONE;
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

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public CryptoCurrency getCrypto(){
        return this.intention.getCrypto();
    }
    private boolean isInPriceRange(){
        intention.getCrypto().compareCotization(intention.getPrice());
        return true;
    }
}
