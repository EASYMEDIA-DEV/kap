package com.kap.core.dto.wb.wbl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  상생협력체감도조사
 *
 * @author 박준희
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  박준희         최초 생성
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
@Schema(title = "상생협력체감도조사 마스터")
public class WBLSurveyMstInsertDTO extends BaseDTO {

    @Schema(title = "순번", example = "숫자")
    private Integer cxstnSrvSeq;

    @Schema(title = "회차관리순번", example = "숫자")
    private Integer cxstnCmpnEpisdSeq;

    @Schema(title = "연도")
    private String year;

    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    @Schema(title = "1차 부품사")
    private String partCmpnNm1;

    @Schema(title = "1차 부품사 코드")
    private String partCmpnCd1;

    @Schema(title = "2차 부품사")
    private String partCmpnNm2;

    @Schema(title = "2차 부품사 코드")
    private String partCmpnCd2;

    @Schema(title = "대표자명")
    private String rprsntNm;

    @Schema(title = "사업자등록번호")
    private String bsnmRegNo;

    @Schema(title = "담당자명")
    private String picNm;

    @Schema(title = "전화번호")
    private String telNo;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "설문타이틀")
    private String titl;

    @Schema(title = "설문응답순번", example = "숫자")
    private Integer srvRspnSeq;

    @Schema(title = "인증번호")
    private String crtfnNo;

    @Schema(title = "참여코드")
    private String ptcptCd;

    @Schema(title = "완료여부")
    private String cmpltnYn;

    @Schema(title = "참여완료일시")
    private String ptcptCmpltnDtm;

    @Schema(title = "점수")
    private String score;

    /* 2024-07-08 추가개발 ppt 3 추가 s */

    @Schema(title = "인증번호 발송일")
    private String sendDtm;
    /* 2024-07-08 추가개발 ppt 3 추가 e */

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;
    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;
    @Schema(title = "등록ID")
    private String regId;
    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;
    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;
    @Schema(title = "수정ID")
    private String modId;

    @Schema(title = "응답시간", example = "")
    private String rspnMm;


    @Schema(title = "문항수", example = "숫자")
    private Integer qstnCnt;

    @Schema(title = "설문내용", example = "")
    private String cntn;

}
