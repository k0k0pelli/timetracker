package id.meier.timetracking.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;


public class DateTimeFormatter {

    public static String d2S(LocalDate date) {
        return formatDate(date);
    }

    public static String formatDate(LocalDate date) {
        java.time.format.DateTimeFormatter df = java.time.format.DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        String s = "";

        if (date != null ) {
            s = df.format(date);
        }
        return s;
    }

    public static String t2S(LocalTime time) {
        return formatTime(time);
    }

    public static String formatTime(LocalTime time) {
        java.time.format.DateTimeFormatter tf = java.time.format.DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        String s = "";
        if (time != null) {
            s = tf.format(time);
        }
        return s;
    }
}
