package com.kap.service;


import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;

import javax.servlet.http.HttpServletRequest;

public interface WBKBFutureCarContestListRegService {

    /**
     * 회차 목록을 조회한다.
     */
    public WBFutureCarContestSearchDTO selectFutureCarContestRegList(WBFutureCarContestSearchDTO wBPrizeSearchDTO) throws Exception;

    /**
     * 회차 등록
     */
    public int insertFuturCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 수정
     */
    public int updateFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;

    /**
     * 회차 상세 조회한다.
     */
    public WBFutureCarContestMstDTO selectFutureCarContestDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception;

    /**
     * 연도 상세 조회한다.
     */
    public WBFutureCarContestMstDTO selectYearDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception;
}
