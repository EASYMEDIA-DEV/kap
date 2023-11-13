package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.annotation.MaskedFieldProcessor;
import com.kap.core.dto.*;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPAUserService;
import com.kap.service.dao.mp.MPAUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <pre>
 * 일반 사용자 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPAUserServiceImpl.java
 * @Description		: 일반사용자 관리를 위한 ServiceImpl
 * @author 신혜정
 * @since 2022.04.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		양현우				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPAUserServiceImpl implements MPAUserService {

    private final MPAUserMapper mpaUserMapper;

    private final COSystemLogService cOSystemLogService;


    /**
     * 일반 사용자 조회
     * @param mpaUserDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAUserDto selectUserList(MPAUserDto mpaUserDto) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaUserDto.getPageIndex());
        page.setRecordCountPerPage(mpaUserDto.getListRowSize());

        page.setPageSize(mpaUserDto.getPageRowSize());

        mpaUserDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaUserDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaUserDto.setTotalCount( mpaUserMapper.selectUserListCnt(mpaUserDto ));
        List<MPAUserDto> mpaUserDtos = mpaUserMapper.selectUserList(mpaUserDto);

        if(mpaUserDto.getExcelYn().equals("Y")) {

        } else {
            MaskedFieldProcessor.process(mpaUserDtos);
        }

        mpaUserDto.setList( mpaUserDtos );
        return mpaUserDto;
    }

    /**
     * 일반 사용자 기본 정보 조회
     * @param mpaUserDto
     * @return
     */
    public MPAUserDto selectUserDtl(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectUserDtl(mpaUserDto);
    }

    /**
     * 일반 사용자 회원 상세 조회
     * @param mpaUserDto
     * @return
     */
    public MPAUserDto selectUserDtlTab(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectUserDtlTab(mpaUserDto);
    }

    /**
     * 이메일 중복 검사
     * @param mpaUserDto
     * @return
     */
    public int selectDupEmail(MPAUserDto mpaUserDto) {
        return mpaUserMapper.selectDupEmail(mpaUserDto);
    }

    /**
     * 회원 수정
     */
    public int updateUserDtl(MPAUserDto mpaUserDto) {
        mpaUserDto.setModSeq(Integer.parseInt(mpaUserMapper.selectSeqNum(mpaUserDto)));
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return mpaUserMapper.updateUserDtl(mpaUserDto);
    }


    /**
     * 일반 사용자 조회
     * @param mpaAttctnDto
     * @return
     * @throws Exception
     */
    @Override
    public MPAAttctnDto selectAttcntList(MPAAttctnDto mpaAttctnDto) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaAttctnDto.getPageIndex());
        page.setRecordCountPerPage(mpaAttctnDto.getListRowSize());

        page.setPageSize(mpaAttctnDto.getPageRowSize());

        mpaAttctnDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaAttctnDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaAttctnDto.setTotalCount( mpaUserMapper.selectAttcntListCnt(mpaAttctnDto));
        List<MPAAttctnDto> mpaAttctnDtos = mpaUserMapper.selectAttcntList(mpaAttctnDto);

        mpaAttctnDto.setList( mpaAttctnDtos );
        return mpaAttctnDto;
    }

    @Override
    public MPAInqrDto selectInqrList(MPAInqrDto mpaInqrDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpaInqrDto.getPageIndex());
        page.setRecordCountPerPage(mpaInqrDto.getListRowSize());

        page.setPageSize(mpaInqrDto.getPageRowSize());

        mpaInqrDto.setFirstIndex( page.getFirstRecordIndex() );
        mpaInqrDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpaInqrDto.setTotalCount( mpaUserMapper.selectInqrListCnt(mpaInqrDto));
        List<MPAInqrDto> mpaInprDtos = mpaUserMapper.selectInqrList(mpaInqrDto);

        mpaInqrDto.setList( mpaInprDtos );
        return mpaInqrDto;
    }


    @Override
    public void excelDownload(MPAUserDto mpaUserDto, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("가입일");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("최종수정자");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("최종 수정일시");
        cell.setCellStyle(style_header);

        // Body
        List<MPAUserDto> list = mpaUserDto.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpaUserDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getId());
            cell.setCellStyle(style_body);

            //이름
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //휴대폰
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getHpNo().substring(0, 3)+"-"+list.get(i).getHpNo().substring(3, 7)+"-"+list.get(i).getHpNo().substring(7, 11));
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //가입일
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getRegDtm() == null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

            //최종수정자
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getModName());
            cell.setCellStyle(style_body);

            //가입일
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getModDtm() == null ? "-" : list.get(i).getModDtm().substring(0, list.get(i).getModDtm().lastIndexOf(":")));
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_일반회원관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 일반회원관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPAUserServiceImpl");
        pCoSystemLogDTO.setFncNm("selectUserList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpaUserDto.getRsn());
        pCoSystemLogDTO.setRegId(lgnCOAAdmDTO.getId());
        pCoSystemLogDTO.setRegIp(lgnCOAAdmDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }


}

