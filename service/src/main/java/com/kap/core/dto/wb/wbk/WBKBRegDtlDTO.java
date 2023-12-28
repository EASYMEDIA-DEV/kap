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
@Schema(title = "상생신청 미래차공모전 상세")
public class WBKBRegDtlDTO extends BaseDTO {

    /* 미래차공모전 상세 */
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;

    /* 팀장 정보*/
    @Schema(title = "팀장이름", example = "")
    private String RdName;
    @Schema(title = "휴대전화번호", example = "")
    private String RdHpNo;
    @Schema(title = "이메일", example = "")
    private String RdEmail;
    @Schema(title = "학교명", example = "")
    private String RdSchlNm;
    @Schema(title = "학년", example = "")
    private Integer RdGrd;
    @Schema(title = "학년코드", example = "")
    private String RdGrdCd;

    @Schema(title = "주제코드", example = "")
    private String themeCd;
    @Schema(title = "상세내용", example = "")
    private String dtlCntn;
    @Schema(title = "시상식코드", example = "")
    private String wdcrmCd;
    @Schema(title = "역대수상자여부", example = "")
    private String hghstWinerYn;
    @Schema(title = "참여구분", example = "")
    private String ptcptType;

    @Schema(title = "미래차 공모전 참여자 리스트")
    private List<WBKBRegPartDTO> partList;

}
