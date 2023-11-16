package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
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

    // 사업자번호
    private String bsnmNo;

    // 회사명
    private String cmpnNm;

    // 대표자명
    private String rprsntNm;

    // 회사약식명
    private String cmpnNfrmlNm;

    // 회사코드
    private String cmpnCd;

    // 카테고리코드
    private String ctgryCd;

    // 카테고리명
    private String ctgryNm;

    // 규모코드
    private String sizeCd;

    // 규모명
    private String sizeNm;

    // 설립일자
    private String stbsmDt;

    // 전화번호
    private String telNo;

    // 우편번호
    private String zipcode;

    // 기본주소
    private String bscAddr;

    // 상세주소
    private String dtlAddr;

    // 매출금액
    private Integer slsPmt;

    // 매출년도
    private Integer slsYear;

    // 직원수
    private Integer mpleCnt;

    // 주요상품1
    private String mjrPrdct1;

    // 주요상품2
    private String mjrPrdct2;

    // 주요상품3
    private String mjrPrdct3;

    // 품질5스타코드
    private String qlty5StarCd;

    // 품질5스타년도
    private Integer qlty5StarYear;

    // 납입5스타코드
    private String pay5StarCd;

    // 납입5스타년도
    private Integer pay5StarYear;

    // 기술5스타코드
    private String tchlg5StarCd;

    // 기술5스타년도
    private Integer tchlg5StarYear;

    // SQ정보
    // 업종순번
    private Integer cbsnSeq;

    // 업종명
    private String nm;

    // 점수
    private Integer score;

    // SQ 평가년도
    private Integer year;

    // 인증주관사명
    private String crtfnCmpnNm;

    // SQ 리스트
    private List<String> sqInfoList;

    // SQ 리스트1
    private List<String> sqInfoList1;
    // SQ 리스트2
    private List<String> sqInfoList2;
    // SQ 리스트3
    private List<String> sqInfoList3;


    // 관리자메모
    @SaxFilter
    private String admMemo;

    // 등록ID
    private String regId;

    // 등록IP
    private String regIP;

    // 등록일시
    private String regDtm;

    // 수정ID
    private String modId;

    // 수정IP
    private String modIP;

    // 수정일시
    private String modDtm;

    // 조회
    private List<MPEPartsCompanyDTO> list;

    // 검색조건
    // 검색 등록/수정 기간 시작일
    private String dStrDt;

    // 검색 등록/수정 기간 종료일
    private String dEndDt;

    // 카테고리 구분
    private List<String> ctgryCdList;

    // 기업규모 구분
    private List<String> sizeCdList;

    //엑셀여부
    private String excelYn;

    // 검색 레이어에서 호출 여부
    private String srchPartsComLayer;

}
