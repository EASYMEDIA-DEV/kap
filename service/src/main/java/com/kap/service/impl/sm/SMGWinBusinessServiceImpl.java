package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMGWinBusinessDTO;
import com.kap.service.COSeqGnrService;
import com.kap.service.SMGWinBusinessService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.sm.SMGWinBusinessMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * SMGWinBusinessService Helper 클래스
 *
 * @author 임서화
 * @since 2023.10.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *  2023.10.25    임서화              최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SMGWinBusinessServiceImpl implements SMGWinBusinessService {

    //Mapper
    private final SMGWinBusinessMapper sMGWinBusinessMapper;
    // DAO
    private final COSeqGnrService cOSeqGnrService;

    private final COFileMapper cOFileMapper;

    String tableNm = "WIN_BUS_SEQ";

    /**
     * 상생사업 관리 리스트
     */
    public SMGWinBusinessDTO selectWinBusinessList(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pSMGWinBusinessDTO.getPageIndex());
        page.setRecordCountPerPage(pSMGWinBusinessDTO.getListRowSize());

        page.setPageSize(pSMGWinBusinessDTO.getPageRowSize());

        pSMGWinBusinessDTO.setFirstIndex(page.getFirstRecordIndex());
        pSMGWinBusinessDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pSMGWinBusinessDTO.setTotalCount(sMGWinBusinessMapper.selectWinBusinessTotCnt(pSMGWinBusinessDTO));
        pSMGWinBusinessDTO.setList(sMGWinBusinessMapper.selectWinBusinessList(pSMGWinBusinessDTO));
        return pSMGWinBusinessDTO;
    }

    /**
     * 상생사업 관리 등록
     */
    public int insertWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        pSMGWinBusinessDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));
        sMGWinBusinessMapper.insertWinBusiness(pSMGWinBusinessDTO);
        return pSMGWinBusinessDTO.getRespCnt();
    }

    /**
     * 상생사업 관리 수정
     */

    public int updateWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        return pSMGWinBusinessDTO.getRespCnt();
    }

    /**
     * 상생 사업 관리 삭제
     */
    public int deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        return sMGWinBusinessMapper.deleteWinBusiness(pSMGWinBusinessDTO);
    }

    /**
     * 상생 사업 관리 정렬 수정
     */
    public void updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {

        SMGWinBusinessDTO newRow = sMGWinBusinessMapper.selectWinBusinessNewRow(pSMGWinBusinessDTO);

        if(newRow != null){

            int newRowOrd = newRow.getOrd();
            int orginOrd = pSMGWinBusinessDTO.getOrd();

            pSMGWinBusinessDTO.setOrd(newRowOrd);
            sMGWinBusinessMapper.updateOrder(pSMGWinBusinessDTO);

            newRow.setModIp(pSMGWinBusinessDTO.getModIp());
            newRow.setModId(pSMGWinBusinessDTO.getModId());
            newRow.setOrd(orginOrd);
            sMGWinBusinessMapper.updateOrder(newRow);
        }
    }
}


