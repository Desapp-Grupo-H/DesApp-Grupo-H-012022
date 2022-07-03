package ar.edu.unq.desapp.grupoh.backenddesappapi.service.response;

import java.time.LocalDateTime;

public class ResponseUSD {
    private Float venta;
    private Float compra;

    public ResponseUSD(Float compra, Float venta){
        this.compra = compra;
        this.venta = venta;
    }

    public ResponseUSD(){}

    public void setCompra(Float compra) {this.compra = compra;}
    public Float getCompra(){return this.compra;}

    public void setVenta(Float venta) {this.venta = venta;}
    public Float getVenta(){return this.venta;}

}
