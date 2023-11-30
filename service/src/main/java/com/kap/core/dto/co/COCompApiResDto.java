package com.kap.core.dto.co;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "나이스 api 사업자 정보 조회")
public class COCompApiResDto  {

    @Schema(title = "사업자번호" , example = "~~" )
    private String compNum;

    @Schema(title = "응답코드" , example = "1" )
    /**
     * P000	정상응답코드
     * P001	사업자/법인번호 유효성 오류
     * P005	참가기관ID 존재하지 않음
     * P013	서비스이용 권한 없음
     * L399	필수입력값오류 (입력값 유효성 포함)
     * E###	기타시스템 오류
     * S###	DB AP 오류 (SQL 오류 포함)
     * E998	기타 권한 오류
     * E999	내부시스템 오류
     * E~	기타시스템 오류
     */
    private String rsp_cd;

    @Schema(title = "응답 오류 메시지" , example = "~~" )
    private String res_msg;

    @Schema(title = "사업자번호" , example = "~~" )
    private String comp_num;

    @Schema(title = "대표자명" , example = "~~" )
    private String representive_name;

    /**
     * 01	성공
     * 03	미보유
     */
    @Schema(title = "결과코드" , example = "~~" )
    private String result_cd;

    @Schema(title = "응답사업자/법인명" , example = "~~" )
    private String comp_name;

    /**
     * 0	정보없음
     * 1	상장
     * 2	등록
     * 3	외감
     * 4	일반
     * 5	신설법인 (2년 이내)
     */
    @Schema(title = "기업형태코드" , example = "~~" )
    private String comp_type;

    /**
     * comp_status	내용
     * 0	정보없음
     * 1	정상
     * 6	부도
     * 7	휴업
     * 8	폐업
     * 9	기타
     */
    @Schema(title = "기업상태코드" , example = "~~" )
    private String comp_status;




}