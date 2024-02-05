package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육 시험 마스터 DTO
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
@Schema(title = "교육 참여 응답 마스터")
public class EXGExamEdctnPtcptRspnMst extends BaseDTO {
    @Schema(title = "시험참여순번")
    private Integer examPtcptSeq;
    @Schema(title = "참여순번")
    @NotNull
    private Integer ptcptSeq;
    @Schema(title = "시험순번")
    @NotNull
    private Integer examSeq;
    @Schema(title = "회원순번")
    private Integer memSeq;
    @Schema(title = "점수")
    private Integer examScore;
    @Schema(title = "수료여부")
    private String cmptnYn;
    @Schema(title = "수료번호")
    private String crtfctNo;



    @Schema(title = "질문 답변")
    private List<EXGExamQstnRspnDtlDTO> qstnList;
    @Schema(title = "객관식 답변")
    private List<EXGExamEdctnPtcptMtlccRspnMst> mtlccList;
    @Schema(title = "주관식 답변")
    private List<EXGExamEdctnPtcptSbjctRspnMst> sbjctList;
}
