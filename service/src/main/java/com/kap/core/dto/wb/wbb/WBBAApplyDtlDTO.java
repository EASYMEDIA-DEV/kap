package com.kap.core.dto.wb.wbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
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
@Schema(title = "상생사업 신청 상세")
public class WBBAApplyDtlDTO extends BaseDTO {

    @Schema(title = "진행순번")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬")
    private Integer rsumeOrd;

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "사업별상태코드")
    private String rsumeSttsCd;

    @Schema(title = "신청상태코드")
    private String appctnSttsCd;

    @Schema(title = "신청상태코드명")
    private String appctnSttsNm;

    @Schema(title = "신청상태변경일시")
    private String appctnSttsChngDtm;

    @Schema(title = "관리자상태코드")
    private String mngSttsCd;

    @Schema(title = "관리상태명")
    private String mngSttsNm;

    @Schema(title = "관리상태변경일시")
    private String mngSttsChngDtm;

    @Schema(title = "반려사유내용")
    private String rtrnRsnCntn;

    @Schema(title = "파일첨부 제출순번")
    private Integer sbmsnSeq;

    @Schema(title = "단계순번")
    private Integer stageSeq;

    @Schema(title = "사업코드")
    private String bsnCd;

    @Schema(title = "단계정렬")
    private Integer stageOrd;

    @Schema(title = "파일여부")
    private String fileYn;

    @Schema(title = "단계명")
    private String stageNm;

    @Schema(title = "옵션순번")
    private Integer optnSeq;

    @Schema(title = "옵션명")
    private String optnNm;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "상생단계 상세 리스트")
    private WBBAApplyDtlDTO applyDtl;

    @Schema(title = "상생단계 상세 리스트")
    private List<WBBAApplyDtlDTO> applyList;

    @Schema(title = "상생단계옵션 상세 리스트")
    private List<WBBAApplyDtlDTO> applyTempOptnList;

    @Schema(title = "상생단계옵션 상세 리스트")
    private List<WBBAApplyDtlDTO> applyOptnList;

}
