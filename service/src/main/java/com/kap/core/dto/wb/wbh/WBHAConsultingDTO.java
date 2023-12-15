package com.kap.core.dto.wb.wbh;

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
@Schema(title = "컨설팅 내역")
public class WBHAConsultingDTO extends BaseDTO {

    @Schema(title = "사업년도")
    private String bsnYear;

    @Schema(title = "사업구분")
    private String cnstgNm;

    @Schema(title = "진행상태")
    private String rsumeSttsNm;

    @Schema(title = "부품사명")
    private String cmpnNm;

    @Schema(title = "구분")
    private String ctgryNm;

    @Schema(title = "규모")
    private String sizeNm;

    @Schema(title = "사업자등록번호")
    private String appctnBsnmNo;

    @Schema(title = "지역")
    private String areaName;

    @Schema(title = "신청분야/업종")
    private String appctnFldNm;

    @Schema(title = "신청소재지")
    private String rgnsName;

    @Schema(title = "담당위원")
    private String cmssrNm;

    @Schema(title = "리스트")
    List<WBHAConsultingDTO> list;
}
