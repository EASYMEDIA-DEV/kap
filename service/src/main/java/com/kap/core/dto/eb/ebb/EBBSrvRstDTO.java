package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 강사목록 객체
 *
 * @author 김학규
 * @since 2023.11.24
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.24  교육강의 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBSrvRstDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "종합평균", example = "숫자")
    private Float vrllAvrg;

    @Schema(title = "전체공통점수", example = "숫자")
    private Float ttlCoScore;

    @Schema(title = "교육환경점수", example = "숫자")
    private Float edctnEnvrnmtScore;

    @Schema(title = "교육지원점수", example = "숫자")
    private Float edctnSpprtScore;

    @Schema(title = "교육내용점수", example = "숫자")
    private Float edctnCntnScore;

    @Schema(title = "강사점수", example = "숫자")
    private Float isttrScore;


    @Schema(title = "부서별 인원 - 품질", example = "숫자")
    private Integer t1;

    @Schema(title = "부서별 인원 - R&D", example = "숫자")
    private Integer t2;

    @Schema(title = "부서별 인원 - 생산", example = "숫자")
    private Integer t3;

    @Schema(title = "부서별 인원 - 구매", example = "숫자")
    private Integer t4;

    @Schema(title = "부서별 인원 - 경영지원", example = "숫자")
    private Integer t5;

    @Schema(title = "부서별 인원 - 업체평가", example = "숫자")
    private Integer t6;

    @Schema(title = "부서별 인원 - 안전", example = "숫자")
    private Integer t7;

    @Schema(title = "부서별 인원 - ESG", example = "숫자")
    private Integer t8;

    @Schema(title = "부서별 인원 - 기타", example = "숫자")
    private Integer t9;

    @Schema(title = "직급별 인원 - 대표", example = "숫자")
    private Integer a1;

    @Schema(title = "직급별 인원 - 임원", example = "숫자")
    private Integer a2;

    @Schema(title = "직급별 인원 - 부장", example = "숫자")
    private Integer a3;

    @Schema(title = "직급별 인원 - 과장/차장", example = "숫자")
    private Integer a4;

    @Schema(title = "직급별 인원 - 사원/대리", example = "숫자")
    private Integer a5;

    @Schema(title = "직급별 인원 - 조장/반창(계장)", example = "숫자")
    private Integer a6;

    @Schema(title = "직급별 인원 - 기타", example = "숫자")
    private Integer a7;

}
