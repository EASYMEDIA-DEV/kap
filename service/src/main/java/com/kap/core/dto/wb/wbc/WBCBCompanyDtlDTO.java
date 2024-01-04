package com.kap.core.dto.wb.wbc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@Schema(title = "신청부품사 업종 상세")
public class WBCBCompanyDtlDTO extends BaseDTO {

    @Schema(title = "사업자번호", example = "숫자")
    private String bsnmNo;

    @Schema(title = "업종순번", example = "숫자")
    private Integer cbsnSeq;

    @Schema(title = "명", example = "")
    private String nm;

    @Schema(title = "점수", example = "")
    private String score;

    @Schema(title = "년도", example = "")
    private Integer year;

    @Schema(title = "인증회사명", example = "")
    private String crtfnCmpnNm;

}
