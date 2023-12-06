package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
public class SVASurveyMstInsertDTO extends BaseDTO {

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "제목")
    private String titl;

    @Schema(title = "유형코드")
    private String typeCd;

    @Schema(title = "척도코드")
    private String msrStndCd;

    @Schema(title = "척도사용여부")
    private String msrYn;

    @Schema(title = "응답분", example = "숫자")
    private Integer rspnMm;

    @Schema(title = "내용")
    private String cntn;

    @Schema(title = "노출 여부")
    private String expsYn;

    @Schema(title = "수정 가능 여부")
    @Hidden
    @Builder.Default
    private boolean posbChg = true;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;
    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;
    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;
    @Schema(title = "수정ID")
    private String modId;

    @Schema(title = "설문관리 질문 리스트")
    private List<SVASurveyQstnDtlDTO> svSurveyQstnDtlList;

}
