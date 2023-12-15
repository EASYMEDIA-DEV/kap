package com.kap.service;


import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyTrnsfDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WBDBSafetyService {

    /**
     * 신청 목록을 조회한다.
     */
    public WBEBCarbonCompanySearchDTO selectCarbonCompanyList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public List<String> selectYearDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 상세 조회한다.
     */
    public WBEBCarbonCompanyMstInsertDTO selectCarbonCompanyDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception;
    
    /**
     * 연도 상세 조회한다.
     */
    public WBEBCarbonCompanySearchDTO getYearSelect(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 이관 로그 조회
     */
    public WBEBCarbonCompanyTrnsfDTO getTrnsfList(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, HttpServletResponse response) throws Exception;

}
