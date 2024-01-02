package com.kap.core.dto.eb.ebi;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.eb.ebb.EBBBdgetDTO;
import com.kap.core.dto.eb.ebb.EBBLctrDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  비회원 교육 과정 관리 DTO
 *
 * @author 장두석
 * @since 2023.12.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.15  장두석         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBINonMemberDTO extends BaseDTO {

    @Schema(title = "비회원 교육 순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "접수시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsStrtDtm;
    @Schema(title = "접수시작시간", example = "HH:mm")
    private String accsStrtHour;

    @Schema(title = "접수종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsEndDtm;
    @Schema(title = "접수종료시간", example = "HH:mm")
    private String accsEndHour;


    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "정원수", example = "숫자")
    private Integer fxnumCnt;

    @Schema(title = "정원제한여부", example = "Y = 정원 제한함 / N = 정원 제한 안함(체크박스 체크)")
    @Builder.Default
    private String fxnumImpsbYn = "Y";

    @Schema(title = "정원수포화상태 여부 S : 포화X, F:포화", example = "숫자")
    private String fxnumStta;


    @Schema(title = "담당자명", example = "이름")
    private String picNm;

    @Schema(title = "담당자이메일", example = "이메일")
    private String picEmail;

    @Schema(title = "담당자전화번호", example = "xxx-xxxx-xxxx")
    private String picTelNo;

    @Schema(title = "교육안내문", example = "숫자")
    private Integer edctnNtctnFileSeq;

    @Schema(title = "교육장소순번", example = "숫자")
    private Integer placeSeq;

    @Schema(title = "교육장소명", example = "")
    private String placeNm;

    @Schema(title = "교육 참여수", example = "숫자")
    private Integer edctnMemCnt;

    @Schema(title = "과정 소개", example = "")
    private String itrdcCntn;

    @Schema(title = "학습 목표", example = "")
    private String stduyTrgtCntn;

    @Schema(title = "학습대상 코드", example = "텍스트")
    private String targetCd;

    @Schema(title = "학습대상 코드 - 기타", example = "텍스트")
    private String etcNm;

    @Schema(title = "학습준비물명", example = "텍스트")
    private String stduySuplsNm;

    @Schema(title = "PC학습내용", example = "텍스트")
    private String pcStduyCntn;

    @Schema(title = "모바일학습내용", example = "텍스트")
    private String mblStduyCntn;

    @Schema(title = "썸네일파일순번", example = "숫자")
    private Integer thnlFileSeq;


    @Schema(title = "사용여부", example = "Y/N")
    private String expsYn;

    @Schema(title = "카테고리 부모코드번호", example = "숫자")
    private String parntSeq;

    /*@Schema(title = "평가여부", example = "Y/N")
    private String jdgmtYn;*/



    @Schema(title = "카테고리 부모 코드 ", example = "")
    private String prntCd;

    @Schema(title = "카테고리 부모 코드 명 ", example = "")
    private String prntCdNm;

    @Schema(title = "카테고리 코드", example = "")
    private String ctgryCd;

    @Schema(title = "카테고리 코드 명", example = "")
    private String ctgryCdNm;

    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;

    @Schema(title = "교육과정 요약", example = "텍스트")
    private String smmryNm;

    /*@Schema(title = "학습방식 코드", example = "")
    private String stduyMthdCd;

    @Schema(title = "학습방식 코드 명", example = "")
    private String stduyMthdCdNm;*/

    @Schema(title = "학습일 코드", example = "")
    private String stduyDdCd;

    @Schema(title = "학습일 명", example = "숫자")
    private String stduyDdCdNm;

    @Schema(title = "학습시간 코드", example = "")
    private String stduyTimeCd;

    @Schema(title = "학습시간 코드 명", example = "숫자")
    private String stduyTimeCdNm;

    @Schema(title = "접수상태", example = "접수대기/접수중/마감")
    private String accsStatus;

    @Schema(title = "교육상태", example = "교육대기/교육중/마감")
    private String edctnStatus;

    @Schema(title = "접수상태 명", example = "접수대기/접수중/마감")
    private String accsStatusNm;

    @Schema(title = "교육상태 명", example = "교육대기/교육중/마감")
    private String edctnStatusNm;

    @Schema(title = "강사명", example = "이름")
    private String isttrName;

    @Schema(title = "회차정렬", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "강사수(외 x명)", example = "숫자")
    private String isttrOutCnt;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "신청자수", example = "숫자")
    private String accsCnt;

    /*@Schema(title = "모집방법코드명", example = "숫자")
    private String rcrmtMthdCdNm;*/

    @Schema(title = "복사여부", example = "Y/N")
    @Builder.Default
    private String copyYn = "N";

    /*@Schema(title = "회차번호 공통코드", example = "숫자")
    private String roundCd;*/

    /*@Schema(title = "회차번호 강의순번", example = "숫자")
    private Integer lctrSeq;*/

    @Schema(title = "협업기관명 코드", example = "숫자")
    private Integer cprtnInsttSeq;

    @Schema(title = "협업기관명", example = "텍스트")
    private String cprtnInsttNm;

    @Schema(title = "예산지출 마감여부", example = "마감:Y, 미마감:N, 교육취소:C")
    @Builder.Default
    private String bdgetExpnsYn = "N";

    @Schema(title = "예산지출 마감여부", example = "마감:Y, 미마감:N, 교육취소:C")
    private String bdgetExpnsNm;



    @Schema(title = "지출협업기관번호", example = "텍스트")
    private Integer expnsCprtnInsttSeq;

    @Schema(title = "지출협업기관명", example = "텍스트")
    private String expnsCprtnInsttNm;

    @Schema(title = "지출금액", example = "금액")
    private Integer expnsPmt;



    @Schema(title = "비회원 교육 참여 순번", example = "숫자")
    private Integer ptcptSeq;

    @Schema(title = "비회원 교육 신청자 사업자 번호", example = "")
    private String ptcptBsnmNo;

    @Schema(title = "비회원 교육 신청자 회사명", example = "")
    private String ptcptCmpnNm;

    @Schema(title = "비회원 교육 신청자명", example = "")
    private String name;

    @Schema(title = "비회원 교육 신청자 이메일", example = "")
    private String email;

    @Schema(title = "비회원 교육 신청자 부서 코드", example = "")
    private String deptCd;

    @Schema(title = "비회원 교육 신청자 부서명", example = "")
    private String deptCdNm;

    @Schema(title = "비회원 교육 신청자 부서 상세명", example = "")
    private String deptDtlNm;

    @Schema(title = "비회원 교육 신청자 직급 코드", example = "")
    private String pstnCd;

    @Schema(title = "비회원 교육 신청자 직급명", example = "")
    private String pstnCdNm;

    @Schema(title = "비회원 교육 신청자 직급 상세", example = "직급 코드가 기타시 입력받는 값")
    private String pstnNm;

    @Schema(title = "비회원 교육 신청자 핸드폰 번호", example = "")
    private String hpNo;

    @Schema(title = "비회원 교육 신청 상태 코드", example = "")
    private String sttsCd;

    @Schema(title = "신청 중복 여부", example = "")
    private String regStat;



    //엑셀 필요 값
    @Schema(title = "강사명1", example = "")
    @Builder.Default
    private String isttrNm1 = "-";

    @Schema(title = "강사명2", example = "")
    @Builder.Default
    private String isttrNm2 = "-";

    @Schema(title = "강사명3", example = "")
    @Builder.Default
    private String isttrNm3 = "-";

    @Schema(title = "강사명4", example = "")
    @Builder.Default
    private String isttrNm4 = "-";

    @Schema(title = "강사명5", example = "")
    @Builder.Default
    private String isttrNm5 = "-";

    @Schema(title = "강사명6", example = "")
    @Builder.Default
    private String isttrNm6 = "-";

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



    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "게시판 목록", example = "DTO의 리스트")
    private List<EBINonMemberDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;

    private List<String> ptcptSeqList;

    private List<String> expsYnList;

    private List<String> accsStatusList;

    private List<String> edctnStatusList;

    private List<String> rcrmtMthdCdList;

    private List<String> targetCdList;

    private List<COFileDTO> pcThumbList;


    private String srchLayer;

    private List<EBINonMemberDTO> ptcptList;//참여자 목록

    private List<EBINonMemberDTO> atndcList;//참여자 목록

    private List<EBBLctrDTO> lctrList;//온라인강의 목록

    private List<EBBisttrDTO> isttrSeqList;//강사목록

    private List<EBBBdgetDTO> bdgetList;//예산지출내역 목록

    private List<EBINonMemberDTO> trgtDtlList;//교육과정대상 상세 목록

    @Schema(title = "엑셀다운로드 여부", example = "Y")
    private String excelYn;

    @Schema(title = "변경사유", example = "이유")
    private String rsn;

}
