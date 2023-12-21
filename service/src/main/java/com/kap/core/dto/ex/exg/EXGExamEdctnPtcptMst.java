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
@Schema(title = "교육 참여 마스터")
public class EXGExamEdctnPtcptMst {
    @Schema(title = "참여순번")
    private Integer ptcptSeq;
    @Schema(title = "교육순번")
    private Integer edctnSeq;
    @Schema(title = "회차년도")
    private Integer episdYear;
    @Schema(title = "회차정렬")
    private Integer episdOrd;
    @Schema(title = "회원순번")
    private Integer memSeq;
    @Schema(title = "시험순번")
    private Integer examSeq;
    @Schema(title = "명")
    private String nm;
    @Schema(title = "업종코드")
    private String cbsnCd;
    @Schema(title = "업종코드명")
    private String cbsnCdNm;
    @Schema(title = "카테고리코드")
    private String ctgryCd;
    @Schema(title = "카테고리코드명")
    private String ctgryCdNm;
    @Schema(title = "강사명")
    private String isttrNm;
    @Schema(title = "질문수")
    private Integer qstnCnt;
    @Schema(title = "평가 참여수")
    private Integer ptcptCnt;
    @Schema(title = "시험개요")
    private String smmryCntn;
    @Schema(title = "응시자 ID")
    private String id;
    @Schema(title = "응시자 이름")
    private String name;

    @Schema(title = "자동화 여부")
    private String cmptnAutoYn;
    @Schema(title = "수료 출석 코드")
    private String cmptnStndCd;
    @Schema(title = "수료 출석 코드 명")
    private Integer cmptnStndCdNm;
    @Schema(title = "수료 평가 코드")
    private String cmptnJdgmtCd;
    @Schema(title = "수료 평가 코드 명")
    private Integer cmptnJdgmtCdNm;
    @Schema(title = "시험점수")
    private Integer examScore;
}
