package com.kap.service;

import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;

import java.util.HashMap;

/**
 * <pre>
 * 교육차수 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBBEpisdService.java
 * @Description		: 교육차수 관리를 위한 Service
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		김학규				   최초 생성
 * </pre>
 */
public interface EBBEpisdService {

	/**
	 * 교육차수 목록을 조회한다.
	 */
	public EBBEpisdDTO selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public HashMap<String, Object> selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육 참여자 목록을 호출한다.
	 */
	public EBBPtcptDTO setPtcptList(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수를 등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육과정을 수정한다.
	 */
	public int updateEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 삭제한다.
	 */
	public int deleteEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 중복 체크
	 */
	public EBBEpisdDTO selectEpisdChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;

}

