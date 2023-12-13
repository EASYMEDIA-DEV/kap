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
 * @author 양현우
 * @since 2023.12.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				   description
 *   ===========    ==============    =============================
 *   2023.12.06			양현우			 		최초생성
 * </pre>
 */
public interface COUserLgnService {
	
	/**
	 * 로그인을 처리한다.
	 */
	COLoginDTO actionLogin(COLoginDTO cOLoginDTO, HttpServletRequest request) throws Exception;
    /**
	 * 로그아웃을 처리한다.
	 */
	void actionLogout(HttpServletRequest request) throws Exception;
    /**
     * 비밀번호를 변경한다.
     */
	String setPwdChng(MPAUserDto mpaUserDto) throws Exception;

	/**
	 * 다음에 비밀번호 변경
	 * @param mpaUserDto
	 * @return
	 * @throws Exception
	 */
	String setPwdNextChng(MPAUserDto mpaUserDto) throws Exception;


	/**
	 * id를 찾는다
	 * @param coIdFindDto
	 * @return
	 * @throws Exception
	 */
	MPAUserDto getIdFind(COIdFindDto coIdFindDto) throws Exception;

	MPAUserDto getPasswordChk(COLoginDTO cOLoginDTO) throws Exception;

}