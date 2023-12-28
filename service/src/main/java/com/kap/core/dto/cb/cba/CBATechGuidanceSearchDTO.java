package com.kap.core.dto.cb.cba;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  컨설팅 기술 지도 DTO
 *
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  임서화         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class CBATechGuidanceSearchDTO extends BaseDTO {
    @Schema(title = "컨설팅순번", example = "숫자")
    private Integer cnstgSeq;
    @Schema(title = "부품사 구분 리스트")
    private List<String> ctgryCdList;
    @Schema(title = "지도분야")
    private String appctnFidCd;
    @Schema(title = "진행상태")
    private String rsumeSttsCd;
    @Schema(title = "리스트")
    private List<CBATechGuidanceInsertDTO> list;

    @Schema(title = "엑셀다운로드사유")
    private String rsn;
    @Schema(title = "엑셀여부")
    private String excelYn;
}
