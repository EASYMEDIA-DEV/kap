package com.kap.service.impl.wb.wbj;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.core.dto.wb.wbj.*;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBJBAcomListService;
import com.kap.service.dao.wb.wbf.WBFBRegisterCompanyMapper;
import com.kap.service.dao.wb.wbj.WBJBAcomListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBJBAcomListServiceImpl implements WBJBAcomListService {

    //Mapper
    private final WBJBAcomListMapper wBJBAcomListMapper;

    //파일 서비스
    private final COFileService cOFileService;

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

    /**
     * 연도 상세 조회
     */
    public WBRoundMstDTO selectYearDtl(WBRoundMstSearchDTO wBRoundMstSearchDTO) throws Exception {

        return wBJBAcomListMapper.selectYearDtl(wBRoundMstSearchDTO);
    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    @Transactional
    public int putRegisterCompany(WBJAcomDTO wBJAcomDTO) throws Exception
    {

        int respCnt = 1;

        /* 회원 마스터 Update */
        respCnt *= wBJBAcomListMapper.updCoMemMst(wBJAcomDTO);

        /* 회사 마스터 Update */
        respCnt *= wBJBAcomListMapper.updCmpnCbsnMst(wBJAcomDTO);

        /* 구분에 따른 분기 */
        if(wBJAcomDTO.getCtgryCd().equals("COMPANY01002")) {
            wBJAcomDTO.setQlty5StarCd(null);
            wBJAcomDTO.setQlty5StarYear(null);
            wBJAcomDTO.setPay5StarCd(null);
            wBJAcomDTO.setPay5StarYear(null);
            wBJAcomDTO.setTchlg5StarCd(null);
            wBJAcomDTO.setTchlg5StarYear(null);

            /* 회사 상세 Update or Insert */
            List<WBJAcomDTO> sqInfoList = wBJAcomDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBJAcomDTO WBJAcomDTO : sqInfoList) {
                    wBJAcomDTO.setBsnmNo(wBJAcomDTO.getBsnmNo());
                    if(!Objects.nonNull(wBJAcomDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBJAcomDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBJAcomDTO.setRegId(wBJAcomDTO.getRegId());
                        wBJAcomDTO.setRegIp(wBJAcomDTO.getRegIp());
                        respCnt *= wBJBAcomListMapper.insCmpnCbsnDtl(wBJAcomDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBJAcomDTO.setModIp(wBJAcomDTO.getRegIp());
                        wBJAcomDTO.setModId(wBJAcomDTO.getRegId());
                        respCnt *= wBJBAcomListMapper.updCmpnCbsnDtl(wBJAcomDTO);
                    }
                }
            }
        } else {
            /* 회사 상세 Update or Insert */
            List<WBJAcomDTO> sqInfoList = wBJAcomDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBJAcomDTO WBJAcomDTO : sqInfoList) {
                    wBJAcomDTO.setBsnmNo(wBJAcomDTO.getBsnmNo());
                    wBJAcomDTO.setNm(null);
                    wBJAcomDTO.setScore(null);
                    wBJAcomDTO.setYear(null);
                    wBJAcomDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBJAcomDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBJAcomDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBJAcomDTO.setRegId(wBJAcomDTO.getRegId());
                        wBJAcomDTO.setRegIp(wBJAcomDTO.getRegIp());
                        respCnt *= wBJBAcomListMapper.insCmpnCbsnDtl(wBJAcomDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBJAcomDTO.setModIp(wBJAcomDTO.getRegIp());
                        wBJAcomDTO.setModId(wBJAcomDTO.getRegId());
                        respCnt *= wBJBAcomListMapper.updCmpnCbsnDtl(wBJAcomDTO);
                    }
                }
            }
        }

        /* 상생신청 마스터 */
        int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBJAcomDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
        respCnt *= wBJBAcomListMapper.putAppctnMst(wBJAcomDTO);

        /* 상생신청 상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBJAcomDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        /* 스마트 신청 상태 코드 */
        respCnt *= wBJBAcomListMapper.putAppctnRsumeDtl(wBJAcomDTO);

        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBJAcomDTO.getFileList());
        wBJAcomDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
        respCnt *= wBJBAcomListMapper.putAppctnFileDtl(wBJAcomDTO);

        /* 상생신청 자동차부품산업 상세 */
        wBJAcomDTO.setAppctnSeq(firstAppctnRsumeDtlIdgen);
        respCnt *= wBJBAcomListMapper.putAppctnRsumePizePartDtl(wBJAcomDTO);

        return respCnt;
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptEpisdList(WBJAcomSearchDTO wBJAcomSearchDTO)  throws Exception
    {
        return wBJBAcomListMapper.getOptEpisdList(wBJAcomSearchDTO);
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptPrizeList(WBJAcomSearchDTO wBJAcomSearchDTO)  throws Exception
    {
        return wBJBAcomListMapper.getOptPrizeList(wBJAcomSearchDTO);
    }

    /**
     *   신청부품사 목록 List Get
     */
    public WBJAcomSearchDTO getRegisterCompanyList(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBJAcomSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBJAcomSearchDTO.getListRowSize());

        page.setPageSize(wBJAcomSearchDTO.getPageRowSize());

        wBJAcomSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBJAcomSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        List<WBJAcomSearchDTO> getMainLists = wBJBAcomListMapper.getRegisterCompanyList(wBJAcomSearchDTO);

        wBJAcomSearchDTO.setList(getMainLists);
        wBJAcomSearchDTO.setTotalCount(wBJBAcomListMapper.getAcomListTotCnt(wBJAcomSearchDTO));

        return wBJAcomSearchDTO;
    }

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBJAcomSearchDTO wBJAcomSearchDTO) {
        return wBJBAcomListMapper.getOptYearList(wBJAcomSearchDTO);
    }

    /**
     * 회차 상세 조회
     */
    public WBJAcomDTO selectAcomDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception {

        String detailsKey = wBJAcomSearchDTO.getDetailsKey();

        WBJAcomDTO wBJAcomDTO = wBJBAcomListMapper.selectAcomDtl(wBJAcomSearchDTO);

        if("Y".equals(wBJAcomSearchDTO.getEditYn())){
            List<WBJAcomSearchDTO> optPrizeList = wBJBAcomListMapper.getOptPrizeDtoList(wBJAcomSearchDTO);
            wBJAcomDTO.setPrizeDtoList(optPrizeList);
        }else{
            List<String> optPrizeList = wBJBAcomListMapper.getOptPrizeList(wBJAcomSearchDTO);
            wBJAcomDTO.setPrizeList(optPrizeList);
        }

        List<String> optMrtsList = wBJBAcomListMapper.getOptMrtsList(wBJAcomSearchDTO);
        List<WBJAcomSearchDTO> optFileList = wBJBAcomListMapper.getFileList(wBJAcomSearchDTO);

        wBJAcomDTO.setMrtsList(optMrtsList);
        wBJAcomDTO.setOptFileList(optFileList);
        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBJAcomDTO.getFileList());
        wBJAcomDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
        wBJAcomDTO.setDetailsKey(detailsKey);

        return wBJAcomDTO;
    }

    /**
     *  edit Page
     *  신청부품사 수정
     */
    @Transactional
    public int updateAcom(WBJAcomDTO wBJAcomDTO, WBJBAcomMstDTO wbjbAcomMstDTO, HttpServletRequest request) throws Exception
    {
        int respCnt = 1;

        String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
        String modIp = CONetworkUtil.getMyIPaddress(request);

        wbjbAcomMstDTO.setAppctnBsnmNo(wBJAcomDTO.getBsnmNo());
        WBJBAcomDtlDTO wBJBAcomDtlDTO = new WBJBAcomDtlDTO();
        wBJBAcomDtlDTO.setRsumeSeq(wbjbAcomMstDTO.getRsumeSeq());
        wBJBAcomDtlDTO.setRsumeOrd(wbjbAcomMstDTO.getRsumeOrd());
        wBJBAcomDtlDTO.setAppctnSeq(Integer.valueOf(wbjbAcomMstDTO.getDetailsKey()));
        wBJBAcomDtlDTO.setMngSttsCd(wbjbAcomMstDTO.getMngSttsCd());
        wBJBAcomDtlDTO.setRegId(modId);
        wBJBAcomDtlDTO.setRegIp(modIp);
        wBJBAcomDtlDTO.setModId(modId);
        wBJBAcomDtlDTO.setModIp(modIp);

        /* 회원 마스터 Update */
        respCnt *= wBJBAcomListMapper.updCoMemMst(wBJAcomDTO);

        /* 회사 마스터 Update */
        respCnt *= wBJBAcomListMapper.updCmpnCbsnMst(wBJAcomDTO);

        /* 구분에 따른 분기 */
        if(wBJAcomDTO.getCtgryCd().equals("COMPANY01002")) {
            wBJAcomDTO.setQlty5StarCd(null);
            wBJAcomDTO.setQlty5StarYear(null);
            wBJAcomDTO.setPay5StarCd(null);
            wBJAcomDTO.setPay5StarYear(null);
            wBJAcomDTO.setTchlg5StarCd(null);
            wBJAcomDTO.setTchlg5StarYear(null);

            /* 회사 상세 Update or Insert */
            List<WBJAcomDTO> sqInfoList = wBJAcomDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBJAcomDTO WBJAcomDTO : sqInfoList) {
                    wBJAcomDTO.setBsnmNo(wBJAcomDTO.getBsnmNo());
                    if(!Objects.nonNull(wBJAcomDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBJAcomDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBJAcomDTO.setRegId(wBJAcomDTO.getRegId());
                        wBJAcomDTO.setRegIp(wBJAcomDTO.getRegIp());
                        respCnt *= wBJBAcomListMapper.insCmpnCbsnDtl(wBJAcomDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBJAcomDTO.setModIp(wBJAcomDTO.getRegIp());
                        wBJAcomDTO.setModId(wBJAcomDTO.getRegId());
                        respCnt *= wBJBAcomListMapper.updCmpnCbsnDtl(wBJAcomDTO);
                    }
                }
            }
        } else {
            /* 회사 상세 Update or Insert */
            List<WBJAcomDTO> sqInfoList = wBJAcomDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBJAcomDTO WBJAcomDTO : sqInfoList) {
                    wBJAcomDTO.setBsnmNo(wBJAcomDTO.getBsnmNo());
                    wBJAcomDTO.setNm(null);
                    wBJAcomDTO.setScore(null);
                    wBJAcomDTO.setYear(null);
                    wBJAcomDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBJAcomDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBJAcomDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBJAcomDTO.setRegId(wBJAcomDTO.getRegId());
                        wBJAcomDTO.setRegIp(wBJAcomDTO.getRegIp());
                        respCnt *= wBJBAcomListMapper.insCmpnCbsnDtl(wBJAcomDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBJAcomDTO.setModIp(wBJAcomDTO.getRegIp());
                        wBJAcomDTO.setModId(wBJAcomDTO.getRegId());
                        respCnt *= wBJBAcomListMapper.updCmpnCbsnDtl(wBJAcomDTO);
                    }
                }
            }
        }

        if("PRO_TYPE05002_02_001".equals(wbjbAcomMstDTO.getFinalMngSttsCd()) ||
           "PRO_TYPE05002_02_002".equals(wbjbAcomMstDTO.getFinalMngSttsCd()) ||
           "PRO_TYPE05002_02_003".equals(wbjbAcomMstDTO.getFinalMngSttsCd())) {

            wbjbAcomMstDTO.setRsumeSttsCd("PRO_TYPE05002");
            wBJBAcomListMapper.updateFinalApplyStatus(wbjbAcomMstDTO);

        }else{
            if ("PRO_TYPE05001_02_003".equals(wbjbAcomMstDTO.getMngSttsCd())) {
                //통과(PRO_TYPE05001_02_003)경우 최종 심사로 넘어감
                if ("PRO_TYPE05001_02_003".equals(wbjbAcomMstDTO.getMngSttsCd())) {
                    wBJBAcomDtlDTO.setAppctnSttsCd("PRO_TYPE05002_01_001"); // 최종 심사 신청자 상태값 결과대기 값 코드 넣기
                }

                wBJBAcomListMapper.updateApplyStatus(wbjbAcomMstDTO);

                //다음 스텝 생성
                wBJBAcomDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBJBAcomDtlDTO.setRsumeOrd(wBJAcomDTO.getRsumeOrd() + 1);
                wBJBAcomDtlDTO.setRsumeSttsCd("PRO_TYPE05002");
                wBJBAcomDtlDTO.setAppctnSttsCd("PRO_TYPE05002_01_001");
                wBJBAcomDtlDTO.setMngSttsCd("PRO_TYPE05002_02_001");
                wBJBAcomDtlDTO.setRegId(modId);
                wBJBAcomDtlDTO.setRegIp(modIp);

                wBJBAcomListMapper.insertApplyDtl(wBJBAcomDtlDTO);
            } else {
                respCnt *= wBJBAcomListMapper.updAppctnRsumeDtl(wBJAcomDTO);
            }
        }

        //신청자 변경
        if ("Y".equals(wbjbAcomMstDTO.getUserLogYn())) {
            WBJBAcomChangeDTO wBJBAcomChangeDTO = new WBJBAcomChangeDTO();

            wBJBAcomChangeDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
            wBJBAcomChangeDTO.setAppctnSeq(Integer.valueOf(wbjbAcomMstDTO.getDetailsKey()));
            wBJBAcomChangeDTO.setBfreMemSeq(wbjbAcomMstDTO.getBfreMemSeq());
            wBJBAcomChangeDTO.setAftrMemSeq(wbjbAcomMstDTO.getAftrMemSeq());
            wBJBAcomChangeDTO.setRegId(modId);
            wBJBAcomChangeDTO.setRegIp(modIp);

            //상생참여이관로그 생성
            respCnt *= wBJBAcomListMapper.insertChangeLog(wBJBAcomChangeDTO);
        }

        /* 상생신청파일 삭제 */
        respCnt *= wBJBAcomListMapper.delAppctnFileDtl(wBJAcomDTO);
        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBJAcomDTO.getFileList());
        wBJAcomDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
        respCnt *= wBJBAcomListMapper.putAppctnFileDtl(wBJAcomDTO);

        respCnt *= wBJBAcomListMapper.updAppctnMst(wBJAcomDTO);

        if ("PRO_TYPE05002_02_003".equals(wbjbAcomMstDTO.getFinalMngSttsCd())) {
            wBJAcomDTO.setHghstWinerYn("Y"); // 최종 심사 신청자 상태값 결과대기 값 코드 넣기

            respCnt *= wBJBAcomListMapper.updAppctnRsumePrizeDtl(wBJAcomDTO);
        }else{
            wBJAcomDTO.setHghstWinerYn(""); // 최종 심사 신청자 상태값 결과대기 값 코드 넣기

            respCnt *= wBJBAcomListMapper.updAppctnRsumePrizeDtl(wBJAcomDTO);
        }

        return respCnt;
    }

    /**
     * 진행상태 목록을 조회한다.
     * @return
     */
    public WBJBAcomMstDTO selectAppctnRsumeDtl(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception {

        WBJBAcomMstDTO wBJBAcomMstDTO = wBJBAcomListMapper.selectApplyMst(wBJAcomSearchDTO);

        List<WBJBAcomDtlDTO> wBJBAcomDtlDTOList = wBJBAcomListMapper.selectApplyFinalDtlList(wBJAcomSearchDTO);

        wBJBAcomMstDTO.setApplyList(wBJBAcomDtlDTOList);

        return wBJBAcomMstDTO;
    }

    @Transactional
    public int delete(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception {
        int rtnCnt = 0;

        //마스터 삭제
        rtnCnt = wBJBAcomListMapper.deleteApplyMst(wBJAcomSearchDTO);

        if (rtnCnt > 0) {
            for (int i=0; i < wBJAcomSearchDTO.getDelValueList().size(); i++) {
                wBJAcomSearchDTO.setDetailsKey(wBJAcomSearchDTO.getDelValueList().get(i));
                List<WBJBAcomDtlDTO> wBJBAcomDtlDTOList = wBJBAcomListMapper.selectApplyDtlList(wBJAcomSearchDTO);

                for (int j=0; j < wBJBAcomDtlDTOList.size(); j++) {
                    wBJAcomSearchDTO.setRsumeSeq(wBJBAcomDtlDTOList.get(j).getRsumeSeq());
                    wBJBAcomListMapper.deleteOptnDtl(wBJAcomSearchDTO);
                }
            }
            wBJBAcomListMapper.deleteApplyDtl(wBJAcomSearchDTO);
            wBJBAcomListMapper.deleteTrans(wBJAcomSearchDTO);
        }
        return rtnCnt;
    }

    public WBJBAcomChangeDTO getChangeList(WBJBAcomChangeDTO wBJBAcomChangeDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBJBAcomChangeDTO.getPageIndex());
        page.setRecordCountPerPage(wBJBAcomChangeDTO.getListRowSize());

        page.setPageSize(wBJBAcomChangeDTO.getPageRowSize());

        wBJBAcomChangeDTO.setFirstIndex(page.getFirstRecordIndex());
        wBJBAcomChangeDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wBJBAcomChangeDTO.setList(wBJBAcomListMapper.getChangeList(wBJBAcomChangeDTO));
        wBJBAcomChangeDTO.setTotalCount(wBJBAcomListMapper.getChangeCount(wBJBAcomChangeDTO));

        return wBJBAcomChangeDTO;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBJAcomSearchDTO wBJAcomSearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("훈격");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("포상부문");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("포상대상자");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("핸드폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("1차결과");
        cell.setCellStyle(style_header);
        
        cell = row.createCell(11);
        cell.setCellValue("최종결과");
        cell.setCellStyle(style_header);

        // Body
        List<WBJAcomSearchDTO> list = wBJAcomSearchDTO.getList();

        int maxOrd = 0;
        int length = 0;

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);
            length = list.size();
            maxOrd = list.size();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBJAcomSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getCmpnSeqNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getBsnmNo().substring(0,3)+"-"+list.get(i).getBsnmNo().substring(3,5)+"-"+list.get(i).getBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCtgryCdNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getSizeCdNm());
            cell.setCellStyle(style_body);

            //훈격
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getMrtsCdNm());
            cell.setCellStyle(style_body);

            //포상부문
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getPrizeCdNm());
            cell.setCellStyle(style_body);

            //포상대상자
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //핸드폰번호
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //1차결과
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getAppctnSttsCdNm());
            cell.setCellStyle(style_body);

            //최종결과
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getMngSttsCdNm());
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

    public int getCnt(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception {
        int rtnCnt = 0;

        //마스터 삭제
        rtnCnt = wBJBAcomListMapper.getCnt(wBJAcomSearchDTO);

        return rtnCnt;
    }

    /**
     *  회차 값에 따른 포상 검색
     */
    public List<String> getPrizeList(WBJAcomSearchDTO wBJAcomSearchDTO)  throws Exception
    {
        return wBJBAcomListMapper.getPrizeList(wBJAcomSearchDTO);
    }
    /**
     *  회차 값에 따른 포상 검색
     */
    public List<WBJAcomSearchDTO> getPrizeDtoList(WBJAcomSearchDTO wBJAcomSearchDTO)  throws Exception
    {
        return wBJBAcomListMapper.getPrizeDtoList(wBJAcomSearchDTO);
    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    @Transactional
    public int insertApply(WBJAcomDTO wBJAcomDTO, HttpServletRequest request) throws Exception
    {
        int rtnCnt = 0;
        try{
            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            wBJAcomDTO.setAppctnSeq(appctnSeq);
            wBJAcomDTO.setRegId(regId);
            wBJAcomDTO.setRegIp(regIp);

            rtnCnt = wBJBAcomListMapper.insertApply(wBJAcomDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                wBJAcomDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBJAcomDTO.setAppctnSeq(appctnSeq); /* 신청순번 */
                wBJAcomDTO.setRsumeOrd(1); /* 신청순번 */
                wBJAcomDTO.setRegId(regId);
                wBJAcomDTO.setRegIp(regIp);

                wBJBAcomListMapper.putAppctnRsumeDtl(wBJAcomDTO);

                //신청파일 넣기
                /* 상생신청파일 상세 */
                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBJAcomDTO.getFileList());
                wBJAcomDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
                rtnCnt *= wBJBAcomListMapper.putAppctnFileDtl(wBJAcomDTO);

                /* 상생신청 자동차부품산업 상세 */
                rtnCnt *= wBJBAcomListMapper.putAppctnRsumePizePartDtl(wBJAcomDTO);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 회차 상세 조회
     */
    public WBJAcomDTO selectRecent(WBJAcomSearchDTO wBJAcomSearchDTO) throws Exception {

        WBJAcomDTO wBJAcomDTO = wBJBAcomListMapper.selectRecent(wBJAcomSearchDTO);

        return wBJAcomDTO;
    }

    /**
     *  역대 수상자 리스트
     */
    public WBJAcomDTO selectWinerList(WBJAcomDTO wBJAcomDTO) throws Exception{
        List<WBJAcomDTO> getWinnerLists = wBJBAcomListMapper.selectWinerList(wBJAcomDTO);

        wBJAcomDTO.setList(getWinnerLists);

        return wBJAcomDTO;
    }
}
