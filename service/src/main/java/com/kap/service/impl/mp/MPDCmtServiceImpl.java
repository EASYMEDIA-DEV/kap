package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.MPAUserDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.service.*;
import com.kap.service.MPAUserService;
import com.kap.service.MPDCmtService;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPDCmtMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 * 위원 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPDCmtServiceImpl.java
 * @Description		: 위원 관리를 위한 ServiceImpl
 * @author 양현우
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		양현우				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPDCmtServiceImpl implements MPDCmtService {

    private final COFileService cOFileService;

    private final EgovIdGnrService memSeqIdgen;

    private final EgovIdGnrService memModSeqIdgen;


    private final MPAUserMapper mpaUserMapper;

    private final MPDCmtMapper mpdCmtMapper;

    private final COSystemLogService cOSystemLogService;


    @Override
    public int insertCmt(MPAUserDto mpaUserDto) throws Exception {

        if(mpaUserDto.getFileList() != null && !mpaUserDto.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(mpaUserDto.getFileList());
            mpaUserDto.setCmssrPhotoFileSeq(fileSeqMap.get("fileSeq"));
        }
        mpaUserDto.setMemCd("CS");
        mpaUserDto.setMemSeq(memSeqIdgen.getNextIntegerId());
        mpaUserDto.setEncPwd(COSeedCipherUtil.encryptPassword(mpaUserDto.getPwd(), mpaUserDto.getId()));
        int i = mpdCmtMapper.insertCmt(mpaUserDto);
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return i;
    }

    @Override
    public int deleteCmt(MPAUserDto mpaUserDto) throws Exception {
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return  mpdCmtMapper.deleteCmt(mpaUserDto);
    }

    @Override
    public MPDKenDto selectKenList(MPDKenDto mpdKenDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpdKenDto.getPageIndex());
        page.setRecordCountPerPage(mpdKenDto.getListRowSize());

        page.setPageSize(mpdKenDto.getPageRowSize());

        mpdKenDto.setFirstIndex( page.getFirstRecordIndex() );
        mpdKenDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        mpdKenDto.setYear(mpdKenDto.getMonthpicker().split("-")[0]);
        mpdKenDto.setMnth(mpdKenDto.getMonthpicker().split("-")[1]);
         if(mpdKenDto.getMonthpicker().split("-").length ==3) {
             mpdKenDto.setDd(mpdKenDto.getMonthpicker().split("-")[2]);
         }

        mpdKenDto.setTotalCount( mpdCmtMapper.selectKenListCnt(mpdKenDto ));
        List<MPDKenDto> mpdKenDtos = mpdCmtMapper.selectKenList(mpdKenDto);

        mpdKenDto.setList( mpdKenDtos );
        return mpdKenDto;
    }

    @Override
    public void excelDownload(MPDKenDto mpdKenDto, HttpServletResponse response) throws Exception {
        if(mpdKenDto.getExcelType().equals("D")) { //일별 엑셀
            excelDay(mpdKenDto, response);
        } else if(mpdKenDto.getExcelType().equals("M")){ //월별 엑셀

        }

    }

    private String excelHypen(String str) {
        return StringUtils.isEmpty(str) ? "-" : str;
    }

    private void excelDay(MPDKenDto mpdKenDto, HttpServletResponse response) throws IOException {

        int atndn = 0; //출근
        int annual = 0; //연차
        int eng = 0 ; //지도
        int lecture = 0 ; //강의
        int cmptnDvlpm = 0; //역량 개발
        int etc = 0; //기타
        String outputDateStr="";
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdfInput.parse(mpdKenDto.getMonthpicker());
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd EEE", Locale.KOREAN);
            outputDateStr = sdfOutput.format(date);

        } catch (ParseException e) {
            e.printStackTrace();

        }
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
        cell.setCellValue("날짜");
        cell.setCellStyle(style_header);

        cell = row.createCell(1);
        cell.setCellValue("번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("이름");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("근태옵션");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("지도부품사1");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("소재지역");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("지도부품사2");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("소재지역2");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("기타출장");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("기타");
        cell.setCellStyle(style_header);


        cell = row.createCell(10);
        cell.setCellValue("근태체크일시");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("출근");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("연차");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("출장");
        cell.setCellStyle(style_header);
        cell = row.createCell(14);
        cell.setCellStyle(style_header);
        cell = row.createCell(15);
        cell.setCellStyle(style_header);
        cell = row.createCell(16);
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("총원");
        cell.setCellStyle(style_header);


        row = sheet.createRow(rowNum++);
        for (int i = 0; i <= 17; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style_header);
//            StringUtils.
            if (i != 13 && i != 14 && i != 15 && i != 16) {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i)); //열시작, 열종료, 행시작, 행종료 (자바배열과 같이 0부터 시작)
            } else if(i == 13){
                cell.setCellValue("지도");
            } else if(i == 14) {
                cell.setCellValue("강의");
            } else if(i == 15) {
                cell.setCellValue("역량개발");
            } else if(i == 16) {
                cell.setCellValue("기타");
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13,16));

        // Body
        List<MPDKenDto> list = mpdKenDto.getList();

        for(int i = 0 ; i < list.size() ; i++) {
            //TODO 코드 정의 후 밑 주석 해제 후 이 부분 삭제
            switch(list.get(i).getAtndcCd()) {
                case "a" :
                    atndn++;
                    break;
                case "b" :
                    annual++;
                    break;
                case "c" :
                    eng++;
                    break;
                case "d" :
                    lecture++;
                    break;
                case "e" :
                    cmptnDvlpm++;
                    break;
                case "f" :
                    etc++;
                    break;
                default :
                    log.info("값이 없다.");
                    break;
            }

            //            switch(list.get(i).getAtndcCdNm()) {
//                case "출근" :
//                    atndn++;
//                    break;
//                case "연차" :
//                    annual++;
//                    break;
//                case "지도" :
//                    eng++;
//                    break;
//                case "강의" :
//                    lecture++;
//                    break;
//                case "역량개발" :
//                    cmptnDvlpm++;
//                    break;
//                case "기타" :
//                    etc++;
//                    break;
//                default :
//                    log.info("값이 없다.");
//                    break;
//            }
        }


        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //날짜
                cell = row.createCell(0);
                cell.setCellValue(outputDateStr);
                cell.setCellStyle(style_body);
            //번호
            cell = row.createCell(1);
            cell.setCellValue(mpdKenDto.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //아이디
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //근태옵션
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getAtndcCdNm());
            cell.setCellStyle(style_body);





            //지도부품사1
            cell = row.createCell(4);
            cell.setCellValue(excelHypen(list.get(i).getGuidePartCmpn1()));
            cell.setCellStyle(style_body);


            //소재지역1
            cell = row.createCell(5);
            cell.setCellValue(excelHypen(list.get(i).getRgns1()));
            cell.setCellStyle(style_body);

            //지도부품사2
            cell = row.createCell(6);
            cell.setCellValue(excelHypen(list.get(i).getGuidePartCmpn2()));
            cell.setCellStyle(style_body);

            //소재지역2
            cell = row.createCell(7);
            cell.setCellValue(excelHypen(list.get(i).getRgns2()));
            cell.setCellStyle(style_body);

            //기타출장
            cell = row.createCell(8);
            cell.setCellValue(excelHypen(list.get(i).getEtcBsntrp()));
            cell.setCellStyle(style_body);

            //기타
            cell = row.createCell(9);
            cell.setCellValue(excelHypen(list.get(i).getEtc()));
            cell.setCellStyle(style_body);

            //근태체크일시
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getRegDtm());
            cell.setCellStyle(style_body);

            //출근
            cell = row.createCell(11);
            cell.setCellValue(atndn);
            cell.setCellStyle(style_body);

            //연차
            cell = row.createCell(12);
            cell.setCellValue(annual);
            cell.setCellStyle(style_body);

            //지도
            cell = row.createCell(13);
            cell.setCellValue(eng);
            cell.setCellStyle(style_body);

            //강의
            cell = row.createCell(14);
            cell.setCellValue(lecture);
            cell.setCellStyle(style_body);

            //역량개발
            cell = row.createCell(15);
            cell.setCellValue(cmptnDvlpm);
            cell.setCellStyle(style_body);

            //기타
            cell = row.createCell(16);
            cell.setCellValue(etc);
            cell.setCellStyle(style_body);

            //총원
            cell = row.createCell(17);
            cell.setCellValue(list.size());
            cell.setCellStyle(style_body);

        }





        CellRangeAddress mergedRegion = new CellRangeAddress(2, list.size()+1,0,0 );
        sheet.addMergedRegion(mergedRegion);

        for(int i = 11 ; i<=17 ; i++) {
            CellRangeAddress mergedRegion2 = new CellRangeAddress(2, list.size()+1,i,i );
            sheet.addMergedRegion(mergedRegion2);
        }





        // 열 너비 설정
       /* for(int i =0; i < 8; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i)  + 800));
        }*/

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_위원_일일_근태_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COAAdmDTO lgnCOAAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("위원 관리 > 일일 근태");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPDCmtServiceImpl");
        pCoSystemLogDTO.setFncNm("selectKenList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpdKenDto.getRsn());
        pCoSystemLogDTO.setRegId(lgnCOAAdmDTO.getId());
        pCoSystemLogDTO.setRegIp(lgnCOAAdmDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);

    }
}