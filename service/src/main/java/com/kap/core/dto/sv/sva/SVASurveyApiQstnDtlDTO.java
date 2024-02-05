package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
@Schema(title = "설문관리 질문 테이블")
public class SVASurveyApiQstnDtlDTO{

    @Schema(title = "설문순번", example = "")
    private Integer srvySeq;

    @Schema(title = "설문구분", example = "")
    private String srvyCd;

    @Schema(title = "설문타입", example = "")
    private String srvyType;

    @Schema(title = "질문제목", example = "")
    private String qstn;

    @Schema(title = "1번보기", example = "")
    private String exmpl1;
    @Schema(title = "2번보기", example = "")
    private String exmpl2;
    @Schema(title = "3번보기", example = "")
    private String exmpl3;
    @Schema(title = "4번보기", example = "")
    private String exmpl4;
    @Schema(title = "5번보기", example = "")
    private String exmpl5;
    @Schema(title = "6번보기", example = "")
    private String exmpl6;
    @Schema(title = "7번보기", example = "")
    private String exmpl7;
    @Schema(title = "8번보기", example = "")
    private String exmpl8;
    @Schema(title = "9번보기", example = "")
    private String exmpl9;
    @Schema(title = "10번보기", example = "")
    private String exmpl10;

}
