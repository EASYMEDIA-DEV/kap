package com.kap.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class MPPwdInitDto extends BaseDTO {

    private String email;

    private String id;

    private String memCd;

    private String Pwd;

    private String newEncPwd;

    private String name;





}