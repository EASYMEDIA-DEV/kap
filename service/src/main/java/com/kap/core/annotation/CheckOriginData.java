package com.kap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckOriginData {
    // null 인 필드는 무시 - 추후 자세히 설명
    boolean ignoreNull() default true;
}