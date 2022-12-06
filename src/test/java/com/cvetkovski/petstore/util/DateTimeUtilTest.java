package com.cvetkovski.petstore.util;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void testConvertDateToLocalDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2020-01-01");
        LocalDate localDate = DateTimeUtil.convertDateToLocalDate(date);
        assertNotNull(localDate);
        assertEquals(localDate, LocalDate.of(2020, 1, 1));
    }
}