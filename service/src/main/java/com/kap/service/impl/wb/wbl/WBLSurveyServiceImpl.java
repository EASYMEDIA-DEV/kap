package com.kap.service.impl.wb.wbl;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.wbl.WBLEpisdMstDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstInsertDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBLSurveyService;
import com.kap.service.dao.wb.wbl.WBLSurveyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 상생협력체감도조사 Service
		 * </pre>
		*
		* @ClassName		: SVASurveyService.java
		* @Description		: 상생협력체감도조사 Service
		* @author 박준희
		* @since 2023.11.20
		* @version 1.0
		* @see
 * @Modification Information
		* <pre>
 * 		since			author					description
		 *   ===========    ==============    =============================
		 *    2023.11.20		박준희					최초생성
		 * </pre>
		*/
@Slf4j
@Service
@RequiredArgsConstructor
public class WBLSurveyServiceImpl implements WBLSurveyService {

	//DAO
	private final WBLSurveyMapper wBLSurveyMapper;

	/* 상생협력체감도조사 시퀀스 */
	private final EgovIdGnrService cxAppctnRsumeSrvIdgen;


	/* 회차관리 시퀀스 */
	private final EgovIdGnrService cxCmpnEpisdMstIdgen;

	private final COSystemLogService cOSystemLogService;


	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLSurveyMstSearchDTO.getPageIndex());
		page.setRecordCountPerPage(wBLSurveyMstSearchDTO.getListRowSize());

		page.setPageSize(wBLSurveyMstSearchDTO.getPageRowSize());

		wBLSurveyMstSearchDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLSurveyMstSearchDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLSurveyMstSearchDTO.setList( wBLSurveyMapper.selectSurveyList(wBLSurveyMstSearchDTO) );
		wBLSurveyMstSearchDTO.setTotalCount( wBLSurveyMapper.selectSurveyListCnt(wBLSurveyMstSearchDTO) );
		return wBLSurveyMstSearchDTO;
	}


	/**
	 *  조회정보의 설문문항정보목록 조회
	 */
	public List<WBLSurveyMstSearchDTO> selecrSurveyQstnList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {
		return wBLSurveyMapper.selecrSurveyQstnList(wBLSurveyMstSearchDTO);
	}

	/**
	 *  응답자별 설문정보를 불러온다
	 */
	public List<WBLSurveyMstSearchDTO> selectSurveyRspnList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {
		return wBLSurveyMapper.selectSurveyRspnList(wBLSurveyMstSearchDTO);
	}




	@Override
	public int insertSurveyList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;
		int surveyMstIdgen = 0;

		String crtfnNo = "";
		String episdText ="";
		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLSurveyMstInsertDTO.setRegIp(regIp);
		wBLSurveyMstInsertDTO.setRegId(regId);
//		wBLSurveyMstInsertDTO.setModIp(regIp);  //2024-11-11 제거
//		wBLSurveyMstInsertDTO.setModId(regId);  //2024-11-11 제거

		//등록
		surveyMstIdgen = cxAppctnRsumeSrvIdgen.getNextIntegerId();

		wBLSurveyMstInsertDTO.setCxstnSrvSeq( surveyMstIdgen );

		if (wBLSurveyMstInsertDTO.getEpisd() < 10){
			episdText = "0"+Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}else{
			episdText = Integer.toString(wBLSurveyMstInsertDTO.getEpisd());
		}

		//2024-07-11 추가개발 ppt 5 인증번호 형식 변경
		crtfnNo = (wBLSurveyMstInsertDTO.getYear()+episdText+wBLSurveyMstInsertDTO.getBsnmRegNo().replaceAll("-","")+wBLSurveyMstInsertDTO.getTelNo().substring(3).replaceAll("-", "")).replaceAll("\\s", "");


		wBLSurveyMstInsertDTO.setCrtfnNo(crtfnNo);
		wBLSurveyMstInsertDTO.setPtcptCd("E");
		wBLSurveyMstInsertDTO.setCmpltnYn("N");

		respCnt = wBLSurveyMapper.insertSurveyMst( wBLSurveyMstInsertDTO );

		return respCnt;
	}

	@Override
	public int updateSurveyList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;

		String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String modIp = CONetworkUtil.getMyIPaddress(request);

		wBLSurveyMstInsertDTO.setModIp(modId);
		wBLSurveyMstInsertDTO.setModId(modIp);

		respCnt = wBLSurveyMapper.updateSurveyMst(wBLSurveyMstInsertDTO);

		return respCnt;
	}

	@Override
	public int deleteSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		int	respCnt = wBLSurveyMapper.deleteSurveyMst(wBLSurveyMstSearchDTO);

		return respCnt;

	}

	@Override
	public WBLSurveyMstInsertDTO selectSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception {
		WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO = wBLSurveyMapper.selectSurveyDtl(wBLSurveyMstSearchDT);

		return wBLSurveyMstInsertDTO;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(wBLEpisdMstDTO.getPageIndex());
		page.setRecordCountPerPage(wBLEpisdMstDTO.getListRowSize());

		page.setPageSize(wBLEpisdMstDTO.getPageRowSize());

		wBLEpisdMstDTO.setFirstIndex( page.getFirstRecordIndex() );
		wBLEpisdMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdList(wBLEpisdMstDTO) );
		wBLEpisdMstDTO.setTotalCount( wBLSurveyMapper.selectEpisdListCnt(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}

	@Override
	public int deleteEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception {

		int Cnt = 0;
		int	respCnt = 0;

		Cnt = wBLSurveyMapper.selectCxstnEpisdListCnt(wBLEpisdMstDTO);

		if (Cnt > 0 ){		// 매핑된 업체가 있으면 설문 번호만 지움

			String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
			String regIp = CONetworkUtil.getMyIPaddress(request);

			wBLEpisdMstDTO.setRegIp(regIp);
			wBLEpisdMstDTO.setRegId(regId);
			wBLEpisdMstDTO.setModIp(regIp);
			wBLEpisdMstDTO.setModId(regId);
			wBLEpisdMstDTO.setSrvSeq(0);
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq(Integer.parseInt(wBLEpisdMstDTO.getDetailsKey()));
			respCnt = wBLSurveyMapper.updateEpisdMst( wBLEpisdMstDTO );
		}else{
			respCnt = wBLSurveyMapper.deleteEpisdList(wBLEpisdMstDTO);
		}

		return respCnt;

	}


	@Override
	public int insertEpisdList(WBLEpisdMstDTO wBLEpisdMstDTO, HttpServletRequest request) throws Exception {
		int respCnt = 0;
		int episdMstIdgen = 0;

		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLEpisdMstDTO.setRegIp(regIp);
		wBLEpisdMstDTO.setRegId(regId);
		wBLEpisdMstDTO.setModIp(regIp);
		wBLEpisdMstDTO.setModId(regId);

		WBLEpisdMstDTO wBLEpisdMstCheck = wBLSurveyMapper.selectEpisdMst( wBLEpisdMstDTO );

		if (wBLEpisdMstCheck!=null){
			//수정
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( wBLEpisdMstCheck.getCxstnCmpnEpisdSeq() );
			respCnt = wBLSurveyMapper.updateEpisdMst( wBLEpisdMstDTO );
		}else{
			//등록
			episdMstIdgen = cxCmpnEpisdMstIdgen.getNextIntegerId();
			wBLEpisdMstDTO.setCxstnCmpnEpisdSeq( episdMstIdgen );
			respCnt = wBLSurveyMapper.insertEpisdMst( wBLEpisdMstDTO );
		}

		return respCnt;
	}

	@Override
	public WBLEpisdMstDTO selectEpisdSurveyList(WBLEpisdMstDTO wBLEpisdMstDTO) throws Exception {

		wBLEpisdMstDTO.setList( wBLSurveyMapper.selectEpisdSurveyList(wBLEpisdMstDTO) );

		return wBLEpisdMstDTO;
	}


	/**
	 * 샘플 엑셀 생성
	 */
	public void excelSampleDownload(HttpServletResponse response) throws Exception{

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

		// Header
		row = sheet.createRow(rowNum++);

		cell = row.createCell(0);
		cell.setCellValue("1차 부품사");
		cell.setCellStyle(style_header);

		cell = row.createCell(1);
		cell.setCellValue("1차 부품사 코드");
		cell.setCellStyle(style_header);

		cell = row.createCell(2);
		cell.setCellValue("2차 부품사");
		cell.setCellStyle(style_header);

		cell = row.createCell(3);
		cell.setCellValue("2차 부품사 코드");
		cell.setCellStyle(style_header);

		cell = row.createCell(4);
		cell.setCellValue("대표자명");
		cell.setCellStyle(style_header);

		cell = row.createCell(5);
		cell.setCellValue("담당자명");
		cell.setCellStyle(style_header);

		cell = row.createCell(6);
		cell.setCellValue("사업자등록번호");
		cell.setCellStyle(style_header);

		cell = row.createCell(7);
		cell.setCellValue("전화번호");
		cell.setCellStyle(style_header);

		cell = row.createCell(8);
		cell.setCellValue("이메일 주소");
		cell.setCellStyle(style_header);


		// Body
		row = sheet.createRow(rowNum++);

		cell = row.createCell(0);
		cell.setCellValue("KOMOS");
		cell.setCellStyle(style_body);

		cell = row.createCell(1);
		cell.setCellValue("LB20");
		cell.setCellStyle(style_body);

		cell = row.createCell(2);
		cell.setCellValue("경일산업");
		cell.setCellStyle(style_body);

		cell = row.createCell(3);
		cell.setCellValue("#268C");
		cell.setCellStyle(style_body);

		cell = row.createCell(4);
		cell.setCellValue("이길동");
		cell.setCellStyle(style_body);

		cell = row.createCell(5);
		cell.setCellValue("홍길동");
		cell.setCellStyle(style_body);

		cell = row.createCell(6);
		cell.setCellValue("123-45-67890");
		cell.setCellStyle(style_body);

		cell = row.createCell(7);
		cell.setCellValue("010-1234-5678");
		cell.setCellStyle(style_body);

		cell = row.createCell(8);
		cell.setCellValue("abc@abc.com");
		cell.setCellStyle(style_body);


		//컨텐츠 타입 및 파일명 지정
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("SensitivitySurveyForm", "UTF-8") + ".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

	}

	@Override
	public HashMap<String, Object> insertSurveyExcelList(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO, HttpServletRequest request , MultipartFile file) throws Exception {

		int respCnt = 0;

		HashMap<String ,Object> rtnMap = new HashMap<String, Object>();


		List<Map<String, Object>> listMap = getListData(file, 1, 8 );
		rtnMap.put("uploadSize", listMap.size());
		WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO = new WBLSurveyMstSearchDTO();
		wBLSurveyMstSearchDTO.setExcelYn("Y");

		List<WBLSurveyMstSearchDTO> selectSurveyList = wBLSurveyMapper.selectSurveyList(wBLSurveyMstSearchDTO);

		int duplCnt = 0;

		List<Map<String, Object>> listMap2 = new ArrayList<Map<String, Object>>();

		boolean inputData = true;
		for(int i=0; i<listMap.size(); i++){
			Map<String, Object> temp = listMap.get(i);
			inputData = true;
			for(WBLSurveyMstSearchDTO aa : selectSurveyList) {
				if (
					//aa.getPartCmpnNm1().equals(temp.get("0").toString()) &&
					aa.getPartCmpnCd1().equals(temp.get("1").toString()) &&
					//aa.getPartCmpnNm2().equals(temp.get("2").toString()) &&
					aa.getPartCmpnCd2().equals(temp.get("3").toString())
					//aa.getRprsntNm().equals(temp.get("4").toString()) &&
					//aa.getPicNm().equals(temp.get("5").toString()) &&
					//aa.getBsnmRegNo().equals(temp.get("6").toString().replaceAll("-","")) &&
					//aa.getTelNo().equals(temp.get("7").toString()) &&
					//aa.getEmail().equals(temp.get("8").toString())

				){
						//System.out.println("@@중복제거");
						duplCnt++;
						inputData = false;
					}
			}

			if(inputData){
				//여기다 새로 담는다.
				listMap2.add(temp);
			}
		}
		rtnMap.put("duplCnt", duplCnt);

		if (listMap2.size()>0){
			for(int i = 0 ; i < listMap2.size() ; i++) {

				wBLSurveyMstInsertDTO.setPartCmpnNm1(listMap2.get(i).get("0").toString().trim());
				wBLSurveyMstInsertDTO.setPartCmpnCd1(listMap2.get(i).get("1").toString().trim());
				wBLSurveyMstInsertDTO.setPartCmpnNm2(listMap2.get(i).get("2").toString().trim());
				wBLSurveyMstInsertDTO.setPartCmpnCd2(listMap2.get(i).get("3").toString().trim());
				wBLSurveyMstInsertDTO.setRprsntNm(listMap2.get(i).get("4").toString().replaceAll("-","").trim());
				wBLSurveyMstInsertDTO.setPicNm(listMap2.get(i).get("5").toString().trim());
				wBLSurveyMstInsertDTO.setBsnmRegNo(listMap2.get(i).get("6").toString().trim());
				wBLSurveyMstInsertDTO.setTelNo(listMap2.get(i).get("7").toString().trim());
				wBLSurveyMstInsertDTO.setEmail(listMap2.get(i).get("8").toString().trim());

				respCnt = insertSurveyList(wBLSurveyMstInsertDTO, request);
			}
		}

		return rtnMap;
	}



	public List<Map<String, Object>> getListData(MultipartFile file, int startRowNum, int columnLength) throws IOException {

		List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = null;
		OPCPackage opcPackage = null;
		InputStream stream = null;

		try {
			stream = file.getInputStream();
			opcPackage = OPCPackage.open(stream);
			workbook = new XSSFWorkbook(opcPackage);
			// 첫번째 시트
			XSSFSheet sheet = workbook.getSheetAt(0);

			int rowIndex = 0;
			int columnIndex = 0;

			// 첫번째 행(0)은 컬럼 명이기 때문에 두번째 행(1) 부터 검색
			for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);

				// 빈 행은 Skip
				if (row.getCell(0) != null) {

					Map<String, Object> map = new HashMap<String, Object>();

					int cells = columnLength;

					for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
						XSSFCell cell = row.getCell(columnIndex);
						map.put(String.valueOf(columnIndex), getCellValue(cell));
					}

					excelList.add(map);
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null){
				stream.close();
			}
			if (opcPackage != null){
				opcPackage.close();
			}
			if (workbook != null){
				workbook.close();
			}
		}

		return excelList;
	}

	public String getCellValue(XSSFCell cell) {

		String value = "";

		if(cell == null){
			return value;
		}

		switch (cell.getCellType()) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				value = (int) cell.getNumericCellValue() + "";
				break;
			default:
				break;
		}
		return value;
	}

	@Override
	public int updateSurveyRspn(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO , HttpServletRequest request) throws Exception {

		int respCnt = 0;

		String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
		String regIp = CONetworkUtil.getMyIPaddress(request);

		wBLSurveyMstInsertDTO.setRegIp(regIp);
		wBLSurveyMstInsertDTO.setRegId(regId);
		wBLSurveyMstInsertDTO.setModIp(regIp);
		wBLSurveyMstInsertDTO.setModId(regId);


		respCnt = wBLSurveyMapper.updateSurveyRspn( wBLSurveyMstInsertDTO );

		if (respCnt > 0 && wBLSurveyMstInsertDTO.getSrvRspnSeq() != null){
			wBLSurveyMapper.deleteSurveyRspn( wBLSurveyMstInsertDTO );
		}

		return respCnt;
	}

	@Override
	public void excelDownload(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO, List<WBLSurveyMstSearchDTO> qstnList, HttpServletResponse response) throws Exception {

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
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

			// Header
			row = sheet.createRow(rowNum++);

			cell = row.createCell(0);
			cell.setCellValue("번호");
			cell.setCellStyle(style_header);

			cell = row.createCell(1);
			cell.setCellValue("년도");
			cell.setCellStyle(style_header);

			cell = row.createCell(2);
			cell.setCellValue("회차");
			cell.setCellStyle(style_header);

			cell = row.createCell(3);
			cell.setCellValue("1차부품사");
			cell.setCellStyle(style_header);

			cell = row.createCell(4);
			cell.setCellValue("1차부품사코드");
			cell.setCellStyle(style_header);

			cell = row.createCell(5);
			cell.setCellValue("2차부품사");
			cell.setCellStyle(style_header);

			cell = row.createCell(6);
			cell.setCellValue("2차부품사코드");
			cell.setCellStyle(style_header);

			cell = row.createCell(7);
			cell.setCellValue("담당자명");
			cell.setCellStyle(style_header);

			cell = row.createCell(8);
			cell.setCellValue("이메일");
			cell.setCellStyle(style_header);

			cell = row.createCell(9);
			cell.setCellValue("전화번호");
			cell.setCellStyle(style_header);

			cell = row.createCell(10);
			cell.setCellValue("참여여부");
			cell.setCellStyle(style_header);

			cell = row.createCell(11);
			cell.setCellValue("완료여부");
			cell.setCellStyle(style_header);

			cell = row.createCell(12);
			cell.setCellValue("점수");
			cell.setCellStyle(style_header);

			cell = row.createCell(13);
			cell.setCellValue("응답업체수");
			cell.setCellStyle(style_header);

			cell = row.createCell(14);
			cell.setCellValue("평균점수");
			cell.setCellStyle(style_header);

			cell = row.createCell(15);
			cell.setCellValue("HKMC평균점수");
			cell.setCellStyle(style_header);

			cell = row.createCell(16);
			cell.setCellValue("참여일");
			cell.setCellStyle(style_header);

			//2024-12-03 응답 카운트 추가
			cell = row.createCell(17);
			cell.setCellValue("응답 카운트");
			cell.setCellStyle(style_header);

			/*cell = row.createCell(14);
			cell.setCellValue("최초등록자");
			cell.setCellStyle(style_header);

			cell = row.createCell(15);
			cell.setCellValue("최초등록일시");
			cell.setCellStyle(style_header);

			cell = row.createCell(16);
			cell.setCellValue("최종수정자");
			cell.setCellStyle(style_header);

			cell = row.createCell(17);
			cell.setCellValue("최종수정일시");
			cell.setCellStyle(style_header);*/

			int qstnRow = 18;
			String scoreExclusionYn = "";
			for (WBLSurveyMstSearchDTO qstnDto : qstnList) {
				cell = row.createCell(qstnRow);
				if ("Y".equals(qstnDto.getScoreExclusionYn())) {
					scoreExclusionYn = "[점수 미반영] ";
				} else {
					scoreExclusionYn = "";
				}
				cell.setCellValue(scoreExclusionYn + qstnDto.getQstnNm());
				cell.setCellStyle(style_header);
				qstnRow++;
			}

			cell = row.createCell(qstnRow);
			cell.setCellValue("인증번호");
			cell.setCellStyle(style_header);


			// Body
			List<WBLSurveyMstSearchDTO> list = wBLSurveyMstSearchDTO.getList();

			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(rowNum++);

				//번호
				cell = row.createCell(0);
				cell.setCellValue(wBLSurveyMstSearchDTO.getTotalCount() - i);
				cell.setCellStyle(style_body);

				cell = row.createCell(1);
				cell.setCellValue(list.get(i).getYear().substring(0, 4));
				cell.setCellStyle(style_body);

				cell = row.createCell(2);
				cell.setCellValue(list.get(i).getEpisd());
				cell.setCellStyle(style_body);

				cell = row.createCell(3);
				cell.setCellValue(list.get(i).getPartCmpnNm1());
				cell.setCellStyle(style_body);

				cell = row.createCell(4);
				cell.setCellValue(list.get(i).getPartCmpnCd1());
				cell.setCellStyle(style_body);

				cell = row.createCell(5);
				cell.setCellValue(list.get(i).getPartCmpnNm2());
				cell.setCellStyle(style_body);

				cell = row.createCell(6);
				cell.setCellValue(list.get(i).getPartCmpnCd2());
				cell.setCellStyle(style_body);

				cell = row.createCell(7);
				cell.setCellValue(list.get(i).getPicNm());
				cell.setCellStyle(style_body);

				cell = row.createCell(8);
				cell.setCellValue(list.get(i).getEmail());
				cell.setCellStyle(style_body);

				cell = row.createCell(9);
				cell.setCellValue(list.get(i).getTelNo());
				cell.setCellStyle(style_body);

				cell = row.createCell(10);
				cell.setCellValue("E".equals(list.get(i).getPtcptCd()) ? "대기" : "Y".equals(list.get(i).getPtcptCd()) ? "참여" : "미참여");
				cell.setCellStyle(style_body);

				cell = row.createCell(11);
				cell.setCellValue("Y".equals(list.get(i).getCmpltnYn()) ? "완료" : "N".equals(list.get(i).getCmpltnYn()) ? "미완료" : "-");
				cell.setCellStyle(style_body);

				cell = row.createCell(12);
				cell.setCellValue(list.get(i).getScore() + " (" + list.get(i).getPercentage() + ")"); //2024-08-06 추가개발 백분율 점수값 추가
				cell.setCellStyle(style_body);

				cell = row.createCell(13);
				cell.setCellValue(list.get(i).getCnt());
				cell.setCellStyle(style_body);

				cell = row.createCell(14);
				cell.setCellValue(list.get(i).getAvgScore() + " (" + list.get(i).getAvgScorePercentage() + ")");  //2024-12-03 100점 환산 평균점수 추가
				cell.setCellStyle(style_body);

				cell = row.createCell(15);
				cell.setCellValue(list.get(i).getHkmcAvgScore() + " (" + list.get(i).getHkmcAvgScorePercentage() + ")");  //2024-12-03 HKMC 100점 환산 평균점수 추가
				cell.setCellStyle(style_body);

				cell = row.createCell(16);
				cell.setCellValue(list.get(i).getPtcptCmpltnDtm() == null ? "-" : list.get(i).getPtcptCmpltnDtm().substring(0, list.get(i).getPtcptCmpltnDtm().lastIndexOf(":")));
				cell.setCellStyle(style_body);

				//2024-12-03 응답 카운트 추가
				cell = row.createCell(17);
				cell.setCellValue(list.get(i).getCountSelect());
				cell.setCellStyle(style_body);

				/*cell = row.createCell(14);
				cell.setCellValue(list.get(i).getRegName());
				cell.setCellStyle(style_body);

				cell = row.createCell(15);
				cell.setCellValue(list.get(i).getRegDtm() == null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
				cell.setCellStyle(style_body);

				cell = row.createCell(16);
				cell.setCellValue(list.get(i).getModName());
				cell.setCellStyle(style_body);

				cell = row.createCell(17);
				cell.setCellValue(list.get(i).getModDtm() == null ? "-" : list.get(i).getModDtm().substring(0, list.get(i).getModDtm().lastIndexOf(":")));
				cell.setCellStyle(style_body);*/


				int rspnRow = 18;
				if (list.get(i).getList() != null && list.get(i).getList().size() > 0) {

					for (WBLSurveyMstSearchDTO rspnDto : list.get(i).getList()) {

						if (list.get(i).getSrvRspnSeq() == rspnDto.getSrvRspnSeq()) {
							cell = row.createCell(rspnRow);
							cell.setCellValue(rspnDto.getExmplAnswer());
							cell.setCellStyle(style_body);
							rspnRow++;
						}

					}

				}

				cell = row.createCell(qstnRow);
				cell.setCellValue(list.get(i).getCrtfnNo());
				cell.setCellStyle(style_body);
			}

			// 열 너비 설정
		   /* for(int i =0; i < 8; i++){
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 800));
			}*/

			String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

			//컨텐츠 타입 및 파일명 지정
			response.setContentType("ms-vnd/excel");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("상생협력체감도조사", "UTF-8") + timeStamp + ".xlsx");

			// Excel File Output
			workbook.write(response.getOutputStream());
//			workbook.close();

			//다운로드 사유 입력
			COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
			COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
			pCoSystemLogDTO.setTrgtMenuNm("상생협력체감도조사 ");
			pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.wb.wbl.WBLSurveyServiceImpl");
			pCoSystemLogDTO.setFncNm("getSurveyListExcel");
			pCoSystemLogDTO.setPrcsCd("DL");
			pCoSystemLogDTO.setRsn(wBLSurveyMstSearchDTO.getRsn());
			pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
			pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
			cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
		}

	}



	/**
	 *  목록을 조회한다.
	 */
	public WBLSurveyMstSearchDTO selectFrontSurveyList(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {
		wBLSurveyMstSearchDTO.setList( wBLSurveyMapper.selectFrontSurveyList(wBLSurveyMstSearchDTO) );
		return wBLSurveyMstSearchDTO;
	}

	/**
	 *  설문 미참여
	 */
	@Override
	public int updateNoSurvey(WBLSurveyMstSearchDTO wBLSurveyMstSearchDTO) throws Exception {

		int respCnt = 0;
		respCnt = wBLSurveyMapper.updateNoSurvey( wBLSurveyMstSearchDTO );

		return respCnt;
	}

	@Override
	public WBLSurveyMstInsertDTO selectFrontSurveyDtl(WBLSurveyMstSearchDTO wBLSurveyMstSearchDT) throws Exception {
		WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO = wBLSurveyMapper.selectFrontSurveyDtl(wBLSurveyMstSearchDT);

		return wBLSurveyMstInsertDTO;
	}

	/**
	 *  설문 참여
	 */
	@Override
	public int updateSurvey(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO) throws Exception {

		int respCnt = 0;
		respCnt = wBLSurveyMapper.updateSurvey( wBLSurveyMstInsertDTO );

		return respCnt;
	}

	/**
	 *  인증번호 발송
	 */
	@Override
	public int updateSendDtm(WBLSurveyMstInsertDTO wBLSurveyMstInsertDTO) throws Exception {

		int respCnt = 0;
		respCnt = wBLSurveyMapper.updateSendDtm( wBLSurveyMstInsertDTO );

		return respCnt;
	}

}