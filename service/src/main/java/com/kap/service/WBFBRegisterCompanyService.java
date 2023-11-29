package com.kap.service;

import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;

import java.util.List;

/**
 * 스마트공장구축 > 신청업체관리 Service
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.16  김동현         최초 생성
 * </pre>
 */
public interface WBFBRegisterCompanyService {

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     선택 연도/회차 값에 따른
     * 과제명/사업유형 SELECT Ajax
     */
    public WBRoundMstDTO getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;


    /**
     *   신청부품사 목록 List Get
     */
    public WBFBRegisterSearchDTO getRegisterCompanyList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception;

    /**
     *   신청 부품사 등록 Insert
     */
    public int putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception;

}
