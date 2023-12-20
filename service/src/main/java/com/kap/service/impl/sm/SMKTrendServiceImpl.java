package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.service.COFileService;
import com.kap.service.SMKTrendService;
import com.kap.service.dao.sm.SMKTrendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <pre>
 * TREND 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMKTrendServiceImpl.java
 * @Description		: TREND 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMKTrendServiceImpl implements SMKTrendService {

    // DAO
    private final SMKTrendMapper smkTrendMapper;
    //파일 서비스
    private final COFileService cOFileService;

    /* 시퀀스 */
    private final EgovIdGnrService trendIdgen;

    /**
     * TREND 목록을 조회한다.
     */
    public SMKTrendDTO selectTrendList(SMKTrendDTO smkTrendDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smkTrendDTO.getPageIndex());
        page.setRecordCountPerPage(smkTrendDTO.getListRowSize());

        page.setPageSize(smkTrendDTO.getPageRowSize());

        smkTrendDTO.setFirstIndex( page.getFirstRecordIndex() );
        smkTrendDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smkTrendDTO.setList( smkTrendMapper.selectTrendList(smkTrendDTO) );
        smkTrendDTO.setTotalCount( smkTrendMapper.selectUseTrendCnt(smkTrendDTO) );
        return smkTrendDTO;
    }

    /**
     * TREND 상세를 조회한다.
     */
    @Override
    public SMKTrendDTO selectTrendDtl(SMKTrendDTO smkTrendDTO) throws Exception {
        return smkTrendMapper.selectTrendDtl(smkTrendDTO);
    }

    /**
     * TREND 수정한다.
     */
    @Override
    public int updateTrend(SMKTrendDTO smkTrendDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smkTrendDTO.getFileList());
        //파일 처리
        smkTrendDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));

        return smkTrendMapper.updateTrend(smkTrendDTO);
    }

    /**
     * TREND 미노출 여부를 수정한다.
     */
    @Override
    public int updateUseYn(SMKTrendDTO smkTrendDTO) throws Exception {
        return smkTrendMapper.updateUseYn(smkTrendDTO);
    }

    /**
     * TREND 정렬을 수정한다.
     */
    @Override
    public void updateOrder(SMKTrendDTO smkTrendDTO) throws Exception {
        SMKTrendDTO newRow = smkTrendMapper.selectTrendNewRow(smkTrendDTO);

        if(newRow != null){

            int newRowOrd = newRow.getExpsOrd();
            int orginOrd = smkTrendDTO.getExpsOrd();

            smkTrendDTO.setExpsOrd(newRowOrd);
            smkTrendMapper.updateOrder(smkTrendDTO);

            newRow.setModIp(smkTrendDTO.getModIp());
            newRow.setModId(smkTrendDTO.getModId());
            newRow.setExpsOrd(orginOrd);
            smkTrendMapper.updateOrder(newRow);
        }
    }

    /**
     * TREND 개수를 조회한다.
     */
    @Override
    public int selectUseTrendCnt(SMKTrendDTO smkTrendDTO) throws Exception {
        return smkTrendMapper.selectUseTrendCnt(smkTrendDTO);
    }

    /**
     * TREND 등록한다.
     */
    public int insertTrend(SMKTrendDTO smkTrendDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smkTrendDTO.getFileList());
        smkTrendDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));
        smkTrendDTO.setTrndSeq(trendIdgen.getNextIntegerId());

        return smkTrendMapper.insertTrend(smkTrendDTO);
    }

    /**
     * TREND 삭제한다.
     */
    @Transactional
    public int deleteTrend(SMKTrendDTO smkTrendDTO) throws Exception {
        return smkTrendMapper.deleteTrend(smkTrendDTO);
    }

    /**
     * 정렬할 TREND 조회한다.
     */
    public SMKTrendDTO selectTrendNewRow(SMKTrendDTO smkTrendDTO) throws Exception {
        return smkTrendMapper.selectTrendNewRow(smkTrendDTO);
    }

}
