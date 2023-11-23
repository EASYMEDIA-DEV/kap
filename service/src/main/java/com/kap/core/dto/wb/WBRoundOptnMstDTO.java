package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 회차 스마트공장 사업옵션 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.23  김동현         최초 생성
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
@Schema(title = "상생사업관리 회차 스마트공장 사업옵션 마스터")
public class WBRoundOptnMstDTO {

    @Schema(title = "사업옵션순번", example = "숫자")
    private int bsnOptnSeq;

    @Schema(title = "회차순번", example = "숫자")
    private int episdSeq;

    @Schema(title = "옵션구분", example = "숫자")
    private String optnCd;

    @Schema(title = "옵션명", example = "숫자")
    private String optnNm;

    @Schema(title = "옵션순서", example = "숫자")
    private int optnOrd;

    @Schema(title = "등록ID", example = "숫자")
    private int regId;

    @Schema(title = "등록IP", example = "숫자")
    private int regIp;

    @Schema(title = "등록일시", example = "숫자")
    private int regDtm;

    @Schema(title = "수정ID", example = "숫자")
    private int modId;

    @Schema(title = "수정IP", example = "숫자")
    private int modIp;

    @Schema(title = "수정일시", example = "숫자")
    private int modDtm;

    List<WBRoundOptnMstDTO> list;

}
