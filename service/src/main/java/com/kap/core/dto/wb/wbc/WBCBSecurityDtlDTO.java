package com.kap.core.dto.wb.wbc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생사업관리 지급 상세 DTO
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
@Schema(title = "탄소배출저감 신청부품사 상세 검색")
public class WBCBSecurityDtlDTO extends BaseDTO {

    @Schema(title = "진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬", example = "숫자")
    private Integer rsumeOrd;

    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "진행상태코드", example = "")
    private String rsumeSttsCd;

    @Schema(title = "진행상태명", example = "")
    private String rsumeSttsNm;

    @Schema(title = "신청상태코드", example = "")
    private String appctnSttsCd;

    @Schema(title = "신청상태명", example = "")
    private String appctnSttsNm;

    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd hh:mm:ss")
    private String appctnSttsChngDtm;
    @Schema(title = "관리상태코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리상태명", example = "")
    private String mngSttsNm;

    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd hh:mm:ss")
    private String mngSttsChngDtm;

    @Schema(title = "반려사유내용", example = "")
    private String rtrnRsnCntn;
}
