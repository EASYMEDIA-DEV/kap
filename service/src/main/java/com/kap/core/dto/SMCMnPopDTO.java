package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메인 팝업 관리
 *
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.09.21  구은희         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SMCMnPopDTO extends BaseDTO {

    // 팝업 순번
    private Integer popupSeq;

    // pc/mbl 구분값
    private String mdCd;

    // 제목
    private String titl;
    // 내용

    @SaxFilter
    private String cntn;

    // 검색 내용
    private String srchCntn;

    // IMG, HTML 타입코드
    private String tagCd;

    // 첨부파일 id
    //private String atchFileId;

    // 이미지 파일 id
    //private String imgFileId;

    // 링크
    private String urlUrl;

    // 상시여부
    private String odtmYn;

    // 게시 시작일시
    private String expsStrtDtm;

    // 게시 종료일시
    private String expsEndDtm;

    // 새창여부
    private String wnppYn;

    // 노출여부
    private String expsYn;

    // 정렬값
    private Integer expsOrd;

    // 등록자
    private String regId;

    // 등록일자
    private String regDtm;

    // 수정자Id
    private String modId;

    // 수정일자
    private String modDtm;

    //파일 순번
    private Integer fileSeq;

    // 조회
    private List<SMCMnPopDTO> list;

    // IMG, HTML 구분
    private List<String> tagCdList;

    // 노출여부 구분(=사용여부 구분)
    private List<String> expsYnList;

    // 검색 등록/수정 기간 시작일
    private String dStrDt;

    // 검색 등록/수정 기간 종료일
    private String dEndDt;

    // 게시 기간 등록
    // 게시 시작 날짜
    private String ptupStrtDt;

    // 게시 종료 날짜
    private String ptupEndDt;

    // 게시 시작 시간
    private String ptupStrtHh;

    // 게시 시작 분
    private String ptupStrtMi;

    // 게시 종료 시간
    private String ptupEndHh;

    // 게시 종료 분
    private String ptupEndMi;

    //삭제할 데이터
    private List<String> delValueList;

    // 노출정렬 UP/DOWN
    private String sortType;

    //테이블 이름
    private String tableNm;
}
