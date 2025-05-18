package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoCreateDTO {

    @NotBlank(message = "El tipo de pago no puede estar vacío")
    private String tipoPago;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 12, fraction = 2)
    private BigDecimal monto;

    @NotNull(message = "La fecha de pago no puede ser nula")
    private LocalDateTime fechaPago;

}
