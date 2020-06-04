package br.com.racingreader.application.corrida;

import br.com.racingreader.application.piloto.PilotoDTO;
import br.com.racingreader.application.volta.VoltaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorridaDTO implements Serializable {

    private LocalDateTime hora;
    private PilotoDTO piloto;
    private VoltaDTO volta;

}
