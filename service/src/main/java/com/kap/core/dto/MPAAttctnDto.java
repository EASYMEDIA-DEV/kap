package com.kap.core.dto;

/**
 *  미래차공모전 신청내역
 *
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  일반사용자         최초 생성
 * </pre>
 */

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPAAttctnDto extends BaseDTO {

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq; //회원순번

    @Schema(title = "사업년도", example = "2022")
    private Integer  year;

    @Schema(title = "팀장명", example = "홍길동")
    private String name;

    @Schema(title = "참여구분", example = "개인")
    private String participationCategory;

    @Schema(title = "카테고리", example = "공존")
    private String ctgryCd;

    @Schema(title = "시상부문", example = "대상")
    private String wdcrmCd;

    @Schema(title = "회차", example = "1")
    private String episd;

    @Schema(title = "1차결과", example = "탈락")
    private String appctnSttsCd;

    @Schema(title = "최종결과", example = "수상")
    private String mngSttsCd;

    private List<MPAAttctnDto> list;

    private String lgnSsnId;

}