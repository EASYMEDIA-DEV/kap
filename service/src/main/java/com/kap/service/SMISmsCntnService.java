package com.kap.service;

import com.kap.core.dto.SMISmsCntnDTO;

/**
 * <pre>
 * SMS 내용 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMISmsCntnService.java
 * @Description		: SMS 내용 관리를 위한 Service
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
public interface SMISmsCntnService {

    /**
     * 목록을 조회한다.
     */
    public SMISmsCntnDTO selectSmsCntnList(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * 상세를 조회한다.
     */
    public SMISmsCntnDTO selectSmsCntnDtl(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * SMS 내용 등록 시 구분 코드 중복을 확인한다.
     */
    public int selectSmsCodeDupCheck(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * SMS 내용을 등록한다.
     */
    public int insertSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

    /**
     * SMS 내용을 수정한다.
     */
    public int updateSmsCntn(SMISmsCntnDTO smiSmsCntnDTO) throws Exception;

}
