package com.kap.core.dto.wb.wbf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
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
@Schema(title = "스마트공장구축 회차 검색")
public class WBFASmartRoundSearchDTO extends BaseDTO {

    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;
    @Schema(title = "년도", example = "숫자")
    private Integer year;
    @Schema(title = "회차", example = "숫자")
    private Integer episd;
    @Schema(title = "접수시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String accsStrtDtm;
    @Schema(title = "접수종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String accsEndDtm;
    @Schema(title = "사업시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnStrtDtm;
    @Schema(title = "사업종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnEndDtm;
    @Schema(title = "진행상태", example = "")
    private String dateState;
    @Schema(title = "노출 여부")
    private String expsYn;
    @Schema(title = "검색조건", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;
    @Schema(title = "진행상태 코드 리스트")
    private List CdList;
    @Schema(title = "노출여부 리스트")
    private List expsYnList;
    @Schema(title = "유형 옵션 구분")
    private String optnCd;
    @Schema(title = "진행상태 코드 리스트")
    private List carbonCdList;

    @Schema(title = "조회 리스트")
    private List<WBFASmartRoundSearchDTO> list;

}
