package com.kap.service.impl.sm;

import com.kap.core.dto.sm.smh.SMHSmsSendYnDTO;
import com.kap.service.SMHSmsSendYnService;
import com.kap.service.dao.sm.SMHSmsSendYnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * SMS 발송 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: SMHmpgeServiceImpl.java
 * @Description		: SMS 발송 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SMHSmsSendYnServiceImpl implements SMHSmsSendYnService {

    // DAO
    private final SMHSmsSendYnMapper smsSendYnMapper;

    /**
     * 상세를 조회한다.
     */
    public SMHSmsSendYnDTO selectSmsSendYnDtl(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception {
        return smsSendYnMapper.selectSmsSendYnDtl(smhSmsSendYnDTO);
    }

    /**
     * 양식 관리를 수정한다.
     */
    public int updateSmsSendYn(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception {
        return smsSendYnMapper.updateSmsSendYn(smhSmsSendYnDTO);
    }
}
