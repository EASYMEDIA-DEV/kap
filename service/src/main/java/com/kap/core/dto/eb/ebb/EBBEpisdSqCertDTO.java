package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  MY-PAGE > 교육차수 SQ 평가원 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  교육차수 SQ 평가원 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdSqCertDTO extends BaseDTO {
    @Schema(title = "교육순번", example = "숫자")
    private Integer edctnSeq;
    @Schema(title = "회차정렬", example = "숫자")
    private Integer episdOrd;
    @Schema(title = "회차년도", example = "yyyy")
    private Integer episdYear;
    @Schema(title = "교육차수순번", example = "숫자")
    private Integer episdSeq;
    @Schema(title = "카테고리 코드 명", example = "")
    private String ctgryCdNm;
    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;
    @Schema(title = "업종코드", example = "코드")
    private String cbsnCd;
    @Schema(title = "업종코드", example = "코드명")
    private String cbsnCdNm;
    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;
    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;
    @Schema(title = "수요일시", example = "yyyy-mm-dd HH:mm:ss")
    private String cmptnDtm;
    @Schema(title = "자격증연계코드", example = "LCNS_CNNCT02")
    private String lcnsCnnctCd;

    @Schema(title = "참여순번", example = "숫자")
    private Integer ptcptSeq;
    @Schema(title = "학습방식")
    private String stduyMthdCdNm;
    @Schema(title = "학습시간")
    private String stduyDdCdNm;
    @Schema(title = "학습시간")
    private String stduyTimeCdNm;
    @Schema(title = "수료여부")
    private String cmptnYn;
}
