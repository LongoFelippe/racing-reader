package br.com.racingreader.application.volta.factory;

import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;

import java.math.BigDecimal;
import java.time.LocalTime;

public interface VoltaFactory {

    static Volta buildVolta(Piloto piloto, LocalTime hora, Integer numero,
                            LocalTime tempo, BigDecimal velocidadeMedia) {
        return Volta.builder()
                .piloto(piloto)
                .numero(numero)
                .hora(hora)
                .tempo(tempo)
                .velocidadeMedia(velocidadeMedia)
                .build();
    }

}
