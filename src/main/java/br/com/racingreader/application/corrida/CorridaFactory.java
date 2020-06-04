package br.com.racingreader.application.corrida;

import br.com.racingreader.domain.corrida.Corrida;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

public interface CorridaFactory {

    static Corrida makeCorridaFromFileInput(String fileName) throws IOException {
        Stream<String> fileLines = lines(get(fileName));
        fileLines.forEach(line -> {
            String hora = line.substring(0, 12);
            String codigo = line.substring(13, 16);
            String nome = line.substring(18, line.length() - 17);

            int pointer = (hora.length() + 1) + (codigo.length() + 1) + (nome.length() + 1);
            String qtdVoltas = line.substring(pointer, pointer + 1);
            String tempoVolta = line.substring(pointer + 2, pointer + 11);

            int pointerAux = pointer + 10;
            String velocidadeMediavolta = line.substring(pointerAux);

            makeLocalTime(tempoVolta);

            System.out.println("HORA: " + hora);
            System.out.println("NOME: " + nome);
            System.out.println("CÓDIGO: " + codigo);
            System.out.println("QTD VOLTAS: " + qtdVoltas);
            System.out.println("TEMPO VOLTA: " + tempoVolta);
            System.out.println("VELOCIDADE MEDIA VOLTA: " + velocidadeMediavolta);


            System.out.println("       --       ");
            System.out.println();
        });
        fileLines.close();

        return null;
    }

    static LocalTime makeLocalTime(String localTimeString) {
        return LocalTime.of(0,
                parseInt(localTimeString.substring(0, 1)),
                parseInt(localTimeString.substring(2, 4)),
                parseInt(localTimeString.substring(5, 8)) * 1000000);
    }

}
