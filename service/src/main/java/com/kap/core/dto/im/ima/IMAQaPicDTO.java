package com.kap.core.dto.im.ima;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  1:1 문의 담당자 DTO
 *
 * @author 장두석
 * @since 2023.11.28
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.28    장두석              최초 생성
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
@Schema(title = "1:1문의 담당자 마스터")
public class IMAQaPicDTO extends BaseDTO {

    @Schema(title = "담당자 순번", example = "숫자")
    private Integer picSeq;

    @Schema(title = "부모 카테고리 코드")
    private String parntCtgryCd;

    @Schema(title = "부모 카테고리 이름")
    private String parntCtgryNm;

    @Schema(title = "카테고리 코드")
    private String ctgryCd;

    @Schema(title = "카테고리 이름")
    private String ctgryNm;

    @Schema(title = "담당자 이름")
    private String picNm;

    @Schema(title = "담당자 이메일")
    private String piceMail;

    @Schema(title = "조회 리스트")
    @Hidden
    private List<IMAQaPicDTO> list;

    @Schema(title = "부모 카테고리 (등록용)")
    @Hidden
    private String inqFirPic;

    @Schema(title = "카테고리 (등록용)")
    @Hidden
    private String inqSecPic;
}
