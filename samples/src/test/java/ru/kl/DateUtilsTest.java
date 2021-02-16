package ru.kl;

import org.junit.jupiter.api.Test;
import ru.kl.dateutil.DateUtils;
import ru.kl.dateutil.SqlDateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void convertDateToLocalDateTest() throws ParseException {
        assertNull(DateUtils.convertDateToLocalDate(null));

        LocalDate targetLocalDate = LocalDate.of(2021, 01, 27);
        Date actualDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-27");
        assertEquals(targetLocalDate, DateUtils.convertDateToLocalDate(actualDate));

        Date actualDateWithTime = new Date();
        assertThrows(IllegalArgumentException.class, () -> DateUtils.convertDateToLocalDate(actualDateWithTime));
    }

    @Test
    void convertDateToLocalDateTimeTest() throws ParseException {
        assertNull(DateUtils.convertDateToLocalDateTime(null));

        LocalDateTime targetLocalDateTime = LocalDateTime.of(2021, 01, 27, 13, 00, 15);
        Date actualDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-27 13:00:15");
        assertEquals(targetLocalDateTime, DateUtils.convertDateToLocalDateTime(actualDate));
    }

    @Test
    void convertLocalDateToDateTest() throws ParseException {
        assertNull(DateUtils.convertLocalDateToDate(null));

        Date targetDate = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-27");
        LocalDate actualLocalDate = LocalDate.of(2021, 1, 27);
        assertEquals(targetDate, DateUtils.convertLocalDateToDate(actualLocalDate));
    }

    @Test
    void convertLocalDateTimeToDateTest() throws ParseException {
        assertNull(DateUtils.convertLocalDateTimeToDate(null));

        Date targetDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-27 13:00:15");
        LocalDateTime actualLocalDateTime = LocalDateTime.of(2021, 01, 27, 13, 00, 15);
        assertEquals(targetDate, DateUtils.convertLocalDateTimeToDate(actualLocalDateTime));

        LocalDateTime actualLocalDateTimeWithNano = LocalDateTime.of(2021, 01, 27, 13, 00, 15, 345000000);
        assertThrows(
                IllegalArgumentException.class,
                () -> DateUtils.convertLocalDateTimeToDate(actualLocalDateTimeWithNano)
        );
    }

    @Test
    void convertLocalDateToSqlDateTest() {
        assertNull(SqlDateUtils.convertLocalDateToSqlDate(null));

        java.sql.Date targetSqlDate = java.sql.Date.valueOf("2021-01-27");
        LocalDate actualLocalDate = LocalDate.of(2021, 01, 27);
        assertEquals(targetSqlDate, SqlDateUtils.convertLocalDateToSqlDate(actualLocalDate));
    }

    @Test
    void convertLocalDateTimeToSqlTimestampTest() {
        assertNull(SqlDateUtils.convertLocalDateTimeToSqlTimestamp(null));

        java.sql.Timestamp targetSqlTimestamp = Timestamp.valueOf("2021-01-27 00:30:30.345");
        LocalDateTime actualLocalDateTime = LocalDateTime.of(2021, 01, 27, 00, 30, 30, 345000000);
        assertEquals(targetSqlTimestamp, SqlDateUtils.convertLocalDateTimeToSqlTimestamp(actualLocalDateTime));
    }

    @Test
    void convertSqlDatToLocalDateTest() {
        assertNull(SqlDateUtils.convertSqlDateToLocalDate(null));

        LocalDate targetLocalDate = LocalDate.of(2021, 01, 27);
        java.sql.Date actualSqlDate = java.sql.Date.valueOf("2021-01-27");
        assertEquals(targetLocalDate, SqlDateUtils.convertSqlDateToLocalDate(actualSqlDate));
    }

    @Test
    void convertSqlTimestampToLocalDateTimeTest() {
        assertNull(SqlDateUtils.convertSqlTimestampToLocalDateTime(null));

        LocalDateTime targetLocalDateTime = LocalDateTime.of(2021, 01, 27, 00, 30, 30, 345000000);
        java.sql.Timestamp actualSqlTimestamp = Timestamp.valueOf("2021-01-27 00:30:30.345");
        assertEquals(targetLocalDateTime, SqlDateUtils.convertSqlTimestampToLocalDateTime(actualSqlTimestamp));
    }
}
