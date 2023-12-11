package com.kap.core.dto.wb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "상생신청지원금액 상세")
public class WBSpprtDtlDTO extends BaseDTO {
    
    /* Key */
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "신청상태", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태명", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태명", example = "")
    private String mngSttsCdNm;

    /* 신청지원금 금액 상세 */
    @Schema(title = "지급구분", example = "")
    private String giveType;
    @Schema(title = "접수일자", example = "")
    private String accsDt;
    @Schema(title = "정부지원금액", example = "")
    private Integer gvmntSpprtPmt;
    @Schema(title = "대기업출연금액", example = "")
    private Integer mjcmnAprncPmt;
    @Schema(title = "총금액", example = "")
    private Integer ttlPmt;
    @Schema(title = "은행명", example = "")
    private String bankNm;
    @Schema(title = "예금명", example = "")
    private String acntNo;
    @Schema(title = "수수료금액", example = "")
    private Integer cmssnPmt;
    @Schema(title = "신청상태변경일", example = "")
    private Integer appctnSttsChngDtm;
    @Schema(title = "지급일자", example = "")
    private String giveDt;
    @Schema(title = "지급순번", example = "")
    private Integer giveSeq;
    @Schema(title = "지원신청파일순번", example = "")
    private Integer spprtAppctnFileSeq;
    @Schema(title = "계좌파일순번", example = "")
    private Integer acntFileSeq;
    @Schema(title = "통장파일순번", example = "")
    private Integer bnkbkFileSeq;
    @Schema(title = "기술임차파일순번", example = "")
    private Integer tchlgLseFileSeq;
    @Schema(title = "임차납입파일순번", example = "")
    private Integer lsePayFileSeq;

    /* 파일 저장시 사용 정보 */
    List<WBRsumeFileDtlDTO> appctnFileInfo;

    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    /* List */
    List<WBSpprtDtlDTO> list;

}
