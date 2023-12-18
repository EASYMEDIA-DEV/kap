package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  회사업종 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.22  김동현         최초 생성
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
@Schema(title = "회사업종 상세 테이블")
public class WBCompanyDetailMstDTO extends BaseDTO {

    @Schema(title = "업종순번")
    private String bsnmNo;

    @Schema(title = "업종순번")
    private Integer cbsnSeq;

    @Schema(title = "명")
    private String nm;

    @Schema(title = "점수")
    private Integer score;

    @Schema(title = "년도")
    private Integer year;

    @Schema(title = "인증회사명")
    private String crtfnCmpnNm;

    @Schema(title = "등록ID")
    private String regId;

    @Schema(title = "등록IP")
    private String regIp;

    @Schema(title = "등록일시")
    private String regDtm;

    @Schema(title = "수정ID ")
    private String modId;

    @Schema(title = "수정IP")
    private String modIp;

    @Schema(title = "수정일시")
    private String modDtm;

    List<WBCompanyDetailMstDTO> list;

}
