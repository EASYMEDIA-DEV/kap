package com.kap.core.dto.eb.ebb;

import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  교육차수 객체
 *
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.15  교육차수 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBBEpisdDTO extends BaseDTO {


    @Schema(title = "교육차수순번", example = "숫자")
    private Integer episdSeq;

    @Schema(title = "교육순번", example = "숫자")
    @NotNull
    private Integer edctnSeq;

    @Schema(title = "회차정렬", example = "숫자")
    @NotNull
    @Builder.Default
    private Integer episdOrd = 1;

    //수정시에만 사용
    @Schema(title = "회차년도(수정전)", example = "yyyy")
    private Integer orgEpisdYear;

    @Schema(title = "회차정렬(수정전)", example = "숫자")
    @Builder.Default
    private Integer orgEpisdOrd = 1;

    @Schema(title = "회차년도", example = "yyyy")
    @NotNull
    private Integer episdYear;

    @Schema(title = "업종코드", example = "")
    private String cbsnCd;

    @Schema(title = "접수시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsStrtDtm;
    @Schema(title = "접수시작시간", example = "HH:mm")
    private String accsStrtHour;

    @Schema(title = "접수종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String accsEndDtm;
    @Schema(title = "접수종료시간", example = "HH:mm")
    private String accsEndHour;


    @Schema(title = "교육시작일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnStrtDtm;

    @Schema(title = "교육종료일시", example = "yyyy-mm-dd HH:mm:ss")
    private String edctnEndDtm;

    @Schema(title = "정원수", example = "숫자")
    private Integer fxnumCnt;

    @Schema(title = "정원제한여부", example = "Y = 정원 제한함 / N = 정원 제한 안함(체크박스 체크)")
    @Builder.Default
    private String fxnumImpsbYn = "Y";

    @Schema(title = "모집방법코드", example = "숫자")
    private String rcrmtMthdCd;

    @Schema(title = "담당자명", example = "이름")
    private String picNm;

    @Schema(title = "담당자이메일", example = "이메일")
    private String picEmail;

    @Schema(title = "담당자전화번호", example = "xxx-xxxx-xxxx")
    private String picTelNo;

    @Schema(title = "교육안내문", example = "숫자")
    private Integer edctnNtctnFileSeq;

    @Schema(title = "교육장소순번", example = "숫자")
    private Integer placeSeq;

    @Schema(title = "교육장소명", example = "")
    private String placeNm;

    @Schema(title = "설문순번", example = "숫자")
    private Integer srvSeq;

    @Schema(title = "설문명", example = "숫자")
    private String srvNm;

    @Schema(title = "설문시작일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String srvStrtDtm;

    @Schema(title = "설문종료일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String srvEndDtm;

    @Schema(title = "설문 참여수", example = "숫자")
    private Integer srvMemCnt;

    @Schema(title = "교육 참여수", example = "숫자")
    private Integer edctnMemCnt;



    @Schema(title = "시험순번", example = "숫자")
    private Integer examSeq;

    @Schema(title = "시험명", example = "텍스트")
    private String examNm;

    @Schema(title = "시험유형", example = "텍스트")
    private String typeNm;


    @Schema(title = "시험시작일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String examStrtDtm;

    @Schema(title = "시험종료일", example = "날짜 yyyy-mm-dd HH:mm:ss")
    private String examEndDtm;


    @Schema(title = "수료자동여부", example = "Y/N")
    private String cmptnAutoYn;


    @Schema(title = "사용여부", example = "Y/N")
    private String expsYn;

    @Schema(title = "카테고리 부모코드번호", example = "숫자")
    private String parntSeq;

    @Schema(title = "평가여부", example = "Y/N")
    private String jdgmtYn;



    @Schema(title = "카테고리 부모 코드 ", example = "")
    private String prntCd;

    @Schema(title = "카테고리 부모 코드 명 ", example = "")
    private String prntCdNm;

    @Schema(title = "카테고리 코드", example = "")
    private String ctgryCd;

    @Schema(title = "카테고리 코드 명", example = "")
    private String ctgryCdNm;

    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;

    @Schema(title = "학습방식 코드", example = "")
    private String stduyMthdCd;

    @Schema(title = "학습방식 코드 명", example = "")
    private String stduyMthdCdNm;

    @Schema(title = "학습일 코드", example = "")
    private String stduyDdCd;

    @Schema(title = "학습일 명", example = "숫자")
    private String stduyDdCdNm;

    @Schema(title = "학습시간 코드", example = "")
    private String stduyTimeCd;

    @Schema(title = "학습시간 코드 명", example = "숫자")
    private String stduyTimeCdNm;

    @Schema(title = "접수상태", example = "접수대기/접수중/마감")
    private String accsStatus;

    @Schema(title = "교육상태", example = "교육대기/교육중/마감")
    private String edctnStatus;

    @Schema(title = "접수상태 명", example = "접수대기/접수중/마감")
    private String accsStatusNm;

    @Schema(title = "교육상태 명", example = "교육대기/교육중/마감")
    private String edctnStatusNm;

    @Schema(title = "강사명", example = "이름")
    private String isttrName;

    @Schema(title = "회차정렬", example = "텍스트")
    private String ffltnNm;

    @Schema(title = "강사수(외 x명)", example = "숫자")
    private String isttrOutCnt;

    @Schema(title = "강사순번", example = "숫자")
    private Integer isttrSeq;

    @Schema(title = "신청자수", example = "숫자")
    private String accsCnt;

    @Schema(title = "모집방법코드명", example = "숫자")
    private String rcrmtMthdCdNm;

    @Schema(title = "복사여부", example = "Y/N")
    @Builder.Default
    private String copyYn = "N";

    @Schema(title = "회차번호 공통코드", example = "숫자")
    private String roundCd;

    @Schema(title = "회차번호 강의순번", example = "숫자")
    private Integer lctrSeq;

    @Schema(title = "협업기관명", example = "텍스트")
    private String cprtnInsttNm;

    @Schema(title = "오프라인평가여부", example = "Y/N")
    private String otsdExamPtcptYn;

    //오프라인여부

    //로그인세션ID
    private String lgnSsnId;


    @Schema(title = "게시판 목록", example = "DTO의 리스트")
    private List<EBBEpisdDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;

    private List<String> expsYnList;

    private List<String> accsStatusList;

    private List<String> edctnStatusList;

    private List<String> rcrmtMthdCdList;



    private String sqnm;
    private String sqscore;


    private String srchLayer;


    private List<EBBLctrDTO> lctrList;

    private List<EBBisttrDTO> isttrSeqList;

    private List<EBBBdgetDTO> bdgetList;



}
