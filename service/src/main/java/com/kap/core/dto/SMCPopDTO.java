package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메인팝업 관리
 *
 * @author 신지혁
 * @since 2022.04.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.08  신지혁         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SMCPopDTO extends BaseDTO {
    // 팝업 순번
    private Integer popupSeq;

    // 다국어
    private String lnggCd;

    // 제목
    private String titl;

    // 내용
    @SaxFilter
    private String cntn;

    // 검색 내용
    private String srchCntn;

    // 타입코드
    private String typeCd;

    // pc파일 순번
    private Integer pcFileSeq;

    // 모바일파일 순번
    private Integer mblFileSeq;

    // 링크
    private String url;

    // 상시여부
    private String odtmYn;

    // 게시 시작일시
    private String postStrtDtm;

    // 게시 종료일시
    private String postEndDtm;
    
    // 새창여부
    private String wnppYn;

    // 노출여부
    private String expsYn;

    // 조회
    private List<SMCPopDTO> list;

    // 검색 조건
    private List<String> expsYnList;
}
