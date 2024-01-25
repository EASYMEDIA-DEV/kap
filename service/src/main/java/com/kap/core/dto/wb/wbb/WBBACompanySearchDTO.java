package com.kap.core.dto.wb.wbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
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
@Schema(title = "공통 신청업체관리 검색")
public class WBBACompanySearchDTO extends BaseDTO {
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "사업코드명", example = "")
    private String bsnNm;
    @Schema(title = "옵션 구분 코드", example = "")
    private String optnCd;
    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;
    @Schema(title = "단계명", example = "")
    private String stageNm;
    @Schema(title = "사업시작일시", example = "yyyy-MM-dd")
    private String bsnStrtDtm;
    @Schema(title = "사업종료일시", example = "yyyy-MM-dd")
    private String bsnEndDtm;
    @Schema(title = "사업명", example = "")
    private String title;
    
    /* List Data */
    @Schema(title = "사업년도", example = "YYYY")
    private String year;
    @Schema(title = "회차", example = "1")
    private String episd;
    @Schema(title = "사업년도", example = "YYYY")
    private String appctnSeq;
    @Schema(title = "사용자순번", example = "")
    private Integer memSeq;
    @Schema(title = "신청상태코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태코드 명", example = "") /* 코드 값 nm */
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "") /* 코드 값 nm */
    private String mngSttsCdNm;
    @Schema(title = "부품사명", example = "")
    private String cmpnNm;
    @Schema(title = "구분 코드 값", example = "")
    private String ctgryCd;
    @Schema(title = "구분", example = "") /* 코드 값 nm */
    private String ctgryCdNm;
    @Schema(title = "규모 코드 값", example = "")
    private String sizeCd;
    @Schema(title = "규모", example = "") /* 코드 값 nm */
    private String sizeCdNm;
    @Schema(title = "사업자등록번호", example = "")
    private String bsnmNo;
    @Schema(title = "대표자명", example = "")
    private String rprsntNm;
    @Schema(title = "설립일자", example = "")
    private String stbsmDt;
    @Schema(title = "우편번호", example = "")
    private String zipcode;
    @Schema(title = "기본주소", example = "")
    private String bscAddr;
    @Schema(title = "상세주소", example = "")
    private String dtlAddr;
    @Schema(title = "매출금액", example = "")
    private String slsPmt;
    @Schema(title = "매출년도", example = "")
    private String slsYear;
    @Schema(title = "직원수", example = "")
    private Integer mpleCnt;
    @Schema(title = "주요상품1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요상품2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요상품3", example = "")
    private String mjrPrdct3;
    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 ID", example = "")
    private String id;
    @Schema(title = "휴대번호", example = "")
    private String hpNo;
    @Schema(title = "이메일", example = "")
    private String email;
    @Schema(title = "진행순번", example = "")
    private Integer rsumeSeq;
    @Schema(title = "진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "진행상태 명", example = "")
    private String rsumeSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd")
    private String appctnSttsChngDtm;
    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd")
    private String mngSttsChngDtm;
    @Schema(title = "관리자메모", example = "")
    private String admMemo;
    @Schema(title = "관리자메모 수정시간", example = "")
    private String admMemoModDtm;
    @Schema(title = "수정ID", example = "")
    private String modId;
    @Schema(title = "수정일시", example = "yyyy-MM-dd")
    private String modDtm;

    @Schema(title = "담당위원코드", example = "")
    private String picCmssrSeq;
    @Schema(title = "담당위원이름", example = "")
    private String picName;

    @Schema(title = "공급업체명", example = "")
    private String offerCmpnCdNm;
    @Schema(title = "공급업체 사업자등록번호", example = "")
    private String offerBsnmNo;


    @Schema(title = "검색 리스트", example = "")
    List<WBBACompanySearchDTO> list;

    @Schema(title = "삭제가능여부", example = "")
    private String deleteYn;

    /* 검색 관련 코드 */
    @Schema(title = "공통", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;

    @Schema(title = "구분 코드 리스트")
    private List ctgryCdList;
}
