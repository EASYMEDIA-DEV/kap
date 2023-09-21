package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMCPopDTO;
import com.kap.service.SMCPopService;
import com.kap.service.dao.sm.SMCPopMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 *    2023.09.07		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SMCPopServiceImpl implements SMCPopService {

    //Mapper
    private final SMCPopMapper popMapper;

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

        smcPopDTO.setList( popMapper.selectMnPopList(smcPopDTO) );
        smcPopDTO.setTotalCount( popMapper.selectUseMnPopCnt(smcPopDTO) );
        smcPopDTO.setList(popMapper.selectMnPopList(smcPopDTO));
        return smcPopDTO;
    }

    /**
     * 팝업 상세를 조회한다.
     */
    @Override
    public SMCPopDTO selectMnPopDtl(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.selectMnPopDtl(smcPopDTO);
    }

    /**
     * 팝업을 수정한다.
     */
    @Override
    public int updateMnPop(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.updateMnPop(smcPopDTO);
    }

    /**
     * 팝업 미노출 여부를 수정한다.
     */
    @Override
    public int updateUseYn(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.updateUseYn(smcPopDTO);
    }

    /**
     * 팝업 정렬을 수정한다.
     */
    @Override
    public void updateOrder(SMCPopDTO smcPopDTO) throws Exception {
        SMCPopDTO newRow = popMapper.selectPopNewRow(smcPopDTO); //선택한 행 밑에 값들
        if(newRow != null){

            int newRowOrd = newRow.getOrd();
            int orginOrd = smcPopDTO.getOrd();

            smcPopDTO.setOrd(newRowOrd);
            popMapper.updateOrder(smcPopDTO);

            newRow.setModIp(smcPopDTO.getModIp());
            newRow.setModId(smcPopDTO.getModId());
            newRow.setOrd(orginOrd);
            popMapper.updateOrder(newRow);
        }
    }

    /**
     * 팝업 개수를 조회한다.
     */
    @Override
    public int selectUseMnPopCnt(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.selectUseMnPopCnt(smcPopDTO);
    }

    /**
     * 팝업을 등록한다.
     */
    public int insertMnPop(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.insertMnPop(smcPopDTO);
    }

    /**
     * 팝업을 삭제한다.
     */
    @Transactional
    public int deleteMnPop(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.deleteMnPop(smcPopDTO);
    }

    /**
     * 정렬할 팝업을 조회한다.
     */
    public SMCPopDTO selectPopNewRow(SMCPopDTO smcPopDTO) throws Exception {
        return popMapper.selectPopNewRow(smcPopDTO);
    }

}
