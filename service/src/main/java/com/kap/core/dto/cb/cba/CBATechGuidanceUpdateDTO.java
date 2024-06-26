package com.kap.core.dto.cb.cba;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  컨설팅 기술 지도 DTO
 *
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.14  임서화         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class CBATechGuidanceUpdateDTO extends BaseDTO {
    // 부품사 정보
    @Schema(title = "컨설팅 순번")
    private Integer cnstgSeq;
    @Schema(title = "신청사업자번호")
    private String bsnmNo;

    @Schema(title = "사전심사결과")
    private String bfreJdgmtRsltCd;
    @Schema(title = "사전심사결과 의견")
    private String bfreJdgmtRsltCntn;
    @Schema(title = "방문자")
    private String vstrNm;
    @Schema(title = "면담자")
    private String ntrvrNm;
    @Schema(title = "초도방문일")
    private String vstDt;
    @Schema(title = "초도방문결과")
    private String initVstRsltCd;
    @Schema(title = "초도방문탈락사유")
    private String initVstRsltCntn;
    @Schema(title = "초도방문의견")
    private String initVstOpnnCntn;
    @Schema(title = "초도방문코드")
    private String initVstOpnnCd;
    @Schema(title = "담당위원시퀀스")
    private Integer cmssrSeq;
    @Schema(title = "담당위원이름")
    private String cmssrName;
    @Schema(title = "지도구분")
    private String guideTypeCd;
    @Schema(title = "지도현황")
    private String guidePscndCd;
    @Schema(title = "지도현황명")
    private String guidePscndCdNm;
    @Schema(title = "지도현황일자")
    private String guidePscndDt;
    @Schema(title = "지도착수일")
    private String guideBgnDt;
    @Schema(title = "킥오프일")
    private String guideKickfDt;
    @Schema(title = "연기/취소 사유")
    private String xtnsnCnclRsn;

    // 사업진행 상세
    @Schema(title = "불량개선이전율")
    private Double fltyImpvmBfreRate;
    @Schema(title = "불량개선이후률")
    private Double fltyImpvmAftrRate;
    @Schema(title = "불량개선율")
    private Double fltyImpvmRate;

    @Schema(title = "초도방문자료")
    private Integer initVstFileSeq;
    @Schema(title = "킥오프자료")
    private Integer kickfFileSeq;
    @Schema(title = "랩업자료")
    private Integer lvlupFileSeq;
    @Schema(title = "기타사업자료")
    private Integer etcFileSeq;

    @Schema(title = "컨설팅현황")
    private String cnstgPscndCd;
    @Schema(title = "컨설팅현황명")
    private String cnstgPscndCdNm;
    @Schema(title = "컨설팅현황일자")
    private String cnstgPscndDt;

    // 만족도 설문
    @Schema(title = "설문순번")
    private Integer srvSeq;
    @Schema(title = "설문이름")
    private String srvNm;
    @Schema(title = "설문시작일자")
    private String srvStrtDtm;
    @Schema(title = "설문종료일자")
    private String srvEndDtm;
    @Schema(title = "설문종료일자")
    private String typeNm;

    @Schema(title = "진행상태코드")
    private String rsumeSttsCd;
    @Schema(title = "진행상태 텍스트")
    private String rsumeSttsNm;

    @Schema(title = "신청분야코드")
    private String appctnFldCd;
    @Schema(title = "신청분야코드이름")
    private String appctnFldNm;

}
