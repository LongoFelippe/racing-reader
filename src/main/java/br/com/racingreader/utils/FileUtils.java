package br.com.racingreader.utils;

import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

@NoArgsConstructor
public final class FileUtils {

    private static final String LOG_FILE_PATH = "output.log";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public void veriyFileExists(String fileName) throws IOException {
        if (!new File(fileName).exists()) {
            throw new IOException("[!] Arquivo não encontrado.");
        }
    }

    public void generateNewFile(String fileContent, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            try {
                fileWriter.write(fileContent);
                fileWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void generateLog(Class<T> clazz, String log) {

        String logMessage = new SimpleDateFormat(DATE_TIME_PATTERN).format(
                new Date()) + " : "
                + clazz.getName() + " : "
                + log;

        out.println(logMessage);

        File raceErrorFile = new File(LOG_FILE_PATH);

        if (raceErrorFile.exists()) {
            try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) {
                fileWriter.write(logMessage);
                fileWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            generateNewFile(logMessage, LOG_FILE_PATH);
        }
    }

}
