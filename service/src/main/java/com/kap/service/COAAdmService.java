package com.kap.service;

import com.kap.core.dto.COAAdmDTO;

/**
 * <pre> 
 * 관리자 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COCAdmService.java
 * @Description		: 관리자 관리를 위한 Service
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
public interface COAAdmService {

	/**
	 * 관리자 목록을 조회한다.
	 */
	public COAAdmDTO selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;

}