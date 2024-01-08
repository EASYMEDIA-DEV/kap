package com.kap.core.dto.wb;

import com.kap.core.dto.BaseDTO;
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

    @Schema(title = "회원순번")
    private Integer memSeq;

    @Schema(title = "회원코드")
    private String memCd;

    @Schema(title = "회원코드")
    private String appctnSeq;

    @Schema(title = "아이디")
    private String id;

    @Schema(title = "이름")
    private String name;

    @Schema(title = "생일")
    private String birth;

    @Schema(title = "성별")
    private String gndr;

    @Schema(title = "휴대번호")
    private String hpNo;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "전화번호")
    private String telNo;

    @Schema(title = "우편변호")
    private String zipCode;

    @Schema(title = "기본주소")
    private String bscAddr;

    @Schema(title = "상세주소")
    private String dtlAddr;

    @Schema(title = "공지sms수신여부")
    private Character ntfySmsRcvYn;

    @Schema(title = "공지sms수신변경일시")
    private String ntfySmsRcvChngDtm;

    @Schema(title = "공지이메일수신여부")
    private Character ntfyEmailRcvYn;

    @Schema(title = "공지이메일수신변경일시")
    private String ntfyEmailRcvChngDtm;

    @Schema(title = "마지막로그인일시")
    private String lastLgnDtm;

    private String excelYn;

    private String lgnSsnId;

    private String date;

    private String modCd;

    @Schema(title = "변경사유")
    private String rsn;

    //테이블 이름
    private String tableNm;

    private Integer modSeq;

    @Schema(title = "직급코드")
    private String pstnCd;
    @Schema(title = "직급코드 명")
    private String pstnCdNm;
    @Schema(title = "직급 상세명")
    private String pstnNm;
    @Schema(title = "부서코드")
    private String deptCd;
    @Schema(title = "부서코드 명")
    private String deptCdNm;
    @Schema(title = "부서상세명")
    private String deptDtlNm;
    @Schema(title = "회사명")
    private String cmpnNm;
    @Schema(title = "대표자 명")
    private String rprsntNm;
    @Schema(title = "회사 규모 Code 값")
    private String sizeCd;
    @Schema(title = "회사 사이즈")
    private String sizeCdNm;
    @Schema(title = "구분")
    private String ctgryCdNm;
    @Schema(title = "최초등록자 ID")
    private String regId;
    @Schema(title = "최초등록자 이름")
    private String regName;
    @Schema(title = "최종수정자 이름")
    private String modId;
    @Schema(title = "최종수정자 이름")
    private String modName;

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
    private Integer slsPmt;
    @Schema(title = "매출액 - 년도", example = "yyyy")
    private String slsYear;

    @Schema(title = "직원 수", example = "100")
    private Integer mpleCnt;          // 직원 수

    @Schema(title = "품질5스타코드", example = "")
    private String qlty5starCd;
    @Schema(title = "품질5스타년도", example = "")
    private String qlty5starYear;

    @Schema(title = "납입5스타코드", example = "")
    private String pay5starCd;
    @Schema(title = "납입5스타년도", example = "")
    private String pay5starYear;

    @Schema(title = "기술5스타코드", example = "")
    private String tchlg5starCd;
    @Schema(title = "기술5스타년도", example = "")
    private String tchlg5starYear;

    @Schema(title = "주요생산품 1", example = "")
    private String mjrPrdct1;
    @Schema(title = "주요생산품 2", example = "")
    private String mjrPrdct2;
    @Schema(title = "주요생산품 3", example = "")
    private String mjrPrdct3;

    private List<String> ctgryCdList; // 카테고리 구분

    private List<WBPartCompanyDTO> list;

    @Schema(title = "상생단계 상세 리스트")
    private WBPartCompanyDTO applyDtl;

    @Schema(title = "상생단계 상세 리스트")
    private List<WBPartCompanyDTO> applyList;

    @Schema(title = "상생단계옵션 상세 리스트")
    private List<WBPartCompanyDTO> applyTempOptnList;

    @Schema(title = "상생단계옵션 상세 리스트")
    private List<WBPartCompanyDTO> applyOptnList;

    @Schema(title = "sq정보")
    private List<WBPartCompanyDTO> sqInfoList;

}
