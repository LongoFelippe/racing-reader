package br.com.racingreader.application.corrida;

import br.com.racingreader.domain.corrida.Corrida;
import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;

import java.math.BigDecimal;
import java.time.LocalTime;

import static br.com.racingreader.application.piloto.PilotoFactory.buildPiloto;
import static br.com.racingreader.application.volta.VoltaFactory.buildVolta;

public interface CorridaFactory {

    static Corrida makeCorridaFromFileInput(LocalTime horaCorrida,
                                            Integer codigoPiloto,
                                            String nomePiloto,
                                            Integer numeroVoltas,
                                            LocalTime tempoVolta,
                                            BigDecimal velocidadeMediaVolta) {
        Piloto piloto = buildPiloto(codigoPiloto, nomePiloto);
        Volta volta = buildVolta(piloto, horaCorrida, numeroVoltas, tempoVolta, velocidadeMediaVolta);
        return Corrida.builder()
                .build();
    }

}
