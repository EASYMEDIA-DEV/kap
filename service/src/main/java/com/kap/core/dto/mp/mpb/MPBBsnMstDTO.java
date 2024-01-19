package com.kap.core.dto.mp.mpb;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetyMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyMstInsertDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbg.WBGAApplyMstDTO;
import com.kap.core.dto.wb.wbh.WBHAApplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
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
public class MPBBsnMstDTO extends BaseDTO {

    @Schema(title = "사업코드", example = "")
    private String bsnCd;

    @Schema(title = "신청자순번", example = "")
    private Integer appctnSeq;

    @Schema(title = "공통사업", example = "")
    private WBBAApplyMstDTO wbbaApplyMstDTO;

    @Schema(title = "시험계측장비", example = "")
    private WBGAApplyMstDTO exam;

    @Schema(title = "검교정", example = "")
    private WBHAApplyMstDTO equiment;
    
    @Schema(title = "공급망", example = "")
    private WBIBSupplyMstDTO wBIBSupplyMstDTO;

    @Schema(title = "보안환경구축", example = "")
    private WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO;

    @Schema(title = "안전설비구축", example = "")
    private WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO;
    
    @Schema(title = "탄소배출저감", example = "")
    private WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO;

    @Schema(title = "스마트공장", example = "")
    private WBFBRegisterDTO wBFBRegisterDTO;
}
