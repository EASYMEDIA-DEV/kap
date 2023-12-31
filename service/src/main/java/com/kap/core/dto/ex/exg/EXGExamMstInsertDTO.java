package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육 시험 마스터 DTO
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
@Schema(title = "교육 시험 마스터")
public class EXGExamMstInsertDTO extends BaseDTO {
    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;

    //카테고리코드 소분류
    @Schema(title = "카테고리코드 소분류", example = "")
    private String ctgryCd;

    //카테고리코드 소분류명
    @Schema(title = "카테고리코드 소분류명", example = "")
    private String ctgryCdNm;


    //카테고리코드 대분류
    @Schema(title = "카테고리코드 대분류", example = "")
    private String prntCd;

    //카테고리코드 대분류명
    @Schema(title = "카테고리코드 대분류명", example = "")
    private String prntCdNm;

    //교육과정 명
    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;

    @Schema(title = "제목")
    @NotNull
    private String titl;

    @Schema(title = "개요 내용")
    @NotNull
    private String smmryCntn;

    @Schema(title = "노출 여부")
    @NotNull
    private String expsYn;

    @Schema(title = "질문 모든 갯수")
    @Hidden
    private Integer qstnSize;

    @Schema(title = "보기 모든 갯수")
    @Hidden
    private Integer exmplSize;

    @Schema(title = "수정 가능 여부")
    @Hidden
    @Builder.Default
    private boolean posbChg = true;

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

    @Schema(title = "교육 시험 질문 리스트")
    private List<EXGExamQstnDtlDTO> exExamQstnDtlList;
}
