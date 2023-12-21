package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 *  교육 시험 질문 답변 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
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
@Schema(title = "교육 시험 질문 답변 상세")
public class EXGExamExmplRspnDtlDTO extends BaseDTO  {
    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;
    @Schema(title = "질문순번", example = "숫자")
    private Integer qstnSeq;
    @Schema(title = "답변순번", example = "숫자")
    private Integer examPtcptSeq;
    @Schema(title = "참여순번", example = "숫자")
    private Integer ptcptSeq;
    @Schema(title = "보기순번", example = "숫자")
    private Integer exmplSeq;
    @Schema(title = "주관식 답변", example = "")
    private String sbjctRply;
    @Schema(title = "정답여부", example = "")
    private String canswYn;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;
    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;
    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;
    @Schema(title = "수정ID")
    private String modId;
}
