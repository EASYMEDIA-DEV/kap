package com.kap.service;

import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO , HttpServletRequest request) throws Exception;

	/**
	 * 리스트 등록
	 */
	public int insertEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception;


	/**
	 *  회차 설문 목록을 조회한다.
	 */
	public WBLEpisdMstDTO selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception;

	/**
	 * 샘플 엑셀 생성
	 */
	public void excelSampleDownload(HttpServletResponse response) throws Exception;

	/**
	 * 리스트 등록
	 */
	public int insertSurveyExcelList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request , MultipartFile file) throws Exception;

	/**
	 * 응답 초기화
	 */
	public int updateSurveyRspn(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request ) throws Exception;

	/**
	 * 엑셀 생성
	 */
	void excelDownload(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, HttpServletResponse response ) throws Exception;


	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectFrontSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;

	/**
	 * 설문 미참여
	 */
	public int updateNoSurvey(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception;

	/**
	 *  상세
	 */
	public WBLSurveyMstInsertDTO selectFrontSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception;

	/**
	 * 설문 참여
	 */
	public int updateSurvey(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO) throws Exception;

	/**
	 * 인증번호 발송
	 */
	public int updateSendDtm(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO) throws Exception;

}