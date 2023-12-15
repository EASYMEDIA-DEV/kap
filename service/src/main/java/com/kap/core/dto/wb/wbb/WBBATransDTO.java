package com.kap.core.dto.wb.wbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 상생참여이관로그 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.28  김태훈         최초 생성
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
@Schema(title = "상생사업 상생참여이관로그")
public class WBBATransDTO extends BaseDTO {

    @Schema(title = "이관순번")
    private Integer trnsfSeq;

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "이전회원순번")
    private Integer bfreMemSeq;

    @Schema(title = "이전회원이름")
    private String bfreMemNm;

    @Schema(title = "이후회원순번")
    private Integer aftrMemSeq;

    @Schema(title = "이후회원이름")
    private String aftrMemNm;

    @Schema(title = "리스트", example = "")
    List<WBBATransDTO> list;
}
