package com.kap.service;

import com.kap.core.dto.SMELegNtcDTO;

/**
 * <pre>
 * 법적고지 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMELegNtcService.java
 * @Description		: 법적고지 관리를 위한 Service
 * @author 구은희
 * @since 2023.10.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.04		구은희				   최초 생성
 * </pre>
 */
public interface SMELegNtcService {
    /**
     * 법적고지 목록을 조회한다.
     */public SMELegNtcDTO selectLegNtcList(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 개수를 조회한다.
     */
    public int selectLegNtcCnt(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지 상세를 조회한다.
     */
    public SMELegNtcDTO selectLegNtcDtl(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지를 등록한다.
     */
    public int insertLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지를 수정한다.
     */
    public int updateLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;

    /**
     * 법적고지를 삭제한다.
     */
    public int deleteLegNtc(SMELegNtcDTO smeLegNtcDTO) throws Exception;
}
