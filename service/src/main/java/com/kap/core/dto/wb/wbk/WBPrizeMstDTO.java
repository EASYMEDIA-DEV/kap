package com.kap.core.dto.wb.wbk;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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
@Schema(title = "상생사업관리 포상 상세")
public class WBPrizeMstDTO extends BaseDTO {


    @Schema(title = "포상순번", example = "숫자")
    private int prizeSeq;
    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "시상코드", example = "")
    private String wdcrmCd;

    @Schema(title = "참여구분", example = "")
    private String ptcptType;

    @Schema(title = "포상금액", example = "")
    private int prizePmt;

    @Schema(title = "포상 리스트")
    private List<WBPrizeMstDTO> prizeList;

}
