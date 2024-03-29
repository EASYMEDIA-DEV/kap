package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  설문조사관리
 *
 * @author 박준희
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  박준희         최초 생성
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
@Schema(title = "설문 마스터")
public class SVASurveyMstSearchDTO extends BaseDTO {

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;
    @Schema(title = "제목")
    private String titl;
    @Schema(title = "내용")
    private String cntn;
    @Schema(title = "노출 여부")
    private String expsYn;
    @Schema(title = "설문유형")
    private String typeNm;

    @Schema(title = "설문유형코드")
    private String typeCd;

    @Schema(title = "조회 리스트")
    private List<SVASurveyMstSearchDTO> list;
    @Schema(title = "검색 사용 여부 리스트")
    private List<String> expsYnList;
    private List<String> typeCdList;

    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    @Hidden
    private String srchLayer;

    @Schema(title = "설문응답순번", example = "숫자")
    private Integer srvRspnSeq;

    @Schema(title = "참조코드")
    private String rfncCd;

    @Schema(title = "참조순번", example = "숫자")
    private Integer rfncSeq;

    @Schema(title = "교육회차순번", example = "숫자")
    private Integer episdSeq;



}
