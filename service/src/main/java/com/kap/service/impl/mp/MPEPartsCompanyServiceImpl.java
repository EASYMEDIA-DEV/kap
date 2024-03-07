package com.kap.service.impl.mp;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultListDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.wbg.WBGAExamSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.service.COSystemLogService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 부품사 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: MPEPartsCompanyServiceImpl.java
 * @Description		: 부품사 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.09
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.09		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MPEPartsCompanyServiceImpl implements MPEPartsCompanyService {

    // DAO
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;

    /* 회사 테이블 시퀀스 */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /* SQ정보 테이블 시퀀스 */
    private final EgovIdGnrService mpePartsCompanySqInfoDtlIdgen;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    COStringUtil stringUtil;

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());

        page.setPageSize(mpePartsCompanyDTO.getPageRowSize());

        mpePartsCompanyDTO.setFirstIndex(page.getFirstRecordIndex());
        mpePartsCompanyDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectPartsCompanyList(mpePartsCompanyDTO));
        mpePartsCompanyDTO.setTotalCount(mpePartsCompanyMapper.selectPartsCompanyCnt(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 부품사 개수를 조회한다.
     */
    @Override
    public int selectPartsCompanyCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectPartsCompanyCnt(mpePartsCompanyDTO);
    }

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsComSQInfo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());

        page.setPageSize(mpePartsCompanyDTO.getPageRowSize());

        mpePartsCompanyDTO.setFirstIndex(page.getFirstRecordIndex());
        mpePartsCompanyDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectPartsComSQInfo(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 상세를 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 부품사 사업자번호 등록여부를 확인한다.
     */
    public MPEPartsCompanyDTO checkBsnmNo(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.checkBsnmNo(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 등록한다.
     */
    public int insertPartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        if (mpePartsCompanyDTO.getCtgryCd().equals("COMPANY01002")) {
            List<String> sqList1 = mpePartsCompanyDTO.getSqInfoList1();
            List<String> sqList2 = mpePartsCompanyDTO.getSqInfoList2();
            List<String> sqList3 = mpePartsCompanyDTO.getSqInfoList3();

            List<List<String>> allSqLists = new ArrayList<>();
            allSqLists.add(sqList1);
            allSqLists.add(sqList2);
            allSqLists.add(sqList3);

            String nm = "";
            int score = 0;
            int year = 0;
            String crtfnCmnNm = "";
            int index = 1;

            sqLoop:
            for(List<String> sqList : allSqLists) {
                for (int i = 1; i < sqList.size(); i++) {
                    String info = sqList.get(i);
                    if(info == null || info.isEmpty()) {
                        continue sqLoop;
                    }
                }
                nm = sqList.get(1);
                year = Integer.parseInt(sqList.get(3));
                score = Integer.parseInt(sqList.get(2));
                crtfnCmnNm = sqList.get(4);

                mpePartsCompanyDTO.setNm(nm);
                mpePartsCompanyDTO.setYear(year);
                mpePartsCompanyDTO.setScore((double) score);
                mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmnNm);
                mpePartsCompanyDTO.setCbsnSeq(mpePartsCompanySqInfoDtlIdgen.getNextIntegerId());

                mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                index += 1;
            }
        }
        return mpePartsCompanyMapper.insertPartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 수정한다.
     */
    public int updatePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        mpePartsCompanyDTO.setModId(cOUserDetailsDTO.getId());
        mpePartsCompanyDTO.setModIp(cOUserDetailsDTO.getLoginIp());

        mpePartsCompanyMapper.deletePartsComSQInfo(mpePartsCompanyDTO);

        List<String> sqList1 = mpePartsCompanyDTO.getSqInfoList1();
        List<String> sqList2 = mpePartsCompanyDTO.getSqInfoList2();
        List<String> sqList3 = mpePartsCompanyDTO.getSqInfoList3();

        List<List<String>> allSqLists = new ArrayList<>();
        allSqLists.add(sqList1);
        allSqLists.add(sqList2);
        allSqLists.add(sqList3);

        String seq = "";
        String nm = "";
        String score = "";
        String year = "";
        String crtfnCmnNm = "";

        if (mpePartsCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

            int index = 1;
            for(List<String> sqList : allSqLists) {
                if(!sqList.isEmpty()) {
                    seq = sqList.get(0);
                    nm = sqList.get(1);
                    year = sqList.get(3);
                    score = sqList.get(2);
                    crtfnCmnNm = sqList.get(4);

                    if(year.equals("")|| year.isEmpty()) {
                        mpePartsCompanyDTO.setYear(null);
                    } else {
                        mpePartsCompanyDTO.setYear(Integer.valueOf(year));
                    }

                    if(score.equals("")|| score.isEmpty()) {
                        mpePartsCompanyDTO.setScore(null);
                    } else {
                        mpePartsCompanyDTO.setScore(Double.valueOf(score));
                    }
                    mpePartsCompanyDTO.setNm(nm);
                    mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmnNm);

                    // 2차인 경우, 스타등급 빈 값 처리
                    mpePartsCompanyDTO.setTchlg5StarCd(null);
                    mpePartsCompanyDTO.setPay5StarCd(null);
                    mpePartsCompanyDTO.setQlty5StarCd(null);
                    mpePartsCompanyDTO.setTchlg5StarYear(null);
                    mpePartsCompanyDTO.setPay5StarYear(null);
                    mpePartsCompanyDTO.setQlty5StarYear(null);

                    mpePartsCompanyDTO.setCbsnSeq(mpePartsCompanySqInfoDtlIdgen.getNextIntegerId());
                    mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                }
                index += 1;
            }
        } else {
            // 2차사가 아닌 경우, SQ정보 빈 값 처리
            mpePartsCompanyMapper.deletePartsComSQInfo(mpePartsCompanyDTO);
        }
        return mpePartsCompanyMapper.updatePartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 부품사를 삭제한다.
     */
    @Transactional
    public int deletePartsCompany(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.deletePartsCompany(mpePartsCompanyDTO);
    }

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectEduCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectEduCnt(mpePartsCompanyDTO);
    }

    /**
     * 교육사업 개수를 조회한다.
     */
    public int selectConsultingCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectConsultingCnt(mpePartsCompanyDTO);
    }

    /**
     * 상생사업 개수를 조회한다.
     */
    public int selectWinBusinessCnt(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectWinBusinessCnt(mpePartsCompanyDTO);
    }

    /**
     * 교육사업 연도별 개수 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectEduStatisticsCntList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());
        page.setPageSize(mpePartsCompanyDTO.getPageRowSize());
        mpePartsCompanyDTO.setFirstIndex(page.getFirstRecordIndex());
        mpePartsCompanyDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        mpePartsCompanyDTO.setList(mpePartsCompanyMapper.selectEduStatisticsCntList(mpePartsCompanyDTO));
        return mpePartsCompanyDTO;
    }

    /**
     * 자동차부품산업대상 목록을 조회한다.
     */
    public WBJAcomSearchDTO selectCarTargetList(WBJAcomSearchDTO wbjAcomSearchDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(wbjAcomSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wbjAcomSearchDTO.getListRowSize());
        page.setPageSize(wbjAcomSearchDTO.getPageRowSize());
        wbjAcomSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wbjAcomSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        wbjAcomSearchDTO.setList(mpePartsCompanyMapper.selectCarTargetList(wbjAcomSearchDTO));

        return wbjAcomSearchDTO;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(MPEPartsCompanyDTO mpePartsCompanyDTO, HttpServletResponse response) throws Exception{

        XSSFWorkbook workbook = new XSSFWorkbook();

        // 폰트 설정
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);

        XSSFCellStyle style_header = workbook.createCellStyle();
        XSSFCellStyle style_body = workbook.createCellStyle();
        XSSFCellStyle style_body_sqInfo = workbook.createCellStyle();
        style_header.setFont(font);
        style_body.setFont(font);
        style_body_sqInfo.setFont(font);
        CreationHelper createHelper = workbook.getCreationHelper();

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
        style_body_sqInfo.setBorderTop(BorderStyle.THIN);
        style_body_sqInfo.setBorderLeft(BorderStyle.THIN);
        style_body_sqInfo.setBorderRight(BorderStyle.THIN);
        style_body_sqInfo.setBorderBottom(BorderStyle.THIN);

        //BackGround Color 지정
        style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Header
        row = sheet.createRow(rowNum++);

        cell = row.createCell(0);
        cell.setCellValue("번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(1);
        cell.setCellValue("대표자명");
        cell.setCellStyle(style_header);

        cell = row.createCell(2);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("부품사코드");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("매출액(억원)");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("직원수");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("설립일자");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("회사 전화번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("본사주소");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("우편번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("주생산품");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("품질5스타");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("납입5스타");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("기술5스타");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("SQ 정보");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("가입일");
        cell.setCellStyle(style_header);

        // Body
        List<MPEPartsCompanyDTO> list = mpePartsCompanyDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(mpePartsCompanyDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //대표자명
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getRprsntNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(3);

            String bsnmNo = list.get(i).getBsnmNo();
            StringBuilder newBsnmNo = new StringBuilder();

            // '-' 추가
            int length = bsnmNo.length();
            for (int j = 0; j < length; j++) {
                newBsnmNo.append(bsnmNo.charAt(j));
                if (j == 2 || j == 4) {
                    newBsnmNo.append("-");
                }
            }
            cell.setCellValue(newBsnmNo.toString());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //부품사코드
            cell = row.createCell(6);
            cell.setCellValue(COStringUtil.nvl(list.get(i).getCmpnCd(), "-"));
            cell.setCellStyle(style_body);

            //매출액(억원)
            cell = row.createCell(7);
            cell.setCellValue(COStringUtil.nvl(String.valueOf(list.get(i).getSlsPmt()), "-"));
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(8);
            cell.setCellValue(COStringUtil.nvl(String.valueOf(list.get(i).getMpleCnt()), "-"));
            cell.setCellStyle(style_body);

            //설립일자
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getStbsmDt());
            cell.setCellStyle(style_body);

            //회사 전화번호
            cell = row.createCell(10);
            cell.setCellValue(COStringUtil.nvl(list.get(i).getTelNo(), "-"));
            cell.setCellStyle(style_body);

            //본사주소
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getBscAddr() + " " + list.get(i).getDtlAddr());
            cell.setCellStyle(style_body);

            //우편번호
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getZipcode());
            cell.setCellStyle(style_body);

            //주생산품
            cell = row.createCell(13);

            if (list.get(i).getMjrPrdct1() == null && list.get(i).getMjrPrdct2() == null && list.get(i).getMjrPrdct3() == null) {
                cell.setCellValue("-");
            } else if (list.get(i).getMjrPrdct1() == null) {
                cell.setCellValue("-/" + list.get(i).getMjrPrdct2() + "/" + list.get(i).getMjrPrdct3());
            } else if (list.get(i).getMjrPrdct2() == null) {
                cell.setCellValue(list.get(i).getMjrPrdct1() + "/-/" + list.get(i).getMjrPrdct3());
            } else if (list.get(i).getMjrPrdct3() == null) {
                cell.setCellValue(list.get(i).getMjrPrdct1() + "/" + list.get(i).getMjrPrdct2() + "/-");
            } else {
                cell.setCellValue(list.get(i).getMjrPrdct1() + "/" + list.get(i).getMjrPrdct2() + "/" + list.get(i).getMjrPrdct3());
            }
            cell.setCellStyle(style_body);

            //품질5스타
            cell = row.createCell(14);

            if (list.get(i).getQlty5StarCdNm() == null && list.get(i).getQlty5StarYear() == null) {
                cell.setCellValue("-");
            } else if (list.get(i).getQlty5StarCdNm() == null) {
                cell.setCellValue("-/" + list.get(i).getQlty5StarYear() + "년");
            } else if (list.get(i).getPay5StarYear() == null) {
                cell.setCellValue(list.get(i).getQlty5StarCdNm() + "/-");
            } else {
                cell.setCellValue(list.get(i).getQlty5StarCdNm() + "/" + list.get(i).getQlty5StarYear() + "년");
            }
            cell.setCellStyle(style_body);

            //납입5스타
            cell = row.createCell(15);
            if (list.get(i).getPay5StarCdNm() == null && list.get(i).getPay5StarYear() == null) {
                cell.setCellValue("-");
            } else if (list.get(i).getPay5StarCdNm() == null) {
                cell.setCellValue("-/" + list.get(i).getPay5StarYear() + "년");
            } else if (list.get(i).getPay5StarYear() == null) {
                cell.setCellValue(list.get(i).getPay5StarCdNm() + "/-");
            } else {
                cell.setCellValue(list.get(i).getPay5StarCdNm() + "/" + list.get(i).getPay5StarYear() + "년");
            }
            cell.setCellStyle(style_body);

            //기술5스타
            cell = row.createCell(16);
            if (list.get(i).getTchlg5StarCdNm() == null && list.get(i).getTchlg5StarYear() == null) {
                cell.setCellValue("-");
            } else if (list.get(i).getTchlg5StarCdNm() == null) {
                cell.setCellValue("-/" + list.get(i).getTchlg5StarYear() + "년");
            } else if (list.get(i).getTchlg5StarYear() == null) {
                cell.setCellValue(list.get(i).getTchlg5StarCdNm() + "/-");
            } else {
                cell.setCellValue(list.get(i).getTchlg5StarCdNm() + "/" + list.get(i).getTchlg5StarYear() + "년");
            }
            cell.setCellStyle(style_body);

            mpePartsCompanyDTO.setBsnmNo(list.get(i).getBsnmNo());
            MPEPartsCompanyDTO sqDto = selectPartsComSQInfo(mpePartsCompanyDTO);
            List<MPEPartsCompanyDTO> sqList = sqDto.getList();

            int index = 1;
            String sqInfo1 = "";
            String sqInfo2 = "";
            String sqInfo3 = "";

            String nm = "";
            String year = "";
            String score = "";
            String crtfnCmpnNm = "";
            String sqInfo = "";

            for (MPEPartsCompanyDTO dto : sqList) {
                if (dto == null) {
                    break;
                } else {
                    nm = dto.getNm();
                    year = String.valueOf(dto.getYear());
                    score = String.valueOf(dto.getScore());
                    crtfnCmpnNm = dto.getCrtfnCmpnNm();

                    if ("null".equals(nm)) { nm = ""; }
                    if ("null".equals(year)) { year = "";}
                    if ("null".equals(score)) { score = ""; }
                    if ("null".equals(crtfnCmpnNm)) { crtfnCmpnNm = ""; }

                    sqInfo = nm + "/" + score + "/" + year + "년/" + crtfnCmpnNm;

                    if (index == 1) {
                        sqInfo1 = sqInfo + "\n";
                    } else if (index == 2) {
                        sqInfo2 = sqInfo + "\n";
                    } else if (index == 3) {
                        sqInfo3 = sqInfo;
                    }
                    index++;
                }

            }

            //SQ 정보
            Cell sqCell = row.createCell(17);

            if (list.get(i).getCtgryCd().equals("COMPANY01001")) {
                sqCell.setCellValue("-");
            } else if (list.get(i).getCtgryCd().equals("COMPANY01002")) {

                RichTextString newSqInfoText = createHelper.createRichTextString(sqInfo1 + sqInfo2 + sqInfo3);
                sqCell.setCellValue(newSqInfoText);
                style_body_sqInfo.setAlignment(HorizontalAlignment.LEFT);
                style_body_sqInfo.setWrapText(true);
            }
            sqCell.setCellStyle(style_body_sqInfo);

            //가입일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

            // 열 크기 조정
            sheet.setColumnWidth(0, 1000); sheet.setColumnWidth(1, 2000);
            sheet.setColumnWidth(2, 6000); sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(4, 1600); sheet.setColumnWidth(5, 2400);
            sheet.setColumnWidth(6, 4000); sheet.setColumnWidth(7, 3000);
            sheet.setColumnWidth(8, 2400); sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000); sheet.setColumnWidth(11, 11000);
            sheet.setColumnWidth(12, 2000); sheet.setColumnWidth(13, 10000);
            sheet.setColumnWidth(14, 5000); sheet.setColumnWidth(15, 5000);
            sheet.setColumnWidth(16, 5000); sheet.setColumnWidth(17, 10000);
            sheet.setColumnWidth(18, 5000);

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_부품사_관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("회원/부품사 관리 > 부품사 관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.mp.MPEPartsCompanyServiceImpl");
        pCoSystemLogDTO.setFncNm("selectPartsCompanyList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(mpePartsCompanyDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 컨설팅 목록(기술지도)을 조회한다.
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceList(CBATechGuidanceInsertDTO cbaTechGuidanceInsertDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(cbaTechGuidanceInsertDTO.getPageIndex());
        page.setRecordCountPerPage(cbaTechGuidanceInsertDTO.getListRowSize());

        page.setPageSize(cbaTechGuidanceInsertDTO.getPageRowSize());

        cbaTechGuidanceInsertDTO.setFirstIndex(page.getFirstRecordIndex());
        cbaTechGuidanceInsertDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        cbaTechGuidanceInsertDTO.setList(mpePartsCompanyMapper.selectTechGuidanceList(cbaTechGuidanceInsertDTO));
        cbaTechGuidanceInsertDTO.setTotalCount(mpePartsCompanyMapper.selectConsultingListCnt(cbaTechGuidanceInsertDTO));

        return cbaTechGuidanceInsertDTO;
    }

    /**
     * 컨설팅 목록(기술지도)을 조회한다.
     */
    public CBBManageConsultListDTO selectManageConsultList(CBBManageConsultListDTO cbbManageConsultListDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(cbbManageConsultListDTO.getPageIndex());
        page.setRecordCountPerPage(cbbManageConsultListDTO.getListRowSize());

        page.setPageSize(cbbManageConsultListDTO.getPageRowSize());

        cbbManageConsultListDTO.setFirstIndex(page.getFirstRecordIndex());
        cbbManageConsultListDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        CBATechGuidanceInsertDTO dto = new CBATechGuidanceInsertDTO();

        dto.setCnstgCd(cbbManageConsultListDTO.getCnstgCd());
        dto.setBsnmNo(cbbManageConsultListDTO.getBsnmNo());

        cbbManageConsultListDTO.setList(mpePartsCompanyMapper.selectManageConsultList(cbbManageConsultListDTO));
        cbbManageConsultListDTO.setTotalCount(mpePartsCompanyMapper.selectConsultingListCnt(dto));

        return cbbManageConsultListDTO;
    }

    /**
     * 자금지원 목록을 조회한다.
     */
    public WBGAExamSearchDTO selectFundingList(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbgaExamSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wbgaExamSearchDTO.getListRowSize());

        page.setPageSize(wbgaExamSearchDTO.getPageRowSize());

        wbgaExamSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wbgaExamSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wbgaExamSearchDTO.setList(mpePartsCompanyMapper.selectFundingList(wbgaExamSearchDTO));
        wbgaExamSearchDTO.setTotalCount(mpePartsCompanyMapper.selectFundingCnt(wbgaExamSearchDTO));

        return wbgaExamSearchDTO;
    }

    /**
     * 등록된 부품사 회원이 있는지 체크
     */
    public int selectMemberPartsSocietyExistsCheck(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectMemberPartsSocietyExistsCheck(mpePartsCompanyDTO);
    }

}
