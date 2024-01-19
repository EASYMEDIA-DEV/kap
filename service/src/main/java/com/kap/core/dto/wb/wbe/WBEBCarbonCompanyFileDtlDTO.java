package com.kap.core.dto.wb.wbe;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생신청 파일 진행 상세 DTO
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
@Schema(title = "상생신청 파일 진행 상세")
public class WBEBCarbonCompanyFileDtlDTO extends BaseDTO {

    @Schema(title = "진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬", example = "숫자")
    private Integer rsumeOrd;

    @Schema(title = "파일코드", example = "")
    private String fileCd;

    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    @Schema(title = "파일명", example = "숫자")
    private String fileNm;

    @Schema(title = "조회 리스트", example = "")
    List<WBEBCarbonCompanyFileDtlDTO> list;

}
