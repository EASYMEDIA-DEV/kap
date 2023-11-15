package com.kap.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.annotation.CMDate;
import com.kap.core.annotation.CheckOriginData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 *  상세/수정 신청자, 부품사 정보
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  양현우         최초 생성
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
@Slf4j
@Schema(title = "상세/수정 신청자, 부품사 정보")
public class COUserCmpnDto {
    @Schema(title = "회원 순번")
    @NotNull
    private Integer memMemSeq;
    @Schema(title = "회원 코드")
    private String memMemCd;
    @Schema(title = "회원 ID")
    private String memId;
    @Schema(title = "회원 명")
    private String memName;
    @Schema(title = "회원 이메일")
    @CheckOriginData
    private String memEmail;
    @Schema(title = "회원 부서 코드")
    @CheckOriginData
    private String memDeptCd;
    @Schema(title = "회원 부서 상세 명")
    private String memDeptDtlNm;
    @Schema(title = "회원 직급 코드")
    @CheckOriginData
    private String memPstnCd;
    @Schema(title = "회원 휴대 전화 번호")
    private String memHpNo;
    @Schema(title = "회원 전화 번호")
    @CheckOriginData
    private String memTelNo;
    @Schema(title = "회원 부품사 사업자 번호")
    private String cmpnBsnmNo;
    @Schema(title = "부품사 명")
    private String cmpnNm;
    @Schema(title = "부품사 구분 코드")
    private String cmpnCtgryCd;
    @Schema(title = "부품사 규모 코드")
    private String cmpnSizeCd;
    @Schema(title = "부품사 대표자 명")
    private String cmpnRprsntNm;
    @Schema(title = "부품사 설립 일자")
    private String cmpnStbsmDt;
    @Schema(title = "부품사 전화 번호")
    private String cmpnTelNo;
    @Schema(title = "부품사 우편 번호")
    private String cmpnZipcode;
    @Schema(title = "부품사 기본 주소")
    private String cmpnBscAddr;
    @Schema(title = "부품사 상세 주소")
    private String cmpnDtlAddr;
    @Schema(title = "부품사 매출 금액")
    private Integer cmpnSlsPmt;
    @Schema(title = "부품사 매출 연도")
    private Integer cmpnSlsYear;
    @Schema(title = "부품사 직원수")
    private Integer cmpnMpleCnt;
    @Schema(title = "부품사 주요 상품 1")
    private String cmpnMjrPrdct1;
    @Schema(title = "부품사 주요 상품 2")
    private String cmpnMjrPrdct2;
    @Schema(title = "부품사 주요 상품 3")
    private String cmpnMjrPrdct3;

    @Schema(title = "코드 리스트")
    private HashMap<String, List<COCodeDTO>> cdDtlList;
    @Schema(title = "수정 ID")
    private String modId;
    @Schema(title = "수정 IP")
    private String modIp;

}
