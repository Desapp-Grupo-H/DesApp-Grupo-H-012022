package ar.edu.unq.desapp.grupoh.backenddesappapi.service.response;

import java.time.LocalDateTime;

public class ResponseUSD {
    private Float venta;
    private Float compra;
    private LocalDateTime fecha = LocalDateTime.now();

    public ResponseUSD(Float compra, Float venta){
        this.compra = compra;
        this.venta = venta;
    }

    public ResponseUSD(){}

    public LocalDateTime getFecha(){return this.fecha;}

    private void setCompra(Float compra) {this.compra = compra;}
    public Float getCompra(){return this.compra;}

    private void setVenta(Float venta) {this.venta = venta;}
    public Float getVenta(){return this.venta;}

}
