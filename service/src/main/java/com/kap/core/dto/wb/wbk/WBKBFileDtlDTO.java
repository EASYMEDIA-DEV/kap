package com.kap.core.dto.wb.wbk;

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
@Schema(title = "상생신청 미래차공모전 팀원 상세")
public class WBKBFileDtlDTO extends BaseDTO {

    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    @Schema(title = "파일코드", example = "숫자")
    private String fileCd;
    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    @Schema(title = "파일순번", example = "숫자")
    private Integer atchFileSeq;
    @Schema(title = "파일코드", example = "숫자")
    private String fisrtFileSeq;
    @Schema(title = "파일순번", example = "숫자")
    private Integer lastFileSeq;

    @Schema(title = "스텝 파일 리스트")
    private List<WBKBFileDtlDTO> FileDtlList;
}
