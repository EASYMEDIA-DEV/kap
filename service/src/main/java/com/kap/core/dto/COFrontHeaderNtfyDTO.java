package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  상단 공지사항 DTO
 *
 * @author 이옥정
 * @since 2023.11.17
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
public class COFrontHeaderNtfyDTO {
    // 공지 순번
    private Integer ntfySeq;
    // 제목
    private String titl;
    // 노출여부
    private String regDtm;
}
