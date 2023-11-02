package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COGCntsDTO;
import com.kap.service.COGCntsService;
import com.kap.service.COSeqGnrService;
import com.kap.service.COSystemLogService;
import com.kap.service.dao.COGCntsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * CMS 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COGCntsServiceImpl.java
 * @Description		: CMS 관리를 위한 ServiceImpl
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
	private final COSeqGnrService cOSeqGnrService;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;
	String tableNm = "CMS_SEQ";
	/**
	 * CMS 목록을 조회한다.
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
	 * CMS 상세를 조회한다.
	 */
	public COGCntsDTO selectCntsDtl(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		return cOGCntsMapper.selectCntsDtl(pCOGCntsDTO);
	}


	/**
	 * CMS를 등록한다.
	 */
	public int insertCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		String cnts = pCOGCntsDTO.getCnts();

		cOGCntsMapper.updateUseCnts(pCOGCntsDTO);

		pCOGCntsDTO.setSeq(cOSeqGnrService.selectSeq(tableNm));

		pCOGCntsDTO.setCnts(COWebUtil.clearXSSMinimum(cnts));

		return cOGCntsMapper.insertCnts(pCOGCntsDTO);
	}

	/**
	 * CMS를 수정한다.
	 */
	public int updateCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		COGCntsDTO info = cOGCntsMapper.selectCntsDtl(pCOGCntsDTO);
		String cnts = pCOGCntsDTO.getCnts();

		if (!info.getCnts().equals(pCOGCntsDTO.getCnts()))
		{
			return this.insertCnts(pCOGCntsDTO);
		}
		else
		{
			pCOGCntsDTO.setCnts(COWebUtil.clearXSSMinimum(cnts));
			return cOGCntsMapper.updateCnts(pCOGCntsDTO);
		}
	}

	/**
	 * CMS를 삭제한다.
	 */
	@Transactional
	public int deleteCnts(COGCntsDTO pCOGCntsDTO) throws Exception
	{
		return cOGCntsMapper.deleteCnts(pCOGCntsDTO);
	}

}