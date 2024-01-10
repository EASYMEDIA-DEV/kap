package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  설문응답관리
 *
 * @author 박준희
 * @since 2023.12.26
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.26  박준희         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "설문 마스터")
public class SVASurveyRspnMstInsertDTO extends BaseDTO {

    @Schema(title = "응답순번", example = "숫자")
    private Integer srvRspnSeq;

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "회원순번", example = "숫자")
    private Integer memSeq;

    @Schema(title = "참조코드")
    private String rfncCd;

    @Schema(title = "참조순번", example = "숫자")
    private Integer rfncSeq;

    @Schema(title = "이름")
    private String name;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "참여이름")
    private String ptcptName;
    @Schema(title = "참여전화번호")
    private String ptcptTelno;
    @Schema(title = "참여직급")
    private String ptcptPstn;
    @Schema(title = "참여이메일")
    private String ptcptEmail;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;

    @Schema(title = "설문응답 리스트")
    private List<SVASurveyQstnRspnDtlDTO> svSurveyQstnRspnDtlList;

}
