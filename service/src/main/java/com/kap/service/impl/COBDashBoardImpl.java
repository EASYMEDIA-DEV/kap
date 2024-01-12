package com.kap.service.impl;

import com.kap.core.dto.COBDashBoardDTO;
import com.kap.service.COBDashBoardService;
import com.kap.service.dao.COBDashBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * 대쉬보드 ServiceImpl
 * </pre>
 *
 * @ClassName		: COECodeServiceImpl.java
 * @Description		: 대쉬보드 ServiceImpl
 * @author 박준희
 * @since 2024.01.12
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2024.01.12		박준희					최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COBDashBoardImpl implements COBDashBoardService {

	//DAO
	private final COBDashBoardMapper cOBDashBoardMapper;

	@Override
	public COBDashBoardDTO selectDashboard(COBDashBoardDTO cOBDashBoardDTO) throws Exception {

		COBDashBoardDTO eduResult = cOBDashBoardMapper.getEduCnt();
		COBDashBoardDTO conResult = cOBDashBoardMapper.getConCnt();
		List<COBDashBoardDTO> noticeResult = cOBDashBoardMapper.getNoticeList();
		COBDashBoardDTO inquiryResult = cOBDashBoardMapper.getInquiryCnt();
		COBDashBoardDTO memResult = cOBDashBoardMapper.getMemberCnt();

		cOBDashBoardDTO.setEduAccepting(eduResult.getEduAccepting());
		cOBDashBoardDTO.setEduAcceptWaiting(eduResult.getEduAcceptWaiting());
		cOBDashBoardDTO.setEduAcceptTraining(eduResult.getEduAcceptTraining());

		cOBDashBoardDTO.setConTechApplication(conResult.getConTechApplication());
		cOBDashBoardDTO.setConTechTraining(conResult.getConTechTraining());
		cOBDashBoardDTO.setConMngApplication(conResult.getConMngApplication());
		cOBDashBoardDTO.setConMngTraining(conResult.getConMngTraining());

		cOBDashBoardDTO.setNoticeList(noticeResult);

		cOBDashBoardDTO.setInquiryApplicationWaiting(inquiryResult.getInquiryApplicationWaiting());
		cOBDashBoardDTO.setInquiryApplicationCompleted(inquiryResult.getInquiryApplicationCompleted());

		cOBDashBoardDTO.setGeneralMemJoin(memResult.getGeneralMemJoin());
		cOBDashBoardDTO.setGeneralMemSecession(memResult.getGeneralMemSecession());
		cOBDashBoardDTO.setCompanyMemJoin(memResult.getCompanyMemJoin());
		cOBDashBoardDTO.setCompanyMemSecession(memResult.getCompanyMemSecession());

		return cOBDashBoardDTO;
	}
}