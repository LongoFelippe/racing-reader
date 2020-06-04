package br.com.racingreader.domain.corrida;

import br.com.racingreader.domain.volta.Volta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Corrida implements Serializable {

    private List<Volta> voltas;

}
