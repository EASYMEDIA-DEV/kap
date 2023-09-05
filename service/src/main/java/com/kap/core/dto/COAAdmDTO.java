package com.kap.core.dto;

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
public class COAAdmDTO extends BaseDTO  {
    //관리자순번
    private Integer admSeq;
    //권한코드
    private String authCd;
    //권한코드명
    private String authCdNm;
    //부서명
    private String deptNm;
    //구분코드
    private String typeCd;
    //구분코드명
    private String typeCdNm;
    //ID
    private String id;
    //전달받은 비밀번호
    private String password;
    //비밀번호
    private String pwd;
    //현재 암호화 비밀번호
    private String nowEncPwd;
    //변경할 암호화 비밀번호
    private String newEncPwd;
    //변경할 원본 비밀번호
    private String newPassword;
    //변경할 원본 비밀번호 확인
    private String passwordConfirm;
    //비밀번호 변경 이력 순번
    private Integer pwdSeq;
    //이름
    private String name;
    //이메일
    private String email;
    //휴대전화번호
    private String hpNo;
    //허용IP
    private String allwIp;
    //비밀번호변경일시
    private String pwdChngDtm;
    //로그인실패횟수
    private int lgnFailCnt;
    //임시비밀번호여부
    private String tmpPwdYn;
    //마지막로그인일시
    private String lastLgnDtm;
    //로그인세션ID
    private String lgnSsnId;
    //사용여부
    private String useYn;
    //최초 이동할 URL
    private String rdctUrl;
    //최초 로그인 IP
    private String loginIp;
    //최초 로그인 세션 ID
    private String conSessionId;
    //이전권한
    private String bfreAuthCd;
    //수정권한
    private String modAuthCd;
    //변경사유
    private String rsn;
    //엑셀여부
    private String excelYn;
    // 비밀번호 내역 정렬
    private Integer ord;
    // 로그인 2차인증여부
    private String lgnCrtfnYn;

    //검색영역
    //관리자 권한
    private List<String> authCdList;
    //부서
    private List<String> deptCdList;
    //관리자 구분
    private List<String> typeCdList;
    //사용여부
    private List<String> useYnList;

    //관리자 메뉴
    private String mChecked;
    private Integer menuSeq;
    private String isChkd;

    //관리자 수정 여부
    private Integer modSeq; //수정 순번
    private String isAdmMng;
    private String trgtAdmId;
    private String trgtAdmNm;
    private String trgtAdmDept;

    //조회
    private List<COAAdmDTO> list;
    private List<COMenuDTO> userMenuList;
    private List<COAAdmAuthLogDTO> chngAuthList;

    private List<String> pwdList;
    //드라이브 순번
    private int driveMenuSeq;
}
