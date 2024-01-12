package com.kap.core.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *  시스템 로그 객체
 *
 * @author 박준희
 * @since 2024.01.12
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2024.01.12  박준희         최초 생성
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=false)
public class COBDashBoardDTO extends BaseDTO {

    //교육 접수중
    private Integer eduAccepting;
    //교육 접수대기
    private Integer eduAcceptWaiting;
    //교육 교육중
    private Integer eduAcceptTraining;

    //컨설팅사업 기술지도 신청
    private Integer conTechApplication;
    //컨설팅사업 기술지도 지도중
    private Integer conTechTraining;

    //컨설팅사업 경영컨설팅 신청
    private Integer conMngApplication;
    //컨설팅사업 경영컨설팅 지도중
    private Integer conMngTraining;

    //공지사항 순번
    private Integer ntfySeq;
    //공지사항 제목
    private String titl;
    //공지사항 날짜
    private String regDtm;

    //1:1문의 접수대기
    private Integer inquiryApplicationWaiting;
    //1:1문의 접수완료
    private Integer inquiryApplicationCompleted;

    //일반회원 신규가입
    private Integer generalMemJoin;
    //일반회원 신규가입
    private Integer generalMemSecession;

    //부품사회원 신규가입
    private Integer companyMemJoin;
    //부품사회원 신규가입
    private Integer companyMemSecession;

    private List<COBDashBoardDTO> noticeList;

    private Integer driveMenuSeq;
    private String Link;
}
