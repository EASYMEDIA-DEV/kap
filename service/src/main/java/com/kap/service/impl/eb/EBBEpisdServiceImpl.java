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
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



		dto.setPtcptList(ptcptList);
		dto.setTotalCount(ptcptCnt);
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



		return dto;
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
	* 교육차수 신청자(참여) 출석 상세 생성
	* */
	public void setAtndcList(EBBPtcptDTO eBBPtcptDTO){

		try {
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


			List<EBBPtcptDTO> atndcDtoList = new ArrayList<>();
			// 반복문을 통해 날짜 출력
			while (!startDate.isAfter(endDate)) {
				EBBPtcptDTO atndcDto = new EBBPtcptDTO();

				atndcDto.setPtcptSeq(eBBPtcptDTO.getPtcptSeq());
				atndcDto.setEdctnDt(String.valueOf(startDate));
				atndcDto.setAtndcDtm(null);
				atndcDto.setLvgrmDtm(null);
				atndcDto.setRegId(eBBPtcptDTO.getRegId());
				atndcDto.setRegIp(eBBPtcptDTO.getRegIp());
				atndcDto.setModId(eBBPtcptDTO.getModId());
				atndcDto.setModIp(eBBPtcptDTO.getModIp());
				atndcDtoList.add(atndcDto);

				startDate = startDate.plusDays(1); // 다음 날짜로 이동
			}
			//마지막날은 while에서 안되서서 따로 추가해줌
			if(startDate.isAfter(endDate)){
				EBBPtcptDTO atndcDto = new EBBPtcptDTO();

				atndcDto.setPtcptSeq(eBBPtcptDTO.getPtcptSeq());
				atndcDto.setEdctnDt(String.valueOf(startDate));
				atndcDto.setAtndcDtm(null);
				atndcDto.setLvgrmDtm(null);
				atndcDto.setRegId(eBBPtcptDTO.getRegId());
				atndcDto.setRegIp(eBBPtcptDTO.getRegIp());
				atndcDto.setModId(eBBPtcptDTO.getModId());
				atndcDto.setModIp(eBBPtcptDTO.getModIp());
				atndcDto.setEdctnDt(String.valueOf(startDate));
				atndcDtoList.add(atndcDto);
			}

			eBBPtcptDTO.setPtcptList(atndcDtoList);
			eBBEpisdMapper.insertAtndcList(eBBPtcptDTO);
		}catch (Exception e){
			System.out.println("e = " + e);
		}

	}









}