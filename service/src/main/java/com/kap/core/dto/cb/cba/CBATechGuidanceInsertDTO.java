package com.kap.core.dto.cb.cba;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  컨설팅 기술 지도 DTO
 *
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  임서화         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class CBATechGuidanceInsertDTO extends BaseDTO {

    // 신청자 정보
    @Schema(title = "신청자 이름")
    private String name;
    @Schema(title = "신청자 순번")
    private String memSeq;
    private String appctnFidCd;

    @Schema(title = "이관 전 신청자 순번")
    private String bfreMemSeq;
    @Schema(title = "이관 후 신청자 순번")
    private String aftrMemSeq;
    @Schema(title = "이관 후 신청자 이름")
    private String afName;
    @Schema(title = "이관 후 신청자 아이디")
    private String afId;
    @Schema(title = "이관 전 신청자 이름")
    private String bfName;
    @Schema(title = "이관 전 신청자 아이디")
    private String bfId;
    @Schema(title = "이관여부", example = "Y")
    private String trnsfYn;
    @Schema(title = "탈락 이유")
    private String rsltCntn;

    @Schema(title = "신청자 이메일")
    private String email;
    @Schema(title = "신청자 아이디")
    private String id;
    @Schema(title = "신청자 부서 코드")
    private String deptCd;
    @Schema(title = "신청자 부서 이름")
    private String deptNm;
    @Schema(title = "신청자 부서명")
    private String deptDtlNm;
    @Schema(title = "신청자 직급")
    private String pstnCd;
    @Schema(title = "신청자 직급코드명")
    private String pstnCdNm;
    @Schema(title = "신청자 직급명")
    private String pstnNm;
    @Schema(title = "신청자 전화번호")
    private String telNo;
    @Schema(title = "신청자 핸드폰 전화번호")
    private String hpNo;
    @Schema(title = "이관 순번")
    private Integer trnsfSeq;

    // 부품사 정보
    @Schema(title = "컨설팅 순번")
    private Integer cnstgSeq;
    @Schema(title = "부품사 이름")
    private String cmpnNm;
    @Schema(title = "대표 이름")
    private String rprsntNm;
    @Schema(title = "회사 코드")
    private String cmpnCd;
    @Schema(title = "회사약식명")
    private String cmpnNfrmlNm;
    @Schema(title = "신청사업자번호")
    private String bsnmNo;
    private String appctnBsnmNo;
    @Schema(title = "신청회원순번")
    private Integer appctnMemNo;
    @Schema(title = "업종")
    private String cbsnCd;
    @Schema(title = "컨설팅 코드")
    private String cnstgCd;
    @Schema(title = "컨설팅 구분")
    private String cnstgNm;
    @Schema(title = "기타명")
    private String etcNm;
    @Schema(title = "구분")
    private String ctgryCd;
    @Schema(title = "설립일자")
    private String stbsmDt;
    @Schema(title = "부품사 전화번호")
    private String cmpnTelNo;
    @Schema(title = "부품사 우편번호")
    private String zipcode;
    @Schema(title = "부품사 기본 주소")
    private String bscAddr;
    @Schema(title = "부품사 상세 주소")
    private String dtlAddr;
    @Schema(title = "매출액")
    private Integer slsPmt;
    @Schema(title = "매출년도")
    private Integer slsYear;
    @Schema(title = "직원수")
    private Integer  mpleCnt;
    @Schema(title = "주생산품1")
    private String mjrPrdct1;
    @Schema(title = "주생산품2")
    private String mjrPrdct2;
    @Schema(title = "주생산품3")
    private String mjrPrdct3;
    @Schema(title = "부품사 구분")
    private String ctgryNm;
    @Schema(title = "규모")
    private String sizeNm;

    // 5스타 폼
    @Schema(title = "품질5스타")
    private String qlty5StarCd;
    @Schema(title = "품질5스타년도")
    private Integer  qlty5StarYear;
    @Schema(title = "납입5스타")
    private String pay5StarCd;
    @Schema(title = "납입5스타년도")
    private Integer  pay5StarYear;
    @Schema(title = "기술5스타")
    private String tchlg5StarCd;
    @Schema(title = "기술5스타년도")
    private Integer  tchlg5StarYear;

    // SQ 정보
    @Schema(title = "SQ 업종")
    private String nm;
    private String nm1;
    private String nm2;
    private String nm3;

    @Schema(title = "SQ업종 순번")
    private String cbsnSeq ="";

    @Schema(title = "SQ 점수")
    private Double score;
    private Double score1;
    private Double score2;
    private Double score3;

    @Schema(title = "SQ 평가년도")
    private Integer year;
    private Integer year1;
    private Integer year2;
    private Integer year3;

    @Schema(title = "인증주관사")
    private String crtfnCmpnNm;
    private String crtfnCmpnNm1;
    private String crtfnCmpnNm2;
    private String crtfnCmpnNm3;

    @Schema(title = "담당위원")
    private String  cmssrNm;
    @Schema(title = "초도방문일")
    private String vstDt;
    private List<CBATechGuidanceInsertDTO> list;
    @Schema(title = "킥오프일")
    private String guideKickfDt;

    // SQ 리스트
    private List<String> sqInfoList;
    // SQ 리스트1
    private List<String> sqInfoList1;
    // SQ 리스트2
    private List<String> sqInfoList2;
    // SQ 리스트3
    private List<String> sqInfoList3;

    @Schema(title = "사업년도")
    private Integer bsnYear;
    @Schema(title = "신청회원순번")
    private String appctnMemSeq;
    @Schema(title = "신청일자")
    private String appctnDt;
    @Schema(title = "회사규모코드")
    private String sizeCd;

    @Schema(title = "초도방문자료")
    private Integer initVstFileSeq;
    @Schema(title = "초도방문ord")
    private Integer initVstFileOrd;
    @Schema(title = "킥오프자료")
    private Integer kickfFileSeq;
    @Schema(title = "킥오프파일ord")
    private Integer kickfFileOrd;
    @Schema(title = "랩업자료")
    private Integer lvlupFileSeq;
    @Schema(title = "렙업자료ord")
    private Integer lvlupFileOrd;
    @Schema(title = "렙업일")
    private String lvlupDt;
    @Schema(title = "기타사업자료")
    private Integer etcFileSeq;
    @Schema(title = "지도착수일")
    private String guideBgnDt;

    @Schema(title = "회사주소동일여부")
    private String cmpnAddrSameYn;
    @Schema(title = "공장우편번호")
    private String fctryZipcode;
    @Schema(title = "공장기본주소")
    private String fctryBscAddr;
    @Schema(title = "공장상세주소")
    private String fctryDtlAddr;
    @Schema(title = "첫번째 지역 코드")
    private String firstRgnsCd;
    @Schema(title = "첫번째 지역 이름")
    private String firstRgnsNm;
    @Schema(title = "두번째 지역 코드")
    private String scndRgnsCd;
    @Schema(title = "두번째 지역 이름")
    private String scndRgnsNm;
    @Schema(title = "신청분야")
    private String appctnFldNm;
    @Schema(title = "방문횟수")
    private String vstCnt;

    @Schema(title="고객사 비율 순번")
    private Integer cmpnDlvrySeq;
    @Schema(title="거래처별 매출 비중 업체명")
    private String dlvryCmpnNm;
    @Schema(title="거래처별 매출비중")
    private String dlvryRate;
    @Schema(title="완성차 의존율 순번")
    private Integer dpndnSeq;
    @Schema(title="완성차 의존율 업체명")
    private String dpndnCmpnNm;
    @Schema(title="완성차 의존율 매출비중")
    private String dpndnRate;

    @Schema(title = "지도현황코드")
    private String guidePscndCd;
    @Schema(title = "지도현황일자")
    private String guidePscndDt;

    @Schema(title = "신청업종")
    private String cbsnNm;
    @Schema(title = "품질담당자수")
    private Integer qltyPicCnt;
    @Schema(title = "FAX번호")
    private String faxNo;
    @Schema(title = "진행상태코드")
    private String rsumeSttsCd;
    @Schema(title = "진행상태 텍스트")
    private String rsumeSttsNm;
    @Schema(title = "대표자승인여부")
    private String rprsntApprvYn;
    @Schema(title = "신청사유코드")
    private String appctnRsnCd;
    @Schema(title = "신청분야코드")
    private String appctnTypeCd;
    @Schema(title = "신청분야코드명")
    private String appctnTypeNm;
    @Schema(title = "요청내용, 신청사항")
    private String rqstCntn;
    @Schema(title = "관리자 메모")
    private String admMemo;
    @Schema(title = "관리자 메모 저장 시간")
    private String admMemoModDtm;
    @Schema(title = "소개파일순번")
    private Integer itrdcFileSeq;
    @Schema(title = "소개파일ord")
    private Integer itrdcFileOrd;
    @Schema(title = "개선파일순번")
    private Integer impvmFileSeq;
    @Schema(title = "파일순번")
    private Integer fileSeq;

    private List<String> dlvryCmpnList;
    private List<String> dpndCmpnList;
    private List<String> rsumeList;
    private List<String> appctnTypeList;

    // 사업진행 상세
    @Schema(title = "불량개선이전율")
    private Double fltyImpvmBfreRate;
    @Schema(title = "불량개선이후률")
    private Double fltyImpvmAftrRate;
    @Schema(title = "불량개선율")
    private Double fltyImpvmRate;

    // 만족도 설문
    @Schema(title = "설문순번")
    private String srvSeq;
    @Schema(title = "설문이름")
    private String srvNm;
    @Schema(title = "설문시작일자")
    private String srvStrtDtm;
    @Schema(title = "설문종료일자")
    private String srvEndDtm;
    @Schema(title = "설문종료일자")
    private String typeNm;
    @Schema(title = "문항수", example = "숫자")
    private Integer qstnCnt;
    @Schema(title = "응답수", example = "숫자")
    private Integer rspnCnt;
    @Schema(title = "예상 응답시간", example = "숫자")
    private Integer rspnMm;
    @Schema(title = "설문 문구")
    private String cntn;
    @Schema(title = "설문 가능 기간에 걸리는 설문 갯수", example = "숫자")
    private Integer srvCnt;

    // 이관 이력 리스트
    List<CBATechGuidanceInsertDTO> trsfGuidanceList;
    // 만족도 상세
    List<CBATechGuidanceInsertDTO> surveyInfoList;
    private List<String> ctgryCdList;

    // 검색조건
    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String dStrDt;
    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String dEndDt;
    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    private String srchLayer;
    @Schema(title = "컨설팅 구분")
    private String rtnBsnGubun;
    private List<CBATechGuidanceInsertDTO> rsList;
    @Schema(title = "엑셀 다운로드 여부")
    private String excelYn;
    @Schema(title = "엑셀 리스트")
    private List<CBATechGuidanceInsertDTO> excelList;
    @Schema(title = "엑셀 다운로드 이유")
    private String rsn;

    @Schema(title = "신청부품사 업종 리스트", example = "")
    List<CBATechGuidanceInsertDTO> dtlList;

    @Schema(title = "기술지도 개선활동 파일")
    private String tchgdFileSeq;
    @Schema(title = "경영컨설팅 개선활동 파일")
    private String mngmntFileSeq;
    @Schema(title = "기술지도 개선활동 파일 경로")
    private String tchgdFilePath;
    @Schema(title = "경영컨설팅 개선활동 파일 경로")
    private String mngmntFilePath;
    @Schema(title = "기술지도 개선활동 파일 순번")
    private String tchgdFileOrd;
    @Schema(title = "경영컨설팅 개선활동 파일 순변")
    private String mngmntFileOrd;
    @Schema(title = "")
    private String bsnCd;

    @Schema(title = "사용자 메뉴 시퀀스")
    private String UserMenuSeq;
    @Schema(title = "마이페이지에서 사업 종류")
    private String statusChk;
    @Schema(title = "마이페이지에서 날짜")
    private String dateType;
    @Schema(title = "정렬 기준")
    private String ordFlag;

    @Schema(title = "사용자 마이페이지 - 컨설팅신청내역 상세 - 회사소개서 파일명(확장자 포함)")
    private String itrdcFileName;
    @Schema(title = "사용자 마이페이지 - 컨설팅신청내역 상세 - 사용자 개선활동 추진계획서 파일명(확장자 포함)")
    private String impvmFileName;
    @Schema(title = "사용자 마이페이지 - 컨설팅신청내역 상세 - 사용자 개선활동 추진계획서 파일 정렬값")
    private Integer impvmFileOrd;

    @Schema(title = "사용자 마이페이지 - 컨설팅신청내역 목록 - 사전심사결과의견")
    private String bfreJdgmtRsltCntn;
    @Schema(title = "사용자 마이페이지 - 컨설팅신청내역 목록 - 초도방문결과의견")
    private String initVstOpnnCntn;
    @Schema(title = "관리자 대시보드 검색타입", example = "A/B")
    private String dashBoardType;

    @Schema(title = "컨설팅 현황")
    private String cnstgPscndCd;
    @Schema(title = "컨설팅 현황 날짜")
    private String cnstgPscndDt;



}
