package com.kap.service;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstInsertDTO;
import com.kap.core.dto.sv.sva.SVASurveyMstSearchDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <pre> 
 * 설문조사 Service
 * </pre>
 * 
 * @ClassName		: SVASurveyService.java
 * @Description		: 설문조사 Service
 * @author 박준희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2023.11.06		박준희					최초생성
 * </pre>
 */ 
public interface SVASurveyService {

	/**
	 * 코드 목록을 조회한다.
	 */
	public HashMap<String, List<COCodeDTO>> getSurveyTypeList() throws Exception;


	/**
	 *  목록을 조회한다.
	 */
	public SVASurveyMstSearchDTO selectSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;

	/**
	 * 리스트 삭제
	 */
	public int deleteSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;

	/**
	 * 리스트 등록
	 */
	public int insertSurveyList(SVASurveyMstInsertDTO sVASurveyMstInsertDTO, HttpServletRequest request) throws Exception;

	/**
	 * 리스트 수정
	 */
	public int updateSurveyList(SVASurveyMstInsertDTO sVASurveyMstInsertDTO, HttpServletRequest request) throws Exception;

	/**
	 * 설문 상세
	 */
	public SVASurveyMstInsertDTO selectSurveyDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;

	/**
	 * 응답 상세(상생)
	 */
	public SVASurveyMstInsertDTO selectSurveyTypeWinDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;

	/**
	 * 응답 상세(교육)
	 */
	public SVASurveyMstInsertDTO selectSurveyTypeEduDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;


	/**
	 * 응답 상세(컨설팅)
	 */
	public SVASurveyMstInsertDTO selectSurveyTypeConDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception;

}