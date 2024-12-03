package com.kap.core.dto.wb.wbl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생협력체감도조사관리
 *
 * @author 박준희
 * @since 2023.11.20
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
public class WBLSurveyMstSearchDTO extends BaseDTO {

    @Schema(title = "순번", example = "숫자")
    private Integer cxstnSrvSeq;

    @Schema(title = "1차 부품사")
    private String partCmpnNm1;

    @Schema(title = "1차 부품사 코드")
    private String partCmpnCd1;

    @Schema(title = "2차 부품사")
    private String partCmpnNm2;

    @Schema(title = "2차 부품사 코드")
    private String partCmpnCd2;

    @Schema(title = "완료 여부")
    private String cmpltnYn;

    @Schema(title = "참여 코드")
    private String ptcptCd;

    @Schema(title = "참여 완료일시")
    private String ptcptCmpltnDtm;

    @Schema(title = "점수")
    private String score;

    /* 2024-08-06 추가개발 점수 백분율 값 추가 */
    @Schema(title = "척도 문항 사용자 점수에 대한 백분율")
    private double percentage;

    @Schema(title = "대표자명")
    private String rprsntNm;

    @Schema(title = "사업자번호")
    private String bsnmRegNo;

    @Schema(title = "설문참여번호")
    private int srvRspnSeq;




    @Schema(title = "조회 리스트")
    private List<WBLSurveyMstSearchDTO> list;
    @Schema(title = "검색 사용 여부 리스트")
    private List<String> cmpltnYnList;
    private List<String> ptcptCdList;

    @Schema(title = "연도")
    private String year;
    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    @Schema(title = "평균점수", example = "숫자")
    private double avgScore;

    //2024.12.03 추가
    @Schema(title = "평균점수 100점 환산", example = "숫자")
    private double avgScorePercentage;

    @Schema(title = "HKMC 평균점수", example = "숫자")
    private double hkmcAvgScore;

    //2024.12.03 추가
    @Schema(title = "HKMC 평균점수 100점 환산", example = "숫자")
    private double hkmcAvgScorePercentage;

    @Schema(title = "응답업체수", example = "숫자")
    private Integer cnt;

    @Schema(title = "엑셀", example = "Y")
    private String excelYn;

    @Schema(title = "엑셀사유", example = "")
    private String rsn;

    @Schema(title = "인증번호", example = "")
    private String crtfnNo;

    @Schema(title = "응답시간", example = "")
    private String rspnMm;

    @Schema(title = "문항수", example = "숫자")
    private Integer qstnCnt;

    //2024.12.03 추가
    @Schema(title = "사용자 답변수", example = "숫자")
    private Integer countSelect;

    @Schema(title = "설문내용", example = "")
    private String cntn;

    /* 2024-07-08 추가개발 ppt 4, 7 추가 s */
    @Schema(title = "담당자명")
    private String picNm;

    @Schema(title = "전화번호")
    private String telNo;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "인증번호 발송일")
    private String sendDtm;
    /* 2024-07-08 추가개발 ppt 4, 7 추가 e */

    @Schema(title = "설문참여번호 목록")
    private List<Integer> srvRspnSeqList;

    @Schema(title = "문항타입")
    private String srvTypeCd;

    @Schema(title = "문항타입명")
    private String srvTypeNm;

    @Schema(title = "문항순번")
    private String qstnOrd;

    @Schema(title = "문항명")
    private String qstnNm;

    @Schema(title = "설문응답값")
    private String exmplAnswer;

    // 2024-08-23 추가개발
    @Schema(title = "점수 미반영 여부", example = "Y/N")
    @Builder.Default
    private String scoreExclusionYn = "N";


}
