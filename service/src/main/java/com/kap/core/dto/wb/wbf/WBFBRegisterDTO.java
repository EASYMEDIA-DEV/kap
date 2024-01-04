package com.kap.core.dto.wb.wbf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBSpprtDtlDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "스마트공장구축 신청업체관리 검색")
public class WBFBRegisterDTO extends BaseDTO {


    /* 공통 - 컬럼명 동일 */
    @Schema(title = "신청상태", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태명", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태명", example = "")
    private String mngSttsCdNm;

    @Schema(title = "이관번호", example = "")
    private Integer trnsfSeq;
    @Schema(title = "이전회원순번", example = "")
    private String bfreMemSeq;
    @Schema(title = "이후회원순번", example = "")
    private String aftrMemSeq;

    /* Detail */
    /* 상생 */
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;
    @Schema(title = "회차순번", example = "")
    private String optnCd;
    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;
    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSpprtSeq;
    @Schema(title = "신청순번", example = "숫자")
    private String giveType;
    @Schema(title = "과제명 코드", example = "")
    private String taskCd;
    @Schema(title = "사업 유형 코드", example = "")
    private String bsnTypeCd;
    @Schema(title = "스마트화 현재코드", example = "")
    private String smtfnPrsntCd;
    @Schema(title = "스마트화 목표코드", example = "")
    private String smtfnTrgtCd;
    @Schema(title = "종된사업장번호", example = "")
    private String sbrdnBsnmNo;
    @Schema(title = "상세 진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    @Schema(title = "SQ 리스트 - 회사업종상세 테이블")
    private List<WBCompanyDetailMstDTO> sqInfoList;

    /*상생 파일 상세*/
    @Schema(title = "파일순번", example = "숫자")
    private String fileCd;
    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    /* 상생 Data */
    @Schema(title = "사업년도", example = "YYYY")
    private String year;
    @Schema(title = "회차", example = "1")
    private String episd;

    @Schema(title = "신청자 이름", example = "")
    private String beforeMemSeq;
    @Schema(title = "신청자 이름", example = "")
    private String beforeId;

    /* 신청자 정보 */
    @Schema(title = "신청자 이름", example = "")
    private String memSeq;
    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 ID", example = "")
    private String id;
    @Schema(title = "일반 전화번호", example = "")
    private String telNo;
    @Schema(title = "휴대번호", example = "")
    private String hpNo;
    @Schema(title = "이메일", example = "")
    private String email;
    @Schema(title = "부서코드", example = "")
    private String deptCd;
    @Schema(title = "부서상세명", example = "")
    private String deptDtlNm;
    @Schema(title = "직급코드", example = "")
    private String pstnCd;
    @Schema(title = "직급 상세명", example = "")
    private String pstnNm;

    /* 회사 정보*/
    @Schema(title = "사업자등록번호", example = "")
    private String bsnmNo;
    @Schema(title = "회사 명", example = "")
    private String cmpnNm;
    @Schema(title = "회사 대표자명", example = "")
    private String rprsntNm;
    @Schema(title = "회사 전화번호", example = "")
    private String compTel;       // 중복 컬럼 명
    @Schema(title = "설립일자", example = "yyyy-MM-dd")
    private String stbsmDt;       //설립일자
    @Schema(title = "구분 코드 값", example = "")
    private String ctgryCd;
    @Schema(title = "구분", example = "")
    private String ctgryCdNm; /* 코드 값 nm */
    @Schema(title = "규모 코드 값", example = "")
    private String sizeCd;
    @Schema(title = "규모", example = "")
    private String sizeCdNm; /* 코드 값 nm */
    @Schema(title = "우편변호", example = "")
    private String zipcode;
    @Schema(title = "우편변호", example = "")
    private String bscAddr;
    @Schema(title = "우편변호", example = "")
    private String dtlAddr;
    @Schema(title = "매출액 - 금액", example = "100")
    private Integer slsPmt;
    @Schema(title = "매출액 - 년도", example = "yyyy")
    private String slsYear;
    @Schema(title = "직원 수", example = "100")
    private Integer mpleCnt;          // 직원 수
    @Schema(title = "주요생산품 1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요생산품 2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요생산품 3", example = "")
    private String mjrPrdct3;
    @Schema(title = "품질5스타코드", example = "")
    private String qlty5starCd;
    @Schema(title = "품질5스타코드", example = "")
    private String qlty5starCdNm;
    @Schema(title = "품질5스타년도", example = "")
    private String qlty5starYear;
    @Schema(title = "납입5스타코드", example = "")
    private String pay5starCd;
    @Schema(title = "납입5스타코드", example = "")
    private String pay5starCdNm;
    @Schema(title = "납입5스타년도", example = "")
    private String pay5starYear;
    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5starCd;
    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5starCdNm;
    @Schema(title = "기술5스타년도", example = "")
    private String tchlg5starYear;
    @Schema(title = "선급금액여부", example = "")
    private String pmndvPmtYn;

    @Schema(title = "담당위원코드", example = "")
    private String picCmssrSeq;

    @Schema(title = "담당위원이름", example = "")
    private String picCmssrNm;
    @Schema(title = "담당위원이메일", example = "")
    private String picCmssrEmail;
    @Schema(title = "담당위원 핸드폰번호", example = "")
    private String picCmssrHpNo;
    @Schema(title = "담당위원 회사명", example = "")
    private String picCmssrCmpnNm;

    @Schema(title = "관리자메모", example = "")
    private String admMemo;
    @Schema(title = "등록ID", example = "")
    private String regId;
    @Schema(title = "등록IP", example = "")
    private String regIp;
    @Schema(title = "등록일시", example = "")
    private String regDtm;
    @Schema(title = "수정ID", example = "")
    private String modId;
    @Schema(title = "수정IP", example = "")
    private String modIp;
    @Schema(title = "수정일시", example = "")
    private String modDtm;

    List<String> delList;
    List<String> delMarkRsumeSeq;

    /* 현재 지원금상세 지급 구분 */
    private String nowSpprtCd;
    /* 현재 스마트상세 진행상태 */
    private String nowRsumeTaskCd;

    /* 상생신청지원금 상세 DTO 고정 - 선급금지급 */
    WBSpprtDtlDTO defaultSpprtDtl;

    /* 상생신청지원금 상세 DTO */
    WBSpprtDtlDTO spprtDtl;

    /* 스마트 공장 상세 DTO */
    WBFBRsumeTaskDtlDTO rsumeTaskDtl;


}
