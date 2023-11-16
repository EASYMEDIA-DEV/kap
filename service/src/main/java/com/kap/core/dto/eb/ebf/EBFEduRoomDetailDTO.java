package com.kap.core.dto.eb.ebf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  교육장 상세 DTO
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
@Schema(title = "교육 장소 상세")
public class EBFEduRoomDetailDTO {

    @Schema(title = "공통 순번")
    private String detailsKey;

    @Schema(title = "장소 순번", example = "숫자")
    private Integer placeSeq;

    @Schema(title = "명")
    private String nm;

    @Schema(title = "지역 코드")
    private String rgnsCd;

    @Schema(title = "지역 명")
    private String rgnsName;

    @Schema(title = "우편 번호")
    private String zipcode;

    @Schema(title = "기본 주소")
    private String bscAddr;

    @Schema(title = "상세 주소")
    private String dtlAddr;

    @Schema(title = "대표 전화 번호")
    private String rprsntTelNo;

    @Schema(title = "등록 ID")
    private String regId;

    @Schema(title = "등록자")
    private String regName;

    @Schema(title = "등록 IP")
    private String regIp;

    @Schema(title = "등록일")
    private String regDtm;

    @Schema(title = "수정 ID")
    private String modId;

    @Schema(title = "수정자")
    private String modName;

    @Schema(title = "수정 IP")
    private String modIp;

    @Schema(title = "수정일")
    private String modDtm;
}
