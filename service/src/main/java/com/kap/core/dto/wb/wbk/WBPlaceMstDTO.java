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
@Schema(title = "상생사업관리 포상 상세")
public class WBPlaceMstDTO extends BaseDTO {

    @Schema(title = "장소순번", example = "숫자")
    private int placeSeq;

    @Schema(title = "장소", example = "")
    private String placeNm;

    @Schema(title = "지역 명")
    private String rgnsName;

    @Schema(title = "우편 번호")
    private String zipcode;

    @Schema(title = "기본 주소")
    private String bscAddr;

    @Schema(title = "상세 주소")
    private String dtlAddr;

    @Schema(title = "대표 전화 번호")
    private String rprsntTelNo;

    @Schema(title = "장소 리스트")
    private List<WBPlaceMstDTO> placeList;

}
