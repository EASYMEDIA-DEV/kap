package com.kap.service;


import com.kap.core.dto.COSystemLogDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre> 
 * 접속로그 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COCAssLogService.java
 * @Description		: 접속로그 관리를 위한 Service
 * @author 허진영
 * @since 2022.04.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2022.04.08		허진영				   최초 생성
 * </pre>
 */
public interface COCAssLogService {

    /**
     * 접속로그 목록을 조회한다.
     */
    public COSystemLogDTO selectAssLogList(COSystemLogDTO pCODSysLogDTO) throws Exception;

    /**
     * 엑셀 생성
     */
    public void excelDownload(COSystemLogDTO pCODSysLogDTO, HttpServletResponse response) throws Exception;
}
