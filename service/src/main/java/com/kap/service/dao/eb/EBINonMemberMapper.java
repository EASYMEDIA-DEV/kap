package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebb.EBBBdgetDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import com.kap.core.dto.eb.ebi.EBINonMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 비회원 교육 과정 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBINonMemberMapper.java
 * @Description		: 비회원 교육 과정 관리를 위한 DAO
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
@Mapper
public interface EBINonMemberMapper {

	/**
	 * 비회원 교육 과정 목록 조회
	 */
	public List<EBINonMemberDTO> selectNonMemberList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 엑셀 목록 조회
	 */
	public List<EBINonMemberDTO> selectNonMemberExcelList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 목록 개수 조회
	 */
	public int selectNonMemberListCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 상세 조회
	 */
	public EBINonMemberDTO selectNonMemberDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 대상 상세 조회
	 */
	public List<EBINonMemberDTO> selectNonMemberTrgtList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 등록
	 */
	public int insertNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 등록 대상 등록
	 */
	public int insertNonMemberTrgt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 수정
	 */
	public int updateNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 강사관계 호출
	 */
	public List<EBBisttrDTO> selectIsttrList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 강사관계 삭제
	 */
	public int deleteIsttrRel(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 강사관계 등록
	 */
	public int insertIsttrRel(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 삭제
	 */
	public int deleteNonMemberDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 대상 삭제
	 */
	public int deleteNonMemberTrgt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 중복체크
	 */
	public EBINonMemberDTO selectNonMemberChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 교육 참여자 목록 카운트
	 */
	public int selectNonMemberPtcptListCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 교육 참여자 목록
	 */
	public List<EBINonMemberDTO> selectNonMemberPtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 정원 체크
	 */
	public EBINonMemberDTO selectFxnumChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 교육 예산 지출 상세 삭제
	 */
	public int deleteBdgetList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 교육 예산 지출 상세 등록
	 */
	public int insertBdgetList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 - 교육 예산 지출 상세 목록 조회
	 */
	public List<EBBBdgetDTO> selectBdgetDtlList(EBBBdgetDTO eBBBdgetDTO) throws Exception;

	/**
	 * 교육차수 - 신청자 등록 전 확인
	 */
	public EBINonMemberDTO selectPtcptDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 교육차수 - 신청자 등록
	 */
	public int insertPtcptDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 교육차수 삭제 전 참여자 체크
	 */
	public int selectNonMemberDeletePrevChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 목록 신청 취소
	 */
	public int updatePtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 비회원 교육 과정 신청자 신청 취소
	 */
	public int updatePtcpt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;





	/**
	 * 사용자 - 비회원 교육 과정 신청 개수 조회
	 */
	public int searchPtcptCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 사용자 - 비회원 교육 과정 신청 목록 조회
	 */
	public List<EBINonMemberDTO> selectNonMemberApplyList(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

	/**
	 * 사용자 - 비회원 교육 신청 페이지 정보 상세
	 */
	public EBINonMemberDTO selectNonMemberApplyPtcptDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception;

}