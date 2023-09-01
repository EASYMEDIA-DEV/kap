package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  메일 객체
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
//GETTER, SETTER
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COMailDTO extends BaseDTO {
    //제목
    private String subject;
    //내용
    private String content;
    //수신자
    private String emails;
    //수신자 이름
    private String name;
    //웹서버 경로
    private String httpFrontUrl;
    //관리자 경로
    private String httpAdmUrl;
    //TITLE 명
    private String siteName;
    //치환 필드정의
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private String field5;
    private String field6;
    private String field7;
    private String field8;
    private String field9;
}
