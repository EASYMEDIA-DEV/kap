package com.kap.service;

import com.kap.core.dto.SMHSmsSendYnDTO;

/**
 * <pre>
 * SMS 발송 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMHSmsSendYnService.java
 * @Description		: SMS 발송 관리를 위한 Service
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
public interface SMHSmsSendYnService {

    /**
     * 상세를 조회한다.
     */
    public SMHSmsSendYnDTO selectSmsSendYnDtl(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception;

    /**
     * SMS 발송 여부를 수정한다.
     */
    public int updateSmsSendYn(SMHSmsSendYnDTO smhSmsSendYnDTO) throws Exception;

}
