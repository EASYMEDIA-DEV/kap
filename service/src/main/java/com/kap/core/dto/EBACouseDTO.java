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


    //관리자순번
    private Integer CouseSeq;

    //과정분류 코드
    private String couseCtgCd1;

    //과정분류 코드
    private String couseCtgCd2;

    //과정명
    private String couseNm;

    //과정요약
    private String couseSummary;


    //과정 소개
    private String couseIntro;


    //학습 목표
    private String couseObject;

    //학습 대상
    private String couseTgTpyeSeq1;//회사  seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String couseTgTpyeSeq2;//업종  seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String couseTgTpyeSeq3;//직무  seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String couseTgTpyeSeq4;//직급 seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String couseTgTpyeSeq5;//기타


    //학습방식
    private String couseType;

    //수료기준

    //평가 유무

    //학습 시간-일
    private String couseTimeDay;

    //학습시간-시간
    private String couseTimeHour;

    //학습 준비물
    private String couseMaterials;

    //협업기관
    private String collaboAgency;

    //학습 내용
    private String couseContents;

    //연계학습 - 선수과목 seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String cousePrevLink;

    //연계학습 - 후속과목 seq 형태로 연계되는 테이블에 1:다 구조로 입력
    private String couseNextLink;

    //썸네일 이미지
    private String thumFileSeq;


    //노출여부
    private String useYn;

    //검색영역

    //로그인세션ID
    private String lgnSsnId;


    //사용여부
    private List<EBACouseDTO> list;
    private List<String> useYnList;

}
