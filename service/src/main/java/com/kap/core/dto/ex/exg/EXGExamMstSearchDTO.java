package com.kap.core.dto.ex.exg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
public class EXGExamMstSearchDTO extends BaseDTO {

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
    private String titl;
    @Schema(title = "개요 내용")
    private String smmryCntn;
    @Schema(title = "노출 여부")
    private String expsYn;
    @Schema(title = "조회 리스트")
    private List<EXGExamMstSearchDTO> list;
    @Schema(title = "검색 사용 여부 리스트")
    private List<String> expsYnList;

    @Schema(title = "검색 레이어에서 호출 여부", example = "Y")
    @Hidden
    private String srchLayer;

    @Schema(title = "사용자 평가 조회 참여 순번")
    private Integer ptcptSeq;
    @Schema(title = "사용자 평가 조회 회원 순번")
    private Integer memSeq;
    @Schema(title = "질문 보기중 정답만 조회")
    private String canswYn;
}
