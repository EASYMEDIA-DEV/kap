package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.eb.ebb.EBBLctrDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.service.*;
import com.kap.service.dao.COFileMapper;
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

		if(ebbDto !=null && ebbDto.getPlaceSeq() !=null){
			roomDto.setDetailsKey(String.valueOf(ebbDto.getPlaceSeq()));
			roomDto = eBFEduRoomService.selectEduRoomDtl(roomDto);
		}

		//차수관리 - 강사관계 호출
		List<EBBisttrDTO> isttrList = eBBEpisdMapper.selectIsttrList(ebbDto);

		//온라인 교육강의 상세 호출
		List<EBBLctrDTO> lctrDtoList = new ArrayList();
		EBBLctrDTO lctrDto = new EBBLctrDTO();
		if(ebbDto !=null) {
			lctrDto.setEdctnSeq(ebbDto.getEdctnSeq());
			lctrDto.setEpisdOrd(ebbDto.getEpisdOrd());
			lctrDto.setEpisdYear(ebbDto.getEpisdYear());
			lctrDtoList = eBBEpisdMapper.selectLctrDtlList(lctrDto);
		}

		map.put("rtnData", ebbDto);
		map.put("roomDto", roomDto);
		map.put("lctrDtoList", lctrDtoList);
		map.put("isttrList", isttrList);

		return map;
	}


	/**
	 * 교육차수를  등록한다.
	 */
	public int insertEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		int respCnt = 0;

		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		eBBEpisdDTO.setRegId( cOUserDetailsDTO.getId() );
		eBBEpisdDTO.setRegName( cOUserDetailsDTO.getName() );
		eBBEpisdDTO.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
		eBBEpisdDTO.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
		eBBEpisdDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
		eBBEpisdDTO.setModId( cOUserDetailsDTO.getId() );
		eBBEpisdDTO.setModIp( cOUserDetailsDTO.getLoginIp() );


		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBBEpisdDTO.getFileList());
		eBBEpisdDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq"));//교육안내문

		//교육차수 등록
		respCnt = eBBEpisdMapper.insertEpisd(eBBEpisdDTO);

		//교육 강사관계 등록
		eBBEpisdMapper.deleteIsttrRel(eBBEpisdDTO);
		eBBEpisdMapper.insertIsttrRel(eBBEpisdDTO);

		//교육강의 상세 등록(온라인교육)
		setLctrList(eBBEpisdDTO, cOUserDetailsDTO);

		return respCnt;
	}

	/**
	 * 교육차수 -> 온라인교육 강의 목록 등록
	 */
	private void setLctrList(EBBEpisdDTO eBBEpisdDTO, COUserDetailsDTO cOUserDetailsDTO){
		try{
			//교육강의 상세 등록(온라인교육)
			List<EBBLctrDTO> lctrDtoList = eBBEpisdDTO.getLctrList();

			for(EBBLctrDTO lctrDto : lctrDtoList){

				lctrDto.setRegId( cOUserDetailsDTO.getId() );
				lctrDto.setRegName( cOUserDetailsDTO.getName() );
				lctrDto.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
				lctrDto.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
				lctrDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
				lctrDto.setModId( cOUserDetailsDTO.getId() );
				lctrDto.setModIp( cOUserDetailsDTO.getLoginIp() );

				//파일 처리
				HashMap<String, Integer> lctrFileSeqMap = cOFileService.setFileInfo(lctrDto.getFileList());
				lctrDto.setThnlFileSeq(lctrFileSeqMap.get("lctrFileSeq"));

				int firstEdctnLctrIIdgen = edctnLctrIdgen.getNextIntegerId();
				lctrDto.setLctrSeq(firstEdctnLctrIIdgen);
				eBBEpisdMapper.insertLctrDtl(lctrDto);


			}
		}catch (Exception e){

		}

	}


	/**
	 * 교육차수를 수정한다.
	 */
	public int updateEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int respCnt = 0;

		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		eBBEpisdDTO.setRegId( cOUserDetailsDTO.getId() );
		eBBEpisdDTO.setRegName( cOUserDetailsDTO.getName() );
		eBBEpisdDTO.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
		eBBEpisdDTO.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
		eBBEpisdDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
		eBBEpisdDTO.setModId( cOUserDetailsDTO.getId() );
		eBBEpisdDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBBEpisdDTO.getFileList());

		//교육차수 수정
		respCnt = eBBEpisdMapper.updateEpisd(eBBEpisdDTO);

		//강사정보랑 만족도조사(설문), 평가 항목은 교육일 시작 전에만 수정 가능 - 화면단에서 교육 시작일 지나면 수정버튼 비활성화

		//교육 강사관계 등록 (삭제후 등록)
		eBBEpisdMapper.deleteIsttrRel(eBBEpisdDTO);
		eBBEpisdMapper.insertIsttrRel(eBBEpisdDTO);

		//교육강의 상세 등록(온라인교육)
		eBBEpisdMapper.deleteLctrDtl(eBBEpisdDTO);//삭제후
		setLctrList(eBBEpisdDTO, cOUserDetailsDTO);//재등록


		//예산지출 내역 등록
		eBBEpisdMapper.deleteBdgetList(eBBEpisdDTO);
		eBBEpisdMapper.insertBdgetList(eBBEpisdDTO);

		return respCnt;
	}



	/**
	 * 교육차수를 삭제한다.
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