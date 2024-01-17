package com.kap.core.dto.eb.ebd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.MPBEduDto;
import com.kap.core.dto.eb.ebb.EBBEpisdSqCertDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  SQ평가원 검색 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김학규         최초 생성
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
@Schema(title = "SQ평가원 신청 검색")
public class EBDSqCertiSearchDTO extends BaseDTO {
    @Schema(title = "부품사 구분 리스트")
    private List<String> ctgryCdList;
    @Schema(title = "발급 상태 리스트")
    private List<String> issueCdList;
    @Schema(title = "발급 구분 리스트")
    private List<String> examCdList;
    @Schema(title = "자격증 상태 리스트")
    private List<String> certiSttsList;
    @Schema(title = "리스트")
    private List<EBDSqCertiListDTO> list;
    @Schema(title = "조회 회원 순번")
    private Integer memSeq;
    @Schema(title = "조회 자격증 연계 코드")
    private String lcnsCnnctCd;
    @Schema(title = "교육 차수 년도")
    private String episdYear;
    @Schema(title = "교육 차수 정렬")
    private String episdOrd;

    @Schema(title = "필수과목 리스트")
    private List<EBBEpisdSqCertDTO> educationList;

    @Schema(title = "보수과목 리스트")
    private List<EBBEpisdSqCertDTO> educationRepairList;
    private int repairTotalCount = 0;
}
