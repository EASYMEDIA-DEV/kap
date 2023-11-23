package com.kap.core.dto.co;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "관리자, 사용자 로그인 DTO")
public class COAuthLoginDTO extends BaseDTO {
    @Schema(title = "관리자, 사용자 순번")
    private Integer seq;

    @Schema(title = "관리자, 사용자 로그인 ID")
    private String id;
}
