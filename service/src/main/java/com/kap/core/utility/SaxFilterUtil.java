package com.kap.core.utility;

import com.kap.core.annotation.SaxFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

import java.lang.reflect.Field;

public class SaxFilterUtil {
    public static <T> T checkAnnotation(T targetObj) {
        Field[] fields = targetObj.getClass().getDeclaredFields();
        for (Field f : fields) {

            SaxFilter annotation = f.getAnnotation(SaxFilter.class); //필드에 선언된 어노테이션을 가져온다.
            if(annotation != null && f.getType() == String.class) { //어노테이션이 null이 아니며 타입이 문자열인 경우
                f.setAccessible(true); //해당 필드의 접근을 허용
                try {
                    f.set(targetObj, filter((String)f.get(targetObj)));

                }
                catch (IllegalAccessException e) { System.out.println(e.getMessage()); }
            }
        }
        return targetObj;
    }

    private static String filter(String inputMsg){
        String convertMsg = XssPreventer.unescape(inputMsg);
        //HTML태그 변환을 해야한다.CNTN 타입에 역치환용
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml",true);
        String actual = filter.doFilter(convertMsg);

        return actual;
    }
}
