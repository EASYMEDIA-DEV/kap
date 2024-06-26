package com.kap.service.impl.wb.wbj;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.ex.exg.EXGExamExmplDtlDTO;
import com.kap.core.dto.ex.exg.EXGExamQstnDtlDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.WBJARoundListService;
import com.kap.service.dao.COGCntsMapper;
import com.kap.service.dao.sm.SMJFormMapper;
import com.kap.service.dao.wb.wbj.WBJARoundListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBJARoundListServiceImpl implements WBJARoundListService {


    //Mapper
    private final WBJARoundListMapper wBJARoundListMapper;
    private final COGCntsMapper cOGCntsMapper;
    private final SMJFormMapper sMJFormMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     * 회차 목록
     */
    public WBRoundMstSearchDTO selectRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        WBRoundMstDTO wBRoundMstDTO = new WBRoundMstDTO();

        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());

        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBRoundMstSearchDTO.setList(wBJARoundListMapper.selectRoundList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBJARoundListMapper.getRoundListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        String cnts = wBRoundMstDTO.getAddNtfyCntn();
        wBRoundMstDTO.setAddNtfyCntn(COWebUtil.clearXSSMinimum(cnts));

        respCnt = wBJARoundListMapper.insertRound(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getPrizeList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getPrizeList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setPrizeSeq(firstEpisdGiveSeqIdgen);

            wBJARoundListMapper.insertPrizeList(wBOrderMstDto);
        }
        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();
        int firstEpisdGiveSeqIdgen = 0;

        List<String> episdCnt =  wBJARoundListMapper.episdCnt(wBRoundMstDTO);
        if(episdCnt.size()>0){
        //if(episdCnt.contains("0")){
            wBJARoundListMapper.deletePrizeList(wBRoundMstDTO);

            for(int i = 0; i < wBRoundMstDTO.getPrizeList().size(); i++){
                firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

                WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getPrizeList().get(i);

                wBOrderMstDto.setPrizeSeq(firstEpisdGiveSeqIdgen);
                wBOrderMstDto.setDetailsKey(detailsKey);

                wBJARoundListMapper.updatePrizeList(wBOrderMstDto);
            }
        }

        String cnts = wBRoundMstDTO.getAddNtfyCntn();
        wBRoundMstDTO.setAddNtfyCntn(COWebUtil.clearXSSMinimum(cnts));

        respCnt = wBJARoundListMapper.updateRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
        eXGExamQstnDtlDTO.setDelValueList( wBRoundMstDTO.getDelValueList() );
        respCnt = wBJARoundListMapper.deleteRound(wBRoundMstDTO);

        EXGExamExmplDtlDTO eXGExamExmplDtlDTO = new EXGExamExmplDtlDTO();
        eXGExamExmplDtlDTO.setDelValueList( wBRoundMstDTO.getDelValueList() );
        wBJARoundListMapper.deletePrizeList(wBRoundMstDTO);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBJARoundListMapper.selectRoundDtl(wBRoundMstSearchDTO);

        if(wBRoundMstDTO != null) {
            wBRoundMstDTO.setDetailsKey(detailsKey);
            wBRoundMstDTO.setPrizeList(wBJARoundListMapper.selectPrizeList(wBRoundMstDTO));
            wBRoundMstDTO.setRoomList(wBJARoundListMapper.selectRoomList(wBRoundMstDTO));
        }

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        return wBJARoundListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     *  선택 연도 값에 따른 episdCnt 값 가져오기
     */
    public List<String> roundCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        return wBJARoundListMapper.roundCnt(wBRoundMstDTO);
    }

    /**
     *  해당년도 신청 갯수
     */
    public List<String> episdCnt(WBRoundMstDTO wBRoundMstDTO)  throws Exception
    {
        EXGExamQstnDtlDTO eXGExamQstnDtlDTO = new EXGExamQstnDtlDTO();
        eXGExamQstnDtlDTO.setDelValueList( wBRoundMstDTO.getDelValueList() );

        return wBJARoundListMapper.episdCnt(wBRoundMstDTO);
    }

    /**
     * 해당 년도 회차 조회
     */
    public WBRoundMstDTO selectEpisdDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        return wBJARoundListMapper.selectEpisdDtl(wBRoundMstSearchDTO);
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBRoundMstSearchDTO getRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        //공통사업의 경우 신청단계의 옵션정보를 가져온다. 그외 사업의 경우 양식관리 파일정보를 가져와야함.
//        wBRoundMstSearchDTO.setOptnList(wBJARoundListMapper.selectOPtnList(wBRoundMstSearchDTO));

        // 양식
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
}
