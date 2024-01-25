package com.kap.core.dto.wb.wbk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
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
public class WBKBRegPartDTO extends BaseDTO {

    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;

    @Schema(title = "팀원순번", example = "숫자")
    private Integer tmmbrSeq;
    
    @Schema(title = "참여자이름", example = "")
    private String name;

    @Schema(title = "참여자 휴대전화번호", example = "")
    private String hpNo;

    @Schema(title = "참여자 이메일", example = "")
    private String email;

    @Schema(title = "참여자 학교명", example = "")
    private String schlNm;

    @Schema(title = "참여자 학년", example = "")
    private String grd;

    @Schema(title = "참여자 학년코드", example = "")
    private String grdCd;

    @Schema(title = "참여자 학년코드", example = "")
    private String grdCdNm;

}
