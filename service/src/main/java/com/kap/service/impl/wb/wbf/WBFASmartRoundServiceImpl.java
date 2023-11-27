package com.kap.service.impl.wb.wbf;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.WBRoundOptnMstDTO;
import com.kap.service.WBFASmartRoundService;
import com.kap.service.dao.wb.wbf.WBFASmartRoundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 김동현
 * @since 2023.11.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.17		김동현				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBFASmartRoundServiceImpl implements WBFASmartRoundService {

    /* Mapper */
    private final WBFASmartRoundMapper wBFASmartRoundMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

    /* 회차 사업옵션 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdBsnOptnSeqIdgen;

    /* 상생사업관리 지급 시퀀스 */
    private final EgovIdGnrService cxEpisdGiveSeqIdgen;

    /**
     *   신청부품사 목록 List Get
     */
    public WBRoundMstSearchDTO selApplyCompanyList(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        wBRoundMstSearchDTO.setBsnCd("INQ07006");

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBRoundMstSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBRoundMstSearchDTO.getListRowSize());

        page.setPageSize(wBRoundMstSearchDTO.getPageRowSize());

        wBRoundMstSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBRoundMstSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBRoundMstSearchDTO.setList(wBFASmartRoundMapper.selectSmartRoundList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBFASmartRoundMapper.selectSmartRoundListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 관리 글 상세 Select
     */
    public WBRoundMstDTO selRoundDetail(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBFASmartRoundMapper.selRoundDetail(wBRoundMstSearchDTO);

        wBRoundMstDTO.setDetailsKey(detailsKey);
        wBRoundMstDTO.setGiveList(wBFASmartRoundMapper.selectGiveList(wBRoundMstDTO));
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001"); /* 사업유형 구분 코드 값 */
        wBRoundMstDTO.setBsinList(wBFASmartRoundMapper.selectBusinessList(wBRoundMstDTO));
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002"); /* 과제명 구분 코드 값 */
        wBRoundMstDTO.setAsigtList(wBFASmartRoundMapper.selectAssignmentList(wBRoundMstDTO));

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {
        return wBFASmartRoundMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdBsnOptnSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        respCnt = wBFASmartRoundMapper.insertRound(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){

            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setEpisdSeq(firstEpisdSeqIdgen);
            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);

            wBFASmartRoundMapper.insertGiveList(wBOrderMstDto);
        }

        /* 사업 옵션 - 사업 유형 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getBsinList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01001");
            list.setEpisdSeq(firstEpisdSeqIdgen);
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsinList(list);
        }
        
        /* 사업 옵션 - 과제명 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getBsinList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01002");
            list.setEpisdSeq(firstEpisdSeqIdgen);
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsinList(list);
        }

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    @Override
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        String detailsKey = wBRoundMstDTO.getDetailsKey();
        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdBsnOptnSeqIdgen = 0;

        wBFASmartRoundMapper.deleteGiveList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001");
        wBFASmartRoundMapper.deleteBsinList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002");
        wBFASmartRoundMapper.deleteAsigtList(wBRoundMstDTO);

        for(int i = 0; i < wBRoundMstDTO.getGiveList().size(); i++){
            firstEpisdGiveSeqIdgen = cxEpisdGiveSeqIdgen.getNextIntegerId();

            WBOrderMstDto wBOrderMstDto= wBRoundMstDTO.getGiveList().get(i);

            wBOrderMstDto.setGiveSeq(firstEpisdGiveSeqIdgen);
            wBOrderMstDto.setDetailsKey(detailsKey);

            wBFASmartRoundMapper.updateGiveList(wBOrderMstDto);
        }

        /* 사업 옵션 - 사업 유형 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getBsinList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01001");
            list.setEpisdSeq(Integer.parseInt(detailsKey));
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsinList(list);
        }

        /* 사업 옵션 - 과제명 */
        for(WBRoundOptnMstDTO list : wBRoundMstDTO.getAsigtList()){
            firstEpisdBsnOptnSeqIdgen = cxEpisdBsnOptnSeqIdgen.getNextIntegerId();
            list.setOptnCd("BGN_REG_INF01002");
            list.setEpisdSeq(Integer.parseInt(detailsKey));
            list.setBsnOptnSeq(firstEpisdBsnOptnSeqIdgen);
            wBFASmartRoundMapper.insertBsinList(list);
        }

        respCnt = wBFASmartRoundMapper.updateRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    @Override
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception {
        int respCnt = 0;

        respCnt = wBFASmartRoundMapper.deleteRound(wBRoundMstDTO);
        wBFASmartRoundMapper.deleteGiveList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01001");
        wBFASmartRoundMapper.deleteBsinList(wBRoundMstDTO);
        wBRoundMstDTO.setOptnCd("BGN_REG_INF01002");
        wBFASmartRoundMapper.deleteAsigtList(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }
}