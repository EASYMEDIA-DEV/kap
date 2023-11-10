package com.kap.core.dto;

import com.kap.core.annotation.CMName;
import com.kap.core.annotation.SaxFilter;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
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

    @Builder.Default
    private boolean admin = true;
}
