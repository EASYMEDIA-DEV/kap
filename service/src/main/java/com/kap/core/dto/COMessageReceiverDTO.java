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
@Schema(title="이메일 및 SMS 대상자 DTO")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Hidden
public class COMessageReceiverDTO {
    @Schema(title="메일 및 SMS 대상자 명")
    private String name;
    @Schema(title="메일 대상자 이메일")
    private String email;
    @Schema(title="SMS 대상자 전화번호")
    private String mobile;
    @Schema(title="치환 문자 1")
    private String note1;
    @Schema(title="치환 문자 2")
    private String note2;
    @Schema(title="치환 문자 3")
    private String note3;
    @Schema(title="치환 문자 4")
    private String note4;
    @Schema(title="치환 문자 5")
    private String note5;
}
