package com.kap.service.dao;

import com.kap.core.dto.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 로그인/로그아웃
 * </pre>
 *
 * @ClassName		: COUserLgnMapper
 * @Description		: 로그인/로그아웃
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
@Mapper
public interface COUserLgnMapper {
	/**
	 * 로그인 정보를 가져온다.
	 */
	MPAUserDto getLoginInfo(COLoginDTO cOLoginDTO) throws Exception;
	/**
	 * 관리자 정보를 가져온다.
	 */
	COUserDetailsDTO actionLogin(MPAUserDto mpaUserDto) throws Exception;
    /**
   	 * 로그인 오류 횟수를 증가시킨다.
   	 */
	int updateLgnFailCntIncrs(MPAUserDto mpaUserDto) throws Exception;
	/**
   	 *  로그인 오류 횟수를 초기화한다.
   	 */
	int updateLgnFailCntInit(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
    /**
	 * 로그인 일시를 업데이트한다.
	 */
	int updateLastLgnDtm(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
    /**
   	 * 비밀번호를 변경한다.
   	 */
	int updatePwdChng(MPAUserDto mpaUserDto) throws Exception;

	/**
	 * 다음에 비밀번호를 변경한다.
	 */
	int updatePwdNextChng(MPAUserDto mpaUserDto) throws Exception;

	/**
	 * 아이디를 찾는다
	 * @param coIdFindDto
	 * @return
	 * @throws Exception
	 */

	MPAUserDto getIdFind(COIdFindDto coIdFindDto) throws Exception;
}