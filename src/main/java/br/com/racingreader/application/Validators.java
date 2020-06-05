package br.com.racingreader.application;

import br.com.racingreader.utils.FileUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_EVEN;

public abstract class Validators {

    FileUtils fileUtils = new FileUtils();

    static final DateTimeFormatter LOCAL_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    static final String INVALID_LINE_LOG = "[!] Arquivo com formato inválido: [%s].";

    public LocalTime isValidLocalTime(String value) {
        try {
            return LocalTime.parse(value, LOCAL_TIME_FORMAT);
        } catch (Exception ex) {
            fileUtils.generateLog(Validators.class, format(INVALID_LINE_LOG, ex.getMessage()));
        }
        return null;
    }

    public BigDecimal isValidBigDecimal(String value) {
        try {
            return new BigDecimal(value.replace(",", ".")).setScale(3, HALF_EVEN);
        } catch (Exception ex) {
            fileUtils.generateLog(Validators.class, format(INVALID_LINE_LOG, ex.getMessage()));
        }
        return null;
    }

}
