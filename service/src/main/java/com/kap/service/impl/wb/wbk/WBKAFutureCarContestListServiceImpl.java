package com.kap.service.impl.wb.wbk;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;
import com.kap.core.dto.wb.wbk.WBPrizeMstDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBKAFutureCarContestListService;
import com.kap.service.dao.sm.SMJFormMapper;
import com.kap.service.dao.wb.wbb.WBBARoundMapper;
import com.kap.service.dao.wb.wbk.WBKAFutureCarContestListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBKAFutureCarContestListServiceImpl implements WBKAFutureCarContestListService {


    //Mapper
    private final WBKAFutureCarContestListMapper wBKAFutureCarContestListMapper;

    private final SMJFormMapper sMJFormMapper;

    private final WBBARoundMapper wBBARoundMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     * 회차 목록
     */
    public WBFutureCarContestSearchDTO selectFutureCarContestList(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        WBFutureCarContestMstDTO wBFutureCarContestMstDTO = new WBFutureCarContestMstDTO();

        page.setCurrentPageNo(wBFutureCarContestSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBFutureCarContestSearchDTO.getListRowSize());

        page.setPageSize(wBFutureCarContestSearchDTO.getPageRowSize());

        wBFutureCarContestSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBFutureCarContestSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBFutureCarContestSearchDTO.setList(wBKAFutureCarContestListMapper.selectFutureCarContestList(wBFutureCarContestSearchDTO));
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

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        // 최신 회차
        wBRoundMstSearchDTO = wBKAFutureCarContestListMapper.getRoundDtl(wBRoundMstSearchDTO);

        if (wBRoundMstSearchDTO != null) {
            //wBRoundMstSearchDTO.setStageOrd(1);
            SMJFormDTO smjFormDTO = new SMJFormDTO();
            smjFormDTO.setTypeCd("BUSINESS02");
            //그외 사업의 경우 양식관리 파일정보를 가져와야함.
            SMJFormDTO optionList = sMJFormMapper.selectFormDtl(smjFormDTO);
            wBRoundMstSearchDTO.setFileSeq(optionList.getCarPartAppctnFileSeq());
        }

        return wBRoundMstSearchDTO;
    }

    /**
     * 신청자 공모전 신청 여부 확인
     */
    public int getApplyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        int rtnCode = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;

        if (!COUserDetailsHelperService.isAuthenticated())
        {
            //비로그인 코드 999
            rtnCode = 999;
        }
        else
        {
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();


                if ("CS".equals(cOUserDetailsDTO.getAuthCd())) {
                    //위원인 경우 150
                    rtnCode = 150;
               } else{
                wBRoundMstSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                int cnt = wBBARoundMapper.getApplyCount(wBRoundMstSearchDTO);

                if (cnt > 0) {
                    //신청여부 존재 코드 300
                    rtnCode = 300;
                } else {
                    //신청가능 코드 200
                    rtnCode = 200;
                }

            }
        }

        return rtnCode;
    }

}
