package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  설문조사관리
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.02.05  박준희         최초 생성
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
@Schema(title = "설문관리 응답 테이블")
public class SVASurveyApiRspnDtlDTO {

    @Schema(title = "설문순번", example = "")
    private Integer srvySeq;

    @Schema(title = "kap 아이디", example = "")
    private String kapId;

    @Schema(title = "설문구분", example = "")
    private String srvyCd;

    @Schema(title = "설문타입", example = "")
    private String srvyType;

    @Schema(title = "설문답변", example = "")
    private String rply;


}
