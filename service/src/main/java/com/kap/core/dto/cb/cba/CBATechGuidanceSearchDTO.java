package com.kap.core.dto.cb.cba;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
public class CBATechGuidanceSearchDTO extends BaseDTO {
    @Schema(title = "컨설팅 순번")
    private Integer cnstgSeq;

    @Schema(title = "신청사업자번호")
    private Integer appctnBsnmNo;

    @Schema(title = "신청회원순번")
    private Integer appctnMemNo;

    @Schema(title = "컨설팅")
    private String cnstgCd;

    @Schema(title = "사업년도")
    private Integer bsnYear;

    @Schema(title = "신청일자")
    private Integer appctnDt;

    @Schema(title = "회사규모코드")
    private String cmpnSizeCd;

    @Schema(title = "승용상용코드")
    private String rdngCmbsnCd;

    @Schema(title = "해외의존율")
    private Integer frgnDpndnRate;

    @Schema(title = "전체매출금액")
    private Integer ttlSlsPmt;

    @Schema(title = "국내매출금액")
    private Integer dmstcSlsPmt;

    @Schema(title = "해외매출금액")
    private Integer frgnSlsPmt;

    @Schema(title = "자동차부품매출금액")
    private Integer carPartSlsPmt;

    @Schema(title = "자동차부품제외매출금액")
    private Integer carPartXcludSlsPmt;

    @Schema(title = "회사주소동일여부")
    private String cmpnAddrSameYn;

    // 이미지 파일 확장자
    private String imageExtns;
    // 영상 파일 확장자
    private String videoExtns;

    // 조회
    private List<CBATechGuidanceSearchDTO> list;

    // 검색 조건
    private List<String> mainYnList;

    // 선택 항목
    private List<String> seqList;




}
