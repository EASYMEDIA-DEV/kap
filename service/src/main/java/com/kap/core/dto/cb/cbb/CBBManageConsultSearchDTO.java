package com.kap.core.dto.cb.cbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  경영컨설팅 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  이옥정         최초 생성
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
@Schema(title = "경영컨설팅 신청부품사 검색")
public class CBBManageConsultSearchDTO extends BaseDTO {
    @Schema(title = "컨설팅순번", example = "숫자")
    private Integer cnstgSeq;
    @Schema(title = "부품사 구분 리스트")
    private List<String> ctgryCdList;
    @Schema(title = "지도분야")
    private String appctnFidCd;
    @Schema(title = "진행상태")
    private String rsumeSttsCd;
    @Schema(title = "리스트")
    private List<CBBManageConsultListDTO> list;

    @Schema(title = "엑셀다운로드사유")
    private String rsn;
    @Schema(title = "엑셀여부")
    private String excelYn;

}
