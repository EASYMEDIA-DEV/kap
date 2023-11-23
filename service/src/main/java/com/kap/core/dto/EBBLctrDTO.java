package com.kap.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육차수 교육강의 상세 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  교육강의 상세 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBLctrDTO extends BaseDTO  {

    @Schema(title = "교육순번", example = "숫자")
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    private Integer episdYear;

    @Schema(title = "강의순번", example = "숫자")
    private Integer lctrSeq;

    @Schema(title = "강의명", example = "텍스트")
    private String nm;

    @Schema(title = "URL", example = "텍스트")
    private String url;

    @Schema(title = "시간", example = "텍스트")
    private String time;

    @Schema(title = "썸네일파일 순번", example = "텍스트")
    private Integer thnlFileSeq;

}
