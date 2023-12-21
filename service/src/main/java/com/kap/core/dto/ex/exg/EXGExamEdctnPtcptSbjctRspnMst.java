package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@Schema(title = "교육 참여 주관식 응답 마스터")
public class EXGExamEdctnPtcptSbjctRspnMst {
    @Schema(title = "시험참여순번")
    private Integer examPtcptSeq;
    @Schema(title = "참여순번")
    private Integer ptcptSeq;
    @Schema(title = "시험순번")
    private Integer examSeq;
    @Schema(title = "질문유형")
    private String srvTypeCd;
    @Schema(title = "질문순번")
    private Integer qstnSeq;
    @Schema(title = "주관식답변")
    private String sbjctRply;
    @Schema(title = "정답여부")
    private String canswYn;
    @Schema(title = "점수")
    private Integer scord;
}
