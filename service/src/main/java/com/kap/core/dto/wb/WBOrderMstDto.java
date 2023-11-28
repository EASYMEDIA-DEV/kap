package com.kap.core.dto.wb;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 지급 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.08  김대성         최초 생성
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
@Schema(title = "상생사업관리 지급 상세")
public class WBOrderMstDto extends BaseDTO {


    @Schema(title = "지급순번", example = "숫자")
    private int giveSeq;
    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;
    @Schema(title = "포상순번", example = "숫자")
    private Integer prizeSeq;

    @Schema(title = "지급정렬", example = "숫자")
    private int giveOrd;

    @Schema(title = "시작 일자", example = "yyyy-MM-dd hh:mm:ss")
    private String strtDt;
    @Schema(title = "종료 일자", example = "yyyy-MM-dd hh:mm:ss")
    private String endDt;

    @Schema(title = "지급차수 리스트")
    private List<WBOrderMstDto> giveList;
    
    @Schema(title = "포상 리스트")
    private List<WBOrderMstDto> prizeList;

    @Schema(title = "훈격코드", example = "코드")
    private String mrtsCd;

    @Schema(title = "포상코드", example = "코드")
    private String prizeCd;

    @Schema(title = "포상금", example = "숫자")
    private int prizePmt;
}
