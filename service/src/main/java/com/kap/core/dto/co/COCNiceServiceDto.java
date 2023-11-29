package com.kap.core.dto.co;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "나이스 api 서비스 호출 ")
public class COCNiceServiceDto  {

    @Schema(title = "암호화토큰요청_API_응답으로_받은 값" , example = "" )
    private String token_version_id;

    @Schema(title = "암호화한 요청정보" , example = "" )
    private String enc_data;

    @Schema(title = "enc_data의 무결성값" , example = "asd")
    private String integrity_value;



}