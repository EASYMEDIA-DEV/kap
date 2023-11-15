package com.kap.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  교육과정 객체
 *
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.02  교육과정 객체         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EBACouseDTO extends BaseDTO  {

    //교육순번
    @Schema(title = "교육순번", example = "숫자")
    private Integer edctnSeq;

    //카테고리코드 소분류
    @Schema(title = "카테고리코드 소분류", example = "")
    private String ctgryCd;

    //카테고리코드 소분류명
    @Schema(title = "카테고리코드 소분류명", example = "")
    private String ctgryCdNm;

    //카테고리코드 대분류
    @Schema(title = "카테고리코드 대분류", example = "")
    private String prntCd;

    //카테고리코드 대분류명
    @Schema(title = "카테고리코드 대분류명", example = "")
    private String prntCdNm;




    //교육과정 명
    @Schema(title = "교육과정 명", example = "텍스트")
    private String nm;

    //요약명
    @Schema(title = "요약명", example = "텍스트")
    private String smmryNm;

    //소개내용
    @Schema(title = "소개내용", example = "텍스트")
    private String itrdcCntn;

    //학습목표내용
    @Schema(title = "학습목표내용", example = "텍스트")
    private String stduyTrgtCntn;

    //학습대상 코드
    @Schema(title = "학습대상 코드", example = "텍스트")
    private String targetCd;

    //학습대상 코드 - 기타
    @Schema(title = "학습대상 코드 - 기타", example = "텍스트")
    private String etcNm;

    //학습방식코드
    @Schema(title = "교육순번", example = "텍스트")
    private String stduyMthdCd;

    //학습방식코드명
    @Schema(title = "학습방식코드명", example = "텍스트")
    private String stduyMthdCdNm;

    //수료기준코드
    @Schema(title = "수료기준코드", example = "텍스트")
    private String cmptnStndCd;

    //수료평가코드
    @Schema(title = "수료평가코드", example = "텍스트")
    private String cmptnJdgmtCd;

    //평가여부
    @Schema(title = "평가여부", example = "평가없음:N / 평가함:N")
    private String jdgmtYn = "Y";

    @Schema(title = "학습일코드", example = "텍스트")
    private String stduyDdCd;

    @Schema(title = "학습일코드명", example = "텍스트")
    private String stduyDdCdNm;

    @Schema(title = "학습시간코드", example = "텍스트")
    private String stduyTimeCd;

    @Schema(title = "학습시간코드명", example = "텍스트")
    private String stduyTimeCdNm;

    @Schema(title = "학습준비물명", example = "텍스트")
    private String stduySuplsNm;

    @Schema(title = "협업기관명", example = "텍스트")
    private String cprtnInsttNm;

    @Schema(title = "학습내용", example = "텍스트")
    private String stduyCntn;

    @Schema(title = "썸네일파일순번", example = "숫자")
    private Integer thnlFileSeq;

    //노출여부
    @Schema(title = "노출여부", example = "Y/N")
    private String expsYn;

    @Schema(title = "복사여부", example = "Y/N")
    private String copyYn = "N";





    //로그인세션ID
    private String lgnSsnId;


    //사용여부
    private List<EBACouseDTO> list;

    private List<String> ctgryCdList;

    private List<String> stduyMthdCdList;

    private List<String> edctnSeqList;



    private List<String> expsYnList;


    private String sqnm;
    private String sqscore;






}
