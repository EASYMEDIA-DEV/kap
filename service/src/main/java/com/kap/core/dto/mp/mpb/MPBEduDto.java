package com.kap.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  일반사용자
 *
 * @author 양현우
 * @since 2023.11.16
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  부품사회원 교육사업 현황         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MPBEduDto extends BaseDTO {

    @Schema(title = "교육순번", example = "숫자")
    private int edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    private int episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    private int episdYear;


    @Schema(title = "담당자명", example = "이름")
    private String picNm;


    @Schema(title = "교육설명내용", example = "텍스트")
    private String edctnDscCntn;


    @Schema(title = "카테고리 부모코드번호", example = "숫자")
    private String parntSeq;

    @Schema(title = "카테고리 부모 ", example = "")
    private String prntCdNm;

    @Schema(title = "카테고리 코드", example = "")
    private String ctgryCd;

    @Schema(title = "카테고리 코드 명", example = "")
    private String ctgryCdNm;

    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;

    @Schema(title = "학습방식 코드", example = "")
    private String stduyMthdCd;

    @Schema(title = "학습방식 코드 명", example = "")
    private String stduyMthdCdNm;

    @Schema(title = "신청일", example = "")
    private String regDay;

    @Schema(title = "수료일", example = "")
    private String cmptnDtm;

    @Schema(title = "신청상태", example = "")
    private String sttsCdNm;

    @Schema(title = "회차정렬", example = "숫자")
    private String stduyDdCdNm;

    @Schema(title = "학습시간 코드", example = "")
    private String stduyTimeCd;

    @Schema(title = "학습시간 코드 명", example = "숫자")
    private String stduyTimeCdNm;

    @Schema(title = "교육시간 시작", example = "")
    private String edctnStrtDtm;

    @Schema(title = "교육시간 종료", example = "")
    private String edctnEndDtm;

    @Schema(title = "강사", example = "")
    private String isttrName;

    @Schema(title = "모집방법코드명", example = "숫자")
    private String rcrmtMthdCdNm;

    @Schema(title = "회차정렬", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "강사수(외 x명)", example = "숫자")
    private String isttrOutCnt;

    @Schema(title = "교육상태", example = "교육대기/교육중/마감")
    private String edctnStatus;

    @Schema(title = "교육장소명", example = "")
    private String placeNm;

    @Schema(title = "수료자동여부", example = "Y/N")
    private String cmptnAutoYn;



    @Schema(title = "교육상태 명", example = "교육대기/교육중/마감")
    private String edctnStatusNm;

    @Schema(title = "부품사명", example = "부품사")
    private String cmpnNm;

    @Schema(title = "구분", example = "1차")
    private String ctgryCdCpNm;

    @Schema(title = "규모", example = "중소기업")
    private String sizeCdNm;

    @Schema(title = "사업자등록번호", example = "1231231231")
    private String workBsnmNo;



    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "게시판 목록", example = "DTO의 리스트")
    private List<MPBEduDto> list;


}