package br.com.racingreader.application.corrida.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoCorridaDTO implements Serializable {

    private Integer posicaoChegada;
    private Integer codigoPiloto;
    private String nomePiloto;
    private Integer qtdVoltasCompletadasPorPiloto;
    private LocalTime tempoTotalProva;

}
