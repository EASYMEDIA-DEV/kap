package com.kap.service;


import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;

import javax.servlet.http.HttpServletRequest;

public interface WBKAFutureCarContestListService {

    /**
     * 회차 목록을 조회한다.
     */
    public WBFutureCarContestSearchDTO selectFutureCarContestList(WBFutureCarContestSearchDTO wBPrizeSearchDTO) throws Exception;

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

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

    /**
     * 신청자 공모전 신청 여부 확인
     */
    public int getApplyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception;

}
