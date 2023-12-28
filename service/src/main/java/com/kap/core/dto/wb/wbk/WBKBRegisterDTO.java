package com.kap.core.dto.wb.wbk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "미래차 공모전 신청 마스터 DTO")
public class WBKBRegisterDTO extends BaseDTO {

    /* 회차 마스터 컬럼*/
    @Schema(title = "회차순번", example = "")
    private String episdSeq;
    @Schema(title = "사업코드", example = "")
    private String bsnCd;
    @Schema(title = "사업년도", example = "YYYY")
    private String year;
    @Schema(title = "회차", example = "1")
    private String episd;

    /* 신청 마스터 컬럼*/
    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;
    /* 신청자 정보 */
    @Schema(title = "회원 순번", example = "")
    private String memSeq;
    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 ID", example = "")
    private String id;
    @Schema(title = "생년월일", example = "")
    private String birth;
    @Schema(title = "성별", example = "")
    private String gndr;
    @Schema(title = "휴대번호", example = "")
    private String hpNo;
    @Schema(title = "이메일", example = "")
    private String email;
    @Schema(title = "일반 전화번호", example = "")
    private String telNo;
    @Schema(title = "미래차 신청자 팀장 동일 구분")
    private String regUserSame;

    /*상생 파일 상세*/
    @Schema(title = "파일코드", example = "숫자")
    private String fileCd;
    @Schema(title = "파일순번", example = "숫자")
    private Integer fileSeq;
    @Schema(title = "파일순번", example = "숫자")
    private Integer atchFileSeq;
    @Schema(title = "파일코드", example = "숫자")
    private String fisrtFileSeq;
    @Schema(title = "파일코드", example = "숫자")
    private String firstFileSeq;

    @Schema(title = "파일순번", example = "숫자")
    private Integer lastFileSeq;


    /* 진행상세 */
    @Schema(title = "상생상세 진행순번", example = "숫자")
    private Integer rsumeSeq;
    @Schema(title = "상생상세 진행정렬", example = "숫자")
    private Integer rsumeOrd;
    @Schema(title = "진행상태 코드", example = "")
    private String rsumeSttsCd;
    @Schema(title = "신청상태 코드", example = "")
    private String appctnSttsCd;
    @Schema(title = "진행상태값", example = "")
    private String appctnSttsCdNm;
    @Schema(title = "신청상태변경일시", example = "")
    private String appctnSttsChngDtm;
    @Schema(title = "관리자상태값코드", example = "")
    private String mngSttsCd;
    @Schema(title = "관리자상태값", example = "")
    private String mngSttsCdNm;
    @Schema(title = "관리상태변경일시", example = "")
    private String mngSttsChngDtm;
    @Schema(title = "반려 내용")
    private String rtrn_rsn_cntn;
    @Schema(title = "신청 상세")
    private List<WBKBRsumeDTO> RsumeList;

    @Schema(title = "신청 상세")
    private List<WBFutureCarContestMstDTO> list;

    @Schema(title = "미래차 공모전 상세 DTL")
    private WBKBRegDtlDTO regDtl;

    @Schema(title = "주제코드", example = "")
    private String themeCd;
    @Schema(title = "상세내용", example = "")
    private String dtlCntn;
    @Schema(title = "시상식코드", example = "")
    private String wdcrmCd;
    @Schema(title = "역대수상자여부", example = "")
    private String hghstWinerYn;
    @Schema(title = "참여구분", example = "")
    private String ptcptType;


    @Schema(title = "팀장 이름", example = "")
    private String rdName;
    @Schema(title = "팀장 휴대전화번호", example = "")
    private String rdHpNo;
    @Schema(title = "팀장  이메일", example = "")
    private String rdEmail;
    @Schema(title = "팀장  학교명", example = "")
    private String rdSchlNm;
    @Schema(title = "팀장  학년", example = "")
    private Integer rdGrd;
    @Schema(title = "팀장  학년코드", example = "")
    private String rdGrdCd;

    @Schema(title = "팀원순번", example = "숫자")
    private Integer tmmbrSeq;
    @Schema(title = "참여자이름", example = "")
    private String partName;
    @Schema(title = "참여자 휴대전화번호", example = "")
    private String partHpNo;
    @Schema(title = "참여자 이메일", example = "")
    private String partEmail;
    @Schema(title = "참여자 학교명", example = "")
    private String partSchlNm;
    @Schema(title = "참여자 학년", example = "")
    private String partGrd;
    @Schema(title = "참여자 학년코드", example = "")
    private String partGrdCd;

    @Schema(title = "미래차 공모전 참여자 리스트")
    private List<WBKBRegPartDTO> partList;

    @Schema(title = "스텝 파일 리스트")
    private List<WBKBFileDtlDTO> FileDtlList;

    @Schema(title = "스텝 파일 리스트")
    private List<String> appctnSeqs;
}
