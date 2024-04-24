package com.kap.service.impl.wb.wbk;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;
import com.kap.core.dto.wb.wbk.WBPrizeMstDTO;
import com.kap.service.WBKBFutureCarContestListRegService;
import com.kap.service.dao.wb.wbk.WBKAFutureCarContestListMapper;
import com.kap.service.dao.wb.wbk.WBKBFutureCarContestListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBKBFutureCarContestListRegServiceImpl implements WBKBFutureCarContestListRegService {


    //Mapper
    private final WBKBFutureCarContestListMapper wBKBFutureCarContestListMapper;

    //없앨 코드
    private final WBKAFutureCarContestListMapper wBKAFutureCarContestListMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     * 회차 목록
     */
    public WBFutureCarContestSearchDTO selectFutureCarContestRegList(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        WBFutureCarContestMstDTO wBFutureCarContestMstDTO = new WBFutureCarContestMstDTO();

        page.setCurrentPageNo(wBFutureCarContestSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBFutureCarContestSearchDTO.getListRowSize());

        page.setPageSize(wBFutureCarContestSearchDTO.getPageRowSize());

        wBFutureCarContestSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBFutureCarContestSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBFutureCarContestSearchDTO.setList(wBKBFutureCarContestListMapper.selectFutureCarContestList1(wBFutureCarContestSearchDTO));
        wBFutureCarContestSearchDTO.setTotalCount(wBKAFutureCarContestListMapper.getFutureCarContestListTotCnt(wBFutureCarContestSearchDTO));

        return wBFutureCarContestSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertFuturCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBFutureCarContestMstDTO.setEpisdSeq(firstEpisdSeqIdgen);
        
        respCnt = wBKAFutureCarContestListMapper.insertCFutureCarContest(wBFutureCarContestMstDTO);

        for(int i = 0; i < wBFutureCarContestMstDTO.getPrizeList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBPrizeMstDTO wBPrizeMstDTO= wBFutureCarContestMstDTO.getPrizeList().get(i);

            wBPrizeMstDTO.setEpisdSeq(firstEpisdSeqIdgen);
            wBPrizeMstDTO.setPrizeSeq(firstEpisdGiveSeqIdgen);
            wBPrizeMstDTO.setRegIp(wBFutureCarContestMstDTO.getRegIp());
            wBPrizeMstDTO.setRegId(wBFutureCarContestMstDTO.getRegId());
            wBKAFutureCarContestListMapper.insertPrizeList(wBPrizeMstDTO);
        }

        wBFutureCarContestMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        String detailsKey = wBFutureCarContestMstDTO.getDetailsKey();
        int firstEpisdGiveSeqIdgen = 0;

        wBKAFutureCarContestListMapper.deletePrizeList(wBFutureCarContestMstDTO);

        for(int i = 0; i < wBFutureCarContestMstDTO.getPrizeList().size(); i++){
            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBPrizeMstDTO wBPrizeMstDTO= wBFutureCarContestMstDTO.getPrizeList().get(i);

            wBPrizeMstDTO.setPrizeSeq(firstEpisdGiveSeqIdgen);
            wBPrizeMstDTO.setDetailsKey(detailsKey);
            wBPrizeMstDTO.setRegIp(wBFutureCarContestMstDTO.getRegIp());
            wBPrizeMstDTO.setRegId(wBFutureCarContestMstDTO.getRegId());

            wBKAFutureCarContestListMapper.updatePrizeList(wBPrizeMstDTO);
        }

        respCnt = wBKAFutureCarContestListMapper.updateFutureCarContest(wBFutureCarContestMstDTO);

        wBFutureCarContestMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteFutureCarContest(WBFutureCarContestMstDTO wBFutureCarContestMstDTO) throws Exception {

        int respCnt = 0;

        String inputString  = wBFutureCarContestMstDTO.getDetailsKey();
        String[] detailsKey = inputString.split(",");

        for (String detailKey : detailsKey) {
            wBFutureCarContestMstDTO.setDetailsKey(detailKey);
            respCnt = wBKAFutureCarContestListMapper.deleteFutureCarContest(wBFutureCarContestMstDTO);
            wBKAFutureCarContestListMapper.deletePrizeList(wBFutureCarContestMstDTO);
        }



        wBFutureCarContestMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBFutureCarContestMstDTO selectFutureCarContestDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception {

        String detailsKey = wBFutureCarContestSearchDTO.getDetailsKey();

        WBFutureCarContestMstDTO wBFutureCarContestMstDTO = wBKAFutureCarContestListMapper.selectFutureCarContestDtl(wBFutureCarContestSearchDTO);

        if(wBFutureCarContestMstDTO != null) {
            wBFutureCarContestMstDTO.setDetailsKey(detailsKey);
            wBFutureCarContestMstDTO.setPrizeList(wBKAFutureCarContestListMapper.selectPrizeList(wBFutureCarContestMstDTO));
            wBFutureCarContestMstDTO.setPlaceList(wBKAFutureCarContestListMapper.selectPlaceList(wBFutureCarContestMstDTO));
        }

        return wBFutureCarContestMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBFutureCarContestMstDTO selectYearDtl(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception {

        return wBKAFutureCarContestListMapper.selectYearDtl(wBFutureCarContestSearchDTO);
    }
    
    
}
