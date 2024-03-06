package com.kap.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  부품사회원 - 컨설팅 사업
 *
 * @author 양현우
 * @since 2023.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.20  부품사회원 컨설팅 사업 현황         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPBBusDto extends BaseDTO {

    @Schema(title = "컨설팅 순번", example = "숫자")
    private int busctnSeq;


    @Schema(title = "사업년도", example = "yyyy")
    private int bsnYear;


    @Schema(title = "사업구분", example = "기술지도")
    private String cnstgCdNm;


    @Schema(title = "진행상태", example = "")
    private String statusNm;


    @Schema(title = "부품사명", example = "기업")
    private String cmpnNm;

    @Schema(title = "구분 ", example = "1차")
    private String ctgryCdNm;

    @Schema(title = "규모", example = "소기업")
    private String cmpnSizeCdNm;

    @Schema(title = "사업자등록번호", example = "1234567800")
    private String appctnBsnmNo;

    @Schema(title = "신청분야/업종", example = "it")
    private String appctnFldCdNm;

    @Schema(title = "신청 소재지", example = "경부")
    private String rgnsCdNm;

    @Schema(title = "담당위원", example = "홍길동")
    private String name;


    @Schema(title = "신청일", example = "date")
    private String appctnDt;

    @Schema(title = "지도착수일", example = "date")
    private String guideBgnDt;

    @Schema(title = "지도완료일", example = "date")
    private String guidePscndDt;

    @Schema(title = "개선률", example = "5")
    private double fltyImpvmRate;



    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "컨설팅 목록", example = "DTO의 리스트")
    private List<MPBBusDto> list;

    @Schema(title="회원 구분" , example = "C")
    private String chkPS;


}