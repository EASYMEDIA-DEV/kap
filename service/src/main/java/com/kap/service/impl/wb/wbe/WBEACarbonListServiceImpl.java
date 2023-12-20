package com.kap.service.impl.wb.wbe;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.WBEACarbonListService;
import com.kap.service.dao.wb.wbe.WBEACarbonListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBEACarbonListServiceImpl implements WBEACarbonListService {


    //Mapper
    private final WBEACarbonListMapper wBEACarbonListMapper;

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
        wBRoundMstSearchDTO.setList(wBEACarbonListMapper.selectCarbonList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBEACarbonListMapper.getCarbonListTotCnt(wBRoundMstSearchDTO));

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
        
        respCnt = wBEACarbonListMapper.insertCarbon(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);

            wBEACarbonListMapper.insertGiveList(wBOrderMstDto);
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
            wBEACarbonListMapper.deleteGiveList(wBRoundMstDTO);

            for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){
                firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

                WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

                wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);
                wBOrderMstDto.setDetailsKey(detailsKey);

                wBEACarbonListMapper.updateGiveList(wBOrderMstDto);
            }
            respCnt = wBEACarbonListMapper.updateCarbon(wBRoundMstDTO);
        }else{
            respCnt = wBEACarbonListMapper.updateExpsYnCarbon(wBRoundMstDTO);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteCarbon(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBEACarbonListMapper.deleteCarbon(wBRoundMstDTO);
        wBEACarbonListMapper.deleteGiveList(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectCarbonDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBEACarbonListMapper.selectCarbonDtl(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);
        wBRoundMstDTO.setGiveList(wBEACarbonListMapper.selectGiveList(wBRoundMstDTO));

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBEACarbonListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 매핑 여부 확인
     */
    public int getAppctnCnt(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBEACarbonListMapper.getAppctnCnt(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int carbonDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBEACarbonListMapper.carbonDeleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 중복 체크
     */
    public int episdChk(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBEACarbonListMapper.episdChk(wBRoundMstDTO);
        return respCnt;
    }
    
    
}
