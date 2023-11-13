package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육 시험 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
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
@Schema(title = "교육 시험 마스터")
public class EXGExamMstSearchDTO extends BaseDTO {
    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;
    @Schema(title = "제목")
    private String titl;
    @Schema(title = "개요 내용")
    private String smmryCntn;
    @Schema(title = "노출 여부")
    private String expsYn;
    @Schema(title = "조회 리스트")
    private List<EXGExamMstSearchDTO> list;
    @Schema(title = "검색 사용 여부 리스트")
    private List<String> expsYnList;
}
