package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMCPopDTO;
import com.kap.service.COFileService;
import com.kap.service.SMCPopService;
import com.kap.service.dao.sm.SMCPopMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <pre>
 * 팝업 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMCPopServiceImpl.java
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
public class SMCPopServiceImpl implements SMCPopService {

    // DAO
    private final SMCPopMapper smcPopMapper;
    //파일 서비스
    private final COFileService cOFileService;

    /**
     * 팝업 목록을 조회한다.
     */
    public SMCPopDTO selectMnPopList(SMCPopDTO smcPopDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smcPopDTO.getPageIndex());
        page.setRecordCountPerPage(smcPopDTO.getListRowSize());

        page.setPageSize(smcPopDTO.getPageRowSize());

        smcPopDTO.setFirstIndex( page.getFirstRecordIndex() );
        smcPopDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smcPopDTO.setList( smcPopMapper.selectMnPopList(smcPopDTO) );
        smcPopDTO.setTotalCount( smcPopMapper.selectUseMnPopCnt(smcPopDTO) );
        smcPopDTO.setList(smcPopMapper.selectMnPopList(smcPopDTO));
        return smcPopDTO;
    }

    /**
     * 팝업 상세를 조회한다.
     */
    @Override
    public SMCPopDTO selectMnPopDtl(SMCPopDTO smcPopDTO) throws Exception {
        return smcPopMapper.selectMnPopDtl(smcPopDTO);
    }

    /**
     * 팝업을 수정한다.
     */
    @Override
    public int updateMnPop(SMCPopDTO smcPopDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smcPopDTO.getFileList());
        //파일 처리
        smcPopDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        return smcPopMapper.updateMnPop(smcPopDTO);
    }

    /**
     * 팝업 미노출 여부를 수정한다.
     */
    @Override
    public int updateUseYn(SMCPopDTO smcPopDTO) throws Exception {
        return smcPopMapper.updateUseYn(smcPopDTO);
    }

    /**
     * 팝업 정렬을 수정한다.
     */
    @Override
    public void updateOrder(SMCPopDTO smcPopDTO) throws Exception {
        SMCPopDTO newRow = smcPopMapper.selectPopNewRow(smcPopDTO);

        if(newRow != null){

            int newRowOrd = newRow.getOrd();
            int orginOrd = smcPopDTO.getOrd();

            smcPopDTO.setOrd(newRowOrd);
            smcPopMapper.updateOrder(smcPopDTO);

            newRow.setModIp(smcPopDTO.getModIp());
            newRow.setModId(smcPopDTO.getModId());
            newRow.setOrd(orginOrd);
            smcPopMapper.updateOrder(newRow);
        }
    }

    /**
     * 팝업 개수를 조회한다.
     */
    @Override
    public int selectUseMnPopCnt(SMCPopDTO smcPopDTO) throws Exception {
        return smcPopMapper.selectUseMnPopCnt(smcPopDTO);
    }

    /**
     * 팝업을 등록한다.
     */
    public int insertMnPop(SMCPopDTO smcPopDTO) throws Exception {
        //파일 처리
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(smcPopDTO.getFileList());
        smcPopDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        return smcPopMapper.insertMnPop(smcPopDTO);
    }

    /**
     * 팝업을 삭제한다.
     */
    @Transactional
    public int deleteMnPop(SMCPopDTO smcPopDTO) throws Exception {
        return smcPopMapper.deleteMnPop(smcPopDTO);
    }

    /**
     * 정렬할 팝업을 조회한다.
     */
    public SMCPopDTO selectPopNewRow(SMCPopDTO smcPopDTO) throws Exception {
        return smcPopMapper.selectPopNewRow(smcPopDTO);
    }

}
