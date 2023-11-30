package com.kap.core.dto.eb.ebd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  SQ평가원 목록 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
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
@Schema(title = "SQ평가원 목록")
public class EBDSqCertiListDTO {
    @Schema(title = "발급순번")
    private String examAppctnSeq;
    @Schema(title = "발급코드")
    private String issueCd;
    @Schema(title = "발급코드명")
    private String issueNm;
    @Schema(title = "년도")
    private int episdYear;
    @Schema(title = "회차")
    private int episdOrd;
    @Schema(title = "업종")
    private String cbsnNm;
    @Schema(title = "부품사명")
    private String cmpnNm;
    @Schema(title = "부품사구분코드")
    private String ctgryCd;
    @Schema(title = "부품사구분코드명")
    private String ctgryNm;
    @Schema(title = "부품사규모코드")
    private String sizeCd;
    @Schema(title = "부품사규모코드명")
    private String sizeNm;
    @Schema(title = "사업자등록번호")
    private String bsnmNo;
    @Schema(title = "이름")
    private String id;
    @Schema(title = "이름")
    private String name;
    @Schema(title = "GPC ID")
    private String gpcId;
    @Schema(title = "휴대전화번호")
    private String hpNo;
    @Schema(title = "이메일")
    private String email;
    @Schema(title = "신청일시")
    private String regDtm;
    @Schema(title = "최종수정자ID")
    private String modId;
    @Schema(title = "최종수정자ID명")
    private String modName;
    @Schema(title = "최종수정일시")
    private String modDtm;
    @Schema(title = "사용여부(사용,중지)")
    private String useYn;
    @Schema(title = "SQ 평가원 구분 코드")
    private String examCd;
    @Schema(title = "SQ 평가원 구분 코드명")
    private String examCdNm;
    @Schema(title = "취득일시")
    private String acqsnDtm;
    @Schema(title = "만료예정일")
    private String validEndDt;
    @Schema(title = "자격증번호")
    private String jdgmtNo;
}
