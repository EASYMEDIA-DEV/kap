package com.kap.service.impl.cb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.CBATechGuidanceDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.dao.cb.CBATechGuidanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <pre>
 * 컨설팅 기술 지도를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMBMnVslServiceImpl.java
 * @Description		: 컨설팅 기술 지도를 위한 ServiceImpl
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
public class CBATechGuidanceServiceImpl implements CBATechGuidanceService {

    // DAO
    private final COFileService cOFileService;

    /* 컨설팅 기술 지도 시퀀스 */
    private final EgovIdGnrService winBusIdgen;

    private final CBATechGuidanceMapper cBATechGuidanceMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public CBATechGuidanceDTO selectTechGuidanceList(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCBATechGuidanceDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceDTO.getPageRowSize());

        pCBATechGuidanceDTO.setFirstIndex(page.getFirstRecordIndex());
        pCBATechGuidanceDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pCBATechGuidanceDTO.setTotalCount(cBATechGuidanceMapper.selectTechGuidanceTopCnt(pCBATechGuidanceDTO));
        pCBATechGuidanceDTO.setList(cBATechGuidanceMapper.selectTechGuidanceList(pCBATechGuidanceDTO));

        return pCBATechGuidanceDTO;
    }

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceDTO selectTechGuidanceDtl(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        CBATechGuidanceDTO info = new CBATechGuidanceDTO();

        if (!"".equals(pCBATechGuidanceDTO.getDetailsKey()))
        {
            info = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceDTO);
        }

        return info;
    }

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceDTO.getFileList());
        pCBATechGuidanceDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        pCBATechGuidanceDTO.setRespCnt(cBATechGuidanceMapper.insertTechGuidance(pCBATechGuidanceDTO));
        return pCBATechGuidanceDTO.getRespCnt();
    }

    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        return cBATechGuidanceMapper.deleteTechGuidance(pCBATechGuidanceDTO);
    }

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceDTO.getFileList());
        pCBATechGuidanceDTO.setFileSeq(fileSeqMap.get("fileSeq"));

        pCBATechGuidanceDTO.setRespCnt(cBATechGuidanceMapper.updateTechGuidance(pCBATechGuidanceDTO));
        return pCBATechGuidanceDTO.getRespCnt();
    }

}
