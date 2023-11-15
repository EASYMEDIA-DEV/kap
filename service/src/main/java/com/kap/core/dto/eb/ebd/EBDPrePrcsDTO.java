package com.kap.core.dto.eb.ebd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  SQ평가원 과정 회차 정보 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "SQ평가원 선수과목 DTO")
public class EBDPrePrcsDTO {
    @Schema(title = "과정 분류")
    private String ctgryCdNm;
    @Schema(title = "과정명")
    private String nm;
    @Schema(title = "학습방식")
    private String stduyMthdCdNm;
    @Schema(title = "학습일")
    private String stduyDdCdNm;
    @Schema(title = "학습시간")
    private String stduyTimeCdNm;
    @Schema(title = "년도")
    private String episdYear;
    @Schema(title = "회차")
    private String episdOrd;
    @Schema(title = "업종")
    private String cbsnCdNm;
    @Schema(title = "교육 시작 일시")
    private String edctnStrtDtm;
    @Schema(title = "교육 종료 일시")
    private String edctnEndDtm;
    @Schema(title = "수료 여부")
    private String cmptnYn;
    @Schema(title = "수료 일시")
    private String cmptnDtm;
}
