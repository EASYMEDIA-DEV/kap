package com.kap.service.impl.wb.wbi;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.ex.exg.EXGExamQstnDtlDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.WBIASupplyListService;
import com.kap.service.dao.wb.wbi.WBIASupplyListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBIASupplyListServiceImpl implements WBIASupplyListService {


    //Mapper
    private final WBIASupplyListMapper wBIASupplyListMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectSupplyList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());
        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        wBRoundMstSearchDTO.setList(wBIASupplyListMapper.selectSupplyList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBIASupplyListMapper.getSupplyListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        respCnt = wBIASupplyListMapper.insertSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateSupply(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();

        respCnt = wBIASupplyListMapper.updateSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteSupply(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBIASupplyListMapper.deleteSupply(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectSupplyDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBIASupplyListMapper.selectSupplyDtl(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBIASupplyListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 삭제
     */
    public int SupplyDeleteList(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = wBIASupplyListMapper.SupplyDeleteList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     *  선택 연도 값에 따른 episdCnt 값 가져오기
     */
    public List<String> roundCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        return wBIASupplyListMapper.roundCnt(wBRoundMstDTO);
    }

    /**
     *  해당년도 신청 갯수
     */
    public List<String> episdCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
        eXGExamQstnDtlDTO.setDelValueList( wBRoundMstDTO.getDelValueList() );

        return wBIASupplyListMapper.episdCnt(wBRoundMstDTO);
    }
    
}
