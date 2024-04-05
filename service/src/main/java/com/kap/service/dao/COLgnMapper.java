package com.kap.service.dao;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COLoginDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COUserDetailsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 로그인/로그아웃을 위한 DAO
 * </pre>
 *
 * @ClassName		: COBLgnDAO.java
 * @Description		: 로그인/로그아웃을 위한 DAO
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
public interface COLgnMapper {
	/**
	 * 로그인 정보를 가져온다.
	 */
    public COAAdmDTO getLoginInfo(COLoginDTO cOLoginDTO) throws Exception;
	/**
	 * 관리자 정보를 가져온다.
	 */
    public COUserDetailsDTO actionLogin(COAAdmDTO cOAAdmDTO) throws Exception;
    /**
   	 * 로그인 오류 횟수를 증가시킨다.
   	 */
	public int updateLgnFailCntIncrs(COAAdmDTO cOAAdmDTO) throws Exception;
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
	public int updatePwdChng(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * 드라이브 메뉴 목록을 조회한다.
	 */
	public List<COMenuDTO> getDriveMenuList(COAAdmDTO cOAAdmDTO) throws Exception;
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
	public List<String> getPwdHistoryCheck(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * 기존 비밀번호를 삭제한다.
	 */
	public int deletePwdHistory(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * 기존 비밀번호 정렬값 수정한다.
	 */
	public int updatePwdHistory(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * 기존 비밀번호를 삽입한다.
	 */
	public int insertPwdHistory(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * CMS Root 메뉴 정보를 가져온다.
	 */
	public COMenuDTO getCmsRootInf(COAAdmDTO cOAAdmDTO) throws Exception;
	/**
	 * CMS 메뉴 리스트 조회
	 */
	public List<COMenuDTO> getCmsList(COUserDetailsDTO pCOUserDetailsDTO) throws Exception; /* 2024-04-05 일반관리자 CMS메뉴 안 나오는 문제 해결 */
	/**
	 * CMS 메뉴 시퀀스 조회
	 */
	public String getCmsSeq(COAAdmDTO pCOAAdmDTO) throws Exception; /* 2024-04-05 일반관리자 CMS메뉴 안 나오는 문제 해결 */
}