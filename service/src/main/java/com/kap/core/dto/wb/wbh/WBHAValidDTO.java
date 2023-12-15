package com.kap.core.dto.wb.wbh;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.wbg.WBGAValidDtlDTO;
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
@Schema(title = "사업관리 마스터")
public class WBHAValidDTO extends BaseDTO {

    @Schema(title = "유효순번", example = "숫자")
    private Integer validSeq;

    @Schema(title = "사업코드", example = "")
    private String bsnCd;

    @Schema(title = "기준매출금액", example = "숫자")
    private int stndSlsPmt;

    @Schema(title = "상세 리스트", example = "")
    List<WBGAValidDtlDTO> dtlList;


}
