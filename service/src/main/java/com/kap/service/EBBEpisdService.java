package com.kap.service;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.eb.ebb.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 교육차수 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: EBBEpisdService.java
 * @Description		: 교육차수 관리를 위한 Service
 * @author 김학규
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		김학규				   최초 생성
 * </pre>
 */
public interface EBBEpisdService {

	/**
	 * 교육차수 목록을 조회한다.
	 */
	public EBBEpisdDTO selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 목록을 조회한다.
	 */
	public int selectEpisdListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육과정에 속한 차수목록을 호출한다.
	 */
	public EBBEpisdDTO selectCouseChildEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 마이페이지 - 최근1년간 신청내역 호출(교육사업)
	 */
	public int selectMypageEduCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 마이페이지 - 교육/세미나 사업 신청내역
	 */
	public EBBEpisdDTO selectMypageEduList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 목록을 조회한다.(엑셀용)
	 */
	public EBBEpisdExcelDTO selectEpisdExcelList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 엑셀 생성 - 교육차수관리 목록
	 */
	public void excelDownload1(EBBEpisdExcelDTO eBBEpisdExcelDTO, HttpServletResponse response) throws Exception;

	/**
	 * 엑셀 생성 - 교육차수관리 참여자 목록
	 */
	public void excelDownload2(EBBPtcptDTO eBBPtcptDTO, HttpServletResponse response) throws Exception;

	/**
	 * 엑셀 생성 - 교육차수관리 참여자의 출석부 목록
	 */
	public void excelDownload3(List<EBBPtcptDTO> tableAtndcList, EBBPtcptDTO eBBPtcptDTO, HttpServletResponse response) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public HashMap<String, Object> selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수에 등록된 온라인강의 목록 호출
	 */
	public EBBLctrDTO selectLctrDtlList(EBBLctrDTO eBBLctrDTO) throws Exception;


	/**
	 * 교육 참여자 목록을 호출한다.
	 */
	public EBBPtcptDTO setPtcptList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육 참여자 출석부 목록을 호출한다.
	 */
	public EBBPtcptDTO setAtndcList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육 참여자 출석부 목록을 호출한다.
	 */
	public List<EBBPtcptDTO> selectMemAtndcList(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수를 등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육과정을 수정한다.
	 */
	public int updateEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 삭제한다.
	 */
	public EBBEpisdDTO deleteEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 중복 체크
	 */
	public EBBEpisdDTO selectEpisdChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 강제 종강처리
	 */
	public int updateEpisdEndEdu(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 신청자 정원체크
	 */
	public EBBEpisdDTO selectFxnumChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 신청자 등록
	 */
	public EBBPtcptDTO setPtcptInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 신청자 조회
	 */
	public EBBPtcptDTO selectPtcptDtl(EBBPtcptDTO eBBPtcptDTO) throws Exception;


	/**
	 * 교육차수 신청자 차수변경
	 */
	public int changeEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 신청자 출석부 수정
	 */
	public int updateAtndcList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 설문초기화 한다.
	 */
	public int deleteSurveyRspn(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 상세를 조회한다.(설문유효성)
	 */
	public EBBEpisdSurveyDTO selectEpisdDtlCheck(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 마이페이지 - 온라인 교육강의 참여자 정보 입력
	 */
	public EBBPtcptDTO setOnlinePtcptInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception;


	/**
	 * 마이페이지 출석체크 진행
	 */
	public void updateAtndcInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 마이페이지 - 교육신청을 취소한다.
	 */
	public void updateApplyCancel(EBBPtcptDTO eBBPtcptDTO) throws Exception;


}

