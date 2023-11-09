package com.kap.service;

import com.kap.core.dto.SMDBTmncsDTO;

/**
 * <pre>
 * 이용약관 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMDBTmncsService.java
 * @Description		: 이용약관 관리를 위한 Service
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
public interface SMDBTmncsService {

    /**
     * 이용약관 상세를 조회한다.
     */
    public SMDBTmncsDTO selectTmncsDtl(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

    /**
     * 이용약관을 등록한다.
     */
    public int insertTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

    /**
     * 이용약관을 수정한다.
     */
    public int updateTmncs(SMDBTmncsDTO smdbTmncsDTO) throws Exception;

}
