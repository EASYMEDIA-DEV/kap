package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.service.COGCntsService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COGCntsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class COGCntsServiceImpl implements COGCntsService {

	//DAO
	private final COGCntsMapper cOGCntsMapper;

	/** Sequence **/
	/* 관리자 시퀀스 */
	private final EgovIdGnrService cmsSeqIdgen;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;

	/**
	 * 컨텐츠 목록 조회
	 */
	public COGCntsDTO selectCntsList(COGCntsDTO pCOGCntsDTO) throws Exception {
		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pCOGCntsDTO.getPageIndex());
		page.setRecordCountPerPage(pCOGCntsDTO.getListRowSize());

		page.setPageSize(pCOGCntsDTO.getPageRowSize());

		pCOGCntsDTO.setFirstIndex( page.getFirstRecordIndex() );
		pCOGCntsDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pCOGCntsDTO.setList( cOGCntsMapper.selectCntsList(pCOGCntsDTO) );
		pCOGCntsDTO.setTotalCount( cOGCntsMapper.getCntsListCnt(pCOGCntsDTO) );

		return pCOGCntsDTO;
	}

	/**
	 * 컨텐츠 상세 조회
	 */
	public COGCntsDTO selectCntsDtl(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		return cOGCntsMapper.selectCntsDtl(pCOGCntsDTO);
	}


	/**
	 * 컨텐츠 등록
	 */
	public int insertCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		pCOGCntsDTO.setRegId(cOUserDetailsDTO.getId());
		pCOGCntsDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

		String cnts = pCOGCntsDTO.getCnts();

		pCOGCntsDTO.setSeq(cmsSeqIdgen.getNextIntegerId());

		pCOGCntsDTO.setCnts(COWebUtil.clearXSSMinimum(cnts));

		return cOGCntsMapper.insertCnts(pCOGCntsDTO);
	}

	/**
	 * 컨텐츠 수정
	 */
	public int updateCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		if (!pCOGCntsDTO.getDetailsKey().isEmpty())
		{
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			pCOGCntsDTO.setModId(cOUserDetailsDTO.getId());
			pCOGCntsDTO.setModIp(cOUserDetailsDTO.getLoginIp());

			String cnts = pCOGCntsDTO.getCnts();
			pCOGCntsDTO.setCnts(COWebUtil.clearXSSMinimum(cnts));

			return cOGCntsMapper.updateCnts(pCOGCntsDTO);
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 컨텐츠 삭제
	 */
	@Transactional
	public int deleteCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		return cOGCntsMapper.deleteCnts(pCOGCntsDTO);
	}

	/**
	 * 컨텐츠 배포
	 */
	public void updateCntsAprvl(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		// 수정자 셋팅
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		pCOGCntsDTO.setModId(cOUserDetailsDTO.getId());
		pCOGCntsDTO.setModIp(cOUserDetailsDTO.getLoginIp());

		// 컨텐츠 만료
		cOGCntsMapper.updateCntsExpr(pCOGCntsDTO);

		// 컨텐츠 배포
		cOGCntsMapper.updateCntsAprvl(pCOGCntsDTO);
	}

	/**
	 * 컨텐츠 복사
	 */
	public void insertCntsCopy(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		COGCntsDTO selectCnts = cOGCntsMapper.selectCntsDtl(pCOGCntsDTO);

		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		selectCnts.setRegId(cOUserDetailsDTO.getId());
		selectCnts.setRegIp(cOUserDetailsDTO.getLoginIp());

		selectCnts.setSeq(cmsSeqIdgen.getNextIntegerId());

		cOGCntsMapper.insertCntsCopy(selectCnts);
	}

	/**
	 * 컨텐츠 최신 버전 값 조회
	 */
	public COGCntsDTO selectNewVer(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		if(pCOGCntsDTO.getMenuSeq() > 0) {
			pCOGCntsDTO.setVer(cOGCntsMapper.selectNewVer(pCOGCntsDTO));
			return pCOGCntsDTO;
		}
		else {
			pCOGCntsDTO.setVer(-1);
			return pCOGCntsDTO;
		}
	}

}