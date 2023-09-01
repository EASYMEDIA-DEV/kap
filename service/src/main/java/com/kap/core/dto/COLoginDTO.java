package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  로그인 객체
 *
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.01.10  박주석         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(exclude = "password", callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COLoginDTO extends BaseDTO {
    //ID
    private String id;
    //전달받은 비밀번호
    private String password;
    //최초 이동할 URL
    private String rdctUrl;
    //이메일 인증 번호
    private String emailAuthNum;
    //이메일2차인증여부
    private String lgnCrtfnYn;
}
