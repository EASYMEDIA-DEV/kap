package com.kap.core.dto.cb.cbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *  경영컨설팅 List DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  이옥정         최초 생성
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
@Schema(title = "경영컨설팅 리스트")
public class CBBManageConsultListDTO extends BaseDTO {
    @Schema(title = "컨설팅순번", example = "숫자")
    private String cnstgSeq;
    @Schema(title = "사업연도")
    private Integer bsnYear;
    @Schema(title = "진행상태코드")
    private String resumeSttsCd;
    @Schema(title = "진행상태코드명")
    private String resumeSttsNm;
    @Schema(title = "부품사명")
    private String cmpnNm;
    @Schema(title = "부품사구분코드")
    private String ctgryCd;
    @Schema(title = "부품사구분코드명")
    private String ctgryNm;
    @Schema(title = "부품사규모코드")
    private String sizeCd;
    @Schema(title = "부품사규모코드명")
    private String sizeNm;
    @Schema(title = "신청 당시 사업자등록번호")
    private String appctnBsnmNo;
    @Schema(title = "매출액(억원)")
    private Integer slsPmt;
    @Schema(title = "부품사 직원수")
    private Integer mpleCnt;
    @Schema(title = "신청분야 코드")
    private String appctnFldCd;
    @Schema(title = "신청분야 코드명")
    private String appctnFldNm;
    @Schema(title = "첫번째 지역 코드")
    private String firstRgnsCd;
    @Schema(title = "첫번째 지역 코드명")
    private String firstRgnsNm;
    @Schema(title = "두번째 지역 코드")
    private String scndRgnsCd;
    @Schema(title = "두번째 지역 코드명")
    private String scndRgnsNm;
    @Schema(title = "SQ 인증 주관사")
    private String crtfnCmpnNm;
    @Schema(title = "주고객사")
    private String mnCmpnNm;
    @Schema(title = "방문일")
    private String vstDt;
    @Schema(title = "위원순번")
    private Integer cmssrSeq;
    @Schema(title = "위원이름")
    private String cmssrNm;
    @Schema(title = "담당위원지도분야 코드")
    private String cmssrCbsnCd;
    @Schema(title = "담당위원지도분야 코드명")
    private String cmssrCbsnNm;
    @Schema(title = "킥오프시작일")
    private String cnstgKickfDt;
    @Schema(title = "킥오프파일순번")
    private Integer kickfFileSeq;
    @Schema(title = "킥오프파일ord")
    private Integer kickfFileOrd;
    @Schema(title = "컨설팅현황코드")
    private String cnstgPscndCd;
    @Schema(title = "컨설팅현황코드명")
    private String cnstgPscndNm;
    @Schema(title = "컨설팅현황일자")
    private String cnstgPscndDt;
    @Schema(title = "렙업자료순번")
    private Integer lvlupFileSeq;
    @Schema(title = "렙업자료ord")
    private Integer lvlupFileOrd;
    @Schema(title = "신청일자")
    private String appctnDt;
    @Schema(title = "렙업일")
    private String lvlupDt;

    @Schema(title = "주생산품1")
    private String mjrPrdct1;
    @Schema(title = "주생산품2")
    private String mjrPrdct2;
    @Schema(title = "주생산품3")
    private String mjrPrdct3;
}
