package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  이용약관 관리
 *
 * @author 구은희
 * @since 2023.11.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.06  구은희         최초 생성
 * </pre>
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMDBTmncsDTO extends BaseDTO {

    // 이용약관순번
    private Integer tmncsSeq;

    // 내용
    @SaxFilter
    private String cntn;

    // 등록ID
    private String regId;

    // 등록IP
    private String regIP;

    // 등록일시
    private String regDtm;

    // 수정ID
    private String modId;

    // 수정IP
    private String modIP;

    // 수정일시
    private String modDtm;

    //테이블 이름
    private String tableNm;

}
