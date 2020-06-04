package br.com.racingreader.application.volta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoltaDTO implements Serializable {

    private LocalDateTime hora;
    private Integer numero;
    private LocalTime tempo;
    private BigDecimal velocidadeMedia;


}
