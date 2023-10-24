package com.kap.service.dao;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.EmfMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 관리자 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: COAAdmMapper.java
 * @Description		: 관리자 관리를 위한 DAO
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
@Mapper
public interface COAAdmMapper {
	/**
	 * 관리자 목록 갯수 조회
	 */
	public int getAdmListCnt(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 목록을 조회한다.
	 */
	public List<COAAdmDTO> selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;
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
	 * 관리자를 삭제한다.
	 */
	public int deleteAdm(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 메뉴를 등록한다.
	 */
	public int insertAdmMenu(COMenuDTO pCOMenuDTO) throws Exception;
	/**
	 * 관리자 메뉴를 삭제한다.
	 */
	public int deleteAdmMenu(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 변경전 관리자 메뉴를 조회한다.
	 */
	public String getAdmMenuInf(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 수정 마스터를 등록한다.
	 */
	public int insertAdmModMst(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 수정 상세를 등록한다.
	 */
	public int insertAdmModDtl(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 아이디 중복여부를 가져온다.
	 */
	public int getIdOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 이메일 중복여부를 가져온다.
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
	 * 삭제할 관리자 수정 마스터 정보를 조회한다.
	 */
	public List<COAAdmDTO> selectDelAdmModMst(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 수정 마스터를 삭제한다.
	 */
	public int deleteAdmModMst(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 수정 상세를 삭제한다.
	 */
	public int deleteAdmModDtl(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 비밀번호 이력리스트를 조회한다.
	 */
	public List<COAAdmDTO> selectPwdList(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 비밀번호 이력을 삭제한다.
	 */
	public int deletePwd(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 비밀번호 이력을 수정한다.
	 */
	public int updatePwd(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 비밀번호 이력을 삽입한다.
	 */
	public int insertPwd(COAAdmDTO pCOAAdmDTO) throws Exception;
	/**
	 * 관리자 비밀번호 사용가능여부를 조회한다.
	 */
	public int getPwdCheck(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 신고하기 기존 대상자 조회
	 */
	public List<EmfMap> getEthicList() throws Exception;

	/**
	 * 신고하기 기존 대상자 비밀번호 변경
	 */
	public int setEthicPwd(EmfMap dataMap) throws Exception;
	/**
	 * 관리자 시퀀스로 ID 조회한다.
	 */
	public List<COAAdmDTO> getSeqAdmList(COAAdmDTO pCOAAdmDTO) throws Exception;



	/**
	 * 관리자 권한 변경 로그를 가져온다.
	 */
	public List<COAAdmDTO> getAuthLogList(COAAdmDTO pCOAAdmDTO) throws Exception;

	/**
	 * 관리자 권한 변경 로그를 등록한다.
	 */
	public int insertAuthModLog(COAAdmDTO pCOAAdmDTO) throws Exception;

}