package br.com.racingreader;

import br.com.racingreader.application.corrida.CorridaApplicationService;
import br.com.racingreader.application.corrida.dto.ResultadoCorridaDTO;
import br.com.racingreader.utils.FileUtils;

import java.util.List;

public class RacingReaderApplication {

    private static final String INPUT_RACING_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        FileUtils fileUtils = new FileUtils();
        CorridaApplicationService applicationService = new CorridaApplicationService();
        try {
            fileUtils.veriyFileExists(INPUT_RACING_FILE_NAME);

            fileUtils.generateLog(RacingReaderApplication.class, "[+] Iniciando leitura...");

            List<ResultadoCorridaDTO> resultadoCorrida = applicationService.buildCorridaFromFileInput(INPUT_RACING_FILE_NAME);
            resultadoCorrida.forEach(resultado ->
                    fileUtils.generateLog(RacingReaderApplication.class, resultado.toString())
            );

        } catch (Exception ex) {
            fileUtils.generateLog(RacingReaderApplication.class, "[!] Erro durante a execução");
        }
        fileUtils.generateLog(RacingReaderApplication.class, "[+] Leitura finalizada!");
    }

}
