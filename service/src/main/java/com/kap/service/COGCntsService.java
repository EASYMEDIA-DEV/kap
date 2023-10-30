package com.kap.service;

import com.kap.core.dto.COGCntsDTO;

/**
 * <pre>
 * CMS 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COGCntsService.java
 * @Description		: CMS 관리를 위한 Service
 * @author 임서화
 * @since 2023.09.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.07		임서화				   최초 생성
 * </pre>
 */
public interface COGCntsService {

	/**
	 * CMS 목록을 조회한다.
	 */
	public COGCntsDTO selectCntsList(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS 상세를 조회한다.
	 */
	public COGCntsDTO selectCntsDtl(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS를 등록한다.
	 */
	public int insertCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS를 수정한다.
	 */
	public int updateCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

	/**
	 * CMS를 삭제한다.
	 */
	public int deleteCnts(COGCntsDTO pCOGCntsDTO) throws Exception;

}