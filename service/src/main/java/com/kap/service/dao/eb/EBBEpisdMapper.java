package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebb.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육차수 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBBEpisdMapper.java
 * @Description		: 교육과정 관리를 위한 DAO
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Mapper
public interface EBBEpisdMapper {

	/**
	 * 교육차수 목록을 조회한다.
	 */
	public List<EBBEpisdDTO> selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 목록 갯수 조회
	 */
	public int selectEpisdListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육신청 목록(사용자)
	 */
	public List<EBBEpisdDTO> selectFrontEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육신청 목록(사용자) 목록 갯수 조회
	 */
	public int selectFrontEpisdListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 목록을 조회한다.(엑셀용)
	 */
	public List<EBBEpisdExcelDTO> selectEpisdExcelList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 목록 갯수 조회(엑셀용)
	 */
	public int selectEpisdExcelListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public EBBEpisdDTO selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육 차수별 만족도 결과 상세
	 */
	public int insertEpisdSrvRsltDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 수정한다.
	 */
	public int updateEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 - 강사관계를 호출한다
	 */
	public List<EBBisttrDTO> selectIsttrList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 강사관계를 삭제한다.
	 */
	public int deleteIsttrRel(EBBEpisdDTO eBBEpisdDTO) throws Exception;
	/**
	 * 교육차수 - 강사관계를 등록한다
	 */
	public int insertIsttrRel(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 온라인강의 목록을 호출한다.
	 */
	public List<EBBLctrDTO> selectLctrDtlList(EBBLctrDTO eBBLctrDTO) throws Exception;

	/**
	 * 교육차수 - 온라인강의 목록 갯수 조회
	 */
	public int selectLctrDtlListCnt(EBBLctrDTO eBBLctrDTO) throws Exception;

	/**
	 * 교육차수 - 교육강의 상세를 등록한다.
	 */
	public int insertLctrDtl(EBBLctrDTO eBBLctrDTO) throws Exception;

	/**
	 * 교육차수 - 교육강의 상세를 삭제한다.
	 */
	public int deleteLctrDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 삭제 전 참여자 체크
	 */
	public int selectEpisdDeletePrevChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수를 삭제한다.
	 */
	public int deleteEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 중복체크
	 */
	public EBBEpisdDTO selectEpisdChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 참여자 교육취소처리 - 차수내 전체인원
	 */
	public int updatePtcptStatus(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 참여자 교육취소처리 차수변경을 통한 변경 - 개인
	 */
	public int updatePtcptStatusInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception;



	/**
	 * 교육차수 차수 종강(폐강처리)
	 */
	public int updateEpisdStatus(EBBEpisdDTO eBBEpisdDTO) throws Exception;




	/**
	 * 교육차수 - 교육 참여자 목록 카운트
	 */
	public int selectEpisdPtcptListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육 참여자 목록
	 */
	public List<EBBPtcptDTO> selectEpisdPtcptList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육 참여자 출석정보 목록
	 */
	public List<EBBPtcptDTO> selectPtcptAtndcList(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 - 교육 참여자 개인별 출석정보
	 */
	public List<EBBPtcptDTO> selectMemAtndcDtl(EBBPtcptDTO eBBPtcptDTO) throws Exception;


	/**
	 * 교육차수 - 교육 참여자 수정(평가점수, 수료여부)
	 */
	public int updateEpisdPtcpt(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 신청자 정원체크
	 */
	public EBBEpisdDTO selectFxnumChk(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육예산지출상세 삭제
	 */
	public int deleteBdgetList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육예산지출상세 등록
	 */
	public int insertBdgetList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 교육예산지출상세목록 조회
	 */
	public List<EBBBdgetDTO> selectBdgetDtlList(EBBBdgetDTO eBBBdgetDTO) throws Exception;

	/**
	 * 교육차수관리 - 만족도 결과 평균 점수 호출
	 */
	public EBBSrvRstDTO selectEpisdSrvScoreDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 만족도 조사결과 통계
	 */
	public EBBSrvRstDTO selectEpisdSrvRstDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 - 신청자 등록 전 확인
	 */
	public EBBPtcptDTO selectPtcptDtl(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 - 신청자 등록
	 */
	public int insertPtcptDtl(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 - 신청자  출석 목록 등록
	 */
	public int insertAtndcList(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 - 신청자  출석 목록 수정
	 */
	public int updateAtndcList(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육차수 - 설문초기화
	 */
	public int deleteSurveyRspn(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.(설문유효성)
	 */
	public EBBEpisdSurveyDTO selectEpisdDtlCheck(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.(설문유효성)
	 */
	public int updateApplyCancel(EBBPtcptDTO eBBPtcptDTO) throws Exception;

	/**
	 * 교육차수 상세를 조회한다.(설문유효성)
	 */
	public int setOnlinePtcptInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception;




}