package com.kap.core.dto.bd.bdc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  FAQ 마스터 DTO
 *
 * @author 장두석
 * @since 2023.11.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21    장두석              최초 생성
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
@Schema(title = "FAQ 마스터")
public class BDCFaqDTO extends BaseDTO {

    @Schema(title = "FAQ 순번", example = "숫자")
    private Integer faqSeq;

    @Schema(title = "FAQ 구분 코드")
    private String ctgryCd;

    @Schema(title = "제목")
    private String titl;

    @Schema(title = "내용")
    private String cntn;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "조회수")
    private Integer readCnt;

    @Schema(title = "노출여부")
    private String expsYn;


    @Schema(title = "FAQ 구분명")
    private String ctgryName;

    @Schema(title = "노출여부 리스트")
    @Hidden
    private List<String> expsYnList;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<BDCFaqDTO> list;

    @Schema(title = "FAQ 구분 코드 리스트")
    @Hidden
    private List<String> ctgryCdList;
}
