package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.mp.mpg.MPGWthdrwDto;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPAUserService;
import com.kap.service.dao.mp.MPGWthdrwMapper;
import com.kap.service.mp.mpg.MPGWthdrwService;
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
 * 탈퇴 사용자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPAUserServiceImpl.java
 * @Description		: 탈퇴사용자 관리를 위한 ServiceImpl
 * @author 양현우
 * @since 2023.11.28
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.28		양현우				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPGWthdrwServiceImpl  implements MPGWthdrwService {

    private final MPGWthdrwMapper mpgWthdrwMapper;

    private final COSystemLogService cOSystemLogService;

    @Override
    public MPGWthdrwDto selectWthdrwList(MPGWthdrwDto mpgWthdrwDto) {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpgWthdrwDto.getPageIndex());
        page.setRecordCountPerPage(mpgWthdrwDto.getListRowSize());

        page.setPageSize(mpgWthdrwDto.getPageRowSize());

        mpgWthdrwDto.setFirstIndex( page.getFirstRecordIndex() );
        mpgWthdrwDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpgWthdrwDto.setTotalCount( mpgWthdrwMapper.selectWthdrwListCnt(mpgWthdrwDto ));
        List<MPGWthdrwDto> mpgWthdrwDtos = mpgWthdrwMapper.selectWthdrwList(mpgWthdrwDto);

        mpgWthdrwDto.setList( mpgWthdrwDtos );
        return mpgWthdrwDto;
    }

    @Override
    public void excelDownload(MPGWthdrwDto mpgWthdrwDto, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("회원분류");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("아이디");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("탈퇴 사유");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("탈퇴일");
        cell.setCellStyle(style_header);


        // Body
        List<MPGWthdrwDto> list = mpgWthdrwDto.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpgWthdrwDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //회원분류
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getMemCdNm());
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getId());
            cell.setCellStyle(style_body);

            //탈퇴 사유
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getWthdrwRsnNm());
            cell.setCellStyle(style_body);


            //등록일
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getWthdrwRegDtm() == null ? "-" : list.get(i).getWthdrwRegDtm().substring(0, list.get(i).getWthdrwRegDtm().lastIndexOf(":")));
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_탈퇴회원관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("탈퇴회원관리 ");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPGWthdrwServiceImpl");
        pCoSystemLogDTO.setFncNm("selectWthdrwList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpgWthdrwDto.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);

    }
}