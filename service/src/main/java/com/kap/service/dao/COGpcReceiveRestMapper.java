package com.kap.service.dao;

import com.kap.core.dto.co.COGpcEdctnDTO;
import com.kap.core.dto.co.COGpcEpisdDTO;
import com.kap.core.dto.co.COGpcPtcptDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <pre>
 * GPC API 호출 Mapper
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
 *    2024.01.30		김학규				   최초 생성
 * </pre>
 */
@Mapper
public interface COGpcReceiveRestMapper {

	/**
	 *  API번호 GPCKAP001 KAP등록된 교육 과정 정보
	 */
	public List<COGpcEdctnDTO> selectGpcEdctnList(COGpcEdctnDTO cOGpcEdctnDTO) throws Exception;

	/**
	 *  API번호 GPCKAP002 KAP 등록 된 교육 과정의 상세 차수 정보
	 */
	public List<COGpcEpisdDTO> selectGpcEpisdList(COGpcEpisdDTO cOGpcEpisdDTO) throws Exception;

	/**
	 *  API번호 GPCKAP003 KAP KAP 교육 신청자 정보
	 */
	public List<COGpcPtcptDTO> selectGpcPtcptList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception;

	/**
	 *  API번호 GPCKAP004 KAP 교육 신청자 교육 완료 평가 정보
	 */
	public List<COGpcPtcptDTO> selectGpcPtcptExamList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception;

}