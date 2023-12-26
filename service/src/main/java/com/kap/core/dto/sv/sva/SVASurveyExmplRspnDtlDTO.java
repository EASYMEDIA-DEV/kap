package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 *  설문조사관리
 *
 * @author 박준희
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  박준희         최초 생성
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
@Schema(title = "설문관리 질문 보기 상세")
public class SVASurveyExmplRspnDtlDTO extends BaseDTO  {


    @Schema(title = "객관식순번", example = "숫자")
    private Integer mtlccRspnSeq;

    @Schema(title = "주관식순번", example = "숫자")
    private Integer sbjctRspnSeq;

    @Schema(title = "보기순번", example = "숫자")
    private Integer exmplSeq;

    @Schema(title = "질문순번", example = "숫자")
    private Integer qstnSeq;

    @Schema(title = "설문응답순번", example = "숫자")
    private Integer srvRspnSeq;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "주관식답변")
    private String sbjctRply;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;
}
