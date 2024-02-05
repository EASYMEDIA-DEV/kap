package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 교육참여자 상세 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.30  교육참여자 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBPtcptDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;


    @Schema(title = "참여순번", example = "숫자")
    @NotNull
    private Integer ptcptSeq;

    @Schema(title = "학습방식", example = "텍스트")
    private String stduyMthdCd;

    @Schema(title = "모집방법코드", example = "텍스트")
    private String rcrmtMthdCd;


    @Schema(title = "아이디", example = "텍스트")
    private String id;

    @Schema(title = "GPC 아이디", example = "텍스트")
    private String gpcId;

    @Schema(title = "회원순번", example = "텍스트")
    private Integer memSeq;

    @Schema(title = "이름", example = "텍스트")
    private String name;

    @Schema(title = "회사 회차", example = "텍스트")
    private String ctgryNm;

    @Schema(title = "부품사명", example = "텍스트")
    private String cmpnNm;

    @Schema(title = "사업자등록번호", example = "텍스트")
    private String ptcptBsnmNo;

    @Schema(title = "휴대폰번호", example = "텍스트")
    private String hpNo;

    @Schema(title = "이메일", example = "텍스트")
    private String email;

    @Schema(title = "교육상태코드", example = "텍스트")
    private String sttsCd;

    @Schema(title = "교육상태코드명", example = "텍스트")
    private String sttsCdNm;

    @Schema(title = "교육신청일시", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String eduDtm;

    @Schema(title = "교육 출석률", example = "xx")
    private String eduAtndc;

    @Schema(title = "시험점수", example = "")
    private Integer examScore;

    //2023.12.21 평가 응시 순번 추가
    @Schema(title = "평가응시순번", example = "")
    private Integer examPtcptSeq;

    @Schema(title = "수료여부", example = "Y/N")
    private String cmptnYn;

    @Schema(title = "수료여부 이전값", example = "Y/N")
    private String orgCmptnYn;


    @Schema(title = "오프라인 평가 여부", example = "Y/N")
    private String otsdExamPtcptYn;

    @Schema(title = "수료여부 변경여부", example = "Y/N")
    private String cmptnChangeYn;

    @Schema(title = "교육상태", example = "텍스트")
    private String eduStat;

    @Schema(title = "등록상태", example = "텍스트 이미있음(실패):F, 성공:S, M:양도이력 있음(마이페이지 교육양도에서만 사용), 연계필수과정 미수료로 신청불가:R")
    private String regStat;

    @Schema(title = "교육일자", example = "날짜 yyyy-MM-dd")
    private String edctnDt;

    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "출석일시", example = "날짜 yyyy-mm-dd HH:mm:ss")
    @Builder.Default
    private String atndcDtm = null;

    @Schema(title = "퇴실일시", example = "날짜 yyyy-mm-dd HH:mm:ss")
    @Builder.Default
    private String lvgrmDtm = null;

    @Schema(title = "출석시간", example = "날짜 시간:분")
    @Builder.Default
    private String atndcHour = null;

    @Schema(title = "퇴실일시", example = "날짜 시간:분")
    @Builder.Default
    private String lvgrmHour = null;

    @Schema(title = "부서코드", example = "")
    private String deptCd;

    @Schema(title = "부서코드명", example = "")
    private String deptCdNm;

    @Schema(title = "부서상세명", example = "")
    private String deptDtlNm;

    @Schema(title = "직급코드", example = "")
    private String pstnCd;

    @Schema(title = "직급코드명", example = "")
    private String pstnCdNm;

    @Schema(title = "직급명", example = "")
    private String pstnNm;

    @Schema(title = "강의번호", example = "숫자")
    private Integer lctrSeq;

    @Schema(title = "양도순번", example = "숫자")
    private Integer trnsfSeq;

    @Schema(title = "이전회원순번", example = "숫자")
    private Integer bfreMemSeq;

    @Schema(title = "이후회원순번", example = "숫자")
    private Integer aftrMemSeq;


    @Schema(title = "신청과목의 연계과정 개수", example = "숫자")
    private Integer relCnt;

    @Schema(title = "신청과목의 신청자의 연계과정 수료여부", example = "Y/N")
    private String relCmptnYn;





    private List<EBBPtcptDTO> list;


    @Schema(title = "비고", example = "텍스트")
    @Builder.Default
    private String etcNm = null;

    @Schema(title = "비고(원본)", example = "출석부 수정에서 비고변경유무를 판단하기 위한 항목")
    @Builder.Default
    private String orgEtcNm = null;

    @Schema(title = "출석부 수정시 열 삭제유무", example = "변경점이없는 출석부 내역 db수정 목록에서 제외용도")
    @Builder.Default
    private String deleteYn ="N";

    @Schema(title = "변경사유", example = "이유")
    private String rsn;

    @Schema(title = "엑셀다운로드 여부", example = "Y")
    private String excelYn;

    private List<EBBPtcptDTO> ptcptList;//교육 참여자 목록

    private List<EBBPtcptDTO> atndcList;//교육 참여자 출석목록

    @Schema(title = "대참 대상 GPC 아이디", example = "텍스트")
    private String aftGpcId;


}
