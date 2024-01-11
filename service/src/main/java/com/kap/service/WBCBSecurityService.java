package com.kap.service;


import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbc.WBCBCompanyDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbc.WBCBSecuritySearchDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityTrnsfDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WBCBSecurityService {

    /**
     * 신청 목록을 조회한다.
     */
    public WBCBSecuritySearchDTO selectCarbonCompanyList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public List<String> selectYearDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 상세 조회한다.
     */
    public WBCBSecurityMstInsertDTO selectCarbonCompanyDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception;
    
    /**
     * 연도 상세 조회한다.
     */
    public WBCBSecuritySearchDTO getYearSelect(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 이관 로그 조회
     */
    public WBCBSecurityTrnsfDTO getTrnsfList(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBCBCompanyDTO selectCompanyUserDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception;

    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception;

}
