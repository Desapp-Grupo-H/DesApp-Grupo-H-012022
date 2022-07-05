package ar.edu.unq.desapp.grupoh.backenddesappapi.service.response;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
public class ResponseUSD {
    @Getter @Setter
    private Float venta;
    @Getter @Setter
    private Float compra;
}
