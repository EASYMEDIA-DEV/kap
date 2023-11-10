package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  회원약관 관리
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
public class SMDCMemTrmsDTO extends BaseDTO {

    // 회원약관순번
    private Integer memTrmsSeq;

    // 이용약관
    @SaxFilter
    private String tmncsCntn;

    // 개인정보수집및이용동의
    @SaxFilter
    private String psnifCntn;

    // 제3자정보제공동의
    @SaxFilter
    private String offerCntn;

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
