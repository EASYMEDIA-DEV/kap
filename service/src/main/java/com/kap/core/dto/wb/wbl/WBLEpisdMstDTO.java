package com.kap.core.dto.wb.wbl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  회차관리
 *
 * @author 박준희
 * @since 2023.11.27
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.27  박준희         최초 생성
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
@Schema(title = "상생협력체감도조사 회차관리")
public class WBLEpisdMstDTO extends BaseDTO {

    @Schema(title = "회차순번", example = "숫자")
    private Integer cxstnCmpnEpisdSeq;
    @Schema(title = "연도")
    private String year;
    @Schema(title = "회차", example = "숫자")
    private Integer episd;
    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;
    @Schema(title = "설문이름")
    private String titl;

    @Schema(title = "회차응답총카운트")
    private String rspnCnt;

    @Schema(title = "조회 리스트")
    private List<WBLEpisdMstDTO> list;

}
