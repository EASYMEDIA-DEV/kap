package com.kap.core.dto;

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

    //교육순번
    int edctnSeq;

    //회차정렬
    int episdOrd;

    //업종코드
    String cbsnCd;

    //접수시작일시
    String accsStrtDtm;

    //접수종료일시
    String accsEndDtm;

    //교육시작일시
    String edctnStrtDtm;
    //교육종료일시
    String edctnEndDtm;

    //정원수
    int fxnumCnt;

    //정원제한여부
    String fxnumImpsbYn;

    //모집방법코드
    String rcrmtMthdCd;

    //담당자명
    String picNm;

    //담당자이메일
    String picEmail;
    //담당자전화번호
    String picTelNo;
    //교육설명내용
    String edctnDscCntn;
    //교육장소순번
    int placeSeq;

    //설문순번
    String srvSeq;
    //시험순번
    String examSeq;
    //수료자동여부
    String cmptnAutoYn;
    //노출여부
    String expsYn;



    //로그인세션ID
    private String lgnSsnId;


    //사용여부
    //private List<EBBEpisdDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;



    private List<String> expsYnList;


    private String sqnm;
    private String sqscore;






}
