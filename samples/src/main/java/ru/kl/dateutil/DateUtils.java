package ru.kl.dateutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class DateUtils {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private static final LocalTime ZERO_TIME = LocalTime.of(0, 0, 0, 0);

    private DateUtils() {
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        LocalDateTime localDateTime = convertDateToLocalDateTime(date);
        if (Objects.isNull(localDateTime) || localDateTime.toLocalTime().equals(ZERO_TIME)) {
            return localDateTime != null ? localDateTime.toLocalDate() : null;
        }

        throw new IllegalArgumentException("Date must be without time");
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return !Objects.isNull(date) ? date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime() : null;
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return !Objects.isNull(localDate) ? getDateForLocalDateTime(localDate.atStartOfDay()) : null;
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        } else if (localDateTime.getNano() > 0) {
            throw new IllegalArgumentException("LocalDateTime must be without nanoseconds");
        }

        return getDateForLocalDateTime(localDateTime);
    }

    private static Date getDateForLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }
}
