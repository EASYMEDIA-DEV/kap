package com.kap.service;

import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;

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
	 * 교육과정연계상세목록을 조회한다.
	 */
	public List<EBACouseDTO> selectEdctnRelList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육차수 목록을 조회한다. (사용자 전체교육일정 레이어)
	 */
	public EBBEpisdDTO selectEpisdLayerList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육과정을 등록한다.
	 */
	public int insertCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 수정한다.
	 */
	public int updateCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 현재 등록된 교육과정에 종속된 교육차수 체크
	 */
	public int selectEpisdListChk(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 삭제한다.
	 */
	public int deleteCouse(EBACouseDTO eBACouseDTO) throws Exception;


	/**
	 * 신청한 과정 목록을 호출한다.
	 */
	public EBBPtcptDTO selectApplyEpisdList(EBBPtcptDTO eBBPtcptDTO) throws Exception;



	/**
	 * 교육과정을  복사한다.
	 */
	//public int copyCouse(EBACouseDTO eBACouseDTO) throws Exception;




}

