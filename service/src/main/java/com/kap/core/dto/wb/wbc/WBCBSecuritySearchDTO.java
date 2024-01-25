package com.kap.core.dto.wb.wbc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kap.core.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 *  상생사업관리 지급 상세 DTO
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.08  김대성         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "보안환경구축 신청부품사 검색")
public class WBCBSecuritySearchDTO extends BaseDTO {

    @Schema(title = "연도", example = "숫자")
    private Integer year;

    @Schema(title = "회차", example = "숫자")
    private Integer episd;

    @Schema(title = "회차 순번", example = "숫자")
    private Integer episdSeq;

    private String dateState;

    @Schema(title = "진행순번", example = "숫자")
    private Integer rsumeSeq;

    @Schema(title = "진행정렬", example = "숫자")
    private Integer rsumeOrd;


    @Schema(title = "신청순번", example = "숫자")
    private Integer appctnSeq;

    @Schema(title = "회원순번", example = "숫자")
    private Integer memSeq;

    @Schema(title = "등록일", example = "yyyy-MM-dd hh:mm:ss")
    private String regDtm;

    @Schema(title = "신정 진행 상태", example = "")
    private String rsumeSttsNm;

    @Schema(title = "사용자 진행 상태 명", example = "")
    private String appctnSttsNm;

    @Schema(title = "관리자 진행 상태 명", example = "")
    private String mngSttsNm;

    @Schema(title = "신청자 진행 상태 변경일", example = "")
    private String appctnSttsChngDtm;

    @Schema(title = "관리자 진행 상태 변경일", example = "")
    private String mngSttsChngDtm;

    @Schema(title = "부품사 명", example = "")
    private String cmpnNm;

    @Schema(title = "대표자 명", example = "")
    private String rprsntNm;

    @Schema(title = "구분코드,", example = "")
    private String ctgryCd;

    @Schema(title = "구분", example = "")
    private String ctgryNm;

    @Schema(title = "규모코드,", example = "")
    private String sizeCd;
    
    @Schema(title = "규모", example = "")
    private String sizeNm;

    @Schema(title = "사업자 등록 번호", example = "")
    private String bsnmNo;

    @Schema(title = "종된 사업자 등록 번호", example = "")
    private String sbrdnBsnmNo;

    @Schema(title = "지역", example = "")
    private String bscAddr;


    @Schema(title = "최초점검일", example = "")
    private String firstChkDt;

    @Schema(title = "최종점검일", example = "")
    private String lastChkDt;




    @Schema(title = "지원금액", example = "")
    private String spprtPmt;

    @Schema(title = "신청사업비", example = "")
    private String bsnPmt;

    @Schema(title = "자부담금액", example = "")
    private String phswPmt;

    @Schema(title = "총금액", example = "")
    private String ttlPmt;

    @Schema(title = "계좌번호", example = "")
    private String acntNo;

    @Schema(title = "은행명", example = "")
    private String bankNm;

    @Schema(title = "예금명", example = "")
    private String dpsitNm;

    @Schema(title = "지급일자", example = "yyyy-MM-dd hh:mm:ss")
    private String giveDt;

    @Schema(title = "관리자 메모", example = "")
    private String admMemo;
    @Schema(title = "관리자 메모 수정 시간", example = "")
    private String admMemoModDtm;




    @Schema(title = "신청자 이름", example = "")
    private String name;
    @Schema(title = "신청자 아이디", example = "")
    private String id;
    @Schema(title = "신청자 아이디", example = "")
    private String hpNo;
    @Schema(title = "신청자 이메일", example = "")
    private String email;

    @Schema(title = "담당 위원", example = "")
    private Integer picCmssrSeq;

    @Schema(title = "담당 위원 명", example = "")
    private String picCmssrNm;

    @Schema(title = "사업시작일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnStrtDtm;
    @Schema(title = "사업종료일시", example = "yyyy-MM-dd hh:mm:ss")
    private String bsnEndDtm;

    @Schema(title = "회차", example = "숫자")
    private Integer episdSelect;

    @Schema(title = "회차 리스트")
    private List episdList;


    @Schema(title = "진행 상태 검색 조건", example = "yyyy-MM-dd hh:mm:ss")
    private String carbonDate;

    @Schema(title = "사업 연도 검색 조건", example = "")
    private String yearSearch;
    @Schema(title = "사업 회차 검색 조건", example = "")
    private String episdSearch;

    @Schema(title = "진행상태 코드 리스트")
    private List carbonCdList;
    @Schema(title = "노출여부 리스트")
    private List expsYnList;


    @Schema(title = "등록자 아이디", example = "")
    private String regNm;
    @Schema(title = "수정자 아이디", example = "")
    private String modNm;


    @Schema(title = "조회 리스트")
    private List<WBCBSecuritySearchDTO> list;
    
}
