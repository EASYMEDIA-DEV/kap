package com.kap.service;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.co.COGpcEdctnDTO;
import com.kap.core.dto.co.COGpcEpisdDTO;
import com.kap.core.dto.co.COGpcPtcptDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 * GPC 연동을 위한 Service
 * </pre>
 *
 * @ClassName		: COGpcService.java
 * @Description		: GPC 연동을 위한 Service
 * @author 김학규
 * @since 2023.01.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.01.30		김학규				   최초 생성
 * </pre>
 */
public interface COGpcService {

	/**
	 * API번호 GPCKAP001 KAP등록된 교육 과정 정보
	 */
	public COGpcEdctnDTO selectGpcEdctnList(COGpcEdctnDTO cOGpcEdctnDTO) throws Exception;

	/**
	 * API번호 GPCKAP002 KAP 등록 된 교육 과정의 상세 차수 정보
	 */
	public COGpcEpisdDTO selectGpcEpisdList(COGpcEpisdDTO cOGpcEpisdDTO) throws Exception;

	/**
	 * API번호 GPCKAP003 KAP 교육 신청자 정보
	 */
	public COGpcPtcptDTO selectGpcPtcptList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception;

	/**
	 * API번호 GPCKAP003 KAP 교육 신청자 정보
	 */
	public COGpcPtcptDTO selectGpcPtcptExamList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception;

}

