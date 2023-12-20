package com.kap.service;

import com.kap.core.dto.sm.smk.SMKTrendDTO;

/**
 * <pre>
 * TREND 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMKTrendService.java
 * @Description		: TREND 관리를 위한 Service
 * @author 구은희
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		구은희				   최초 생성
 * </pre>
 */
public interface SMKTrendService {

    /**
     * TREND 목록을 조회한다.
     */
    public SMKTrendDTO selectTrendList(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 상세를 조회한다.
     */
    public SMKTrendDTO selectTrendDtl(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 수정한다.
     */
    public int updateTrend(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 미노출 처리한다.
     */
    public int updateUseYn(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 정렬을 수정한다.
     */
    public void updateOrder(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 개수를 조회한다.
     */
    public int selectUseTrendCnt(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 등록한다.
     */
    public int insertTrend(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * TREND 삭제한다.
     */
    public int deleteTrend(SMKTrendDTO smkTrendDTO) throws Exception;

    /**
     * 정렬할 TREND 조회한다.
     */
    public SMKTrendDTO selectTrendNewRow(SMKTrendDTO smkTrendDTO) throws Exception;

}
