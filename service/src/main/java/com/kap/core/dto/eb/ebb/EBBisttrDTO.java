package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 강사목록 객체
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
 *   2023.11.24  교육강의 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBisttrDTO extends BaseDTO {

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
    @NotNull
    private Integer isttrSeq;

    @Schema(title = "강사명", example = "텍스트")
    private String name;

    @Schema(title = "소속명", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "직급명", example = "텍스트")
    private String pstnNm;

    @Schema(title = "부서명", example = "텍스트")
    private String deptNm;

    @Schema(title = "약력", example = "텍스트")
    private String spclCntn;


    private List<EBBisttrDTO> isttrSeqList;
}
