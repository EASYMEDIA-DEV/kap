package com.kap.core.dto.wb.wbh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  검교정 대장장비 상세 DTO
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
@Schema(title = "검교정 신청 마스터")
public class WBHAEuipmentDTO extends BaseDTO {

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "장비순번")
    private Integer tchlgSeq;

    @Schema(title = "장비정렬")
    private Integer tchlgOrd;

    @Schema(title = "장비명")
    private String tchlgNm;

    @Schema(title = "장비수")
    private Integer tchlgCnt;
}
