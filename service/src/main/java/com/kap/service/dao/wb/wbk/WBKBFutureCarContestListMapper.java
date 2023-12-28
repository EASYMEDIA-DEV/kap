package com.kap.service.dao.wb.wbk;

import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;
import com.kap.core.dto.wb.wbk.WBPlaceMstDTO;
import com.kap.core.dto.wb.wbk.WBPrizeMstDTO;

import java.util.List;

public interface WBKBFutureCarContestListMapper {
    /**
     * 회차 조회
     */
    public List<WBFutureCarContestSearchDTO> selectFutureCarContestList1(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO);

    /**
     * 회차 전체 갯수
     */
    public int getFutureCarContestListTotCnt(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO);

    /**
     * 회차 등록
     */
    public int insertCFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;

    /**
     * 회차 수정
     */
    public int updateFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;

    /**
     * 포상 등록
     */
    public int insertPrizeList(WBPrizeMstDTO wBPrizeMstDTO) throws Exception;

    /**
     * 지급차수 수정
     */
    public int updatePrizeList(WBPrizeMstDTO wBPrizeMstDTO) throws Exception;

    /**
     * 지급차수 조회
     */
    public List<WBOrderMstDto> selectGiveList(WBRoundMstDTO wBRoundMstDTO) throws Exception;

    /**
     * 회차 삭제
     */
    public int deleteFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;
    
    /**
     * 포상 삭제
     */
    public int deletePrizeList(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;

    /**
     * 회차 상세 조회
     */
    public WBFutureCarContestMstDTO selectFutureCarContestDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception;

    /**
     * 연도 상세 조회
     */
    public WBFutureCarContestMstDTO selectYearDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception;

    /**
     * 포상상세 조회
     */
    public List<WBPrizeMstDTO> selectPrizeList(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;

    /**
     * 장소상세 조회
     */
    public List<WBPlaceMstDTO> selectPlaceList(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception;


}
