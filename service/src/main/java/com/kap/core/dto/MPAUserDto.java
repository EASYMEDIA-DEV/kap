package com.kap.core.dto;

import com.kap.common.utility.seed.COSeedCipherUtil;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "회원순번", example = "1")
    private Integer memSeq;

    @Schema(title = "회원코드", example = "1")
    private String memCd;

    @Schema(title = "아이디", example = "1")
    private String id;

    @Schema(title = "이름", example = "홍길동")
    private String name;                    //이름

    @Schema(title = "생일", example = "19990101")
    private String birth;

    @Schema(title = "성별", example = "남")
    private String gndr;

    @Schema(title = "휴대번호", example = "01011111111")
    private String hpNo;

    @Schema(title = "이메일", example = "test@tewst.com")
    private String email;

    @Schema(title = "전화번호", example = "022344555")
    private String telNo;

    @Schema(title = "우편번호", example = "12345")
    private String zipcode;

    @Schema(title = "기본주소", example = "서울특별시 ~~~")
    private String bscAddr;

    @Schema(title = "상세주소", example = "아파트 111호")
    private String dtlAddr;

    @Schema(title = "공지sms수신여부", example = "Y")
    private Character ntfySmsRcvYn;

    @Schema(title = "공지sms수신변경일시", example = "date")
    private String ntfySmsRcvChngDtm;

    @Schema(title = "공지이메일수신여부", example = "Y")
    private Character ntfyEmailRcvYn;

    @Schema(title = "공지이메일수신변경일시", example = "date")
    private String ntfyEmailRcvChngDtm;

    @Schema(title = "마지막로그인일시", example = "date")
    private String lastLgnDtm;

    @Schema(title = "마지막로그인일시", example = "Y")
    private String excelYn;             //엑셀

    private String lgnSsnId;

    private String date;

    private List<MPAUserDto> list;

    private String modCd;

    @Schema(title = "변경사유", example = "이유")
    private String rsn;

    //테이블 이름
    private String tableNm;

    private int modSeq;

    @Schema(title = "직급코드", example = "MEM_CD")
    private String pstnCd;

    @Schema(title = "부서코드", example = "MEM_CD")
    private String deptCd;

    @Schema(title = "부서상세명", example = "부서상세명")
    private String deptDtlNm;  //부서상세명

    @Schema(title = "회사명", example = "회사이름")
    private String cmpnNm;

    @Schema(title = "회사 규모", example = "중소")
    private String sizeCdNm;
    //회사 사이즈
    @Schema(title = "구분", example = "1차")
    private String ctgryCdNm;

    @Schema(title = "구분코드", example = "구분코드")
    private String ctgryCd;

    @Schema(title = "사업자코드", example = "1234567701")
    private String workBsnmNo;

    @Schema(title = "위원구분코드", example = "MEM_CD")
    private String cmssrTypeCd;

    @Schema(title = "위원구분", example = "품질전문의원")
    private String cmssrTypeCdNm;

    @Schema(title = "최초등록자", example = "홍길동")
    private String cmtModName;

    @Schema(title = "재직여부코드", example = "MEM_CD")
    private String cmssrWorkCd;

    @Schema(title = "재직여부", example = "재직중")
    private String cmssrWorkCdNm;

    @Schema(title = "업종/구분코드", example = "MEM_CD")
    private String cmssrCbsnCd;

    @Schema(title = "업종/구분", example = "품질시스템")
    private String cmssrCbsnCdNm;

    @Schema(title = "카테고리", example = "LIST")
    private List<String> ctgryCdList;

    @Schema(title = "위원구분", example = "LIST")
    private List<String> cmssrTypeList;

    @Schema(title = "재직여부", example = "LIST")
    private List<String> cmssrWorkList; // 카테고리 구분


    @Schema(title = "입사일", example = "date")
    private String cmssrMplmnDt;

    @Schema(title = "퇴사일", example = "date")
    private String cmssrRsgntDt;

    @Schema(title = "탈퇴여부", example = "Y")
    private String wthdrwYn = "N";

    @Schema(title="파일순번" , example = "1")
    private Integer cmssrPhotoFileSeq;

    @Schema(title="경력내용" , example = "경력내용")
    private String cmssrMjrCarerCntn;

    @Schema(title="경력 노출여부" , example = "Y")
    private String cmssrMjrCarerExpsYn;


    @Schema(title="패스워드" , example = "")
    private String pwd;
    @Schema(title="인코딩 패스워드" , example = "")
    private String encPwd;




}
