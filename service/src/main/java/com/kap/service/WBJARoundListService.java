package com.kap.service;


import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;

import javax.servlet.http.HttpServletRequest;

public interface WBJARoundListService {

    /**
     * 회차 목록을 조회한다.
     */
    public WBRoundMstSearchDTO selectRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 상세 조회한다.
     */
    public WBRoundMstDTO selectRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

}
