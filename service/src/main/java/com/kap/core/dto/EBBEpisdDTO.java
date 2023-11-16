package com.kap.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육차수 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  교육차수 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdDTO extends BaseDTO  {

    @Schema(title = "교육순번", example = "숫자")
    private int edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    private int episdOrd;

    @Schema(title = "회차년도", example = "yyyy")
    private int episdYear;

    @Schema(title = "업종코드", example = "")
    private String cbsnCd;

    @Schema(title = "접수시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsStrtDtm;

    @Schema(title = "접수종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsEndDtm;

    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "정원수", example = "숫자")
    private int fxnumCnt;

    @Schema(title = "정원제한여부", example = "Y/N")
    private String fxnumImpsbYn;

    @Schema(title = "모집방법코드", example = "숫자")
    private String rcrmtMthdCd;

    @Schema(title = "담당자명", example = "이름")
    private String picNm;

    @Schema(title = "담당자이메일", example = "이메일")
    private String picEmail;

    @Schema(title = "담당자전화번호", example = "xxx-xxxx-xxxx")
    private String picTelNo;

    @Schema(title = "교육설명내용", example = "텍스트")
    private String edctnDscCntn;

    @Schema(title = "교육장소순번", example = "숫자")
    private int placeSeq;

    @Schema(title = "교육장소명", example = "")
    private String placeNm;

    @Schema(title = "설문순번", example = "숫자")
    private String srvSeq;

    @Schema(title = "시험순번", example = "숫자")
    private String examSeq;

    @Schema(title = "수료자동여부", example = "Y/N")
    private String cmptnAutoYn;

    @Schema(title = "사용여부", example = "Y/N")
    private String expsYn;

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

    @Schema(title = "학습일 코드", example = "")
    private String stduyDdCd;

    @Schema(title = "회차정렬", example = "숫자")
    private String stduyDdCdNm;

    @Schema(title = "학습시간 코드", example = "")
    private String stduyTimeCd;

    @Schema(title = "학습시간 코드 명", example = "숫자")
    private String stduyTimeCdNm;

    @Schema(title = "접수상태", example = "접수대기/접수중/마감")
    private String accsStatus;

    @Schema(title = "교육상태", example = "교육대기/교육중/마감")
    private String edctnStatus;

    @Schema(title = "접수상태 명", example = "접수대기/접수중/마감")
    private String accsStatusNm;

    @Schema(title = "교육상태 명", example = "교육대기/교육중/마감")
    private String edctnStatusNm;

    @Schema(title = "강사명", example = "이름")
    private String isttrName;

    @Schema(title = "회차정렬", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "강사수(외 x명)", example = "숫자")
    private String isttrOutCnt;

    @Schema(title = "신청자수", example = "숫자")
    private String accCnt;

    @Schema(title = "모집방법코드명", example = "숫자")
    private String rcrmtMthdCdNm;





    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "게시판 목록", example = "DTO의 리스트")
    private List<EBBEpisdDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;

    private List<String> expsYnList;

    private List<String> accsStatusList;

    private List<String> edctnStatusList;

    private List<String> rcrmtMthdCdList;




    private String sqnm;
    private String sqscore;






}
