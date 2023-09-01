package com.kap.core.validation;

import com.kap.core.annotation.CMId;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * Controller
 * </pre>
 *
 * @ClassName		: NameValidationCheck.java
 * @Description		: 실질적으로 Name Validation을 확인하는 파일
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

public class NameValidationCheck extends BaseValidationCheck implements ConstraintValidator<CMId, Object> {

    @Value("${pattern.korean}")     // TODO 이름의 형식이 어떤걸지 몰라 한글로 표시
    String pattern;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ConstraintValidatorContext vaildContext = chkVaildation(value,  context, "Feel.Msg.Name", pattern) ;     //{msg.err.pattern.name}
        
        // 정상적으로 입력된 경우는 true, 그렇지 않은 경우는 해당 사항에 맞게 message 적용후 false 리턴
        if(vaildContext == null){
            return true;
        }else{
            context = vaildContext;
            return false;
        }
    }
}
