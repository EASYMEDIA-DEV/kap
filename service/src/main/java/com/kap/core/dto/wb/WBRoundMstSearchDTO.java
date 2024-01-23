package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
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
public class WBRoundMstSearchDTO extends BaseDTO {

    @Schema(title = "사업코드", example = "")
    private String bsnCd;

    @Schema(title = "사업명", example = "")
    private String bsnNm;
    
    @Schema(title = "회차순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "년도", example = "숫자")
    private Integer year;
    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    @Schema(title = "멤버순번", example = "")
    private Integer memSeq;

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

    @Schema(title = "노출순서")
    private Integer ordFlag;

    @Schema(title = "옵션순번")
    private Integer stageOrd;

    @Schema(title = "검색조건", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;

    @Schema(title = "진행상태 코드 리스트")
    private List carbonCdList;
    @Schema(title = "노출여부 리스트")
    private List expsYnList;

    @Schema(title = "조회 리스트")
    private List<WBRoundMstSearchDTO> list;

    @Schema(title = "옵션파일 리스트")
    private List<WBAManagementOptnDTO> optnList;

    @Schema(title = "양식 관리 파일 리스트")
    private List<SMJFormDTO> smjList;

    @Schema(title = "파일 시퀀스")
    private Integer fileSeq;

}
