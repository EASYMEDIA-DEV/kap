package com.kap.service.impl.wb.wbl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBLSurveyService;
import com.kap.service.dao.sv.SVASurveyMapper;
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


	/* 회차관리 시퀀스 */
	private final EgovIdGnrService cxCmpnEpisdMstIdgen;


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
		int surveyMstIdgen = 0;

		String crtfnNo = "";
		String episdText ="";
		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLSurveyMstInsertDTO.setRegIp(regIp);
		wBLSurveyMstInsertDTO.setRegId(regId);
		wBLSurveyMstInsertDTO.setModIp(regIp);
		wBLSurveyMstInsertDTO.setModId(regId);

		//등록
		surveyMstIdgen = cxAppctnRsumeSrvIdgen.getNextIntegerId();

		wBLSurveyMstInsertDTO.setCxstnSrvSeq( surveyMstIdgen );

		if (wBLSurveyMstInsertDTO.getEpisd() < 10){
			episdText = "0"+Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}else{
			episdText = Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}
		crtfnNo = wBLSurveyMstInsertDTO.getYear()+episdText+wBLSurveyMstInsertDTO.getBsnmRegNo();

		wBLSurveyMstInsertDTO.setCrtfnNo(crtfnNo);
		wBLSurveyMstInsertDTO.setPtcptCd("E");
		wBLSurveyMstInsertDTO.setCmpltnYn("N");

		respCnt = wBLSurveyMapper.insertSurveyMst( wBLSurveyMstInsertDTO );

		return respCnt;
	}

	@Override
	public int deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteSurveyMst(wBLSurveyMstSearchDTO);

		return respCnt;

	}

	@Override
	public WBLSurveyMstInsertDTO selectSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception {
		WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO = wBLSurveyMapper.selectSurveyDtl(wBLSurveyMstSearchDT);

		return wBLSurveyMstInsertDTO;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLEpisdMstDTO.getPageIndex());
		page.setRecordCountPerPage(wBLEpisdMstDTO.getListRowSize());

		page.setPageSize(wBLEpisdMstDTO.getPageRowSize());

		wBLEpisdMstDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLEpisdMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdList(wBLEpisdMstDTO) );
		wBLEpisdMstDTO.setTotalCount( wBLSurveyMapper.selectEpisdListCnt(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}

	@Override
	public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteEpisdList(wBLEpisdMstDTO);

		return respCnt;

	}


	@Override
	public int insertEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;
		int episdMstIdgen = 0;

		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLEpisdMstDTO.setRegIp(regIp);
		wBLEpisdMstDTO.setRegId(regId);
		wBLEpisdMstDTO.setModIp(regIp);
		wBLEpisdMstDTO.setModId(regId);

		WBLEpisdMstDTO wBLEpisdMstCheck = wBLSurveyMapper.selectEpisdMst( wBLEpisdMstDTO );

		if (wBLEpisdMstCheck!=null){
			//수정
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( wBLEpisdMstCheck.getCxstnCmpnEpisdSeq() );
			respCnt = wBLSurveyMapper.updateEpisdMst( wBLEpisdMstDTO );
		}else{
			//등록
			episdMstIdgen = cxCmpnEpisdMstIdgen.getNextIntegerId();
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( episdMstIdgen );
			respCnt = wBLSurveyMapper.insertEpisdMst( wBLEpisdMstDTO );
		}

		return respCnt;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdSurveyList(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}

}