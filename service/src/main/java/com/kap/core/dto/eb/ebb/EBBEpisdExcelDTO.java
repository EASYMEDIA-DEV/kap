package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 엑셀 출력 객체
 *
 * @author 김학규
 * @since 2023.11.24
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.25  교육차수 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdExcelDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "강사1", example = "숫자")
    private Integer isttr1;

    @Schema(title = "강사2", example = "숫자")
    private Integer isttr2;

    @Schema(title = "강사3", example = "숫자")
    private Integer isttr3;

    @Schema(title = "강사4", example = "숫자")
    private Integer isttr4;

    @Schema(title = "강사5", example = "숫자")
    private Integer isttr5;

    @Schema(title = "강사6", example = "숫자")
    private Integer isttr6;

    @Schema(title = "강사명1", example = "숫자")
    private Integer isttrNm1;

    @Schema(title = "강사명2", example = "숫자")
    private Integer isttrNm2;

    @Schema(title = "강사명3", example = "숫자")
    private Integer isttrNm3;

    @Schema(title = "강사명4", example = "숫자")
    private Integer isttrNm4;

    @Schema(title = "강사명5", example = "숫자")
    private Integer isttrNm5;

    @Schema(title = "강사명6", example = "숫자")
    private Integer isttrNm6;



    private List<EBBEpisdExcelDTO> isttrSeqList;
}
