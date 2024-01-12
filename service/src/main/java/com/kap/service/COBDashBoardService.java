package com.kap.service;

import com.kap.core.dto.COBDashBoardDTO;

/**
 * <pre>
 * 대쉬보드 Service
 * </pre>
 *
 * @ClassName		: COBDashBoardService.java
 * @Description		: 대쉬보드 Service
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2024.01.12		박준희					최초생성
 * </pre>
 */
public interface COBDashBoardService {

    /**
     * 대쉬보드
     */
    public COBDashBoardDTO selectDashboard(COBDashBoardDTO cOBDashBoardDTO) throws Exception;

}
