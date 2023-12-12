package com.kap.service.dao.eb;

import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * 방문교육 관리를 위한 DAO
 * </pre>
 *
 * @ClassName		: EBCVisitEduMapper.java
 * @Description		: 방문교육 관리를 위한 DAO
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */
@Mapper
public interface EBCVisitEduMapper {

	/**
	 * 방문교육 목록을 조회한다.
	 */
	public List<EBCVisitEduDTO> selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 개수를 조회
	 */
	public int selectVisitEduCnt(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 상세를 조회
	 */
	public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청분야 상세코드 목록을 조회한다.
	 */
	public List<EBCVisitEduDTO> selectAppctnTypeList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청자 정보 수정
	 */
	public int updateMemPartsSociety(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 강사를 조회
	 */
	public List<EBCVisitEduDTO> selectIsttrList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 결과상세를 등록
	 */
	public int insertEdctnVstRslt(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청자 정보를 수정
	 */
	public int updatePartsMemInfo(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 강사관계 등록
	 */
	public int insertIsttrRel(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 강사관계 삭제
	 */
	public int deleteIsttrRel(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청내용 수정
	 */
	public int updateEdctnVst(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 결과상세 수정
	 */
	public int updateEdctnVstRslt(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청분야 상세 등록
	 */
	public int insertAppctnType(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 신청분야 상세 삭제
	 */
	public int deleteAppctnType(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 교육실적 조회
	 */
	public List<EBCVisitEduDTO> selectResultOpList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 교육실적 등록
	 */
	public int insertResultOp(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;

	/**
	 * 방문교육 교육실적 삭제
	 */
	public int deleteResultOp(EBCVisitEduDTO ebcVisitEduDTO) throws Exception;
}