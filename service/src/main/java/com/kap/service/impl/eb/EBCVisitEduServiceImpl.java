package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.*;
import com.kap.service.dao.eb.EBCVisitEduMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 방문교육 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: EBCVisitEduServiceImpl.java
 * @Description		: 방문교육 관리를 위한 ServiceImpl
 * @author 구은희
 * @since 2023.11.30
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.30		구은희				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EBCVisitEduServiceImpl implements EBCVisitEduService {

    // DAO
    private final EBCVisitEduMapper ebcVisitEduMapper;

    // 부품사관리 DAO
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;

    // 파일 서비스
    private final COFileService cOFileService;
    
    // 부품사관리 서비스
    public final MPEPartsCompanyService mpePartsCompanyService;

    // 회원관리 서비스
    private final MPAUserService mpaUserService;

    /* 방문교육 결과 상세 시퀀스 */
    private final EgovIdGnrService edctnVstRsltSeqIdgen;

    /* 방문교육 결과옵션 상세 시퀀스 */
    private final EgovIdGnrService edctnVstOptnSeqIdgen;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    /**
     * 방문교육 목록을 조회한다.
     */
    public EBCVisitEduDTO selectVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduDTO.getListRowSize());

        page.setPageSize(ebcVisitEduDTO.getPageRowSize());

        ebcVisitEduDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduDTO.setTotalCount(ebcVisitEduMapper.selectVisitEduCnt(ebcVisitEduDTO));
        ebcVisitEduDTO.setList(ebcVisitEduMapper.selectVisitEduList(ebcVisitEduDTO));

        return ebcVisitEduDTO;
    }

    /**
     * 방문교육 목록을 조회한다.
     */
    public List<EBCVisitEduDTO> selectAppctnTypeList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectAppctnTypeList(ebcVisitEduDTO);
    }

    /**
     * 방문교육 강사를 조회한다.
     */
    public List<EBCVisitEduDTO> selectIsttrList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectIsttrList(ebcVisitEduDTO);
    }

    /**
     * 방문교육 교육실적 조회
     */
    public List<EBCVisitEduDTO> selectResultOpList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception{
        return ebcVisitEduMapper.selectResultOpList(ebcVisitEduDTO);
    }

    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO);
    }

    /**
     * 강사를 수정한다.
     */
    public EBCVisitEduDTO selectVisitEduDtl(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectVisitEduDtl(ebcVisitEduDTO);
    }

    /**
     * 방문교육 상세를 수정한다.
     */
    public int updateVisitEdu(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        HashMap<String, Integer> itrdcFileSeqMap = cOFileService.setFileInfo(ebcVisitEduDTO.getItrdcFileList());
        HashMap<String, Integer> lctrFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getLctrFileList());
        HashMap<String, Integer> etcMatlsFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getEtcMatlsFileList());
        ebcVisitEduDTO.setItrdcFileSeq(itrdcFileSeqMap.get("itrdcFileSeq"));
        ebcVisitEduDTO.setLctrFileSeq(lctrFileMap.get("lctrFileSeq"));
        ebcVisitEduDTO.setEtcMatlsFileSeq(etcMatlsFileMap.get("etcMatlsFileSeq"));

        int respCnt = 0;
        List<EBCVisitEduDTO> resultOpList = ebcVisitEduDTO.getResultOpList();

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        ebcVisitEduDTO.setRegId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
        ebcVisitEduDTO.setModId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

        // 방문교육 결과정보 insert, 시퀀스 생성
        if(ebcVisitEduDTO.getVstRsltSeq() == null) {
            ebcVisitEduDTO.setVstRsltSeq(edctnVstRsltSeqIdgen.getNextIntegerId());
            ebcVisitEduMapper.insertEdctnVstRslt(ebcVisitEduDTO);
        }

        //신청자 정보 수정
        ebcVisitEduMapper.updatePartsMemInfo(ebcVisitEduDTO);

        //부품사 정보 수정
        MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();

        mpePartsCompanyDTO.setBsnmNo(ebcVisitEduDTO.getAppctnBsnmNo());
        mpePartsCompanyDTO.setCtgryCd(ebcVisitEduDTO.getCtgryCd());
        mpePartsCompanyDTO.setSizeCd(ebcVisitEduDTO.getSizeCd());
        mpePartsCompanyDTO.setStbsmDt(ebcVisitEduDTO.getStbsmDt());
        mpePartsCompanyDTO.setTelNo(ebcVisitEduDTO.getCmpnTelNo());
        mpePartsCompanyDTO.setZipcode(ebcVisitEduDTO.getZipcode());
        mpePartsCompanyDTO.setBscAddr(ebcVisitEduDTO.getBscAddr());
        mpePartsCompanyDTO.setDtlAddr(ebcVisitEduDTO.getDtlAddr());
        mpePartsCompanyDTO.setSlsPmt(ebcVisitEduDTO.getSlsPmt());
        mpePartsCompanyDTO.setSlsYear(ebcVisitEduDTO.getSlsYear());
        mpePartsCompanyDTO.setMpleCnt(ebcVisitEduDTO.getMpleCnt());
        mpePartsCompanyDTO.setMjrPrdct1(ebcVisitEduDTO.getMjrPrdct1());
        mpePartsCompanyDTO.setMjrPrdct2(ebcVisitEduDTO.getMjrPrdct2());
        mpePartsCompanyDTO.setMjrPrdct3(ebcVisitEduDTO.getMjrPrdct3());

        if(ebcVisitEduDTO.getCtgryCd().equals("COMPANY01001")) {
            mpePartsCompanyDTO.setQlty5StarCd(ebcVisitEduDTO.getQlty5StarCd());
            mpePartsCompanyDTO.setQlty5StarYear(ebcVisitEduDTO.getQlty5StarYear());
            mpePartsCompanyDTO.setPay5StarCd(ebcVisitEduDTO.getPay5StarCd());
            mpePartsCompanyDTO.setPay5StarYear(Integer.valueOf(ebcVisitEduDTO.getPay5StarYear()));
            mpePartsCompanyDTO.setTchlg5StarCd(ebcVisitEduDTO.getTchlg5StarCd());
            mpePartsCompanyDTO.setTchlg5StarYear(ebcVisitEduDTO.getTchlg5StarYear());

        } else if(ebcVisitEduDTO.getCtgryCd().equals("COMPANY01002")) {
            mpePartsCompanyDTO.setSqInfoList1(ebcVisitEduDTO.getSqInfoList1());
            mpePartsCompanyDTO.setSqInfoList2(ebcVisitEduDTO.getSqInfoList2());
            mpePartsCompanyDTO.setSqInfoList3(ebcVisitEduDTO.getSqInfoList3());
        }

        mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
        mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        mpePartsCompanyService.updatePartsCompany(mpePartsCompanyDTO);

        //강사 수정
        ebcVisitEduMapper.deleteIsttrRel(ebcVisitEduDTO);
        ebcVisitEduMapper.insertIsttrRel(ebcVisitEduDTO);

        //신청내용 수정
        ebcVisitEduMapper.updateEdctnVst(ebcVisitEduDTO);
        ebcVisitEduMapper.updateEdctnVstRslt(ebcVisitEduDTO);

        //신청분야 상세 수정
        ebcVisitEduMapper.deleteAppctnType(ebcVisitEduDTO);
        ebcVisitEduMapper.insertAppctnType(ebcVisitEduDTO);

        //교육실적 수정
        ebcVisitEduMapper.deleteResultOp(ebcVisitEduDTO);

        for (EBCVisitEduDTO dto : resultOpList) {

            ebcVisitEduDTO.setRsltOptnSeq(edctnVstOptnSeqIdgen.getNextIntegerId());
            ebcVisitEduDTO.setVstSeq(dto.getVstSeq());
            if(dto.getVstRsltSeq() == null) {
                ebcVisitEduDTO.setVstRsltSeq(ebcVisitEduDTO.getVstRsltSeq());
            } else {
                ebcVisitEduDTO.setVstRsltSeq(dto.getVstRsltSeq());
            }
            ebcVisitEduDTO.setMemSeq(dto.getMemSeq());
            ebcVisitEduDTO.setRsltTypeCd(dto.getRsltTypeCd());
            ebcVisitEduDTO.setOptnCd(dto.getOptnCd());
            ebcVisitEduDTO.setRsltVal(dto.getRsltVal());

            ebcVisitEduMapper.insertResultOp(ebcVisitEduDTO);
        }
        return respCnt;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(EBCVisitEduDTO ebcVisitEduDTO, HttpServletResponse response) throws Exception{

       /* XSSFWorkbook workbook = new XSSFWorkbook();
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
        List<EBCVisitEduDTO> list = ebcVisitEduDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(ebcVisitEduDTO.getTotalCount() - i);
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

            String bsnmNo = list.get(i).getBsnmNo(); // 주어진 숫자
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
            cell.setCellValue(list.get(i).getCmpnCd() == null ? "-" : list.get(i).getCmpnCd());
            cell.setCellStyle(style_body);

            //매출액(억원)
            cell = row.createCell(7);
            if (list.get(i).getSlsPmt() == null) {
                cell.setCellValue("-");
            } else {
                cell.setCellValue(list.get(i).getSlsPmt());
            }
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(8);
            if (list.get(i).getSlsPmt() == null) {
                cell.setCellValue("-");
            } else {
                cell.setCellValue(list.get(i).getMpleCnt());
            }
            cell.setCellStyle(style_body);

            //설립일자
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getStbsmDt());
            cell.setCellStyle(style_body);

            //회사 전화번호
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getTelNo());
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

            //SQ 정보
            cell = row.createCell(17);
            if (list.get(i).getCtgryCd().equals("COMPANY01001")) {
                cell.setCellValue("-");
            } else if (list.get(i).getCtgryCd().equals("COMPANY01002")) {
                cell.setCellValue("SQ 정보");
            }
            cell.setCellStyle(style_body);

            //가입일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);

            CellRangeAddress date_range = new CellRangeAddress(1,1,1,11);// 행시작, 행끝, 열시작, 열끝
            sheet.addMergedRegion(date_range);

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
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
        pCoSystemLogDTO.setRsn(ebcVisitEduDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);*/
    }
}
