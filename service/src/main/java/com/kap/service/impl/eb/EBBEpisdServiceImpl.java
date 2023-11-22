package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.service.*;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBACouseMapper;
import com.kap.service.dao.eb.EBBEpisdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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


	//교육장 서비스
	private final EBFEduRoomService eBFEduRoomService;

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

		EBFEduRoomDetailDTO roomDto = new EBFEduRoomDetailDTO();
		System.out.println("@@@ebbDto = " + ebbDto);
		if(ebbDto !=null && ebbDto.getPlaceSeq() !=null){
			roomDto.setDetailsKey(String.valueOf(ebbDto.getPlaceSeq()));
			roomDto = eBFEduRoomService.selectEduRoomDtl(roomDto);
		}


		map.put("rtnData", ebbDto);
		map.put("roomDto", roomDto);


		return map;
	}


	/**
	 * 교육차수를  등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		int respCnt = 0;

		COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
		eBBEpisdDTO.setRegId( coaAdmDTO.getId() );
		eBBEpisdDTO.setRegName( coaAdmDTO.getName() );
		eBBEpisdDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
		eBBEpisdDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
		eBBEpisdDTO.setRegIp( coaAdmDTO.getLoginIp() );
		eBBEpisdDTO.setModId( coaAdmDTO.getId() );
		eBBEpisdDTO.setModIp( coaAdmDTO.getLoginIp() );


		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBBEpisdDTO.getFileList());

		eBBEpisdDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq"));//교육안내문

		//교육차수 등록
		respCnt = eBBEpisdMapper.insertEpisd(eBBEpisdDTO);

		//eBBEpisdMapper.insertIsttrRel(eBBEpisdDTO);


		//교육강의 상세 등록(온라인교육)
		List<EBBLctrDTO> lctrDtoList = eBBEpisdDTO.getLctrList();

		//파일첨부 dto 세팅
		for(EBBLctrDTO tt : lctrDtoList){
			List<COFileDTO> fileDtoList = new ArrayList();
			COFileDTO fileDto = new COFileDTO();
			fileDto.setStatus(tt.getStatus());
			fileDto.setWidth(tt.getWidth());
			fileDto.setHeight(tt.getHeight());
			fileDto.setWebPath(tt.getWebPath());
			fileDto.setFieldNm(tt.getFieldNm());
			fileDto.setOrgnFileNm(tt.getOrgnFileNm());
			fileDto.setFileDsc(tt.getFileDsc());
			fileDtoList.add(fileDto);
			tt.setFileList(fileDtoList);
		}

		for(EBBLctrDTO lctrDto : lctrDtoList){

			lctrDto.setRegId( coaAdmDTO.getId() );
			lctrDto.setRegName( coaAdmDTO.getName() );
			lctrDto.setRegDeptCd( coaAdmDTO.getDeptCd() );
			lctrDto.setRegDeptNm( coaAdmDTO.getDeptNm() );
			lctrDto.setRegIp( coaAdmDTO.getLoginIp() );
			lctrDto.setModId( coaAdmDTO.getId() );
			lctrDto.setModIp( coaAdmDTO.getLoginIp() );

			//파일 처리
			HashMap<String, Integer> lctrFileSeqMap = cOFileService.setFileInfo(lctrDto.getFileList());

			lctrDto.setThnlFileSeq(lctrFileSeqMap.get("lctrFileSeq"));


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

	/**
	 * 교육차수 중복 체크
	 */
	@Transactional
	public EBBEpisdDTO selectEpisdChk(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int actCnt = 0;
		EBBEpisdDTO tempDto = new EBBEpisdDTO();
		tempDto = eBBEpisdMapper.selectEpisdChk(eBBEpisdDTO);

		return tempDto;
	}





}