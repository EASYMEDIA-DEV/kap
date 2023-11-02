package com.kap.service;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EmfMap;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * 관리자 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COCAdmService.java
 * @Description		: 관리자 관리를 위한 Service
 * @author 허진영
 * @since 2020.10.22
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2020.10.22		허진영				   최초 생성
 * </pre>
 */
public interface COAAdmService {

	/**
	 * 관리자 목록을 조회한다.
	 */
	public COAAdmDTO selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자 상세를 조회한다.
	 */
	public COAAdmDTO selectAdmDtl(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자를 등록한다.
	 */
	public int insertAdm(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자를 수정한다.
	 */
	public int updateAdm(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자를 수정한다. (내 정보변경)
	 */
	public int updatePrsnData(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자를 삭제한다.
	 */
	public int deleteAdm(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 아이디 중복여부를 가져온다.
	 */
	public int getIdOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 이메일 중복여부를 가져온다
	 */
	public int getEmailOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 비밀번호를 초기화한다.
	 */
	public int updatePwdInit(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자 로그인 세션을 조회한다.
	 */
	public String getAdmSessionId(String adminId) throws Exception;

	/**
	 * 엑셀 생성
	 */
	public void excelDownload(COAAdmDTO pCOAAdmDTO, HttpServletResponse response) throws Exception;

	/**
	 * 비밀번호 사용 가능여부를 가져온다
	 */
	public List<String> getPwdCheck(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 신고하기 기존 대상자 비밀번호 변경
	 */
	public int setEthicList() throws Exception;

	/**
	 * 관리자 시퀀스로 ID 조회한다.
	 * @throws 비지니스 로직이나 DAO 처리 중 에러가 발생할 경우 Exception을 Throw 한다.
	 */
	public List<COAAdmDTO> getSeqAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자 권한 변경 로그를 가져온다.
	 * @throws 비지니스 로직이나 DAO 처리 중 에러가 발생할 경우 Exception을 Throw 한다.
	 */
	public COAAdmDTO getAuthLogList(COAAdmDTO pCOAAdmDTO) throws Exception;

}

