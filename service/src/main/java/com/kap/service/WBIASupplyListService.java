package com.kap.service;


import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * 회차 리스트 삭제
     */
    public int SupplyDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 선택 연도 값에 따른
     * episdCnt 값 가져오기
     */
    public List<String> roundCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 신청 갯수
     *
     */
    public List<String> episdCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 최신 회차 목록을 조회한다.
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;
}
