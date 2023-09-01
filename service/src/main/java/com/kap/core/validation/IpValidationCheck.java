package com.kap.core.validation;

import com.kap.core.annotation.CMIp;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * Controller
 * </pre>
 *
 * @ClassName		: IpValidationCheck.java
 * @Description		: 실질적으로 IP Validation을 확인하는 파일
 * @author 신지혁
 * @since 2022.04.29
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.04.29		신지혁					최초생성
 * </pre>
 */

public class IpValidationCheck extends BaseValidationCheck implements ConstraintValidator<CMIp, Object> {

    @Value("${pattern.ip}")
    String pattern;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 비 필수
        if(value == "")
            return true;
        ConstraintValidatorContext vaildContext = chkVaildation(value,  context, "Feel.Msg.Ip", pattern) ;

        // 정상적으로 입력된 경우는 true, 그렇지 않은 경우는 해당 사항에 맞게 message 적용후 false 리턴
        if(vaildContext == null){
            return true;
        }else{
            context = vaildContext;
            return false;
        }
    }
}
