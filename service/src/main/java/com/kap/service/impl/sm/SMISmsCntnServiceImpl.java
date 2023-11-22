package com.kap.service.impl.sm;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.SMISmsCntnDTO;
import com.kap.service.SMISmsCntnService;
import com.kap.service.dao.sm.SMISmsCntnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * SMS 내용 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMISmsCntnServiceImpl.java
 * @Description		: SMS 내용 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMISmsCntnServiceImpl implements SMISmsCntnService {

    // DAO
    private final SMISmsCntnMapper smiSmsCntnMapper;
    /* 시퀀스 */
    private final EgovIdGnrService smiSmsCntnDtlIdgen;

    /**
     * 목록을 조회한다.
     */
    public SMISmsCntnDTO selectSmsCntnList(SMISmsCntnDTO smiSmsCntnDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(smiSmsCntnDTO.getPageIndex());
        page.setRecordCountPerPage(smiSmsCntnDTO.getListRowSize());

        page.setPageSize(smiSmsCntnDTO.getPageRowSize());

        smiSmsCntnDTO.setFirstIndex( page.getFirstRecordIndex() );
        smiSmsCntnDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        smiSmsCntnDTO.setList( smiSmsCntnMapper.selectSmsCntnList(smiSmsCntnDTO) );
        smiSmsCntnDTO.setTotalCount( smiSmsCntnMapper.selectSmsCntnCnt(smiSmsCntnDTO) );
        return smiSmsCntnDTO;
    }

    /**
     * 상세를 조회한다.
     */
    public SMISmsCntnDTO selectSmsCntnDtl(SMISmsCntnDTO smiSmsCntnDTO) throws Exception {
        return smiSmsCntnMapper.selectSmsCntnDtl(smiSmsCntnDTO);
    }

    /**
     * SMS 내용 등록 시 구분 코드 중복을 확인한다.
     */
    public int selectSmsCodeDupCheck(SMISmsCntnDTO smiSmsCntnDTO) throws Exception {
        return smiSmsCntnMapper.selectSmsCodeDupCheck(smiSmsCntnDTO);
    }

    /**
     * SMS 내용을 등록한다.
     */
    public int insertSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception {

        smiSmsCntnDTO.setSmsCntnSeq(smiSmsCntnDtlIdgen.getNextIntegerId());
        return smiSmsCntnMapper.insertSmsCntn(smiSmsCntnDTO);
    }

    /**
     * SMS 내용을 수정한다.
     */
    public int updateSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception {
        return smiSmsCntnMapper.updateSmsCntn(smiSmsCntnDTO);
    }
}
