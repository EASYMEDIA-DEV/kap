package com.kap.service;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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

}