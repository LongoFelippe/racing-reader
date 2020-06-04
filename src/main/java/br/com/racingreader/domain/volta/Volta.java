package br.com.racingreader.domain.volta;

import br.com.racingreader.domain.piloto.Piloto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Volta implements Serializable {

    private Piloto piloto;
    private LocalTime hora;
    private Integer numero;
    private LocalTime tempo;
    private BigDecimal velocidadeMedia;

}
