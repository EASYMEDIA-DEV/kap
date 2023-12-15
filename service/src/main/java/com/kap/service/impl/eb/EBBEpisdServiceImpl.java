package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.eb.eba.EBACouseDTO;
import com.kap.core.dto.eb.ebb.*;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.service.*;
import com.kap.service.dao.COFileMapper;
import com.kap.service.dao.eb.EBBEpisdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
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
import java.util.Date;
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

	/* 교육차수 - 교육참여마스터 시퀀스 */
	private final EgovIdGnrService edctnPtcptSeqIdgen;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;





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

		//예산지출 상세 호출

		List<EBBBdgetDTO> bdgetList = new ArrayList();
		EBBBdgetDTO bdgetDto = new EBBBdgetDTO();
		if(ebbDto !=null) {
			bdgetDto.setEdctnSeq(ebbDto.getEdctnSeq());
			bdgetDto.setEpisdOrd(ebbDto.getEpisdOrd());
			bdgetDto.setEpisdYear(ebbDto.getEpisdYear());
			bdgetList = eBBEpisdMapper.selectBdgetDtlList(bdgetDto);
		}


		//만족도결과 호출
		EBBSrvRstDTO srvRstDtl = eBBEpisdMapper.selectEpisdSrvRstDtl(ebbDto);

		map.put("rtnData", ebbDto);//교육차수 상세
		map.put("roomDto", roomDto);//교육장정보
		map.put("lctrDtoList", lctrDtoList);//온라인강의 목록
		map.put("isttrList", isttrList);//강사 목록
		map.put("bdgetList", bdgetList);//예산지출내역 목록
		map.put("srvRstDtl", srvRstDtl);//설문 상세



		return map;
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
			setAtndcList(eBBPtcptDTO);//교육참여 출석 상세 목록을 등록한다.

			eBBPtcptDTO.setRegStat("S");
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
				this.setAtndcList(nextPtcptDto);//교육참여 출석 상세 목록을 등록한다.


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

		// 시작 날짜와 종료 날짜 설정
		String startDateString = getEdctnStrtDtm.substring(0, 10);
		String endDateString = getEdctnEndDtm.substring(0, 10);

		// 시작 날짜와 종료 날짜를 LocalDate 객체로 변환
		LocalDate startDate = LocalDate.parse(startDateString);
		LocalDate endDate = LocalDate.parse(endDateString);

		// 날짜 출력 형식 지정
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		System.out.println("@@ startDate = " + startDate);
		System.out.println("@@ endDate = " + endDate);

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

			System.out.println("@@@startDate = " + startDate);

			startDate = startDate.plusDays(1); // 다음 날짜로 이동
		}
		//마지막날은 while에서 안되서서 따로 추가해줌
		/*if(startDate.isAfter(endDate)){
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
			atndcDto.setEdctnDt(String.valueOf(startDate));
			atndcDtoList.add(atndcDto);
		}*/

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
	public void excelDownload1(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception{

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCellStyle style_header = workbook.createCellStyle();
		XSSFCellStyle style_body = workbook.createCellStyle();
		Sheet sheet = workbook.createSheet();

		Row row = null;
		Cell cell = null;
		int rowNum = 0;

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


		//헤더 1층 기본정보, 교육실적, 교육실적2, 예산/지출
		row = sheet.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("기본정보");
		cell.setCellStyle(style_header);

		cell = row.createCell(1);
		cell.setCellValue("기본정보1");
		cell.setCellStyle(style_header);

		cell = row.createCell(20);
		cell.setCellValue("교육실적");
		cell.setCellStyle(style_header);
		cell = row.createCell(57);
		cell.setCellValue("예산/지출");
		cell.setCellStyle(style_header);

		// Header
		/*row = sheet.createRow(rowNum++);


		cell = row.createCell(0);
		cell.setCellValue("번호");
		cell.setCellStyle(style_header);

		cell = row.createCell(1);
		cell.setCellValue("아이디");
		cell.setCellStyle(style_header);

		cell = row.createCell(2);
		cell.setCellValue("이름");
		cell.setCellStyle(style_header);

		cell = row.createCell(3);
		cell.setCellValue("부서");
		cell.setCellStyle(style_header);

		cell = row.createCell(4);
		cell.setCellValue("계정상태");
		cell.setCellStyle(style_header);

		cell = row.createCell(5);
		cell.setCellValue("최종접속일");
		cell.setCellStyle(style_header);*/

		// Body
		List<EBBEpisdDTO> list = eBBEpisdDTO.getList();
		/*for (int i=0; i<list.size(); i++) {
			row = sheet.createRow(rowNum++);

			//번호
			cell = row.createCell(0);
			cell.setCellValue(eBBEpisdDTO.getTotalCount() - i);
			cell.setCellStyle(style_body);

			//아이디
			cell = row.createCell(1);
			cell.setCellValue(list.get(i).getId());
			cell.setCellStyle(style_body);

			//이름
			cell = row.createCell(2);
			cell.setCellValue(list.get(i).getName());
			cell.setCellStyle(style_body);

			//부서
			cell = row.createCell(3);
			cell.setCellValue(list.get(i).getDeptNm());
			cell.setCellStyle(style_body);

			//권한
		*//*	cell = row.createCell(4);
			cell.setCellValue(list.get(i).getAuthCdNm());
			cell.setCellStyle(style_body);*//*

			//계정상태
			cell = row.createCell(4);
			cell.setCellValue("Y".equals(list.get(i).getUseYn()) ? "활성" : "비활성");
			cell.setCellStyle(style_body);

			//최종접속일
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getLastLgnDtm() == null ? "-" : list.get(i).getLastLgnDtm().substring(0, list.get(i).getLastLgnDtm().lastIndexOf(":")));
			cell.setCellStyle(style_body);

		}*/

		// 열 너비 설정
       /* for(int i =0; i < 8; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 800));
        }*/

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
		pCoSystemLogDTO.setRsn(eBBEpisdDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}
}