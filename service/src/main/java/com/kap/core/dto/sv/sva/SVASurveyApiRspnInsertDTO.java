package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  설문조사관리
 *
 * @author 박준희
 * @since 2024.02.05
 * @version 1.0
 * @see
 *
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
@Schema(title = "설문 마스터")
public class SVASurveyApiRspnInsertDTO {

    @Schema(title = "kap과정순번")
    private String kapSeq;

    @Schema(title = "kap일정순번")
    private String kapSchdSeq;

    @Schema(title = "설문관리 API 응답 리스트")
    private List<SVASurveyApiRspnDtlDTO> srvyRspnList;


}
