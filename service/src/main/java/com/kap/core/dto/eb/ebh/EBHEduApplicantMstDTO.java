package com.kap.core.dto.eb.ebh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육 신청자 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  장두석         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "교육 신청자 마스터 DTO")
public class EBHEduApplicantMstDTO extends BaseDTO {

    @Schema(title = "참여 순번", example = "숫자")
    private Integer ptcptSeq;

    @Schema(title = "회원 순번", example = "숫자")
    private Integer memSeq;

    @Schema(title = "교육 순번", example = "숫자")
    private Integer edctnSeq;

    @Schema(title = "교육 회차 순번", example = "숫자")
    private Integer episdSeq;


    @Schema(title = "과정 분류 코드")
    private String ctgryCd;

    @Schema(title = "1차 과정 분류명")
    private String stdPrtCateNm;

    @Schema(title = "2차 과정 분류명")
    private String stdCateNm;

    @Schema(title = "과정명")
    private String stdName;

    @Schema(title = "학습 방식 코드")
    private String stduyMthdCd;

    @Schema(title = "학습 방식명")
    private String stduyMthdNm;

    @Schema(title = "학습일 코드")
    private String stduyDdCd;

    @Schema(title = "학습일")
    private String stduyDd;

    @Schema(title = "학습 시간 코드")
    private String stduyTimeCd;

    @Schema(title = "학습 시간")
    private String stduyTime;


    @Schema(title = "회차")
    private String episdOrd;

    @Schema(title = "회차 년도")
    private String episdYear;

    /*@Schema(title = "업종 코드")
    private String cbsnCd;*/

    @Schema(title = "교육 회차 업종")
    private String cbsnNm;

    @Schema(title = "회차 접수 시작 일시")
    private String accsStrtDtm;

    @Schema(title = "회차 접수 종료 일시")
    private String accsEndDtm;

    @Schema(title = "회차 교육 시작 일시")
    private String edctnStrtDtm;

    @Schema(title = "회차 교육 종료 일시")
    private String edctnEndDtm;

    @Schema(title = "강사 이름 목록")
    private List<String> insNameList;

    @Schema(title = "회차 정원수")
    private String fxnumCnt;

    @Schema(title = "회차 모집 방식 코드")
    private String rcrmtMthdCd;

    @Schema(title = "회차 모집 방식")
    private String rcrmtMthd;

    @Schema(title = "회차 문의 담당자명")
    private String picNm;

    @Schema(title = "회차 문의 담당자 이메일")
    private String picEmail;

    @Schema(title = "회차 문의 담당자 전화번호")
    private String picTelNo;

    @Schema(title = "회차 교육 장소명")
    private String placeName;


    @Schema(title = "회원명")
    private String name;

    @Schema(title = "회원 아이디")
    private String id;

    @Schema(title = "회원 이메일")
    private String email;

    @Schema(title = "회원 부서 코드")
    private String deptCd;

    @Schema(title = "회원 부서 상세명")
    private String deptDtlNm;

    @Schema(title = "회원 직급 코드")
    private String pstnCd;

    @Schema(title = "회원 직급명", description = "직급 코드 기타 선택 시 입력")
    private String pstnNm;

    @Schema(title = "회원 휴대폰 번호")
    private String hpNo;

    @Schema(title = "회원 일반 전화번호")
    private String telNo;


    @Schema(title = "부품사명")
    private String cmpnNm;

    @Schema(title = "부품사 구분 코드")
    private String partsCtgryCd;

    @Schema(title = "부품사 구분")
    private String cmpnCateNm;

    @Schema(title = "부품사 규모 코드")
    private String sizeCd;

    @Schema(title = "부품사 규모")
    private String sizeNm;

    @Schema(title = "부품사 대표자명")
    private String rprsntNm;

    @Schema(title = "부품사 설립일자")
    private String stbsmDt;

    @Schema(title = "부품사 전화번호")
    private String cmpnTelNo;

    @Schema(title = "신청 당시 사업자 등록 번호")
    private String ptcptBsnmNo;

    @Schema(title = "부품사 우편번호")
    private String zipcode;

    @Schema(title = "부품사 기본 주소")
    private String bscAddr;

    @Schema(title = "부품사 상세 주소")
    private String dtlAddr;

    @Schema(title = "부품사 매출액")
    private String slsPmt;

    @Schema(title = "부품사 매출 년도")
    private String slsYear;

    @Schema(title = "부품사 직원 수")
    private String mpleCnt;

    @Schema(title = "부품사 주요 상품 1")
    private String mjrPrdct1;

    @Schema(title = "부품사 주요 상품 2")
    private String mjrPrdct2;

    @Schema(title = "부품사 주요 상품 3")
    private String mjrPrdct3;

    @Schema(title = "부품사 품질 5스타 코드")
    private String qlty5StarCd;

    @Schema(title = "부품사 품질 5스타 년도")
    private String qlty5StarYear;

    @Schema(title = "부품사 납입 5스타 코드")
    private String pay5StarCd;

    @Schema(title = "부품사 납입 5스타 년도")
    private String pay5StarYear;

    @Schema(title = "부품사 기술 5스타 코드")
    private String tchlg5StarCd;

    @Schema(title = "부품사 기술 5스타 년도")
    private String tchlg5StarYear;

    @Schema(title = "SQ 정보 목록")
    private List<MPEPartsCompanyDTO> sqList;


    @Schema(title = "GPC 교육 여부", example = "Y/N")
    private String gpcYn;

    @Schema(title = "GPC 아이디")
    private String gpcId;

    @Schema(title = "선발 상태 코드")
    private String sttsCd;

    @Schema(title = "선발 상태명")
    private String sttsName;

    @Schema(title = "지역")
    private String addr;


    @Schema(title = "선발/미선발 상태 변경 구분값")
    @Hidden
    private String stts;

    @Schema(title = "정원 초과 여부 상태값")
    @Hidden
    private String fxnumStta;

    @Schema(title = "현재 신청자 수")
    @Hidden
    private String cnt;

    @Schema(title = "정원 체크 리스트")
    @Hidden
    private List<String> fxnumCheckList;

    @Schema(title = "조회 목록", description = "목록 페이지 리스트")
    @Hidden
    private List<EBHEduApplicantMstDTO> list;


    /* 목록 검색 s */
    @Schema(title = "상위 과정 분류 검색 항목")
    @Hidden
    private List<String> prntCdList;

    @Schema(title = "하위 과정 분류 검색 항목")
    @Hidden
    private List<String> ctgryCdList;

    @Schema(title = "학습 방식 검색 항목")
    @Hidden
    private List<String> stduyMthdCdList;

    @Schema(title = "부품사 구분 검색 항목")
    @Hidden
    private List<String> partsCtgryCdList;

    @Schema(title = "선발 구분 검색 항목")
    @Hidden
    private List<String> choiceCtgryCdList;
    /* 목록 검색 e */
}
