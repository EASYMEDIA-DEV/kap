package com.kap.core.dto.wb.wbh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  검교정 계측장비 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.11  김태훈         최초 생성
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
@Schema(title = "검교정 계측장비 마스터")
public class WBHAMsEuipmentDTO extends BaseDTO {

    @Schema(title = "진행순번")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬")
    private Integer rsumeOrd;

    @Schema(title = "지원상한선여부")
    private String spprtCllngYn;

    @Schema(title = "담당위원순번")
    private String chkCmssrSeq;

    @Schema(title = "담당위원이름")
    private String chkCmssrNm;

    @Schema(title = "접수일자")
    private String accsDt;

    @Schema(title = "투자금액")
    private String nvstmPmt;

    @Schema(title = "검교정비용금액")
    private String clbtnExpnsPmt;

    @Schema(title = "실제지급일자")
    private String realGiveDt;

    @Schema(title = "재단지원금액")
    private String fndnSpprtPmt;

    @Schema(title = "업체부담금액")
    private String entBrdnPmt;

    @Schema(title = "자동차시험연구소지원금액")
    private String carExamLbrtySpptPmt;

}
