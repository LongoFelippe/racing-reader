package br.com.racingreader.application.corrida.fatory;

import br.com.racingreader.application.corrida.dto.ResultadoCorridaDTO;
import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface CorridaFactory {

    static List<ResultadoCorridaDTO> buildQtdVoltasCompletadas(List<Volta> voltas) {
        List<ResultadoCorridaDTO> resultadoCorrida = new ArrayList<>();
        AtomicInteger posicao = new AtomicInteger(1);
        voltas.forEach(volta -> resultadoCorrida
                .add(buildResultadoCorrida(volta.getPiloto(), posicao.getAndIncrement(), volta.getNumero(), LocalTime.now())));

        return resultadoCorrida;
    }

    static ResultadoCorridaDTO buildResultadoCorrida(Piloto piloto,
                                                     Integer posicaoChegada,
                                                     Integer qtdVoltasCompletadasPorPiloto,
                                                     LocalTime tempoTotalProva) {

        return ResultadoCorridaDTO.builder()
                .posicaoChegada(posicaoChegada)
                .codigoPiloto(piloto.getCodigo())
                .nomePiloto(piloto.getNome())
                .qtdVoltasCompletadasPorPiloto(qtdVoltasCompletadasPorPiloto)
                .tempoTotalProva(tempoTotalProva)
                .build();
    }

}
