package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.EBACouseDTO;
import com.kap.core.dto.EBBEpisdDTO;
import com.kap.core.dto.EBBLctrDTO;
import com.kap.service.COFileService;
import com.kap.service.COSeqGnrService;
import com.kap.service.EBBEpisdService;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBACouseMapper;
import com.kap.service.dao.eb.EBBEpisdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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
	private final EBBEpisdMapper eBBEpisdMapper;

	//파일 서비스
	private final COFileService cOFileService;
	// DAO
	private final COFileMapper cOFileMapper;
	//파일 업로드 위치
	@Value("${app.file.upload-path}")
	private String fileUploadPath;

	/* 교육차수마스터 시퀀스 */
	private final EgovIdGnrService edctnEpisdIdgen;

	/* 교육차수 - 교육강의상세 시퀀스 */
	private final EgovIdGnrService edctnLctrIdgen;



	/**
	 *  교육차수 목록을 조회한다.
	 */
	public EBBEpisdDTO selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(eBBEpisdDTO.getPageIndex());
		page.setRecordCountPerPage(eBBEpisdDTO.getListRowSize());

		page.setPageSize(eBBEpisdDTO.getPageRowSize());

		eBBEpisdDTO.setFirstIndex( page.getFirstRecordIndex() );
		eBBEpisdDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		eBBEpisdDTO.setList( eBBEpisdMapper.selectEpisdList(eBBEpisdDTO) );
		eBBEpisdDTO.setTotalCount( eBBEpisdMapper.selectEpisdListCnt(eBBEpisdDTO) );

		return eBBEpisdDTO;
	}

	/**
	 * 교육차수 상세를 조회한다.
	 */
	public HashMap<String, Object> selectEpisdDtl(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		HashMap<String, Object> map = new HashMap();

		EBBEpisdDTO ebbDto = eBBEpisdMapper.selectEpisdDtl(eBBEpisdDTO);

		map.put("rtnData", ebbDto);

		return map;
	}


	/**
	 * 교육차수를  등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		int respCnt = 0;

		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBBEpisdDTO.getFileList());

		eBBEpisdDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq"));


		int firstEdctnMstIdgen = edctnEpisdIdgen.getNextIntegerId();

		eBBEpisdDTO.setEdctnSeq(firstEdctnMstIdgen);

		//교육차수 등록
		respCnt = eBBEpisdMapper.insertEpisd(eBBEpisdDTO);

		eBBEpisdMapper.insertIsttrRel(eBBEpisdDTO);

		//교육강의 상세 등록(온라인교육)

		List<EBBLctrDTO> lctrDtoList = eBBEpisdDTO.getLctrList();
		for(EBBLctrDTO lctrDto : lctrDtoList){
			int firstEdctnLctrIIdgen = edctnLctrIdgen.getNextIntegerId();
			lctrDto.setLctrSeq(firstEdctnLctrIIdgen);
			eBBEpisdMapper.insertLctrDtl(lctrDto);
		}

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