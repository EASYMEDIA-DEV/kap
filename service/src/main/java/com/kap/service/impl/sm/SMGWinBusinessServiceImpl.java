package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMGWinBusinessDTO;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.SMGWinBusinessService;
import com.kap.service.dao.sm.SMGWinBusinessMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
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
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SMGWinBusinessServiceImpl implements SMGWinBusinessService {

    // DAO
    private final COFileService cOFileService;

    /* 상생 사업 시퀀스 */
    private final EgovIdGnrService winBusIdgen;
    
    private final SMGWinBusinessMapper sMGWinBusinessMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    /**
     * 상생 사업 목록 조회
     */
    public SMGWinBusinessDTO selectWinBusinessList(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
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
     * 상생 사업 상세 조회
     */
    public SMGWinBusinessDTO selectWinBusinessDtl(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        SMGWinBusinessDTO info = new SMGWinBusinessDTO();

        if (!"".equals(pSMGWinBusinessDTO.getDetailsKey()))
        {
            info = sMGWinBusinessMapper.selectWinBusinessDtl(pSMGWinBusinessDTO);
        }

        return info;
    }

    /**
     * 상생 사업 등록
     */
    public int insertWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMGWinBusinessDTO.getFileList());
        pSMGWinBusinessDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        pSMGWinBusinessDTO.setRespCnt(sMGWinBusinessMapper.insertWinBusiness(pSMGWinBusinessDTO));
        return pSMGWinBusinessDTO.getRespCnt();
    }

    /**
     * 상생 사업 삭제
     */
    public int deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        return sMGWinBusinessMapper.deleteWinBusiness(pSMGWinBusinessDTO);
    }

    /**
     * 상생 사업 수정
     */
    public int updateWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pSMGWinBusinessDTO.getFileList());
        pSMGWinBusinessDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        pSMGWinBusinessDTO.setRespCnt(sMGWinBusinessMapper.updateWinBusiness(pSMGWinBusinessDTO));
        return pSMGWinBusinessDTO.getRespCnt();
    }

    /**
     * 상생 사업 정렬 수정
     */
    public void updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {

        SMGWinBusinessDTO newRow = sMGWinBusinessMapper.selectWinBusinessNewRow(pSMGWinBusinessDTO);

        if(newRow != null){

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

    /**
     * 정렬할 상생 사업을 조회한다.
     */
    public SMGWinBusinessDTO selectWinBusinessNewRow(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception {
        return sMGWinBusinessMapper.selectWinBusinessNewRow(pSMGWinBusinessDTO);
    }
}
