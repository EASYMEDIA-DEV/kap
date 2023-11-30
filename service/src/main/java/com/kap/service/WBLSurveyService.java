package com.kap.service;

import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre> 
 * 상생협력체감도조사 Service
 * </pre>
 * 
 * @ClassName		: SVASurveyService.java
 * @Description		: 상생협력체감도조사 Service
 * @author 박준희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2023.11.20		박준희					최초생성
 * </pre>
 */ 
public interface WBLSurveyService {


	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;

	/**
	 * 리스트 등록
	 */
	public int insertSurveyList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception;

	/**
	 * 리스트 삭제
	 */
	public int deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;

	/**
	 *  상세
	 */
	public WBLSurveyMstInsertDTO selectSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception;

	/**
	 *  회차 목록을 조회한다.
	 */
	public WBLEpisdMstDTO selectEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;


	/**
	 * 리스트 삭제
	 */
	public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;

	/**
	 * 리스트 등록
	 */
	public int insertEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception;


	/**
	 *  회차 설문 목록을 조회한다.
	 */
	public WBLEpisdMstDTO selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;


}