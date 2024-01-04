package com.kap.core.dto.wb.wbc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생사업관리 지급 상세 DTO
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
@Schema(title = "상생신청지원금액 상세")
public class WBCBSecuritySpprtDTO extends BaseDTO {

    @Schema(title = "신청지원순번", example = "숫자")
    private Integer appctnSpprtSeq;

    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "지급구분", example = "")
    private String giveType;

    @Schema(title = "접수일자", example = "yyyy-MM-dd hh:mm:ss")
    private String accsDt;

    @Schema(title = "정부지원금액", example = "")
    private String gvmntSpprtPmt;

    @Schema(title = "대기업출연금액", example = "")
    private String mjcmnAprncPmt;

    @Schema(title = "총금액", example = "")
    private String ttlPmt;

    @Schema(title = "은행명", example = "")
    private String bankNm;

    @Schema(title = "계좌번호", example = "")
    private String acntNo;

    @Schema(title = "예금명", example = "")
    private String dpsitNm;

    @Schema(title = "수수료금액", example = "")
    private String cmssnPmt;

    @Schema(title = "신청상태코드", example = "")
    private String appctnSttsCd;

    @Schema(title = "신청상태명", example = "")
    private String appctnSttsNm;

    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd hh:mm:ss")
    private String appctnSttsChngDtm;

    @Schema(title = "지급일자", example = "yyyy-MM-dd hh:mm:ss")
    private String giveDt;

    @Schema(title = "지급순번", example = "숫자")
    private Integer giveSeq;

    @Schema(title = "관리상태코드", example = "")
    private String mngSttsCd;

    @Schema(title = "관리상태명", example = "")
    private String mngSttsNm;

    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd hh:mm:ss")
    private String mngSttsChngDtm;


    @Schema(title = "지원신청파일순번", example = "")
    private Integer spprtAppctnFileSeq;

    @Schema(title = "계좌파일순번", example = "숫자")
    private Integer acntFileSeq;

    @Schema(title = "통장파일순번", example = "숫자")
    private Integer bnkbkFileSeq;

    @Schema(title = "기술임차파일순번", example = "숫자")
    private Integer tchlgLseFileSeq;

    @Schema(title = "임차납입파일순번", example = "숫자")
    private Integer lsePayFileSeq;

    @Schema(title = "협약파일순번", example = "숫자")
    private Integer agrmtFileSeq;

    @Schema(title = "보증보험파일순번", example = "숫자")
    private Integer grnteInsrncFileSeq;

    @Schema(title = "거래파일순번", example = "숫자")
    private Integer blingFileSeq;

    @Schema(title = "매출파일순번", example = "숫자")
    private Integer slsFileSeq;

    @Schema(title = "검수확인파일순번", example = "숫자")
    private Integer insptChkFileSeq;
}
