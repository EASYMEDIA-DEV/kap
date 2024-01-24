package com.kap.core.dto.wb.wbg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.wbh.WBHAMsEuipmentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  검교정 신청 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.11  김태훈         최초 생성
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
@Schema(title = "상생사업 신청 상세")
public class WBGAApplyDtlDTO extends BaseDTO {

    @Schema(title = "진행순번")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬")
    private Integer rsumeOrd;

    @Schema(title = "신청순번")
    private Integer appctnSeq;

    @Schema(title = "사업별상태코드")
    private String rsumeSttsCd;

    @Schema(title = "사업별상태명")
    private String rsumeSttsNm;

    @Schema(title = "신청상태코드")
    private String appctnSttsCd;

    @Schema(title = "신청상태코드명")
    private String appctnSttsNm;

    @Schema(title = "신청상태변경일시")
    private String appctnSttsChngDtm;

    @Schema(title = "관리자상태코드")
    private String mngSttsCd;

    @Schema(title = "관리상태명")
    private String mngSttsNm;

    @Schema(title = "관리상태변경일시")
    private String mngSttsChngDtm;

    @Schema(title = "반려사유내용")
    private String rtrnRsnCntn;

    @Schema(title = "사업코드")
    private String bsnCd;

    @Schema(title = "파일순번")
    private Integer fileSeq;

    @Schema(title = "파일이름")
    private String fileNm;

    @Schema(title = "파일코드")
    private String fileCd;

    @Schema(title = "파일코드 리스트")
    private List<String> fileCdList;

    @Schema(title = "신청파일 리스트")
    private List<WBGAApplyDtlDTO> fileInfoList;

    @Schema(title = "대상 장비 리스트")
    private List<WBGAEuipmentDTO> euipmentList;

    @Schema(title = "계측장비 리스트")
    private List<WBGAMsEuipmentDTO> msEquipmentList;

    @Schema(title = "계측장비상세")
    private WBGAMsEuipmentDTO wbgaMsEuipmentDTO;

    @Schema(title = "파일리스트", example = "")
    private List<Integer> fileSeqList;
}
