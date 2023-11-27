package com.kap.service;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COLoginDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre> 
 * 로그인/로그아웃을 위한 Service
 * </pre>
 * 
 * @ClassName		: COBLgnService.java
 * @Description		: 로그인/로그아웃을 위한 Service
 * @author 허진영
 * @since 2020.10.23
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2020.10.23			허진영			 		최초생성
 * </pre>
 */
public interface COLgnService {
	
	/**
	 * 로그인을 처리한다.
	 */
	public COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception;
    /**
	 * 로그아웃을 처리한다.
	 */
    public void actionLogout(COAAdmDTO cOAAdmDTO, HttpServletRequest request) throws Exception;
    /**
     * 비밀번호를 변경한다.
     */
    public String setPwdChng(COAAdmDTO cOAAdmDTO) throws Exception;
    /**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
    public List<COMenuDTO> getDriveMenuList(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
	public List<COMenuDTO> getMenuList(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
	/**
	 * 비밀번호 이력 업데이트한다.
	 */
	public int setPwdHistory(COAAdmDTO cOAAdmDTO) throws Exception;

	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(COAAdmDTO cOAAdmDTO) throws Exception;
}