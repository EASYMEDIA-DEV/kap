package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 엑셀 출력 객체
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
 *   2023.12.25  교육차수 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdExcelDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "강사명1", example = "숫자")
    @Builder.Default
    private String isttrNm1 = "-";

    @Schema(title = "강사명2", example = "숫자")
    @Builder.Default
    private String isttrNm2 = "-";

    @Schema(title = "강사명3", example = "숫자")
    @Builder.Default
    private String isttrNm3 = "-";

    @Schema(title = "강사명4", example = "숫자")
    @Builder.Default
    private String isttrNm4 = "-";

    @Schema(title = "강사명5", example = "숫자")
    @Builder.Default
    private String isttrNm5 = "-";

    @Schema(title = "강사명6", example = "숫자")
    @Builder.Default
    private String isttrNm6 = "-";

    @Schema(title = "차수순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "과정분류1", example = "문자")
    @Builder.Default
    private String prntCdNm = "-";

    @Schema(title = "과정분류2", example = "문자")
    @Builder.Default
    private String ctgryCdNm = "-";

    @Schema(title = "과정명", example = "문자")
    @Builder.Default
    private String nm = "-";

    @Schema(title = "학습방식", example = "문자")
    @Builder.Default
    private String stduyMthdCdNm = "-";

    @Schema(title = "학습방식", example = "문자")
    @Builder.Default
    private String stduyTimeCdNm = "-";

    @Schema(title = "업종", example = "문자")
    @Builder.Default
    private String cbsnCdNm = "-";

    @Schema(title = "교육일", example = "문자")
    @Builder.Default
    private String stduyDdCdNm = "-";

    @Schema(title = "교육기간 시작일", example = "문자")
    @Builder.Default
    private String edctnStrtDtm = "-";

    @Schema(title = "교육기간 종료일", example = "문자")
    @Builder.Default
    private String edctnEndDtm = "-";

    @Schema(title = "교육장소", example = "문자")
    @Builder.Default
    private String placeNm = "-";

    @Schema(title = "협력기관", example = "문자")
    @Builder.Default
    private String cprtnInsttNm = "-";

    @Schema(title = "정원(명)", example = "숫자")
    private Integer  fxnumCnt;

    @Schema(title = "교육상태", example = "문자")
    @Builder.Default
    private String  edctnStatusNm = "-";

    @Schema(title = "신청인원", example = "숫자")
    @Builder.Default
    private String  accsCnt = "-";

    @Schema(title = "수료인원", example = "숫자")
    @Builder.Default
    private String  cmptnCnt = "-";

    @Schema(title = "참여율", example = "숫자")
    @Builder.Default
    private String  attedPer = "-";

    @Schema(title = "완성차", example = "숫자")
    @Builder.Default
    private String  c1 = "-";

    @Schema(title = "1차사", example = "숫자")
    @Builder.Default
    private String  c2 = "-";

    @Schema(title = "2차사", example = "숫자")
    @Builder.Default
    private String  c3 = "-";

    @Schema(title = "기타", example = "숫자")
    @Builder.Default
    private String  c4 = "-";

    @Schema(title = "부서별 인원 - 품질", example = "숫자")
    @Builder.Default
    private String  t1 = "-";

    @Schema(title = "부서별 인원 - R&D", example = "숫자")
    @Builder.Default
    private String  t2 = "-";

    @Schema(title = "부서별 인원 - 생산", example = "숫자")
    @Builder.Default
    private String  t3 = "-";

    @Schema(title = "부서별 인원 - 구매", example = "숫자")
    @Builder.Default
    private String  t4 = "-";

    @Schema(title = "부서별 인원 - 경영지원", example = "숫자")
    @Builder.Default
    private String  t5 = "-";

    @Schema(title = "부서별 인원 - 업체평가", example = "숫자")
    @Builder.Default
    private String  t6 = "-";

    @Schema(title = "부서별 인원 - 안전", example = "숫자")
    @Builder.Default
    private String t7 = "-";

    @Schema(title = "부서별 인원 - ESG" , example = "숫자")
    @Builder.Default
    private String  t8 = "-";

    @Schema(title = "부서별 인원 - 기타", example = "숫자")
    @Builder.Default
    private String  t9 = "-";

    @Schema(title = "부서별 인원 - 대표", example = "숫자")
    @Builder.Default
    private String  a1 = "-";

    @Schema(title = "직급별 인원 - 임원", example = "숫자")
    @Builder.Default
    private String  a2 = "-";

    @Schema(title = "직급별 인원 - 부장", example = "숫자")
    @Builder.Default
    private String  a3 = "-";

    @Schema(title = "직급별 인원 - 과장/차장타", example = "숫자")
    @Builder.Default
    private String  a4 = "-";

    @Schema(title = "급별 인원 - 사원/대리", example = "숫자")
    @Builder.Default
    private String  a5 = "-";

    @Schema(title = "직급별 인원 - 조장/반창(계장)", example = "숫자")
    @Builder.Default
    private String  a6 = "-";

    @Schema(title = "직급별 인원 - 기타", example = "숫자")
    @Builder.Default
    private String  a7 = "-";

    @Schema(title = "출석률", example = "숫자")
    @Builder.Default
    private String  a11 = "-";

    @Schema(title = "평가점수(점)", example = "숫자")
    @Builder.Default
    private String  avgScore = "-";

    @Schema(title = "종합 평균", example = "숫자")
    @Builder.Default
    private String  a12 = "-";

    @Schema(title = "전체(공통) ", example = "숫자")
    @Builder.Default
    private String a13 = "-";

    @Schema(title = "교육환경", example = "숫자")
    @Builder.Default
    private String a14 = "-";

    @Schema(title = "교육지원", example = "숫자")
    @Builder.Default
    private String a15 = "-";

    @Schema(title = "교육내용", example = "숫자")
    @Builder.Default
    private String a16 = "-";

    @Schema(title = "종합 평 ", example = "숫자")
    @Builder.Default
    private String a17 = "-";

    @Schema(title = "강사시간1", example = "숫자")
    @Builder.Default
    private String pmtC1 = "-";

    @Schema(title = "강사시간2", example = "숫자")
    @Builder.Default
    private String pmtC2 = "-";

    @Schema(title = "강사시간3", example = "숫자")
    @Builder.Default
    private String pmtC3 = "-";

    @Schema(title = "강사시간4", example = "숫자")
    @Builder.Default
    private String pmtC4 = "-";

    @Schema(title = "강사시간5", example = "숫자")
    @Builder.Default
    private String pmtC5 = "-";

    @Schema(title = "강사시간6", example = "숫자")
    @Builder.Default
    private String pmtC6 = "-";

    @Schema(title = "예산총계", example = "숫자")
    @Builder.Default
    private String pmtA0 = "-";

    @Schema(title = "부담금/대관비(원)", example = "숫자")
    @Builder.Default
    private String pmtA1 = "-";

    @Schema(title = "강사비(원)", example = "숫자")
    @Builder.Default
    private String pmtA2 = "-";

    @Schema(title = "교재비(원)", example = "숫자")
    @Builder.Default
    private String pmtA3 = "-";

    @Schema(title = "식대(원)", example = "숫자")
    @Builder.Default
    private String pmtA4 = "-";

    @Schema(title = "다과비(원)", example = "숫자")
    @Builder.Default
    private String pmtA5 = "-";

    @Schema(title = "소모품비(원)", example = "숫자")
    @Builder.Default
    private String pmtA6 = "-";

    @Schema(title = "발송비(원)", example = "숫자")
    @Builder.Default
    private String pmtA7 = "-";

    @Schema(title = "재료비(원)", example = "숫자")
    @Builder.Default
    private String pmtA8 = "-";

    @Schema(title = "집행비(원)", example = "숫자")
    @Builder.Default
    private String pmtA9 = "-";

    @Schema(title = "기타(원)", example = "숫자")
    @Builder.Default
    private String pmtA10 = "-";

    @Schema(title = "비고", example = "숫자")
    @Builder.Default
    private String pmtA11 = "-";

    @Schema(title = "지출총계", example = "숫자")
    @Builder.Default
    private String pmtB0 = "-";

    @Schema(title = "부담금/대관비(원)", example = "숫자")
    @Builder.Default
    private String pmtB1 = "-";

    @Schema(title = "강사비(원) ", example = "숫자")
    @Builder.Default
    private String pmtB2 = "-";

    @Schema(title = "교재비(원)", example = "숫자")
    @Builder.Default
    private String pmtB3 = "-";

    @Schema(title = "식대(원)", example = "숫자")
    @Builder.Default
    private String pmtB4 = "-";

    @Schema(title = "다과비(원)", example = "숫자")
    @Builder.Default
    private String pmtB5 = "-";

    @Schema(title = "소모품비(원)", example = "숫자")
    @Builder.Default
    private String pmtB6 = "-";

    @Schema(title = "발송비(원)", example = "숫자")
    @Builder.Default
    private String pmtB7 = "-";

    @Schema(title = "재료비(원)", example = "숫자")
    @Builder.Default
    private String pmtB8 = "-";

    @Schema(title = "집행비(원)", example = "숫자")
    @Builder.Default
    private String pmtB9 = "-";

    @Schema(title = "기타(원)", example = "숫자")
    @Builder.Default
    private String pmtB10 = "-";

    @Schema(title = "비고", example = "문자")
    @Builder.Default
    private String pmtB11 = "-";





    @Schema(title = "변경사유", example = "이유")
    private String rsn;

    @Schema(title = "엑셀다운로드 여부", example = "Y")
    private String excelYn;

    private List<EBBEpisdExcelDTO> list;
}
