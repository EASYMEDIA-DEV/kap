package com.kap.service.impl.sm;

import com.kap.core.dto.SMDBTmncsDTO;
import com.kap.service.SMDBTmncsService;
import com.kap.service.dao.sm.SMDBTmncsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 이용약관 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMDBTmncsServiceImpl.java
 * @Description		: 이용약관 관리를 위한 ServiceImpl
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
public class SMDBTmncsServiceImpl implements SMDBTmncsService {

    // DAO
    private final SMDBTmncsMapper smdbTmncsMapper;

    /* 시퀀스 */
    private final EgovIdGnrService trmsDtlIdgen;

    /**
     * 상세를 조회한다.
     */
    public SMDBTmncsDTO selectTmncsDtl(SMDBTmncsDTO smdbTmncsDTO) throws Exception {
        return smdbTmncsMapper.selectTmncsDtl(smdbTmncsDTO);
    }

    /**
     * 게시물을 등록한다.
     */
    public int insertTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception {

        smdbTmncsDTO.setTmncsSeq(trmsDtlIdgen.getNextIntegerId());
        return smdbTmncsMapper.insertTmncs(smdbTmncsDTO);
    }
    /**
     * 게시물을 수정한다.
     */
    public int updateTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception {
        return smdbTmncsMapper.updateTmncs(smdbTmncsDTO);
    }
}
