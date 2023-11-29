package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
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
@Schema(title = "교육 시험 질문 상세")
public class EXGExamQstnDtlDTO extends BaseDTO  {
    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;
    @Schema(title = "질문순번", example = "숫자")
    private Integer qstnSeq;

    @Schema(title = "설문유형코드", example = "EXG_A,EXG_B,EXG_C,EXG_D")
    @NotNull
    private String srvTypeCd;

    @Schema(title = "질문명", example = "")
    @NotNull
    private String qstnNm;

    @Schema(title = "질문순서", example = "0부터")
    @NotNull
    private Integer qstnOrd;

    @Schema(title = "배점", example = "")
    @NotNull
    private Integer scord;

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

    @Schema(title = "교육 시험 질문 보기 리스트")
    private List<EXGExamExmplDtlDTO> exExamExmplDtlList;

    @Schema(title = "사용자 시험 질문 정답 보기(채점)", description = "group_concat(exmpl_seq SEPARATOR ',')")
    @Hidden
    private String exmplCansw;
}
