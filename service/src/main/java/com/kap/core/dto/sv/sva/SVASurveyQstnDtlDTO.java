package com.kap.core.dto.sv.sva;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  설문조사관리
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  박준희         최초 생성
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
@Schema(title = "설문관리 질문 테이블")
public class SVASurveyQstnDtlDTO extends BaseDTO  {

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "질문순번", example = "숫자")
    private Integer qstnSeq;

    @Schema(title = "카테고리코드", example = "")
    @NotNull
    private String ctgryCd;

    @Schema(title = "설문유형코드", example = "")
    @NotNull
    private String srvTypeCd;

    @Schema(title = "부모질문순번", example = "숫자")
    @NotNull
    private Integer parntQstnSeq;

    @Schema(title = "정렬", example = "숫자")
    @NotNull
    private Integer dpth;

    @Schema(title = "질문명", example = "")
    @NotNull
    private String qstnNm;

    @Schema(title = "필수여부", example = "")
    @NotNull
    private String ncsYn;

    @Schema(title = "질문순번", example = "")
    @NotNull
    private Integer qstnOrd;
    
    @Schema(title = "부모질문순번", example = "")
    @NotNull
    private Integer parntQstnOrd;

    @Schema(title = "응답갯수", example = "")
    @NotNull
    private Integer exmplCnt;

    @Schema(title = "그룹별 순서", example = "")
    @NotNull
    private Integer qstnGrpOrd;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;
    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;
    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;
    @Schema(title = "수정ID")
    private String modId;

    @Schema(title = "설문관리 보기 리스트")
    private List<SVASurveyExmplDtlDTO> svSurveyExmplDtlList;

    //코드ID
    private String cdId;
    //코드
    private String cd;
    //코드명
    private String cdNm;



}
