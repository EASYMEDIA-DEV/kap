package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPHNewsLetterService;
import com.kap.service.dao.mp.MPHNewsLetterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <pre>
 * 뉴스레터 신청자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPHNewsLetterServiceImpl.java
 * @Description		: 뉴스레터 신청자 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPHNewsLetterServiceImpl implements MPHNewsLetterService {

    // DAO
    private final MPHNewsLetterMapper mphNewsLetterMapper;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;
    
    /**
     * 강사 목록을 조회한다.
     */
    public MPHNewsLetterDTO selectNewsLetterList(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mphNewsLetterDTO.getPageIndex());
        page.setRecordCountPerPage(mphNewsLetterDTO.getListRowSize());

        page.setPageSize(mphNewsLetterDTO.getPageRowSize());

        mphNewsLetterDTO.setFirstIndex(page.getFirstRecordIndex());
        mphNewsLetterDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mphNewsLetterDTO.setList(mphNewsLetterMapper.selectNewsLetterList(mphNewsLetterDTO));
        mphNewsLetterDTO.setTotalCount(mphNewsLetterMapper.selectNewsLetterCnt(mphNewsLetterDTO));

        return mphNewsLetterDTO;
    }

    /**
     * 신청자 수를 조회한다.
     */
    public int selectNewsLetterCnt(MPHNewsLetterDTO mphNewsLetterDTO) throws Exception {
        return mphNewsLetterMapper.selectNewsLetterCnt(mphNewsLetterDTO);
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(MPHNewsLetterDTO mphNewsLetterDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("신청일");
        cell.setCellStyle(style_header);


        // Body
        List<MPHNewsLetterDTO> list = mphNewsLetterDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mphNewsLetterDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //신청일
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

            // 열 크기 조정
            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 10000);
            sheet.setColumnWidth(2, 5000);

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_뉴스레터_신청자_관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 뉴스레터 신청자 관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPHNewsLetterServiceImpl");
        pCoSystemLogDTO.setFncNm("selectNewsLetterList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mphNewsLetterDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

}
