package com.kap.core.dto.wb.wbd;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  참여이관 로그 DTO
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
@Schema(title = "보안환경구축 신청부품사 검색")
public class WBDBSafetyTrnsfDTO extends BaseDTO {
    
    @Schema(title = "이관순번", example = "숫자")
    private Integer trnsfSeq;

    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "이전회원순번", example = "숫자")
    private Integer bfreMemSeq;

    @Schema(title = "이전회원명", example = "")
    private String bfreMemNm;

    @Schema(title = "이후회원순번", example = "숫자")
    private Integer aftrMemSeq;

    @Schema(title = "이후회원명", example = "")
    private String aftrMemNm;

    @Schema(title = "조회 리스트")
    private List<WBDBSafetyTrnsfDTO> list;

    @Schema(title = "관리자명", example = "")
    private String regNm;
    
}
