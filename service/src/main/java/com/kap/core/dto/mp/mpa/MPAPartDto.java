package com.kap.core.dto.mp.mpa;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생
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
 *   2023.11.09  상생         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPAPartDto extends BaseDTO {

    @Schema(title = "상태코드")
    private String rsumeSttsCd;

    @Schema(title = "순번")
    private String rsumeSeq;

    @Schema(title = "번호")
    private String memSeq;

    private Boolean chk;

    private int count;

    private List<MPAPartDto> list;

}
