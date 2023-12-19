package com.kap.core.dto.mp.mpi;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  탈퇴
 *
 * @author 양현우
 * @since 2023.12.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.19  탈퇴         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPIWthdrwDto extends BaseDTO {

    @Schema(title = "사용자번호", example = "1")
    private String memSeq;

    @Schema(title = "탈퇴코드", example = "1")
    private String wthdrwRsnCd;

    @Schema(title = "기타 탈퇴 코드", example = "1")
    private String wthdrwRsnEtcNm;
}