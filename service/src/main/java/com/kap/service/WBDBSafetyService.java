package com.kap.service;



import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbd.WBDBCompanyDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetyMstInsertDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetySearchDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetyTrnsfDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WBDBSafetyService {

    /**
     * 신청 목록을 조회한다.
     */
    public WBDBSafetySearchDTO selectCarbonCompanyList(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public List<String> selectYearDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 상세 조회한다.
     */
    public WBDBSafetyMstInsertDTO selectCarbonCompanyDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception;
    
    /**
     * 연도 상세 조회한다.
     */
    public WBDBSafetySearchDTO getYearSelect(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 이관 로그 조회
     */
    public WBDBSafetyTrnsfDTO getTrnsfList(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO) throws Exception;

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 엑셀 다운로드
     */
    public void excelDownload(WBDBSafetySearchDTO wBDBSafetySearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 회원 부품사정보 상세 조회한다.
     * @return
     */
    public WBDBCompanyDTO selectCompanyUserDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception;

    /**
     * 사용자 신청 수정
     */
    public int carbonUserUpdate(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception;

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    public int getInsertBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    public int getInsertSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 수정 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception;

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    public int updAdmMemo(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception;

}
