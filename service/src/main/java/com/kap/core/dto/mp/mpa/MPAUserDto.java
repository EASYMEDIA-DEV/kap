package com.kap.core.dto.mp.mpa;

import com.kap.core.dto.BaseDTO;
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

    @Schema(title = "대표자명", example = "아파트 111호")
    private String rprsntNm;



    @Schema(title = "회사우편번호", example = "12345")
    private String cmpnZipcode;

    @Schema(title = "회사기본주소", example = "서울특별시 ~~~")
    private String cmpnBscAddr;

    @Schema(title = "회사상세주소", example = "아파트 111호")
    private String CmpnDtlAddr;


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

    @Schema(title = "엑셀", example = "Y")
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

    @Schema(title = "기타 직급명", example = "기타")
    private String pstnNm;

    @Schema(title = "직급명", example = "사원")
    private String pstnCdNm;

    @Schema(title = "부서코드", example = "MEM_CD")
    private String deptCd;

    @Schema(title = "부서명", example = "품질")
    private String deptCdNm;

    @Schema(title = "부서상세명", example = "부서상세명")
    private String deptDtlNm;  //부서상세명

    @Schema(title = "회사명", example = "회사이름")
    private String cmpnNm;

    @Schema(title = "회사 규모", example = "중소")
    private String sizeCdNm;

    @Schema(title = "회사 규모 코드", example = "중소")
    private String sizeCd;
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
    @Builder.Default
    private String wthdrwYn = "Y";

    @Schema(title="파일순번" , example = "1")
    private Integer cmssrPhotoFileSeq;

    @Schema(title="경력내용" , example = "경력내용")
    private String cmssrMjrCarerCntn;

    @Schema(title="컨설팅내용" , example = "컨설팅내용")
    private String cmssrCnstgFldCntn;

    @Schema(title="경력 노출여부" , example = "Y")
    private String cmssrMjrCarerExpsYn;

    @Schema(title="패스워드" , example = "")
    private String pwd;
    @Schema(title="인코딩 패스워드" , example = "")
    private String encPwd;

    private String cmpnBscDtlAddr;

    @Schema(title="파일순번" , example = "1")
    private Integer cmpnMpleCnt;
    @Schema(title = "전화번호", example = "022344555")
    private String cmpnTelNo;

    // 2023-12-05 컨설팅 사업관리 관련하여 검색 제외 dto 추가
    @Schema(title = "컨설팅 순번", example = "")
    private Integer cnstgSeq;


    @Schema(title = "로그인변경일시", example = "date")
    private String pwdChngDtm;

    @Schema(title = "실패횟수", example = "date")
    private int lgnFailCnt;

    @Schema(title = "변경할 원본 비밀번호 확인", example = "")
    private String passwordConfirm;


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

    @Schema(title ="ci")
    private String ci;

    @Schema(title = "비밀번호 변경 타입 " , example = "new")
    private String pwdChngType;


    @Schema(title="약관동의")
    private String trmsAgmntYn;

    @Schema(title="개인정보")
    private String psnifAgmntYn;

    @Schema(title="3자정보")
    private String psnif3AgmntYn;

    @Schema(title="마케팅")
    private String fndnNtfyRcvYn;


    @Schema(title = "회원검색 레이어 여부", example = "Y")
    private String srchLayer;

    @Schema(title="회원검색 레이어 구분" , example = "Y")
    private String srchDivide;

    @Schema(title="참/거짓")
    private Boolean passwordChk;

    @Schema(title="이메일")
    private String emailName;

    @Schema(title="이메일주소")
    private String emailAddr;

    @Schema(title="이전이메일여부")
    private String oldEmailRcv;

    @Schema(title="이전이메일여부")
    private String oldWorkBsnmNo;


    @Schema(title="이전sms여부")
    private String oldSmsRcv;

    @Schema(title="이전sms여부YN")
    private String chngEmail;

    @Schema(title="이전sms여부YN")
    private String chngSms;

    @Schema(title="패스워드변경여부")
    private String chngPwd;

    @Schema(title="재단공지변경여부")
    private String chngFndn;

}
