package com.kap.core.dto.wb;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.MPAUserDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생 - 부품사 회원 공통 DTO
 *
 * @author 김동현
 * @since 2023.11.21
 * @version 1.0
 * @see
 *
 * <pre>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21  김동현         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WBPartCompanyDTO extends BaseDTO {
    private Integer memSeq;                 //회원순번

    private String memCd;                   //회원코드

    private String id;                      //아이디

    private String name;                    //이름

    private String birth;                   //생일

    private String gndr;                    //성별

    private String hpNo;                    //휴대번호

    private String email;                   //이메일

    private String telNo;                   //전화번호

    private String zipCode;                 //우편변호

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

    @Schema(title = "대표자 명")
    private String rprsntNm;

    @Schema(title = "회사 규모 Code 값")
    private String sizeCd;
    
    @Schema(title = "회사 사이즈")
    private String sizeCdNm;

    @Schema(title = "구분")
    private String ctgryCdNm;

    @Schema(title = "설립일자", example = "yyyy-MM-dd")
    private String stbsmDt;       //설립일자
    
    private String compTel;       //회사 전화번호

    private String ctgryCd; //      //구분 코드

    @Schema(title = "선택한 부품사 ID 검색 값")
    @Hidden
    private String selPartUser;

    @Schema(title = "재직사업자 코드 - 회원 테이블", example = "")
    private String workBsnmNo;

    @Schema(title = "사업자 번호 - 회사 테이블",  example = "")
    private String bsnmNo;

    @Schema(title = "매출액 - 금액", example = "100")
    private int slsPmt;
    @Schema(title = "매출액 - 년도", example = "yyyy")
    private String slsYear;

    @Schema(title = "직원 수", example = "100")
    private int mpleCnt;          // 직원 수

    @Schema(title = "주요생산품 1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요생산품 2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요생산품 3", example = "")
    private String mjrPrdct3;

    private List<String> ctgryCdList; // 카테고리 구분

    private List<WBPartCompanyDTO> list;
}
