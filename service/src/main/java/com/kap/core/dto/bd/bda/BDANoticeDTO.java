package com.kap.core.dto.bd.bda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  공지사항 마스터 DTO
 *
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20    장두석              최초 생성
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
@Schema(title = "공지사항 마스터")
public class BDANoticeDTO extends BaseDTO {

    @Schema(title = "공지사항 순번", example = "숫자")
    private Integer ntfySeq;

    @Schema(title = "중요공지 설정")
    private String ntfyYn;

    @Schema(title = "중요공지 노출 시작일시")
    private String expsStrtDtm;

    @Schema(title = "중요공지 노출 종료일시")
    private String expsEndDtm;

    @Schema(title = "중요공지 상시 여부")
    private String odtmYn;

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

    @Schema(title = "노출여부")
    private String expsYn;

    @Schema(title = "조회수")
    private Integer readCnt;

    @Schema(title = "다음글 순번")
    private String nextSeq;

    @Schema(title = "이전글 순번")
    private String prevSeq;

    @Schema(title = "다음글 제목")
    private String nextTitl;

    @Schema(title = "이전 글 제목")
    private String prevTitl;

    @Schema(title = "사용자 검색값")
    private String srchVal;

    @Schema(title = "최신글 여부", example = "Y")
    private String newPostYn;

    @Schema(title = "노출여부 리스트")
    @Hidden
    private List<String> expsYnList;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<BDANoticeDTO> list;

    @Schema(title = "중요공지 조회 리스트")
    @Hidden
    private List<BDANoticeDTO> mainPostList;

    @Schema(title = "사용자 메인여부", example = "Y/N")
    @Builder.Default
    private String mainYn = "N";
}
