package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육설문 객체
 *
 * @author 박준희
 * @since 2024.01.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.01.15  교육차수 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdSurveyDTO extends BaseDTO {


    @Schema(title = "교육차수순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    @Builder.Default
    private Integer episdOrd = 1;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "문항수", example = "숫자")
    private Integer qstnCnt;
    @Schema(title = "응답수", example = "숫자")
    private Integer rspnCnt;
    @Schema(title = "응답분", example = "숫자")
    private Integer rspnMm;

    @Schema(title = "내용")
    private String cntn;

    @Schema(title = "과정명")
    private String nm;

    @Schema(title = "회원번호", example = "숫자")
    private Integer memSeq;

    @Schema(title = "참여자순번", example = "숫자")
    private Integer ptcptSeq;


    @Schema(title = "카테고리 부모 코드 명 ", example = "")
    private String prntCdNm;

    @Schema(title = "카테고리 코드 명", example = "")
    private String ctgryCdNm;
    @Schema(title = "업종코드명", example = "코드명")
    private String cbsnCdNm;
    @Schema(title = "회차 내 모든 강사명", example = "이름")
    private String isttrGroupName;

}
