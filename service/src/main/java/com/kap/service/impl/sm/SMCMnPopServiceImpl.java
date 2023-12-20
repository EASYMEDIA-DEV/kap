package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.sm.smc.SMCMnPopDTO;
import com.kap.service.COFileService;
import com.kap.service.SMCMnPopService;
import com.kap.service.dao.sm.SMCMnPopMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <pre>
 * 팝업 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMCMnPopServiceImpl.java
 * @Description		: 팝업 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.21		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMCMnPopServiceImpl implements SMCMnPopService {

    // DAO
    private final SMCMnPopMapper smcMnPopMapper;
    //파일 서비스
    private final COFileService cOFileService;

    /* 시퀀스 */
    private final EgovIdGnrService smcMnPopDtlIdgen;

    /**
     * 팝업 목록을 조회한다.
     */
    public SMCMnPopDTO selectMnPopList(SMCMnPopDTO smcMnPopDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smcMnPopDTO.getPageIndex());
        page.setRecordCountPerPage(smcMnPopDTO.getListRowSize());

        page.setPageSize(smcMnPopDTO.getPageRowSize());

        smcMnPopDTO.setFirstIndex( page.getFirstRecordIndex() );
        smcMnPopDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smcMnPopDTO.setList( smcMnPopMapper.selectMnPopList(smcMnPopDTO) );
        smcMnPopDTO.setTotalCount( smcMnPopMapper.selectUseMnPopCnt(smcMnPopDTO) );
         return smcMnPopDTO;
    }

    /**
     * 팝업 상세를 조회한다.
     */
    @Override
    public SMCMnPopDTO selectMnPopDtl(SMCMnPopDTO smcMnPopDTO) throws Exception {
        return smcMnPopMapper.selectMnPopDtl(smcMnPopDTO);
    }

    /**
     * 팝업을 수정한다.
     */
    @Override
    public int updateMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smcMnPopDTO.getFileList());
        //파일 처리
        smcMnPopDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        return smcMnPopMapper.updateMnPop(smcMnPopDTO);
    }

    /**
     * 팝업 미노출 여부를 수정한다.
     */
    @Override
    public int updateUseYn(SMCMnPopDTO smcMnPopDTO) throws Exception {
        return smcMnPopMapper.updateUseYn(smcMnPopDTO);
    }

    /**
     * 팝업 정렬을 수정한다.
     */
    @Override
    public void updateOrder(SMCMnPopDTO smcMnPopDTO) throws Exception {
        SMCMnPopDTO newRow = smcMnPopMapper.selectPopNewRow(smcMnPopDTO);

        if(newRow != null){

            int newRowOrd = newRow.getExpsOrd();
            int orginOrd = smcMnPopDTO.getExpsOrd();

            smcMnPopDTO.setExpsOrd(newRowOrd);
            smcMnPopMapper.updateOrder(smcMnPopDTO);

            newRow.setModIp(smcMnPopDTO.getModIp());
            newRow.setModId(smcMnPopDTO.getModId());
            newRow.setExpsOrd(orginOrd);
            smcMnPopMapper.updateOrder(newRow);
        }
    }

    /**
     * 팝업 개수를 조회한다.
     */
    @Override
    public int selectUseMnPopCnt(SMCMnPopDTO smcMnPopDTO) throws Exception {
        return smcMnPopMapper.selectUseMnPopCnt(smcMnPopDTO);
    }

    /**
     * 팝업을 등록한다.
     */
    public int insertMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smcMnPopDTO.getFileList());
        smcMnPopDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        smcMnPopDTO.setPopupSeq(smcMnPopDtlIdgen.getNextIntegerId());

        return smcMnPopMapper.insertMnPop(smcMnPopDTO);
    }

    /**
     * 팝업을 삭제한다.
     */
    @Transactional
    public int deleteMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception {
        return smcMnPopMapper.deleteMnPop(smcMnPopDTO);
    }

    /**
     * 정렬할 팝업을 조회한다.
     */
    public SMCMnPopDTO selectPopNewRow(SMCMnPopDTO smcMnPopDTO) throws Exception {
        return smcMnPopMapper.selectPopNewRow(smcMnPopDTO);
    }

}
