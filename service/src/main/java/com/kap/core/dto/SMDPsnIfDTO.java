package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  개인정보 처리방침 DTO
 *
 * @author 손태주
 * @since 2022.04.12
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.04.12  손태주         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMDPsnIfDTO extends BaseDTO {
    // 정보처리 순번
    private Integer psnifSeq;
    // 다국어 언어 코드
    private String lnggCd;
    // 제목
    private String titl;
    // 내용
    @SaxFilter
    private String cntn;
    // 노출여부
    private String expsYn;

    // 조회
    private List<SMDPsnIfDTO> list;

    // 검색 조건
    private List<String> expsYnList;
}
