package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 *  교육차수 교육예산지출상세 상세 객체
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
 *   2023.11.24  교육차수 교육예산지출 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBBdgetDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    @Builder.Default
    private Integer episdOrd = 1;

    @Schema(title = "코드", example = "공통코드")
    @NotNull
    private String cd;

    @Schema(title = "금액", example = "공통코드")
    @NotNull
    private Integer pmt;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "비고", example = "텍스트")
    @NotNull
    private String etcNm;




}
