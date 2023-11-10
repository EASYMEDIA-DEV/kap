package com.kap.service;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EBACouseDTO;
import com.kap.core.dto.EmfMap;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육과정 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBACouseService.java
 * @Description		: 교육과정 관리를 위한 Service
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
public interface EBACouseService {

	/**
	 * 교육과정 목록을 조회한다.
	 */
	public EBACouseDTO selectCouseList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정 상세를 조회한다.
	 */
	public HashMap<String, Object> selectCouseDtl(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 등록한다.
	 */
	public int insertCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 수정한다.
	 */
	public int updateCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 삭제한다.
	 */
	public int deleteCouse(EBACouseDTO eBACouseDTO) throws Exception;


	/**
	 * 교육과정을  복사한다.
	 */
	public int copyCouse(EBACouseDTO eBACouseDTO) throws Exception;

}

