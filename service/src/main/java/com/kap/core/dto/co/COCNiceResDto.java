package com.kap.core.dto.co;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "나이스 api 요청데이터 Dto")
public class COCNiceResDto  {

    @Schema(title = "사이트코드" , example = "asdf" )
    private String site_code;

    @Schema(title = "토큰 버전 id" , example = "asdf-asdfas-sdf" )
    private String token_version_id;

    @Schema(title = "토큰" , example = "asdf" )
    private String token_val;

    @Schema(title = "응답코드" , example = "000" )
    private String rsp_cd;

    @Schema(title = "결과값" , example = "p000" )
    private String result_cd;

    @Schema(title = "타이머" , example = "3600.0" )
    private Long period;





}