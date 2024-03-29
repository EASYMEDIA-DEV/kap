package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
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
public class EBBLctrDTO extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    private Integer episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "강의순번", example = "숫자")
    @NotNull
    private Integer lctrSeq;

    @Schema(title = "현재강의순번", example = "숫자")
    @NotNull
    private Integer nowLctrSeq;

    @Schema(title = "참여자순번", example = "숫자")
    private Integer ptcptSeq;

    @Schema(title = "강의명", example = "텍스트")
    private String nm;

    @Schema(title = "URL", example = "텍스트")
    private String url;

    @Schema(title = "시간", example = "텍스트")
    private String time;

    @Schema(title = "썸네일파일 순번", example = "텍스트")
    private Integer thnlFileSeq;

    @Schema(title = "썸네일파일 경로", example = "텍스트")
    private String webPath;

    @Schema(title = "교육 수강일", example = "YYYY")
    private String lctrDtm;

    @Schema(title = "마이페이지에서만 사용하는 조건절", example = "Y/N")
    @Builder.Default
    private String mypageYn = "N";


    private List<EBBLctrDTO> list;

}
