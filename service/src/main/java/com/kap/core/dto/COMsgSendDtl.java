package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Hidden
@Schema(title="메시지 대상자 상세 수집 DTO")
public class COMsgSendDtl {
    @Schema(title="메시지 발송 순번")
    private Integer msgSendSeq;
    @Schema(title="발송정렬")
    private Integer sendOrd;
    @Schema(title="타입", description = "email,sms")
    private String type;
    @Schema(title="이메일")
    private String email;
    @Schema(title="휴대전화번호")
    private String hpNo;
    @Schema(title="note1 ")
    private String note1;
    @Schema(title="note2 ")
    private String note2;
    @Schema(title="note3 ")
    private String note3;
    @Schema(title="note4 ")
    private String note4;
    @Schema(title="note5 ")
    private String note5;
    @Schema(title="등록일시")
    private String regDtm;
}
