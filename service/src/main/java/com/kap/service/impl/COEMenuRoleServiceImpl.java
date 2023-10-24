package com.kap.service.impl;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COEMenuRoleDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.service.COEMenuRoleService;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.dao.COEMenuRoleMapper;
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
 * 메뉴 권한 변경 관리를 위한 Service
 * </pre>
 *
 * @ClassName		: COEMenuRoleServiceImpl.java
 * @Description		: 메뉴 권한 변경 관리를 위한 Service
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author					description
 *   ===========    ==============    =============================
 *    2022.04.14		신혜정					최초생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class COEMenuRoleServiceImpl implements COEMenuRoleService {

    //DAO
    private final COEMenuRoleMapper cOEMenuRoleMapper;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    /**
     * 메뉴권한 변경 목록을 조회한다.
     */
    public COEMenuRoleDTO selectMenuRoleLogList(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCOMenuRoleDTO.getPageIndex());
        page.setRecordCountPerPage(pCOMenuRoleDTO.getListRowSize());
        page.setPageSize(pCOMenuRoleDTO.getPageRowSize());

        pCOMenuRoleDTO.setFirstIndex(page.getFirstRecordIndex());
        pCOMenuRoleDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        pCOMenuRoleDTO.setList(cOEMenuRoleMapper.selectMenuRoleLogList(pCOMenuRoleDTO));
        pCOMenuRoleDTO.setTotalCount(cOEMenuRoleMapper.getMenuRoleLogListCnt(pCOMenuRoleDTO));

        return pCOMenuRoleDTO;
    }

    /**
     * 메뉴권한 변경 상세 조회한다.
     */
    public COEMenuRoleDTO selectMenuRoleLogWrite(COEMenuRoleDTO pCOMenuRoleDTO) throws Exception{

        COEMenuRoleDTO info = null;

        if(pCOMenuRoleDTO.getDetailsKey() != null){
            // 정보조회
            info = cOEMenuRoleMapper.selectModDtl(pCOMenuRoleDTO);

            // 이전 메뉴 목록 조회
            pCOMenuRoleDTO.setType("bfre");
            info.setBfreMenuList(cOEMenuRoleMapper.selectModMenuList(pCOMenuRoleDTO));

            // 변경 메뉴 목록 조회
            pCOMenuRoleDTO.setType("chng");

            System.out.println(" @@@@ = " + cOEMenuRoleMapper.selectModMenuList(pCOMenuRoleDTO));

            info.setChngMenuList(cOEMenuRoleMapper.selectModMenuList(pCOMenuRoleDTO));
        }


        return info;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(COEMenuRoleDTO pCoMenuRoleDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("변경자 아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("변경자 이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("변경자 소속");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("대상 아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("대상 이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("대상 소속");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("처리일자");
        cell.setCellStyle(style_header);

        // Body
        List<COEMenuRoleDTO> list = pCoMenuRoleDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(pCoMenuRoleDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //변경자 아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getAdmId());
            cell.setCellStyle(style_body);

            //변경자 이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getAdmNm());
            cell.setCellStyle(style_body);

            //변경자 부서
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getAdmDeptNm());
            cell.setCellStyle(style_body);

            //대상 아이디
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getTrgtAdmId());
            cell.setCellStyle(style_body);

            //대상 이름
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getTrgtAdmNm());
            cell.setCellStyle(style_body);

            //대상부서
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getTrgtAdmDeptNm());
            cell.setCellStyle(style_body);

            //처리일자
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getRegDtm()==null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_메뉴권한변경로그관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCODSysLogDTO = new COSystemLogDTO();

        pCODSysLogDTO.setTrgtMenuNm("시스템 관리 > 로그 관리 > 메뉴권한변경로그");
        pCODSysLogDTO.setSrvcNm("mngwserc.co.cob.service.impl.COEMenuRoleServiceImpl");
        pCODSysLogDTO.setFncNm("selectMenuRoleLogList");
        pCODSysLogDTO.setPrcsCd("DL");
        pCODSysLogDTO.setRsn(pCoMenuRoleDTO.getRsn());
        pCODSysLogDTO.setRegId(lgnCOAAdmDTO.getId());
        pCODSysLogDTO.setRegIp(lgnCOAAdmDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCODSysLogDTO);
    }

}
