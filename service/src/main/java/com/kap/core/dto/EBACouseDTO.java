package com.kap.core.dto;

import lombok.*;

import java.util.List;

/**
 *  교육과정 객체
 *
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.02  관리자 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBACouseDTO extends BaseDTO  {

    //교육순번
    private Integer edctnSeq;

    //카테고리코드 소분류
    private String ctgryCd;

    //카테고리코드 소분류명
    private String ctgryCdNm;

    //카테고리코드 대분류
    private String prntCd;

    //카테고리코드 대분류명
    private String prntCdNm;




    //교육과정 명
    private String nm;

    //요약명
    private String smmryNm;

    //소개내용
    private String itrdcCntn;

    //학습목표내용
    private String stduyTrgtCntn;

    //학습대상 코드
    private String targetCd;

    //학습대상 코드 - 기타
    private String etcNm;

    //학습방식코드
    private String stduyMthdCd;

    //학습방식코드명
    private String stduyMthdCdNm;

    //수료기준코드
    private String cmptnStndCd;

    //수료평가코드
    private String cmptnJdgmtCd;

    //평가여부
    private String jdgmtYn = "Y";

    //학습일코드
    private String stduyDdCd;

    //학습일코드명
    private String stduyDdCdNm;

    //학습시간코드
    private String stduyTimeCd;

    //학습시간코드명
    private String stduyTimeCdNm;

    //학습준비물명
    private String stduySuplsNm;

    //협업기관명
    private String cprtnInsttNm;

    //학습내용
    private String stduyCntn;

    //썸네일파일순번
    private Integer thnlFileSeq;

    //노출여부
    private String expsYn;







    //로그인세션ID
    private String lgnSsnId;


    //사용여부
    private List<EBACouseDTO> list;

    private List<String> useYnList;

}
