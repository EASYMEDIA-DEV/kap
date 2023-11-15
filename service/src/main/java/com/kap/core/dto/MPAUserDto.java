package com.kap.core.dto;

import com.kap.core.annotation.MaskRequired;
import lombok.*;

import java.util.List;

/**
 *  일반사용자
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
public class MPAUserDto extends BaseDTO {

    private Integer memSeq;                 //회원순번

    private String memCd;                   //회원코드

    private String id;                      //아이디

    private String name;                    //이름

    private String birth;                   //생일

    private String gndr;                    //성별

    private String hpNo;                    //휴대번호

    private String email;                   //이메일

    private String telNo;                   //전화번호

    private String zipcode;                 //우편변호

    private String bscAddr;                 //기본주소

    private String dtlAddr;                 //상세주소

    private Character ntfySmsRcvYn;              //공지sms수신여부

    private String ntfySmsRcvChngDtm;       //공지sms수신변경일시

    private Character ntfyEmailRcvYn;            //공지이메일수신여부

    private String ntfyEmailRcvChngDtm;     //공지이메일수신변경일시

    private String lastLgnDtm;              //마지막로그인일시

    private String excelYn;             //변경사유

    private String lgnSsnId;

    private String date;

    private List<MPAUserDto> list;

    private String modCd;

    //변경사유
    private String rsn;

    //테이블 이름
    private String tableNm;

    private int modSeq;

    private String pstnCd; //   //직급코드

    private String deptCd; //   // 부서코드

    private String deptDtlNm;  //부서상세명
    private String cmpnNm;          //회사명

    private String sizeCdNm;        //회사 사이즈

    private String ctgryCdNm;       //구분

    private String ctgryCd; //      //구분 코드

    private String workBsnmNo;          //사업자 코드

    private List<String> ctgryCdList; // 카테고리 구분


}
