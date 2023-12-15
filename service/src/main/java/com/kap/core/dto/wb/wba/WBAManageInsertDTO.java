package com.kap.core.dto.wb.wba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  김태훈         최초 생성
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
@Schema(title = "상생사업 마스터")
public class WBAManageInsertDTO  extends BaseDTO {
    @Schema(title = "사업코드")
    private String bsnCd;

    @Schema(title = "사용자메뉴 순번")
    private Integer userMenuSeq;

    @Schema(title = "사용자메뉴명")
    private String userMenuName;

    @Schema(title = "관리자회차메뉴 순번")
    private Integer admEpisdMenuSeq;

    @Schema(title = "관리자회차메뉴명")
    private String admEpisdMenuName;

    @Schema(title = "관리자신청메뉴 순번")
    private Integer admAppctnMenuSeq;

    @Schema(title = "관리자신청메뉴명")
    private String admAppctnMenuName;

    @Schema(title = "등록 ID")
    private String regId;

    @Schema(title = "등록자명")
    private String regName;
    
    @Schema(title = "등록 IP")
    private String regIp;

    @Schema(title = "등록일시")
    private String regDtm;

    @Schema(title = "수정 ID")
    private String modId;

    @Schema(title = "수정자명")
    private String modName;

    @Schema(title = "수정 IP")
    private String modIp;

    @Schema(title = "수정일시")
    private String modDtm;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "단계번호")
    private Integer stageSeq;

    @Schema(title = "옵션번호")
    private Integer optnSeq;

    @Schema(title = "상생사업 단계 리스트")
    private List<WBAManagementDtlDTO> managementDtlList;

}
