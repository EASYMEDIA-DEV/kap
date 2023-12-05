package com.kap.core.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  사용자
 *
 * @author 양현우
 * @since 2023.11.09
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.09  일반사용자         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class COUserDto extends BaseDTO  {

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq;

    @Schema(title = "회원코드", example = "1")
    private String memCd;

    @Schema(title = "이메일", example = "1@na.com")
    private String email;

    @Schema(title = "아이디", example = "1")
    private String id;

    @Schema(title = "이름", example = "홍길동")
    private String name;                    //이름

    @Schema(title = "생일", example = "19990101")
    private String birth;

    @Schema(title = "휴대번호", example = "01011111111")
    private String hpNo;

    @Schema(title = "마지막로그인일시", example = "date")
    private String lastLgnDtm;

    @Schema(title = "탈퇴여부", example = "Y")
    private String wthdrwYn ;

    @Schema(title = "로그인변경일시", example = "date")
    private String pwdChngDtm;

    @Schema(title = "실패횟수", example = "date")
    private int lgnFailCnt;

    @Schema(title = "변경할 원본 비밀번호 확인", example = "")
    private String passwordConfirm;

    @Schema(title = "비밀번호", example = "")
    private String pwd;

    //전달받은 비밀번호
    @Schema(title = "전달받은 비밀번호", example = "")
    private String password;


    private int driveMenuSeq;

    @Schema(title = "변경할 암호화 비밀번호 확인", example = "")
    private String newEncPwd;

    @Schema(title = "현재 암호화 비밀번호 확인", example = "")
    private String nowEncPwd;

    @Schema(title = "변경할 원본 비밀번호", example = "")
    private String newPassword;

    @Schema(title = "임시 비밀번호 확인", example = "")
    private String tmpPwdYn;

    @Schema(title = "변경연장수", example = "")
    private int chngXtnsnCnt;


}
