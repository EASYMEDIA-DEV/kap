package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.common.utility.COWebUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.EBBBdgetDTO;
import com.kap.core.dto.eb.ebb.EBBisttrDTO;
import com.kap.core.dto.eb.ebf.EBFEduRoomDetailDTO;
import com.kap.core.dto.eb.ebi.EBINonMemberDTO;
import com.kap.service.*;
import com.kap.service.dao.eb.EBINonMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 비회원 교육 과정 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: EBINonMemberServiceImpl.java
 * @Description		: 비회원 교육 과정 관리 관리를 위한 ServiceImpl
 * @author 장두석
 * @since 2023.12.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.15		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBINonMemberServiceImpl implements EBINonMemberService {

	//DAO
	private final EBINonMemberMapper eBINonMemberMapper;

	//교육장 서비스
	private final EBFEduRoomService eBFEduRoomService;

	//파일 서비스
	private final COFileService cOFileService;

	/* 비회원 교육 과정 -  마스터 시퀀스 */
	private final EgovIdGnrService nmbEdctnPrcsSeqIdgen;

	/* 비회원 교육 과정 - 교육 참여 마스터 시퀀스 */
	private final EgovIdGnrService nmbEdctnPtcptSeqIdgen;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;



	/**
	 *  비회원 교육 과정 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberList(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pEBINonMemberDTO.getPageIndex());
		page.setRecordCountPerPage(pEBINonMemberDTO.getListRowSize());

		page.setPageSize(pEBINonMemberDTO.getPageRowSize());

		pEBINonMemberDTO.setFirstIndex( page.getFirstRecordIndex() );
		pEBINonMemberDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pEBINonMemberDTO.setList( eBINonMemberMapper.selectNonMemberList(pEBINonMemberDTO) );
		pEBINonMemberDTO.setTotalCount( eBINonMemberMapper.selectNonMemberListCnt(pEBINonMemberDTO) );

		return pEBINonMemberDTO;
	}

	/**
	 *  비회원 교육 과정 엑셀 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberExcelList(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pEBINonMemberDTO.getPageIndex());
		page.setRecordCountPerPage(pEBINonMemberDTO.getListRowSize());

		page.setPageSize(pEBINonMemberDTO.getPageRowSize());

		pEBINonMemberDTO.setFirstIndex( page.getFirstRecordIndex() );
		pEBINonMemberDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pEBINonMemberDTO.setList( eBINonMemberMapper.selectNonMemberExcelList(pEBINonMemberDTO) );
		pEBINonMemberDTO.setTotalCount( eBINonMemberMapper.selectNonMemberListCnt(pEBINonMemberDTO) );

		return pEBINonMemberDTO;
	}

	/**
	 * 비회원 교육 과정 상세 조회
	 */
	public HashMap<String, Object> selectNonMemberDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		HashMap<String, Object> map = new HashMap();

		EBINonMemberDTO ebbDto = eBINonMemberMapper.selectNonMemberDtl(pEBINonMemberDTO);

		//교육 신청 페이지 진입 시간
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentDateTime = LocalDateTime.now();
		String formattedDateTime = currentDateTime.format(formatter);
		ebbDto.setApplyDateTime(formattedDateTime);

		EBFEduRoomDetailDTO roomDto = new EBFEduRoomDetailDTO();

		if(ebbDto !=null && ebbDto.getPlaceSeq() !=null){
			roomDto.setDetailsKey(String.valueOf(ebbDto.getPlaceSeq()));
			roomDto = eBFEduRoomService.selectEduRoomDtl(roomDto);
		}

		//강사관계 호출
		List<EBBisttrDTO> isttrList = eBINonMemberMapper.selectIsttrList(ebbDto);

		//예산지출 상세 호출
		List<EBBBdgetDTO> bdgetList = new ArrayList();
		EBBBdgetDTO bdgetDto = new EBBBdgetDTO();
		if(ebbDto !=null) {
			bdgetDto.setEdctnSeq(ebbDto.getEdctnSeq());
			bdgetList = eBINonMemberMapper.selectBdgetDtlList(bdgetDto);
		}

		map.put("rtnData", ebbDto);//비회원 교육 과정 상세
		map.put("roomDto", roomDto);//교육장 정보
		map.put("isttrList", isttrList);//강사 목록
		map.put("bdgetList", bdgetList);//예산 지출 내역 목록
		map.put("rtnTrgtData", eBINonMemberMapper.selectNonMemberTrgtList(pEBINonMemberDTO)); //비회원 교육 대상 목록

		return map;
	}

	/**
	 * 교육 신청자 목록 호출
	 */
	public EBINonMemberDTO setPtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		List<EBINonMemberDTO> ptcptList = new ArrayList();
		EBINonMemberDTO dto = new EBINonMemberDTO();

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pEBINonMemberDTO.getPageIndex());
		page.setRecordCountPerPage(pEBINonMemberDTO.getListRowSize());

		page.setPageSize(pEBINonMemberDTO.getPageRowSize());

		pEBINonMemberDTO.setFirstIndex( page.getFirstRecordIndex() );
		pEBINonMemberDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		ptcptList = eBINonMemberMapper.selectNonMemberPtcptList(pEBINonMemberDTO);
		int ptcptCnt = eBINonMemberMapper.selectNonMemberPtcptListCnt(pEBINonMemberDTO);


		dto.setPtcptList(ptcptList);
		dto.setTotalCount(ptcptCnt);

		return dto;
	}

	/**
	 * 비회원 교육 과정 등록
	 */
	@Transactional
	public int insertNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		int respCnt = 0;

		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		pEBINonMemberDTO.setRegId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
		pEBINonMemberDTO.setModId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

		pEBINonMemberDTO.setEdctnSeq(nmbEdctnPrcsSeqIdgen.getNextIntegerId());

		//파일 처리
		if(pEBINonMemberDTO.getFileList() != null && !pEBINonMemberDTO.getFileList().isEmpty()) {
			HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pEBINonMemberDTO.getFileList());
			pEBINonMemberDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq")); //교육안내문
		}
		if(pEBINonMemberDTO.getPcThumbList() != null && !pEBINonMemberDTO.getPcThumbList().isEmpty()) {
			HashMap<String, Integer> thnlFileSeqMap = cOFileService.setFileInfo(pEBINonMemberDTO.getPcThumbList());
			pEBINonMemberDTO.setThnlFileSeq(thnlFileSeqMap.get("thnlFileSeq")); //썸네일
		}

		pEBINonMemberDTO.setPcStduyCntn(COWebUtil.clearXSSMinimum(pEBINonMemberDTO.getPcStduyCntn()));
		pEBINonMemberDTO.setMblStduyCntn(COWebUtil.clearXSSMinimum(pEBINonMemberDTO.getMblStduyCntn()));

		//비회원 교육 과정 등록
		respCnt = eBINonMemberMapper.insertNonMember(pEBINonMemberDTO);

		//교육 강사관계 등록
		eBINonMemberMapper.deleteIsttrRel(pEBINonMemberDTO);
		eBINonMemberMapper.insertIsttrRel(pEBINonMemberDTO);

		//교육 과정 대상(학습 대상) 상세 등록 start
		List<String> temp = pEBINonMemberDTO.getTargetCdList();
		List<EBINonMemberDTO> tempList = new ArrayList<>();

		if(temp != null &  temp.size() > 0){
			for(String trgCd : temp){
				EBINonMemberDTO targetDto = new EBINonMemberDTO();
				targetDto.setEdctnSeq(pEBINonMemberDTO.getEdctnSeq());
				targetDto.setTargetCd(trgCd);
				targetDto.setEtcNm(null);
				targetDto.setRegId( cOUserDetailsDTO.getId() );
				targetDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
				tempList.add(targetDto);
			}
		}

		//기타부분 입력
		if(pEBINonMemberDTO.getEtcNm() != null && !pEBINonMemberDTO.getEtcNm().isEmpty()){
			EBINonMemberDTO targetDto = new EBINonMemberDTO();
			targetDto.setEdctnSeq(pEBINonMemberDTO.getEdctnSeq());
			targetDto.setTargetCd("ED_TARGET05001");
			targetDto.setEtcNm(pEBINonMemberDTO.getEtcNm());
			targetDto.setRegId( cOUserDetailsDTO.getId() );
			targetDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
			tempList.add(targetDto);
		}

		//교육과정대상 등록
		pEBINonMemberDTO.setTrgtDtlList(tempList);
		eBINonMemberMapper.insertNonMemberTrgt(pEBINonMemberDTO);
		//교육 과정 대상(학습 대상) 상세 등록 end

		return respCnt;
	}


	/**
	 * 비회원 교육 과정 수정
	 */
	@Transactional
	public int updateNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		int respCnt = 0;
		int respCnt2 = 0;
		int respCnt3 = 0;
		int respCnt4 = 0;

		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		pEBINonMemberDTO.setRegId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
		pEBINonMemberDTO.setModId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

		//파일 처리
		if(pEBINonMemberDTO.getFileList() != null && !pEBINonMemberDTO.getFileList().isEmpty()) {
			HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pEBINonMemberDTO.getFileList());
			pEBINonMemberDTO.setEdctnNtctnFileSeq(fileSeqMap.get("edctnNtctnFileSeq")); //교육안내문
		}
		if(pEBINonMemberDTO.getPcThumbList() != null && !pEBINonMemberDTO.getPcThumbList().isEmpty()) {
			HashMap<String, Integer> thnlFileSeqMap = cOFileService.setFileInfo(pEBINonMemberDTO.getPcThumbList());
			pEBINonMemberDTO.setThnlFileSeq(thnlFileSeqMap.get("thnlFileSeq")); //썸네일
		}

		pEBINonMemberDTO.setPcStduyCntn(COWebUtil.clearXSSMinimum(pEBINonMemberDTO.getPcStduyCntn()));
		pEBINonMemberDTO.setMblStduyCntn(COWebUtil.clearXSSMinimum(pEBINonMemberDTO.getMblStduyCntn()));

		//비회원 교육 과정 수정
		respCnt = eBINonMemberMapper.updateNonMember(pEBINonMemberDTO);

		//교육 강사관계 등록 (삭제후 등록)
		eBINonMemberMapper.deleteIsttrRel(pEBINonMemberDTO);
		respCnt2 = eBINonMemberMapper.insertIsttrRel(pEBINonMemberDTO);

		//예산지출 내역 등록
		eBINonMemberMapper.deleteBdgetList(pEBINonMemberDTO);
		respCnt3 = eBINonMemberMapper.insertBdgetList(pEBINonMemberDTO);

		//교육 과정 대상(학습 대상) 상세 등록 start
		eBINonMemberMapper.deleteNonMemberTrgt(pEBINonMemberDTO);
		List<String> temp = pEBINonMemberDTO.getTargetCdList();
		List<EBINonMemberDTO> tempList = new ArrayList<>();

		if(temp != null &  temp.size() > 0){
			for(String trgCd : temp){
				EBINonMemberDTO targetDto = new EBINonMemberDTO();
				targetDto.setEdctnSeq(pEBINonMemberDTO.getEdctnSeq());
				targetDto.setTargetCd(trgCd);
				targetDto.setEtcNm(null);
				targetDto.setRegId( cOUserDetailsDTO.getId() );
				targetDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
				tempList.add(targetDto);
			}
		}

		//기타부분 입력
		if(pEBINonMemberDTO.getEtcNm() != null && !pEBINonMemberDTO.getEtcNm().isEmpty()){
			EBINonMemberDTO targetDto = new EBINonMemberDTO();
			targetDto.setEdctnSeq(pEBINonMemberDTO.getEdctnSeq());
			targetDto.setTargetCd("ED_TARGET05001");
			targetDto.setEtcNm(pEBINonMemberDTO.getEtcNm());
			targetDto.setRegId( cOUserDetailsDTO.getId() );
			targetDto.setRegIp( cOUserDetailsDTO.getLoginIp() );
			tempList.add(targetDto);
		}

		//교육과정대상 등록
		pEBINonMemberDTO.setTrgtDtlList(tempList);
		respCnt4 = eBINonMemberMapper.insertNonMemberTrgt(pEBINonMemberDTO);
		//교육 과정 대상(학습 대상) 상세 등록 end

		return respCnt + respCnt2 + respCnt3 + respCnt4;
	}

	/**
	 * 비회원 교육 과정 삭제
	 */
	@Transactional
	public EBINonMemberDTO deleteNonMember(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		EBINonMemberDTO resultDto = new EBINonMemberDTO();

		int ptcptCnt = eBINonMemberMapper.selectNonMemberDeletePrevChk(pEBINonMemberDTO);

		if(ptcptCnt>0){
			resultDto.setRsn("F");
		}else{
			eBINonMemberMapper.deleteNonMemberDtl(pEBINonMemberDTO);
			resultDto.setRsn("S");
		}
		
		return resultDto;
	}

	/**
	 * 비회원 교육 과정 중복 체크
	 */
	@Transactional
	public EBINonMemberDTO selectNonMemberChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
//		int actCnt = 0;
//		EBINonMemberDTO tempDto = new EBINonMemberDTO();
//		tempDto = eBINonMemberMapper.selectNonMemberChk(pEBINonMemberDTO);

		return eBINonMemberMapper.selectNonMemberChk(pEBINonMemberDTO);
	}


	/**
	 * 비회원 교육 과정 신청자 정원 체크
	 */
	@Transactional
	public EBINonMemberDTO selectFxnumChk(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		EBINonMemberDTO tempDto = new EBINonMemberDTO();

		tempDto = eBINonMemberMapper.selectFxnumChk(pEBINonMemberDTO);
		//정원수 비교

		return tempDto;
	}

	/**
	 * 비회원 교육 과정 신청자 등록
	 */
	@Transactional
	public EBINonMemberDTO setPtcptInfo(EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception
	{
		EBINonMemberDTO tempDto = new EBINonMemberDTO();
		EBINonMemberDTO tempDto2 = new EBINonMemberDTO();

		tempDto = eBINonMemberMapper.selectPtcptDtl(pEBINonMemberDTO);
		tempDto2 = eBINonMemberMapper.selectNonMemberDtl(pEBINonMemberDTO);

		//해당 과정이 존재할 경우
		if(tempDto2.getAccsStatusNm().contains("접수중")) {
			boolean modYn = false;
			String tempADT = pEBINonMemberDTO.getApplyDateTime();
			String tempMOD = tempDto2.getModDtm();

			if(tempMOD != null && !tempMOD.isEmpty() && tempADT != null && !tempADT.isEmpty()) {
				tempADT = tempADT.substring(0, 19);
				tempMOD = tempMOD.substring(0, 19);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime applyDateTime = LocalDateTime.parse(tempADT, formatter);
				LocalDateTime modDtm = LocalDateTime.parse(tempMOD, formatter);
				if(applyDateTime.compareTo(modDtm) < 0) {
					modYn = true;
				}
			}
			//해당 과정이 신청 작성 중에 수정 됐을 경우
			if(modYn) {
				pEBINonMemberDTO.setRegStat("N");
			}
			//이미 등록된 신청자 (중복)
			else if(tempDto.getSttsCd() != null && !"EDU_STTS_CD02".equals(tempDto.getSttsCd())){
				pEBINonMemberDTO.setRegStat("F");
			}
			//등록
			else{
				if("admin".equals(pEBINonMemberDTO.getSiteGubun())) {
					COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
					pEBINonMemberDTO.setRegId(cOUserDetailsDTO.getId());
					pEBINonMemberDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
					pEBINonMemberDTO.setModId(cOUserDetailsDTO.getId());
					pEBINonMemberDTO.setModIp(cOUserDetailsDTO.getLoginIp());
				}
				else if("front".equals(pEBINonMemberDTO.getSiteGubun())) {
					pEBINonMemberDTO.setRegId(pEBINonMemberDTO.getName());
					pEBINonMemberDTO.setRegIp(request.getRemoteAddr());
					pEBINonMemberDTO.setModId(pEBINonMemberDTO.getName());
					pEBINonMemberDTO.setModIp(request.getRemoteAddr());
				}

				pEBINonMemberDTO.setHpNo(pEBINonMemberDTO.getHpNo().replaceAll("-", ""));
				int firstEdctnPtcptIdgen = nmbEdctnPtcptSeqIdgen.getNextIntegerId();
				pEBINonMemberDTO.setPtcptSeq(firstEdctnPtcptIdgen);
				eBINonMemberMapper.insertPtcptDtl(pEBINonMemberDTO);
				pEBINonMemberDTO.setRegStat("S");
			}
		}
		//해당 과정이 삭제된 경우
		else {
			pEBINonMemberDTO.setRegStat("N");
		}

		return pEBINonMemberDTO;
	}

	/**
	 * 비회원 교육 과정 신청자 목록 신청 취소
	 */
	@Transactional
	public int updatePtcptList(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		pEBINonMemberDTO.setRegId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
		pEBINonMemberDTO.setModId( cOUserDetailsDTO.getId() );
		pEBINonMemberDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

		return eBINonMemberMapper.updatePtcptList(pEBINonMemberDTO);
	}

	/**
	 * 비회원 교육 과정 신청자 신청 취소
	 */
	@Transactional
	public int updatePtcpt(EBINonMemberDTO pEBINonMemberDTO, HttpServletRequest request) throws Exception
	{
		if("admin".equals(pEBINonMemberDTO.getSiteGubun())) {
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			pEBINonMemberDTO.setModId(cOUserDetailsDTO.getId());
			pEBINonMemberDTO.setModIp(cOUserDetailsDTO.getLoginIp());
		}
		else if("front".equals(pEBINonMemberDTO.getSiteGubun())) {
			pEBINonMemberDTO.setModId(pEBINonMemberDTO.getName());
			pEBINonMemberDTO.setModIp(request.getRemoteAddr());
		}

		return eBINonMemberMapper.updatePtcpt(pEBINonMemberDTO);
	}


	/**
	 * 비회원 교육 과정 -> 과정 목록 엑셀
	 */
	public void excelDownload1(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception{

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
		cell = row.createCell(13); cell.setCellValue("정원(명)"); cell.setCellStyle(style_header);
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
		List<EBINonMemberDTO> list = pEBINonMemberDTO.getList();
		for(EBINonMemberDTO dto: list){
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0); cell.setCellValue(dto.getPrntCdNm()); cell.setCellStyle(style_body);//과정분류
			cell = row.createCell(1); cell.setCellValue(dto.getCtgryCdNm()); cell.setCellStyle(style_body);//과정분류2
			cell = row.createCell(2); cell.setCellValue(dto.getNm()); cell.setCellStyle(style_body);//과정명
			cell = row.createCell(3); cell.setCellValue("집체교육"); cell.setCellStyle(style_body);//학습방식
			cell = row.createCell(4); cell.setCellValue(dto.getStduyTimeCdNm()); cell.setCellStyle(style_body);//학습시간
			cell = row.createCell(5); cell.setCellValue(""); cell.setCellStyle(style_body);//업종
			cell = row.createCell(6); cell.setCellValue(""); cell.setCellStyle(style_body);//년도
			cell = row.createCell(7); cell.setCellValue(""); cell.setCellStyle(style_body);//차수
			cell = row.createCell(8); cell.setCellValue(dto.getStduyDdCdNm()); cell.setCellStyle(style_body);//교육일

			String edctnStrtDtm = (dto.getEdctnStrtDtm().equals("") || dto.getEdctnStrtDtm() == null) ? "-" : COStringUtil.convertDate(dto.getEdctnStrtDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");
			String edctnEndDtm = (dto.getEdctnEndDtm().equals("") || dto.getEdctnEndDtm() == null) ? "-" : COStringUtil.convertDate(dto.getEdctnEndDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");

			cell = row.createCell(9); cell.setCellValue(edctnStrtDtm + " ~ "+ edctnEndDtm); cell.setCellStyle(style_body);//교육기간 시작
			//cell = row.createCell(10); cell.setCellValue(edctnEndDtm); cell.setCellStyle(style_body);//교육기간 종료

			cell = row.createCell(11); cell.setCellValue(dto.getPlaceNm()); cell.setCellStyle(style_body);//교육장소
			cell = row.createCell(12); cell.setCellValue(dto.getCprtnInsttNm()); cell.setCellStyle(style_body);//협력기관

			if(dto.getFxnumCnt() != null && dto.getFxnumCnt() != 0) {
				cell = row.createCell(13); cell.setCellValue(dto.getFxnumCnt()); cell.setCellStyle(style_body);//정원(명)
			}
			else {
				cell = row.createCell(13); cell.setCellValue("제한없음"); cell.setCellStyle(style_body);//정원(명)
			}

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
			cell = row.createCell(22); cell.setCellValue(""); cell.setCellStyle(style_body);//수료인원(명)
			cell = row.createCell(23); cell.setCellValue(""); cell.setCellStyle(style_body);//참석율(%)
			cell = row.createCell(24); cell.setCellValue(""); cell.setCellStyle(style_body);//완성차(명) 24
			cell = row.createCell(25); cell.setCellValue(""); cell.setCellStyle(style_body);//1차사(명)
			cell = row.createCell(26); cell.setCellValue(""); cell.setCellStyle(style_body);//2차사(명)
			cell = row.createCell(27); cell.setCellValue(""); cell.setCellStyle(style_body);//기타(명)

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
			cell = row.createCell(44); cell.setCellValue(""); cell.setCellStyle(style_body);//출석률(%) 43
			cell = row.createCell(45); cell.setCellValue(""); cell.setCellStyle(style_body);//평가점수(점)
			cell = row.createCell(46); cell.setCellValue(""); cell.setCellStyle(style_body);//종합 평균 ######
			cell = row.createCell(47); cell.setCellValue(""); cell.setCellStyle(style_body);//전체(공통) ######
			cell = row.createCell(48); cell.setCellValue(""); cell.setCellStyle(style_body);//교육환경 ######
			cell = row.createCell(49); cell.setCellValue(""); cell.setCellStyle(style_body);//교육지원 ######
			cell = row.createCell(50); cell.setCellValue(""); cell.setCellStyle(style_body);//교육내용 ######
			cell = row.createCell(51); cell.setCellValue(""); cell.setCellStyle(style_body);//강사 ######
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
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_비회원 교육 과정 목록_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 비회원 교육 과정 목록");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.EBINonMemberServiceImpl");
		pCoSystemLogDTO.setFncNm("selectNonMemberExcelList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(pEBINonMemberDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}

	/**
	 * 비회원 교육 과정 관리 -> 신청자 목록 엑셀
	 */
	public void excelDownload2(EBINonMemberDTO pEBINonMemberDTO, HttpServletResponse response) throws Exception{

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
		cell = row.createCell(1); cell.setCellValue("이름"); cell.setCellStyle(style_header);
		cell = row.createCell(2); cell.setCellValue("부품사명"); cell.setCellStyle(style_header);
		cell = row.createCell(3); cell.setCellValue("사업자등록번호"); cell.setCellStyle(style_header);
		cell = row.createCell(4); cell.setCellValue("부서"); cell.setCellStyle(style_header);
		cell = row.createCell(5); cell.setCellValue("직급"); cell.setCellStyle(style_header);
		cell = row.createCell(6); cell.setCellValue("휴대폰번호"); cell.setCellStyle(style_header);
		cell = row.createCell(7); cell.setCellValue("이메일"); cell.setCellStyle(style_header);
		cell = row.createCell(8); cell.setCellValue("교육신청일시"); cell.setCellStyle(style_header);


		// Body
		List<EBINonMemberDTO> list = pEBINonMemberDTO.getPtcptList();
		for(int i=0; i<list.size(); i++){
			EBINonMemberDTO dto = list.get(i);


			row = sheet.createRow(rowNum++);
			cell = row.createCell(0); cell.setCellValue(i+1); cell.setCellStyle(style_body);//번호
			cell = row.createCell(1); cell.setCellValue(dto.getName()); cell.setCellStyle(style_body);//이름
			cell = row.createCell(2); cell.setCellValue(dto.getPtcptCmpnNm()); cell.setCellStyle(style_body);//부품사명
			cell = row.createCell(3); cell.setCellValue(COStringUtil.bsnmNoConvert(dto.getPtcptBsnmNo())); cell.setCellStyle(style_body);//사업자등록번호

			String deptCdNm = dto.getDeptCdNm();
			String deptDtlNm = (dto.getDeptDtlNm() == "") ? "" : dto.getDeptDtlNm();
			cell = row.createCell(4); cell.setCellValue(COStringUtil.getHtmlStrCnvr(deptCdNm)+"("+deptDtlNm+")"); cell.setCellStyle(style_body);//부서

			cell = row.createCell(5); cell.setCellValue(dto.getPstnCdNm()); cell.setCellStyle(style_body);//직급
			cell = row.createCell(6); cell.setCellValue(dto.getHpNo()); cell.setCellStyle(style_body);//휴대폰번호

			cell = row.createCell(7); cell.setCellValue(dto.getEmail()); cell.setCellStyle(style_body);//이메일

			String regDtm = (dto.getRegDtm() == null || dto.getRegDtm().equals("")) ? "-" : COStringUtil.convertDate(dto.getRegDtm(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "-");
			cell = row.createCell(8); cell.setCellValue(regDtm); cell.setCellStyle(style_body);//가입일

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
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_비회원 교육 과정 신청자 목록_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 비회원 교육 과정 신청자 목록");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.EBINonMemberServiceImpl");
		pCoSystemLogDTO.setFncNm("setPtcptList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(pEBINonMemberDTO.getRsn());
		pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
		pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}






	/**
	 * 사용자 - 비회원 교육 과정 신청 개수 조회
	 */
	@Transactional
	public int searchPtcptCnt(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		pEBINonMemberDTO.setHpNo(pEBINonMemberDTO.getHpNo().replaceAll("-", ""));

		return eBINonMemberMapper.searchPtcptCnt(pEBINonMemberDTO);
	}

	/**
	 *  사용자 - 비회원 교육 신청한 과정 목록 조회
	 */
	public EBINonMemberDTO selectNonMemberApplyList(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		pEBINonMemberDTO.setHpNo(pEBINonMemberDTO.getHpNo().replaceAll("-", ""));

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pEBINonMemberDTO.getPageIndex());
		page.setRecordCountPerPage(pEBINonMemberDTO.getListRowSize());

		page.setPageSize(pEBINonMemberDTO.getPageRowSize());

		pEBINonMemberDTO.setFirstIndex( page.getFirstRecordIndex() );
		pEBINonMemberDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pEBINonMemberDTO.setList( eBINonMemberMapper.selectNonMemberApplyList(pEBINonMemberDTO) );
		pEBINonMemberDTO.setTotalCount( eBINonMemberMapper.searchPtcptCnt(pEBINonMemberDTO) );

		return pEBINonMemberDTO;
	}

	/**
	 * 사용자 - 비회원 교육 신청 페이지 정보 상세
	 */
	@Transactional
	public EBINonMemberDTO selectNonMemberApplyPtcptDtl(EBINonMemberDTO pEBINonMemberDTO) throws Exception
	{
		return eBINonMemberMapper.selectNonMemberApplyPtcptDtl(pEBINonMemberDTO);
	}

}