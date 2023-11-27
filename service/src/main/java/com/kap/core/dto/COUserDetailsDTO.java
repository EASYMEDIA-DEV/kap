package com.kap.core.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  관리자 객체
 *
 * @author 박주석
 * @since 2022.03.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2022.03.29  관리자 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title="COUserDetailsHelperService에서 사용되는 로그인 서비스")
@Hidden
public class COUserDetailsDTO extends  BaseDTO{
    @Schema(title="로그인 객체 순번")
    private Integer seq;
    @Schema(title="로그인 객체 ID")
    private String id;
    @Schema(title="로그인 객체 이름")
    private String name;
    @Schema(title="로그인 객체 이메일")
    private String email;
    @Schema(title="로그인 객체 휴대전화 번호")
    private String hpNo;
    @Schema(title="로그인 객체 마지막 로그인 일시")
    private String lastLgnDtm;
    @Schema(title="로그인 객체 로그인 IP")
    private String loginIp;
    @Schema(title="로그인 객체 로그인 세션 ID")
    private String conSessionId;

    /** 관리자 객체 시작 **/
    @Schema(title="로그인 객체 권한 코드")
    private String authCd;
    @Schema(title="로그인 객체 권한 코드 명")
    private String authNm;
    @Schema(title="로그인 객체 부서 코드")
    private String deptCd;
    @Schema(title="로그인 객체 부서 명")
    private String deptNm;
    @Schema(title="로그인 객체 유형")
    private String typeCd;
    @Schema(title="로그인 객체 유형 명")
    private String typeNm;
    @Schema(title="로그인 객체 허용 IP")
    private String allwIp;
    @Schema(title="로그인 객체 최초 이동할 URL")
    private String rdctUrl;
    @Schema(title="로그인 객체 드라이브 순번")
    private int driveMenuSeq;
}
