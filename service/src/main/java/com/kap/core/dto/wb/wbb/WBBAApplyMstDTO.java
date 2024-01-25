package com.kap.core.dto.wb.wbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.wba.WBAManagementDtlDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 신청 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.28  김태훈         최초 생성
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
@Schema(title = "상생사업 신청 마스터")
public class  WBBAApplyMstDTO extends BaseDTO {

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "회차순번")
    private Integer episdSeq;

    @Schema(title = "회원순번")
    private Integer memSeq;

    @Schema(title = "신청사업자번호")
    private String appctnBsnmNo;

    @Schema(title = "사업자번호", example = "")
    private String bsnmNo;

    @Schema(title = "관리자메모")
    private String admMemo;

    @Schema(title = "관리자메모")
    private String admMemoModDtm;

    @Schema(title = "년도")
    private String year;

    @Schema(title = "회차번호")
    private String episd;

    @Schema(title = "사업코드")
    private String bsnCd;
    
    @Schema(title = "옵션순번")
    private List<Integer> optnSeq;
    
    @Schema(title = "단계명")
    private Integer stageNm;

    @Schema(title = "단계순번")
    private Integer stageOrd;

    @Schema(title = "다음단계명")
    private String nextStageNm;

    @Schema(title = "최고단계 순번")
    private Integer maxStage;

    @Schema(title = "진행순번")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬")
    private Integer rsumeOrd;

    @Schema(title = "진행상태명")
    private String rsumeSttsNm;

    @Schema(title = "관리자 상태코드")
    private String mngSttsCd;

    @Schema(title = "관리자 상태명")
    private String mngSttsNm;

    @Schema(title = "사용자 상태코드")
    private String appctnSttsCd;

    @Schema(title = "사용자 상태명")
    private String appctnSttsNm;

    @Schema(title = "반려사유내용")
    private String rtrnRsnCntn;

    @Schema(title = "파일순번")
    private List<Integer> sbmsnSeqList;
    
    @Schema(title = "신청자변경")
    private String userLogYn;

    @Schema(title = "사업단계 리스트")
    private List<WBAManagementDtlDTO> stepDtlList;

    @Schema(title = "신청 상세 리스트")
    private List<WBBAApplyDtlDTO> applyList;

    @Schema(title = "신청자정보")
    private WBBACompanyDTO wbbcompanyInfo;

    @Schema(title = "사용자이관정보")
    private List<WBBATransDTO> wbbTransDTOList;

    @Schema(title = "사용자이관정보")
    private WBBATransDTO wbbTransDTO;

    @Schema(title = "재귀DTO")
    private WBBAApplyMstDTO wbbaApplyMstDTO;
}















