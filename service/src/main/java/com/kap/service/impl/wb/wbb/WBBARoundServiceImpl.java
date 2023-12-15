package com.kap.service.impl.wb.wbb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBOrderMstDto;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.WBBARoundService;
import com.kap.service.dao.wb.wbb.WBBARoundMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 공통 회차관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBBARoundServiceImpl.java
 * @Description		: 공통 회차관리 ServiceImpl
 * @author 김태훈
 * @since 2023.11.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.24		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBBARoundServiceImpl implements WBBARoundService {

    //Mapper
    private final WBBARoundMapper wBBARoundMapper;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;

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

        wBRoundMstSearchDTO.setList(wBBARoundMapper.selectRoundList(wBRoundMstSearchDTO));
        wBRoundMstSearchDTO.setTotalCount(wBBARoundMapper.getRoundListTotCnt(wBRoundMstSearchDTO));

        return wBRoundMstSearchDTO;
    }

    /**
     * 회차 상세 조회
     */
    public WBRoundMstDTO selectRoundDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        String detailsKey = wBRoundMstSearchDTO.getDetailsKey();

        WBRoundMstDTO wBRoundMstDTO = wBBARoundMapper.selectRoundDtl(wBRoundMstSearchDTO);

        return wBRoundMstDTO;
    }

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        return wBBARoundMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     * 회차 등록
     */
    public int insertRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int firstEpisdGiveSeqIdgen = 0;
        int firstEpisdSeqIdgen = cxEpisdSeqIdgen.getNextIntegerId();
        wBRoundMstDTO.setEpisdSeq(firstEpisdSeqIdgen);

        respCnt = wBBARoundMapper.insertRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 수정
     */
    public int updateRound(WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        respCnt = wBBARoundMapper.updateRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 회차 삭제
     */
    public int deleteRound(WBRoundMstDTO wBRoundMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBBARoundMapper.deleteRound(wBRoundMstDTO);

        wBRoundMstDTO.setRespCnt(respCnt);

        return respCnt;
    }
}
