package com.kap.service.dao.eb;

import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육차수 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBBFrontEpisdMapper.java
 * @Description		: 교육과정을 위한 DAO
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
public interface EBBFrontEpisdMapper {

	/**
	 * 교육신청 목록(사용자)
	 */
	public List<EBBEpisdDTO> selectFrontCouseList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 교육신청 목록(사용자) 목록 갯수 조회
	 */
	public int selectFrontCouseListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 교육과정 대상 목록
	 */
	public List<EBACouseDTO> selectCouseTrgtList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육차수 목록을 조회한다. (사용자 전체교육일정 레이어)
	 */
	public List<EBBEpisdDTO> selectEpisdLayerList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 마이페이지- 최근1년간 신청내역 호출(교육사업)
	 */
	public int selectMypageEduCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 마이페이지 - 교육/세미나 사입 신청내역
	 */
	public List<EBBEpisdDTO> selectMypageEduList(EBBEpisdDTO eBBEpisdDTO) throws Exception;

	/**
	 * 마이페이지 - 교육/세미나 사입 신청내역 카운트
	 */
	public List<EBBEpisdDTO> selectMypageEduListCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception;


	/**
	 * 마이페이지 - 교육양도시 회원목록을 호출한다.(카운트)
	 */
	public int selectApplyUserListCnt(MPAUserDto mpaUserDto) throws Exception;

	/**
	 * 마이페이지 - 교육양도시 회원목록을 호출한다.
	 */
	public List<MPAUserDto> selectApplyUserList(MPAUserDto mpaUserDto) throws Exception;


}