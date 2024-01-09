package com.kap.service.dao.eb;

import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.EBBPtcptDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 교육과정 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBACouseMapper.java
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
public interface EBACouseMapper {
	/**
	 * 교육과정 목록 갯수 조회
	 */
	public int selectCouseListCnt(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정 목록을 조회한다.
	 */
	public List<EBACouseDTO> selectCouseList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정 상세를 조회한다.
	 */
	public EBACouseDTO selectCouseDtl(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정대상 상세를 조회한다.
	 */
	public List<EBACouseDTO> selectCouseTrgtList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정연계 목록을 조회한다.
	 */
	public List<EBACouseDTO> selectEdctnRelList(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 등록한다.
	 */
	public int insertCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정대상을 등록한다.
	 */
	public int insertCouseTrgt(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정대상을 삭제전 차수관리 사용여부 체크
	 */
	public int selectEpisdListChk(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정대상을 삭제한다.
	 */
	public int deleteCouseTrgt(EBACouseDTO eBACouseDTO) throws Exception;
	/**
	 * 교육과정을 수정한다.
	 */
	public int updateCouse(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정을 삭제한다.
	 */
	public int deleteCouseDtl(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정연계상세를 등록한다.
	 */
	public int insertEdctnRel(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 교육과정연계상세를 삭제한다.
	 */
	public int deleteEdctnRel(EBACouseDTO eBACouseDTO) throws Exception;

	/**
	 * 신청한 교육과정을 호출한다.
	 */
	public List<EBBPtcptDTO> selectApplyEpisdList(EBBPtcptDTO ebbPtcptDTO) throws Exception;

	/**
	 * 신청한 교육과정 갯수를 호출한다.
	 */
	public int selectApplyEpisdListCnt(EBBPtcptDTO ebbPtcptDTO) throws Exception;



}