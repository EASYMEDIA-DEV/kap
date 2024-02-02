package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 객체
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
 *   2023.11.15  교육차수 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdDTO extends BaseDTO {


    @Schema(title = "교육차수순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    @Builder.Default
    private Integer episdOrd = 1;

    //수정시에만 사용
    @Schema(title = "회차년도(수정전)", example = "yyyy")
    private Integer orgEpisdYear;

    @Schema(title = "회차정렬(수정전)", example = "숫자")
    @Builder.Default
    private Integer orgEpisdOrd = 1;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "업종코드", example = "코드")
    private String cbsnCd;

    @Schema(title = "업종코드명", example = "코드명")
    private String cbsnCdNm;


    @Schema(title = "접수시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsStrtDtm;
    @Schema(title = "접수시작일", example = "mm.dd")
    private String accsStrtDt;
    @Schema(title = "접수시작년도", example = "yyyy")
    private String accsStrtYear;

    @Schema(title = "접수시작시간", example = "HH:mm")
    private String accsStrtHour;

    @Schema(title = "접수종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsEndDtm;
    @Schema(title = "접수종료일", example = "mm.dd")
    private String accsEndDt;
    @Schema(title = "접수종료년도", example = "yyyy")
    private String accsEndYear;
    @Schema(title = "접수종료시간", example = "HH:mm")
    private String accsEndHour;

    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;
    @Schema(title = "교육시작일", example = "mm.dd")
    private String edctnStrtDt;
    @Schema(title = "교육시작년도", example = "year")
    private String edctnStrtYear;

    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;
    @Schema(title = "교육종료일", example = "mm.dd")
    private String edctnEndDt;
    @Schema(title = "교육종료년도", example = "year")
    private String edctnEndYear;

    @Schema(title = "정원수", example = "숫자")
    private Integer fxnumCnt;

    @Schema(title = "정원제한여부", example = "Y = 정원 제한함 / N = 정원 제한 안함(체크박스 체크)")
    @Builder.Default
    private String fxnumImpsbYn = "Y";

    @Schema(title = "정원수포화상태 여부 S : 포화X, F:포화", example = "숫자")
    private String fxnumStta;


    @Schema(title = "모집방법코드", example = "숫자")
    private String rcrmtMthdCd;

    @Schema(title = "담당자명", example = "이름")
    private String picNm;

    @Schema(title = "담당자이메일", example = "이메일")
    private String picEmail;

    @Schema(title = "담당자전화번호", example = "xxx-xxxx-xxxx")
    private String picTelNo;

    @Schema(title = "교육안내문", example = "숫자")
    private Integer edctnNtctnFileSeq;

    @Schema(title = "교육안내문 실제 파일명", example = "파일명")
    private String edctnNtctnFileNm;

    @Schema(title = "교육안내문 순번", example = "숫자")
    private Integer fileOrd;

    @Schema(title = "교육장소순번", example = "숫자")
    private Integer placeSeq;

    @Schema(title = "교육장소명", example = "")
    private String placeNm;

    @Schema(title = "교육장소 - 기본주소", example = "")
    private String bscAddr;

    @Schema(title = "교육장소 - 상세주소", example = "")
    private String dtlAddr;

    @Schema(title = "교육장소 - 우편번호", example = "")
    private String zipcode;

    @Schema(title = "교육장소 - 대표전화번호", example = "")
    private String rprsntTelNo;


    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "설문명", example = "숫자")
    private String srvNm;

    @Schema(title = "설문시작일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String srvStrtDtm;

    @Schema(title = "설문종료일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String srvEndDtm;

    @Schema(title = "설문 참여수", example = "숫자")
    private Integer srvMemCnt;

    @Schema(title = "교육 참여수", example = "숫자")
    private Integer edctnMemCnt;



    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;

    @Schema(title = "시험명", example = "텍스트")
    private String examNm;

    @Schema(title = "시험유형", example = "텍스트")
    private String typeNm;


    @Schema(title = "시험시작일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String examStrtDtm;

    @Schema(title = "시험종료일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String examEndDtm;


    @Schema(title = "수료자동여부", example = "Y/N")
    private String cmptnAutoYn;


    @Schema(title = "사용여부", example = "Y/N")
    private String expsYn;

    @Schema(title = "카테고리 부모코드번호", example = "숫자")
    private String parntSeq;

    @Schema(title = "평가여부", example = "Y/N")
    private String jdgmtYn;



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

    @Schema(title = "교육과정 요약명", example = "텍스트")
    private String smmryNm;



    @Schema(title = "학습방식 코드", example = "")
    private String stduyMthdCd;

    @Schema(title = "학습방식 코드 명", example = "")
    private String stduyMthdCdNm;

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

    @Schema(title = "회차 내 모든 강사명", example = "이름")
    private String isttrGroupName;


    @Schema(title = "회차정렬", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "강사수(외 x명)", example = "숫자")
    private String isttrOutCnt;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "신청자수", example = "숫자")
    private String accsCnt;

    @Schema(title = "모집방법코드명", example = "숫자")
    private String rcrmtMthdCdNm;

    @Schema(title = "복사여부", example = "Y/N")
    @Builder.Default
    private String copyYn = "N";

    @Schema(title = "회차번호 공통코드", example = "숫자")
    private String roundCd;

    @Schema(title = "회차번호 강의순번", example = "숫자")
    private Integer lctrSeq;

    @Schema(title = "협업기관명", example = "숫자")
    private Integer cprtnInsttSeq;

    @Schema(title = "협업기관명", example = "텍스트")
    private String cprtnInsttNm;

    @Schema(title = "오프라인평가여부", example = "Y/N")
    private String otsdExamPtcptYn;

    @Schema(title = "교육차수 상태(개강, 종강)", example = "Y/N")
    @Builder.Default
    private String edctnSttsCd = "EDCTN_STTS_CD01";

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

    @Schema(title = "만족도결과 순번", example = "번호")
    private Integer rsltSeq;


    @Schema(title = "교육완료여부", example = "Y/N")
    @Builder.Default
    private String edctnCmpltnYn = "";

    @Schema(title = "검색 정렬조건", example = "숫자")
    @Builder.Default
    private Integer srchOrder = 1;


    @Schema(title = "파일경로", example = "텍스트")
    private String webPath;

    @Schema(title = "파일설명(alt값)", example = "텍스트")
    private String fileDsc;

    @Schema(title = "접수출력순서", example = "숫자")
    private Integer accsStatusOrder;

    @Schema(title = "과정연계순서", example = "텍스트")
    private String cnnctCd;

    @Schema(title = "회원번호", example = "숫자")
    private Integer memSeq;

    @Schema(title = "차수의 교육상태코드", example = "텍스트")
    private String sttsCd;

    @Schema(title = "교육상태코드명", example = "텍스트")
    private String sttsCdNm;

    @Schema(title = "수료여부", example = "텍스트")
    private String cmptnYn;

    @Schema(title = "수료일", example = "yyyy-mm-dd HH:mm:ss")
    private String cmptnDtm;

    @Schema(title = "참여자의 교육상태", example = "텍스트")
    private String eduStat;

    @Schema(title = "교육 참여일", example = "yyyy-mm-dd HH:mm:ss")
    private String ptcptDtm;


    @Schema(title = "교육참여자 번호", example = "숫자")
    private Integer ptcptSeq;

    @Schema(title = "수정가능여부", example = "Y/N")
    private String modifyYn;

    @Schema(title = "교육진행중 여부", example = "Y/N")
    private String eduIng;

    @Schema(title = "GPC여부", example = "Y/N")
    private String gpcYn;

    @Schema(title = "GPC ID", example = "텍스트")
    private String gpcId;

    @Schema(title = "평가점수", example = "텍스트")
    private Integer examScore;

    @Schema(title = "양도 여부", example = "Y/N")
    private String trnsfYn;

    @Schema(title = "양도전 회원번호", example = "숫자")
    private Integer bfreMemSeq;

    @Schema(title = "설문참여여부", example = "Y/N")
    private String srvYn;

    @Schema(title = "설문 참여한 날짜", example = "yyyy-mm-dd HH:mm:ss")
    private String srvDtm;

    @Schema(title = "참여자 시험참여번호", example = "Y/N")
    private String examPtcptSeq;

    @Schema(title = "참여자 시험참여날짜", example = "yyyy-mm-dd HH:mm:ss")
    private String examPtcptDtm;

    @Schema(title = "수료기준코드명", example = "텍스트")
    private String cmptnStndCdNm;

    @Schema(title = "수료평가코드명", example = "텍스트")
    private String cmptnJdgmtCdNm;

    @Schema(title = "출석률", example = "텍스트")
    private Integer atndcInfo;

    @Schema(title = "참여자강의 진도율", example = "텍스트")
    private Integer ptcptInfo;

    @Schema(title = "과정강의개수", example = "텍스트")
    private Integer lcrtInfo;

    @Schema(title = "참여자의 수료여부", example = "텍스트")
    private String ptcptCmtnYn;

    @Schema(title = "자격증 연계코드", example = "텍스트")
    private String lcnsCnnctCd;

    @Schema(title = "수료증번호", example = "텍스트")
    private String crtfctNo;




    //오프라인여부

    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "게시판 목록", example = "DTO의 리스트")
    private List<EBBEpisdDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;

    private List<String> expsYnList;

    private List<String> accsStatusList;

    private List<String> edctnStatusList;

    private List<String> rcrmtMthdCdList;

    private List<String> accsStatusOrderList;

    private List<String> cmptnYnList;

    private List<String> sttsCdList;





    private String sqnm;
    private String sqscore;

    @Schema(title = "차수변경용 차수목록 플래그", example = "Y/N")
    @Builder.Default
    private String changeListYn = "N";

    @Schema(title = "마이페이지 서브 메인에서만 사용하는 조건절", example = "Y/N")
    @Builder.Default
    private String mypageMainYn = "N";

    @Schema(title = "마이페이지에서만 사용하는 조건절", example = "Y/N")
    @Builder.Default
    private String mypageYn = "N";

    @Schema(title = "마이페이지 교육신청내역 교육/방문교육 구분자 교육과정:E, 방문교육:V", example = "E/V")
    private String eduGubun;

    @Schema(title = "관리자-차수관리 출석부팝업에서 사용하는 조건절, 양도된 목록은 제외하기 위함", example = "Y/N")
    @Builder.Default
    private String atndcYn = "N";

    @Schema(title = "관리자-과정관리 상세의 회차목록여부", example = "Y/N")
    @Builder.Default
    private String couseEpisdYn = "N";

    @Schema(title = "마이페이지 교육신청내역 방문교육 목록 조회용 교육희망일", example = "E/V")
    private String hopeDt;

    @Schema(title = "마이페이지 교육신청내역 방문교육 목록 조회용 교육인원")
    private Integer ptcptCnt;

    private String srchLayer;
    private List<EBBPtcptDTO> ptcptList;//참여자 목록

    private List<String> episdSeqList; //차수 번호 리스트

    private List<EBBLctrDTO> lctrList;//온라인강의 목록

    private List<EBBisttrDTO> isttrSeqList;//강사목록

    private List<EBBBdgetDTO> bdgetList;//예산지출내역 목록

    @Schema(title = "엑셀다운로드 여부", example = "Y")
    private String excelYn;

    @Schema(title = "변경사유", example = "이유")
    private String rsn;

    @Schema(title = "사용자 메인여부", example = "Y/N")
    @Builder.Default
    private String mainYn = "N";

    @Schema(title = "회원/비회원 구분 코드", example = "E/N")
    private String nonMemberCd;

    @Schema(title = "사용자 교육 신청 목록 페이지 여부", example = "Y/N")
    private String applyListYn;

}
