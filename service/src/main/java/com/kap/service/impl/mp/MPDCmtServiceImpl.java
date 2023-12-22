package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.seed.COSeedCipherUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpd.MPDKenDto;
import com.kap.core.dto.mp.mpf.MPFFileDto;
import com.kap.service.*;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPDCmtMapper;
import com.kap.service.mp.mpd.MPDCmtService;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

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

    private final EgovIdGnrService coCmssrAtndcSeqIdgen;

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
        mpaUserDto.setTrmsAgmntYn("N");
        mpaUserDto.setPsnifAgmntYn("N");
        mpaUserDto.setPsnif3AgmntYn("N");
        mpaUserDto.setFndnNtfyRcvYn("N");
        mpaUserDto.setCi("");
        mpaUserMapper.insertUserDtl(mpaUserDto);
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return i;
    }

    @Override
    public int updateCmt(MPAUserDto mpaUserDto) throws Exception {
        mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
        mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        return  mpdCmtMapper.updateCmt(mpaUserDto);
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
    public MPDKenDto selectKenMonthList(MPDKenDto mpdKenDto) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        int year = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[0]);
        int month = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[1]);
        mpdKenDto.setYear(String.valueOf(year));
        mpdKenDto.setMnth(String.valueOf(month));

        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<String>();

        for(int day = 1 ; day <= lastDayOfMonth ; day++) {
            LocalDate date = yearMonth.atDay(day);
            String formattedDate = date.format(formatter);
            dates.add(formattedDate);
        }

        page.setCurrentPageNo(mpdKenDto.getPageIndex());
        page.setRecordCountPerPage(mpdKenDto.getListRowSize());

        page.setPageSize(mpdKenDto.getPageRowSize());

        mpdKenDto.setFirstIndex( page.getFirstRecordIndex() );
        mpdKenDto.setRecordCountPerPage( page.getRecordCountPerPage() );

        mpdKenDto.setTotalCount( mpdCmtMapper.selectKenMonthListCnt(mpdKenDto ));
        List<MPDKenDto> mpdKenDtos = mpdCmtMapper.selectKenMonthList(mpdKenDto);

        for (int i = 0; i < mpdKenDtos.size(); i++) {
            List<String> datess = new ArrayList<String>();
            for(int c = 0 ; c < dates.size() ; c++) {
                datess.add("-");
            }
            if(!StringUtils.isEmpty(mpdKenDtos.get(i).getMonthDays())) {
                String[] split = mpdKenDtos.get(i).getMonthDays().split(", ");
                String[] split2 = mpdKenDtos.get(i).getMonthTypesNm().split(", ");
                int length = split.length;
                for (int j = 0; j < dates.size(); j++) {
                    for( int k = 0 ; k < length ; k++) {
                        if(dates.get(j).equals(split[k])) {
                            datess.set(j , split2[k]);
                        }
                    }
                }
            }
            mpdKenDtos.get(i).setKenDays(datess);
        }

        mpdKenDto.setList( mpdKenDtos );
        return mpdKenDto;
    }

    @Override
    public MPDKenDto selectKenMonthTableList(MPDKenDto mpdKenDto) throws Exception {
        int year = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[0]);
        int month = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[1]);
        mpdKenDto.setYear(String.valueOf(year));
        mpdKenDto.setMnth(String.valueOf(month));

        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<String>();
        List<String> weeks = new ArrayList<String>();
        for(int day = 1 ; day <= lastDayOfMonth ; day++) {
            LocalDate date = yearMonth.atDay(day);
            String formattedDate = date.format(formatter);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayOfWeekStr = dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.getDefault()
            );
            dates.add(formattedDate);
            weeks.add(dayOfWeekStr.substring(0,1));

        }

        mpdKenDto.setMonths(dates);
        mpdKenDto.setDays(weeks);

        return mpdKenDto;
    }

    @Override
    public MPDKenDto selectKenMonthCnt(MPDKenDto mpdKenDto) throws Exception {
        return mpdCmtMapper.selectKenMonthCnt(mpdKenDto);

    }

    @Override
    public MPDKenDto selectKenCmpnList(MPDKenDto mpdKenDto) throws Exception {
        List<MPDKenDto> mpdKenDtos = mpdCmtMapper.selectKenCmpnList(mpdKenDto);
        mpdKenDto.setList( mpdKenDtos );
        return mpdKenDto;
    }

    @Override
    public void insertAtend(MPDKenDto mpdKenDto) throws Exception {
        mpdKenDto.setAtndcSeq(coCmssrAtndcSeqIdgen.getNextIntegerId());
        mpdCmtMapper.insertAtend(mpdKenDto);
    }

    @Override
    public MPDKenDto selectKenCmpnDtl(MPDKenDto mpdKenDto) throws Exception {
        return mpdCmtMapper.selectKenCmpnDtl(mpdKenDto);
    }

    @Override
    public MPFFileDto selectKenCmpnKickImage(MPFFileDto mpfFileDto) throws Exception {
        return mpdCmtMapper.selectKenCmpnKickImage(mpfFileDto);
    }

    @Override
    public MPFFileDto selectKenCmpnLvlImage(MPFFileDto mpfFileDto) throws Exception {
        return mpdCmtMapper.selectKenCmpnLvlImage(mpfFileDto);
    }

    @Override
    public void updateCnstgRsumeMst(MPFFileDto mpfFileDto) throws Exception {
        HashMap<String, Integer> kickFile = cOFileService.setFileInfo(mpfFileDto.getKickFile());
        HashMap<String, Integer> lvlFile = cOFileService.setFileInfo(mpfFileDto.getLvlFile());
        mpfFileDto.setKickfFileSeq(kickFile.get(null));
        mpfFileDto.setLvlupFileSeq(lvlFile.get(null));

        mpdCmtMapper.updateCnstgRsumeMst(mpfFileDto);
    }


    @Override
    public void excelDownload(MPDKenDto mpdKenDto, HttpServletResponse response) throws Exception {
        if(mpdKenDto.getExcelType().equals("D")) { //일별 엑셀
            excelDay(mpdKenDto, response);
        }
        else if(mpdKenDto.getExcelType().equals("M")){ //월별 엑셀
            excelMonty(mpdKenDto, response);
        }

    }

    private void excelMonty(MPDKenDto mpdKenDto, HttpServletResponse response) throws Exception {
        int year = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[0]);
        int month = Integer.parseInt(mpdKenDto.getMonthpicker().split("-")[1]);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle style_header = workbook.createCellStyle();
        XSSFCellStyle style_body = workbook.createCellStyle();
        Sheet sheet = workbook.createSheet();

        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dates = new ArrayList<String>();
        List<String> weeks = new ArrayList<String>();

        for(int day = 1 ; day <= lastDayOfMonth ; day++) {
            LocalDate date = yearMonth.atDay(day);
            String formattedDate = date.format(formatter);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayOfWeekStr = dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.getDefault()
            );
            dates.add(formattedDate);
            weeks.add(dayOfWeekStr.substring(0,1));
        }

        Row row = null;
        Cell cell = null;
        int rowNum = 0;
        int column = 0;
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

        //날짜 요일
        cell = row.createCell(0);
        cell.setCellValue("날짜/요일");
        cell.setCellStyle(style_header);
        column++;

        //요일 / 날짜
        for(int i = 0 ; i < dates.size() ; i++) {
            cell = row.createCell((i+1));
            cell.setCellValue((i+1));
            cell.setCellStyle(style_header);
            column++;
        }

        //출장소계
        cell = row.createCell(column++);
        cell.setCellValue("출장소계");
        cell.setCellStyle(style_header);

        //출장
        cell = row.createCell(column++);
        cell.setCellValue("출장");
        cell.setCellStyle(style_header);
        cell = row.createCell(column++);
        cell.setCellStyle(style_header);
        cell = row.createCell(column++);
        cell.setCellStyle(style_header);
        cell = row.createCell(column++);
        cell.setCellStyle(style_header);

        //출장누계
        cell = row.createCell(column++);
        cell.setCellValue("출장누계");
        cell.setCellStyle(style_header);

        column = 0;
        row = sheet.createRow(rowNum++);

        //이름
        cell = row.createCell(0);
        cell.setCellValue("성명");
        cell.setCellStyle(style_header);

        //요일
        for(int i = 0 ; i < weeks.size() ; i++) {
            cell = row.createCell((i+1));
            cell.setCellValue((weeks.get(i).substring(0,1)));
            cell.setCellStyle(style_header);
            column++;
        }

        cell = row.createCell(++column);
        cell.setCellStyle(style_header);
        int start = column;

        CellRangeAddress mergedRegion = new CellRangeAddress(0, 1, start, start);
        sheet.addMergedRegion(mergedRegion);

        //cell 나누기
        for (int i = start+1; i <= start+4; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style_header);
            if(i == start+1){
                cell.setCellValue("지도");
            } else if(i == start+2) {
                cell.setCellValue("강의");
            } else if(i == start+3) {
                cell.setCellValue("역량개발");
            } else if(i == start+4) {
                cell.setCellValue("기타");
            }
        }

        column = start+4;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, start+1,column));

        cell = row.createCell(++column);
        cell.setCellStyle(style_header);

        CellRangeAddress mergedRegion2 = new CellRangeAddress(0, 1, column, column);
        sheet.addMergedRegion(mergedRegion2);


        List<MPDKenDto> list = mpdKenDto.getList();

        if(list.size() >= 1) {
            //일별 사용자 근태옵션 그리기
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(rowNum++);
                int atndnPeople = 0; //출근
                int annualPeople = 0; //연차
                int engPeople = 0 ; //지도
                int lecturePeople = 0 ; //강의
                int cmptnDvlpmPeople = 0; //역량 개발
                int etcPeople = 0; // 기타
                //날짜
                cell = row.createCell(0);
                cell.setCellValue(list.get(i).getName());
                cell.setCellStyle(style_body);

                if(!StringUtils.isEmpty(list.get(i).getMonthDays())) {
                    String[] split = list.get(i).getMonthDays().split(", ");
                    String[] split2 = list.get(i).getMonthTypes().split(", ");
                    String[] split3 = list.get(i).getMonthTypesNm().split(", ");
                    int length = split.length;
                    for (int j = 0; j < dates.size(); j++) {
                        cell = row.createCell(j + 1);
                        cell.setCellStyle(style_body);
                        for (int k = 0; k < length; k++) {
                            if (dates.get(j).equals(split[k])) {
                                cell.setCellValue(split3[k]);
                                switch (split2[k]){
                                    case "CMSSR_ATTEND_002": //출근
                                        atndnPeople++;
                                        break;
                                    case "CMSSR_ATTEND_005":  //연차
                                        annualPeople++;
                                        break;
                                    case "CMSSR_ATTEND_001":       //지도
                                        engPeople++;
                                        break;
                                    case "CMSSR_ATTEND_003":       //강의
                                        lecturePeople++;
                                        break;
                                    case "CMSSR_ATTEND_004":       //역량 개발
                                        cmptnDvlpmPeople++;
                                        break;
                                    case "CMSSR_ATTEND_007":   //기타
                                        etcPeople++;
                                        break;
                                    default:
                                        log.info("값이 없다.");
                                        break;
                                }
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < dates.size(); j++) {
                        cell = row.createCell(j+1);
                        cell.setCellStyle(style_body);
                    }
                }
                cell = row.createCell(dates.size()+1);
                cell.setCellValue(atndnPeople);
                cell.setCellStyle(style_body);
                cell = row.createCell(dates.size()+2);
                cell.setCellValue(engPeople);
                cell.setCellStyle(style_body);
                cell = row.createCell(dates.size()+3);
                cell.setCellValue(cmptnDvlpmPeople);
                cell.setCellStyle(style_body);
                cell = row.createCell(dates.size()+4);
                cell.setCellValue(lecturePeople);
                cell.setCellStyle(style_body);
                cell = row.createCell(dates.size()+5);
                cell.setCellValue(etcPeople);
                cell.setCellStyle(style_body);
                cell = row.createCell(dates.size()+6);
                cell.setCellValue(list.get(i).getTotal());
                cell.setCellStyle(style_body);
            }

            List<String> kentae = new ArrayList<String>();
            List<String> kentaeCd = new ArrayList<String>();

            //TODO 양현우 코드 수정 하기
            kentae.add("출근");
            kentae.add("재택");
            kentae.add("연차");
            kentae.add("지도");
            kentae.add("강의");
            kentae.add("역량개발");
            kentae.add("기타");

            kentaeCd.add("CMSSR_ATTEND_002");
            kentaeCd.add("CMSSR_ATTEND_006");
            kentaeCd.add("CMSSR_ATTEND_005");
            kentaeCd.add("CMSSR_ATTEND_001");
            kentaeCd.add("CMSSR_ATTEND_003");
            kentaeCd.add("CMSSR_ATTEND_004");
            kentaeCd.add("CMSSR_ATTEND_007");

            //출근 , 재택 , 연차 , 지도 , 강의 ,역량개발 , 기타 그리기
            for (int i = 0; i < kentae.size(); i++) {
                row = sheet.createRow(rowNum++);

                //날짜
                cell = row.createCell(0);
                cell.setCellValue(kentae.get(i));
                cell.setCellStyle(style_body);


                for(int j = 0 ; j < dates.size() ; j++) {
                    int atndnDate = 0;
                    for(int k = 0 ; k < list.size();k++) {
                        if (!StringUtils.isEmpty(list.get(k).getMonthDays())) {
                            String[] split = list.get(k).getMonthDays().split(", ");
                            String[] split2 = list.get(k).getMonthTypes().split(", ");
                            int length = split.length;
                            for (int z = 0; z < length; z++) {
                                if (dates.get(j).equals(split[z])) {
                                    if(split2[z].equals(kentaeCd.get(i))){
                                        atndnDate++;
                                    }
                                }
                            }
                        }
                    }
                    cell = row.createCell(j+1);

                    cell.setCellValue(atndnDate);
                    cell.setCellStyle(style_body);

                }
            }

            //계 그리기
            int rowNumbers = rowNum;
            row = sheet.createRow(rowNum++);

            cell = row.createCell(0);
            cell.setCellValue("계");
            cell.setCellStyle(style_header);

            //근태 옵션 계
            for(int i = 1 ; i < dates.size()+1 ; i++) {
                int totals =  0;
                for(int j = list.size()+2; j < rowNumbers ; j++) {
                    Row row22 = sheet.getRow(j);
                    Cell cellB1 = row22.getCell(i);
                    int value = (int) cellB1.getNumericCellValue();
                    totals +=value;
                }
                cell = row.createCell(i);
                cell.setCellValue(totals);
                cell.setCellStyle(style_header);
            }

            //출장소계 , 출장 ,출장누계
            for(int i = dates.size()+1 ; i < dates.size()+7 ; i++) {
                int totals =  0;
                for(int j = 2; j < list.size()+2 ; j++) {
                    Row row22 = sheet.getRow(j);
                    Cell cellB1 = row22.getCell(i);
                    int value = (int) cellB1.getNumericCellValue();
                    totals +=value;
                }
                cell = row.createCell(i);
                cell.setCellValue(totals);
                cell.setCellStyle(style_header);
            }

            //출장 계 셀 합치기 위해서  sum 후 병합
            int totalv = 0;
            for(int i = dates.size()+2 ; i < dates.size()+6 ; i++) {
                Row row22 =  sheet.getRow(rowNum-1);
                Cell cellB1 = row22.getCell(i);
                int value = (int) cellB1.getNumericCellValue();
                totalv +=value;
            }
            Row rowChange = sheet.getRow(rowNum-1);
            Cell cellChange = rowChange.getCell(dates.size()+2);

            cellChange.setCellValue(totalv);
            sheet.addMergedRegion(new CellRangeAddress(rowNum-1, rowNum-1, dates.size()+2,dates.size()+5));

        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_위원_월_근태_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("위원 관리 > 월 근태");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPDCmtServiceImpl");
        pCoSystemLogDTO.setFncNm("selectKenMonthList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn("월 근태 엑셀 다운로드");
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);

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

        if(list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                //TODO 코드 정의 후 밑 주석 해제 후 이 부분 삭제
                switch (list.get(i).getAtndcCd()) {
                    case "CMSSR_ATTEND_002": //출근
                        atndn++;
                        break;
                    case "CMSSR_ATTEND_005":  //연차
                        annual++;
                        break;
                    case "CMSSR_ATTEND_001":       //지도
                        eng++;
                        break;
                    case "CMSSR_ATTEND_003":       //강의
                        lecture++;
                        break;
                    case "CMSSR_ATTEND_004":       //역량 개발
                        cmptnDvlpm++;
                        break;
                    case "CMSSR_ATTEND_007":   //기타
                        etc++;
                        break;
                    default:
                        log.info("값이 없다.");
                        break;
                }

            }


            for (int i = 0; i < list.size(); i++) {
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

        if(list.size() >=2) {
            CellRangeAddress mergedRegion = new CellRangeAddress(2, list.size() + 1, 0, 0);
            sheet.addMergedRegion(mergedRegion);

            for (int i = 11; i <= 17; i++) {
                CellRangeAddress mergedRegion2 = new CellRangeAddress(2, list.size() + 1, i, i);
                sheet.addMergedRegion(mergedRegion2);
            }
        }


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
        COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("위원 관리 > 일일 근태");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPDCmtServiceImpl");
        pCoSystemLogDTO.setFncNm("selectKenList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn("일일 근태 엑셀 다운로드");
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);

    }
}