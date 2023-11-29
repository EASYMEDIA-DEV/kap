package com.kap.service.dao.wb.wbf;

import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;

import java.util.List;

/**
 * <pre>
 * 스마트공장 > 신청부품사 관리 DAO
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyMapper.java
 * @Description		: 스마트공장 > 신청부품사 관리 DAO
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2023.11.16	  김동현	             최초 생성
 * </pre>
 */
public interface WBFBRegisterCompanyMapper {

    /**
     *  List Page
     *  신청부품사 목록 List Get
     */
    public List<WBFBRegisterSearchDTO> getRegisterCompanyList(WBFBRegisterSearchDTO wbApplyMstSearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 Sub List Get
     */
    public WBFBRegisterSearchDTO getRegisterCompanySubList(WBFBRegisterSearchDTO wbApplyMstSearchDTO);

    /**
     *  List Page
     *  신청부품사 목록 Count
     */
    public int getRegisterCompanyCount(WBFBRegisterSearchDTO wbApplyMstSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 연도 기준 회차 검색
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);
    
    /**
     *  Write Page
     *  사업회차 연도 기준 회차 순번 검색
     */
    public Integer getEpisdSeq(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  사업회차 년도/회차 기준 옵션 검색
     */
    public List<WBRoundOptnMstDTO> getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 마스터
     */
    public int putAppctnMst(WBFBRegisterDTO wBFBRegisterDTO);
    
    /**
     *  Write Page
     *  신청부품사 등록 - 신청 파일 상세
     */
    public int putAppctnFileDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 신청 상세
     */
    public int putAppctnRsumeDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  신청부품사 등록 - 스마트 싱세
     */
    public int putAppctnRsumeTaskDtl(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCoMemMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사 마스터 Update
     */
    public int updCmpnCbsnMst(WBFBRegisterDTO wBFBRegisterDTO);

    /**
     *  Write Page
     *  회사업종 상세 Update
     */
    public int updCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);

    /**
     *  Write Page
     *  회사업종 상세 Insert
     */
    public int insCmpnCbsnDtl(WBCompanyDetailMstDTO wBCompanyDetailMstDTO);

}
