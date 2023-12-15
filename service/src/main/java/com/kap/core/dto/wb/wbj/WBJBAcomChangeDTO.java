package com.kap.core.dto.wb.wbj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "참여이관로그")
public class WBJBAcomChangeDTO extends BaseDTO {

    @Schema(title = "이관순번")
    private Integer trnsfSeq;

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "이전회원순번")
    private Integer bfreMemSeq;

    @Schema(title = "이전회원이름")
    private String bfreMemNm;

    @Schema(title = "이전회원아이디")
    private String bfreMemId;

    @Schema(title = "이후회원순번")
    private Integer aftrMemSeq;

    @Schema(title = "이후회원이름")
    private String aftrMemNm;

    @Schema(title = "이후회원아이디")
    private String aftrMemId;

    @Schema(title = "리스트", example = "")
    List<WBJBAcomChangeDTO> list;

}
