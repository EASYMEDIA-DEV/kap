package com.kap.core.dto.wb.wbh;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
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
@Schema(title = "사업관리 상세")
public class WBHAValidDtlDTO extends BaseDTO {

    @Schema(title = "유효순번", example = "숫자")
    private Integer validSeq;

    @Schema(title = "옵션코드", example = "")
    private String optnCd;

    @Schema(title = "옵션코드 리스트", example = "")
    private List optnCdList;

    @Schema(title = "옵션명", example = "")
    private String optnNm;
    
    @Schema(title = "옵션순서", example = "숫자")
    private int optnOrd;

    

}
