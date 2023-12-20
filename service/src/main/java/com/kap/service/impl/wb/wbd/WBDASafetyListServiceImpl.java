package com.kap.service.impl.wb.wbd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.WBDASafetyListService;
import com.kap.service.dao.wb.wbd.WBDASafetyListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBDASafetyListServiceImpl implements WBDASafetyListService {


    //Mapper
    private final WBDASafetyListMapper wBDASafetyListMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectCarbonList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());
        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        wBRoundMstSearchDTO.setList(wBDASafetyListMapper.selectCarbonList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBDASafetyListMapper.getCarbonListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertCarbon(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);
        
        respCnt = wBDASafetyListMapper.insertCarbon(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);

            wBDASafetyListMapper.insertGiveList(wBOrderMstDto);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateCarbon(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();
        int firstEpisdGiveSeqIdgen = 0;

        if(wBRoundMstDTO.getGiveList().size() > 0){
            wBDASafetyListMapper.deleteGiveList(wBRoundMstDTO);

            for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){
                firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

                WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

                wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);
                wBOrderMstDto.setDetailsKey(detailsKey);

                wBDASafetyListMapper.updateGiveList(wBOrderMstDto);
            }
            respCnt = wBDASafetyListMapper.updateCarbon(wBRoundMstDTO);
        }else{
            respCnt = wBDASafetyListMapper.updateExpsYnCarbon(wBRoundMstDTO);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBDASafetyListMapper.deleteCarbon(wBRoundMstDTO);
        wBDASafetyListMapper.deleteGiveList(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectCarbonDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBDASafetyListMapper.selectCarbonDtl(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);
        wBRoundMstDTO.setGiveList(wBDASafetyListMapper.selectGiveList(wBRoundMstDTO));

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBDASafetyListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 매핑 여부 확인
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBDASafetyListMapper.getAppctnCnt(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int carbonDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBDASafetyListMapper.carbonDeleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBDASafetyListMapper.episdChk(wBRoundMstDTO);
        return respCnt;
    }
    
    
}
