package com.kap.service;

import com.kap.core.dto.sm.smd.SMDCMemTrmsDTO;

/**
 * <pre>
 * 회원약관 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMDCMemTrmsService.java
 * @Description		: 회원약관 관리를 위한 Service
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
public interface SMDCMemTrmsService {

    /**
     * 회원약관 상세를 조회한다.
     */
    public SMDCMemTrmsDTO selectMemTrmsDtl(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

    /**
     * 이용약관을 등록한다.
     */
    public int insertMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

    /**
     * 이용약관을 수정한다.
     */
    public int updateMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO) throws Exception;

}
