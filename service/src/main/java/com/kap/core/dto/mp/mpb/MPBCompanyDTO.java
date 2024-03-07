package com.kap.core.dto.mp.mpb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 부품사 조회 DTO
 *
 * @author 김태훈
 * @since 2024.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.01.10  김태훈             상생사업 부품사 조회
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPBCompanyDTO extends BaseDTO {
    @Schema(title = "회원순번")
    private Integer memSeq;

    @Schema(title = "아이디")
    private String id;

    @Schema(title = "이름")
    private String name;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "사업자번호")
    private String bsnmNo;

    @Schema(title = "부서코드")
    private String deptCd;

    @Schema(title = "부서상세")
    private String deptDtlNm;

    @Schema(title = "직급코드")
    private String pstnCd;

    @Schema(title = "기타 직급명")
    private String pstnNm;

    @Schema(title = "휴대전화번호")
    private String hpNo;

    @Schema(title = "일반전화번호")
    private String telNo;

    @Schema(title = "회사명")
    private String cmpnNm;

    @Schema(title = "대표자명")
    private String rprsntNm;

    @Schema(title = "카테고리 코드")
    private String ctgryCd;

    @Schema(title = "카테고리 코드명")
    private String ctgryNm;

    @Schema(title = "규모 코드")
    private String sizeCd;

    @Schema(title = "규모 코드명")
    private String sizeNm;

    @Schema(title = "설립일자")
    private String stbsmDt;

    @Schema(title = "전화번호")
    private String compTel;

    @Schema(title = "우편번호")
    private String zipcode;

    @Schema(title = "기본주소")
    private String bscAddr;

    @Schema(title = "상세주소")
    private String dtlAddr;

    @Schema(title = "매출금액")
    private Integer slsPmt;

    @Schema(title = "매출년도")
    private Integer slsYear;

    @Schema(title = "직원수")
    private Integer mpleCnt;

    @Schema(title = "주요상품1")
    private String mjrPrdct1;

    @Schema(title = "주요상품2")
    private String mjrPrdct2;

    @Schema(title = "주요상품3")
    private String mjrPrdct3;

    @Schema(title = "품질5스타코드")
    private String qlty5starCd;

    @Schema(title = "품질5스타값")
    private String qlty5starNm;

    @Schema(title = "품질5스타년도")
    private Integer qlty5starYear;

    @Schema(title = "납입5스타코드")
    private String pay5starCd;

    @Schema(title = "납입5스타값")
    private String pay5starNm;

    @Schema(title = "납입5스타년도")
    private Integer pay5starYear;

    @Schema(title = "기술5스타코드")
    private String tchlg5starCd;

    @Schema(title = "기술5스타값")
    private String tchlg5starNm;

    @Schema(title = "기술5스타년도")
    private Integer tchlg5starYear;

    @Schema(title = "sq정보")
    private List<MPBCompanyDTO> sqInfoList;

    @Schema(title = "업종순번")
    private Integer cbsnSeq;

    @Schema(title = "업종명")
    private String nm;

    @Schema(title = "점수")
    private Double score;

    @Schema(title = "SQ 평가년도")
    private Integer year;

    @Schema(title = "인증주관사명")
    private String crtfnCmpnNm;
}
