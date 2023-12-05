package com.kap.service.impl.cb.cbb;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.service.COSystemLogService;
import com.kap.core.dto.cb.cbb.CBBManageConsultSearchDTO;
import com.kap.core.dto.cb.cbb.CBBConsultSuveyRsltListDTO;
import com.kap.service.CBBManageConsultService;
import com.kap.service.dao.cb.cbb.CBBManageConsultMapper;
import com.kap.service.COCommService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * 경영컨설팅 서비스 구현 클래스
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21  이옥정         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CBBManageConsultServiceimpl implements CBBManageConsultService {

    private final CBBManageConsultMapper cBBManageConsultMapper;

    /** 공통 서비스 **/
    private final COCommService cOCommService;

    /* 파일 서비스 */
    private final COFileService cOFileService;

    /*로그 서비스*/
    private final COSystemLogService cOSystemLogService;

    /**
     * 리스트 조회
     */
    public CBBManageConsultSearchDTO selectManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception{
            COPaginationUtil page = new COPaginationUtil();
            page.setCurrentPageNo(cBBManageConsultSearchDTO.getPageIndex());
            if("Y".equals(cBBManageConsultSearchDTO.getExcelYn())) {
                page.setRecordCountPerPage(cBBManageConsultMapper.getManageConsultListCnt(cBBManageConsultSearchDTO));
            }else{
                page.setRecordCountPerPage(cBBManageConsultSearchDTO.getListRowSize());
            }
            page.setPageSize(cBBManageConsultSearchDTO.getPageRowSize());
            cBBManageConsultSearchDTO.setFirstIndex(page.getFirstRecordIndex());
            cBBManageConsultSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
            cBBManageConsultSearchDTO.setTotalCount(cBBManageConsultMapper.getManageConsultListCnt(cBBManageConsultSearchDTO));
            cBBManageConsultSearchDTO.setList(cBBManageConsultMapper.selectManageConsultList(cBBManageConsultSearchDTO));
        return cBBManageConsultSearchDTO;
    }

    /**
     * 만족도 종합 결과 리스트 조회
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(cBBConsultSuveyRsltListDTO.getPageIndex());
        page.setRecordCountPerPage(cBBConsultSuveyRsltListDTO.getListRowSize());
        page.setPageSize(cBBConsultSuveyRsltListDTO.getPageRowSize());
        cBBConsultSuveyRsltListDTO.setFirstIndex(page.getFirstRecordIndex());
        cBBConsultSuveyRsltListDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        cBBConsultSuveyRsltListDTO.setTotalCount(cBBManageConsultMapper.getConsultSuveyRsltCnt(cBBConsultSuveyRsltListDTO));
        cBBConsultSuveyRsltListDTO.setList(cBBManageConsultMapper.selectConsultSuveyRsltList(cBBConsultSuveyRsltListDTO));
        return cBBConsultSuveyRsltListDTO;
    }



    /**
     * 엑셀 생성
     */
    public void excelDownload(CBBManageConsultSearchDTO cBBManageConsultSearchDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("사업연도");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("진행상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("매출액(억원)");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("직원수");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("주샌상품");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("신청분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("신청 소재지");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("SQ 인증 주관사");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("주고객사");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("방문일");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("담당위원 업종/분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("킥오프일");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("신청일");
        cell.setCellStyle(style_header);

        // Body
        List<CBBManageConsultListDTO> list = cBBManageConsultSearchDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(cBBManageConsultSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getResumeSttsNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getAppctnBsnmNo());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //매출액(억원)
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSlsPmt() == null ? 0 : list.get(i).getSlsPmt());
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getMpleCnt() == null ? 0 : list.get(i).getMpleCnt());
            cell.setCellStyle(style_body);

            //주생산품
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getMjrPrdct1() == null ? "-" : list.get(i).getMjrPrdct1() +"/"+ list.get(i).getMjrPrdct2() +"/"+ list.get(i).getMjrPrdct3());
            cell.setCellStyle(style_body);

            //신청분야
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getAppctnFldNm());
            cell.setCellStyle(style_body);

            //신청소재지
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getFirstRgnsNm() +" "+ list.get(i).getScndRgnsNm());
            cell.setCellStyle(style_body);

            //SQ 인증 주관사
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getCrtfnCmpnNm());
            cell.setCellStyle(style_body);

            //주고객사
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getMnCmpnNm());
            cell.setCellStyle(style_body);

            //방문일
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getVstDt() == null ? "-" : list.get(i).getVstDt());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getCmssrNm() == null ? "-" : list.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //담당위원 업종/분야
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getCmssrCbsnNm() == null ? "-" : list.get(i).getCmssrCbsnNm());
            cell.setCellStyle(style_body);

            //킥오프일
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getCnstgKickfDt() == null ? "-" : list.get(i).getCnstgKickfDt());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getLvlupDt() == null ? "-" : list.get(i).getLvlupDt());
            cell.setCellStyle(style_body);

            //신청일
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getAppctnDt());
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("경영컨설팅_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 경영컨설팅");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cbb.service.impl.CBBManageConsultServiceImpl");
        pCoSystemLogDTO.setFncNm("selectManageConsultList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(cBBManageConsultSearchDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 만족도 종합 결과 엑셀다운로드 상세
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltDtlExcel(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO) throws Exception{
        /*COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(cBBConsultSuveyRsltListDTO.getPageIndex());
        page.setRecordCountPerPage(cBBManageConsultMapper.getConsultSuveyRsltDtlCnt(cBBConsultSuveyRsltListDTO));
        page.setPageSize(cBBConsultSuveyRsltListDTO.getPageRowSize());
        cBBConsultSuveyRsltListDTO.setFirstIndex(page.getFirstRecordIndex());
        cBBConsultSuveyRsltListDTO.setRecordCountPerPage(page.getRecordCountPerPage());*/
        /*cBBConsultSuveyRsltListDTO.setTotalCount(cBBManageConsultMapper.getConsultSuveyRsltDtlCnt(cBBConsultSuveyRsltListDTO));*/
        cBBConsultSuveyRsltListDTO.setList(cBBManageConsultMapper.selectConsultSuveyRsltDtlExcel(cBBConsultSuveyRsltListDTO));
        return cBBConsultSuveyRsltListDTO;
    }

    /**
     * 만족도 종합 결과 상세 엑셀 생성
     */
    public void srvRsltExcelDownload(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("사업연도");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("참여자");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("신청업종/분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("지도구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("총점(100)");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("지도실적(50)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("의사소동(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("기획력(10)");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("실행력(15)");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("마인드(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("전문지식(15)");
        cell.setCellStyle(style_header);


        // Body
        List<CBBConsultSuveyRsltListDTO> list = cBBConsultSuveyRsltListDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(cBBConsultSuveyRsltListDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmssrNm() == null ? "-" : list.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //참여자
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getPtcptName() == null ? "-" : list.get(i).getPtcptName());
            cell.setCellStyle(style_body);

            //신청업종/분야
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getAppctnFldNm());
            cell.setCellStyle(style_body);

            //지도구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getGuideTypeNm());
            cell.setCellStyle(style_body);

            //상태
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSrvStts());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getLvlupDt() == null ? "-" : list.get(i).getLvlupDt());
            cell.setCellStyle(style_body);

            //총점(100)
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getTtlScore());
            cell.setCellStyle(style_body);

            //지도실적(50)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getGuideRsltScore());
            cell.setCellStyle(style_body);

            //의사소통(5)
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getCmmnctnScore());
            cell.setCellStyle(style_body);

            //기획력(10)
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getPlnngabltScore());
            cell.setCellStyle(style_body);

            //실행력(15)
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getExctvabltScore());
            cell.setCellStyle(style_body);

            //마인드(5)
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getMndScore());
            cell.setCellStyle(style_body);

            //전문지식(15)
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getExprtsScore());
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("경영컨설팅_만족도종합결과_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 경영컨설팅");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cbb.service.impl.CBBManageConsultServiceImpl");
        pCoSystemLogDTO.setFncNm("selectConsultSuveyRsltDtlExcel");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(cBBConsultSuveyRsltListDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

}
