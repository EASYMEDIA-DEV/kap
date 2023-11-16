package com.kap.core.dto.eb.ebf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육장 목록 검색 DTO
 *
 * @author 장두석
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15    장두석              최초 생성
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
@Schema(title = "교육 장소 마스터")
public class EBFEduRoomSearchDTO extends BaseDTO {

    @Schema(title = "장소 순번", example = "숫자")
    private Integer placeSeq;

    @Schema(title = "명")
    private String nm;

    @Schema(title = "지역 코드")
    private List<String> rgnsCd;

    @Schema(title = "지역 명")
    private List<String> rgnsNm;

    @Schema(title = "우편 번호")
    private String zipcode;

    @Schema(title = "기본 주소")
    private String bscAddr;

    @Schema(title = "상세 주소")
    private String dtlAddr;

    @Schema(title = "대표 전화 번호")
    private String rprsntTelNo;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<EBFEduRoomSearchDTO> list;

    @Schema(title = "지역 코드 검색 리스트")
    @Hidden
    private List<String> rgnsCdList;
}
