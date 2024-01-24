package com.kap.service.impl.wb.wbi;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyDtlDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBIBSupplyCompanyService;
import com.kap.service.dao.wb.wbi.WBIBSupplyCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 공급망안정화기금 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 오병호
 * @since 2023.12.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.04		오병호				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBIBSupplyCompanyServiceImpl implements WBIBSupplyCompanyService {

    /* Mapper */
    private final WBIBSupplyCompanyMapper wBIBSupplyCompanyMapper;

    //파일 서비스
    private final COFileService cOFileService;
    private final COFileUtil cOFileUtil;

    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    /* 회사 상세 시퀀스 - SQ */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;


    /* 상생 신청 마스터 시퀀스 */
    private final EgovIdGnrService cxAppctnMstSeqIdgen;

    /* 상생 신청 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;
    private final EgovIdGnrService fileApplyIdgen;

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBIBSupplySearchDTO wBIBSupplySearchDTO) {
        return wBIBSupplyCompanyMapper.getOptYearList(wBIBSupplySearchDTO);
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptEpisdList(WBIBSupplySearchDTO wBIBSupplySearchDTO)  throws Exception
    {
        return wBIBSupplyCompanyMapper.getOptEpisdList(wBIBSupplySearchDTO);
    }

    /**
     * 선택 연도/회차 값에 따른
     * episdSeq 값 가져오기
     */
    public WBRoundMstDTO getEpisdSeq(WBIBSupplySearchDTO wBIBSupplySearchDTO)  throws Exception
    {
        WBRoundMstDTO wBRoundMstDTO = new WBRoundMstDTO();

        int episdSeq = wBIBSupplyCompanyMapper.getEpisdSeq(wBIBSupplySearchDTO);
        wBIBSupplySearchDTO.setEpisdSeq(episdSeq);
        wBRoundMstDTO.setEpisdSeq(episdSeq);

        return wBRoundMstDTO;
    }

    /**
     *   신청부품사 목록 List Get
     */
    public WBIBSupplySearchDTO getSupplyCompanyList(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBIBSupplySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBIBSupplySearchDTO.getListRowSize());

        page.setPageSize(wBIBSupplySearchDTO.getPageRowSize());

        wBIBSupplySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBIBSupplySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wBIBSupplySearchDTO.setList(wBIBSupplyCompanyMapper.getRegisterCompanyList(wBIBSupplySearchDTO));
        wBIBSupplySearchDTO.setTotalCount(wBIBSupplyCompanyMapper.getRegisterCompanyCount(wBIBSupplySearchDTO));

        return wBIBSupplySearchDTO;
    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    @Transactional
    public int putSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request) throws Exception
    {

        int respCnt = 1;

        /* 회원 마스터 Update */
        respCnt *= wBIBSupplyCompanyMapper.updCoMemMst(wBIBSupplyDTO);
        /* 회사 마스터 Update */
        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnMst(wBIBSupplyDTO);

        /* 구분에 따른 분기 */
        if(wBIBSupplyDTO.getCtgryCd().equals("COMPANY01002")) {
            wBIBSupplyDTO.setQlty5StarCd(null);
            wBIBSupplyDTO.setQlty5StarYear(null);
            wBIBSupplyDTO.setPay5StarCd(null);
            wBIBSupplyDTO.setPay5StarYear(null);
            wBIBSupplyDTO.setTchlg5StarCd(null);
            wBIBSupplyDTO.setTchlg5StarYear(null);

            /* 회사 상세 Update or Insert */
            List<WBIBSupplyDTO> sqInfoList = wBIBSupplyDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBIBSupplyDTO WBIBSupplyDTO : sqInfoList) {
                    WBIBSupplyDTO.setBsnmNo(wBIBSupplyDTO.getBsnmNo());
                    if(!Objects.nonNull(wBIBSupplyDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBIBSupplyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBIBSupplyDTO.setRegId(wBIBSupplyDTO.getRegId());
                        wBIBSupplyDTO.setRegIp(wBIBSupplyDTO.getRegIp());
                        respCnt *= wBIBSupplyCompanyMapper.insCmpnCbsnDtl(wBIBSupplyDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBIBSupplyDTO.setModIp(wBIBSupplyDTO.getRegIp());
                        wBIBSupplyDTO.setModId(wBIBSupplyDTO.getRegId());
                        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnDtl(wBIBSupplyDTO);
                    }
                }
            }
        } else {
            /* 회사 상세 Update or Insert */
            List<WBIBSupplyDTO> sqInfoList = wBIBSupplyDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBIBSupplyDTO WBIBSupplyDTO : sqInfoList) {
                    WBIBSupplyDTO.setBsnmNo(wBIBSupplyDTO.getBsnmNo());
                    WBIBSupplyDTO.setNm(null);
                    WBIBSupplyDTO.setScore(null);
                    WBIBSupplyDTO.setYear(null);
                    WBIBSupplyDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBIBSupplyDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBIBSupplyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBIBSupplyDTO.setRegId(wBIBSupplyDTO.getRegId());
                        wBIBSupplyDTO.setRegIp(wBIBSupplyDTO.getRegIp());
                        respCnt *= wBIBSupplyCompanyMapper.insCmpnCbsnDtl(wBIBSupplyDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBIBSupplyDTO.setModIp(wBIBSupplyDTO.getRegIp());
                        wBIBSupplyDTO.setModId(wBIBSupplyDTO.getRegId());
                        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnDtl(wBIBSupplyDTO);
                    }
                }
            }
        }

        /* 상생신청 마스터 */
        wBIBSupplyDTO.setAppctnSeq(cxAppctnMstSeqIdgen.getNextIntegerId()); /* 신청순번 */
        respCnt *= wBIBSupplyCompanyMapper.putAppctnMst(wBIBSupplyDTO);
        /* 상생신청 상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBIBSupplyDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        /* 스마트 신청 상태 코드 */
        respCnt *= wBIBSupplyCompanyMapper.putAppctnRsumeDtl(wBIBSupplyDTO);
        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBIBSupplyDTO.getFileList());
        wBIBSupplyDTO.setFileSeq(fileSeqMap.get("appctnSeq")); /* 파일 시퀀스 */
        respCnt *= wBIBSupplyCompanyMapper.putAppctnFileDtl(wBIBSupplyDTO);

        return respCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBIBSupplyDTO selectSupplyDtl(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception {

        String detailsKey = wBIBSupplySearchDTO.getDetailsKey();

        WBIBSupplyDTO wBIBSupplyDTO = wBIBSupplyCompanyMapper.selectSupplyDtl(wBIBSupplySearchDTO);

        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBIBSupplyDTO.getFileList());
        wBIBSupplyDTO.setFileSeq(fileSeqMap.get("appctnSeq")); /* 파일 시퀀스 */
        wBIBSupplyDTO.setDetailsKey(detailsKey);

        return wBIBSupplyDTO;
    }

    /**
     *  Write Page
     *  신청부품사 수정
     */
    @Transactional
    public int updateSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request) throws Exception
    {
        String detailsKey = wBIBSupplyDTO.getDetailsKey();
        int respCnt = 1;
        wBIBSupplyDTO.setDetailsKey(detailsKey);
        respCnt *= wBIBSupplyCompanyMapper.updAppctnRsumeDtl(wBIBSupplyDTO);

        String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
        String modIp = CONetworkUtil.getMyIPaddress(request);

        wBIBSupplyMstDTO.setAppctnBsnmNo(wBIBSupplyDTO.getBsnmNo());
        wBIBSupplyMstDTO.setModId(modId);
        wBIBSupplyMstDTO.setModIp(modIp);


        /* 회원 마스터 Update */
        respCnt *= wBIBSupplyCompanyMapper.updCoMemMst(wBIBSupplyDTO);

        /* 회사 마스터 Update */
        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnMst(wBIBSupplyDTO);

        /* 구분에 따른 분기 */
        if(wBIBSupplyDTO.getCtgryCd().equals("COMPANY01002")) {
            wBIBSupplyDTO.setQlty5StarCd(null);
            wBIBSupplyDTO.setQlty5StarYear(null);
            wBIBSupplyDTO.setPay5StarCd(null);
            wBIBSupplyDTO.setPay5StarYear(null);
            wBIBSupplyDTO.setTchlg5StarCd(null);
            wBIBSupplyDTO.setTchlg5StarYear(null);

            /* 회사 상세 Update or Insert */
            List<WBIBSupplyDTO> sqInfoList = wBIBSupplyDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBIBSupplyDTO WBIBSupplyDTO : sqInfoList) {
                    WBIBSupplyDTO.setBsnmNo(wBIBSupplyDTO.getBsnmNo());
                    if(!Objects.nonNull(wBIBSupplyDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBIBSupplyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBIBSupplyDTO.setRegId(wBIBSupplyDTO.getRegId());
                        wBIBSupplyDTO.setRegIp(wBIBSupplyDTO.getRegIp());
                        respCnt *= wBIBSupplyCompanyMapper.insCmpnCbsnDtl(wBIBSupplyDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBIBSupplyDTO.setModIp(wBIBSupplyDTO.getRegIp());
                        wBIBSupplyDTO.setModId(wBIBSupplyDTO.getRegId());
                        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnDtl(wBIBSupplyDTO);
                    }
                }
            }
        } else {
            /* 회사 상세 Update or Insert */
            List<WBIBSupplyDTO> sqInfoList = wBIBSupplyDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBIBSupplyDTO WBIBSupplyDTO : sqInfoList) {
                    WBIBSupplyDTO.setBsnmNo(wBIBSupplyDTO.getBsnmNo());
                    WBIBSupplyDTO.setNm(null);
                    WBIBSupplyDTO.setScore(null);
                    WBIBSupplyDTO.setYear(null);
                    WBIBSupplyDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBIBSupplyDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBIBSupplyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBIBSupplyDTO.setRegId(wBIBSupplyDTO.getRegId());
                        wBIBSupplyDTO.setRegIp(wBIBSupplyDTO.getRegIp());
                        respCnt *= wBIBSupplyCompanyMapper.insCmpnCbsnDtl(wBIBSupplyDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBIBSupplyDTO.setModIp(wBIBSupplyDTO.getRegIp());
                        wBIBSupplyDTO.setModId(wBIBSupplyDTO.getRegId());
                        respCnt *= wBIBSupplyCompanyMapper.updCmpnCbsnDtl(wBIBSupplyDTO);
                    }
                }
            }
        }

        //신청자 변경
        if ("Y".equals(wBIBSupplyMstDTO.getUserLogYn())) {
            WBIBSupplyChangeDTO wBIBSupplyChangeDTO = new WBIBSupplyChangeDTO();

            wBIBSupplyChangeDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
            wBIBSupplyChangeDTO.setAppctnSeq(Integer.valueOf(detailsKey));
            wBIBSupplyChangeDTO.setBfreMemSeq(wBIBSupplyMstDTO.getBfreMemSeq());
            wBIBSupplyChangeDTO.setAftrMemSeq(wBIBSupplyMstDTO.getAftrMemSeq());
            wBIBSupplyChangeDTO.setRegId(modId);
            wBIBSupplyChangeDTO.setRegIp(modIp);

            //상생참여이관로그 생성
            respCnt *= wBIBSupplyCompanyMapper.insertChangeLog(wBIBSupplyChangeDTO);
        }

        /* 상생신청파일 삭제 */
        respCnt *= wBIBSupplyCompanyMapper.delAppctnFileDtl(wBIBSupplyDTO);
        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBIBSupplyDTO.getFileList());
        wBIBSupplyDTO.setFileSeq(fileSeqMap.get("fileSeq")); /* 파일 시퀀스 */
        respCnt *= wBIBSupplyCompanyMapper.putAppctnFileDtl(wBIBSupplyDTO);

        wBIBSupplyDTO.setDetailsKey(detailsKey); /* 신청순번 */
        /* 상생 관리자 메모 */
        respCnt *= wBIBSupplyCompanyMapper.uptAppctnMst(wBIBSupplyDTO);

        return respCnt;
    }

    @Transactional
    public int delete(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception {
        int rtnCnt = 0;

        //마스터 삭제
        rtnCnt = wBIBSupplyCompanyMapper.deleteApplyMst(wBIBSupplySearchDTO);

        if (rtnCnt > 0) {
            for (int i=0; i < wBIBSupplySearchDTO.getDelValueList().size(); i++) {
                wBIBSupplySearchDTO.setDetailsKey(wBIBSupplySearchDTO.getDelValueList().get(i));
            }
            wBIBSupplyCompanyMapper.deleteApplyDtl(wBIBSupplySearchDTO);
            wBIBSupplyCompanyMapper.deleteTrans(wBIBSupplySearchDTO);
        }
        return rtnCnt;
    }

    public WBIBSupplyChangeDTO getChangeList(WBIBSupplyChangeDTO wBIBSupplyChangeDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBIBSupplyChangeDTO.getPageIndex());
        page.setRecordCountPerPage(wBIBSupplyChangeDTO.getListRowSize());

        page.setPageSize(wBIBSupplyChangeDTO.getPageRowSize());

        wBIBSupplyChangeDTO.setFirstIndex(page.getFirstRecordIndex());
        wBIBSupplyChangeDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wBIBSupplyChangeDTO.setList(wBIBSupplyCompanyMapper.getChangeList(wBIBSupplyChangeDTO));
        wBIBSupplyChangeDTO.setTotalCount(wBIBSupplyCompanyMapper.getChangeCount(wBIBSupplyChangeDTO));

        return wBIBSupplyChangeDTO;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBIBSupplySearchDTO wBIBSupplySearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("관리자상태값");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(4);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(5);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(6);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(7);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(8);
        cell.setCellValue("신청자(아이디)");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(9);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(10);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(11);
        cell.setCellValue("등록일시");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(12);
        cell.setCellValue("사용자등록일");
        cell.setCellStyle(style_header);

        // Body
        List<WBIBSupplySearchDTO> list = wBIBSupplySearchDTO.getList();

        int maxOrd = 0;
        int length = 0;

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);
            length = list.size();
            maxOrd = list.size();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBIBSupplySearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //회차
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getEpisd());
            cell.setCellStyle(style_body);

            //관리자 상태값
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getMngSttsCd());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getBsnmNo().substring(0,3)+"-"+list.get(i).getBsnmNo().substring(3,5)+"-"+list.get(i).getBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getCtgryCdNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSizeCdNm());
            cell.setCellStyle(style_body);

            //신청자(아이디)
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getName()+"("+list.get(i).getId()+")");
            cell.setCellStyle(style_body);

            //휴대폰번호
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //등록일시
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getMngSttsChngDtm());
            cell.setCellStyle(style_body);

            //사용자수정일
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getAppctnSttsChngDtm());
            cell.setCellStyle(style_body);


        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_신청부품사관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     *  관리자 상태값 미확인 갯수 조회
     */
    public int getCnt(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception {
        int rtnCnt = 0;

        //마스터 삭제
        rtnCnt = wBIBSupplyCompanyMapper.getCnt(wBIBSupplySearchDTO);

        return rtnCnt;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insertApply(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            wBIBSupplyDTO.setAppctnSeq(appctnSeq);
            wBIBSupplyDTO.setRegId(regId);
            wBIBSupplyDTO.setRegIp(regIp);

            rtnCnt = wBIBSupplyCompanyMapper.insertApply(wBIBSupplyDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                wBIBSupplyDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBIBSupplyDTO.setAppctnSeq(appctnSeq); /* 신청순번 */
                wBIBSupplyDTO.setRegId(regId);
                wBIBSupplyDTO.setRegIp(regIp);

                wBIBSupplyCompanyMapper.insertApplyDtl(wBIBSupplyDTO);

                //신청파일 넣기
                /* 상생신청파일 상세 */
                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBIBSupplyDTO.getFileList());
                wBIBSupplyDTO.setFileSeq(fileSeqMap.get("atchFile")); /* 파일 시퀀스 */
                rtnCnt *= wBIBSupplyCompanyMapper.putAppctnFileDtl(wBIBSupplyDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBIBSupplyDTO selectRecent(WBIBSupplySearchDTO wBIBSupplySearchDTO) throws Exception {

        WBIBSupplyDTO wBIBSupplyDTO = wBIBSupplyCompanyMapper.selectRecent(wBIBSupplySearchDTO);

        return wBIBSupplyDTO;
    }

    /**
     * 부품사 목록을 조회한다.
     */
    public MPEPartsCompanyDTO selectPartsCompanyList(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(mpePartsCompanyDTO.getPageIndex());
        page.setRecordCountPerPage(mpePartsCompanyDTO.getListRowSize());
        page.setPageSize(mpePartsCompanyDTO.getPageRowSize());

        mpePartsCompanyDTO.setList(wBIBSupplyCompanyMapper.selectPartsCompanyList(mpePartsCompanyDTO));
        mpePartsCompanyDTO.setTotalCount(wBIBSupplyCompanyMapper.selectPartsCompanyCnt(mpePartsCompanyDTO));

        return mpePartsCompanyDTO;
    }

    /**
     * 부품사 신청자를 수정한다.
     */
    @Transactional
    public int updateInfo(WBIBSupplyDTO wBIBSupplyDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;
        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);
            //상생신청진행 상태 업데이트
            //관리자상태에 따라 분기처리해야함
            wBIBSupplyDTO.setRegId(modId);
            wBIBSupplyDTO.setRegIp(modIp);
            wBIBSupplyDTO.setModId(modId);
            wBIBSupplyDTO.setModIp(modIp);
            /* 상생신청파일 삭제 */
            wBIBSupplyCompanyMapper.delAppctnFileDtl(wBIBSupplyDTO);

            //신청파일 넣기
            List<COFileDTO> rtnList = null;
            Map<String, MultipartFile> files = multiRequest.getFileMap();
            Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
            MultipartFile file;
            int atchFileCnt = 0;

            while (itr.hasNext()) {
                Map.Entry<String, MultipartFile> entry = itr.next();
                file = entry.getValue();

                if (file.getName().indexOf("atchFile") > -1  && file.getSize() > 0) {
                    atchFileCnt++;
                }
            }

            if (!files.isEmpty()) {
                List<WBIBSupplyDTO> optinList = null;
                rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                if (rtnList.size() > 0) {
                    WBIBSupplySearchDTO wBIBSupplySearchDTO = new  WBIBSupplySearchDTO();
                    int stageSeq = 0;

                    wBIBSupplySearchDTO.setBsnCd(wBIBSupplyDTO.getBsnCd());
                }

                for (int i = 0; i < rtnList.size() ; i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    Integer fileSeq;

                    if ("99".equals(rtnList.get(i).getRespCd())) {
                        fileSeq = wBIBSupplyDTO.getFileSeqList().get(i);
                    } else {
                        rtnList.get(i).setStatus("success");
                        rtnList.get(i).setFieldNm("fileSeq");
                        fileList.add(rtnList.get(i));
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                        fileSeq = fileSeqMap.get("fileSeq");
                    }

                    wBIBSupplyDTO.setFileSeq(fileSeq);
                    wBIBSupplyDTO.setFileCd("ATTACH_FILE_TYPE01");
                    wBIBSupplyCompanyMapper.putAppctnFileDtl(wBIBSupplyDTO);
                }
            }


            if ("PRO_TYPE06001_01_002".equals(wBIBSupplyDTO.getAppctnSttsCd())) {
                //보완요청 사용자->보완완료, 관리자 -> 미확인
                wBIBSupplyDTO.setDetailsKey(String.valueOf(wBIBSupplyDTO.getAppctnSeq()));
                wBIBSupplyDTO.setAppctnSttsCd("PRO_TYPE06001_01_003");
                wBIBSupplyDTO.setMngSttsCd("PRO_TYPE06001_02_001");
                wBIBSupplyCompanyMapper.updAppctnRsumeDtl(wBIBSupplyDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBIBSupplyMstDTO wBIBSupplyMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBIBSupplyCompanyMapper.getBsnmNoCnt(wBIBSupplyMstDTO);

        wBIBSupplyMstDTO.setRespCnt(respCnt);

        return respCnt;
    }
}
