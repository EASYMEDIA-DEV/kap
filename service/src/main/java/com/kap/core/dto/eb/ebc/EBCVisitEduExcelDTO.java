package com.kap.core.dto.eb.ebc;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  방문교육 엑셀 출력 객체
 *
 * @author 구은희
 * @since 2023.12.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.18  방문교육 엑셀 출력 객체       최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBCVisitEduExcelDTO extends BaseDTO {

    @Schema(title = "방문순번", example = "숫자")
    private Integer vstSeq;

    @Schema(title = "회원순번", example = "숫자")
    private Integer memSeq;

    @Schema(title = "방문결과순번", example = "숫자")
    private Integer vstRsltSeq;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "강사1", example = "")
    private String isttr1;

    @Schema(title = "강사2", example = "")
    private String isttr2;

    @Schema(title = "강사3", example = "")
    private String isttr3;

    @Schema(title = "강사4", example = "")
    private String isttr4;

    @Schema(title = "강사5", example = "")
    private String isttr5;

    @Schema(title = "강사6", example = "")
    private String isttr6;

    @Schema(title = "강사명1", example = "숫자")
    private Integer isttrNm1;

    @Schema(title = "강사명2", example = "숫자")
    private Integer isttrNm2;

    @Schema(title = "강사명3", example = "숫자")
    private Integer isttrNm3;

    @Schema(title = "강사명4", example = "숫자")
    private Integer isttrNm4;

    @Schema(title = "강사명5", example = "숫자")
    private Integer isttrNm5;

    @Schema(title = "강사명6", example = "숫자")
    private Integer isttrNm6;

    @Schema(title = "강사리스트", example = "")
    private List<EBCVisitEduExcelDTO> isttrSeqList;

    @Schema(title = "신청사업자번호", example = "숫자")
    private String appctnBsnmNo;

    @Schema(title = "신청분야코드", example = "")
    private String appctnFldCd;

    @Schema(title = "신청분야명", example = "")
    private String appctnFldName;

    @Schema(title = "신청주체내용", example = "")
    private String appctnThemeCntn;

    @Schema(title = "희망일자", example = "")
    private String hopeDt;

    @Schema(title = "장소기본주소", example = "")
    private String placeBscAddr;

    @Schema(title = "장소상세주소", example = "")
    private String placeDtlAddr;

    @Schema(title = "소재지 검색을 위한 교육장소주소값", example = "")
    private String edctnPlaceAddr;

    @Schema(title = "교육인원", example = "")
    private Integer ptcptCnt;

    @Schema(title = "교육시간코드", example = "")
    private String ptcptHh;

    @Schema(title = "교육시간", example = "숫자")
    private Integer ptcptHhNum;

    @Schema(title = "등록ID", example = "")
    private String regId;

    @Schema(title = "등록IP", example = "127.0.0.1")
    private String regIp;

    @Schema(title = "방문교육신청일시", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "수정ID", example = "")
    private String modId;

    @Schema(title = "수정IP", example = "127.0.0.1")
    private String modIp;

    @Schema(title = "최종수정일시", example = "yyyy-MM-dd hh:mm:ss")
    private String modDtm;

    // 방문교육을 신청한 회원정보
    @Schema(title = "신청회원이름", example = "")
    private String name;

    @Schema(title = "신청회원아이디", example = "")
    private String id;

    @Schema(title = "신청회원 휴대폰번호", example = "")
    private String hpNo;

    @Schema(title = "신청회원 이메일", example = "")
    private String email;

    @Schema(title = "회사명", example = "")
    private String cmpnNm;

    @Schema(title = "구분코드", example = "")
    private String ctgryCd;

    @Schema(title = "구분코드명", example = "")
    private String ctgryName;

    @Schema(title = "규모코드", example = "")
    private String sizeCd;

    @Schema(title = "규모명", example = "")
    private String sizeName;

    @Schema(title = "확정주제", example = "")
    private String cnfrmdTheme;

    @Schema(title = "교육상태코드", example = "")
    private String edctnSttsCd;

    @Schema(title = "교육상태명", example = "")
    private String edctnSttsName;

    @Schema(title = "교육년도", example = "숫자")
    private Integer edctnYear;

    @Schema(title = "교육시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "교육장소", example = "")
    private String edctnPlace;

    @Schema(title = "수료인원", example = "숫자")
    private Integer cmptnCnt;

    @Schema(title = "참석률", example = "")
    private Double ptcptRate;

    @Schema(title = "실적마감여부", example = "Y")
    private Character rsltEndYn;

    @Schema(title = "실적마감여부명", example = "")
    private String rsltEndYnNm;

    //방문교육 결과 옵션 상세
    @Schema(title = "결과옵션순번", example = "")
    private Integer rsltOptnSeq;

    @Schema(title = "결과구분코드", example = "")
    private String rsltTypeCd;

    @Schema(title = "옵션코드", example = "")
    private String optnCd;

    @Schema(title = "결과값", example = "숫자")
    private Integer rsltVal;

    @Schema(title = "옵션정렬", example = "숫자")
    private Integer optnOrd;

    // 회사별 수료 인원
    @Schema(title = "완성차", example = "")
    private String educnt1;

    @Schema(title = "1차사", example = "")
    private String educnt2;

    @Schema(title = "2차사", example = "")
    private String educnt3;

    @Schema(title = "기타", example = "")
    private String educnt4;

    // 분야별 수료 인원
    @Schema(title = "품질", example = "")
    private String fieldcnt1;

    @Schema(title = "RnD", example = "")
    private String fieldcnt2;

    @Schema(title = "생산", example = "")
    private String fieldcnt3;

    @Schema(title = "구매", example = "")
    private String fieldcnt4;

    @Schema(title = "경영지원", example = "")
    private String fieldcnt5;

    @Schema(title = "업체평가", example = "")
    private String fieldcnt6;

    @Schema(title = "안전", example = "")
    private String fieldcnt7;

    @Schema(title = "ESG", example = "")
    private String fieldcnt8;

    @Schema(title = "기타", example = "")
    private String fieldcnt9;

    // 직급별 수료 인원
    @Schema(title = "대표", example = "")
    private String positioncnt1;

    @Schema(title = "임원", example = "")
    private String positioncnt2;

    @Schema(title = "부장", example = "")
    private String positioncnt3;

    @Schema(title = "과장/차장", example = "")
    private String positioncnt4;

    @Schema(title = "사원/대리", example = "")
    private String positioncnt5;

    @Schema(title = "조장/반장", example = "")
    private String positioncnt6;

    // 출석/평가
    @Schema(title = "출석률", example = "")
    private String attendcnt1;

    @Schema(title = "평가점수", example = "")
    private String attendcnt2;

    // 만족도
    @Schema(title = "종합평균", example = "")
    private String saticnt1;

    @Schema(title = "전체(공통)", example = "")
    private String saticnt2;

    @Schema(title = "교육환경", example = "")
    private String saticnt3;

    @Schema(title = "교육지원", example = "")
    private String saticnt4;

    @Schema(title = "교육내용", example = "")
    private String saticnt5;

    @Schema(title = "강사1", example = "")
    private String saticnt6;

    @Schema(title = "강사2", example = "")
    private String saticnt7;

    @Schema(title = "강사3", example = "")
    private String saticnt8;

    @Schema(title = "강사4", example = "")
    private String saticnt9;

    @Schema(title = "강사5", example = "")
    private String saticnt10;

    @Schema(title = "강사6", example = "")
    private String saticnt11;

    // 교육시간
    @Schema(title = "강사1(시간)", example = "")
    private String edutimecnt1;

    @Schema(title = "강사2(시간)", example = "")
    private String edutimecnt2;

    @Schema(title = "강사3(시간)", example = "")
    private String edutimecnt3;

    @Schema(title = "강사4(시간)", example = "")
    private String edutimecnt4;

    @Schema(title = "강사5(시간)", example = "")
    private String edutimecnt5;

    @Schema(title = "강사6(시간)", example = "")
    private String edutimecnt6;

    @Schema(title = "조회 리스트", example = "")
    private List<EBCVisitEduExcelDTO> list;

    @Schema(title = "엑셀여부", example = "Y")
    private String excelYn;

    @Schema(title = "변경사유", example = "이유")
    private String rsn;
}
