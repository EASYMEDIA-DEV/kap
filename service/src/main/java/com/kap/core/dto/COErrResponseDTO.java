package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  공통 에러 객체
 *
 * @author 신혜정
 * @since 2022.03.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.03.29  신혜정         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
public class COErrResponseDTO {
    //필드명
    private String fieldNm;
    //메시지
    private String msg;
}
