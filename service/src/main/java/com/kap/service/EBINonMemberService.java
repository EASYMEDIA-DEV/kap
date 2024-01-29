package com.kap.service;

import com.kap.core.dto.eb.ebi.EBINonMemberDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <pre>
 * 비회원 교육 과정 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBINonMemberService.java
 * @Description		: 비회원 교육 과정 관리를 위한 Service
 * @author 장두석
 * @since 2023.12.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.15		장두석				   최초 생성
 * </pre>
 */
public interface EBINonMemberService {

	/**
	 * 비회원 교육 과정 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 엑셀 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberExcelList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 엑셀 생성 - 과정 목록
	 */
	public void excelDownload1(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception;

	/**
	 * 엑셀 생성 - 비회원 교육 신청자 목록
	 */
	public void excelDownload2(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception;

	/**
	 * 비회원 교육 과정 상세 조회
	 */
	public HashMap<String, Object> selectNonMemberDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 교육 참여자 목록 호출
	 */
	public EBINonMemberDTO setPtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 등록
	 */
	public int insertNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 수정
	 */
	public int updateNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 삭제
	 */
	public EBINonMemberDTO deleteNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 중복 체크
	 */
	public EBINonMemberDTO selectNonMemberChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 정원체크
	 */
	public EBINonMemberDTO selectFxnumChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 등록
	 */
	public EBINonMemberDTO setPtcptInfo(EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 목록 신청 취소
	 */
	public int updatePtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 신청 취소
	 */
	public int updatePtcpt(EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception;






	/**
	 * 사용자 - 비회원 교육 과정 신청 개수 조회
	 */
	public int searchPtcptCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 사용자 - 비회원 교육 신청한 과정 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberApplyList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 사용자 - 비회원 교육 과정 신청 상세
	 */
	public EBINonMemberDTO selectNonMemberApplyPtcptDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

}

