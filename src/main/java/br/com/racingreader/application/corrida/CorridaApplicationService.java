package br.com.racingreader.application.corrida;

import br.com.racingreader.application.Validators;
import br.com.racingreader.application.piloto.PilotoFactory;
import br.com.racingreader.application.volta.VoltaFactory;
import br.com.racingreader.domain.corrida.Corrida;
import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;
import br.com.racingreader.utils.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.racingreader.application.piloto.PilotoFactory.buildPiloto;
import static br.com.racingreader.application.volta.VoltaFactory.buildVolta;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

public class CorridaApplicationService extends Validators {

    public void buildCorridaFromFileInput(String fileName) {
        FileUtils fileUtils = new FileUtils();
        List<Volta> voltas = new ArrayList<>();
        try {
            Stream<String> fileLines = lines(get(fileName));
             voltas.addAll(fileLines.map(line -> {
                String hora = getHora(line);
                String codigo = getCodigo(line);
                String nome = getNome(line);
                int pointer = hora.length() + codigo.length() + nome.length() + 5;
                String qtdVoltas = getQtdVoltas(line, pointer);
                String tempoVolta = getTempoVolta(line, pointer);
                String velocidadeMediavolta = getVelocidadeMediaVolta(line, pointer);

                Piloto piloto = buildPiloto(parseInt(codigo), nome);
                return buildVolta(piloto,
                        buildIfItsValidLocalTimeWithHours(hora),
                        parseInt(qtdVoltas),
                        buildIfItsValidLocalTimeWithMinutes(tempoVolta),
                        buildVelodicadeMediaVoltaIfItsValidBigDecimal(velocidadeMediavolta));

            }).collect(Collectors.toList()));
            fileLines.close();
        } catch (IOException ex) {
            fileUtils.generateLog(Validators.class,
                    format("[!] Arquivo com formato inválido: [%s].", ex.getMessage()));
        }
        System.out.println("Voltas " + voltas);
    }

    private String getHora(String line) {
        return getValueForLineFile(0, 12, line);
    }

    private String getCodigo(String line) {
        return getValueForLineFile(13, 16, line);
    }

    private String getNome(String line) {
        return getValueForLineFile(18, line.length() - 17, line);
    }

    private String getQtdVoltas(String line, int pointer) {
        return getValueForLineFile(pointer, pointer + 1, line);
    }

    private String getTempoVolta(String line, int pointer) {
        return getValueForLineFile(pointer + 2, pointer + 11, line);
    }

    private String getVelocidadeMediaVolta(String line, int pointer) {
        return line.substring(pointer + 10).trim();
    }

    private String getValueForLineFile(int initialIndex, int endIndex, String line) {
        return line.substring(initialIndex, endIndex).trim();
    }

    private LocalTime buildIfItsValidLocalTimeWithMinutes(String localTimeString) {
        LocalTime localTimeParsed = LocalTime.of(0,
                parseInt(localTimeString.substring(0, 1)),
                parseInt(localTimeString.substring(2, 4)),
                parseInt(localTimeString.substring(5, 8)) * 1000000);
        return super.isValidLocalTime(localTimeParsed.toString());
    }

    private LocalTime buildIfItsValidLocalTimeWithHours(String localTimeString) {
        return super.isValidLocalTime(localTimeString);
    }

    private BigDecimal buildVelodicadeMediaVoltaIfItsValidBigDecimal(String value) {
        return super.isValidBigDecimal(value);
    }

}
