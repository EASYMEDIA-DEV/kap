package com.kap.service;

import com.kap.core.dto.SMCPopDTO;

/**
 * <pre>
 * 팝업 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMCPopService.java
 * @Description		: 팝업 관리를 위한 Service
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.21		구은희				   최초 생성
 * </pre>
 */
public interface SMCPopService {

    /**
     * 팝업 목록을 조회한다.
     */
    public SMCPopDTO selectMnPopList(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업 상세를 조회한다.
     */
    public SMCPopDTO selectMnPopDtl(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업을 수정한다.
     */
    public int updateMnPop(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업을 미노출 처리한다.
     */
    public int updateUseYn(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업 정렬을 수정한다.
     */
    public void updateOrder(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업 개수를 조회한다.
     */
    public int selectUseMnPopCnt(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업을 등록한다.
     */
    public int insertMnPop(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 팝업을 삭제한다.
     */
    public int deleteMnPop(SMCPopDTO smcPopDTO) throws Exception;

    /**
     * 정렬할 팝업을 조회한다.
     */
    public SMCPopDTO selectPopNewRow(SMCPopDTO smcPopDTO) throws Exception;

}
