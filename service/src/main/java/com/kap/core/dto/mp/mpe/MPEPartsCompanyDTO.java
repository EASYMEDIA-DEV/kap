package com.kap.core.dto.mp.mpe;

import com.kap.core.annotation.SaxFilter;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  이용약관 관리
 *
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class MPEPartsCompanyDTO extends BaseDTO {

    @Schema(title = "사업자번호", example = "")
    private String bsnmNo;

    @Schema(title = "회사명", example = "")
    private String cmpnNm;

    @Schema(title = "대표자명", example = "")
    private String rprsntNm;

    @Schema(title = "회사약식명", example = "")
    private String cmpnNfrmlNm;

    @Schema(title = "부품사코드", example = "")
    private String cmpnCd;

    @Schema(title = "구분코드", example = "")
    private String ctgryCd;

    @Schema(title = "구분명", example = "")
    private String ctgryNm;

    @Schema(title = "규모코드", example = "")
    private String sizeCd;

    @Schema(title = "규모명", example = "")
    private String sizeNm;

    @Schema(title = "설립일자", example = "")
    private String stbsmDt;

    @Schema(title = "전화번호", example = "")
    private String telNo;

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

    @Schema(title = "품질5스타코드명", example = "")
    private String qlty5StarCdNm;

    @Schema(title = "품질5스타년도", example = "")
    private Integer qlty5StarYear;

    @Schema(title = "납입5스타코드", example = "")
    private String pay5StarCd;

    @Schema(title = "납입5스타코드명", example = "")
    private String pay5StarCdNm;

    @Schema(title = "납입5스타년도", example = "")
    private Integer pay5StarYear;

    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5StarCd;

    @Schema(title = "기술5스타코드명", example = "")
    private String tchlg5StarCdNm;

    @Schema(title = "기술5스타년도", example = "")
    private Integer tchlg5StarYear;

    // SQ정보
    @Schema(title = "업종순서", example = "")
    private Integer cbsnSeq;

    @Schema(title = "업종명", example = "")
    private String nm;

    @Schema(title = "점수", example = "")
    private Double score;

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

    @Schema(title = "관리자메모", example = "")
    @SaxFilter
    private String admMemo;

    @Schema(title = "등록ID")
    private String regId;

    @Schema(title = "등록IP")
    private String regIP;

    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID")
    private String modId;

    @Schema(title = "수정IP")
    private String modIP;

    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

    @Schema(title = "등록자 이름(사용자이름)")
    private String regUserName;

    @Schema(title = "수정자 이름(사용자이름)")
    private String modUserName;

    // 조회
    private List<MPEPartsCompanyDTO> list;

    //부품사 실적정보

    // 종합현황
    @Schema(title = "교육사업 개수", example = "숫자")
    private Integer eduTotalCnt;
    
    //자동차부품산업대상 목록 조회
    @Schema(title = "수상년도", example = "숫자")
    private Integer cartTargetYear;

    @Schema(title = "포상금액", example = "숫자")
    private Integer prizePmt;

    @Schema(title = "훈격명", example = "")
    private String mrtsName;

    @Schema(title = "포상부문명", example = "")
    private String pmtName;

    @Schema(title = "수상자명", example = "")
    private String winnerName;

    @Schema(title = "수상자 직급", example = "")
    private String winnerPstnNm;

    //교육사업 통계
    @Schema(title = "교육사업명", example = "")
    private String cdNm;

    @Schema(title = "2024년도", example = "")
    private Integer year2024;

    @Schema(title = "2023년도", example = "")
    private Integer year2023;

    @Schema(title = "2022년도", example = "")
    private Integer year2022;

    @Schema(title = "2021년도", example = "")
    private Integer year2021;

    @Schema(title = "2020년도", example = "")
    private Integer year2020;

    @Schema(title = "2019년도 이전 ", example = "")
    private Integer yearElse;

    // 검색조건
    // 검색 등록/수정 기간 시작일
    private String dStrDt;

    // 검색 등록/수정 기간 종료일
    private String dEndDt;

    // 카테고리 구분
    private List<String> ctgryCdList;

    // 기업규모 구분
    private List<String> sizeCdList;

    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    private String srchLayer;

    @Schema(title = "엑셀여부", example = "Y")
    private String excelYn;

    @Schema(title = "변경사유", example = "이유")
    private String rsn;
}
