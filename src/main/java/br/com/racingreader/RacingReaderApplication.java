package br.com.racingreader;

import br.com.racingreader.application.corrida.CorridaApplicationService;
import br.com.racingreader.utils.FileUtils;

import java.io.IOException;

import static java.lang.String.format;

public class RacingReaderApplication {

    private static final String INPUT_RACING_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        FileUtils fileUtils = new FileUtils();
        CorridaApplicationService applicationService = new CorridaApplicationService();
        try {
            fileUtils.veriyFileExists(INPUT_RACING_FILE_NAME);

            fileUtils.generateLog(RacingReaderApplication.class, "Iniciando leitura...");

            applicationService.buildCorridaFromFileInput(INPUT_RACING_FILE_NAME);

            fileUtils.generateLog(RacingReaderApplication.class, "Leitura finalizada!");

        } catch (IOException ex) {
            fileUtils.generateLog(RacingReaderApplication.class,
                    format("[!] Erro durante a execução: [%s].", ex.getMessage()));
        }
    }

}
