package com.kap.service;

import com.kap.core.dto.COCodeDTO;
import org.json.simple.JSONArray;

import java.util.List;

/**
 * <pre> 
 * 코드 관리를 위한 Service
 * </pre>
 * 
 * @ClassName		: COFCodeService.java
 * @Description		: 코드 관리를 위한 Service
 * @author 허진영
 * @since 2021.04.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2021.04.26		허진영					최초생성
 * </pre>
 */ 
public interface COFCodeService {
	
	/**
	 * 코드 목록을 조회한다.
	 */
	public List<COCodeDTO> getCodeList(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드 상세정보를 조회한다.
	 */
	public COCodeDTO selectCodeDtl(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드를 등록한다.
	 */
	public int insertCode(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드명을 수정한다.
	 */
	public int updateCodeNm(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드 정보를 수정한다.
	 */
	public int updateCodeInf(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드를 이동한다.
	 */
	public int updateCodePstn(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드를 삭제한다.
	 */
	public int deleteCode(COCodeDTO cOCodeDTO) throws Exception;
	
	/**
	 * 코드 목록 일괄처리를 위한 재귀함수
	 */
	public JSONArray getJsonData(List<COCodeDTO> jsonList, int startNum, int paramSeq) throws Exception;
}