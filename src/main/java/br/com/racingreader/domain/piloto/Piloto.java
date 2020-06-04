package br.com.racingreader.domain.piloto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Piloto implements Serializable {

    private Integer codigo;
    private String nome;

}
