package com.kap.service;


import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;

import javax.servlet.http.HttpServletRequest;

public interface WBIASupplyListService {

    /**
     * 회차 목록을 조회한다.
     */
    public WBRoundMstSearchDTO selectSupplyList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 등록
     */
    public int insertSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 수정
     */
    public int updateSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 상세 조회한다.
     */
    public WBRoundMstDTO selectSupplyDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 리스트 삭제
     */
    public int SupplyDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception;
}
