package com.kap.core.dto.wb.wbl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생협력체감도조사관리
 *
 * @author 박준희
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  박준희         최초 생성
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
@Schema(title = "상생협력체감도조사 마스터")
public class WBLSurveyMstSearchDTO extends BaseDTO {

    @Schema(title = "순번", example = "숫자")
    private Integer cxstnSrvSeq;

    @Schema(title = "연도")
    private String year;

    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    @Schema(title = "1차 부품사")
    private String partCmpnNm1;

    @Schema(title = "1차 부품사 코드")
    private String partCmpnCd1;

    @Schema(title = "2차 부품사")
    private String partCmpnNm2;

    @Schema(title = "2차 부품사 코드")
    private String partCmpnCd2;

    @Schema(title = "참여 여부")
    private String cmpltnYn;

    @Schema(title = "참여 완료일시")
    private String ptcptCmpltnDtm;

    @Schema(title = "조회 리스트")
    private List<WBLSurveyMstSearchDTO> list;
    @Schema(title = "검색 사용 여부 리스트")
    private List<String> cmpltnYnList;

}
