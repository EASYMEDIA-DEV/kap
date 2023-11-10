package com.kap.service;

import com.kap.core.dto.SMCMnPopDTO;

/**
 * <pre>
 * 팝업 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: SMCMnPopService.java
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
public interface SMCMnPopService {

    /**
     * 팝업 목록을 조회한다.
     */
    public SMCMnPopDTO selectMnPopList(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업 상세를 조회한다.
     */
    public SMCMnPopDTO selectMnPopDtl(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업을 수정한다.
     */
    public int updateMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업을 미노출 처리한다.
     */
    public int updateUseYn(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업 정렬을 수정한다.
     */
    public void updateOrder(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업 개수를 조회한다.
     */
    public int selectUseMnPopCnt(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업을 등록한다.
     */
    public int insertMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 팝업을 삭제한다.
     */
    public int deleteMnPop(SMCMnPopDTO smcMnPopDTO) throws Exception;

    /**
     * 정렬할 팝업을 조회한다.
     */
    public SMCMnPopDTO selectPopNewRow(SMCMnPopDTO smcMnPopDTO) throws Exception;

}
