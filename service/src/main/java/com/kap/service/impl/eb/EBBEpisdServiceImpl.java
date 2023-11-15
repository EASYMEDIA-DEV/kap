package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.EBACouseDTO;
import com.kap.core.dto.EBBEpisdDTO;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.EBBEpisdService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBACouseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * <pre>
 * 관리자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: COCAdmServiceImpl.java
 * @Description		: 관리자 관리를 위한 ServiceImpl
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.01		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBBEpisdServiceImpl implements EBBEpisdService {

	//DAO
	private final EBACouseMapper eBACouseMapper;

	//파일 서비스
	private final COFileService cOFileService;
	// DAO
	private final COFileMapper cOFileMapper;
	//파일 업로드 위치
	@Value("${app.file.upload-path}")
	private String fileUploadPath;

	/* 교육차수마스터 시퀀스 */
	private final EgovIdGnrService edctnEpisdIdgen;


	/**
	 *  교육차수 목록을 조회한다.
	 */
	public EBBEpisdDTO selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		/*page.setCurrentPageNo(eBACouseDTO.getPageIndex());
		page.setRecordCountPerPage(eBACouseDTO.getListRowSize());

		page.setPageSize(eBACouseDTO.getPageRowSize());

		eBACouseDTO.setFirstIndex( page.getFirstRecordIndex() );
		eBACouseDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		eBACouseDTO.setList( eBACouseMapper.selectCouseList(eBACouseDTO) );
		eBACouseDTO.setTotalCount( eBACouseMapper.selectCouseListCnt(eBACouseDTO) );
*/
		return eBBEpisdDTO;
	}

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public HashMap<String, Object> selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		HashMap<String, Object> map = new HashMap();
		/*
		EBACouseDTO ebaDto = eBACouseMapper.selectCouseDtl(eBACouseDTO);

		if(eBACouseDTO.getCopyYn().equals("Y")){

		}


		map.put("rtnData", ebaDto);

		map.put("rtnTrgtData", eBACouseMapper.selectCouseTrgtList(eBACouseDTO));
		*/


		return map;
	}


	/**
	 * 교육차수를  등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		int respCnt = 0;

		//파일 처리
		/*HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBACouseDTO.getFileList());

		eBACouseDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));


		int firstEdctnMstIdgen = edctnEpisdIdgen.getNextIntegerId();

		eBACouseDTO.setEdctnSeq(firstEdctnMstIdgen);

		//교육과정 등록
		respCnt = eBACouseMapper.insertCouse(eBACouseDTO);

		String temp = eBACouseDTO.getTargetCd();

		String[] tempArray =temp.split(",");

		for(String a : tempArray){
			eBACouseDTO.setTargetCd(a);
			eBACouseMapper.insertCouseTrgt(eBACouseDTO);
		}


		eBACouseDTO.setEdctnSeq(respCnt);*/
		//교육과정대상 등록




		return respCnt;
	}

	/**
	 * 교육차수를 수정한다.
	 */
	public int updateEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int respCnt = 0;

		//파일 처리
		/*HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBACouseDTO.getFileList());

		eBACouseDTO.setThnlFileSeq(fileSeqMap.get("thnlFileSeq"));

		respCnt = eBACouseMapper.updateCouse(eBACouseDTO);

		eBACouseMapper.deleteCouseTrgt(eBACouseDTO);

		String temp = eBACouseDTO.getTargetCd();

		String[] tempArray =temp.split(",");

		for(String a : tempArray){
			eBACouseDTO.setTargetCd(a);
			eBACouseMapper.insertCouseTrgt(eBACouseDTO);
		}*/

		return respCnt;
	}



	/**
	 * 교육과정을 삭제한다.
	 */
	@Transactional
	public int deleteEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int actCnt = 0;


		return actCnt;
	}



}