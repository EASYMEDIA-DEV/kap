package com.kap.core.dto.sm.smg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생 사업 관리 DTO
 *
 * @author 임서화
 * @since 2023.10.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2023.10.25    임서화             최초 생성
 *  2023.11.22    장두석             최신화
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
@Schema(title = "상생사업 마스터")
public class SMGWinBusinessDTO extends BaseDTO {

    @Schema(title = "상생사업 순번", example = "숫자")
    private Integer cxstnSeq;

    @Schema(title = "사업명")
    private String bsnNm;

    @Schema(title = "설명")
    private String dsc;

    @Schema(title = "링크")
    private String urlUrl;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "노출여부")
    private String expsYn;

    @Schema(title = "노출순서")
    private Integer expsOrd;


    @Schema(title = "노출여부 리스트")
    @Hidden
    private List<String> expsYnList;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<SMGWinBusinessDTO> list;

    @Schema(title = "노출순서 구분")
    @Hidden
    private String sortType;
}