package com.kap.core.dto.cb.cba;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    @Schema(title = "신청자 이메일")
    private String email;
    @Schema(title = "신청자 부서 코드")
    private String deptCd;
    @Schema(title = "신청자 부서명")
    private String deptDtlNm;
    @Schema(title = "신청자 직급")
    private String pstnCd;
    @Schema(title = "신청자 전화번호")
    private String memTelNo;

    // 부품사 정보
    @Schema(title = "컨설팅 순번")
    private Integer cnstgSeq;
    @Schema(title = "신청사업자번호")
    private Integer appctnBsnmNo;
    @Schema(title = "신청회원순번")
    private Integer appctnMemNo;
    @Schema(title = "업종")
    private String cnstgCd;
    @Schema(title = "기타명")
    private String etcNm;
    @Schema(title = "구분")
    private String ctgryCd;
    @Schema(title = "설립일자")
    private String stbsmDt;
    @Schema(title = "부품사 전화번호")
    private String telNo;
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
    @Schema(title = "SQ 점수")
    private Integer  score;
    @Schema(title = "SQ 평가년도")
    private String year;
    @Schema(title = "인증주관사")
    private Integer  crtfnCmpnNm;

    @Schema(title = "사업년도")
    private Integer bsnYear;
    @Schema(title = "신청회원순번")
    private String appctnMemSeq;
    @Schema(title = "신청일자")
    private String appctnDt;
    @Schema(title = "회사규모코드")
    private String sizeCd;

    @Schema(title = "회사주소동일여부")
    private String cmpnAddrSameYn;
    @Schema(title = "공장우편번호")
    private String fctryZipcode;
    @Schema(title = "공장기본주소")
    private String fctryBscAddr;
    @Schema(title = "공장상세주소")
    private String fctryDtlAddr;
    @Schema(title = "소재지 코드")
    private String rgnsCd;
    @Schema(title = "홈페이지URL")
    private String hmpgeUrl;

    @Schema(title="거래처별 매출 비중 업체명")
    private String dlvryCmpnNm;
    @Schema(title="거래처별 매출비중")
    private String dlvryRate;
    @Schema(title="완성차 의존율 업체명")
    private String dpndnCmpnNm;
    @Schema(title="완성차 의존율 매출비중")
    private String dpndnRate;


    @Schema(title = "품질담당자수")
    private Integer qltyPicCnt;
    @Schema(title = "FAX번호")
    private String faxNo;

    @Schema(title = "대표자승인여부")
    private String rprsntApprvYn;
    @Schema(title = "신청사유코드")
    private String appctnRsnCd;
    @Schema(title = "신청분야코드")
    private String appctnFldCd;
    @Schema(title = "요청내용, 신청사항")
    private String rqstCntn;

    @Schema(title = "소개파일순번")
    private Integer itrdcFileSeq;
    @Schema(title = "개선파일순번")
    private Integer impvmFileSeq;
    @Schema(title = "파일순번")
    private Integer fileSeq;

    private List<String> dlvryCmpnNmList;
    private List<String> dlvryRateList;
    private List<String> dpndnCmpnNmList;
    private List<String> dpndnRateList;
}
