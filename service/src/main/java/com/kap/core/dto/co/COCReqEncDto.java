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
@Schema(title = "나이스 api 요청데이터 암호화")
public class COCReqEncDto  {

    @Schema(title = "서비스 요청 고유 번호" , example = "121313" )

    private String requestno;

    @Schema(title = "인증결과를 받을 회원사 url" , example = "https://~~" )
    private String returnurl;

    @Schema(title = "암호화토큰요청 API에 응답받은 site_code" , example = "asd")
    private String sitecode;

    @Schema(title = "인증수단 고정\n" +
            "(M:휴대폰인증,C:카드본인확인인증,X:인증서인증,U:공동인증서인증,F:금융인증서인증,S:PASS인증서인증)" , example = "H" )
    private String authtype;

    @Schema(title = "이통사 우선 선택(S : SKT, K : KT, L : LGU+)" , example = "K" )
    private String mobilceco;

    @Schema(title = "사업자번호(법인인증인증에 한함)" , example = "1" )
    private String businessno;

    @Schema(title = "결과 url 전달시 http method타입" , example = "1" )
    private String methodtype;

    @Schema(title = "팝업" , example = "Y" )
    private String popupyn;

    @Schema(title = "인증 후 전달받을 데이터 세팅 (요청값 그대로 리턴)" , example = "1" )
    private String receivedata;






}