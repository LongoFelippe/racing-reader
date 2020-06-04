package br.com.racingreader.application.piloto;

import br.com.racingreader.domain.piloto.Piloto;

public interface PilotoFactory {

    static Piloto buildPiloto(Integer codigo, String nome) {
        return Piloto.builder()
                .codigo(codigo)
                .nome(nome)
                .build();
    }

}
