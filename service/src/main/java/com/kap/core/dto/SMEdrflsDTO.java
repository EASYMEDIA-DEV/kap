package com.kap.core.dto;

import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class SMEdrflsDTO extends BaseDTO {
    // 법적고지 순번
    private Integer drflsSeq;

    // 다국어
    private String lnggCd;

    // 내용
    @SaxFilter
    private String cntn;
}