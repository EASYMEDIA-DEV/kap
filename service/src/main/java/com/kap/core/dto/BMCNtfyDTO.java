package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  전자공고관리 DTO
 *
 * @author 신지혁
 * @since 2022.04.19
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.19  신지혁         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class BMCNtfyDTO extends BaseDTO {
    // 공지 순번
    private Integer ntfySeq;
    // 다국어 언어 코드
    private String lnggCd;
    // 제목
    private String titl;
    // 파일 순번
    private Integer fileSeq;
    // 등록일(사용자노출일시)
    private String expsDtm;
    // 노출여부
    private String expsYn;
    // 조회
    private List<BMCNtfyDTO> list;

    // 검색 조건
    private List<String> expsYnList;
}
