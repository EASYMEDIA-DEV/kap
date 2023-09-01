package com.kap.core.dto;

import com.kap.core.annotation.CMName;
import com.kap.core.annotation.SaxFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class COSmpleSrchDTO extends BaseDTO {
    // 내용
    @SaxFilter
    private String cntn;

    // 조회용 내용
    @NotNull
    private String titl;

    // 조회용 내용
    @CMName
    private String name;

}
