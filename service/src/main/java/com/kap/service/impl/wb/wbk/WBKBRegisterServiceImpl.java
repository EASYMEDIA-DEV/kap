package com.kap.service.impl.wb.wbk;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbk.*;
import com.kap.service.*;
import com.kap.service.dao.wb.wbf.WBFBRegisterCompanyMapper;
import com.kap.service.dao.wb.wbk.WBKBRegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 미래차 공모전 신청팀 ServiceImpl
 * @author 민윤기
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		민윤기				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBKBRegisterServiceImpl implements WBKBRegisterService {

    /* Mapper */
    private final WBKBRegisterMapper wBKBRegisterMapper;

    private final COSystemLogService cOSystemLogService;



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

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) {
        return wBKBRegisterMapper.getOptYearList(wBKBRegisterSearchDTO);
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptEpisdList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO)  throws Exception
    {
        return wBKBRegisterMapper.getOptEpisdList(wBKBRegisterSearchDTO);
    }

    /**
     *  사업 연도/회차에 맞는 사업 유형 SELECT
     */
    public  List<String> getEpisdSeq(WBKBRegisterSearchDTO wBKBRegisterSearchDTO)  throws Exception
    {
        return wBKBRegisterMapper.getEpisdSeq(wBKBRegisterSearchDTO);
    }

    /**
     *   신청부품사 목록 List Get
     */
    public WBKBRegisterSearchDTO getRegisterList(WBKBRegisterSearchDTO wBKBRegisterSearchDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBKBRegisterSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBKBRegisterSearchDTO.getListRowSize());

        page.setPageSize(wBKBRegisterSearchDTO.getPageRowSize());

        wBKBRegisterSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBKBRegisterSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        List<WBKBRegisterSearchDTO> getMainLists = wBKBRegisterMapper.getRegisterList(wBKBRegisterSearchDTO);

        /* 신청 상세 Data Get - Add Sub List */
        for(int listIdx=0; listIdx < getMainLists.size(); listIdx++) {
            WBKBRegisterSearchDTO MainList = getMainLists.get(listIdx);
            WBKBRegisterSearchDTO SubList = wBKBRegisterMapper.getRegisterCompanySubList(MainList);
            WBKBRegisterSearchDTO FirstResult = wBKBRegisterMapper.getRegisterFirstResultList(MainList);
            WBKBRegisterSearchDTO FinalResult = wBKBRegisterMapper.getRegisterFinalResultList(MainList);

                /* Re Build */
                MainList.setName(SubList.getName());
                MainList.setHpNo(SubList.getHpNo());
                MainList.setEmail(SubList.getEmail());

                if (FirstResult != null ) {
                    MainList.setFResultCd(FirstResult.getFResultCd());
                    MainList.setFResultNm(FirstResult.getFResultNm());
                }
                if (FinalResult != null ) {
                    MainList.setLResultCd(FinalResult.getLResultCd());
                    MainList.setLResultNm(FinalResult.getLResultNm());
                }

            getMainLists.set(listIdx, MainList);
        }
        wBKBRegisterSearchDTO.setList(getMainLists);
        wBKBRegisterSearchDTO.setTotalCount(wBKBRegisterMapper.getRegisterCompanyCount(wBKBRegisterSearchDTO));

        return wBKBRegisterSearchDTO;
    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    public int putRegisterCompany(WBKBRegisterDTO wBKBRegisterDTO) throws Exception {

        int respCnt = 1;

        /* 회원 마스터 Update */
        respCnt *= wBKBRegisterMapper.updCoMemMst(wBKBRegisterDTO);

        /* 상생신청 마스터 */
        int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBKBRegisterDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
        respCnt *= wBKBRegisterMapper.putAppctnMst(wBKBRegisterDTO);

        /* 상생신청 상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBKBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        /* 스마트 신청 상태 코드 */
        wBKBRegisterDTO.setAppctnSttsCd("WBKB_REG_FRT001");
        respCnt *= wBKBRegisterMapper.putAppctnRsumeDtl(wBKBRegisterDTO);

        /* 상생신청파일 상세 */
        if(wBKBRegisterDTO.getFileList() != null && !wBKBRegisterDTO.getFileList().isEmpty()) {
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBKBRegisterDTO.getFileList());
            wBKBRegisterDTO.setFileSeq(fileSeqMap.get("appctnFileSeq")); /* 파일 시퀀스 */
            respCnt *= wBKBRegisterMapper.putAppctnFileDtl(wBKBRegisterDTO);
            //respCnt *= wBKBRegisterMapper.putDtlFileDtl(wBKBRegisterDTO);
        }

        /* 상생신청 참여자 상세 */
        respCnt *= wBKBRegisterMapper.putAppctnRsumeTaskDtl(wBKBRegisterDTO);

        if(wBKBRegisterDTO.getPartList() != null && !wBKBRegisterDTO.getPartList().isEmpty()) {
            WBKBRegPartDTO wBKBRegPartDTO = new WBKBRegPartDTO();
            wBKBRegPartDTO.setRsumeSeq(wBKBRegisterDTO.getRsumeSeq());
            wBKBRegPartDTO.setModId(wBKBRegisterDTO.getModId());
            wBKBRegPartDTO.setModIp(wBKBRegisterDTO.getModIp());

            for (int i = 0; i < wBKBRegisterDTO.getPartList().size(); i++) {

                int firstEpisdGiveSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();

                //wBKBRegPartDTO.setRsumeSeq(firstEpisdGiveSeqIdgen);
                wBKBRegPartDTO.setTmmbrSeq(firstEpisdGiveSeqIdgen);
                wBKBRegPartDTO.setName(wBKBRegisterDTO.getPartList().get(i).getName());
                wBKBRegPartDTO.setHpNo(wBKBRegisterDTO.getPartList().get(i).getHpNo());
                wBKBRegPartDTO.setEmail(wBKBRegisterDTO.getPartList().get(i).getEmail());
                wBKBRegPartDTO.setSchlNm(wBKBRegisterDTO.getPartList().get(i).getSchlNm());
                wBKBRegPartDTO.setGrd(wBKBRegisterDTO.getPartList().get(i).getGrd());
                wBKBRegPartDTO.setGrdCd(wBKBRegisterDTO.getPartList().get(i).getGrdCd());
                wBKBRegPartDTO.setRegId(wBKBRegisterDTO.getRegIp());
                wBKBRegPartDTO.setRegId(wBKBRegisterDTO.getRegId());

                respCnt *= wBKBRegisterMapper.putPartDtl(wBKBRegPartDTO);
            }
        }
        return respCnt;

    }

    /**
     *  Write Page
     *  신청부품사 등록
     */
    public int updRegisterCompany(WBKBRegisterDTO wBKBRegisterDTO) throws Exception {

        int respCnt = 1;
        /* 회원 마스터 Update */
        respCnt *= wBKBRegisterMapper.updCoMemMst(wBKBRegisterDTO);

        /* 상생신청 마스터 Update */
        if(wBKBRegisterDTO.getEpisdSeq() != null && !wBKBRegisterDTO.getEpisdSeq().isEmpty()) {
            respCnt *= wBKBRegisterMapper.updAppctnMst(wBKBRegisterDTO);
        }

        /* 신청 상세 Update */
        respCnt *= wBKBRegisterMapper.updAppctnRsumeTaskDtl(wBKBRegisterDTO);

        System.out.println("업데이트 wBKBRegisterDTO == " + wBKBRegisterDTO.getRsumeSeq());
        /* 파일 업데이트 */

        /* 팀원 Update */
        if(wBKBRegisterDTO.getPartList() != null && !wBKBRegisterDTO.getPartList().isEmpty()) {
            respCnt *= wBKBRegisterMapper.delPartDtl(wBKBRegisterDTO);
            WBKBRegPartDTO wBKBRegPartDTO = new WBKBRegPartDTO();
            wBKBRegPartDTO.setRsumeSeq(wBKBRegisterDTO.getRsumeSeq());
            wBKBRegPartDTO.setModId(wBKBRegisterDTO.getModId());
            wBKBRegPartDTO.setModIp(wBKBRegisterDTO.getModIp());


            for (int i = 0; i < wBKBRegisterDTO.getPartList().size(); i++) {

                int firstEpisdGiveSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
                wBKBRegPartDTO.setTmmbrSeq(firstEpisdGiveSeqIdgen);
                wBKBRegPartDTO.setName(wBKBRegisterDTO.getPartList().get(i).getName());
                wBKBRegPartDTO.setHpNo(wBKBRegisterDTO.getPartList().get(i).getHpNo());
                wBKBRegPartDTO.setEmail(wBKBRegisterDTO.getPartList().get(i).getEmail());
                wBKBRegPartDTO.setSchlNm(wBKBRegisterDTO.getPartList().get(i).getSchlNm());
                wBKBRegPartDTO.setGrd(wBKBRegisterDTO.getPartList().get(i).getGrd());
                wBKBRegPartDTO.setGrdCd(wBKBRegisterDTO.getPartList().get(i).getGrdCd());
                wBKBRegPartDTO.setRegId(wBKBRegisterDTO.getRegIp());
                wBKBRegPartDTO.setRegId(wBKBRegisterDTO.getRegId());
                wBKBRegPartDTO.setRsumeSeq(wBKBRegisterDTO.getRsumeSeq());

                respCnt *= wBKBRegisterMapper.putPartDtl(wBKBRegPartDTO);
            }
        }

        return respCnt;

    }

    /**
     *  Write Page
     *  공모전 진행 상세 업데이트
     */
    public int updRsumeStep(WBKBRegisterDTO wBKBRegisterDTO) throws Exception {

        int respCnt = 1;

        // DTO set
        WBKBRsumeDTO wBKBRsumeDTO = new WBKBRsumeDTO();

        wBKBRsumeDTO.setRsumeSeq(wBKBRegisterDTO.getRsumeSeq());
        wBKBRsumeDTO.setRsumeOrd(wBKBRegisterDTO.getRsumeOrd());
        wBKBRsumeDTO.setAppctnSeq(wBKBRegisterDTO.getDetailsKey());
        wBKBRsumeDTO.setAppctnSttsCd(wBKBRegisterDTO.getAppctnSttsCd());
        wBKBRsumeDTO.setMngSttsCd(wBKBRegisterDTO.getMngSttsCd());
        wBKBRsumeDTO.setFileCd(wBKBRegisterDTO.getFileCd());

        // 파일 업데이트
        respCnt *= wBKBRegisterMapper.delDtlFile(wBKBRsumeDTO);
        if(wBKBRegisterDTO.getFileList() != null && !wBKBRegisterDTO.getFileList().isEmpty()) {
            wBKBRsumeDTO.setFileList(wBKBRegisterDTO.getFileList());
            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBKBRsumeDTO.getFileList());
            System.err.println("fileSeqMap :: " + fileSeqMap);

            if (wBKBRsumeDTO.getRsumeOrd() == 1) wBKBRsumeDTO.setFileSeq(fileSeqMap.get("appctnFileSeq"));
            else if (wBKBRsumeDTO.getRsumeOrd() == 2) wBKBRsumeDTO.setFileSeq(fileSeqMap.get("firstFileSeq"));
            else if (wBKBRsumeDTO.getRsumeOrd() == 3) wBKBRsumeDTO.setFileSeq(fileSeqMap.get("lastFileSeq"));

            if (wBKBRegisterDTO.getFileList().get(0).getFileDsc() != null && !wBKBRegisterDTO.getFileList().get(0).getFileDsc().isEmpty())
            respCnt *= wBKBRegisterMapper.putDtlFileDtl(wBKBRsumeDTO);
        }

       //if(wBKBRegisterDTO.getMngSttsCd().equals("WBKB_REG_LRT002")  && wBKBRegisterDTO.getRsumeOrd() < 4){ // 통과 시
        if(wBKBRegisterDTO.getMngSttsCd().equals("WBKB_REG_LRT003") || wBKBRegisterDTO.getMngSttsCd().isEmpty() && wBKBRsumeDTO.getRsumeOrd() < 4){ // 탈락 시
            /* 해당 상태 수정 */
            wBKBRegisterMapper.updRsumeStep(wBKBRsumeDTO);
            /* 다음 스텝 있으면 삭제*/
            if(wBKBRegisterDTO.getRsumeOrd()+1 < 4 ) {
                wBKBRegisterMapper.delRsumeNestStep(wBKBRsumeDTO);
            }

        }else if(wBKBRegisterDTO.getRsumeOrd() < 4){ // 통과,수상,참여자 취소 시

            /* 기존 스텝 상태 변경 및 다음 스텝 추가 */
            wBKBRegisterMapper.updRsumeStep(wBKBRsumeDTO); // 기존 스텝 상태변경
            if(wBKBRegisterDTO.getMngSttsCd().equals("WBKB_REG_LRT002")  && wBKBRsumeDTO.getRsumeOrd() < 3) {
                wBKBRsumeDTO.setAppctnSttsCd("WBKB_REG_FRT001");
                wBKBRegisterMapper.insertRsumeStep(wBKBRsumeDTO); // 다음 스탭 추가
            }

        }

        return respCnt;

    }

    /**
     *  Write Page
     *  공모전 신청 삭제
     */
    public int deleteRegFutureCarContest(WBKBRegisterDTO wBKBRegisterDTO) throws Exception {

        int respCnt = 0;
        int CheckStepZero = 0;
        int rsumeSeq = 0;

        String inputString  = wBKBRegisterDTO.getDetailsKey();
        String[] detailsKey = inputString.split(",");

        wBKBRegisterDTO.setAppctnSeqs(Arrays.asList(detailsKey));

        // 미 진행 심사 체크
        CheckStepZero += wBKBRegisterMapper.checkAppctnRsumeDtl(wBKBRegisterDTO);


        if(CheckStepZero != 0 && detailsKey.length == CheckStepZero) { //삭제 시작
            for (String detailKey : detailsKey) {
                wBKBRegisterDTO.setDetailsKey(detailKey);
                //  rsumeSeq 체크
                rsumeSeq = wBKBRegisterMapper.checkAppctnRsumeSeq(wBKBRegisterDTO);
                wBKBRegisterDTO.setRsumeSeq(rsumeSeq);
                System.err.println("CheckStepZero ==" + CheckStepZero);
                System.err.println("CheckStepZero ==" + rsumeSeq);


                respCnt = wBKBRegisterMapper.deleteCarRegAppctnMst(wBKBRegisterDTO); // 신청 마스터
                wBKBRegisterMapper.deleteCarRegAppctnRsumeDtl(wBKBRegisterDTO); // 진행상태
                wBKBRegisterMapper.deleteCarRegAppctnFileDtl(wBKBRegisterDTO); //신청파일
                wBKBRegisterMapper.deleteCarRegAppctnDtl(wBKBRegisterDTO); //공모전 상세
                wBKBRegisterMapper.deleteCarRegAppctnTmmbrDtl(wBKBRegisterDTO); //팀원 상세
            }
        }



        return respCnt;
    }

        /**
         * 엑셀 생성
         */
    public void excelDownload(WBKBRegisterSearchDTO wBKBRegisterSearchDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("팀장명");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("참여구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("주제");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("시상부문");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("1차결과");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("최종결과");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("핸드폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

       /* cell = row.createCell(10);
        cell.setCellValue("최초 등록자(아이디)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("최초 수정일시");
        cell.setCellStyle(style_header);*/

        cell = row.createCell(10);
        cell.setCellValue("최종 수정자(아이디)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("최종 수정일시");
        cell.setCellStyle(style_header);

        // Body
        List<WBKBRegisterSearchDTO> list = wBKBRegisterSearchDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBKBRegisterSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //팀장명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //참여구분
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getPtcptTypeNm());
            /*cell.setCellValue(list.get(i).getDeptNm());*/
            cell.setCellStyle(style_body);

            //주제
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getThemeCdNm());
            cell.setCellStyle(style_body);

            //시상부문
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getWdcrmNm() == null ? "-" : list.get(i).getWdcrmNm());
            cell.setCellStyle(style_body);

            //1차결과
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getFResultNm() == null ? "-" : list.get(i).getFResultNm());
            cell.setCellStyle(style_body);

            //최종결과
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getLResultNm() == null ? "-" : list.get(i).getLResultNm() );
            cell.setCellStyle(style_body);

            //핸드폰번호
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getHpNo()==null ? "-" : list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            /*
            //최초 등록자(아이디)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getRegId()==null ? "-" : list.get(i).getRegId());
            cell.setCellStyle(style_body);

            //최초 등록일시
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getRegDtm()==null ? "-" : list.get(i).getRegDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
            cell.setCellStyle(style_body);
            */

            //최종 수정자(아이디)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getModId()==null ? "-" : list.get(i).getModName());
            cell.setCellStyle(style_body);

            //최종 수정일시
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getModDtm()==null ? "-" : list.get(i).getModDtm().substring(0, list.get(i).getRegDtm().lastIndexOf(":")));
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_미래차공모전신청팀_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCODSysLogDTO = new COSystemLogDTO();

        pCODSysLogDTO.setTrgtMenuNm("시스템 관리 > 로그 관리 > 이용로그관리");
        pCODSysLogDTO.setSrvcNm("mngwserc.co.cob.service.impl.CODSysLogServiceImpl");
        pCODSysLogDTO.setFncNm("selectSysLogList");
        pCODSysLogDTO.setPrcsCd("DL");
        pCODSysLogDTO.setRsn(pCODSysLogDTO.getRsn());
        pCODSysLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCODSysLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCODSysLogDTO);
    }

    public WBKBRegisterDTO selectFutureCarConRegDtl(WBKBRegisterSearchDTO wBKBRegisterSearchDTO){

        String detailsKey = wBKBRegisterSearchDTO.getDetailsKey();

        WBKBRegisterDTO wBKBRegisterDTO = wBKBRegisterMapper.selectFutureCarRegDtl(wBKBRegisterSearchDTO);

        wBKBRegisterDTO.setDetailsKey(detailsKey);

        /*팀원정보로직*/
        List<WBKBRegPartDTO> partLists= wBKBRegisterMapper.selectFutureCarPartDtl(wBKBRegisterDTO);
        wBKBRegisterDTO.setPartList(partLists);

        /*
         서류진행상태로직
         */
        List<WBKBRsumeDTO> rsumeList= wBKBRegisterMapper.selectFutureCarRsumeDtl(wBKBRegisterDTO);
        wBKBRegisterDTO.setRsumeList(rsumeList);

        /*
         진행 상태에 따른 첨부파일 확인
         */
        List<WBKBFileDtlDTO> FileDtlList = wBKBRegisterMapper.selectFutureCarFileDtl(wBKBRegisterDTO);
        wBKBRegisterDTO.setFileDtlList(FileDtlList);


        return wBKBRegisterDTO;
    }

}
