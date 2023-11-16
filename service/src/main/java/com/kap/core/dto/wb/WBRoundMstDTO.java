package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 회차 마스터 DTO
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
@Schema(title = "상생사업관리 회차 마스터")
public class WBRoundMstDTO extends BaseDTO {

    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
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

    @Schema(title = "장소순번", example = "숫자")
    private int placeSeq;

    @Schema(title = "추가공지내용", example = "Y")
    private String addNtfyCntn;

    @Schema(title = "사업자료파일순번", example = "숫자")
    private int bsnMatlsFileSeq;

    @Schema(title = "시상식파일순번", example = "숫자")
    private int wdcrmFileSeq;

    @Schema(title = "노출 여부")
    private String expsYn;

    @Schema(title = "진행상태", example = "")
    private String dateState;

    @Schema(title = "회차 조회 결과 리스트")
    private List<WBRoundMstDTO> list;

    @Schema(title = "지급차수 리스트")
    private List<WBOrderMstDto> giveList;

}
