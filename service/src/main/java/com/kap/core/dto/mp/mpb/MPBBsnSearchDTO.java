package com.kap.core.dto.mp.mpb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업 신청 전체 DTO
 *
 * @author 김태훈
 * @since 2024.01.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.01.10  김태훈             상생사업 신청 전체 DTO 최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPBBsnSearchDTO extends BaseDTO {
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "사업코드명", example = "")
    private String bsnNm;
    @Schema(title = "사업자번호", example = "")
    private String bsnmNo;
    @Schema(title = "회차순번", example = "")
    private Integer episdSeq;
    @Schema(title = "단계명", example = "")
    private String stageNm;
    @Schema(title = "사업시작일시", example = "yyyy-MM-dd")
    private String bsnStrtDtm;
    @Schema(title = "사업종료일시", example = "yyyy-MM-dd")
    private String bsnEndDtm;
    @Schema(title = "사업명", example = "")
    private String title;
    @Schema(title = "공통사업여부", example = "")
    private String businessYn;
    
    /* List Data */
    @Schema(title = "사업년도", example = "YYYY")
    private String year;
    @Schema(title = "회차", example = "1")
    private String episd;
    @Schema(title = "신청순번", example = "YYYY")
    private Integer appctnSeq;
    @Schema(title = "사용자순번", example = "")
    private Integer memSeq;
    @Schema(title = "신청상태코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "신청상태코드 명", example = "") /* 코드 값 nm */
    private String appctnSttsCdNm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "") /* 코드 값 nm */
    private String mngSttsCdNm;
    @Schema(title = "진행순번", example = "")
    private Integer rsumeSeq;
    @Schema(title = "진행단계", example = "")
    private Integer rsumeOrd;
    @Schema(title = "진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "진행상태 명", example = "")
    private String rsumeSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "yyyy-MM-dd")
    private String appctnSttsChngDtm;
    @Schema(title = "관리상태변경일시", example = "yyyy-MM-dd")
    private String mngSttsChngDtm;
    @Schema(title = "관리자메모", example = "")
    private String admMemo;
    @Schema(title = "수정ID", example = "")
    private String modId;
    @Schema(title = "수정일시", example = "yyyy-MM-dd")
    private String modDtm;

    @Schema(title = "담당위원코드", example = "")
    private String picCmssrSeq;
    @Schema(title = "담당위원이름", example = "")
    private String picName;

    @Schema(title = "공급업체명", example = "")
    private String offerCmpnCdNm;
    @Schema(title = "공급업체 사업자등록번호", example = "")
    private String offerBsnmNo;

    @Schema(title = "검색 리스트", example = "")
    List<MPBBsnSearchDTO> list;

    /* 검색 관련 코드 */
    @Schema(title = "공통", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;

    @Schema(title = "구분 코드 리스트")
    private List ctgryCdList;
}
