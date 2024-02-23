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
@Schema(title = "상생 발송 DTO")
public class WBSendDTO extends BaseDTO {

    @Schema(title = "사용자순번", example = "")
    private Integer memSeq;

    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;

    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "사업시작일시", example = "yyyy-MM-dd")
    private String bsnStrtDtm;

    @Schema(title = "사업종료일시", example = "yyyy-MM-dd")
    private String bsnEndDtm;

    @Schema(title = "사업명", example = "")
    private String title;

    @Schema(title = "신청자 이름", example = "")
    private String name;

    @Schema(title = "휴대번호", example = "")
    private String hpNo;

    @Schema(title = "이메일", example = "")
    private String email;

    @Schema(title = "부품사명", example = "")
    private String cmpnNm;

    @Schema(title = "진행순번")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬")
    private Integer rsumeOrd;

    @Schema(title = "이전관리자상태 코드", example = "")
    private String mngSttdCd;
    
    @Schema(title = "단계명", example = "")
    private String stageNm;

    @Schema(title = "사유", example = "")
    private String reason;
}
