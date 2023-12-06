package com.kap.service;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre> 
 * 로그인/로그아웃을 위한 Service
 * </pre>
 * 
 * @ClassName		: COUserLgnService.java
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
public interface COUserLgnService {
	
	/**
	 * 로그인을 처리한다.
	 */
	public COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception;
    /**
	 * 로그아웃을 처리한다.
	 */
    public void actionLogout(MPAUserDto mpaUserDto, HttpServletRequest request) throws Exception;
    /**
     * 비밀번호를 변경한다.
     */
    public String setPwdChng(MPAUserDto mpaUserDto) throws Exception;

	/**
	 * 다음에 비밀번호 변경
	 * @param mpaUserDto
	 * @return
	 * @throws Exception
	 */
    public String setPwdNextChng(MPAUserDto mpaUserDto) throws Exception;
    /**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
    public List<COMenuDTO> getDriveMenuList(MPAUserDto mpaUserDto) throws Exception;
	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
	public List<COMenuDTO> getMenuList(COUserDetailsDTO cOUserDetailsDTO) throws Exception;

	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(MPAUserDto mpaUserDto) throws Exception;


	MPAUserDto getIdFind(COIdFindDto coIdFindDto) throws Exception;
}