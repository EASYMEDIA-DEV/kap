package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.*;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.service.*;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBBEpisdMapper;
import com.kap.service.dao.eb.EBBFrontEpisdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 *    2024.01.09		이옥정		  메인에서 호출시 리스트 갯수를 24개로 처리
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBBEpisdServiceImpl implements EBBEpisdService {

	//DAO
	private final EBBEpisdMapper eBBEpisdMapper;

	private final EBBFrontEpisdMapper eBBFrontEpisdMapper;


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

	/* 교육차수 - 교육참여마스터 시퀀스 */
	private final EgovIdGnrService edctnPtcptSeqIdgen;

	/* 교육차수 - 차수별 만족도 결과 상세 */
	private final EgovIdGnrService edctnRsltSeqIdgen;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;





	/**
	 *  교육차수 목록을 조회한다.
	 */
	public EBBEpisdDTO selectEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{



		if(eBBEpisdDTO.getSiteGubun().equals("front")){

			eBBEpisdDTO.setTotalCount( eBBFrontEpisdMapper.selectFrontCouseListCnt(eBBEpisdDTO) );
			// 2024-01-09 메인에서 호출시 리스트 갯수를 24개로 처리
			if(eBBEpisdDTO.getMainYn().equals("Y")){
				eBBEpisdDTO.setPageRowSize(24);
			}else {
				eBBEpisdDTO.setPageRowSize(9);
			}
			int recordCountPerPage = (eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize() >= eBBEpisdDTO.getTotalCount()) ? eBBEpisdDTO.getTotalCount() : eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize();

			eBBEpisdDTO.setFirstIndex(0);
			eBBEpisdDTO.setRecordCountPerPage( recordCountPerPage );

			eBBEpisdDTO.setList( eBBFrontEpisdMapper.selectFrontCouseList(eBBEpisdDTO) );

		}else{

			COPaginationUtil page = new COPaginationUtil();


			page.setCurrentPageNo(eBBEpisdDTO.getPageIndex());
			page.setRecordCountPerPage(eBBEpisdDTO.getListRowSize());

			page.setPageSize(eBBEpisdDTO.getPageRowSize());

			eBBEpisdDTO.setFirstIndex( page.getFirstRecordIndex() );
			eBBEpisdDTO.setRecordCountPerPage( page.getRecordCountPerPage() );


			eBBEpisdDTO.setList( eBBEpisdMapper.selectEpisdList(eBBEpisdDTO) );
			eBBEpisdDTO.setTotalCount( eBBEpisdMapper.selectEpisdListCnt(eBBEpisdDTO) );
		}


		return eBBEpisdDTO;
	}

	/**
	 *  교육과정에 속한 차수목록을 호출한다.
	 */
	public EBBEpisdDTO selectCouseChildEpisdList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		eBBEpisdDTO.setTotalCount( eBBEpisdMapper.selectEpisdListCnt(eBBEpisdDTO) );

		int recordCountPerPage = (eBBEpisdDTO.getPageIndex() * eBBEpisdDTO.getPageRowSize() >= eBBEpisdDTO.getTotalCount()) ? eBBEpisdDTO.getTotalCount() : eBBEpisdDTO.getPageIndex() * eBBEpisdDTO.getPageRowSize();

		eBBEpisdDTO.setFirstIndex(0);
		eBBEpisdDTO.setRecordCountPerPage(recordCountPerPage);

		eBBEpisdDTO.setList( eBBEpisdMapper.selectEpisdList(eBBEpisdDTO) );

		return eBBEpisdDTO;
	}

	/**
	 *  교육차수 목록을 조회한다.(엑셀용)
	 */
	public EBBEpisdExcelDTO selectEpisdExcelList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		EBBEpisdExcelDTO excelListDto = new EBBEpisdExcelDTO();

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(eBBEpisdDTO.getPageIndex());
		page.setRecordCountPerPage(eBBEpisdDTO.getListRowSize());

		page.setPageSize(eBBEpisdDTO.getPageRowSize());

		excelListDto.setFirstIndex( page.getFirstRecordIndex() );
		excelListDto.setRecordCountPerPage( page.getRecordCountPerPage() );

		excelListDto.setList( eBBEpisdMapper.selectEpisdExcelList(eBBEpisdDTO) );
		excelListDto.setTotalCount( eBBEpisdMapper.selectEpisdExcelListCnt(eBBEpisdDTO) );



		return excelListDto;
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

		if(eBBEpisdDTO.getSiteGubun().equals("admin")){
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

			//예산지출 상세 호출

			List<EBBBdgetDTO> bdgetList = new ArrayList();
			EBBBdgetDTO bdgetDto = new EBBBdgetDTO();
			if(ebbDto !=null) {
				bdgetDto.setEdctnSeq(ebbDto.getEdctnSeq());
				bdgetDto.setEpisdOrd(ebbDto.getEpisdOrd());
				bdgetDto.setEpisdYear(ebbDto.getEpisdYear());
				bdgetList = eBBEpisdMapper.selectBdgetDtlList(bdgetDto);
			}



			EBBSrvRstDTO srvScoreDtl = eBBEpisdMapper.selectEpisdSrvScoreDtl(ebbDto);


			//만족도결과 호출
			EBBSrvRstDTO srvRstDtl = eBBEpisdMapper.selectEpisdSrvRstDtl(ebbDto);


			map.put("roomDto", roomDto);//교육장정보
			map.put("lctrDtoList", lctrDtoList);//온라인강의 목록
			map.put("isttrList", isttrList);//강사 목록
			map.put("bdgetList", bdgetList);//예산지출내역 목록

			map.put("srvScoreDtl", srvScoreDtl);//만족도결과 점수
			map.put("srvRstDtl", srvRstDtl);//설문 상세
		}
		map.put("rtnData", ebbDto);//교육차수 상세




		return map;
	}

	/**
	 * 온라인 교육강의 상세 호출
	 */
	public EBBLctrDTO selectLctrDtlList(EBBLctrDTO eBBLctrDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();


		eBBLctrDTO.setTotalCount( eBBEpisdMapper.selectLctrDtlListCnt(eBBLctrDTO) );

		int recordCountPerPage = (eBBLctrDTO.getPageIndex() * eBBLctrDTO.getPageRowSize() >= eBBLctrDTO.getTotalCount()) ? eBBLctrDTO.getTotalCount() : eBBLctrDTO.getPageIndex() * eBBLctrDTO.getPageRowSize();

		eBBLctrDTO.setFirstIndex(0);
		eBBLctrDTO.setRecordCountPerPage(recordCountPerPage);

		eBBLctrDTO.setList( eBBEpisdMapper.selectLctrDtlList(eBBLctrDTO) );


		return eBBLctrDTO;
	}

	/**
	 * 교육 참여자 목록을 호출한다.
	 */
	public EBBPtcptDTO setPtcptList(EBBEpisdDTO ebbDto) throws Exception
	{

		List<EBBPtcptDTO> ptcptList = new ArrayList();
		EBBPtcptDTO dto = new EBBPtcptDTO();

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(ebbDto.getPageIndex());
		page.setRecordCountPerPage(ebbDto.getListRowSize());

		page.setPageSize(ebbDto.getPageRowSize());

		ebbDto.setFirstIndex( page.getFirstRecordIndex() );
		ebbDto.setRecordCountPerPage( page.getRecordCountPerPage() );

		ptcptList = eBBEpisdMapper.selectEpisdPtcptList(ebbDto);
		int ptcptCnt = eBBEpisdMapper.selectEpisdPtcptListCnt(ebbDto);


		dto.setPtcptList(ptcptList);
		dto.setTotalCount(ptcptCnt);


		return dto;
	}

	/**
	 * 교육 참여자 출석부 목록을 호출한다.
	 */
	public EBBPtcptDTO setAtndcList(EBBEpisdDTO ebbDto) throws Exception
	{

		List<EBBPtcptDTO> ptcptList = new ArrayList();
		EBBPtcptDTO dto = new EBBPtcptDTO();

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(ebbDto.getPageIndex());
		page.setRecordCountPerPage(ebbDto.getListRowSize());

		page.setPageSize(ebbDto.getPageRowSize());

		ebbDto.setFirstIndex( page.getFirstRecordIndex() );
		ebbDto.setRecordCountPerPage( page.getRecordCountPerPage() );

		ptcptList = eBBEpisdMapper.selectEpisdPtcptList(ebbDto);
		int ptcptCnt = eBBEpisdMapper.selectEpisdPtcptListCnt(ebbDto);

		dto.setFirstIndex( page.getFirstRecordIndex() );
		dto.setRecordCountPerPage( page.getRecordCountPerPage() );
		dto.setPtcptList(ptcptList);
		dto.setTotalCount(ptcptCnt);

		//참여자가 있을때만 불러옴
		if(ptcptList.size()>0){
			List<EBBPtcptDTO> ptcptAtndcList = new ArrayList();
			ptcptAtndcList = eBBEpisdMapper.selectPtcptAtndcList(dto);

			for(EBBPtcptDTO orgDto: ptcptList){
				List<EBBPtcptDTO> tempList = new ArrayList();
				for(EBBPtcptDTO atndcDto : ptcptAtndcList){
					//원본 참여자 목록 반복문 돌리면서 출석데이터와 매칭, 매칭하면서 같으면 리스트안에 리스트 넣어줌
					if(atndcDto.getPtcptSeq() == orgDto.getPtcptSeq()){
						tempList.add(atndcDto);
					}
				}
				orgDto.setAtndcList(tempList);
			}
			dto.setPtcptList(ptcptList);
		}

		return dto;
	}

	/**
	 * 교육차수 개인별 출석부를 호출한다.
	 */
	public List<EBBPtcptDTO> selectMemAtndcList(EBBPtcptDTO eBBPtcptDTO) throws Exception
	{
		List<EBBPtcptDTO> memAtndcList = new ArrayList();

		memAtndcList = eBBEpisdMapper.selectMemAtndcDtl(eBBPtcptDTO);


		return memAtndcList;
	}

	/**
	 * 마이페이지 - 최근1년간 신청내역 호출(교육사업)
	 */
	public int selectMypageEduCnt(EBBEpisdDTO eBBEpisdDTO) throws Exception{
		int eduCnt = eBBFrontEpisdMapper.selectMypageEduCnt(eBBEpisdDTO);

		return eduCnt;
	}

	/**
	 *  마이페이지 - 교육/세미나 사입 신청내역
	 */
	public EBBEpisdDTO selectMypageEduList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		/*
		COPaginationUtil page = new COPaginationUtil();


		page.setCurrentPageNo(eBBEpisdDTO.getPageIndex());
		page.setRecordCountPerPage(eBBEpisdDTO.getListRowSize());

		page.setPageSize(eBBEpisdDTO.getPageRowSize());

		eBBEpisdDTO.setFirstIndex(0);//더보기라서 0 고정
		int recordCountPerPage = (eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize() >= eBBEpisdDTO.getTotalCount()) ? eBBEpisdDTO.getTotalCount() : eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize();
		eBBEpisdDTO.setRecordCountPerPage( recordCountPerPage );


		eBBEpisdDTO.setList( eBBFrontEpisdMapper.selectMypageEduList(eBBEpisdDTO) );
		int totcnt = eBBFrontEpisdMapper.selectMypageEduListCnt(eBBEpisdDTO).size();
		eBBEpisdDTO.setTotalCount( totcnt );*/



		int totcnt = eBBFrontEpisdMapper.selectMypageEduListCnt(eBBEpisdDTO).size();
		eBBEpisdDTO.setTotalCount( totcnt  );

		eBBEpisdDTO.setPageRowSize(9);

		int recordCountPerPage = (eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize() >= eBBEpisdDTO.getTotalCount()) ? eBBEpisdDTO.getTotalCount() : eBBEpisdDTO.getPageIndex()*eBBEpisdDTO.getPageRowSize();

		eBBEpisdDTO.setFirstIndex(0);
		eBBEpisdDTO.setRecordCountPerPage( recordCountPerPage );

		eBBEpisdDTO.setList( eBBFrontEpisdMapper.selectMypageEduList(eBBEpisdDTO) );


		return eBBEpisdDTO;
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




		int firstEdctnEpisdIdgen = edctnEpisdIdgen.getNextIntegerId();

		eBBEpisdDTO.setEpisdSeq(firstEdctnEpisdIdgen);




		//파일 처리
		HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(eBBEpisdDTO.getFileList());
		eBBEpisdDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq"));//교육안내문

		//교육차수 등록
		respCnt = eBBEpisdMapper.insertEpisd(eBBEpisdDTO);

		//교육 차수별 만족도 결과 상세
		int firstRsltSeqIdgen = edctnRsltSeqIdgen.getNextIntegerId();

		eBBEpisdDTO.setRsltSeq(firstRsltSeqIdgen);
		respCnt = eBBEpisdMapper.insertEpisdSrvRsltDtl(eBBEpisdDTO);

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

		eBBEpisdDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq"));
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

		List<EBBPtcptDTO> ptcptList = eBBEpisdDTO.getPtcptList();
		if(ptcptList !=null && ptcptList.size()>0){
			for(EBBPtcptDTO eBBPtcptDTO : ptcptList){
				eBBPtcptDTO.setModId( cOUserDetailsDTO.getId() );
				eBBPtcptDTO.setModIp( cOUserDetailsDTO.getLoginIp() );
			}
			//교육참여자 평가, 수료상태 수정
			eBBEpisdMapper.updateEpisdPtcpt(eBBEpisdDTO);
		}



		return respCnt;
	}



	/**
	 * 교육차수를 삭제한다.
	 */
	@Transactional
	public EBBEpisdDTO deleteEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		EBBEpisdDTO resultDto = new EBBEpisdDTO();



		int ptcptCnt = eBBEpisdMapper.selectEpisdDeletePrevChk(eBBEpisdDTO);

		if(ptcptCnt>0){
			resultDto.setRsn("F");
		}else{
			eBBEpisdMapper.deleteEpisdDtl(eBBEpisdDTO);
			resultDto.setRsn("S");
		}




		return resultDto;
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

	/**
	 * 교육차수 강제 종강처리
	 */
	@Transactional
	public int updateEpisdEndEdu(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int actCnt = 0;
		try{

			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			eBBEpisdDTO.setModId( cOUserDetailsDTO.getId() );
			eBBEpisdDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

			//참여한 회원 전부 교육 취소상태로 변경 EDU_STTS_CD06
			eBBEpisdMapper.updatePtcptStatus(eBBEpisdDTO);

			//선택한 교육차수의 상태를 종강(폐강)으로 변경 EDCTN_STTS_CD02
			eBBEpisdMapper.updateEpisdStatus(eBBEpisdDTO);




			EBBEpisdDTO tempDto = new EBBEpisdDTO();
			tempDto = eBBEpisdMapper.selectEpisdChk(eBBEpisdDTO);

		}catch (Exception e){

		}

		return actCnt;
	}



	/**
	 * 교육차수 신청자 정원체크
	 */
	@Transactional
	public EBBEpisdDTO selectFxnumChk(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		EBBEpisdDTO tempDto = new EBBEpisdDTO();

		tempDto = eBBEpisdMapper.selectFxnumChk(eBBEpisdDTO);
		//정원수 비교


		return tempDto;
	}

	/**
	 * 교육차수 신청자 등록
	 */
	@Transactional
	public EBBPtcptDTO setPtcptInfo(EBBPtcptDTO eBBPtcptDTO) throws Exception
	{
		EBBPtcptDTO tempDto = new EBBPtcptDTO();

		tempDto = eBBEpisdMapper.selectPtcptDtl(eBBPtcptDTO);

		//이미 등록된 회원
		if(tempDto !=null){

			eBBPtcptDTO.setRegStat("F");

		//없어서 새로 추가함
		}else{

			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			eBBPtcptDTO.setRegId( cOUserDetailsDTO.getId() );
			eBBPtcptDTO.setRegName( cOUserDetailsDTO.getName() );
			eBBPtcptDTO.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
			eBBPtcptDTO.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
			eBBPtcptDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
			eBBPtcptDTO.setModId( cOUserDetailsDTO.getId() );
			eBBPtcptDTO.setModIp( cOUserDetailsDTO.getLoginIp() );


			int firstEdctnPtcptIdgen = edctnPtcptSeqIdgen.getNextIntegerId();
			eBBPtcptDTO.setPtcptSeq(firstEdctnPtcptIdgen);

			eBBEpisdMapper.insertPtcptDtl(eBBPtcptDTO);

			if(!eBBPtcptDTO.getStduyMthdCd().equals("STDUY_MTHD02")){
				setAtndcList(eBBPtcptDTO);//교육참여 출석 상세 목록을 등록한다.
			}




			eBBPtcptDTO.setRegStat("S");
		}


		return eBBPtcptDTO;

	}


	/*
	 * 교육차수 신청자 조회
	 * */
	public EBBPtcptDTO selectPtcptDtl(EBBPtcptDTO eBBPtcptDTO) throws Exception
	{
		try{

			eBBPtcptDTO = eBBEpisdMapper.selectPtcptDtl(eBBPtcptDTO);

		}catch (Exception e){

		}

		return eBBPtcptDTO;
	}

	/*
	 * 교육차수 차수변경
	 * */
	public int changeEpisd(EBBEpisdDTO eBBEpisdDTO) throws Exception{

		int rtnCnt = 0;


			//전달받은 회원번호로 반복문 돌림
			//상태값이 F인경우 반환중지함?
			List<EBBPtcptDTO> ptcptList = eBBEpisdDTO.getPtcptList();

			for(EBBPtcptDTO ptcptDto : ptcptList){
				//변경전 정보를 받아온다
				EBBPtcptDTO prevPtcptDto = eBBEpisdMapper.selectPtcptDtl(ptcptDto);

				//새 dto에 넣는다 차수정렬,차수년도, 회원번호, 사업자번호

				EBBPtcptDTO nextPtcptDto = new EBBPtcptDTO();

				COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
				nextPtcptDto.setRegId( cOUserDetailsDTO.getId() );
				nextPtcptDto.setRegName( cOUserDetailsDTO.getName() );
				nextPtcptDto.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
				nextPtcptDto.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
				nextPtcptDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
				nextPtcptDto.setModId( cOUserDetailsDTO.getId() );
				nextPtcptDto.setModIp( cOUserDetailsDTO.getLoginIp() );



				int firstEdctnPtcptIdgen = edctnPtcptSeqIdgen.getNextIntegerId();
				nextPtcptDto.setPtcptSeq(firstEdctnPtcptIdgen);

				nextPtcptDto.setEdctnSeq(eBBEpisdDTO.getEdctnSeq());//과정 순번


				nextPtcptDto.setEpisdYear(eBBEpisdDTO.getEpisdYear());//신규 회차년도
				nextPtcptDto.setEpisdOrd(eBBEpisdDTO.getEpisdOrd());//신규 회차정렬


				eBBEpisdDTO.setDetailsKey(String.valueOf(eBBEpisdDTO.getEdctnSeq()));
				EBBEpisdDTO targetDto = eBBEpisdMapper.selectEpisdDtl(eBBEpisdDTO);

				nextPtcptDto.setMemSeq(prevPtcptDto.getMemSeq()); //회원번호
				nextPtcptDto.setPtcptBsnmNo(prevPtcptDto.getPtcptBsnmNo());//회원 사업자번호

				nextPtcptDto.setEdctnStrtDtm(targetDto.getEdctnStrtDtm());
				nextPtcptDto.setEdctnEndDtm(targetDto.getEdctnEndDtm());
				nextPtcptDto.setEpisdSeq(targetDto.getEpisdSeq());//회차순번

				eBBEpisdMapper.insertPtcptDtl(nextPtcptDto);

				//온라인 교육일때는 출석폼 입력 안함
				if(!eBBEpisdDTO.getStduyMthdCd().equals("STDUY_MTHD02")){
					this.setAtndcList(nextPtcptDto);//교육참여 출석 상세 목록을 등록한다.
				}



				//이전차수의 참여정보 내역 교육취소 처리
				ptcptDto.setModId( cOUserDetailsDTO.getId() );
				ptcptDto.setModIp( cOUserDetailsDTO.getLoginIp() );
				eBBEpisdMapper.updatePtcptStatusInfo(ptcptDto);

			}


		return rtnCnt;
	}


	/*
	* 교육차수 신청자(참여) 출석 상세 생성
	* */
	public void setAtndcList(EBBPtcptDTO eBBPtcptDTO) throws Exception{

		String getEdctnStrtDtm= eBBPtcptDTO.getEdctnStrtDtm();
		String getEdctnEndDtm= eBBPtcptDTO.getEdctnEndDtm();
		System.out.println("@ getEdctnStrtDtm = " + getEdctnStrtDtm);
		System.out.println("@ getEdctnEndDtm = " + getEdctnEndDtm);
		// 시작 날짜와 종료 날짜 설정
		String startDateString = getEdctnStrtDtm.substring(0, 10);
		String endDateString = getEdctnEndDtm.substring(0, 10);

		// 시작 날짜와 종료 날짜를 LocalDate 객체로 변환
		LocalDate startDate = LocalDate.parse(startDateString);
		LocalDate endDate = LocalDate.parse(endDateString);

		// 날짜 출력 형식 지정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<EBBPtcptDTO> atndcDtoList = new ArrayList<>();
		// 반복문을 통해 날짜 출력
		while (!startDate.isAfter(endDate)) {
			EBBPtcptDTO atndcDto = new EBBPtcptDTO();

			atndcDto.setPtcptSeq(eBBPtcptDTO.getPtcptSeq());
			atndcDto.setEdctnDt(String.valueOf(startDate));
			atndcDto.setAtndcDtm(null);
			atndcDto.setLvgrmDtm(null);
			atndcDto.setEtcNm(null);
			atndcDto.setRegId(eBBPtcptDTO.getRegId());
			atndcDto.setRegIp(eBBPtcptDTO.getRegIp());
			atndcDto.setModId(eBBPtcptDTO.getModId());
			atndcDto.setModIp(eBBPtcptDTO.getModIp());
			atndcDtoList.add(atndcDto);
			startDate = startDate.plusDays(1); // 다음 날짜로 이동
		}


		eBBPtcptDTO.setPtcptList(atndcDtoList);
		eBBEpisdMapper.insertAtndcList(eBBPtcptDTO);

	}

	/**
	 * 교육차수 신청자 등록
	 */
	@Transactional
	public int updateAtndcList(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{

		int rtnCnt = 0;
		//출석 목록 호출
		List<EBBPtcptDTO> ptcptList= eBBEpisdDTO.getPtcptList();


		List<EBBPtcptDTO> removeList = new ArrayList();
		for(int i=0; i < ptcptList.size(); i ++){

			EBBPtcptDTO tempDto = ptcptList.get(i);

			int removeStack = 0;
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			tempDto.setRegId( cOUserDetailsDTO.getId() );
			tempDto.setRegName( cOUserDetailsDTO.getName() );
			tempDto.setRegDeptCd( cOUserDetailsDTO.getDeptCd() );
			tempDto.setRegDeptNm( cOUserDetailsDTO.getDeptNm() );
			tempDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
			tempDto.setModId( cOUserDetailsDTO.getId() );
			tempDto.setModIp( cOUserDetailsDTO.getLoginIp() );

			if(tempDto.getAtndcHour() != null){
				tempDto.setAtndcDtm(tempDto.getEdctnDt()+" "+tempDto.getAtndcHour());
			}

			if(tempDto.getLvgrmHour() != null){
				tempDto.setLvgrmDtm(tempDto.getEdctnDt()+" "+tempDto.getLvgrmHour());
			}

		}

		if(ptcptList.size()>0){
			eBBEpisdDTO.setPtcptList(ptcptList);
			rtnCnt = eBBEpisdMapper.updateAtndcList(eBBEpisdDTO);
		}

		return rtnCnt;
	}


	/**
	 * 설문초기화 삭제한다.
	 */
	@Transactional
	public int deleteSurveyRspn(EBBEpisdDTO eBBEpisdDTO) throws Exception
	{
		int rtnCnt = eBBEpisdMapper.deleteSurveyRspn(eBBEpisdDTO);
		return rtnCnt;
	}












	/**
	 * 교육차수관리-> 차수목록 엑셀 생성
	 */
	public void excelDownload1(EBBEpisdExcelDTO eBBEpisdExcelDTO, HttpServletResponse response) throws Exception{

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle style_header = workbook.createCellStyle();
		XSSFCellStyle style_body = workbook.createCellStyle();
		Sheet sheet = workbook.createSheet();

		Row row = null;
		Cell cell = null;
		int rowNum = 3;

		//Cell Alignment 지정
		style_header.setAlignment(HorizontalAlignment.CENTER);
		style_header.setVerticalAlignment(VerticalAlignment.CENTER);
		style_body.setAlignment(HorizontalAlignment.CENTER);
		style_body.setVerticalAlignment(VerticalAlignment.CENTER);

		// Border Color 지정
		style_header.setBorderTop(BorderStyle.THIN);
		style_header.setBorderLeft(BorderStyle.THIN);
		style_header.setBorderRight(BorderStyle.THIN);
		style_header.setBorderBottom(BorderStyle.THIN);
		style_body.setBorderTop(BorderStyle.THIN);
		style_body.setBorderLeft(BorderStyle.THIN);
		style_body.setBorderRight(BorderStyle.THIN);
		style_body.setBorderBottom(BorderStyle.THIN);

		//BackGround Color 지정
		style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//0~19 :기본정보
		//20~36 : 교육 실적1
		//37~56 : 교육실적2
		//57~80 : 예산/지출


		//셀 병합 관련해서 만든 리스트
		List<CellRangeAddress> cellList = new ArrayList<>();

		//헤더 1층 기본정보, 교육실적, 교육실적2, 예산/지출
		row = sheet.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("기본정보1");
		cell.setCellStyle(style_header);




		cell = row.createCell(20);
		cell.setCellValue("교육실적");
		cell.setCellStyle(style_header);
		cell = row.createCell(58);
		cell.setCellValue("예산/지출");
		cell.setCellStyle(style_header);

		// 셀 병합
		CellRangeAddress row1cols0 = new CellRangeAddress(0, 0, 0, 19);//기본정보 병합 0~19
		CellRangeAddress row1cols20 = new CellRangeAddress(0, 0, 20, 57);//교육실적 병합 병합 20~56
		CellRangeAddress row1cols58 = new CellRangeAddress(0, 0, 58, 81);//예산/지출 병합 57~80
		sheet.addMergedRegion(row1cols0); sheet.addMergedRegion(row1cols20); sheet.addMergedRegion(row1cols58);
		cellList.add(row1cols0); cellList.add(row1cols20); cellList.add(row1cols58);



		//헤더 2층 과정분류1, 과정분류2, 과정명, 학습방식, 학습시간, 업종 년도 차수 교육일 교육기간(2), 교육장소 협력기관 정원(명) 강사1 강사2 강사3 강사4 강사5 강사6
		//개요, 회사별 수료인원(명), 분야별 수료인원(명), 직급별 수료인원(명), 출석/평가, 만족도, 교육시간, 예산, 지출
		row = sheet.createRow(1);

		//2층 기본정보 시작
		cell = row.createCell(0); cell.setCellValue("과정분류1"); cell.setCellStyle(style_header);
		cell = row.createCell(1); cell.setCellValue("과정분류2"); cell.setCellStyle(style_header);
		cell = row.createCell(2); cell.setCellValue("과정명"); cell.setCellStyle(style_header);
		cell = row.createCell(3); cell.setCellValue("학습방식"); cell.setCellStyle(style_header);
		cell = row.createCell(4); cell.setCellValue("학습시간"); cell.setCellStyle(style_header);
		cell = row.createCell(5); cell.setCellValue("업종"); cell.setCellStyle(style_header);
		cell = row.createCell(6); cell.setCellValue("년도"); cell.setCellStyle(style_header);
		cell = row.createCell(7); cell.setCellValue("차수"); cell.setCellStyle(style_header);
		cell = row.createCell(8); cell.setCellValue("교육일"); cell.setCellStyle(style_header);
		cell = row.createCell(9); cell.setCellValue("교육기간"); cell.setCellStyle(style_header);

		cell = row.createCell(11); cell.setCellValue("교육장소"); cell.setCellStyle(style_header);
		cell = row.createCell(12); cell.setCellValue("협력기관"); cell.setCellStyle(style_header);
		cell = row.createCell(13); cell.setCellValue("정원(명"); cell.setCellStyle(style_header);
		cell = row.createCell(14); cell.setCellValue("강사1"); cell.setCellStyle(style_header);
		cell = row.createCell(15); cell.setCellValue("강사2"); cell.setCellStyle(style_header);
		cell = row.createCell(16); cell.setCellValue("강사3"); cell.setCellStyle(style_header);
		cell = row.createCell(17); cell.setCellValue("강사4"); cell.setCellStyle(style_header);
		cell = row.createCell(18); cell.setCellValue("강사5"); cell.setCellStyle(style_header);
		cell = row.createCell(19); cell.setCellValue("강사6"); cell.setCellStyle(style_header);
		//2층 기본정보 끝

		//2층 교육실적1 시작
		cell = row.createCell(20); cell.setCellValue("개요"); cell.setCellStyle(style_header);//개요 20~23
		cell = row.createCell(24); cell.setCellValue("회사별 수료인원(명)"); cell.setCellStyle(style_header);//회사별 수료인원(명) 24~27
		cell = row.createCell(28); cell.setCellValue("분야별 수료인원(명)"); cell.setCellStyle(style_header);//분야별 수료인원(명) 28~36
		//2층 교육실적1 끝

		//2층 교육실적2 시작
		cell = row.createCell(37); cell.setCellValue("직급별 수료인원(명)"); cell.setCellStyle(style_header);//직급별 수료인원(명) 37~42
		cell = row.createCell(44); cell.setCellValue("출석/평가"); cell.setCellStyle(style_header);//출석/평가 43~44
		cell = row.createCell(46); cell.setCellValue("만족도"); cell.setCellStyle(style_header);//만족도 45~50
		cell = row.createCell(52); cell.setCellValue("교육 시간"); cell.setCellStyle(style_header);//교육 시간 51~56
		//2층 교육실적2 끝

		//2층 예산지출 시작
		cell = row.createCell(58); cell.setCellValue("예산"); cell.setCellStyle(style_header);//예산 57~68
		cell = row.createCell(70); cell.setCellValue("지출"); cell.setCellStyle(style_header);//지출 69~80

		//2층 예산지출 끝

		// 셀 병합

		//2층 기본정보 시작
		CellRangeAddress row2cols0 = new CellRangeAddress(1, 2, 0, 0);//과정분류1
		CellRangeAddress row2cols1 = new CellRangeAddress(1, 2, 1, 1);//과정분류2
		CellRangeAddress row2cols2 = new CellRangeAddress(1, 2, 2, 2);//과정명
		CellRangeAddress row2cols3 = new CellRangeAddress(1, 2, 3, 3);//학습방식
		CellRangeAddress row2cols4 = new CellRangeAddress(1, 2, 4, 4);//학습시간
		CellRangeAddress row2cols5 = new CellRangeAddress(1, 2, 5, 5);//업종
		CellRangeAddress row2cols6 = new CellRangeAddress(1, 2, 6, 6);//년도
		CellRangeAddress row2cols7 = new CellRangeAddress(1, 2, 7, 7);//차수
		CellRangeAddress row2cols8 = new CellRangeAddress(1, 2, 8, 8);//교육일
		CellRangeAddress row2cols9 = new CellRangeAddress(1, 2, 9, 10);//교육기간
		CellRangeAddress row2cols11 = new CellRangeAddress(1, 2, 11, 11);//교육장소
		CellRangeAddress row2cols12 = new CellRangeAddress(1, 2, 12, 12);//협력기관
		CellRangeAddress row2cols13 = new CellRangeAddress(1, 2, 13, 13);//정원(명)
		CellRangeAddress row2cols14 = new CellRangeAddress(1, 2, 14, 14);//강사1
		CellRangeAddress row2cols15 = new CellRangeAddress(1, 2, 15, 15);//강사2
		CellRangeAddress row2cols16 = new CellRangeAddress(1, 2, 16, 16);//강사3
		CellRangeAddress row2cols17 = new CellRangeAddress(1, 2, 17, 17);//강사4
		CellRangeAddress row2cols18 = new CellRangeAddress(1, 2, 18, 18);//강사5
		CellRangeAddress row2cols19 = new CellRangeAddress(1, 2, 19, 19);//강사6
		sheet.addMergedRegion(row2cols0); 	sheet.addMergedRegion(row2cols1);		sheet.addMergedRegion(row2cols2);		sheet.addMergedRegion(row2cols3);
		sheet.addMergedRegion(row2cols4);		sheet.addMergedRegion(row2cols5);		sheet.addMergedRegion(row2cols6);		sheet.addMergedRegion(row2cols7);
		sheet.addMergedRegion(row2cols8);		sheet.addMergedRegion(row2cols9);		sheet.addMergedRegion(row2cols11);		sheet.addMergedRegion(row2cols12);
		sheet.addMergedRegion(row2cols13);		sheet.addMergedRegion(row2cols14);		sheet.addMergedRegion(row2cols15);		sheet.addMergedRegion(row2cols16);
		sheet.addMergedRegion(row2cols17);		sheet.addMergedRegion(row2cols18);		sheet.addMergedRegion(row2cols19);
		//2층 기본정보 끝

		//2층 교육실적1 시작
		CellRangeAddress row2cols20 = new CellRangeAddress(1, 1, 20, 23);//개요
		CellRangeAddress row2cols24 = new CellRangeAddress(1, 1, 24, 27);//회사별 수료인원(명)
		CellRangeAddress row2cols28 = new CellRangeAddress(1, 1, 28, 36);//분야별 수료인원(명)
		sheet.addMergedRegion(row2cols20); sheet.addMergedRegion(row2cols24); sheet.addMergedRegion(row2cols28);
		//2층 교육실적1 끝

		//2층 교육실적2 시작
		CellRangeAddress row2cols37 = new CellRangeAddress(1, 1, 37, 43);//직급별 수료인원(명)
		CellRangeAddress row2cols44 = new CellRangeAddress(1, 1, 44, 45);//출석/평가
		CellRangeAddress row2cols46 = new CellRangeAddress(1, 1, 46, 51);//만족도
		CellRangeAddress row2cols52 = new CellRangeAddress(1, 1, 52, 57);//교육 시간
		sheet.addMergedRegion(row2cols37); sheet.addMergedRegion(row2cols44); sheet.addMergedRegion(row2cols46); sheet.addMergedRegion(row2cols52);
		//2층 교육실적2 끝

		//2층 예산지출 시작
		CellRangeAddress row2cols58 = new CellRangeAddress(1, 1, 58, 69);//예산
		CellRangeAddress row2cols70 = new CellRangeAddress(1, 1, 70, 81);//지출
		sheet.addMergedRegion(row2cols58); sheet.addMergedRegion(row2cols70);
		//2층 예산지출 끝

		cellList.add(row2cols0); cellList.add(row2cols1); cellList.add(row2cols2); cellList.add(row2cols3); cellList.add(row2cols4); cellList.add(row2cols5);
		cellList.add(row2cols6); cellList.add(row2cols7); cellList.add(row2cols8); cellList.add(row2cols9); cellList.add(row2cols11); cellList.add(row2cols12);
		cellList.add(row2cols14); cellList.add(row2cols15); cellList.add(row2cols16); cellList.add(row2cols17); cellList.add(row2cols18); cellList.add(row2cols19);

		cellList.add(row2cols20); cellList.add(row2cols24); cellList.add(row2cols28); cellList.add(row2cols37); cellList.add(row2cols44); cellList.add(row2cols46);
		cellList.add(row2cols52); cellList.add(row2cols58); cellList.add(row2cols70);


		//헤더 3층
		//교육상태 신청인원(명) 신청인원(명) 수료인원(명), 참석율(%), 완성차(명), 1차사(명), 2차사(명), 기타(명), 품질(명), 생산(명), 구매(명), 경영지원(명), 업체평가(명), 안전(명), ESG(명), 기타(명)
		//대표(명) 임원(명) 부장(팀장)(명) 과장/차장(명) 사원/대리(명) 조장/반장(명) 출석률(%) 평가점수(점) 종합 평균 전체(공통) 교육환경 교육지원 교육내용 강사 강사1(시간) 강사1(시간) 강사1(시간)강사1(시간) 강사1(시간) 강사1(시간)
		//예산 총계(원), 부담금/대관비(원), 강사비(원), 교재비(원), 식대(원) 다과비(원) 소모품비(원) 발송비(원) 재료비(원) 집행비(원) 기타(원) 비고(원) 지출(원) 총계(원) 부담금/대관비(원) 강사비(원) 교재비(원) 식대(원) 다과비(원) 소모품비(원) 발송비(원) 재료비(원) 집행비(원) 기타(원) 비고
		row = sheet.createRow(2);
		cell = row.createCell(20); cell.setCellValue("교육상태"); cell.setCellStyle(style_header);//교육상태 20
		cell = row.createCell(21); cell.setCellValue("신청인원(명)"); cell.setCellStyle(style_header);//신청인원(명) 21
		cell = row.createCell(22); cell.setCellValue("수료인원(명)"); cell.setCellStyle(style_header);//수료인원(명) 22
		cell = row.createCell(23); cell.setCellValue("참석율(%)"); cell.setCellStyle(style_header);//참석율(%) 23
		cell = row.createCell(24); cell.setCellValue("완성차(명)"); cell.setCellStyle(style_header);//완성차(명) 24
		cell = row.createCell(25); cell.setCellValue("1차사(명)"); cell.setCellStyle(style_header);//1차사(명) 25
		cell = row.createCell(26); cell.setCellValue("2차사(명)"); cell.setCellStyle(style_header);//2차사(명) 26
		cell = row.createCell(27); cell.setCellValue("기타(명)"); cell.setCellStyle(style_header);//기타(명) 27
		cell = row.createCell(28); cell.setCellValue("품질(명)"); cell.setCellStyle(style_header);//품질(명) 28
		cell = row.createCell(29); cell.setCellValue("R&D(명)"); cell.setCellStyle(style_header);//R&D(명) 29
		cell = row.createCell(30); cell.setCellValue("생산(명)"); cell.setCellStyle(style_header);//생산(명) 30
		cell = row.createCell(31); cell.setCellValue("구매(명)"); cell.setCellStyle(style_header);//구매(명) 31
		cell = row.createCell(32); cell.setCellValue("경영지원(명)"); cell.setCellStyle(style_header);//경영지원(명) 32
		cell = row.createCell(33); cell.setCellValue("업체평가(명)"); cell.setCellStyle(style_header);//업체평가(명) 33
		cell = row.createCell(34); cell.setCellValue("안전(명)"); cell.setCellStyle(style_header);//안전(명) 34
		cell = row.createCell(35); cell.setCellValue("ESG(명)"); cell.setCellStyle(style_header);//ESG(명) 35
		cell = row.createCell(36); cell.setCellValue("기타(명)"); cell.setCellStyle(style_header);//기타(명) 36

		cell = row.createCell(37); cell.setCellValue("대표(명)"); cell.setCellStyle(style_header);//대표(명) 37
		cell = row.createCell(38); cell.setCellValue("임원(명)"); cell.setCellStyle(style_header);//임원(명) 38
		cell = row.createCell(39); cell.setCellValue("부장(팀장)(명)"); cell.setCellStyle(style_header);//부장(팀장)(명) 39
		cell = row.createCell(40); cell.setCellValue("부과장/차장(명)"); cell.setCellStyle(style_header);//부과장/차장(명) 40
		cell = row.createCell(41); cell.setCellValue("사원/대리(명)"); cell.setCellStyle(style_header);//사원/대리(명) 41
		cell = row.createCell(42); cell.setCellValue("조장/반장(명)"); cell.setCellStyle(style_header);//조장/반장(명) 42
		cell = row.createCell(43); cell.setCellValue("기타(명)"); cell.setCellStyle(style_header);//기타(명) 42
		cell = row.createCell(44); cell.setCellValue("출석률(%)"); cell.setCellStyle(style_header);//출석률(%) 43
		cell = row.createCell(45); cell.setCellValue("평가점수(점)"); cell.setCellStyle(style_header);//평가점수(점) 44
		cell = row.createCell(46); cell.setCellValue("종합 평균 "); cell.setCellStyle(style_header);//종합 평균 45
		cell = row.createCell(47); cell.setCellValue("전체(공통)"); cell.setCellStyle(style_header);//전체(공통) 46
		cell = row.createCell(48); cell.setCellValue("교육환경"); cell.setCellStyle(style_header);//교육환경 47
		cell = row.createCell(49); cell.setCellValue("교육지원"); cell.setCellStyle(style_header);//교육지원 48
		cell = row.createCell(50); cell.setCellValue("교육내용"); cell.setCellStyle(style_header);//교육내용 49
		cell = row.createCell(51); cell.setCellValue("강사"); cell.setCellStyle(style_header);//강사 50
		cell = row.createCell(52); cell.setCellValue("강사1(시간)"); cell.setCellStyle(style_header);//강사1(시간) 51
		cell = row.createCell(53); cell.setCellValue("강사2(시간)"); cell.setCellStyle(style_header);//강사2(시간) 52
		cell = row.createCell(54); cell.setCellValue("강사3(시간)"); cell.setCellStyle(style_header);//강사3(시간) 53
		cell = row.createCell(55); cell.setCellValue("강사4(시간)"); cell.setCellStyle(style_header);//강사4(시간) 54
		cell = row.createCell(56); cell.setCellValue("강사5(시간)"); cell.setCellStyle(style_header);//강사5(시간) 55
		cell = row.createCell(57); cell.setCellValue("강사6(시간)"); cell.setCellStyle(style_header);//강사6(시간) 56

		cell = row.createCell(58); cell.setCellValue("예산 총계(원)"); cell.setCellStyle(style_header);//예산 총계(원)
		cell = row.createCell(59); cell.setCellValue("부담금/대관비(원)"); cell.setCellStyle(style_header);//부담금/대관비(원)
		cell = row.createCell(60); cell.setCellValue("강사비(원)"); cell.setCellStyle(style_header);//강사비(원)
		cell = row.createCell(61); cell.setCellValue("교재비(원)"); cell.setCellStyle(style_header);//교재비(원)
		cell = row.createCell(62); cell.setCellValue("식대(원)"); cell.setCellStyle(style_header);//식대(원)
		cell = row.createCell(63); cell.setCellValue("다과비(원)"); cell.setCellStyle(style_header);//다과비(원)
		cell = row.createCell(64); cell.setCellValue("소모품비(원)"); cell.setCellStyle(style_header);//소모품비(원)
		cell = row.createCell(65); cell.setCellValue("발송비(원)"); cell.setCellStyle(style_header);//발송비(원)
		cell = row.createCell(66); cell.setCellValue("재료비(원)"); cell.setCellStyle(style_header);//재료비(원)
		cell = row.createCell(67); cell.setCellValue("집행비(원)"); cell.setCellStyle(style_header);//집행비(원)
		cell = row.createCell(68); cell.setCellValue("기타(원)"); cell.setCellStyle(style_header);//기타(원)
		cell = row.createCell(69); cell.setCellValue("비고"); cell.setCellStyle(style_header);//비고
		cell = row.createCell(70); cell.setCellValue("지출 총계(원)"); cell.setCellStyle(style_header);//지출 총계(원)
		cell = row.createCell(71); cell.setCellValue("부담금/대관비(원)"); cell.setCellStyle(style_header);//부담금/대관비(원)
		cell = row.createCell(72); cell.setCellValue("강사비(원)"); cell.setCellStyle(style_header);//강사비(원)
		cell = row.createCell(73); cell.setCellValue("교재비(원)"); cell.setCellStyle(style_header);//교재비(원)
		cell = row.createCell(74); cell.setCellValue("식대(원)"); cell.setCellStyle(style_header);//식대(원)
		cell = row.createCell(75); cell.setCellValue("다과비(원)"); cell.setCellStyle(style_header);//다과비(원)
		cell = row.createCell(76); cell.setCellValue("소모품비(원)"); cell.setCellStyle(style_header);//소모품비(원)
		cell = row.createCell(77); cell.setCellValue("발송비(원)"); cell.setCellStyle(style_header);//발송비(원)
		cell = row.createCell(78); cell.setCellValue("재료비(원)"); cell.setCellStyle(style_header);//재료비(원)
		cell = row.createCell(79); cell.setCellValue("집행비(원)"); cell.setCellStyle(style_header);//집행비(원)
		cell = row.createCell(80); cell.setCellValue("기타(원)"); cell.setCellStyle(style_header);//기타(원)
		cell = row.createCell(81); cell.setCellValue("비고"); cell.setCellStyle(style_header);//비고

		// 병합된 영역의 각 셀에 스타일을 적용
		for(CellRangeAddress call : cellList){
			for (int i = call.getFirstRow(); i <= call.getLastRow(); i++) {
				Row tempRow = sheet.getRow(i);
				for (int colNum = call.getFirstColumn(); colNum <= call.getLastColumn(); colNum++) {
					Cell tempCel = tempRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					tempCel.setCellStyle(style_header);
				}
			}
		}

		// Body
		List<EBBEpisdExcelDTO> list = eBBEpisdExcelDTO.getList();
		for(EBBEpisdExcelDTO dto: list){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0); cell.setCellValue(dto.getPrntCdNm()); cell.setCellStyle(style_body);//과정분류
			cell = row.createCell(1); cell.setCellValue(dto.getCtgryCdNm()); cell.setCellStyle(style_body);//과정분류2
			cell = row.createCell(2); cell.setCellValue(dto.getNm()); cell.setCellStyle(style_body);//과정명
			cell = row.createCell(3); cell.setCellValue(dto.getStduyMthdCdNm()); cell.setCellStyle(style_body);//학습방식
			cell = row.createCell(4); cell.setCellValue(dto.getStduyTimeCdNm()); cell.setCellStyle(style_body);//학습시간
			cell = row.createCell(5); cell.setCellValue(dto.getCbsnCdNm()); cell.setCellStyle(style_body);//업종
			cell = row.createCell(6); cell.setCellValue(dto.getEpisdYear()); cell.setCellStyle(style_body);//년도
			cell = row.createCell(7); cell.setCellValue(dto.getEpisdOrd()); cell.setCellStyle(style_body);//차수
			cell = row.createCell(8); cell.setCellValue(dto.getStduyDdCdNm()); cell.setCellStyle(style_body);//교육일

			String edctnStrtDtm = (dto.getEdctnStrtDtm().equals("") || dto.getEdctnStrtDtm() == null) ? "-" : COStringUtil.convertDate(dto.getEdctnStrtDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");
			String edctnEndDtm = (dto.getEdctnEndDtm().equals("") || dto.getEdctnEndDtm() == null) ? "-" : COStringUtil.convertDate(dto.getEdctnEndDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");

			cell = row.createCell(9); cell.setCellValue(edctnStrtDtm + " ~ "+ edctnEndDtm); cell.setCellStyle(style_body);//교육기간 시작
			//cell = row.createCell(10); cell.setCellValue(edctnEndDtm); cell.setCellStyle(style_body);//교육기간 종료

			cell = row.createCell(11); cell.setCellValue(dto.getPlaceNm()); cell.setCellStyle(style_body);//교육장소
			cell = row.createCell(12); cell.setCellValue(dto.getCprtnInsttNm()); cell.setCellStyle(style_body);//협력기관
			cell = row.createCell(13); cell.setCellValue(dto.getFxnumCnt()); cell.setCellStyle(style_body);//정원(명)

			//강사 반복문 시작
			cell = row.createCell(14); cell.setCellValue(dto.getIsttrNm1()); cell.setCellStyle(style_body);//강사1
			cell = row.createCell(15); cell.setCellValue(dto.getIsttrNm2()); cell.setCellStyle(style_body);//강사2
			cell = row.createCell(16); cell.setCellValue(dto.getIsttrNm3()); cell.setCellStyle(style_body);//강사3
			cell = row.createCell(17); cell.setCellValue(dto.getIsttrNm4()); cell.setCellStyle(style_body);//강사4
			cell = row.createCell(18); cell.setCellValue(dto.getIsttrNm5()); cell.setCellStyle(style_body);//강사5
			cell = row.createCell(19); cell.setCellValue(dto.getIsttrNm5()); cell.setCellStyle(style_body);//강사6 #
			//강사 반복문 종료

			cell = row.createCell(20); cell.setCellValue(dto.getEdctnStatusNm()); cell.setCellStyle(style_body);//교육상태
			cell = row.createCell(21); cell.setCellValue(dto.getAccsCnt()); cell.setCellStyle(style_body);//신청인원(명)
			cell = row.createCell(22); cell.setCellValue(dto.getCmptnCnt()); cell.setCellStyle(style_body);//수료인원(명)
			cell = row.createCell(23); cell.setCellValue(dto.getAttedPer()); cell.setCellStyle(style_body);//참석율(%)
			cell = row.createCell(24); cell.setCellValue(dto.getC1()); cell.setCellStyle(style_body);//완성차(명) 24
			cell = row.createCell(25); cell.setCellValue(dto.getC2()); cell.setCellStyle(style_body);//1차사(명)
			cell = row.createCell(26); cell.setCellValue(dto.getC3()); cell.setCellStyle(style_body);//2차사(명)
			cell = row.createCell(27); cell.setCellValue(dto.getC4()); cell.setCellStyle(style_body);//기타(명)

			cell = row.createCell(28); cell.setCellValue(dto.getT1()); cell.setCellStyle(style_body);//품질(명)
			cell = row.createCell(29); cell.setCellValue(dto.getT2()); cell.setCellStyle(style_body);//RnD(명)
			cell = row.createCell(30); cell.setCellValue(dto.getT3()); cell.setCellStyle(style_body);//생산(명)
			cell = row.createCell(31); cell.setCellValue(dto.getT4()); cell.setCellStyle(style_body);//구매(명)
			cell = row.createCell(32); cell.setCellValue(dto.getT5()); cell.setCellStyle(style_body);//경영지원(명)
			cell = row.createCell(33); cell.setCellValue(dto.getT6()); cell.setCellStyle(style_body);//업체평가(명)
			cell = row.createCell(34); cell.setCellValue(dto.getT7()); cell.setCellStyle(style_body);//안전(명)
			cell = row.createCell(35); cell.setCellValue(dto.getT8()); cell.setCellStyle(style_body);//ESG(명)
			cell = row.createCell(36); cell.setCellValue(dto.getT9()); cell.setCellStyle(style_body);//기타(명)--36
			cell = row.createCell(37); cell.setCellValue(dto.getA1()); cell.setCellStyle(style_body);//대표(명)
			cell = row.createCell(38); cell.setCellValue(dto.getA2()); cell.setCellStyle(style_body);//임원(명)
			cell = row.createCell(39); cell.setCellValue(dto.getA3()); cell.setCellStyle(style_body);//부장(팀장)(명)
			cell = row.createCell(40); cell.setCellValue(dto.getA4()); cell.setCellStyle(style_body);//과장/차장(명)
			cell = row.createCell(41); cell.setCellValue(dto.getA5()); cell.setCellStyle(style_body);//사원/대리(명)
			cell = row.createCell(42); cell.setCellValue(dto.getA6()); cell.setCellStyle(style_body);//조장/반장(명) 42
			cell = row.createCell(43); cell.setCellValue(dto.getA7()); cell.setCellStyle(style_body);//직급별 인원 - 기타 43
			cell = row.createCell(44); cell.setCellValue(dto.getA11()); cell.setCellStyle(style_body);//출석률(%) 43
			cell = row.createCell(45); cell.setCellValue(dto.getAvgScore()); cell.setCellStyle(style_body);//평가점수(점)
			cell = row.createCell(46); cell.setCellValue(dto.getA12()); cell.setCellStyle(style_body);//종합 평균 ######
			cell = row.createCell(47); cell.setCellValue(dto.getA13()); cell.setCellStyle(style_body);//전체(공통) ######
			cell = row.createCell(48); cell.setCellValue(dto.getA14()); cell.setCellStyle(style_body);//교육환경 ######
			cell = row.createCell(49); cell.setCellValue(dto.getA15()); cell.setCellStyle(style_body);//교육지원 ######
			cell = row.createCell(50); cell.setCellValue(dto.getA16()); cell.setCellStyle(style_body);//교육내용 ######
			cell = row.createCell(51); cell.setCellValue(dto.getA17()); cell.setCellStyle(style_body);//강사 ######
			cell = row.createCell(52); cell.setCellValue(dto.getPmtC1()); cell.setCellStyle(style_body);//강사1(시간)
			cell = row.createCell(53); cell.setCellValue(dto.getPmtC2()); cell.setCellStyle(style_body);//강사2(시간)
			cell = row.createCell(54); cell.setCellValue(dto.getPmtC3()); cell.setCellStyle(style_body);//강사3(시간)
			cell = row.createCell(55); cell.setCellValue(dto.getPmtC4()); cell.setCellStyle(style_body);//강사4(시간)
			cell = row.createCell(56); cell.setCellValue(dto.getPmtC5()); cell.setCellStyle(style_body);//강사5(시간)
			cell = row.createCell(57); cell.setCellValue(dto.getPmtC6()); cell.setCellStyle(style_body);//강사6(시간) 56

			cell = row.createCell(58); cell.setCellValue(dto.getPmtA0()); cell.setCellStyle(style_body);//예산 총계(원)
			cell = row.createCell(59); cell.setCellValue(dto.getPmtA1()); cell.setCellStyle(style_body);//부담금/대관비(원)
			cell = row.createCell(60); cell.setCellValue(dto.getPmtA2()); cell.setCellStyle(style_body);//강사비(원)
			cell = row.createCell(61); cell.setCellValue(dto.getPmtA3()); cell.setCellStyle(style_body);//교재비(원)
			cell = row.createCell(62); cell.setCellValue(dto.getPmtA4()); cell.setCellStyle(style_body);//식대(원)
			cell = row.createCell(63); cell.setCellValue(dto.getPmtA5()); cell.setCellStyle(style_body);//다과비(원)
			cell = row.createCell(64); cell.setCellValue(dto.getPmtA6()); cell.setCellStyle(style_body);//소모품비(원)
			cell = row.createCell(65); cell.setCellValue(dto.getPmtA7()); cell.setCellStyle(style_body);//발송비(원)
			cell = row.createCell(66); cell.setCellValue(dto.getPmtA8()); cell.setCellStyle(style_body);//재료비(원)
			cell = row.createCell(67); cell.setCellValue(dto.getPmtA9()); cell.setCellStyle(style_body);//집행비(원)
			cell = row.createCell(68); cell.setCellValue(dto.getPmtA10()); cell.setCellStyle(style_body);//기타(원)
			cell = row.createCell(69); cell.setCellValue(dto.getPmtA11()); cell.setCellStyle(style_body);//비고(원)

			cell = row.createCell(70); cell.setCellValue(dto.getPmtB0()); cell.setCellStyle(style_body);//지출총계(원)
			cell = row.createCell(71); cell.setCellValue(dto.getPmtB1()); cell.setCellStyle(style_body);//부담금/대관비(원)
			cell = row.createCell(72); cell.setCellValue(dto.getPmtB2()); cell.setCellStyle(style_body);//강사비(원)
			cell = row.createCell(73); cell.setCellValue(dto.getPmtB3()); cell.setCellStyle(style_body);//교재비(원)
			cell = row.createCell(74); cell.setCellValue(dto.getPmtB4()); cell.setCellStyle(style_body);//식대(원)
			cell = row.createCell(75); cell.setCellValue(dto.getPmtB5()); cell.setCellStyle(style_body);//다과비(원)
			cell = row.createCell(76); cell.setCellValue(dto.getPmtB6()); cell.setCellStyle(style_body);//소모품비(원)
			cell = row.createCell(77); cell.setCellValue(dto.getPmtB7()); cell.setCellStyle(style_body);//발송비(원)
			cell = row.createCell(78); cell.setCellValue(dto.getPmtB8()); cell.setCellStyle(style_body);//재료비(원)
			cell = row.createCell(79); cell.setCellValue(dto.getPmtB9()); cell.setCellStyle(style_body);//집행비(원)
			cell = row.createCell(80); cell.setCellValue(dto.getPmtB10()); cell.setCellStyle(style_body);//기타(원)
			cell = row.createCell(81); cell.setCellValue(dto.getPmtB11()); cell.setCellStyle(style_body);//비고



			CellRangeAddress row3cols9 = new CellRangeAddress(rowNum-1, rowNum-1, 9, 10);//교육기간 시작~종료
			sheet.addMergedRegion(row3cols9);

			for (int i = row3cols9.getFirstRow(); i <= row3cols9.getLastRow(); i++) {
				Row tempRow = sheet.getRow(i);
				for (int colNum = row3cols9.getFirstColumn(); colNum <= row3cols9.getLastColumn(); colNum++) {
					Cell tempCel = tempRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					tempCel.setCellStyle(style_body);
				}
			}

			//셀 너비 자동맞춤 시작
			for(int j=0; j<=81;j++){
				if(j == 10) continue;//병합된 셀이므로 패스
				sheet.autoSizeColumn(j);

				//교육기간 시작~종료
				if(j == 9){
					sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+1200 );
				//교육장소, 협력기관, 예산비고, 지출비고
				}else if(j == 11 || j == 12 || j == 69 || j == 81){
					sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+2500 );
				//그외 일반적인 셀들
				}else{
					sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+1024 );
				}
			}

		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

		//컨텐츠 타입 및 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_교육차수관리 목록_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 교육차수관리 목록");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.EBBEPisdServiceImpl");
		pCoSystemLogDTO.setFncNm("selectEpisdList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(eBBEpisdExcelDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}

	/**
	 * 교육차수관리-> 참여자 목록 호출 엑셀
	 */
	public void excelDownload2(EBBPtcptDTO eBBPtcptDTO, HttpServletResponse response) throws Exception{

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle style_header = workbook.createCellStyle();
		XSSFCellStyle style_body = workbook.createCellStyle();
		Sheet sheet = workbook.createSheet();

		Row row = null;
		Cell cell = null;
		int rowNum = 1;

		//Cell Alignment 지정
		style_header.setAlignment(HorizontalAlignment.CENTER);
		style_header.setVerticalAlignment(VerticalAlignment.CENTER);
		style_body.setAlignment(HorizontalAlignment.CENTER);
		style_body.setVerticalAlignment(VerticalAlignment.CENTER);

		// Border Color 지정
		style_header.setBorderTop(BorderStyle.THIN);
		style_header.setBorderLeft(BorderStyle.THIN);
		style_header.setBorderRight(BorderStyle.THIN);
		style_header.setBorderBottom(BorderStyle.THIN);
		style_body.setBorderTop(BorderStyle.THIN);
		style_body.setBorderLeft(BorderStyle.THIN);
		style_body.setBorderRight(BorderStyle.THIN);
		style_body.setBorderBottom(BorderStyle.THIN);

		//BackGround Color 지정
		style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//0~15 기본정보


		//셀 병합 관련해서 만든 리스트
		List<CellRangeAddress> cellList = new ArrayList<>();

		//헤더 1층 기본정보, 교육실적, 교육실적2, 예산/지출
		row = sheet.createRow(0);

		cell = row.createCell(0); cell.setCellValue("번호"); cell.setCellStyle(style_header);
		cell = row.createCell(1); cell.setCellValue("아이디"); cell.setCellStyle(style_header);
		cell = row.createCell(2); cell.setCellValue("이름"); cell.setCellStyle(style_header);
		cell = row.createCell(3); cell.setCellValue("부품사명"); cell.setCellStyle(style_header);
		cell = row.createCell(4); cell.setCellValue("구분"); cell.setCellStyle(style_header);
		cell = row.createCell(5); cell.setCellValue("사업자등록번호"); cell.setCellStyle(style_header);
		cell = row.createCell(6); cell.setCellValue("부서"); cell.setCellStyle(style_header);
		cell = row.createCell(7); cell.setCellValue("직급"); cell.setCellStyle(style_header);
		cell = row.createCell(8); cell.setCellValue("휴대폰번호"); cell.setCellStyle(style_header);
		cell = row.createCell(9); cell.setCellValue("이메일"); cell.setCellStyle(style_header);
		cell = row.createCell(10); cell.setCellValue("가입일"); cell.setCellStyle(style_header);
		cell = row.createCell(11); cell.setCellValue("교육신청일"); cell.setCellStyle(style_header);
		cell = row.createCell(12); cell.setCellValue("교육상태"); cell.setCellStyle(style_header);
		cell = row.createCell(13); cell.setCellValue("출석"); cell.setCellStyle(style_header);
		cell = row.createCell(14); cell.setCellValue("수강"); cell.setCellStyle(style_header);
		cell = row.createCell(15); cell.setCellValue("평가"); cell.setCellStyle(style_header);
		cell = row.createCell(16); cell.setCellValue("수료"); cell.setCellStyle(style_header);


		// Body
		List<EBBPtcptDTO> list = eBBPtcptDTO.getPtcptList();
		for(int i=0; i<list.size(); i++){
			EBBPtcptDTO dto = list.get(i);


			row = sheet.createRow(rowNum++);
			cell = row.createCell(0); cell.setCellValue(i+1); cell.setCellStyle(style_body);//번호
			cell = row.createCell(1); cell.setCellValue(dto.getId()); cell.setCellStyle(style_body);//아이디
			cell = row.createCell(2); cell.setCellValue(dto.getName()); cell.setCellStyle(style_body);//이름
			cell = row.createCell(3); cell.setCellValue(dto.getCmpnNm()); cell.setCellStyle(style_body);//부품사명
			cell = row.createCell(4); cell.setCellValue(dto.getCtgryNm()); cell.setCellStyle(style_body);//구분
			cell = row.createCell(5); cell.setCellValue(COStringUtil.bsnmNoConvert(dto.getPtcptBsnmNo())); cell.setCellStyle(style_body);//사업자등록번호

			String deptCdNm = dto.getDeptCdNm();
			String deptDtlNm = (dto.getDeptDtlNm() == "") ? "" : dto.getDeptDtlNm();
			cell = row.createCell(6); cell.setCellValue(COStringUtil.getHtmlStrCnvr(deptCdNm)+"("+deptDtlNm+")"); cell.setCellStyle(style_body);//부서

			cell = row.createCell(7); cell.setCellValue(dto.getPstnCdNm()); cell.setCellStyle(style_body);//직급
			cell = row.createCell(8); cell.setCellValue(dto.getHpNo()); cell.setCellStyle(style_body);//휴대폰번호

			cell = row.createCell(9); cell.setCellValue(dto.getEmail()); cell.setCellStyle(style_body);//이메일

			String regDtm = (dto.getRegDtm() == null || dto.getRegDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getRegDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");
			cell = row.createCell(10); cell.setCellValue(regDtm); cell.setCellStyle(style_body);//가입일


			String eduDtm = (dto.getEduDtm() == null || dto.getEduDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getEduDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");
			cell = row.createCell(11); cell.setCellValue(eduDtm); cell.setCellStyle(style_body);//교육신청일
			cell = row.createCell(12); cell.setCellValue(dto.getEduStat()); cell.setCellStyle(style_body);//교육상태
			cell = row.createCell(13); cell.setCellValue(dto.getEduAtndc()+"%"); cell.setCellStyle(style_body);//출석
			cell = row.createCell(14); cell.setCellValue(""); cell.setCellStyle(style_body);//수강
			String examScore = (dto.getExamScore() == null || dto.getExamScore().equals("")) ? "0" : String.valueOf(dto.getExamScore());
			cell = row.createCell(15); cell.setCellValue(examScore+"점"); cell.setCellStyle(style_body);//평가

			String cmptnYn = (dto.getCmptnYn().equals("Y")) ?"수료" : "미수료";
			cell = row.createCell(16); cell.setCellValue(cmptnYn); cell.setCellStyle(style_body);//수료

			//셀 너비 자동맞춤 시작
			for(int j=0; j<=16;j++){
				sheet.autoSizeColumn(j);
				if(j == 3){
					sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+2024 );
				}else{
					sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+1024 );
				}

			}

		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

		//컨텐츠 타입 및 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_교육차수관리 참여자 목록_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 교육차수관리 참여자 목록");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.EBBEPisdServiceImpl");
		pCoSystemLogDTO.setFncNm("selectEpisdPtcptList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(eBBPtcptDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}







	/**
	 * 교육차수관리-> 참여자 출석부 목록 호출 엑셀
	 */
	public void excelDownload3(List<EBBPtcptDTO> tableAtndcList, EBBPtcptDTO eBBPtcptDTO, HttpServletResponse response) throws Exception{

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle style_header = workbook.createCellStyle();
		XSSFCellStyle style_body = workbook.createCellStyle();
		Sheet sheet = workbook.createSheet();

		Row row = null;
		Cell cell = null;
		int rowNum = 2;

		//Cell Alignment 지정
		style_header.setAlignment(HorizontalAlignment.CENTER);
		style_header.setVerticalAlignment(VerticalAlignment.CENTER);
		style_body.setAlignment(HorizontalAlignment.CENTER);
		style_body.setVerticalAlignment(VerticalAlignment.CENTER);

		// Border Color 지정
		style_header.setBorderTop(BorderStyle.THIN);
		style_header.setBorderLeft(BorderStyle.THIN);
		style_header.setBorderRight(BorderStyle.THIN);
		style_header.setBorderBottom(BorderStyle.THIN);
		style_body.setBorderTop(BorderStyle.THIN);
		style_body.setBorderLeft(BorderStyle.THIN);
		style_body.setBorderRight(BorderStyle.THIN);
		style_body.setBorderBottom(BorderStyle.THIN);

		//BackGround Color 지정
		style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//0~10 기본정보


		//셀 병합 관련해서 만든 리스트
		List<CellRangeAddress> cellList = new ArrayList<>();

		//헤더 1층 기본정보들
		row = sheet.createRow(0);

		cell = row.createCell(0); cell.setCellValue("번호"); cell.setCellStyle(style_header);
		cell = row.createCell(1); cell.setCellValue("아이디"); cell.setCellStyle(style_header);
		cell = row.createCell(2); cell.setCellValue("이름"); cell.setCellStyle(style_header);
		cell = row.createCell(3); cell.setCellValue("부품사명"); cell.setCellStyle(style_header);
		cell = row.createCell(4); cell.setCellValue("구분"); cell.setCellStyle(style_header);
		cell = row.createCell(5); cell.setCellValue("사업자등록번호"); cell.setCellStyle(style_header);
		cell = row.createCell(6); cell.setCellValue("부서"); cell.setCellStyle(style_header);
		cell = row.createCell(7); cell.setCellValue("직급"); cell.setCellStyle(style_header);
		cell = row.createCell(8); cell.setCellValue("휴대폰번호"); cell.setCellStyle(style_header);
		cell = row.createCell(9); cell.setCellValue("이메일"); cell.setCellStyle(style_header);
		cell = row.createCell(10); cell.setCellValue("교육신청일"); cell.setCellStyle(style_header);



		/*여기부터 출석부 시작*/
		int k = 11;
		for(EBBPtcptDTO dto : tableAtndcList){
			String edctnDt= (dto.getEdctnDt() !=null || dto.getEdctnDt().equals("")) ? "" : dto.getEdctnDt();
			cell = row.createCell(k); cell.setCellValue(edctnDt); cell.setCellStyle(style_header);
			k = k+3;
			//CellRangeAddress row1cols0 = new CellRangeAddress(0, 1, 0, 0);
		}

		/*여기부터 출석부 끝*/




		//헤더 2층 출석일자 데이터 출석.퇴실,비고의 반복
		row = sheet.createRow(1);

		int m = 11;
		for(EBBPtcptDTO dto : tableAtndcList){

			String atndcHour = (dto.getAtndcDtm() == null || dto.getAtndcDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getAtndcDtm(), "yyyy-MM-dd HH:mm:ss", "HH:mm", "-");
			String lvgrmHour = (dto.getLvgrmDtm() == null || dto.getLvgrmDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getLvgrmDtm(), "yyyy-MM-dd HH:mm:ss", "HH:mm", "-");

			cell = row.createCell(m); cell.setCellValue("출석"); cell.setCellStyle(style_header);
			cell = row.createCell(m+1); cell.setCellValue("퇴근"); cell.setCellStyle(style_header);
			cell = row.createCell(m+2); cell.setCellValue("비고"); cell.setCellStyle(style_header);

			CellRangeAddress row1cols10 = new CellRangeAddress(0, 0, m, m+2);//
			sheet.addMergedRegion(row1cols10);
			for (int i = row1cols10.getFirstRow(); i <= row1cols10.getLastRow(); i++) {
				Row tempRow = sheet.getRow(i);
				for (int colNum = row1cols10.getFirstColumn(); colNum <= row1cols10.getLastColumn(); colNum++) {
					Cell tempCel = tempRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					tempCel.setCellStyle(style_header);
				}
			}

			m= m+3;
		}

		CellRangeAddress row1cols0 = new CellRangeAddress(0, 1, 0, 0);//번호
		CellRangeAddress row1cols1 = new CellRangeAddress(0, 1, 1, 1);//아이디
		CellRangeAddress row1cols2 = new CellRangeAddress(0, 1, 2, 2);//이름
		CellRangeAddress row1cols3 = new CellRangeAddress(0, 1, 3, 3);//부품사명
		CellRangeAddress row1cols4 = new CellRangeAddress(0, 1, 4, 4);//구분
		CellRangeAddress row1cols5 = new CellRangeAddress(0, 1, 5, 5);//사업자등록번호
		CellRangeAddress row1cols6 = new CellRangeAddress(0, 1, 6, 6);//부서
		CellRangeAddress row1cols7 = new CellRangeAddress(0, 1, 7, 7);//직급
		CellRangeAddress row1cols8 = new CellRangeAddress(0, 1, 8, 8);//휴대폰번호
		CellRangeAddress row1cols9 = new CellRangeAddress(0, 1, 9, 9);//이메일
		CellRangeAddress row1cols10 = new CellRangeAddress(0, 1, 10, 10);//교육신청일

		sheet.addMergedRegion(row1cols0);	sheet.addMergedRegion(row1cols1);	sheet.addMergedRegion(row1cols2);	sheet.addMergedRegion(row1cols3);
		sheet.addMergedRegion(row1cols4);	sheet.addMergedRegion(row1cols5);	sheet.addMergedRegion(row1cols6);	sheet.addMergedRegion(row1cols7);
		sheet.addMergedRegion(row1cols8);	sheet.addMergedRegion(row1cols9);	sheet.addMergedRegion(row1cols10);
		cellList.add(row1cols0);cellList.add(row1cols1);cellList.add(row1cols2);cellList.add(row1cols3);cellList.add(row1cols4);cellList.add(row1cols5);
		cellList.add(row1cols6);cellList.add(row1cols7);cellList.add(row1cols8);cellList.add(row1cols9);cellList.add(row1cols10);
		//여기 위는 rowspan 2로 통일

		for(CellRangeAddress call : cellList){
			for (int i = call.getFirstRow(); i <= call.getLastRow(); i++) {
				Row tempRow = sheet.getRow(i);
				for (int colNum = call.getFirstColumn(); colNum <= call.getLastColumn(); colNum++) {
					Cell tempCel = tempRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					tempCel.setCellStyle(style_header);
				}
			}
		}

		// Body
		List<EBBPtcptDTO> list = eBBPtcptDTO.getPtcptList();
		for(int i=0; i<list.size(); i++){
			row = sheet.createRow(rowNum++);

			EBBPtcptDTO dto = list.get(i);

			cell = row.createCell(0); cell.setCellValue(i+1); cell.setCellStyle(style_body);//번호
			cell = row.createCell(1); cell.setCellValue(dto.getId()); cell.setCellStyle(style_body);//아이디
			cell = row.createCell(2); cell.setCellValue(dto.getName()); cell.setCellStyle(style_body);//이름
			cell = row.createCell(3); cell.setCellValue(dto.getCmpnNm()); cell.setCellStyle(style_body);//부품사명
			cell = row.createCell(4); cell.setCellValue(dto.getCtgryNm()); cell.setCellStyle(style_body);//구분
			cell = row.createCell(5); cell.setCellValue(COStringUtil.bsnmNoConvert(dto.getPtcptBsnmNo())); cell.setCellStyle(style_body);//사업자등록번호

			String deptCdNm = dto.getDeptCdNm();
			String deptDtlNm = (dto.getDeptDtlNm() == "") ? "" : dto.getDeptDtlNm();
			cell = row.createCell(6); cell.setCellValue(COStringUtil.getHtmlStrCnvr(deptCdNm)+"("+deptDtlNm+")"); cell.setCellStyle(style_body);//부서
			cell = row.createCell(7); cell.setCellValue(dto.getPstnCdNm()); cell.setCellStyle(style_body);//직급
			cell = row.createCell(8); cell.setCellValue(dto.getHpNo()); cell.setCellStyle(style_body);//휴대폰번호
			cell = row.createCell(9); cell.setCellValue(dto.getEmail()); cell.setCellStyle(style_body);//이메일
			String eduDtm = (dto.getEduDtm() == null || dto.getEduDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getEduDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");

			cell = row.createCell(10); cell.setCellValue(eduDtm); cell.setCellStyle(style_body);//교육신청일


			/*여기부터 출석부 시작*/
			int n = 11;
			for(EBBPtcptDTO atndcDto : dto.getAtndcList()){

				String atndcHour = (atndcDto.getAtndcDtm() == null || atndcDto.getAtndcDtm().equals("")) ? "-" : COStringUtil.convertDate(atndcDto.getAtndcDtm(), "yyyy-MM-dd HH:mm:ss", "HH:mm", "-");
				String lvgrmHour = (atndcDto.getLvgrmDtm() == null || atndcDto.getLvgrmDtm().equals("")) ? "-" : COStringUtil.convertDate(atndcDto.getLvgrmDtm(), "yyyy-MM-dd HH:mm:ss", "HH:mm", "-");

				cell = row.createCell(n); cell.setCellValue(atndcHour); cell.setCellStyle(style_body);
				cell = row.createCell(n+1); cell.setCellValue(lvgrmHour); cell.setCellStyle(style_body);
				cell = row.createCell(n+2); cell.setCellValue(atndcDto.getEtcNm()); cell.setCellStyle(style_body);

				n= n+3;
			}

		}

		for(int j=0; j<=11;j++){
			sheet.autoSizeColumn(j);
			if(j == 5){
				sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+1524 );
			}else if(j == 9){
				sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+2024 );
			}else{
				sheet.setColumnWidth(j, (sheet.getColumnWidth(j))+1024 );
			}

		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

		//컨텐츠 타입 및 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_교육차수관리 참여자 출석부 목록_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 교육차수관리 참여자 출석부 목록");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.EBBEPisdServiceImpl");
		pCoSystemLogDTO.setFncNm("selectPtcptAtndcList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(eBBPtcptDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}
}