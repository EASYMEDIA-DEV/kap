package com.kap.core.dto.wb.wba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.12  김태훈         최초 생성
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
@Schema(title = "상세사업 단계 상세")
public class WBAManagementDtlDTO extends BaseDTO {
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

    @Schema(title = "등록 ID")
    private String regId;

    @Schema(title = "등록 IP")
    private String regIp;

    @Schema(title = "등록일시")
    private String regDtm;

    @Schema(title = "수정 ID")
    private String modId;

    @Schema(title = "수정 IP")
    private String modIp;

    @Schema(title = "수정일시")
    private String modDtm;

    @Schema(title = "상생단계옵션 상세 리스트")
    private List<WBAManagementOptnDTO> managementOptnList;
}
