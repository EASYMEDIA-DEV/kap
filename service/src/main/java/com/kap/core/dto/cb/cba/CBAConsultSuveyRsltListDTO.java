package com.kap.core.dto.cb.cba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 컨설팅사업 만족도 종합결과 List DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  이옥정         최초 생성
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
@EqualsAndHashCode(callSuper = false)
@Schema(title = "만족도 종합 결과 리스트")
public class CBAConsultSuveyRsltListDTO extends BaseDTO {
    @Schema(title = "컨설팅순번")
    private Integer cnstgSeq;
    @Schema(title = "사업연도")
    private Integer bsnYear;
    @Schema(title = "대상부품사 개수")
    private Integer cmpnCnt;
    @Schema(title = "설문완료 부품사 개수")
    private Integer srvCmpltCmpnCnt;
    @Schema(title = "정규부품사 개수")
    private Integer rglrCmpnCnt;
    @Schema(title = "단기부품사 개수")
    private Integer shrtCmpnCnt;
    @Schema(title = "담당위원 명수")
    private Integer cmssrCnt;
    @Schema(title = "총합점수평균")
    private float ttlAvgScore;
    @Schema(title = "정규점수평균")
    private float rglrAvgScore;
    @Schema(title = "단기점수평규")
    private float shrtAvgScore;

    @Schema(title = "부품사명")
    private String cmpnNm;
    @Schema(title = "위원이름")
    private String cmssrNm;
    @Schema(title = "참여자")
    private String ptcptName;
    @Schema(title = "참여자 직급")
    private String ptcptPstn;
    @Schema(title = "참여자 연락처")
    private String ptcptTelno;
    @Schema(title = "신청업종 코드명")
    private String cbsnNm;
    @Schema(title = "신청분야 코드")
    private String appctnFldCd;
    @Schema(title = "신청분야 코드명")
    private String appctnFldNm;
    @Schema(title = "지도구분 코드명")
    private String guideTypeNm;
    @Schema(title = "설문참여상태")
    private String srvStts;
    @Schema(title = "렙업일")
    private String lvlupDt;
    @Schema(title = "총점(100)")
    private float ttlScore;
    @Schema(title = "지도실적(50)")
    private float guideRsltScore;
    @Schema(title = "의사소통(5)")
    private float cmmnctnScore;
    @Schema(title = "기획력(10)")
    private float plnngabltScore;
    @Schema(title = "실행력(15)")
    private float exctvabltScore;
    @Schema(title = "마인드(5)")
    private float mndScore;
    @Schema(title = "전문지식(15)")
    private float exprtsScore;

    @Schema(title = "검색사업연도")
    private String rtnBsnYear;
    @Schema(title = "검색사업구분")
    private String rtnBsnGubun;

    @Schema(title = "엑셀다운로드사업연도")
    private String excelBsnYear;
    @Schema(title = "엑셀다운로드사유")
    private String rsn;

    @Schema(title = "리스트")
    private List<CBAConsultSuveyRsltListDTO> list;
}
