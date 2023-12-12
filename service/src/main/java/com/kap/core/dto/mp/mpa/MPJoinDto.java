package com.kap.core.dto.mp.mpa;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  일반사용자
 *
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  일반사용자         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPJoinDto extends BaseDTO {
    private MPAUserDto mpaUserDto;
    private MPEPartsCompanyDTO mpePartsCompanyDTO;


    @Schema(title = "사업자번호", example = "1")
    private String bsnmNo;

    @Schema(title = "등록여부", example = "1")
    private String bsnmChk;

}