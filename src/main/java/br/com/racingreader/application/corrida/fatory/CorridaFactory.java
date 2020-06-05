package br.com.racingreader.application.corrida.fatory;

import br.com.racingreader.application.corrida.dto.ResultadoCorridaDTO;
import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public interface CorridaFactory {

    static ResultadoCorridaDTO buildResultadoCorridaDTO(Piloto piloto,
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

    static List<ResultadoCorridaDTO> buildresultadoCorridaDTO(List<Volta> voltas) {

        List<ResultadoCorridaDTO> resultadoCorrida = new ArrayList<>();
        AtomicBoolean corridaEncerrada = new AtomicBoolean(false);
        AtomicBoolean corridaEmAndamento = new AtomicBoolean(true);
        AtomicInteger posicaoChegada = new AtomicInteger(0);

        voltas.forEach(volta -> {

            if (corridaEmAndamento.get()) {
                corridaEncerrada.set(volta.getNumero().equals(4));
            }

            if (corridaEncerrada.get()) {
                corridaEmAndamento.set(false);
                posicaoChegada.incrementAndGet();
            }

            if (TRUE.equals(verifyResultadoExisits(resultadoCorrida, volta.getPiloto()))) {
                ResultadoCorridaDTO resultadoExistente = getResultadoCorridaDTOIfExists(resultadoCorrida, volta.getPiloto());

                Integer numeroVoltaAtual = volta.getNumero();
                LocalTime tempoProva = sumTwoLocalTime(resultadoExistente.getTempoTotalProva(), volta.getTempo());
                Integer posicao = (corridaEncerrada.get() && resultadoExistente.getPosicaoChegada() == 0)
                        ? parseInt(posicaoChegada.toString())
                        : resultadoExistente.getPosicaoChegada();

                ResultadoCorridaDTO resultadoAtualizado = updateResultadoCorridaDTO(resultadoExistente, posicao, numeroVoltaAtual, tempoProva);
                removePilotoIfPresent(resultadoCorrida, volta.getPiloto());
                resultadoCorrida.add(resultadoAtualizado);
            } else {
                resultadoCorrida.add(buildResultadoCorridaDTO(volta.getPiloto(), posicaoChegada.get(), volta.getNumero(), volta.getTempo()));
            }

        });

        List<ResultadoCorridaDTO> pilotosCompletaramCorrida = getPilotosCompletaramCorrida(resultadoCorrida);
        List<ResultadoCorridaDTO> pilotosQueNaoCompletaramCorrida = getPilotosNaoCompletaramCorrida(resultadoCorrida, posicaoChegada);
        pilotosCompletaramCorrida.addAll(pilotosQueNaoCompletaramCorrida);

        return ordenaPorTempoDeChegada(resultadoCorrida);
    }

    static void removePilotoIfPresent(List<ResultadoCorridaDTO> resultadoCorrida, Piloto piloto) {
        resultadoCorrida.removeIf(resultado -> resultado.getCodigoPiloto().equals(piloto.getCodigo()));
    }

    static ResultadoCorridaDTO getResultadoCorridaDTOIfExists(List<ResultadoCorridaDTO> resultadoCorrida, Piloto piloto) {
        return resultadoCorrida.stream()
                .filter(resultado -> piloto.getCodigo().equals(resultado.getCodigoPiloto()))
                .findAny()
                .orElse(null);
    }

    static Boolean verifyResultadoExisits(List<ResultadoCorridaDTO> resultadoCorrida, Piloto piloto) {
        return resultadoCorrida.stream().anyMatch(resultado -> piloto.getCodigo().equals(resultado.getCodigoPiloto()));
    }

    static ResultadoCorridaDTO updateResultadoCorridaDTO(ResultadoCorridaDTO resultado,
                                                         Integer posicaoChegada,
                                                         Integer qtdVoltasCompletadas,
                                                         LocalTime tempoTotalProva) {
        return resultado.toBuilder()
                .posicaoChegada(posicaoChegada)
                .qtdVoltasCompletadasPorPiloto(qtdVoltasCompletadas)
                .tempoTotalProva(tempoTotalProva)
                .build();

    }

    static List<ResultadoCorridaDTO> getPilotosNaoCompletaramCorrida(List<ResultadoCorridaDTO> resultados, AtomicInteger posicaoChegada) {
        List<ResultadoCorridaDTO> newResultado = resultados.stream().filter(r -> r.getPosicaoChegada() == 0).collect(toList());
        newResultado.forEach(resultadoCorridaDTO -> resultadoCorridaDTO.setPosicaoChegada(posicaoChegada.getAndIncrement()));
        return newResultado;
    }

    static List<ResultadoCorridaDTO> getPilotosCompletaramCorrida(List<ResultadoCorridaDTO> resultados) {
        return resultados.stream().filter(r -> r.getPosicaoChegada() > 1).collect(toList());
    }

    static List<ResultadoCorridaDTO> ordenaPorTempoDeChegada(List<ResultadoCorridaDTO> resultadoCorridaDTOS) {
        return resultadoCorridaDTOS.stream()
                .sorted(comparing(ResultadoCorridaDTO::getPosicaoChegada)
                        .thenComparing(ResultadoCorridaDTO::getTempoTotalProva)).collect(toList());
    }

    static LocalTime sumTwoLocalTime(LocalTime localTime, LocalTime localTimeForSum) {
        return localTime.plusHours(localTimeForSum.getHour())
                .plusMinutes(localTimeForSum.getMinute())
                .plusSeconds(localTimeForSum.getSecond())
                .plusNanos(localTimeForSum.getNano());
    }

}
