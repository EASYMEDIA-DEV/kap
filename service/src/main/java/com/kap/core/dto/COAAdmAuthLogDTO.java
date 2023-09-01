package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  관리자 권한 로그 객체
 *
 * @author 박주석
 * @since 2022.03.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.03.29  박주석         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COAAdmAuthLogDTO extends BaseDTO {
    //관리자순번
    private Integer admSeq;
    //이전 권한 코드
    private String bfreAuthCd;
    //이전 권한명
    private String bfreAuthCdNm;
    //수정 권한 코드
    private String modAuthCd;
    //수정 권한명
    private String modAuthCdNm;
    //권한 사유
    private String rsn;
}
