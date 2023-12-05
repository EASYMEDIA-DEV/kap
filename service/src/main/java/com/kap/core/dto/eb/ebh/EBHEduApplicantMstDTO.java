package com.kap.core.dto.eb.ebh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육 신청자 마스터 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  장두석         최초 생성
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
@Schema(title = "교육 신청자 마스터 DTO")
public class EBHEduApplicantMstDTO extends BaseDTO {

    @Schema(title = "참여 순번", example = "숫자")
    @NotNull
    private Integer ptcptSeq;

    @Schema(title = "교육 순번", example = "숫자")
    @NotNull
    private Integer memSeq;

    @Schema(title = "회원 순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "선발 상태 코드")
    private String sttsCd;

    /*@Schema(title = "선발 상태명")
    private String sttsNm;*/

    @Schema(title = "과정 분류 코드")
    private String ctgryCd;

    @Schema(title = "과정 부모 분류명")
    private String stdPrtCateNm;

    @Schema(title = "과정 자식 분류명")
    private String stdCateNm;

    @Schema(title = "교육 과정명")
    private String nm;

    @Schema(title = "학습 방식 코드")
    private String stduyMthdCd;

    @Schema(title = "학습 방식명")
    private String stduyMthdNm;

    @Schema(title = "학습일 코드")
    private String stduyDdCd;

    @Schema(title = "학습일")
    private String stduyDd;

    @Schema(title = "학습 시간 코드")
    private String stduyTimeCd;

    @Schema(title = "학습 시간")
    private String stduyTime;

    @Schema(title = "회차 년도")
    private String episdYear;

    @Schema(title = "회차")
    private String episdOrd;

    @Schema(title = "업종 코드")
    private String cbsnCd;

    @Schema(title = "업종")
    private String cbsnNm;

    @Schema(title = "부품사명")
    private String cmpnNm;

    @Schema(title = "구분 코드")
    private String partsCtgryCd;

    @Schema(title = "구분")
    private String cmpnCateNm;

    @Schema(title = "규모 코드")
    private String sizeCd;

    @Schema(title = "규모")
    private String sizeNm;

    @Schema(title = "신청 당시 사업자 등록 번호")
    private String ptcptBsnmNo;

    @Schema(title = "지역 전체")
    private String bscAddr;

    @Schema(title = "지역")
    private String addr;

    @Schema(title = "이름")
    private String name;

    @Schema(title = "아이디")
    private String id;

    @Schema(title = "GPC 아이디")
    private String gpcId;

    @Schema(title = "휴대폰 번호")
    private String hpNo;

    @Schema(title = "이메일")
    private String email;


    private List<EBHEduApplicantMstDTO> list;

    private List<String> ctgryCdList;

    private List<String> partsCtgryCdList;

    private List<String> choiceCtgryCdList;

    private List<String> stduyMthdCdList;
}
