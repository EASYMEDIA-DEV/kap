package com.kap.core.dto.eb.ebd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 *  SQ평가원 과정 회차 정보 DTO
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
@Schema(title = "SQ평가원 신청 DTO")
public class EBDEdctnEdisdDTO {
    @Schema(title = "SQ평가원 신청순번")
    @NotNull
    private Integer detailsKey;
    @Schema(title = "SQ평가원 신청순번")
    private Integer examAppctnSeq;
    @Schema(title = "과정명")
    private String nm;
    @Schema(title = "학습 방식 코드")
    private String stduyMthdCd;
    @Schema(title = "학습 방식 코드 명")
    private String stduyMthdCdNm;
    @Schema(title = "과정 분류 코드")
    private String ctgryCd;
    @Schema(title = "과정 분류 코드 명")
    private String ctgryCdNm;
    @Schema(title = "학습 시간 코드")
    private String stduyTimeCd;
    @Schema(title = "학습 시간 코드 명")
    private String stduyTimeCdNm;
    @Schema(title = "학습 일 코드")
    private String stduyDdCd;
    @Schema(title = "학습 일 코드 명")
    private String stduyDdCdNm;
    @Schema(title = "교육 순번")
    private String edctnSeq;
    @Schema(title = "교육 년도")
    private String episdYear;
    @Schema(title = "교육 회차")
    private String episdOrd;
    @Schema(title = "업종 코드")
    private String cbsnCd;
    @Schema(title = "업종 코드 명")
    private String cbsnCdNm;
    @Schema(title = "접수 시작 일시")
    private String accsStrtDtm;
    @Schema(title = "접수 종료 일시")
    private String accsEndDtm;
    @Schema(title = "교육 시작 일시")
    private String edctnStrtDtm;
    @Schema(title = "교육 종료 일시")
    private String edctnEndDtm;
    @Schema(title = "강사 명")
    private String isttrNm;
    @Schema(title = "정원")
    private String fxnumCnt;
    @Schema(title = "모집 방식 코드")
    private String rcrmtMthdCd;
    @Schema(title = "모집 방식 코드 명")
    private String rcrmtMthdCdNm;
    @Schema(title = "문의 담당자 명")
    private String picNm;
    @Schema(title = "문의 담당자 이메일")
    private String picEmail;
    @Schema(title = "문의 담당자 전화번호")
    private String picTelNo;
    @Schema(title = "교육 장소 코드")
    private String placeSeq;
    @Schema(title = "교육 장소 명")
    private String placeNm;
    @Schema(title = "GPC ID")
    private String gpcId;
    @Schema(title = "SQ 평가원 구분 코드")
    private String examCd;
    @Schema(title = "발급상태 코드")
    private String issueCd;
    @Schema(title = "반려 사유")
    private String rtrnRsn;
    @Schema(title = "신청자ID")
    private String regId;
    @Schema(title = "신청자IP")
    private String regIp;
    @Schema(title = "신청일시")
    private String regDtm;
    @Schema(title = "최종수정자ID")
    private String modId;
    @Schema(title = "최종수정자IP")
    private String modIp;
    @Schema(title = "최종수정일시")
    private String modDtm;

    @Schema(title = "회원 순번")
    private Integer memSeq;
    @Schema(title = "취득 일시")
    private String acqsnDtm;
}
