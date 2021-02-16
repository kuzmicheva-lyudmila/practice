package ru.kl.dateutil;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class SqlDateUtils {

    private SqlDateUtils() {
    }

    public static Timestamp convertLocalDateTimeToSqlTimestamp(LocalDateTime localDateTime) {
        return !Objects.isNull(localDateTime) ? Timestamp.valueOf(localDateTime) : null;
    }

    public static java.sql.Date convertLocalDateToSqlDate(LocalDate localDate) {
        return !Objects.isNull(localDate) ? java.sql.Date.valueOf(localDate) : null;
    }

    public static LocalDateTime convertSqlTimestampToLocalDateTime(Timestamp timestamp) {
        return !Objects.isNull(timestamp) ? timestamp.toLocalDateTime() : null;
    }

    public static LocalDate convertSqlDateToLocalDate(java.sql.Date date) {
        return !Objects.isNull(date) ? date.toLocalDate() : null;
    }
}
