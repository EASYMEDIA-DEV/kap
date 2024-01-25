package com.kap.service.impl.wb.wbg;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbg.*;
import com.kap.core.dto.wb.wbh.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBGAExamService;
import com.kap.service.dao.sm.SMJFormMapper;
import com.kap.service.dao.wb.wbg.WBGAExamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
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
 * 신청업체관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBGAExamServiceImpl.java
 * @Description		: 신청업체관리 ServiceImpl
 * @author 김대성
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김대성				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBGAExamServiceImpl implements WBGAExamService {
    //Mapper
    private final WBGAExamMapper wBGAExamMapper;
    private final SMJFormMapper smjFormMapper;

    //파일 업로드 유틸
    private final COFileService cOFileService;
    private final COFileUtil cOFileUtil;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;
    //상생신청 마스터 시퀀스
    private final EgovIdGnrService cxAppctnMstSeqIdgen;
    //대상장비 상세 시퀀스
    private final EgovIdGnrService cxAppctnTchlgSeqIdgen;
    //부품사관리 시퀀스
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;
    //상생신청 상세 시퀀스
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;
    //참여이관 시퀀스
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;
    /* 사업신청자격 시퀀스 */
    private final EgovIdGnrService cxAppctnValidMstIdgen;

    @Override
    public WBGAConsultingDTO getConsultingList(WBGAConsultingDTO wBGAConsultingDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBGAConsultingDTO.getPageIndex());
        page.setRecordCountPerPage(wBGAConsultingDTO.getListRowSize());

        page.setPageSize(wBGAConsultingDTO.getPageRowSize());

        wBGAConsultingDTO.setFirstIndex(page.getFirstRecordIndex());
        wBGAConsultingDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBGAConsultingDTO.setList(wBGAExamMapper.getConsultingList(wBGAConsultingDTO));
        wBGAConsultingDTO.setTotalCount(wBGAExamMapper.getConsultingCount(wBGAConsultingDTO));

        return wBGAConsultingDTO;
    }

    /**
     * 신청 목록
     */
    public WBGAExamSearchDTO selectCalibrationList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBGAExamSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBGAExamSearchDTO.getListRowSize());

        page.setPageSize(wBGAExamSearchDTO.getPageRowSize());

        wBGAExamSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBGAExamSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBGAExamSearchDTO.setList(wBGAExamMapper.selectCalibrationList(wBGAExamSearchDTO));
        wBGAExamSearchDTO.setTotalCount(wBGAExamMapper.getCalibrationListTotCnt(wBGAExamSearchDTO));

        return wBGAExamSearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        return wBGAExamMapper.selectYearDtl(wBGAExamSearchDTO);
    }


    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {


        List<String> appctnSeqList = wBGAExamSearchDTO.getDelValueList();

        wBGAExamSearchDTO.setDelValueList(appctnSeqList);

        //이관삭제
        wBGAExamMapper.carbonCompanyDeleteTrnsf(wBGAExamSearchDTO);
        //장비삭제
        wBGAExamMapper.carbonCompanyDeleteTchlg(wBGAExamSearchDTO);


        wBGAExamSearchDTO.setDelValueList(wBGAExamMapper.selectRsumeSeq(wBGAExamSearchDTO));

        //신청진행계측 삭제
        wBGAExamMapper.carbonCompanyDeleteRsumeTchlg(wBGAExamSearchDTO);
        //신청파일 삭제
        wBGAExamMapper.carbonCompanyDeleteRsumeFile(wBGAExamSearchDTO);


        wBGAExamSearchDTO.setDelValueList(appctnSeqList);
        //신청진행상세 삭제
        wBGAExamMapper.carbonCompanyDeleteRsume(wBGAExamSearchDTO);

        int respCnt = wBGAExamMapper.carbonCompanyDeleteMst(wBGAExamSearchDTO);

        return respCnt;

    }

    /**
     * 옵션 목록
     */
    public WBGAValidDTO selectExamValid(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {
        WBGAValidDTO wBGAValidDTO = new WBGAValidDTO();
        wBGAValidDTO = wBGAExamMapper.selectExamValid(wBGAExamSearchDTO);

        List<WBGAValidDtlDTO>  wBGAValidDtlDTO = wBGAExamMapper.selectExamValidDtlList(wBGAExamSearchDTO);
        if(wBGAValidDtlDTO.size() > 0){
            wBGAValidDTO.setDtlList(wBGAValidDtlDTO);
        }

        return wBGAValidDTO;
    }

    /**
     * 옵션 수정
     */
    public int examValidUpdate(WBGAValidDTO wBGAValidDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int appctnValidSeqIdgen;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();


        if(wBGAValidDTO.getValidSeq() != null && !"".equals(wBGAValidDTO.getValidSeq())){
            appctnValidSeqIdgen = wBGAValidDTO.getValidSeq();
            wBGAValidDTO.setModId(coaAdmDTO.getId());
            wBGAValidDTO.setModIp(coaAdmDTO.getLoginIp());
            respCnt = wBGAExamMapper.examValidUpdate(wBGAValidDTO);
        }else{
            appctnValidSeqIdgen = cxAppctnValidMstIdgen.getNextIntegerId();
            wBGAValidDTO.setValidSeq(appctnValidSeqIdgen);
            wBGAValidDTO.setRegId(coaAdmDTO.getId());
            wBGAValidDTO.setRegIp(coaAdmDTO.getLoginIp());
            respCnt = wBGAExamMapper.examValidInsert(wBGAValidDTO);
        }


        wBGAExamMapper.deleteValidDtl(wBGAValidDTO);

        for(int i=0; i < wBGAValidDTO.getDtlList().size(); i++){
            WBGAValidDtlDTO wBGAValidDtlDTO = wBGAValidDTO.getDtlList().get(i);
            wBGAValidDtlDTO.setValidSeq(appctnValidSeqIdgen);
            wBGAValidDtlDTO.setRegId(coaAdmDTO.getId());
            wBGAValidDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBGAExamMapper.examValidDtlInsert(wBGAValidDtlDTO);
        }

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBGAExamSearchDTO wBGAExamSearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("진행상태");
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
        cell.setCellValue("전년도 매출액");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("장비내역");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("수량");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("투자금액");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("재단지원금");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("실지급일");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("등록일시");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("사용자수정일");
        cell.setCellStyle(style_header);


        // Body
        List<WBGAExamSearchDTO> list = wBGAExamSearchDTO.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBGAExamSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getRsumeSttsNm());
            cell.setCellStyle(style_body);

            //관리자 상태값
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getMngSttsNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getBsnmNo());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //신청자
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getName() +"("+ list.get(i).getId() +")");
            cell.setCellStyle(style_body);

            //휴대폰
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //매출액
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getSlsPmt());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getPicCmssrNm());
            cell.setCellStyle(style_body);

            //장비내역
            cell = row.createCell(13);
            if(list.get(i).getTchlgOrdMax() > 1){
                cell.setCellValue(list.get(i).getTchlgNm()+ " 등 " + (list.get(i).getTchlgOrdMax() -1));
            }else{
                cell.setCellValue(list.get(i).getTchlgNm());
            }
            cell.setCellStyle(style_body);

            //수량
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getTchlgCntSum());
            cell.setCellStyle(style_body);

            //투자금액
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getNvstmPmt());
            cell.setCellStyle(style_body);

            //재단지원금
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getFndnSpprtPmt());
            cell.setCellStyle(style_body);

            //실지급일
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getRealGiveDt());
            cell.setCellStyle(style_body);

            //등록일시
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm());
            cell.setCellStyle(style_body);

            //사용자 수정일
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getModDtm());
            cell.setCellStyle(style_body);
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_신청부품사관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 진행상태 목록을 조회한다.
     * @return
     */
    public WBGAApplyMstDTO selectCompanyDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {

        WBGAApplyMstDTO wBGAApplyMstDTO = wBGAExamMapper.selectApplyMst(wBGAExamSearchDTO);

        List<WBGAApplyDtlDTO> selectApplyDtlList = wBGAExamMapper.selectApplyDtlList(wBGAExamSearchDTO);
        List<WBGAEuipmentDTO> selectEuipemtDtlList = wBGAExamMapper.selectEuipemtDtlList(wBGAExamSearchDTO);

        wBGAApplyMstDTO.setEuipmentList(selectEuipemtDtlList);

        wBGAApplyMstDTO.setApplyList(new ArrayList<WBGAApplyDtlDTO>());

        for (WBGAApplyDtlDTO dtlDto : selectApplyDtlList) {

            List<WBGAApplyDtlDTO> fileList = wBGAExamMapper.selectFileList(dtlDto);
            List<WBGAMsEuipmentDTO> msEuipmentList = wBGAExamMapper.selectMsEuipmentList(dtlDto);

            dtlDto.setFileInfoList(new ArrayList<WBGAApplyDtlDTO>());
            dtlDto.setMsEquipmentList(new ArrayList<WBGAMsEuipmentDTO>());

            //단계별 파일정보 넣기
            for(WBGAApplyDtlDTO fileDTO : fileList) {
                if (fileDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && fileDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getFileInfoList().add(fileDTO);
                }
            }

            //단계별 계측장비정보 넣기
            for(WBGAMsEuipmentDTO msEuipmentDTO : msEuipmentList) {
                if (msEuipmentDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && msEuipmentDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getMsEquipmentList().add(msEuipmentDTO);
                }
            }

            wBGAApplyMstDTO.getApplyList().add(dtlDto);
        }

        wBGAApplyMstDTO.setRsumeSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeSttsNm());
        wBGAApplyMstDTO.setMngSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getMngSttsNm());
        wBGAApplyMstDTO.setStageOrd(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeOrd());

        return wBGAApplyMstDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBGACompanyDTO selectCompanyUserDtl(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {

        WBGACompanyDTO wBGACompanyDTO = new WBGACompanyDTO();

        wBGACompanyDTO = wBGAExamMapper.getCompanyInfo(wBGAExamSearchDTO);
        List<WBGACompanyDTO> sqList = wBGAExamMapper.selectSqList(wBGAExamSearchDTO);
        wBGACompanyDTO.setSqInfoList(sqList);
        wBGACompanyDTO.setMemSeq(wBGAExamSearchDTO.getMemSeq());

        return wBGACompanyDTO;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insert(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {

            //신청시간 년도 구하기
            Date now = new Date();
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

            wBGAApplyMstDTO.setYear(yyyy.format(now));

            WBRoundMstSearchDTO round = wBGAExamMapper.getExisdDtl(wBGAApplyMstDTO);

            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            if (round == null) {
                //해당년도 회차 미 존재시 신규회차 생성
                int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                roundDtl.setEpisdSeq(exipsdSeq);
                roundDtl.setBsnCd(wBGAApplyMstDTO.getBsnCd());
                roundDtl.setYear(Integer.valueOf(wBGAApplyMstDTO.getYear()));
                roundDtl.setEpisd(1); //회차는 1회차 고정
                roundDtl.setAccsStrtDtm(wBGAApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setAccsEndDtm(wBGAApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setBsnStrtDtm(wBGAApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setBsnEndDtm(wBGAApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setExpsYn("Y");
                roundDtl.setRegId(regId);
                roundDtl.setRegIp(regIp);

                wBGAExamMapper.insetRound(roundDtl);

                wBGAApplyMstDTO.setEpisdSeq(exipsdSeq);
            } else {
                wBGAApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
            }

            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            wBGAApplyMstDTO.setAppctnSeq(appctnSeq);
            wBGAApplyMstDTO.setAppctnBsnmNo(wBGACompanyDTO.getBsnmNo());
            wBGAApplyMstDTO.setRegId(regId);
            wBGAApplyMstDTO.setRegIp(regIp);

            rtnCnt = wBGAExamMapper.insertApply(wBGAApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                WBGAApplyDtlDTO wBGAApplyDtlDTO = new WBGAApplyDtlDTO();
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setAppctnSeq(wBGAApplyMstDTO.getAppctnSeq());
                wBGAApplyDtlDTO.setRsumeOrd(1);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                wBGAApplyDtlDTO.setRegId(regId);
                wBGAApplyDtlDTO.setRegIp(regIp);

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);


                //신청대상장비 생성
                for(int j=0; j<wBGAApplyMstDTO.getEuipmentList().size(); j++) {
                    WBGAEuipmentDTO wBGAEuipmentDTO = new WBGAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wBGAEuipmentDTO.setAppctnSeq(appctnSeq);
                    wBGAEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wBGAEuipmentDTO.setTchlgOrd(j+1);
                    wBGAEuipmentDTO.setTchlgNm(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wBGAEuipmentDTO.setTchlgCnt(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wBGAEuipmentDTO.setRegId(regId);
                    wBGAEuipmentDTO.setRegIp(regIp);

                    wBGAExamMapper.insertEuipment(wBGAEuipmentDTO);
                }

                //신청계측장비 상세 1단계생성
                WBGAMsEuipmentDTO wbgaMsEuipmentDTO = new WBGAMsEuipmentDTO();
                wbgaMsEuipmentDTO.setRsumeSeq(wBGAApplyDtlDTO.getRsumeSeq());
                wbgaMsEuipmentDTO.setRsumeOrd(wBGAApplyDtlDTO.getRsumeOrd());
                wbgaMsEuipmentDTO.setRegId(regId);
                wbgaMsEuipmentDTO.setRegIp(regIp);

                wBGAExamMapper.insertMsEuipment(wbgaMsEuipmentDTO);

                //신청파일 넣기
                for (int i = 0; i < wBGAApplyMstDTO.getFileList().size(); i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    fileList.add(wBGAApplyMstDTO.getFileList().get(i));

                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                    wBGAApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    wBGAApplyDtlDTO.setFileCd(wBGAApplyMstDTO.getFileCdList().get(i));

                    wBGAExamMapper.insertFileInfo(wBGAApplyDtlDTO);
                }

                // 부품사 정보수정 Star
                wBGACompanyDTO.setRegId(regId);
                wBGACompanyDTO.setRegIp(regIp);
                wBGACompanyDTO.setModId(regId);
                wBGACompanyDTO.setModIp(regIp);

                //부품사 회원정보 수정
                wBGAExamMapper.updatePartUser(wBGACompanyDTO);

                String seq = "";

                if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for(int t=0; t<wBGACompanyDTO.getSqInfoList().size();t++) {
                        seq = String.valueOf(wBGACompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                        wBGACompanyDTO.setYear(wBGACompanyDTO.getSqInfoList().get(t).getYear());
                        wBGACompanyDTO.setScore(wBGACompanyDTO.getSqInfoList().get(t).getScore());
                        wBGACompanyDTO.setNm(wBGACompanyDTO.getSqInfoList().get(t).getNm());
                        wBGACompanyDTO.setCrtfnCmpnNm(wBGACompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wBGACompanyDTO.setTchlg5starCd(null);
                        wBGACompanyDTO.setPay5starCd(null);
                        wBGACompanyDTO.setQlty5starCd(null);
                        wBGACompanyDTO.setTchlg5starYear(null);
                        wBGACompanyDTO.setPay5starYear(null);
                        wBGACompanyDTO.setQlty5starYear(null);

                        if (!"null".equals(seq)) {
                            wBGACompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wBGAExamMapper.updatePartsComSQInfo(wBGACompanyDTO);
                        } else {
                            wBGACompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wBGAExamMapper.insertPartsComSQInfo(wBGACompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wBGAExamMapper.deletePartsComSQInfo(wBGACompanyDTO);
                }

                wBGAExamMapper.updatePartsCompany(wBGACompanyDTO);
                // 부품사 수정 End
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 부품사 신청자를 수정한다.
     */
    @Transactional
    public int update(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            wBGAApplyMstDTO.setAppctnBsnmNo(wBGACompanyDTO.getBsnmNo());
            wBGAApplyMstDTO.setModId(modId);
            wBGAApplyMstDTO.setModIp(modIp);

            rtnCnt = wBGAExamMapper.updateApply(wBGAApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상태 업데이트
                //관리자상태에 따라 분기처리해야함
                WBGAApplyDtlDTO wBGAApplyDtlDTO = new WBGAApplyDtlDTO();
                wBGAApplyDtlDTO.setRsumeSeq(wBGAApplyMstDTO.getRsumeSeq());
                wBGAApplyDtlDTO.setRsumeOrd(wBGAApplyMstDTO.getRsumeOrd());
                wBGAApplyDtlDTO.setAppctnSeq(Integer.valueOf(wBGAApplyMstDTO.getDetailsKey()));
                wBGAApplyDtlDTO.setMngSttsCd(wBGAApplyMstDTO.getMngSttsCd());
                wBGAApplyDtlDTO.setRegId(modId);
                wBGAApplyDtlDTO.setRegIp(modIp);
                wBGAApplyDtlDTO.setModId(modId);
                wBGAApplyDtlDTO.setModIp(modIp);


                //신청대상장비 생성시작
                wBGAExamMapper.deleteEuipment(wBGAApplyDtlDTO);

                for(int j=0; j<wBGAApplyMstDTO.getEuipmentList().size(); j++) {
                    WBGAEuipmentDTO wBGAEuipmentDTO = new WBGAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wBGAEuipmentDTO.setAppctnSeq(wBGAApplyDtlDTO.getAppctnSeq());
                    wBGAEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wBGAEuipmentDTO.setTchlgOrd(j+1);
                    wBGAEuipmentDTO.setTchlgNm(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wBGAEuipmentDTO.setTchlgCnt(wBGAApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wBGAEuipmentDTO.setRegId(modId);
                    wBGAEuipmentDTO.setRegIp(modIp);

                    wBGAExamMapper.insertEuipment(wBGAEuipmentDTO);
                }

                //단계별 프로세스
                stepUpdateProcess(wBGAApplyMstDTO,wBGAApplyDtlDTO, wBGAMsEuipmentDTO);

                //신청자 변경
                if ("Y".equals(wBGAApplyMstDTO.getUserLogYn())) {
                    WBBATransDTO wbbTransDTO = new WBBATransDTO();

                    wbbTransDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
                    wbbTransDTO.setAppctnSeq(Integer.valueOf(wBGAApplyMstDTO.getDetailsKey()));
                    wbbTransDTO.setBfreMemSeq(wBGAApplyMstDTO.getWbbTransDTO().getBfreMemSeq());
                    wbbTransDTO.setAftrMemSeq(wBGAApplyMstDTO.getWbbTransDTO().getAftrMemSeq());
                    wbbTransDTO.setRegId(modId);
                    wbbTransDTO.setRegIp(modIp);

                    //상생참여이관로그 생성
                    wBGAExamMapper.insertTransUserLog(wbbTransDTO);
                }


                //부품사 회원정보 수정 START
                wBGACompanyDTO.setRegId(modId);
                wBGACompanyDTO.setRegIp(modIp);
                wBGACompanyDTO.setModId(modId);
                wBGACompanyDTO.setModIp(modIp);
                wBGAExamMapper.updatePartUser(wBGACompanyDTO);

                // 부품사 정보수정 Start
                String seq = "";


                if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for(int t=0; t<wBGACompanyDTO.getSqInfoList().size();t++) {
                        seq = String.valueOf(wBGACompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                        wBGACompanyDTO.setYear(wBGACompanyDTO.getSqInfoList().get(t).getYear());
                        wBGACompanyDTO.setScore(wBGACompanyDTO.getSqInfoList().get(t).getScore());
                        wBGACompanyDTO.setNm(wBGACompanyDTO.getSqInfoList().get(t).getNm());
                        wBGACompanyDTO.setCrtfnCmpnNm(wBGACompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wBGACompanyDTO.setTchlg5starCd(null);
                        wBGACompanyDTO.setPay5starCd(null);
                        wBGACompanyDTO.setQlty5starCd(null);
                        wBGACompanyDTO.setTchlg5starYear(null);
                        wBGACompanyDTO.setPay5starYear(null);
                        wBGACompanyDTO.setQlty5starYear(null);

                        if (!"null".equals(seq)) {
                            wBGACompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wBGAExamMapper.updatePartsComSQInfo(wBGACompanyDTO);
                        } else {
                            wBGACompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wBGAExamMapper.insertPartsComSQInfo(wBGACompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wBGACompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wBGAExamMapper.deletePartsComSQInfo(wBGACompanyDTO);
                }

                wBGAExamMapper.updatePartsCompany(wBGACompanyDTO);
                // 부품사 수정 End
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public WBGAApplyDtlDTO stepUpdateProcess(WBGAApplyMstDTO wBGAApplyMstDTO, WBGAApplyDtlDTO wBGAApplyDtlDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO) throws Exception {
        //검교정 단계별 분기 ( 1: 신청,  2: 심사   3:증빙 )
        if (wBGAApplyDtlDTO.getRsumeOrd() == 1) {
            //신청단계
            if ("PRO_TYPE07001_02_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_006");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);

                //PRO_TYPE07001_01_006 선정 다음스텝 생성.
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setRsumeOrd(2);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07002");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_001");

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);

            } else if ("PRO_TYPE07001_02_002".equals(wBGAApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_02_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE06001_02_002 보완요청, PRO_TYPE07001_02_005 미선정 _ 사유필요
                if ("PRO_TYPE07001_02_002".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_002");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_005");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                }
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            } else if ("PRO_TYPE07001_02_004".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                // PRO_TYPE07001_02_004 사용자취소
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_004");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            }

            wBGAMsEuipmentDTO.setRegId(wBGAApplyDtlDTO.getRegId());
            wBGAMsEuipmentDTO.setRegIp(wBGAApplyDtlDTO.getRegIp());

            //계측장비상세 데이터처리
            wBGAExamMapper.deleteMsEuipment(wBGAMsEuipmentDTO);
            wBGAExamMapper.insertMsEuipment(wBGAMsEuipmentDTO);
            wBGAExamMapper.updatePicCmssrSeq(wBGAApplyMstDTO);

        } else if (wBGAApplyDtlDTO.getRsumeOrd() == 2) {
            //심사단계
            if ("PRO_TYPE07001_04_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_006 적합 다음스텝 생성
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);

                //PRO_TYPE07001_04_006 선정 다음스텝 생성.
                wBGAApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wBGAApplyDtlDTO.setRsumeOrd(3);
                wBGAApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07003");
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_001");
                wBGAApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_001");

                wBGAExamMapper.insertApplyDtl(wBGAApplyDtlDTO);

            } else if ("PRO_TYPE07001_04_003".equals(wBGAApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_04_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_003 보완요청, PRO_TYPE07001_04_005 부적합
                if ("PRO_TYPE07001_04_003".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_003");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_005");
                    wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
                }

                wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
            }

            wBGAMsEuipmentDTO.setRegId(wBGAApplyDtlDTO.getRegId());
            wBGAMsEuipmentDTO.setRegIp(wBGAApplyDtlDTO.getRegIp());

            //계측장비상세 데이터처리
            wBGAExamMapper.deleteMsEuipment(wBGAMsEuipmentDTO);
            wBGAExamMapper.insertMsEuipment(wBGAMsEuipmentDTO);
            wBGAExamMapper.updatePicCmssrSeq(wBGAApplyMstDTO);

        } else if (wBGAApplyDtlDTO.getRsumeOrd() == 3) {
            //증빙단계
            if ("PRO_TYPE07001_06_003".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_003 보완요청
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_003");
                wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_005".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_005 부적합
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_005");
                wBGAApplyDtlDTO.setRtrnRsnCntn(wBGAApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_006".equals(wBGAApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_006 적합
                wBGAApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
            }
            wBGAExamMapper.updateApplyStatus(wBGAApplyDtlDTO);
        }

        //파일처리
        if (wBGAApplyDtlDTO.getRsumeOrd() == 1 || wBGAApplyDtlDTO.getRsumeOrd() == 3) {
            if (wBGAApplyMstDTO.getFileList() != null) {
                wBGAExamMapper.deleteFileInfo(wBGAApplyDtlDTO);

                //신청파일 넣기
                for (int i = 0; i < wBGAApplyMstDTO.getFileList().size() ; i++) {
                    List<COFileDTO> fileList = new ArrayList();
                    COFileDTO fileDto = new COFileDTO();

                    fileDto.setStatus(wBGAApplyMstDTO.getFileList().get(i).getStatus());
                    fileDto.setWidth(wBGAApplyMstDTO.getFileList().get(i).getWidth());
                    fileDto.setHeight(wBGAApplyMstDTO.getFileList().get(i).getHeight());
                    fileDto.setWebPath(wBGAApplyMstDTO.getFileList().get(i).getWebPath());
                    fileDto.setFieldNm(wBGAApplyMstDTO.getFileList().get(i).getFieldNm());
                    fileDto.setOrgnFileNm(wBGAApplyMstDTO.getFileList().get(i).getOrgnFileNm());
                    fileDto.setFileDsc(wBGAApplyMstDTO.getFileList().get(i).getFileDsc());
                    fileDto.setFileOrd(wBGAApplyMstDTO.getFileList().get(i).getFileOrd());
                    fileList.add(fileDto);

                    if ("addedfile".equals(fileDto.getStatus())) {
                        wBGAApplyDtlDTO.setFileSeq(wBGAApplyMstDTO.getFileList().get(i).getFileSeq());
                    } else {
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wBGAApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    }

                    wBGAApplyDtlDTO.setFileCd(wBGAApplyMstDTO.getFileCdList().get(i));
                    wBGAExamMapper.insertFileInfo(wBGAApplyDtlDTO);
                }
            }
        }
        return  wBGAApplyDtlDTO;
    }

    public WBBATransDTO getTrnsfList(WBBATransDTO wbbaTransDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaTransDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaTransDTO.getListRowSize());

        page.setPageSize(wbbaTransDTO.getPageRowSize());

        wbbaTransDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaTransDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaTransDTO.setList(wBGAExamMapper.getTrnsfList(wbbaTransDTO));
        wbbaTransDTO.setTotalCount(wBGAExamMapper.getTrnsfCount(wbbaTransDTO));

        return wbbaTransDTO;
    }

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBGAExamSearchDTO wBGAExamSearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBGAExamMapper.getRsumePbsnCnt(wBGAExamSearchDTO);

        return respCnt;
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBGAExamSearchDTO getRoundDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception {

        //양식가져오기
        SMJFormDTO smjFormDTO = new SMJFormDTO();
        smjFormDTO.setTypeCd("BUSINESS02");
        smjFormDTO = smjFormMapper.selectFormDtl(smjFormDTO);

        wbgaExamSearchDTO.setFileSeq(smjFormDTO.getClbtnFileSeq());
        wbgaExamSearchDTO.setFileOrd(smjFormDTO.getClbtnFileOrd());

        //신청가능 매출액가져오기
        WBGAValidDTO wbgaValidDTO = new WBGAValidDTO();
        wbgaValidDTO = wBGAExamMapper.selectExamValid(wbgaExamSearchDTO);
        wbgaExamSearchDTO.setStndSlsPmt(wbgaValidDTO.getStndSlsPmt());

        return wbgaExamSearchDTO;
    }

    /**
     * 최신 회차 상세 조회
     */
    public int getApplyChecked(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception {

        int rtnCode = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;

        if (!COUserDetailsHelperService.isAuthenticated())
        {
            //비로그인 코드 999
            rtnCode = 999;
        }
        else
        {
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            if (!"CP".equals(cOUserDetailsDTO.getAuthCd())) {
                if ("CS".equals(cOUserDetailsDTO.getAuthCd())) {
                    //위원인 경우 150
                    rtnCode = 150;
                } else {
                    //부품사회원이 아닌경우 100
                    rtnCode = 100;
                }
            } else if ("CP".equals(cOUserDetailsDTO.getAuthCd())) {

                WBGACompanyDTO wbgaCompanyDTO = new WBGACompanyDTO();
                wbgaExamSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wbgaCompanyDTO = wBGAExamMapper.getCompanyInfo(wbgaExamSearchDTO);

                if ("COMPANY01001".equals(wbgaCompanyDTO.getCtgryCd()) || "COMPANY01002".equals(wbgaCompanyDTO.getCtgryCd())) {
                    wbgaExamSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                    //신청시간 년도 구하기
                    Calendar cal = Calendar.getInstance();

                    wbgaExamSearchDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));

                    int cnt = wBGAExamMapper.getApplyCount(wbgaExamSearchDTO);

                    if (cnt > 0) {
                        //신청여부 존재 코드 300
                        rtnCode = 300;
                    } else {
                        //참여조건만족 여부 체크
                        //1. 매출액 비교
                        WBGAValidDTO wbgaValidDTO = new WBGAValidDTO();
                        wbgaValidDTO = wBGAExamMapper.selectExamValid(wbgaExamSearchDTO);

                        cal.add(Calendar.YEAR, -1);
                        int yyyy = cal.get(Calendar.YEAR);
                        if ((yyyy != wbgaCompanyDTO.getSlsYear()) || (wbgaCompanyDTO.getSlsPmt() > wbgaValidDTO.getStndSlsPmt())) {
                            //현재 신청년도와 부품사정보에 기입 된 매출년도의 값이 같지 않을떄 및 매출액 금액이 맞지 않는 경우
                            rtnCode = 400;
                        } else {
                            //컨설팅지도 및 기술지도 체크
                            List<WBGAValidDtlDTO> list = wBGAExamMapper.selectExamValidDtlList(wbgaExamSearchDTO);

                            if (list.size() > 0) {
                                wbgaExamSearchDTO.setValidDtlDTOList(list);
                                int rtnCnt = wBGAExamMapper.getApplyCompanyCnt(wbgaExamSearchDTO);

                                if (rtnCnt > 0) {
                                    //신청가능 코드 450
                                    rtnCode = 200;
                                    RequestContextHolder.getRequestAttributes().setAttribute("contentAuth", "Y", RequestAttributes.SCOPE_SESSION);
                                } else {
                                    //컨설팅 내역없음 코드 200
                                    rtnCode = 450;
                                }
                            } else {
                                rtnCode = 450;
                            }
                        }
                    }
                }
            } else {
                //부품사가 1차 2차가 아닐떄,
                rtnCode = 190;
            }
        }

        return rtnCode;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insertApply(WBGAApplyMstDTO wbgaApplyMstDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;
        try {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            //기존 신청자인지 확인,,,
            WBGAExamSearchDTO wbgaExamSearchDTO = new WBGAExamSearchDTO();
            wbgaExamSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
            //신청시간 년도 구하기
            Calendar cal = Calendar.getInstance();
            wbgaExamSearchDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));

            rtnCnt = wBGAExamMapper.getApplyCount(wbgaExamSearchDTO);

            if (rtnCnt > 0) {
                //기존 신청이력 존재
                rtnCnt = 999;
            } else {
                //신청 프로세스 시작
                String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
                String regIp = CONetworkUtil.getMyIPaddress(request);

                wbgaApplyMstDTO.setBsnCd("BSN07");
                wbgaApplyMstDTO.setYear(wbgaExamSearchDTO.getYear());
                WBRoundMstSearchDTO round = wBGAExamMapper.getExisdDtl(wbgaApplyMstDTO);

                if (round == null) {
                    //해당년도 회차 미 존재시 신규회차 생성
                    int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                    WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                    roundDtl.setEpisdSeq(exipsdSeq);
                    roundDtl.setBsnCd(wbgaApplyMstDTO.getBsnCd());
                    roundDtl.setYear(Integer.valueOf(wbgaApplyMstDTO.getYear()));
                    roundDtl.setEpisd(1); //회차는 1회차 고정
                    roundDtl.setAccsStrtDtm(wbgaApplyMstDTO.getYear()+"-01-01 00:00:00");
                    roundDtl.setAccsEndDtm(wbgaApplyMstDTO.getYear()+"-12-31 23:59:59");
                    roundDtl.setBsnStrtDtm(wbgaApplyMstDTO.getYear()+"-01-01 00:00:00");
                    roundDtl.setBsnEndDtm(wbgaApplyMstDTO.getYear()+"-12-31 23:59:59");
                    roundDtl.setExpsYn("Y");
                    roundDtl.setRegId(regId);
                    roundDtl.setRegIp(regIp);

                    wBGAExamMapper.insetRound(roundDtl);

                    wbgaApplyMstDTO.setEpisdSeq(exipsdSeq);
                } else {
                    wbgaApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
                }

                int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
                //마스터 생성
                wbgaApplyMstDTO.setAppctnSeq(appctnSeq);
                wbgaApplyMstDTO.setAppctnBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wbgaApplyMstDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                wbgaApplyMstDTO.setRegId(regId);
                wbgaApplyMstDTO.setRegIp(regIp);

                rtnCnt = wBGAExamMapper.insertApply(wbgaApplyMstDTO);

                if (rtnCnt > 0) {
                    //상생신청진행 상세 생성
                    WBGAApplyDtlDTO wbgaApplyDtlDTO = new WBGAApplyDtlDTO();
                    wbgaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                    wbgaApplyDtlDTO.setAppctnSeq(wbgaApplyMstDTO.getAppctnSeq());
                    wbgaApplyDtlDTO.setRsumeOrd(1);
                    wbgaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                    wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                    wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                    wbgaApplyDtlDTO.setRegId(regId);
                    wbgaApplyDtlDTO.setRegIp(regIp);

                    wBGAExamMapper.insertApplyDtl(wbgaApplyDtlDTO);


                    //신청대상장비 생성
                    for (int j = 0; j < wbgaApplyMstDTO.getEuipmentList().size(); j++) {
                        WBGAEuipmentDTO wbgaEuipmentDTO = new WBGAEuipmentDTO();
                        int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                        wbgaEuipmentDTO.setAppctnSeq(appctnSeq);
                        wbgaEuipmentDTO.setTchlgSeq(tchlgSeq);
                        wbgaEuipmentDTO.setTchlgOrd(j + 1);
                        wbgaEuipmentDTO.setTchlgNm(wbgaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                        wbgaEuipmentDTO.setTchlgCnt(wbgaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                        wbgaEuipmentDTO.setRegId(regId);
                        wbgaEuipmentDTO.setRegIp(regIp);

                        wBGAExamMapper.insertEuipment(wbgaEuipmentDTO);
                    }

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
                        rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                        for (int i = 0; i < rtnList.size() ; i++) {

                            List<COFileDTO> fileList = new ArrayList();
                            rtnList.get(i).setStatus("success");
                            rtnList.get(i).setFieldNm("fileSeq");
                            fileList.add(rtnList.get(i));

                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                            wbgaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                            wbgaApplyDtlDTO.setFileCd(wbgaApplyMstDTO.getFileCdList().get(i));

                            wBGAExamMapper.insertFileInfo(wbgaApplyDtlDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 부품사 신청자를 조회한다.
     */
    public WBGAExamSearchDTO getApplyDtl(WBGAExamSearchDTO wbgaExamSearchDTO) throws Exception {

        WBGAApplyMstDTO wbgaApplyMstDTO = new WBGAApplyMstDTO();
        Calendar cal = Calendar.getInstance();
        wbgaApplyMstDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));
        wbgaApplyMstDTO.setBsnCd("BSN07");
        WBRoundMstSearchDTO round = wBGAExamMapper.getExisdDtl(wbgaApplyMstDTO);

        wbgaExamSearchDTO.setEpisdSeq(round.getEpisdSeq());

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wbgaExamSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
        wbgaExamSearchDTO = wBGAExamMapper.getApplyDtl(wbgaExamSearchDTO);

        return wbgaExamSearchDTO;
    }

    /**
     * 사용자페이지 - 신청자를 수정한다.
     */
    @Transactional
    public int updateInfo(WBGAApplyDtlDTO wbgaApplyDtlDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            //상생신청진행 상태 업데이트
            wbgaApplyDtlDTO.setRegId(modId);
            wbgaApplyDtlDTO.setRegIp(modIp);
            wbgaApplyDtlDTO.setModId(modId);
            wbgaApplyDtlDTO.setModIp(modIp);


            if (wbgaApplyDtlDTO.getRsumeOrd() == 1 || wbgaApplyDtlDTO.getRsumeOrd() == 3) {
                wBGAExamMapper.deleteFileInfo(wbgaApplyDtlDTO);

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
                    rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                    for (int i = 0; i < rtnList.size() ; i++) {

                        List<COFileDTO> fileList = new ArrayList();
                        Integer fileSeq;

                        if ("99".equals(rtnList.get(i).getRespCd())) {
                            fileSeq = wbgaApplyDtlDTO.getFileSeqList().get(i);
                        } else {
                            rtnList.get(i).setStatus("success");
                            rtnList.get(i).setFieldNm("fileSeq");
                            fileList.add(rtnList.get(i));
                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                            fileSeq = fileSeqMap.get("fileSeq");
                        }

                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wbgaApplyDtlDTO.setFileSeq(fileSeq);
                        wbgaApplyDtlDTO.setFileCd(wbgaApplyDtlDTO.getFileCdList().get(i));

                        wBGAExamMapper.insertFileInfo(wbgaApplyDtlDTO);
                    }
                }
            }

            rtnCnt = stepUpdateUserProcess(wbgaApplyDtlDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public int stepUpdateUserProcess(WBGAApplyDtlDTO wbgaApplyDtlDTO) throws Exception {
        int rsultCnt = 0;
        if (wbgaApplyDtlDTO.getRsumeOrd() == 1) {

            //신청대상장비 생성시작
            wBGAExamMapper.deleteEuipment(wbgaApplyDtlDTO);

            for(int j=0; j<wbgaApplyDtlDTO.getEuipmentList().size(); j++) {
                WBGAEuipmentDTO wbgaEuipmentDTO = new WBGAEuipmentDTO();
                int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                wbgaEuipmentDTO.setAppctnSeq(wbgaApplyDtlDTO.getAppctnSeq());
                wbgaEuipmentDTO.setTchlgSeq(tchlgSeq);
                wbgaEuipmentDTO.setTchlgOrd(j+1);
                wbgaEuipmentDTO.setTchlgNm(wbgaApplyDtlDTO.getEuipmentList().get(j).getTchlgNm());
                wbgaEuipmentDTO.setTchlgCnt(wbgaApplyDtlDTO.getEuipmentList().get(j).getTchlgCnt());
                wbgaEuipmentDTO.setRegId(wbgaApplyDtlDTO.getModId());
                wbgaEuipmentDTO.setRegIp(wbgaApplyDtlDTO.getModIp());

                wBGAExamMapper.insertEuipment(wbgaEuipmentDTO);
            }

            if ("PRO_TYPE07001_01_002".equals(wbgaApplyDtlDTO.getAppctnSttsCd())) {
                //보안요청 사용자 -> 보완완료 관리자-> 미확인
                wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_003");
                wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");

                rsultCnt = wBGAExamMapper.updateApplyStatus(wbgaApplyDtlDTO);
            }

        } else if (wbgaApplyDtlDTO.getRsumeOrd() == 2) {
            WBGAMsEuipmentDTO wbgaMsEuipmentDTO = new WBGAMsEuipmentDTO();
            wbgaMsEuipmentDTO.setRsumeSeq(wbgaApplyDtlDTO.getRsumeSeq());
            wbgaMsEuipmentDTO.setRsumeOrd(wbgaApplyDtlDTO.getRsumeOrd());
            wbgaMsEuipmentDTO.setNvstmPmt(wbgaApplyDtlDTO.getWbgaMsEuipmentDTO().getNvstmPmt());
            wbgaMsEuipmentDTO.setRegId(wbgaApplyDtlDTO.getModId());
            wbgaMsEuipmentDTO.setRegIp(wbgaApplyDtlDTO.getModIp());
            wbgaMsEuipmentDTO.setModId(wbgaApplyDtlDTO.getModId());
            wbgaMsEuipmentDTO.setModIp(wbgaApplyDtlDTO.getModIp());


            if ("PRO_TYPE07001_03_001".equals(wbgaApplyDtlDTO.getAppctnSttsCd())) {
                //접수전 사용자 -> 접수완료 관리자-> 미확인
                wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_002");
                wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_002");

                wBGAExamMapper.insertMsEuipment(wbgaMsEuipmentDTO);
                rsultCnt = wBGAExamMapper.updateApplyStatus(wbgaApplyDtlDTO);
            } else if ("PRO_TYPE07001_03_003".equals(wbgaApplyDtlDTO.getAppctnSttsCd())) {
                //보완요청 사용자 -> 보완완료 관리자-> 미확인
                wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_004");
                wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_002");

                wBGAExamMapper.updateMsEuipment(wbgaMsEuipmentDTO);
                rsultCnt = wBGAExamMapper.updateApplyStatus(wbgaApplyDtlDTO);
            }
        } else if (wbgaApplyDtlDTO.getRsumeOrd() == 3) {
            if ("PRO_TYPE07001_05_001".equals(wbgaApplyDtlDTO.getAppctnSttsCd())) {
                //접수전 사용자 -> 접수완료 관리자-> 미확인
                wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_002");
                wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_002");

                rsultCnt = wBGAExamMapper.updateApplyStatus(wbgaApplyDtlDTO);
            } else if ("PRO_TYPE07001_05_003".equals(wbgaApplyDtlDTO.getAppctnSttsCd())) {
                //보완요청 사용자 -> 보완완료 관리자-> 미확인
                wbgaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_004");
                wbgaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_002");

                rsultCnt = wBGAExamMapper.updateApplyStatus(wbgaApplyDtlDTO);
            }
        }

        return rsultCnt;
    }
}
