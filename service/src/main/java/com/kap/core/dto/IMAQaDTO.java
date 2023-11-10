package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  1:1 문의 DTO
 *
 * @author 장두석
 * @since 2023.11.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.01    장두석              최초 생성
 * </pre>
 */
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class IMAQaDTO extends BaseDTO {

    //QA순번
    private Integer qaSeq;

    //QA답변 순번
    private int qaRplySeq;

    //진행코드
    private String rsumeCd;

    //이메일
    private String email;

    //휴대전화 번호
    private String hpNo;

    //제목
    private String titl;

    //내용
    private String cntn;

    //QA 파일 순번
    private Integer fileSeq;

    //부모 카테고리 코드
    private String parntCtgryCd;

    //카테고리 코드
    private String ctgryCd;

    //깊이
    private int dpth;

    //QA답변 내용
    private String rplyCntn;

    //QA답변 파일 순번
    private Integer rplyFileSeq;

    //언어코드
    private String langCd;

    //검색 리스트
    private String inqFir;
    private String inqSec;
    private List<String> rsumeCdList;
    private String srchDate;

    //테이블 이름
    private String tableNm;

    //조회 리스트
    private List<IMAQaDTO> list;
}
