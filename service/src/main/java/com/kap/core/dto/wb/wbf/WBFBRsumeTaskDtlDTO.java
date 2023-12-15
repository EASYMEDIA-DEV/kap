package com.kap.core.dto.wb.wbf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.wb.WBRsumeFileDtlDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@Schema(title = "스마트공장구축 - 상생신청진행 진행/스마트 상세")
public class WBFBRsumeTaskDtlDTO extends BaseDTO {

    /* Primary Key */
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    /* 진행상세테이블 FK */
    @Schema(title = "신청순번", example = "")
    private Integer appctnSeq;

    @Schema(title = "진행상태코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "진행상태코드", example = "")
    private String rsumeSttsCdNm;
    @Schema(title = "신청상태", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태", example = "")
    private String appctnSttsNm;
    @Schema(title = "신청상태명", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "")
    private String appctnSttsChngDtm;
    @Schema(title = "관리자상태코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태명", example = "")
    private String mngSttsCdNm;
    @Schema(title = "관리상태변경일시", example = "")
    private String mngSttsChngDtm;

    /* 상생 상세 */
    @Schema(title = "과제코드", example = "")
    private String taskCd;
    @Schema(title = "사업유형", example = "")
    private String bsnTypeCd;
    @Schema(title = "출연회사코드", example = "")
    private String aprncCmpnCd;
    @Schema(title = "공급업체명", example = "")
    private String offerCmpnCdNm;
    @Schema(title = "공급업체 사업자등록번호", example = "")
    private String offerBsnmNo;
    @Schema(title = "공급담당자명", example = "")
    private String offerPicNm;
    @Schema(title = "공급담당자휴대전화번호", example = "")
    private String offerPicHpNo;
    @Schema(title = "공급담당자이메일", example = "")
    private String offerPicEmail;
    @Schema(title = "총사업금액", example = "")
    private String ttlBsnPmt;
    @Schema(title = "과제번호", example = "")
    private String taskNo;
    @Schema(title = "점검위원순번", example = "")
    private Integer chkCmssrSeq;
    @Schema(title = "점검위원이름", example = "")
    private String chkCmssrNm;
    @Schema(title = "점검계획일자", example = "")
    private String chkPlanDt;
    @Schema(title = "점검실시일자", example = "")
    private String chkImplmnDt;
    @Schema(title = "평가점수", example = "")
    private Integer examScore;
    @Schema(title = "원가계산기관", example = "")
    private String prmcstClcltnInstt;
    @Schema(title = "의뢰일자", example = "")
    private String rqstDt;
    @Schema(title = "회신일자", example = "")
    private String rplyDt;
    @Schema(title = "정부지원금액", example = "")
    private Integer gvmntSpprtPmt;
    @Schema(title = "대기업출연금액", example = "")
    private Integer mjcmnAprncPmt;
    @Schema(title = "도입기업부담금액", example = "")
    private Integer ntdcnCmpnBrdnPmt;
    @Schema(title = "협약일", example = "")
    private String agrmtDt;
    @Schema(title = "협약일", example = "")
    private String agrmtTermDt;
    @Schema(title = "총금액", example = "")
    private Integer ttlPmt;
    @Schema(title = "등록ID", example = "숫자")
    private String regId;
    @Schema(title = "등록IP", example = "숫자")
    private String regIp;
    @Schema(title = "등록일시", example = "숫자")
    private String regDtm;
    @Schema(title = "수정ID", example = "숫자")
    private String modId;
    @Schema(title = "수정IP", example = "숫자")
    private String modIp;
    @Schema(title = "수정일시", example = "숫자")
    private String modDtm;

    @Schema(title = "스마트화현재코드", example = "")
    private String smtfnPrsntCd;
    @Schema(title = "스마트화목표코드", example = "")
    private String smtfnTrgtCd;
    @Schema(title = "스마트화수준확인코드", example = "")
    private String smtfnLvlChkCd;

    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;

    /* List */
    List<WBFBRsumeTaskDtlDTO> list;

    /* 파일 사용 정보 */
    List<WBRsumeFileDtlDTO> appctnFileInfo;

}
