package com.kap.core.validation;

import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * Controller
 * </pre>
 *
 * @ClassName		: BaseValidationCheck.java
 * @Description		: 공통 ValidaionCheck 파일 
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

public class BaseValidationCheck {

    /**
     * 기존 메시지 삭제 후 에러 메시지 새로 생성
     */
    protected ConstraintValidatorContext addConstraintViolation(ConstraintValidatorContext context, String msg) {
        //기본 메시지 비활성화
        context.disableDefaultConstraintViolation();
        //새로운 메시지 추가
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
        return context;
    }

    /**
     * 패턴에 부합한지 확인
     */
    protected boolean chkPattern(String pattern, String val){
        boolean isValidation = false;

        if(val.matches(pattern)){
            isValidation = true;
        }

        return isValidation;
    }

    /**
     * Validation에 부합한지 확인
     */
    protected ConstraintValidatorContext chkVaildation(Object value, ConstraintValidatorContext context, String message, String pattern){
        ConstraintValidatorContext resultContext = null;

        if(value == null || value.toString().trim().isEmpty()){
            resultContext = context;
        }else{
            if(!chkPattern(pattern, value.toString())){
                resultContext = addConstraintViolation(context, message);
            }
        }

        return resultContext;
    }
}
