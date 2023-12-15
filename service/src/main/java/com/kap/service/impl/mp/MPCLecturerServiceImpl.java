package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.core.dto.mp.mpc.MPCLecturerDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPCLecturerService;
import com.kap.service.dao.mp.MPCLecturerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 강사 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyServiceImpl.java
 * @Description		: 강사 관리를 위한 ServiceImpl
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
public class MPCLecturerServiceImpl implements MPCLecturerService {

    // DAO
    private final MPCLecturerMapper mpcLecturerMapper;
    
    /* 시퀀스 */
    private final EgovIdGnrService mpcLecturerDtlIdgen;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    /**
     * 강사 목록을 조회한다.
     */
    public MPCLecturerDTO selectLecturerList(MPCLecturerDTO mpcLecturerDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpcLecturerDTO.getPageIndex());
        page.setRecordCountPerPage(mpcLecturerDTO.getListRowSize());

        page.setPageSize(mpcLecturerDTO.getPageRowSize());

        mpcLecturerDTO.setFirstIndex(page.getFirstRecordIndex());
        mpcLecturerDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpcLecturerDTO.setList(mpcLecturerMapper.selectLecturerList(mpcLecturerDTO));
        mpcLecturerDTO.setTotalCount(mpcLecturerMapper.selectLecturerCnt(mpcLecturerDTO));

        return mpcLecturerDTO;
    }

    /**
     * 강사 개수를 조회한다.
     */
    public int selectLecturerCnt(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectLecturerCnt(mpcLecturerDTO);
    }

    /**
     * 상세를 조회한다.
     */
    public MPCLecturerDTO selectLecturerDtl(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectLecturerDtl(mpcLecturerDTO);
    }

    /**
     * 이메일 중복 체크를 한다.
     */
    public int selectDupEmail(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.selectDupEmail(mpcLecturerDTO);
    }

    /**
     * 강사를 등록한다.
     */
    public int insertLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        mpcLecturerDTO.setIsttrSeq(mpcLecturerDtlIdgen.getNextIntegerId());
       return mpcLecturerMapper.insertLecturer(mpcLecturerDTO);
    }

    /**
     * 강사를 수정한다.
     */
    public int updateLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.updateLecturer(mpcLecturerDTO);
    }

    /**
     * 강사를 삭제한다.
     */
    public int deleteLecturer(MPCLecturerDTO mpcLecturerDTO) throws Exception {
        return mpcLecturerMapper.deleteLecturer(mpcLecturerDTO);
    }

    /**
     * 상생 사업 현황 목록을 조회한다.
     */
    public MPCLecturerDTO selectWinBusinessList(MPCLecturerDTO mpcLecturerDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpcLecturerDTO.getPageIndex());
        page.setRecordCountPerPage(mpcLecturerDTO.getListRowSize());

        page.setPageSize(mpcLecturerDTO.getPageRowSize());

        mpcLecturerDTO.setFirstIndex(page.getFirstRecordIndex());
        mpcLecturerDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpcLecturerDTO.setList(mpcLecturerMapper.selectWinBusinessList(mpcLecturerDTO));
        mpcLecturerDTO.setTotalCount(mpcLecturerMapper.selectWinBusinessCnt(mpcLecturerDTO));

        return mpcLecturerDTO;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(EBBEpisdDTO eBBEpisdDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("회차");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("교육상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("과정분류");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("과정명");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("학습방식");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("학습시간");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("교육기간");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("강사");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("강사 소속");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("모집방식");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("선발여부");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("교육장소");
        cell.setCellStyle(style_header);

        // Body
        List<EBBEpisdDTO> list = eBBEpisdDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(eBBEpisdDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getEpisdYear());
            cell.setCellStyle(style_body);

            //회차
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getEpisdOrd());
            cell.setCellStyle(style_body);

            //교육상태
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getEdctnStatusNm());
            cell.setCellStyle(style_body);

            //과정분류
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getPrntCdNm() + ">" + list.get(i).getCtgryCdNm());
            cell.setCellStyle(style_body);

            //과정명
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getNm());
            cell.setCellStyle(style_body);

            //학습방식
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getStduyMthdCdNm());
            cell.setCellStyle(style_body);

            //학습시간
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getStduyDdCdNm() + "일/" + list.get(i).getStduyTimeCdNm() + "시간");
            cell.setCellStyle(style_body);

            //교육기간
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getEdctnStrtDtm() == null || list.get(i).getEdctnEndDtm() == null ? "-" : list.get(i).getEdctnStrtDtm().substring(0, list.get(i).getEdctnStrtDtm().lastIndexOf(":")) + "~" + list.get(i).getEdctnEndDtm().substring(0, list.get(i).getEdctnEndDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

            //강사
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getIsttrName());
            cell.setCellStyle(style_body);

            //강사 소속
            cell = row.createCell(10);
            cell.setCellValue(Objects.equals(list.get(i).getIsttrOutCnt(), "") ? list.get(i).getFfltnNm() : list.get(i).getIsttrOutCnt() + "외 " + list.get(i).getIsttrOutCnt() + "명");
            cell.setCellStyle(style_body);

            //모집방식
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getRcrmtMthdCdNm());
            cell.setCellStyle(style_body);

            //선발여부
            cell = row.createCell(12);
            cell.setCellValue("선발여부값"); // 확인필요
            cell.setCellStyle(style_body);

            //교육장소
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getPlaceNm());
            cell.setCellStyle(style_body);

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_강사_및_위탁위원_관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 강사 및 위탁위원 관리 > 교육 사업 현황");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPCLecturerServiceImpl");
        pCoSystemLogDTO.setFncNm("selectEpisdList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(eBBEpisdDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }
}
