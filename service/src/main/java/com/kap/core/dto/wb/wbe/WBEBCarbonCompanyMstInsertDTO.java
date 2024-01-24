package com.kap.core.dto.wb.wbe;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 부품사 마스터
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
@Schema(title = "탄소배출저감 신청 마스터")
public class WBEBCarbonCompanyMstInsertDTO extends BaseDTO {

    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;

    @Schema(title = "회차연도", example = "")
    private String year;

    @Schema(title = "회차", example = "")
    private String episd;

    @Schema(title = "사업코드", example = "")
    private String bsnCd;

    @Schema(title = "지급 차수", example = "")
    private List giveOrdList;

    @Schema(title = "회원순번", example = "")
    private Integer memSeq;

    @Schema(title = "이관순번", example = "")
    private Integer transfSeq;

    @Schema(title = "사업자번호", example = "")
    private String bsnmNo;

    @Schema(title = "신청자사업번호", example = "")
    private String appctnBsnmNo;

    @Schema(title = "담당자위원순번", example = "")
    private Integer picCmssrSeq;

    @Schema(title = "종된 사업번호", example = "")
    private String sbrdnBsnmNo;

    @Schema(title = "선급금액여부", example = "")
    private String pmndvPmtYn;

    @Schema(title = "관리자 메모", example = "")
    private String admMemo;

    @Schema(title = "현재 진행 정렬", example = "")
    private int maxRsumeOrd;

    @Schema(title = "진행 순번", example = "")
    private int rsumeSeq;

    @Schema(title = "회원 리스트")
    private List<MPAUserDto> memList;

    @Schema(title = "강사 및 위원")
    private MPCLecturerDTO isttrDtl;

    @Schema(title = "부품사")
    WBEBCompanyDTO companyDtl;

    @Schema(title = "신청 상세 리스트")
    private List<WBEBCarbonCompanyDtlDTO> rsumeDtlList;

    @Schema(title = "신청 진행 상세 리스트")
    private List<WBEBCarbonCompanyPbsnDtlDTO> pbsnDtlList;

    @Schema(title = "상생신청지원금액상세")
    private List<WBEBCarbonCompanySpprtDTO> spprtList;

    @Schema(title = "신청 진행 파일 리스트")
    private List<WBEBCarbonCompanyFileDtlDTO> fileDtlList;

    @Schema(title = "파일 리스트")
    private List<COFileDTO> optnFileList;

}
