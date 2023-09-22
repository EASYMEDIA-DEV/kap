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
public class SMCPopDTO extends BaseDTO {

    // 팝업 순번
    private Integer seq;
    // pc/mobile 구분값
    private String dvcCd;
    // 다국어
    private String langCd;
    // 제목
    private String titl;
    // 내용
    @SaxFilter
    private String cnts;
    // 검색 내용
    private String srchCntn;
    // 타입코드
    private String typeCd;
    // 첨부파일 id
    private String atchFileId;
    // 이미지 파일 id
    private String imgFileId;
    // 링크
    private String linkUrl;
    // 상시여부
    private String odtmYn;
    // 게시 시작일시
    private String strtDtm;
    // 게시 종료일시
    private String endDtm;
    // 새창여부
    private String newWndwYn;
    // 노출여부
    private String useYn;
    // 정렬값
    private Integer ord;
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
    private List<SMCPopDTO> list;
    // 검색 조건
    private List<String> expsYnList;
    // image/html 구분
    private List<String> typeCdList;
    // 노출여부 구분(=사용여부 구분)
    private List<String> useYnList;

    //삭제할 데이터
    private List<String> delValueList;
    // 노출정렬 UP/DOWN
    private String sortType;

    // 엑셀 다운로드 여부
    private String excelYn;
}
