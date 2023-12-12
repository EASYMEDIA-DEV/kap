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

    @Schema(title = "회사우편번호", example = "1")
    private String cmpnZipcode;

    @Schema(title = "회사주소", example = "1")
    private String cmpnBscAddr ; //회사 주소

    @Schema(title = "회사상세주소", example = "1")
    private String cmpnDtlAddr ;

    @Schema(title = "회사번호", example = "1")
    private String cmpnTel;

    @Schema(title = "사업자번호", example = "1")
    private String bsnmNo;

    @Schema(title = "등록여부", example = "1")
    private String bsnmChk;

}