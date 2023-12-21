package com.kap.service;


import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.WBJBAcomChangeDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbj.WBJBAcomMstDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface WBJBAcomListService {

    /**
     * 연도 상세 조회한다.
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 신청 부품사 등록 Insert
     */
    public int putRegisterCompany(WBJAcomDTO wBJAcomDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * episdSeq 값 가져오기
     */
    public List<String> getOptEpisdList(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * episdSeq 값 가져오기
     */
    public List<String> getOptPrizeList(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;


    /**
     * 신청부품사 목록 List Get
     */
    public WBJAcomSearchDTO getRegisterCompanyList(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 사업회차 연도 검색
     */
    public List<String> getOptYearList(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 상세 조회한다.
     */
    public WBJAcomDTO selectAcomDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 신청 부품사 등록 update
     */
    public int updateAcom(WBJAcomDTO wbjAcomDTO, WBJBAcomMstDTO wbjbAcomMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 부품사관리 상세 조회한다.
     *
     * @return
     */
    public WBJBAcomMstDTO selectAppctnRsumeDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 신창자를 삭제한다.
     */
    public int delete(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;

    /**
     * 참여 이관 로그를 가져온다.
     */
    public WBJBAcomChangeDTO getChangeList(WBJBAcomChangeDTO wBJBAcomChangeDTO) throws Exception;

    /**
     * 리스트 엑셀 다운로드
     */
    public void excelDownload(WBJAcomSearchDTO wBJAcomSearchDTO, HttpServletResponse response) throws Exception;

    /**
     * 관리자 미확인 갯수 조회
     */
    public int getCnt(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception;
}
