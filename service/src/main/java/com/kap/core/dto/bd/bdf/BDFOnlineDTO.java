package com.kap.core.dto.bd.bdf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  Online 마스터 DTO
 *
 * @author 오병호
 * @since 2024.02.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.02.20    오병호              최초 생성
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
@Schema(title = "온라인메뉴얼 마스터")
public class BDFOnlineDTO extends BaseDTO {

    @Schema(title = "온라인메뉴얼 순번", example = "숫자")
    private Integer mnlSeq;

    @Schema(title = "Online 구분 코드")
    private String ctgryCd;

    @Schema(title = "제목")
    private String titl;

    @Schema(title = "내용")
    private String cntn;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "파일정렬값")
    private Integer fileOrd;

    @Schema(title = "파일명")
    private String fileName;

    @Schema(title = "조회수")
    private Integer readCnt;

    @Schema(title = "노출여부")
    private String expsYn;

    @Schema(title = "FAQ 구분명")
    private String ctgryName;

    @Schema(title = "노출여부 리스트")
    @Hidden
    private List<String> expsYnList;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<BDFOnlineDTO> list;

    @Schema(title = "FAQ 구분 코드 리스트")
    @Hidden
    private List<String> ctgryCdList;

    @Schema(title = "사용자 메인여부", example = "Y/N")
    @Builder.Default
    private String mainYn = "N";

    @Schema(title = "사용자 검색값")
    private String srchVal;


}
