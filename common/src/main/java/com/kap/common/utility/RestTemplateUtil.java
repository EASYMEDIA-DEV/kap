package com.kap.common.utility;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RestTemplateUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            ;

    @SneakyThrows
    public static <T> T readValue(String content, Class<T> valueType) {
        return objectMapper.readValue(content, valueType);
    }
    @SneakyThrows
    public static <T> T readValue(InputStream src, Class<T> valueType) {
        return objectMapper.readValue(src, valueType);
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }

    public static boolean checkDateFormat(String dateStr, String dateformat) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat(dateformat);
            dateFormatParser.setLenient(false); // false 일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
            dateFormatParser.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkDate(String dateStr) {
        return checkDateFormat(dateStr, "yyyy-MM-dd");
    }

    public static boolean checkDatetime(String dateStr) {
        return checkDateFormat(dateStr, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public static Date parse(String datetimeStr) throws ParseException {
        datetimeStr = datetimeStr.replaceAll("\\.\\d*", "");
        return DateUtils.parseDate(datetimeStr,
                DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern(),
                DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern(),
                DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern(),
                "yyyyMMddHHmmss"
        );
    }

    public static String toDateStr(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(date);
    }

    public static String toDatetimeStr(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(date);
    }

    public static String toDatetimeWithZoneStr(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(date);
    }

    public static LocalDateTime toLocalDateTimeFromTimeZoneString(String datetimeStr) {
        return ZonedDateTime.parse(datetimeStr).withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(String datetimeStr) {
        return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(datetimeStr));
    }
}
