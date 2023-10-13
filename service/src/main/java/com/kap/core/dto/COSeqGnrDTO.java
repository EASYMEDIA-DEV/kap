package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  시퀀스 관리 객체
 *
 * @author 임서화
 * @since 2022.09.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2023.09.08  임서화         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COSeqGnrDTO extends BaseDTO {
    // 시퀀스 값
    private int netxId;
    
    // 테이블 이름
    private String TableNm;

}
