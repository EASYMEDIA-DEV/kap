package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  메인 비쥬얼 DTO
 *
 * @author 손태주
 * @since 2022.04.18
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.18  손태주         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMBMainVslDTO extends BaseDTO {
    // 비주얼 순번
    private Integer vslSeq;
    // 다국어 언어 코드
    private String lnggCd;
    // 제목
    private String titl;
    // PC 파일 순번
    private Integer pcFileSeq;
    // MO 파일 순번
    private Integer mblFileSeq;
    // 상시여부
    private String odtmYn;
    // 게시 시작일시
    private String postStrtDtm;
    // 게시 종료일시
    private String postEndDtm;
    // 링크
    private String url;
    // 새 창여부
    private String wnppYn;
    // 노출여부
    private String expsYn;

    // 조회
    private List<SMBMainVslDTO> list;

    // 검색 조건
    private List<String> expsYnList;
}
