package com.kap.service.dao;

import com.kap.core.dto.*;
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
    public COUserDto getLoginInfo(COLoginDTO cOLoginDTO) throws Exception;
	/**
	 * 관리자 정보를 가져온다.
	 */
    public COUserDetailsDTO actionLogin(COUserDto cOUserDto) throws Exception;
    /**
   	 * 로그인 오류 횟수를 증가시킨다.
   	 */
	public int updateLgnFailCntIncrs(COUserDto coUserDto) throws Exception;
	/**
   	 *  로그인 오류 횟수를 초기화한다.
   	 */
	public int updateLgnFailCntInit(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
    /**
	 * 로그인 일시를 업데이트한다.
	 */
    public int updateLastLgnDtm(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
    /**
   	 * 비밀번호를 변경한다.
   	 */
	public int updatePwdChng(COUserDto cOUserDto) throws Exception;

	/**
	 * 다음에 비밀번호를 변경한다.
	 */
	public int updatePwdNextChng(COUserDto cOUserDto) throws Exception;


	/**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getDriveMenuList(COUserDto cOUserDto) throws Exception;
	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
    public List<COMenuDTO> getMenuList(COUserDetailsDTO cOUserDetailsDTO) throws Exception;
	/**
	 * 로그인 처리에 따른 메뉴를 가져온다.
	 */
	public List<COMenuDTO> getUserMenuList() throws Exception;
	/**
	 * 기존 비밀번호 사용여부를 확인한다.
	 */
	public List<String> getPwdHistoryCheck(COUserDto cOUserDto) throws Exception;
	/**
	 * 기존 비밀번호를 삭제한다.
	 */
	public int deletePwdHistory(COUserDto cOUserDto) throws Exception;
	/**
	 * 기존 비밀번호 정렬값 수정한다.
	 */
	public int updatePwdHistory(COUserDto cOUserDto) throws Exception;
	/**
	 * 기존 비밀번호를 삽입한다.
	 */
	public int insertPwdHistory(COUserDto cOUserDto) throws Exception;
	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(COUserDto cOUserDto) throws Exception;

	COUserDto getIdFind(COIdFindDto coIdFindDto) throws Exception;
}