package com.kap.core.annotation;

import com.kap.core.validation.PasswordValidationCheck;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * Controller
 * </pre>
 *
 * @ClassName		: CMPassword.java
 * @Description		: CMPassword 이름의 어노테이션 정의
 * @author 신혜정
 * @since 2022.03.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.03.10		신혜정					최초생성
 * </pre>
 */

// 어노테이션이 사용될 위치를 정의
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
// 유효성 검사가 시행될 시기(런타임)
@Retention(RetentionPolicy.RUNTIME)
// 유효성 검사에 사용될 Validator(파일)
@Constraint(validatedBy = {PasswordValidationCheck.class})
public @interface CMPassword {

    // 기본 에러 메시지 (여기서는 빈값인 경우 해당 메시지 노출)
    String message() default "Feel.Validation.defaults.msg.empty.text";       //{msg.err.empty.password}";

    //유효성 검사가 어떤 상황에서 실행되는지 정의할 수 있는 매개 변수 그룹.
    Class<?>[] groups() default {};

    //유효성 검사에 전달할 payload를 정의할 수 있는 매개 변수.
    Class<? extends Payload>[] payload() default {};

}