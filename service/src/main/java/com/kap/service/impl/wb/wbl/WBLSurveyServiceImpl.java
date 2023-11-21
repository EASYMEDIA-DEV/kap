package com.kap.service.impl.wb.wbl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBLSurveyService;
import com.kap.service.dao.wb.wbl.WBLSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 상생협력체감도조사 Service
		 * </pre>
		*
		* @ClassName		: SVASurveyService.java
		* @Description		: 상생협력체감도조사 Service
		* @author 박준희
		* @since 2023.11.20
		* @version 1.0
		* @see
 * @Modification Information
		* <pre>
 * 		since			author					description
		 *   ===========    ==============    =============================
		 *    2023.11.20		박준희					최초생성
		 * </pre>
		*/
@Slf4j
@Service
@RequiredArgsConstructor
public class WBLSurveyServiceImpl implements WBLSurveyService {

	//DAO
	private final WBLSurveyMapper wBLSurveyMapper;

	/* 상생협력체감도조사 시퀀스 */
	private final EgovIdGnrService cxAppctnRsumeSrvIdgen;


	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLSurveyMstSearchDTO.getPageIndex());
		page.setRecordCountPerPage(wBLSurveyMstSearchDTO.getListRowSize());

		page.setPageSize(wBLSurveyMstSearchDTO.getPageRowSize());

		wBLSurveyMstSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLSurveyMstSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLSurveyMstSearchDTO.setList( wBLSurveyMapper.selectSurveyList(wBLSurveyMstSearchDTO) );
		wBLSurveyMstSearchDTO.setTotalCount( wBLSurveyMapper.selectSurveyListCnt(wBLSurveyMstSearchDTO) );
		return wBLSurveyMstSearchDTO;
	}


	@Override
	public int insertSurveyList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception {



		int respCnt = 0;
		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		int surveyMstIdgen = 0;

		wBLSurveyMstInsertDTO.setRegIp(regIp);
		wBLSurveyMstInsertDTO.setRegId(regId);
		wBLSurveyMstInsertDTO.setModIp(regIp);
		wBLSurveyMstInsertDTO.setModId(regId);

		System.out.println("wBLSurveyMstInsertDTO==="  +wBLSurveyMstInsertDTO);

		System.out.println("surveyMstIdgen==="  +surveyMstIdgen);

		//등록
		surveyMstIdgen = cxAppctnRsumeSrvIdgen.getNextIntegerId();
		System.out.println("surveyMstIdgen==="  +surveyMstIdgen);

		wBLSurveyMstInsertDTO.setCxstnSrvSeq( surveyMstIdgen );
		respCnt = wBLSurveyMapper.insertSurveyMst( wBLSurveyMstInsertDTO );
		System.out.println("respCnt==="  +respCnt);


		return respCnt;
	}

	@Override
	public int deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteSurveyMst(wBLSurveyMstSearchDTO);

		return respCnt;

	}

}