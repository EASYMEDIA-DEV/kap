package com.kap.service;

import com.kap.core.dto.COSystemLogDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre> 
 * 로그 관리(접속로그/이용로그) 관리를 위한 Service
 * </pre>
 * 
 * @ClassName		: CODSysLogService.java
 * @Description		: 로그 관리(접속로그/이용로그) 관리를 위한 Service
 * @author 손태주
 * @since 2022.02.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.02.17		손태주					최초생성
 * </pre>
 */

public interface CODSysLogService {
	/**
	 * 로그 목록을 조회한다.
	 */
	public COSystemLogDTO selectSysLogList(COSystemLogDTO cOSystemLogDTO) throws Exception;

	/**
	 * 엑셀 생성
	 */
	public void excelDownload(COSystemLogDTO pCODSysLogDTO, HttpServletResponse response) throws Exception;
}