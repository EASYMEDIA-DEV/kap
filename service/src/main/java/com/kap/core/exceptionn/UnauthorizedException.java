package com.kap.core.exceptionn;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <pre>
 * 인증 체크 실패 Exception
 * </pre>
 *
 * @ClassName		: UnauthorizedException.java
 * @Description		: 인증 체크 실패 Exception
 * @author 허진영
 * @since 2021.04.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2021.04.26		허진영					최초생성
 * </pre>
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
