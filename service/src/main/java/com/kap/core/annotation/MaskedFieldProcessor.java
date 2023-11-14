package com.kap.core.annotation;

import com.kap.core.dto.MPAUserDto;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MaskedFieldProcessor {
    public static void process(List<MPAUserDto> mpaUserDtos) throws IllegalAccessException {
        for (MPAUserDto dto : mpaUserDtos) {
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(MaskRequired.class)) {
                    field.setAccessible(true);
                    MaskRequired maskRequiredAnnotation = field.getAnnotation(MaskRequired.class);
                    String type = maskRequiredAnnotation.type();

                    if (field.getType() == String.class) {
                        String value = (String) field.get(dto);
                        if (value != null) {
                            field.set(dto, maskString(value, type));
                        }
                    }
                }
            }
        }
    }

    private static String maskString(String value, String type) {
        String str = "";
        switch (type) {
            case "NAME":
                str = getNameMask(value);
                break;
            case "ID":
                str = getIdMask(value);
                break;
            case "EMAIL":
                str = getEmailMask(value);
                break;
            case "PH":
                str = getPhMask(value);
                break;
            case "BIRTH":
                str = getBirthMask(value);
                break;
            default:
                break;
        }
        return str;
    }

    private static String getBirthMask(String value) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(value);

            // 마스킹할 부분
            SimpleDateFormat maskedFormat = new SimpleDateFormat("yyyy-MM");
            String maskedDate = maskedFormat.format(date);

            return maskedDate;
        } catch (ParseException e) {
            return value;
        }
    }

    private static String getPhMask(String value) {
        if (value != null && value.length() == 11) {
            String prefix = value.substring(0, 3);
            String middlePart = "****";
            String suffix = value.substring(7, 11);
            return prefix + "-" + middlePart + "-" + suffix;
        } else {
            return value;
        }
    }

    private static String getIdMask(String value) {
        int atIndex = value.length();
        if (atIndex >= 5) {
            int startIndex = 4;
            int endIndex = atIndex;
            String prefix = value.substring(0, startIndex);
            String maskedPart = generateStars(endIndex - startIndex);
            String suffix = value.substring(endIndex);
            return prefix + maskedPart + suffix;
        } else {
            return value;
        }
    }

    private static String getNameMask(String value) {
        if (value == null || value.length() < 2) {
            return value;
        }

        char[] chars = value.toCharArray();
        chars[1] = '*';

        return new String(chars);
    }

    private static String getEmailMask(String value) {
        int atIndex = value.indexOf('@');
        if (atIndex >= 5) {
            int startIndex = 4;
            int endIndex = atIndex;
            String prefix = value.substring(0, startIndex);
            String maskedPart = generateStars(endIndex - startIndex);
            String suffix = value.substring(endIndex);
            return prefix + maskedPart + suffix;
        } else {
            return value;
        }
    }

    private static String generateStars(int count) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stars.append('*');
        }
        return stars.toString();
    }
}