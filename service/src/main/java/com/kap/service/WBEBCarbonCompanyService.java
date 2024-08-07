package com.kap.service;


import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyTrnsfDTO;
import com.kap.core.dto.wb.wbe.WBEBCompanyDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WBEBCarbonCompanyService {

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
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBEBCompanyDTO selectCompanyUserDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;

    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request, MultipartHttpServletRequest multiRequest) throws Exception;

    /**
     * 사용자 신청 수정
     */
    public int carbonUserUpdate(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    public int getInsertBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    public int getInsertSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 수정 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception;

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    public int updAdmMemo(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception;
}
