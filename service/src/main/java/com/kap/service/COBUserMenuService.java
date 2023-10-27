package com.kap.service;

import com.kap.core.dto.COMenuDTO;
import org.json.simple.JSONArray;

import java.util.List;

/**
 * <pre>
 * 메뉴 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: CODMenuService.java
 * @Description		: 메뉴 관리를 위한 Service
 * @author 허진영
 * @since 2020.10.19
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *	     since		  author	            description
 *    ==========    ==========    ==============================
 * 	  2020.10.19	  허진영	             최초 생성
 *    2023.10.19	 	 임서화				사용자 메뉴 분리
 * </pre>
 */
public interface COBUserMenuService {

	/**
	 * 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getMenuList(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴의 상세정보를 조회한다.
	 */
	public COMenuDTO selectMenuDtl(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 등록한다.
	 */
	public int insertMenu(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴명을 수정한다.
	 */
	public int updateMenuNm(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴 정보를 수정한다.
	 */
	public int updateMenuInf(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 이동한다.
	 */
	public int updateMenuPstn(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴를 삭제한다.
	 */
	public int deleteMenu(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 상위부모를 다 가져온다.
	 */
	public List<COMenuDTO> getParntData(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 하위노드를 다 가져온다.
	 */
	public List<COMenuDTO> getChildData(COMenuDTO cOMenuDTO) throws Exception;

	/**
	 * 메뉴 목록 일괄처리를 위한 재귀함수
	 */
	public JSONArray getJsonData(List<COMenuDTO> jsonList, int startNum, int paramSeq) throws Exception;
}