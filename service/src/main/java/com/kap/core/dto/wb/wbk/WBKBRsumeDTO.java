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
public class WBKBRsumeDTO extends BaseDTO {

    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    @Schema(title = "진행상태 코드", example = "")
    private String appctnSeq;
    @Schema(title = "진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "신청상태 코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "진행상태값", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "")
    private String appctnSttsChngDtm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "")
    private String mngSttsCdNm;
    @Schema(title = "관리상태변경일시", example = "")
    private String mngSttsChngDtm;
    @Schema(title = "반려 내용")
    private String rtrn_rsn_cntn;

    /*상생 파일 상세*/
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

    @Schema(title = "반려 내용")
    private List<WBKBRsumeDTO> RsumeList;
}
