package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "상생신청진행 파일상세")
public class WBRsumeFileDtlDTO extends BaseDTO {
    @Schema(title = "진행순번", example = "")
    private Integer rsumeSeq;
    @Schema(title = "진행정렬", example = "")
    private Integer rsumeOrd;
    @Schema(title = "파일코드", example = "")
    private String fileCd;
    @Schema(title = "파일순번", example = "")
    private Integer fileSeq;
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

    /* hidden 파일 유형 Type 코드 값 */
    private String type;
    /* hidden 파일  시퀀스 Key */
    private String seqNm;
}
