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
@Schema(title = "나이스 api 본인인증 정보 dto")
public class COCNiceMyResDto  {

    @Schema(title = "결괔도ㅡ" , example = "0000" )
    private String resultcode;

    @Schema(title = "요청 고유 번호(회원사에서 전달보낸 값)" , example = "REQ123" )
    private String requestno;

    @Schema(title = "암호화 일시" , example = "yyyymmddhh24miss" )
    private String enctime;

    @Schema(title = "사이트코드" , example = "asd" )
    private String sitecode;

    @Schema(title = "응답고유번호" , example = "23423" )
    private String responseno;

    /**
     * M	휴대폰인증
     * C	카드본인확인
     * X	공동인증서
     * F	금융인증서
     * S	PASS인증서
     */
    @Schema(title = "인증수단" , example = "M" )
    private String authtype;

    @Schema(title = "이름" , example = "홍길동" )
    private String name;

    @Schema(title = "UTF8로 URLEncoding된 이름 값" , example = "%ABA%ASDF" )
    private String utf8_name;

    @Schema(title = "생년월일 8자리" , example = "19911111" )
    private String birthdate;

    /**
     * 0	여성
     * 1	남성
     */
    @Schema(title = "성별" , example = "1" )
    private String gender;

    /**
     * 0	 내국인
     * 1	 외국인
     */
    @Schema(title = "내외국인" , example = "0" )
    private String nationalinfo;

    /**
     * 1	SK텔레콤
     * 2	KT
     * 3	LGU+
     * 5	SK텔레콤 알뜰폰
     * 6	KT 알뜰폰
     * 7	LGU+ 알뜰폰
     */
    @Schema(title = "이통사 구분" , example = "1" )
    private String mobile_co;

    @Schema(title = "휴대폰번호" , example = "010123455667" )
    private String mobile_no;

    @Schema(title = "개인 식별 코드(CI)" , example = "asd" )
    private String ci;

    @Schema(title = "개인 식별 코드(DI)" , example = "asdf" )
    private String di;

    @Schema(title = "사업자번호" , example = "1340" )
    private String businessno;

    @Schema(title = "요청 시 전달 받은 RECEIVEDATA" , example = "{}" )
    private String receivedata;


}