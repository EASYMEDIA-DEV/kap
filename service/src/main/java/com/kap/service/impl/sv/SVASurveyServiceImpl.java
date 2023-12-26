package com.kap.service.impl.sv;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.sv.sva.*;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SVASurveyService;
import com.kap.service.dao.sv.SVASurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 설문조사 ServiceImpl
 * </pre>
 *
 * @ClassName		: SVASurveyServiceImpl.java
 * @Description		: 설문조사 ServiceImpl
 * @author 박준희
 * @since 2023.11.06
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2023.11.06		박준희					최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SVASurveyServiceImpl implements SVASurveyService {

	//DAO
	private final SVASurveyMapper sVASurveyMapper;

	/* 설문관리 시퀀스 */
	private final EgovIdGnrService svMstIdgen;

	/* 설문관리 질문 시퀀스 */
	private final EgovIdGnrService svQstnDtlIdgen;

	/* 설문관리보기 시퀀스 */
	private final EgovIdGnrService svQstnExmplDtlIdgen;


	/* 설문응답 시퀀스 */
	private final EgovIdGnrService svRspnSeqIdgen;

	/* 설문객관식응답 시퀀스 */
	private final EgovIdGnrService svMtlccRspnSeqIdgen;

	/* 설문주관식응답 시퀀스 */
	private final EgovIdGnrService svSbjctRspnSeqIdgen;


	/**
     * 공통코드를 조회한다.
     */
    public HashMap<String, List<COCodeDTO>> getSurveyTypeList() throws Exception
    {
    	List<COCodeDTO> codeList = sVASurveyMapper.getSurveyTypeList();
		COCodeDTO COCodeDTOdtl = null;
		HashMap<String, List<COCodeDTO>> rtnMap = new HashMap<String, List<COCodeDTO>>();
    	if (codeList != null && codeList.size() > 0)
    	{
    		for (int i = 0, size = codeList.size(); i < size; i++)
        	{
				COCodeDTOdtl = (COCodeDTO) codeList.get(i);
        		if (rtnMap.get(COCodeDTOdtl.getCdId()) == null)
    			{
					rtnMap.put(COCodeDTOdtl.getCdId(), new ArrayList<COCodeDTO>());
    			}
				rtnMap.get(COCodeDTOdtl.getCdId()).add(COCodeDTOdtl);
    		}
    	}
    	return rtnMap;
    }


	/**
	 *  목록을 조회한다.
	 */
	public SVASurveyMstSearchDTO selectSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(sVASurveyDTO.getPageIndex());
		page.setRecordCountPerPage(sVASurveyDTO.getListRowSize());

		page.setPageSize(sVASurveyDTO.getPageRowSize());

		sVASurveyDTO.setFirstIndex( page.getFirstRecordIndex() );
		sVASurveyDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		sVASurveyDTO.setList( sVASurveyMapper.selectSurveyList(sVASurveyDTO) );
		sVASurveyDTO.setTotalCount( sVASurveyMapper.selectSurveyListCnt(sVASurveyDTO) );
		return sVASurveyDTO;
	}

	@Override
	public int deleteSurveyList(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {
		int respCnt = 0 ;
		int edctnEpisdCnt = sVASurveyMapper.getSurveyEdctnEpisdCnt(sVASurveyDTO);
		int cnstgRsumeCnt = sVASurveyMapper.getSurveyCnstgRsumeCnt(sVASurveyDTO);
		int cmpnEpisdCnt = sVASurveyMapper.getSurveyCmpnEpisdCnt(sVASurveyDTO);

		respCnt = edctnEpisdCnt+cnstgRsumeCnt+cmpnEpisdCnt;
		if(respCnt == 0) {
			//삭제하려는 데이터가 교육회차마스터,컨설팅진행마스터,상생체감도에 매핑되어있지 않음.
			respCnt = sVASurveyMapper.deleteSurveyMst(sVASurveyDTO);
		}else{
			respCnt = -1;
		}

		return respCnt;

	}

	@Override
	public int insertSurveyList(SVASurveyMstInsertDTO sVASurveyMstInsertDTO, HttpServletRequest request) throws Exception {

		int respCnt = 0;
		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		int surveyMstIdgen = 0;

		sVASurveyMstInsertDTO.setRegIp(regIp);
		sVASurveyMstInsertDTO.setRegId(regId);
		sVASurveyMstInsertDTO.setModIp(regIp);
		sVASurveyMstInsertDTO.setModId(regId);

		if(sVASurveyMstInsertDTO.getDetailsKey().trim().equals("")){
			//등록
			surveyMstIdgen = svMstIdgen.getNextIntegerId();
			sVASurveyMstInsertDTO.setSrvSeq( surveyMstIdgen );
			respCnt = sVASurveyMapper.insertSurveyMst( sVASurveyMstInsertDTO );
		}else{
		//수정
			surveyMstIdgen = Integer.parseInt( sVASurveyMstInsertDTO.getDetailsKey() );
			respCnt = sVASurveyMapper.updateSurveyMst( sVASurveyMstInsertDTO );
		}

		sVASurveyMstInsertDTO.setRespCnt( respCnt );

		if(sVASurveyMstInsertDTO.getSvSurveyQstnDtlList() != null && sVASurveyMstInsertDTO.getSvSurveyQstnDtlList().size() > 0){
			//질문 순번 생성
			int parntQstnSeq = 0 ;

			for(int i = 0 ; i < sVASurveyMstInsertDTO.getSvSurveyQstnDtlList().size() ; i++) {
				SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO = sVASurveyMstInsertDTO.getSvSurveyQstnDtlList().get(i);

				int surveyQstnDtlIdgen = svQstnDtlIdgen.getNextIntegerId();

				sVASurveyQstnDtlDTO.setQstnSeq( surveyQstnDtlIdgen );
				sVASurveyQstnDtlDTO.setSrvSeq( surveyMstIdgen );
				if (sVASurveyQstnDtlDTO.getDpth() == 2){
					sVASurveyQstnDtlDTO.setParntQstnSeq(parntQstnSeq);
				}else{
					parntQstnSeq = surveyQstnDtlIdgen;
				}

				sVASurveyQstnDtlDTO.setRegIp(regIp);
				sVASurveyQstnDtlDTO.setRegId(regId);
				sVASurveyQstnDtlDTO.setModIp(regIp);
				sVASurveyQstnDtlDTO.setModId(regId);

				if(sVASurveyQstnDtlDTO.getSvSurveyExmplDtlList() != null && sVASurveyQstnDtlDTO.getSvSurveyExmplDtlList().size() > 0){

					int ord = 0;
					for(int k = 0 ; k < sVASurveyQstnDtlDTO.getSvSurveyExmplDtlList().size() ; k++) {
						SVASurveyExmplDtlDTO sVASurveyExmplDtlDTO = sVASurveyQstnDtlDTO.getSvSurveyExmplDtlList().get(k);
						int surveyExmpDtlIdgen = svQstnExmplDtlIdgen.getNextIntegerId();

						sVASurveyExmplDtlDTO.setExmplSeq( surveyExmpDtlIdgen );
						sVASurveyExmplDtlDTO.setQstnSeq( sVASurveyQstnDtlDTO.getQstnSeq() );
						sVASurveyExmplDtlDTO.setExmplOrd(k+1);
						sVASurveyExmplDtlDTO.setRegIp(regIp);
						sVASurveyExmplDtlDTO.setRegId(regId);
						sVASurveyExmplDtlDTO.setModIp(regIp);
						sVASurveyExmplDtlDTO.setModId(regId);
					}
					sVASurveyMapper.insertSurveyExmplDtl( sVASurveyQstnDtlDTO );
				}
			}
			sVASurveyMapper.insertSurveyQstnDtl( sVASurveyMstInsertDTO );
		}

		return respCnt;
	}

	@Override
	public int updateSurveyList(SVASurveyMstInsertDTO sVASurveyMstInsertDTO, HttpServletRequest request) throws Exception {

		SVASurveyMstSearchDTO sVASurveyMstSearchDTO = new SVASurveyMstSearchDTO();
		sVASurveyMstSearchDTO.setDetailsKey( sVASurveyMstInsertDTO.getDetailsKey() );
		int edctnEpisdCnt = sVASurveyMapper.getSurveyEdctnEpisdCnt(sVASurveyMstSearchDTO);
		int cnstgRsumeCnt = sVASurveyMapper.getSurveyCnstgRsumeCnt(sVASurveyMstSearchDTO);
		int cmpnEpisdCnt = sVASurveyMapper.getSurveyCmpnEpisdCnt(sVASurveyMstSearchDTO);

		int respCnt = edctnEpisdCnt+cnstgRsumeCnt+cmpnEpisdCnt ;

		if(respCnt > 0)
		{
			//사용 여부만 수정
			respCnt = sVASurveyMapper.updateSurveyMstExpnYn( sVASurveyMstInsertDTO );
		}
		else
		{
			//질문 보기삭제
			sVASurveyMapper.deleteSurveyQstnList(sVASurveyMstSearchDTO);
			insertSurveyList(sVASurveyMstInsertDTO, request);
		}
		return respCnt;
	}

	@Override
	public SVASurveyMstInsertDTO selectSurveyDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {

		SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVASurveyMapper.selectSurveyDtl(sVASurveyDTO);
		List<SVASurveyQstnDtlDTO> surveyQstnDtlList = sVASurveyMapper.selectSurveyQstnDtlList(sVASurveyDTO);
		SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO = new SVASurveyQstnDtlDTO();

		if(surveyQstnDtlList != null && surveyQstnDtlList.size() > 0) {
			for (int i = 0; i < surveyQstnDtlList.size(); i++) {
				sVASurveyQstnDtlDTO.setQstnSeq(surveyQstnDtlList.get(i).getQstnSeq());
				List<SVASurveyExmplDtlDTO> surveyExmplDtlList = sVASurveyMapper.selectSurveyExmplDtlList(sVASurveyQstnDtlDTO);
				surveyQstnDtlList.get(i).setSvSurveyExmplDtlList(surveyExmplDtlList);
			}
		}

		sVASurveyMstInsertDTO.setSvSurveyQstnDtlList( surveyQstnDtlList );

		//컨설팅 , 시험  ,체감도 매핑 여부
		int edctnEpisdCnt = sVASurveyMapper.getSurveyEdctnEpisdCnt(sVASurveyDTO);
		int cnstgRsumeCnt = sVASurveyMapper.getSurveyCnstgRsumeCnt(sVASurveyDTO);
		int cmpnEpisdCnt = sVASurveyMapper.getSurveyCmpnEpisdCnt(sVASurveyDTO);


		sVASurveyMstInsertDTO.setPosbChg( (edctnEpisdCnt+cnstgRsumeCnt+cmpnEpisdCnt) > 0 ? false : true );

		return sVASurveyMstInsertDTO;
	}

	@Override
	public SVASurveyMstInsertDTO selectSurveyTypeWinDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {

		SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVASurveyMapper.selectSurveyDtl(sVASurveyDTO);

		if (sVASurveyMstInsertDTO != null){
			List<SVASurveyQstnDtlDTO> surveyQstnDtlList = sVASurveyMapper.selectSurveyQstnTypeDtlList(sVASurveyDTO);
			SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO = new SVASurveyQstnDtlDTO();

			if(surveyQstnDtlList != null && surveyQstnDtlList.size() > 0) {
				for (int i = 0; i < surveyQstnDtlList.size(); i++) {
					sVASurveyQstnDtlDTO.setSrvRspnSeq(sVASurveyDTO.getSrvRspnSeq());
					sVASurveyQstnDtlDTO.setQstnSeq(surveyQstnDtlList.get(i).getQstnSeq());
					List<SVASurveyExmplDtlDTO> surveyExmplDtlList = sVASurveyMapper.selectSurveyExmplWinDtlList(sVASurveyQstnDtlDTO);
					surveyQstnDtlList.get(i).setSvSurveyExmplDtlList(surveyExmplDtlList);
				}
			}

			sVASurveyMstInsertDTO.setSvSurveyQstnDtlList( surveyQstnDtlList );
		}

		return sVASurveyMstInsertDTO;
	}



	@Override
	public SVASurveyMstInsertDTO selectSurveyTypeEduDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {

		SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVASurveyMapper.selectSurveyDtl(sVASurveyDTO);

		if (sVASurveyMstInsertDTO != null){
			List<SVASurveyQstnDtlDTO> surveyQstnDtlList = sVASurveyMapper.selectSurveyQstnTypeDtlList(sVASurveyDTO);
			SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO = new SVASurveyQstnDtlDTO();

			if(surveyQstnDtlList != null && surveyQstnDtlList.size() > 0) {
				for (int i = 0; i < surveyQstnDtlList.size(); i++) {
					sVASurveyQstnDtlDTO.setRfncSeq(sVASurveyDTO.getRfncSeq());
					sVASurveyQstnDtlDTO.setQstnSeq(surveyQstnDtlList.get(i).getQstnSeq());
					List<SVASurveyExmplDtlDTO> surveyExmplDtlList = sVASurveyMapper.selectSurveyExmplEduDtlList(sVASurveyQstnDtlDTO);
					surveyQstnDtlList.get(i).setSvSurveyExmplDtlList(surveyExmplDtlList);
				}
			}


			List<SVASurveyQstnDtlDTO> surveyIsttrQstnDtlList = sVASurveyMapper.selectSurveyIsttrQstnTypeDtlList(sVASurveyDTO);

			if(surveyIsttrQstnDtlList != null && surveyIsttrQstnDtlList.size() > 0) {
				for (int i = 0; i < surveyIsttrQstnDtlList.size(); i++) {
					sVASurveyQstnDtlDTO.setIsttrSeq(surveyIsttrQstnDtlList.get(i).getIsttrSeq());
					sVASurveyQstnDtlDTO.setQstnSeq(surveyIsttrQstnDtlList.get(i).getQstnSeq());
					List<SVASurveyExmplDtlDTO> surveyExmplDtlList = sVASurveyMapper.selectSurveyIsttrExmplEduDtlList(sVASurveyQstnDtlDTO);
					surveyIsttrQstnDtlList.get(i).setSvSurveyExmplDtlList(surveyExmplDtlList);
				}
				surveyQstnDtlList.addAll(surveyIsttrQstnDtlList);
			}

			sVASurveyMstInsertDTO.setSvSurveyQstnDtlList( surveyQstnDtlList );
		}

		return sVASurveyMstInsertDTO;
	}

	@Override
	public SVASurveyMstInsertDTO selectSurveyTypeConDtl(SVASurveyMstSearchDTO sVASurveyDTO) throws Exception {

		SVASurveyMstInsertDTO sVASurveyMstInsertDTO = sVASurveyMapper.selectSurveyConDtl(sVASurveyDTO);

		String detailKey = sVASurveyDTO.getDetailsKey();

		if (sVASurveyMstInsertDTO != null){
			sVASurveyDTO.setDetailsKey(Integer.toString(sVASurveyMstInsertDTO.getSrvSeq()));
			List<SVASurveyQstnDtlDTO> surveyQstnDtlList = sVASurveyMapper.selectSurveyQstnTypeDtlList(sVASurveyDTO);
			SVASurveyQstnDtlDTO sVASurveyQstnDtlDTO = new SVASurveyQstnDtlDTO();

			if(surveyQstnDtlList != null && surveyQstnDtlList.size() > 0) {
				for (int i = 0; i < surveyQstnDtlList.size(); i++) {
					sVASurveyQstnDtlDTO.setRfncSeq(Integer.valueOf(detailKey));
					sVASurveyQstnDtlDTO.setQstnSeq(surveyQstnDtlList.get(i).getQstnSeq());
					List<SVASurveyExmplDtlDTO> surveyExmplDtlList = sVASurveyMapper.selectSurveyExmplConDtlList(sVASurveyQstnDtlDTO);
					surveyQstnDtlList.get(i).setSvSurveyExmplDtlList(surveyExmplDtlList);
				}
			}

			sVASurveyMstInsertDTO.setSvSurveyQstnDtlList( surveyQstnDtlList );
		}

		return sVASurveyMstInsertDTO;
	}


	@Override
	public int insertSurveyRspnList(SVASurveyRspnMstInsertDTO sVASurveyRspnMstDTO, HttpServletRequest request) throws Exception {

		int respCnt = 0;

		String regId = "";
		String regIp = CONetworkUtil.getMyIPaddress(request);

		if (COUserDetailsHelperService.getAuthenticatedUser() != null){
			regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		}else{
			regId = sVASurveyRspnMstDTO.getRegId();
		}

		int surveyRspnMstIdgen = 0;

		sVASurveyRspnMstDTO.setRegIp(regIp);
		sVASurveyRspnMstDTO.setRegId(regId);
		sVASurveyRspnMstDTO.setModIp(regIp);
		sVASurveyRspnMstDTO.setModId(regId);

		//등록
		surveyRspnMstIdgen = svRspnSeqIdgen.getNextIntegerId();
		sVASurveyRspnMstDTO.setSrvRspnSeq(surveyRspnMstIdgen);

		sVASurveyMapper.insertSurveyRspnMst( sVASurveyRspnMstDTO );

		respCnt = surveyRspnMstIdgen;

		if(sVASurveyRspnMstDTO.getSvSurveyQstnRspnDtlList() != null && sVASurveyRspnMstDTO.getSvSurveyQstnRspnDtlList().size() > 0){

			for(int i = 0 ; i < sVASurveyRspnMstDTO.getSvSurveyQstnRspnDtlList().size() ; i++) {

				SVASurveyQstnRspnDtlDTO sVASurveyQstnRspnDtlDTO = sVASurveyRspnMstDTO.getSvSurveyQstnRspnDtlList().get(i);

				int qstnSeq = sVASurveyQstnRspnDtlDTO.getQstnSeq();

				if(sVASurveyQstnRspnDtlDTO.getSvSurveyExmplRspnDtlList() != null && sVASurveyQstnRspnDtlDTO.getSvSurveyExmplRspnDtlList().size() > 0){

					for(int k = 0 ; k < sVASurveyQstnRspnDtlDTO.getSvSurveyExmplRspnDtlList().size() ; k++) {

						SVASurveyExmplRspnDtlDTO sVASurveyExmplRspnDtlDTO = sVASurveyQstnRspnDtlDTO.getSvSurveyExmplRspnDtlList().get(k);

						if (sVASurveyQstnRspnDtlDTO.getSrvTypeCd().equals("QST03")||sVASurveyQstnRspnDtlDTO.getSrvTypeCd().equals("QST04")){
							int surveySbjctRspnIdgen = svSbjctRspnSeqIdgen.getNextIntegerId();

							sVASurveyExmplRspnDtlDTO.setSbjctRspnSeq(surveySbjctRspnIdgen);
							sVASurveyExmplRspnDtlDTO.setSrvRspnSeq(surveyRspnMstIdgen);
							sVASurveyExmplRspnDtlDTO.setQstnSeq(qstnSeq);
							sVASurveyExmplRspnDtlDTO.setRegIp(regIp);
							sVASurveyExmplRspnDtlDTO.setRegId(regId);
							sVASurveyMapper.insertSurveyRspnSbjctDtl( sVASurveyExmplRspnDtlDTO );

						}else{
							int surveyMtlccRspnIdgen = svMtlccRspnSeqIdgen.getNextIntegerId();

							sVASurveyExmplRspnDtlDTO.setMtlccRspnSeq(surveyMtlccRspnIdgen);
							sVASurveyExmplRspnDtlDTO.setSrvRspnSeq(surveyRspnMstIdgen);
							sVASurveyExmplRspnDtlDTO.setRegIp(regIp);
							sVASurveyExmplRspnDtlDTO.setRegId(regId);
							sVASurveyMapper.insertSurveyRspnMtlccDtl( sVASurveyExmplRspnDtlDTO );

						}
					}
				}
			}
		}
		return respCnt;
	}

	@Override
	public int selectSurveyScore(SVASurveyRspnScoreDTO sVASurveyRspnScoreDTO) throws Exception {
		int respCnt = 0;
		respCnt = sVASurveyMapper.selectSurveyScore( sVASurveyRspnScoreDTO );

		return respCnt;
	}
}