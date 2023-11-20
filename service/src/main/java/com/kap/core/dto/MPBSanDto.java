package com.kap.core.dto;

import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  부품사회원 - 상생 사업
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
public class MPBSanDto extends BaseDTO {

    @Schema(title = "컨설팅 순번", example = "숫자")
    private int sanCtnSeq;


    @Schema(title = "사업년도", example = "yyyy")
    private int year;


    @Schema(title = "회차", example = "1")
    private String episd;


    @Schema(title = "사업구분", example = "자금지원")
    private String typeCdNm;


    @Schema(title = "사업명", example = "사업명")
    private String bsnCdNm;

    @Schema(title = "진행상태", example = "진행중")
    private String status;

    @Schema(title = "부품사명", example = "부품사명")
    private String cmpnNm;

    @Schema(title = "구분", example = "1차")
    private String ctgryCdNm;

    @Schema(title = "규모", example = "소기업")
    private String cmpnSizeCdNm;

    @Schema(title = "사업자등록번호", example = "1234567800")
    private String appctnBsnmNo;

    @Schema(title = "담당위원", example = "홍길동")
    private String name;

    @Schema(title = "신청일", example = "date")
    private String regDtm;

    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "상생 목록", example = "DTO의 리스트")
    private List<MPBSanDto> list;


}