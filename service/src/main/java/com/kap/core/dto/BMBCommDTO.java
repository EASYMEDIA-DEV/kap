package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  커뮤니케이션관리 DTO
 *
 * @author 신지혁
 * @since 2022.04.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.15  신지혁         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class BMBCommDTO extends BaseDTO {
    // 비주얼 순번
    private Integer commSeq;
    // 다국어 언어 코드
    private String lnggCd;
    // 구분코드
    private String typeCd;
    // 제목
    private String titl;
    // 썸네일파일 순번
    private Integer thnlFileSeq;
    // 링크URL
    private String link;
    // 유튜브 URL
    private String url;
    // 내용
    private String cntn;
    // 조회용 내용
    private String srchCntn;
    // 에디터 내용
    @SaxFilter
    private String dextCntn;
    // 새창여부
    private String wnppYn;
    // 등록일(사용자노출일시)
    private String expsDtm;
    // 노출여부
    private String expsYn;

    // 조회
    private List<BMBCommDTO> list;

    // 검색 조건
    private List<String> expsYnList;
    private List<String> typeCdList;
}
