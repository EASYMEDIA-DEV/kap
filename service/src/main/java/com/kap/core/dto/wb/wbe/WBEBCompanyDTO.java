package com.kap.core.dto.wb.wbe;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 부품사
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.08  김대성         최초 생성
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
@Schema(title = "신청부품사")
public class WBEBCompanyDTO extends BaseDTO {

    @Schema(title = "사업자번호", example = "")
    private String bsnmNo;

    @Schema(title = "회사명", example = "")
    private String cmpnNm;

    @Schema(title = "대표자명", example = "")
    private String rprsntNm;

    @Schema(title = "회사코드", example = "")
    private String cmpnCd;

    @Schema(title = "구분", example = "")
    private String ctgryCd;

    @Schema(title = "규모", example = "")
    private String sizeCd;

    @Schema(title = "설립일자", example = "yyyy-MM-dd hh:mm:ss")
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
    private int slsPmt;
    @Schema(title = "매출년도", example = "")
    private String slsYear;
    @Schema(title = "직원수", example = "")
    private int mpleCnt;

    @Schema(title = "주요상품1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요상품2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요상품3", example = "")
    private String mjrPrdct3;

    @Schema(title = "품질5스타코드", example = "")
    private String qlty5starCd;
    @Schema(title = "품질5스타년도", example = "")
    private Integer qlty5starYear;

    @Schema(title = "납입5스타코드", example = "")
    private String pay5starCd;
    @Schema(title = "납입5스타년도", example = "")
    private Integer pay5starYear;

    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5starCd;
    @Schema(title = "기술5스타년도", example = "")
    private Integer tchlg5starYear;


    @Schema(title = "신청부품사 업종 리스트", example = "")
    List<WBEBCompanyDtlDTO> dtlList;



}
