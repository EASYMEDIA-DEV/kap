package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.EmfMap;
import com.kap.service.COAAdmService;
import com.kap.service.COLgnService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COAAdmMapper;
import com.kap.service.dao.COLgnMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 *    2022.04.14		신혜정				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COAAdmServiceImpl implements COAAdmService {

	//DAO
	private final COAAdmMapper cOAAdmMapper;

	private final COLgnMapper coLgnMapper;

	private final COLgnService cOLgnService;

	//로그인 상태값 시스템 등록
	private final COSystemLogService cOSystemLogService;

	/**
	 * 관리자 목록을 조회한다.
	 */
	public COAAdmDTO selectAdmList(COAAdmDTO pCOAAdmDTO) throws Exception
	{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pCOAAdmDTO.getPageIndex());
		page.setRecordCountPerPage(pCOAAdmDTO.getListRowSize());

		page.setPageSize(pCOAAdmDTO.getPageRowSize());

		pCOAAdmDTO.setFirstIndex( page.getFirstRecordIndex() );
		pCOAAdmDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pCOAAdmDTO.setList( cOAAdmMapper.selectAdmList(pCOAAdmDTO) );
		pCOAAdmDTO.setTotalCount( cOAAdmMapper.getAdmListCnt(pCOAAdmDTO) );

		return pCOAAdmDTO;
	}

	/**
	 * 관리자 상세를 조회한다.
	 */
	public COAAdmDTO selectAdmDtl(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		return cOAAdmMapper.selectAdmDtl(pCOAAdmDTO);
	}


	/**
	 * 관리자를 등록한다.
	 */
	public int insertAdm(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		// 비밀번호
		String password = pCOAAdmDTO.getPwd();
		if (!"".equals(password))
		{
			pCOAAdmDTO.setPwd(COSeedCipherUtil.encryptPassword(password,  pCOAAdmDTO.getId()));
		}
		int respCnt = cOAAdmMapper.insertAdm(pCOAAdmDTO);

		// 관리자 수정정보 Set
		setAdmMenu(pCOAAdmDTO, false);

		// 비밀번호 이력 저장
		pCOAAdmDTO.setNewEncPwd(pCOAAdmDTO.getPwd());
		cOLgnService.setPwdHistory(pCOAAdmDTO);
		RequestContextHolder.getRequestAttributes().setAttribute("COAAdmId", pCOAAdmDTO.getId(), RequestAttributes.SCOPE_SESSION);
		return respCnt;
	}

	/**
	 * 관리자를 수정한다.
	 */
	public int updateAdm(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		int respCnt = 0;

		try{

			COAAdmDTO tempAdmDTO  = cOAAdmMapper.selectAdmDtl(pCOAAdmDTO);
			// 권한을 변경하였는지 확인
			tempAdmDTO.setBfreAuthCd(tempAdmDTO.getAuthCd());
			tempAdmDTO.setModAuthCd(pCOAAdmDTO.getAuthCd());

			if (!tempAdmDTO.getBfreAuthCd().equals(tempAdmDTO.getModAuthCd()))
			{

				COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
				tempAdmDTO.setRegId( coaAdmDTO.getId() );
				tempAdmDTO.setRegIp( coaAdmDTO.getLoginIp() );


				//권한변경 관련하여 로그를 찍을때 사용할것
				System.out.println("@@@@ tempAdmDTO= " + tempAdmDTO);
				System.out.println("@@@@ getAdmSeq= " + tempAdmDTO.getAdmSeq());
				System.out.println("@@@@ getBfreAuthCd= " + tempAdmDTO.getBfreAuthCd());
				System.out.println("@@@@ getModAuthCd= " + tempAdmDTO.getModAuthCd());
				System.out.println("@@@@ getRegId= " + tempAdmDTO.getRegId());
				System.out.println("@@@@ getRegIp= " + tempAdmDTO.getRegIp());


				cOAAdmMapper.insertAuthModLog(tempAdmDTO);
			}

			// 관리자 수정정보 Set
			respCnt = cOAAdmMapper.updateAdm(pCOAAdmDTO);

			setAdmMenu(pCOAAdmDTO, true);
			setAdmId(pCOAAdmDTO);

		}catch (Exception e){
			throw new Exception("1111");
		}

		return respCnt;
	}

	/**
	 * 관리자를 수정한다. (내정보 변경)
	 */
	public int updatePrsnData(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		COAAdmDTO lgnMap = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
		// 관리자 조회
		pCOAAdmDTO.setAdmSeq( lgnMap.getAdmSeq() );
		COAAdmDTO admInfo = cOAAdmMapper.selectAdmDtl(pCOAAdmDTO);
		// 비밀번호와 비밀번호 값이 같은지 확인
		String pwd = pCOAAdmDTO.getPwd();
		if (!"".equals(pwd))
		{
			pCOAAdmDTO.setPwd( COSeedCipherUtil.encryptPassword(pwd, admInfo.getId()) );
			pCOAAdmDTO.setNewEncPwd(pCOAAdmDTO.getPwd());
		}

		int act = cOAAdmMapper.updateAdm(pCOAAdmDTO);
		pCOAAdmDTO.setId(admInfo.getId());
		cOLgnService.setPwdHistory(pCOAAdmDTO);

		return act;
	}

	/**
	 * 관리자를 삭제한다.
	 */
	@Transactional
	public int deleteAdm(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		cOAAdmMapper.deleteAdmMenu(pCOAAdmDTO);

		// 삭제할 메뉴 수정 seq 조회
		List<COAAdmDTO> delList = cOAAdmMapper.selectDelAdmModMst(pCOAAdmDTO);

		for(COAAdmDTO dto : delList){
			// 메뉴 수정 마스터 삭제
			cOAAdmMapper.deleteAdmModMst(dto);
			// 메뉴 수정 상세 삭제
			cOAAdmMapper.deleteAdmModDtl(dto);
		}
		setAdmId(pCOAAdmDTO);
		int actCnt = cOAAdmMapper.deleteAdm(pCOAAdmDTO);

		return actCnt;
	}

	/**
	 * 관리자 메뉴정보를 Setting한다.
	 */
	public void setAdmMenu(COAAdmDTO pCOAAdmDTO, boolean removeFlag) throws Exception
	{
		// 관리자 메뉴 set
		String mChecked = pCOAAdmDTO.getMChecked();

		// 이전 메뉴와 비교하여 변경된 내용이 있는지 확인
		String menuInfs = cOAAdmMapper.getAdmMenuInf(pCOAAdmDTO);
		int admModSeq = -1;

		if(mChecked != null && !"".equals(mChecked) && mChecked.lastIndexOf(",") > -1){
			mChecked = mChecked.substring(0, mChecked.length() - 1);
		}

		if(!"99".equals(pCOAAdmDTO.getAuthCd()) &&  !mChecked.equals(menuInfs)){

			// insert인 경우는 제외(removeFlag = false)
			if(removeFlag){
				// 관리자 메뉴 삭제
				cOAAdmMapper.deleteAdmMenu(pCOAAdmDTO);
			}

			// 메뉴 수정 mst 등록
			admModSeq= cOAAdmMapper.insertAdmModMst(pCOAAdmDTO);

			String[] menuSeqs;
			if (mChecked.indexOf(",") > 0)
			{
				menuSeqs = mChecked.split(",");
			}
			else
			{
				menuSeqs = new String[]{ mChecked };
			}

			//변경 권한 여부 체크
			if (menuSeqs != null)
			{
				COMenuDTO cOMenuDTO = new COMenuDTO();

				cOMenuDTO.setAdmSeq( pCOAAdmDTO.getAdmSeq() );
				cOMenuDTO.setDetailsKey(pCOAAdmDTO.getDetailsKey()== null || pCOAAdmDTO.getDetailsKey().isEmpty() ? pCOAAdmDTO.getAdmSeq() + "" : pCOAAdmDTO.getDetailsKey());
				cOMenuDTO.setRegId(pCOAAdmDTO.getRegId());
				cOMenuDTO.setRegIp(pCOAAdmDTO.getRegIp());

				for (int q = 0, len = menuSeqs.length; q < len; q++)
				{
					int menuSeq = Integer.parseInt(menuSeqs[q]);
					cOMenuDTO.setMenuSeq(menuSeq);

					cOAAdmMapper.insertAdmMenu(cOMenuDTO);
					if (admModSeq > -1)
					{
						pCOAAdmDTO.setMenuSeq(menuSeq);
						cOAAdmMapper.insertAdmModDtl(pCOAAdmDTO);
					}
				}
			}
		}
	}

	/**
	 * 비밀번호 사용 가능여부를 가져온다
	 */
	public List<String> getPwdCheck(COAAdmDTO pCOAAdmDTO) throws Exception{
		String password = pCOAAdmDTO.getPwd();
		pCOAAdmDTO = cOAAdmMapper.selectAdmDtl(pCOAAdmDTO);
		if (!"".equals(password))
		{
			pCOAAdmDTO.setNewEncPwd(COSeedCipherUtil.encryptPassword(password,  pCOAAdmDTO.getId()));
		}

		return coLgnMapper.getPwdHistoryCheck(pCOAAdmDTO);
	}

	/**
	 * 아이디 중복여부를 가져온다.
	 */
	public int getIdOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		return cOAAdmMapper.getIdOverlapCheck(pCOAAdmDTO);
	}

	/**
	 * 이메일 중복여부를 가져온다.
	 */
	public int getEmailOverlapCheck(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		return cOAAdmMapper.getEmailOverlapCheck(pCOAAdmDTO);
	}

	/**
	 * 비밀번호를 초기화한다.
	 */
	@Transactional
	public int updatePwdInit(COAAdmDTO pCOAAdmDTO) throws Exception
	{
		// 특수문자 포함하도록 변경
		String password = RandomStringUtils.random(8, 0, 0, true, true, null, new SecureRandom()) + RandomStringUtils.random(2, 35, 64, false, false, null, new SecureRandom());
		COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();

		// & < > : ; ? ' " 공백은 치환 -> !
		password = password.replaceAll("[\\s&<>:;?']", "!");

		pCOAAdmDTO.setPwd(password);

		pCOAAdmDTO.setRegId( coaAdmDTO.getId() );
		pCOAAdmDTO.setRegIp( coaAdmDTO.getLoginIp() );
		pCOAAdmDTO.setModId( coaAdmDTO.getId() );
		pCOAAdmDTO.setModIp( coaAdmDTO.getLoginIp() );

		// 이전 비밀번호 내역 삭제 (2022-07-14 마지막으로 설정한 비밀번호만 삭제하도록 변경)
		List<String> pwdHistoryList = coLgnMapper.getPwdHistoryCheck(pCOAAdmDTO);

		pCOAAdmDTO.setNewEncPwd(COSeedCipherUtil.encryptPassword(password, pCOAAdmDTO.getId()));

		if(pwdHistoryList != null && pwdHistoryList.size() > 0)
		{
			List<String> tmpList = new ArrayList<>();
			tmpList.add(pwdHistoryList.get(pwdHistoryList.size() -1));
			pCOAAdmDTO.setPwdList(tmpList);
			coLgnMapper.deletePwdHistory(pCOAAdmDTO);
		}

		return cOAAdmMapper.updatePwdInit(pCOAAdmDTO);
	}

	/**
	 * 관리자 로그인 세션을 조회한다.
	 */
	public String getAdmSessionId(String adminId) throws Exception
	{
		return cOAAdmMapper.getAdmSessionId(adminId);
	}

	/**
	 * 엑셀 생성
	 */
	public void excelDownload(COAAdmDTO pCOAAdmDTO, HttpServletResponse response) throws Exception{

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

		/*cell = row.createCell(4);
		cell.setCellValue("권한");
		cell.setCellStyle(style_header);*/

		cell = row.createCell(4);
		cell.setCellValue("계정상태");
		cell.setCellStyle(style_header);

		cell = row.createCell(5);
		cell.setCellValue("최종접속일");
		cell.setCellStyle(style_header);

		// Body
		List<COAAdmDTO> list = pCOAAdmDTO.getList();
		for (int i=0; i<list.size(); i++) {
			row = sheet.createRow(rowNum++);

			//번호
			cell = row.createCell(0);
			cell.setCellValue(pCOAAdmDTO.getTotalCount() - i);
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
		/*	cell = row.createCell(4);
			cell.setCellValue(list.get(i).getAuthCdNm());
			cell.setCellStyle(style_body);*/

			//계정상태
			cell = row.createCell(4);
			cell.setCellValue("Y".equals(list.get(i).getUseYn()) ? "활성" : "비활성");
			cell.setCellStyle(style_body);

			//최종접속일
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getLastLgnDtm() == null ? "-" : list.get(i).getLastLgnDtm().substring(0, list.get(i).getLastLgnDtm().lastIndexOf(":")));
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
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_관리자관리_", "UTF-8") + timeStamp +".xlsx");

		// Excel File Output
		workbook.write(response.getOutputStream());
		workbook.close();

		//다운로드 사유 입력
		COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
		COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
		pCoSystemLogDTO.setTrgtMenuNm("시스템 관리 > 관리자관리");
		pCoSystemLogDTO.setSrvcNm("mngwserc.co.coa.service.impl.COAAdmServiceImpl");
		pCoSystemLogDTO.setFncNm("selectAdmList");
		pCoSystemLogDTO.setPrcsCd("DL");
		pCoSystemLogDTO.setRsn(pCOAAdmDTO.getRsn());
		pCoSystemLogDTO.setRegId(lgnCOAAdmDTO.getId());
		pCoSystemLogDTO.setRegIp(lgnCOAAdmDTO.getLoginIp());
		cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
	}

	/**
	 * 신고하기 기존 대상자 비밀번호 변경
	 */
	public int setEthicList() throws Exception{
		List<EmfMap> ethicList = cOAAdmMapper.getEthicList();
		int ehticSize = ethicList.size();
		for(int q = 0 ; q < ehticSize ; q++){
			String regId = ethicList.get(q).getString("regId");
			String regPw = COSeedCipherUtil.encryptPassword(regId+"ethics",  regId);
			ethicList.get(q).put("regPw", regPw);
			cOAAdmMapper.setEthicPwd(ethicList.get(q));
		}
		return ehticSize;
	}

	/**
	 * 관리자 시퀀스로 ID 조회한다.
	 */
	public List<COAAdmDTO> getSeqAdmList(COAAdmDTO pCOAAdmDTO) throws Exception{
		return cOAAdmMapper.getSeqAdmList(pCOAAdmDTO);
	}

	/**
	 * 관리자 시퀀스로 ID 조회한다.
	 */
	private void setAdmId(COAAdmDTO pCOAAdmDTO) throws Exception{
		List<COAAdmDTO> admList = cOAAdmMapper.getSeqAdmList(pCOAAdmDTO);
		if(admList != null && admList.size() > 0){
			String admId = "";
			for(int q = 0 ; q < admList.size() ; q++){
				if("".equals(admId))
				{
					admId =  admList.get(q).getId();
				}
				else
				{
					admId =  admId + "," + admList.get(q).getId();
				}
			}
			RequestContextHolder.getRequestAttributes().setAttribute("COAAdmId", admId, RequestAttributes.SCOPE_SESSION);
		}
	}

	/**
	 * 관리자 권한 변경 로그를 가져온다.
	 */
	public COAAdmDTO getAuthLogList(COAAdmDTO pCOAAdmDTO) throws Exception{

		COPaginationUtil page = new COPaginationUtil();

		page.setCurrentPageNo(pCOAAdmDTO.getPageIndex());
		page.setRecordCountPerPage(pCOAAdmDTO.getListRowSize());

		page.setPageSize(pCOAAdmDTO.getPageRowSize());

		pCOAAdmDTO.setFirstIndex( page.getFirstRecordIndex() );
		pCOAAdmDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

		pCOAAdmDTO.setList( cOAAdmMapper.getAuthLogList(pCOAAdmDTO) );
		pCOAAdmDTO.setTotalCount( cOAAdmMapper.getAuthLogListCnt(pCOAAdmDTO) );


		return pCOAAdmDTO;
	}

}