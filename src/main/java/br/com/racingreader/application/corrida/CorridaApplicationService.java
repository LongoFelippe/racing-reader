package br.com.racingreader.application.corrida;

import br.com.racingreader.application.Validators;
import br.com.racingreader.application.corrida.dto.ResultadoCorridaDTO;
import br.com.racingreader.domain.piloto.Piloto;
import br.com.racingreader.domain.volta.Volta;
import br.com.racingreader.utils.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static br.com.racingreader.application.corrida.fatory.CorridaFactory.buildresultadoCorridaDTO;
import static br.com.racingreader.application.piloto.factory.PilotoFactory.buildPiloto;
import static br.com.racingreader.application.volta.factory.VoltaFactory.buildVolta;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

public class CorridaApplicationService extends Validators {

    public List<ResultadoCorridaDTO> buildCorridaFromFileInput(String fileName) {
        FileUtils fileUtils = new FileUtils();
        List<Volta> voltas = null;
        try (Stream<String> fileLines = Files.lines(get(fileName))) {
            voltas = new ArrayList<>(fileLines.map(line -> {
                String hora = getHoraFromLine(line);
                String codigo = getCodigoFromLine(line);
                String nome = getNomeFromLine(line);
                int pointer = hora.length() + codigo.length() + nome.length() + 5;
                String qtdVoltas = getQtdVoltasFromLine(line, pointer);
                String tempoVolta = getTempoVoltaFromLine(line, pointer);
                String velocidadeMediavolta = getVelocidadeMediaVoltaFromLine(line, pointer);

                Piloto piloto = buildPiloto(parseInt(codigo), nome);
                return buildVolta(piloto,
                        buildIfItsValidLocalTimeWithHours(hora),
                        parseInt(qtdVoltas),
                        buildIfItsValidLocalTimeWithMinutes(tempoVolta),
                        buildVelodicadeMediaVoltaIfItsValidBigDecimal(velocidadeMediavolta));

            }).collect(toList()));
        } catch (IOException ex) {
            fileUtils.generateLog(CorridaApplicationService.class,
                    format("[!] Arquivo com formato inválido: [%s].", ex.getMessage()));
        }
        return buildresultadoCorridaDTO(voltas);
    }

    private String getHoraFromLine(String line) {
        return getValueForLineFile(0, 12, line);
    }

    private String getCodigoFromLine(String line) {
        return getValueForLineFile(13, 16, line);
    }

    private String getNomeFromLine(String line) {
        return getValueForLineFile(18, line.length() - 17, line);
    }

    private String getQtdVoltasFromLine(String line, int pointer) {
        return getValueForLineFile(pointer, pointer + 1, line);
    }

    private String getTempoVoltaFromLine(String line, int pointer) {
        return getValueForLineFile(pointer + 2, pointer + 11, line);
    }

    private String getVelocidadeMediaVoltaFromLine(String line, int pointer) {
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
