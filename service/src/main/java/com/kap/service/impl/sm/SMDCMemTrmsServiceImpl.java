package com.kap.service.impl.sm;

import com.kap.core.dto.sm.smd.SMDCMemTrmsDTO;
import com.kap.service.SMDCMemTrmsService;
import com.kap.service.dao.sm.SMDCMemTrmsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 회원약관 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMDCMemTrmsServiceImpl.java
 * @Description		: 회원약관 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.06		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMDCMemTrmsServiceImpl implements SMDCMemTrmsService {

    // DAO
    private final SMDCMemTrmsMapper smdcMemTrmsMapper;

    /* 시퀀스 */
    private final EgovIdGnrService memTrmsDtlIdgen;
    /**
     * 상세를 조회한다.
     */
    public SMDCMemTrmsDTO selectMemTrmsDtl(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception {
        return smdcMemTrmsMapper.selectMemTrmsDtl(smdcMemTrmsDTO);
    }

    /**
     * 게시물을 등록한다.
     */
    public int insertMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception {
        smdcMemTrmsDTO.setMemTrmsSeq(memTrmsDtlIdgen.getNextIntegerId());
       return smdcMemTrmsMapper.insertMemTrms(smdcMemTrmsDTO);
    }
    /**
     * 게시물을 수정한다.
     */
    public int updateMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception {
        return smdcMemTrmsMapper.updateMemTrms(smdcMemTrmsDTO);
    }
}
