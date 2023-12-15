package com.kap.core.dto.wb;

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
@Schema(title = "상생참여이관로그")
public class WBAppctnTrnsfDtlDTO extends BaseDTO {

    /* PK */
    @Schema(title = "이관번호", example = "")
    private Integer trnsfSeq;
    /* FK */
    @Schema(title = "신청순번", example = "")
    private Integer appctnSeq;
    @Schema(title = "이전회원순번", example = "")
    private String bfreMemSeq;
    @Schema(title = "이후회원순번", example = "")
    private String aftrMemSeq;
    @Schema(title = "등록ID", example = "")
    private String regId;
    @Schema(title = "등록IP", example = "")
    private String regIp;
    @Schema(title = "등록일시", example = "")
    private String regDtm;
    @Schema(title = "수정ID", example = "")
    private String modId;
    @Schema(title = "수정IP", example = "")
    private String modIp;
    @Schema(title = "수정일시", example = "")
    private String modDtm;

    List<WBAppctnTrnsfDtlDTO> list;
}
