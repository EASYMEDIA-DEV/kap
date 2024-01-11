package com.kap.core.dto.mp.mpb;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbg.WBGAApplyMstDTO;
import com.kap.core.dto.wb.wbh.WBHAApplyMstDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생사업 신청 전체 DTO
 *
 * @author 김태훈
 * @since 2024.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.01.10  김태훈             상생사업 신청 전체 DTO 최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPBBnsMstDTO extends BaseDTO {

    @Schema(title = "사업코드", example = "")
    private String bsnCd;

    @Schema(title = "신청자순번", example = "")
    private Integer appctnSeq;

    @Schema(title = "공통사업", example = "")
    private WBBAApplyMstDTO wbbaApplyMstDTO;

    @Schema(title = "시험계측장비", example = "")
    private WBGAApplyMstDTO wbgaApplyMstDTO;

    @Schema(title = "검교정", example = "")
    private WBHAApplyMstDTO wbhaApplyMstDTO;
}
