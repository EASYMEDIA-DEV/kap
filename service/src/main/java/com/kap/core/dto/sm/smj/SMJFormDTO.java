package com.kap.core.dto.sm.smj;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  양식 관리
 *
 * @author 구은희
 * @since 2023.11.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.06  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Schema(title = "양식 관리")
public class SMJFormDTO extends BaseDTO {

    @Schema(title = "양식순번", example = "숫자")
    private Integer stySeq;

    @Schema(title = "컨설팅, 상생 구분", example = "BUSINESS01, BUSINESS02")
    private String typeCd;

    @Schema(title = "기술지도 추진계획서 파일 순번", example = "숫자")
    private Integer tchgdFileSeq;

    @Schema(title = "경영컨설팅 추진계획서 파일 순번", example = "숫자")
    private Integer mngmntFileSeq;

    @Schema(title = "보안환경구축 사업신청서 파일 순번", example = "숫자")
    private Integer scrtyEnvrnmtFileSeq;

    @Schema(title = "안전설비구축 사업신청서 파일 순번", example = "숫자")
    private Integer sftyFcltyFileSeq;

    @Schema(title = "탄소배출저감 사업신청서 파일 순번", example = "숫자")
    private Integer crbnEmsnsFileSeq;

    @Schema(title = "스마트공장구축 신청서 파일 순번", example = "숫자")
    private Integer smrtFctryAppctnFileSeq;

    @Schema(title = "스마트공장구축 보안서약서 파일순번", example = "숫자")
    private Integer smrtFctryScrtyFileSeq;

    @Schema(title = "시험계측장비 신청서 파일 순번", example = "숫자")
    private Integer examMsremntFileSeq;

    @Schema(title = "검교정 신청서 파일 순번", example = "숫자")
    private Integer clbtnFileSeq;

    @Schema(title = "공급망안정화기금 신청서 파일 순번", example = "숫자")
    private Integer splychnStblztnFileSeq;

    @Schema(title = "자동차부품산업대상 신청서 파일 순번", example = "숫자")
    private Integer carPartAppctnFileSeq;

    @Schema(title = "미래차공모전 신청서 파일 순번", example = "숫자")
    private Integer ftreCarAppctnFileSeq;

    @Schema(title = "전체교육일정 파일 순번", example = "숫자")
    private Integer ttlEdctnFileSeq;

    @Schema(title = "파일순번", example = "숫자")
    private Integer fileOrd;

    @Schema(title = "등록ID", example = "")
    private String regId;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;

    @Schema(title = "등록일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID", example = "")
    private String modId;

    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;

    @Schema(title = "수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

}
