package com.kap.core.dto.cb.cba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생협력체감도조사관리
 *
 * @author 장두석
 * @since 2024.11.28
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.11.28  장두석         최초 생성
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
@Schema(title = "기술지도 만족도조사 질문 DTO")
public class CBAConsultSuveyDTO extends BaseDTO {

    /* 질문 */
    @Schema(title = "문항순번")
    private Integer qstnSeq;

    @Schema(title = "문항순서")
    private String qstnOrd;

    @Schema(title = "문항명")
    private String qstnNm;

    
    /* 응답 */
    @Schema(title = "설문참여 컨설팅순번")
    private Integer rfncSeq;

    @Schema(title = "설문응답 값")
    private String exmplAnswer;

}
