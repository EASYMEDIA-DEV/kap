package com.kap.service.impl;

import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpb.MPBBsnSearchDTO;
import com.kap.core.dto.sm.smb.SMBMainVslDTO;
import com.kap.core.dto.sm.smc.SMCMnPopDTO;
import com.kap.core.dto.sm.smg.SMGWinBusinessDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <pre>
 * 컨텐츠 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COGCntsServiceImpl.java
 * @Description		: 컨텐츠 관리를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.09.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ====                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ======    ==============    =============================
 *    2023.09.07		임서화				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COMainServiceImpl implements COMainService {


	/** 공통 서비스 **/
	private final COCommService cOCommService;

	/** 공통 서비스 **/
	private final SMJFormService sMJFormService;


	/** 메인 비주얼 서비스 **/
	private final SMBMnVslService sMBMnVslService;

	/** 메인 팝업 서비스 **/
	private final SMCMnPopService sMCMnPopService;

	/** 상생사업 관리 서비스 **/
	private final SMGWinBusinessService sMGWinBusinessService;

	/** 공지사항 서비스 **/
	private final BDANoticeService bDANoticeService;

	/** FAQ 서비스 **/
	private final BDCFaqService bDCFaqService;

	/** 재단소식 서비스 **/
	private final BDBCompanyNewsService bDBCompanyNewsService;
	private final EBBEpisdService eBBEpisdService;
	private CBATechGuidanceService cbaTechGuidanceService;

	private MPBCoexistenceService mpbCoexistenceService;

	@Transactional
	public HashMap<String, Object> selectMainGroup(boolean isNomal
			, SMBMainVslDTO sMBMainVslDTO
			, SMCMnPopDTO sMCMnPopDTO
			, SMGWinBusinessDTO sMGWinBusinessDTO
			, BDANoticeDTO bDANoticeDTO
			, BDCFaqDTO bDCFaqDTO
			, BDBCompanyNewsDTO bDBCompanyNewsDTO) throws Exception {

		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		try{
			//상생사업 관리 리스트
			rtnMap.put("winData", sMGWinBusinessService.selectWinBusinessList(sMGWinBusinessDTO));
			//공지사항 리스트
			bDANoticeDTO.setMainYn("Y");
			rtnMap.put("noticeData", bDANoticeService.selectNoticeList(bDANoticeDTO));
			//FAQ 리스트
			bDCFaqDTO.setMainYn("Y");
			rtnMap.put("faqData", bDCFaqService.selectFaqList(bDCFaqDTO));
			//메인여부
			rtnMap.put("mainYn", "Y");
			//재단소식 메인여부
			bDBCompanyNewsDTO.setMainYn("Y");

			if(isNomal){
				sMBMainVslDTO.setMdCd("pc");
				sMCMnPopDTO.setMdCd("pc");
				bDBCompanyNewsDTO.setDeviceGubun("pc");
				//메인 웹 비주얼 리스트
				rtnMap.put("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
				//메인 웹 팝업 리스트
				rtnMap.put("popData", sMCMnPopService.selectMnPopList(sMCMnPopDTO));
				//재단소식 웹 리스트
				rtnMap.put("companyData", bDBCompanyNewsService.selectCompanyNewsList(bDBCompanyNewsDTO));
			}else{
				sMBMainVslDTO.setMdCd("mobile");
				sMCMnPopDTO.setMdCd("mobile");
				bDBCompanyNewsDTO.setDeviceGubun("mobile");
				//메인 모바일 비주얼 리스트
				rtnMap.put("rtnData", sMBMnVslService.selectMnVslList(sMBMainVslDTO));
				//메인 모바일 팝업 리스트
				rtnMap.put("popData", sMCMnPopService.selectMnPopList(sMCMnPopDTO));
				//재단소식 모바일 리스트
				rtnMap.put("companyData", bDBCompanyNewsService.selectCompanyNewsList(bDBCompanyNewsDTO));
			}

		}catch (Exception e){
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}

		return rtnMap;
	}

	@Transactional
	public HashMap<String, Object> selectMainGroup2(boolean selectYn, SMJFormDTO smjFormDTO, SMKTrendDTO sMKTrendDTO, EBBEpisdDTO ebbEpisdDTO, CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, MPBBsnSearchDTO mpbBnsSearchDTO) throws Exception {

		HashMap<String, Object> rtnMap = new HashMap<String, Object>();

		try {


			rtnMap.put("rtnFormDto", sMJFormService.selectFormDtl(smjFormDTO)); //트랜드 리스트 관련

			rtnMap.put("quickTrendList", cOCommService.quickTrendList(sMKTrendDTO)); //트랜드 리스트 관련

			if(selectYn){
				rtnMap.put("eduYearCnt", eBBEpisdService.selectMypageEduCnt(ebbEpisdDTO)); //교육 1년

				rtnMap.put("conYearCnt", cbaTechGuidanceService.selectYearCancelNotConsultingCount(pCBATechGuidanceInsertDTO)); //컨설팅 1년

				rtnMap.put("sanYearCnt", mpbCoexistenceService.selectApplyCount(mpbBnsSearchDTO)); //상생 1년
			}




		}catch (Exception e){
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
			throw new Exception(e.getMessage());
		}

		return rtnMap;


	}


}