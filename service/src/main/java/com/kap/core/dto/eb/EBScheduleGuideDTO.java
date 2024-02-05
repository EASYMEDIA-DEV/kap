package com.kap.core.dto.eb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육 안내 메일 객체
 *
 * @author 장두석
 * @since 2024.02.05
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2024.02.05  장두석         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBScheduleGuideDTO extends BaseDTO {

    @Schema(title = "회원명", example = "텍스트")
    private String name;

    @Schema(title = "이메일", example = "텍스트")
    private String email;

    @Schema(title = "과정명", example = "텍스트")
    private String nm;

    @Schema(title = "교육장명", example = "텍스트")
    private String placeNm;

    @Schema(title = "교육 시작일", example = "텍스트")
    private String edctnStrtDtm;

    @Schema(title = "교육 종료일", example = "텍스트")
    private String edctnEndDtm;

    @Schema(title = "부품사명", example = "텍스트")
    private String cmpnNm;



    private List<EBScheduleGuideDTO> list;

}
