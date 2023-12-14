package com.kap.core.dto.eb.ebc;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  방문교육 관리
 *
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.30  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Schema(title = "방문교육 관리")
public class EBCVisitEduDTO extends BaseDTO {

    // 방문마스터
    @Schema(title = "방문순번", example = "숫자")
    private Integer vstSeq;

    @Schema(title = "회원순번", example = "숫자")
    private Integer memSeq;

    @Schema(title = "신청사업자번호", example = "숫자")
    private String appctnBsnmNo;

    @Schema(title = "신청사유", example = "")
    private String appctnRsn;

    @Schema(title = "신청분야코드", example = "")
    private String appctnFldCd;

    @Schema(title = "신청분야명", example = "")
    private String appctnFldName;

    @Schema(title = "신청분야상세코드", example = "")
    private String cd;

    @Schema(title = "신청분야상세리스트", example = "")
    private List<String> appctnTypeCdList;

    @Schema(title = "신청주체내용", example = "")
    private String appctnThemeCntn;

    @Schema(title = "희망일자", example = "")
    private String hopeDt;

    //
    @Schema(title = "본사와 동일 체크값", example = "")
    private String samePlaceYn;

    @Schema(title = "장소우편번호", example = "")
    private String placeZipcode;

    @Schema(title = "장소기본주소", example = "")
    private String placeBscAddr;

    @Schema(title = "장소상세주소", example = "")
    private String placeDtlAddr;

    @Schema(title = "소재지 검색을 위한 교육장소주소값", example = "")
    private String edctnPlaceAddr;

    @Schema(title = "참여대상내용", example = "")
    private String ptcptTrgtCntn;

    @Schema(title = "교육인원", example = "")
    private Integer ptcptCnt;

    @Schema(title = "교육시간코드", example = "")
    private String ptcptHh;

    @Schema(title = "교육시간", example = "숫자")
    private Integer ptcptHhNum;

    @Schema(title = "소개파일순번", example = "")
    private Integer itrdcFileSeq;

    @Schema(title = "등록ID", example = "")
    private String regId;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;

    @Schema(title = "방문교육신청일시", example = "yyyy-MM-dd hh:mm:ss")
    private String visitRegDtm;

    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID", example = "")
    private String modId;

    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;

    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

    // 방문교육을 신청한 회원정보
    @Schema(title = "신청회원이름", example = "")
    private String name;

    @Schema(title = "신청회원아이디", example = "")
    private String id;

    @Schema(title = "신청회원 휴대폰번호", example = "")
    private String hpNo;

    @Schema(title = "신청회원 전화번호", example = "")
    private String telNo;

    @Schema(title = "신청회원 이메일", example = "")
    private String email;

    @Schema(title = "신청회원 부서코드", example = "")
    private String deptCd;

    @Schema(title = "신청회원 부서상세명", example = "")
    private String deptDtlNm;

    @Schema(title = "신청회원 직급코드", example = "")
    private String pstnCd;

    @Schema(title = "신청회원 직급상세명", example = "")
    private String deptCdNm;

    // 방문교육을 신청한 회사 정보
    @Schema(title = "선택된 구분값 가져오기", example = "")
    private String selectCtgryCd;

    @Schema(title = "회사명", example = "")
    private String cmpnNm;

    @Schema(title = "대표자명", example = "")
    private String rprsntNm;

    @Schema(title = "부품사코드", example = "")
    private String cmpnCd;

    @Schema(title = "구분코드", example = "")
    private String ctgryCd;

    @Schema(title = "구분코드명", example = "")
    private String ctgryName;

    @Schema(title = "규모코드", example = "")
    private String sizeCd;

    @Schema(title = "규모명", example = "")
    private String sizeName;

    @Schema(title = "설립일자", example = "")
    private String stbsmDt;

    @Schema(title = "전화번호", example = "")
    private String cmpnTelNo;

    @Schema(title = "우편번호", example = "")
    private String zipcode;

    @Schema(title = "기본주소", example = "")
    private String bscAddr;

    @Schema(title = "상세주소", example = "")
    private String dtlAddr;

    @Schema(title = "매출금액", example = "")
    private Integer slsPmt;

    @Schema(title = "매출년도", example = "")
    private Integer slsYear;

    @Schema(title = "직원수", example = "")
    private Integer mpleCnt;

    @Schema(title = "주요상품1", example = "")
    private String mjrPrdct1;

    @Schema(title = "주요상품2", example = "")
    private String mjrPrdct2;

    @Schema(title = "주요상품3", example = "")
    private String mjrPrdct3;

    @Schema(title = "품질5스타코드", example = "")
    private String qlty5StarCd;

    @Schema(title = "품질5스타년도", example = "")
    private Integer qlty5StarYear;

    @Schema(title = "납입5스타코드", example = "")
    private String pay5StarCd;

    @Schema(title = "납입5스타년도", example = "")
    private Integer pay5StarYear;

    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5StarCd;

    @Schema(title = "기술5스타년도", example = "")
    private Integer tchlg5StarYear;

    // SQ정보
    @Schema(title = "업종순서", example = "")
    private Integer cbsnSeq;

    @Schema(title = "업종명", example = "")
    private String nm;

    @Schema(title = "점수", example = "")
    private Integer score;

    @Schema(title = "SQ 평가년도", example = "")
    private Integer year;

    @Schema(title = "인증주관사명", example = "")
    private String crtfnCmpnNm;

    @Schema(title = "SQ 리스트", example = "")
    private List<String> sqInfoList;

    @Schema(title = "SQ 리스트1", example = "")
    private List<String> sqInfoList1;

    @Schema(title = "SQ 리스트2", example = "")
    private List<String> sqInfoList2;

    @Schema(title = "SQ 리스트3", example = "")
    private List<String> sqInfoList3;


    // 강사정보
    @Schema(title = "강사번호", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "강사이름", example = "")
    private String isttrName;

    @Schema(title = "강사소속", example = "")
    private String ffltnNm;

    @Schema(title = "강사약력", example = "")
    private String spclCntn;

    @Schema(title = "강사리스트", example = "")
    private List<EBCVisitEduDTO> isttrSeqList;

    // 방문교육 결과상세
    @Schema(title = "방문결과순번", example = "숫자")
    private Integer vstRsltSeq;

    @Schema(title = "확정주제", example = "")
    private String cnfrmdTheme;

    @Schema(title = "교육상태코드", example = "")
    private String edctnSttsCd;

    @Schema(title = "교육상태명", example = "")
    private String edctnSttsName;

    @Schema(title = "교육년도", example = "숫자")
    private Integer edctnYear;

    @Schema(title = "교육시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "교육장소", example = "")
    private String edctnPlace;

    @Schema(title = "수료인원", example = "숫자")
    private Integer cmptnCnt;

    @Schema(title = "참석률", example = "숫자")
    private Integer ptcptRate;

    @Schema(title = "강의파일순번", example = "숫자")
    private Integer lctrFileSeq;

    @Schema(title = "기타자료파일순번", example = "숫자")
    private Integer etcMatlsFileSeq;

    //방문교육 결과 옵션 상세
    @Schema(title = "결과옵션순번", example = "")
    private Integer rsltOptnSeq;

    @Schema(title = "결과구분코드", example = "")
    private String rsltTypeCd;

    @Schema(title = "옵션코드", example = "")
    private String optnCd;

    @Schema(title = "결과값", example = "")
    private Integer rsltVal;

    @Schema(title = "옵션정렬", example = "")
    private Integer optnOrd;

    @Schema(title = "방문교육결과옵션상세 결과코드 리스트", example = "")
    private List<EBCVisitEduDTO> resultOpList;

    @Schema(title = "조회 리스트", example = "")
    private List<EBCVisitEduDTO> list;

    // 검색조건
    @Schema(title = "검색 등록/수정 기간 시작일자", example = "yyyy-MM-dd")
    private String dStrDt;

    @Schema(title = "검색 등록/수정 기간 종료일자", example = "yyyy-MM-dd")
    private String dEndDt;

    @Schema(title = "신청분야코드 리스트", example = "")
    private List<String> appctnFldList;

    @Schema(title = "교육현황코드 리스트", example = "")
    private List<String> edctnSttsCdList;

    @Schema(title = "부품사구분 리스트", example = "")
    private List<String> ctgryCdList;

    @Schema(title = "신청 소재지 첫번째지역코드", example = "")
    private String firstRgnsCd;

    @Schema(title = "신청 소재지 첫번째지역명", example = "")
    private String firstRgnsName;

    @Schema(title = "신청 소재지 두번째지역코드", example = "")
    private String scndRgnsCd;

    @Schema(title = "신청 소재지 두번째지역명", example = "")
    private String scndRgnsName;

    @Schema(title = "엑셀 다운로드 여부", example = "Y")
    private String excelYn;

    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    private String srchLayer;

    // 첨부파일 리스트
    @Schema(title = "회사소개서 파일 리스트", example = "")
    private List<COFileDTO> itrdcFileList;

    @Schema(title = "강의교안 파일 리스트", example = "")
    private List<COFileDTO> lctrFileList;

    @Schema(title = "기타자료 파일 리스트", example = "")
    private List<COFileDTO> etcMatlsFileList;


}
