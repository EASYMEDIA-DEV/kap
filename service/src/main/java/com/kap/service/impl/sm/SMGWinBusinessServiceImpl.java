package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smg.SMGWinBusinessDTO;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMGWinBusinessService;
import com.kap.service.dao.sm.SMGWinBusinessMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
/**
 * <pre>
 * 상생 사업 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMBMnVslServiceImpl.java
 * @Description		: 상생 사업 관리를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 *    2023.11.22        장두석                  최신화
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SMGWinBusinessServiceImpl implements SMGWinBusinessService {

    /** Service **/
    private final COFileService cOFileService;

    /** Mapper **/
    private final SMGWinBusinessMapper sMGWinBusinessMapper;

    /** Sequence **/
    private final EgovIdGnrService winMngSeqIdgen;

    /**
     * 상생 사업 목록 조회
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
     * 상생 사업 상세
     */
    public SMGWinBusinessDTO selectWinBusinessDtl(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        return !"".equals(pSMGWinBusinessDTO.getDetailsKey()) ? sMGWinBusinessMapper.selectWinBusinessDtl(pSMGWinBusinessDTO) : pSMGWinBusinessDTO;
    }

    /**
     * 상생 사업 등록
     */
    public int insertWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        //작성자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pSMGWinBusinessDTO.setRegId(cOUserDetailsDTO.getId());
        pSMGWinBusinessDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pSMGWinBusinessDTO.getFileList() != null && !pSMGWinBusinessDTO.getFileList().isEmpty())
        {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMGWinBusinessDTO.getFileList());
            pSMGWinBusinessDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        pSMGWinBusinessDTO.setCxstnSeq(winMngSeqIdgen.getNextIntegerId());

        return sMGWinBusinessMapper.insertWinBusiness(pSMGWinBusinessDTO);
    }

    /**
     * 상생 사업 수정
     */
    public int updateWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        //수정자
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pSMGWinBusinessDTO.setModId(cOUserDetailsDTO.getId());
        pSMGWinBusinessDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        //파일 처리
        if(pSMGWinBusinessDTO.getFileList() != null && !pSMGWinBusinessDTO.getFileList().isEmpty())
        {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMGWinBusinessDTO.getFileList());
            pSMGWinBusinessDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        }

        return sMGWinBusinessMapper.updateWinBusiness(pSMGWinBusinessDTO);
    }

    /**
     * 상생 사업 삭제
     */
    public int deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        return sMGWinBusinessMapper.deleteWinBusiness(pSMGWinBusinessDTO);
    }

    /**
     * 상생 사업 정렬 수정
     */
    public void updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
    {
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        pSMGWinBusinessDTO.setModId(cOUserDetailsDTO.getId());
        pSMGWinBusinessDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        SMGWinBusinessDTO newRow = sMGWinBusinessMapper.selectWinBusinessNewRow(pSMGWinBusinessDTO);

        if(newRow != null)
        {
            int newRowOrd = newRow.getExpsOrd();
            int orginOrd = pSMGWinBusinessDTO.getExpsOrd();

            pSMGWinBusinessDTO.setExpsOrd(newRowOrd);
            sMGWinBusinessMapper.updateOrder(pSMGWinBusinessDTO);

            newRow.setModIp(pSMGWinBusinessDTO.getModIp());
            newRow.setModId(pSMGWinBusinessDTO.getModId());
            newRow.setExpsOrd(orginOrd);
            sMGWinBusinessMapper.updateOrder(newRow);
        }
    }
}
