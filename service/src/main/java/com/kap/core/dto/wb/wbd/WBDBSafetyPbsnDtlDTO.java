package com.kap.core.dto.wb.wbd;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생사업관리 지급 상세 DTO
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
@Schema(title = "탄소배출저감 신청부품사 검색")
public class WBDBSafetyPbsnDtlDTO extends BaseDTO {

    @Schema(title = "진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬", example = "숫자")
    private Integer rsumeOrd;

    @Schema(title = "우편번호", example = "")
    private String pbsnZipcode;
    @Schema(title = "기본주소", example = "")
    private String pbsnBscAddr;
    @Schema(title = "상세주소", example = "")
    private String pbsnDtlAddr;

    @Schema(title = "점검위원순번", example = "숫자")
    private Integer chkCmssrSeq;

    @Schema(title = "사업금액", example = "")
    private String bsnPmt;

    @Schema(title = "사업계획일자", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnPlanDt;

    @Schema(title = "지원금액", example = "")
    private String spprtPmt;

    @Schema(title = "자부담금액", example = "")
    private String phswPmt;

    @Schema(title = "총금액", example = "")
    private String ttlPmt;

    @Schema(title = "점검일자", example = "yyyy-MM-dd hh:mm:ss")
    private String chkDt;

    @Schema(title = "평가점수", example = "")
    private String examScore;

    @Schema(title = "결제일자", example = "yyyy-MM-dd hh:mm:ss")
    private String payDt;

    @Schema(title = "설치예정일자", example = "yyyy-MM-dd hh:mm:ss")
    private String nslltSchdlDt;

    @Schema(title = "접수일자", example = "yyyy-MM-dd hh:mm:ss")
    private String accsDt;

    @Schema(title = "은행명", example = "")
    private String bankNm;

    @Schema(title = "계좌번호", example = "")
    private String acntNo;

    @Schema(title = "예금명", example = "")
    private String dpsitNm;

    @Schema(title = "지급순번", example = "")
    private String giveSeq;

    @Schema(title = "완료보고일자", example = "yyyy-MM-dd hh:mm:ss")
    private String cmpltnBrfngDt;

    @Schema(title = "최종점검일자", example = "yyyy-MM-dd hh:mm:ss")
    private String lastChkDt;

    @Schema(title = "현재 진행 정렬", example = "")
    private int maxRsumeOrd;
}
