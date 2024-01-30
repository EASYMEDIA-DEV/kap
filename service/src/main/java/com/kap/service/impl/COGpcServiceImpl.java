package com.kap.service.impl;

import com.kap.core.dto.co.COGpcEdctnDTO;
import com.kap.core.dto.co.COGpcEpisdDTO;
import com.kap.core.dto.co.COGpcPtcptDTO;
import com.kap.service.COGpcService;
import com.kap.service.COSystemLogService;
import com.kap.service.dao.COGpcReceiveRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * GPC API 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COGpcServiceImpl.java
 * @Description		: GPC API 관리 ServiceImpl
 * @author 김학규
 * @since 2024.01.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.30		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COGpcServiceImpl implements COGpcService {

	//DAO
	private final COGpcReceiveRestMapper cOGpcReceiveRestMapper;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;

	//사용자 http 경로
	@Value("${app.user-domain}")
	private String appUserDomain;


	/**
	 * API번호 GPCKAP001 KAP등록된 교육 과정 정보
	 */
	public COGpcEdctnDTO selectGpcEdctnList(COGpcEdctnDTO cOGpcEdctnDTO) throws Exception {

		COGpcEdctnDTO rtnDto = new COGpcEdctnDTO();

		List<COGpcEdctnDTO> rtnList = cOGpcReceiveRestMapper.selectGpcEdctnList(cOGpcEdctnDTO);

		for(COGpcEdctnDTO dto : rtnList){
			dto.setKapUrl(appUserDomain+"/education/apply/detail?detailsKey="+dto.getKapSeq());
		}

		rtnDto.setList(rtnList);

		return rtnDto;
	}

	/**
	 * API번호 GPCKAP002 KAP 등록 된 교육 과정의 상세 차수 정보
	 */
	public COGpcEpisdDTO selectGpcEpisdList(COGpcEpisdDTO cOGpcEpisdDTO) throws Exception {

		COGpcEpisdDTO rtnDto = new COGpcEpisdDTO();

		List<COGpcEpisdDTO> rtnList = cOGpcReceiveRestMapper.selectGpcEpisdList(cOGpcEpisdDTO);


		rtnDto.setList(rtnList);

		return rtnDto;
	}

	/**
	 * API번호 GPCKAP003 KAP 교육 신청자 정보
	 */
	public COGpcPtcptDTO selectGpcPtcptList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception {

		COGpcPtcptDTO rtnDto = new COGpcPtcptDTO();

		List<COGpcPtcptDTO> rtnList = cOGpcReceiveRestMapper.selectGpcPtcptList(cOGpcPtcptDTO);


		rtnDto.setList(rtnList);

		return rtnDto;
	}

	/**
	 * API번호 GPCKAP003 KAP 교육 신청자 정보
	 */
	public COGpcPtcptDTO selectGpcPtcptExamList(COGpcPtcptDTO cOGpcPtcptDTO) throws Exception {

		COGpcPtcptDTO rtnDto = new COGpcPtcptDTO();

		List<COGpcPtcptDTO> rtnList = cOGpcReceiveRestMapper.selectGpcPtcptExamList(cOGpcPtcptDTO);


		rtnDto.setList(rtnList);

		return rtnDto;
	}


}