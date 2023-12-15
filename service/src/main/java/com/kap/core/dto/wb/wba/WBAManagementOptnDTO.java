package com.kap.core.dto.wb.wba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
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
@Schema(title = "상세단계옵션 상세")
public class WBAManagementOptnDTO extends BaseDTO {
    @Schema(title = "옵션순번")
    private Integer optnSeq;

    @Schema(title = "단계순번")
    private Integer stageSeq;

    @Schema(title = "옵션정렬")
    private Integer optnOrd;

    @Schema(title = "옵션명")
    private String optnNm;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "등록 ID")
    private String regId;

    @Schema(title = "등록 IP")
    private String regIp;

    @Schema(title = "등록일시")
    private String regDtm;

    @Schema(title = "수정 ID")
    private String modId;

    @Schema(title = "수정 IP")
    private String modIp;

    @Schema(title = "수정일시")
    private String modDtm;

    //전달받을 파일 리스트
    private List<COFileDTO> optnFileList;
}
