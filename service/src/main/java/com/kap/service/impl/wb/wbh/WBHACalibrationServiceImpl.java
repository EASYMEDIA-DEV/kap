package com.kap.service.impl.wb.wbh;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import com.kap.core.dto.wb.wbb.*;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbh.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBHACalibrationService;
import com.kap.service.dao.sm.SMJFormMapper;
import com.kap.service.dao.wb.wbh.WBHACalibrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
 * @ClassName		: WBHACalibrationServiceImpl.java
 * @Description		: 신청업체관리 ServiceImpl
 * @author 김태훈
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBHACalibrationServiceImpl implements WBHACalibrationService {
    //Mapper
    private final WBHACalibrationMapper wbhaCalibrationMapper;
    private final SMJFormMapper smjFormMapper;

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;
    private final COFileService cOFileService;

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
    public WBHAConsultingDTO getConsultingList(WBHAConsultingDTO wbaConsultingDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbaConsultingDTO.getPageIndex());
        page.setRecordCountPerPage(wbaConsultingDTO.getListRowSize());

        page.setPageSize(wbaConsultingDTO.getPageRowSize());

        wbaConsultingDTO.setFirstIndex(page.getFirstRecordIndex());
        wbaConsultingDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wbaConsultingDTO.setList(wbhaCalibrationMapper.getConsultingList(wbaConsultingDTO));
        wbaConsultingDTO.setTotalCount(wbhaCalibrationMapper.getConsultingCount(wbaConsultingDTO));

        return wbaConsultingDTO;
    }

    /**
     * 신청 목록
     */
    public WBHACalibrationSearchDTO selectCalibrationList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBHACalibrationSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBHACalibrationSearchDTO.getListRowSize());

        page.setPageSize(wBHACalibrationSearchDTO.getPageRowSize());

        wBHACalibrationSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBHACalibrationSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBHACalibrationSearchDTO.setList(wbhaCalibrationMapper.selectCalibrationList(wBHACalibrationSearchDTO));
        wBHACalibrationSearchDTO.setTotalCount(wbhaCalibrationMapper.getCalibrationListTotCnt(wBHACalibrationSearchDTO));

        return wBHACalibrationSearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception {
        return wbhaCalibrationMapper.selectYearDtl(wBHACalibrationSearchDTO);
    }


    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception {


        List<String> appctnSeqList = wBHACalibrationSearchDTO.getDelValueList();

        wBHACalibrationSearchDTO.setDelValueList(appctnSeqList);

        //이관삭제
        wbhaCalibrationMapper.carbonCompanyDeleteTrnsf(wBHACalibrationSearchDTO);
        //장비삭제
        wbhaCalibrationMapper.carbonCompanyDeleteTchlg(wBHACalibrationSearchDTO);


        wBHACalibrationSearchDTO.setDelValueList(wbhaCalibrationMapper.selectRsumeSeq(wBHACalibrationSearchDTO));

        //신청진행계측 삭제
        wbhaCalibrationMapper.carbonCompanyDeleteRsumeTchlg(wBHACalibrationSearchDTO);
        //신청파일 삭제
        wbhaCalibrationMapper.carbonCompanyDeleteRsumeFile(wBHACalibrationSearchDTO);


        wBHACalibrationSearchDTO.setDelValueList(appctnSeqList);
        //신청진행상세 삭제
        wbhaCalibrationMapper.carbonCompanyDeleteRsume(wBHACalibrationSearchDTO);

        int respCnt = wbhaCalibrationMapper.carbonCompanyDeleteMst(wBHACalibrationSearchDTO);

        return respCnt;

    }

    /**
     * 옵션 목록
     */
    public WBHAValidDTO selectExamValid(WBHACalibrationSearchDTO wBHACalibrationSearchDTO) throws Exception {
        WBHAValidDTO wBHAValidDTO = new WBHAValidDTO();
        wBHAValidDTO = wbhaCalibrationMapper.selectExamValid(wBHACalibrationSearchDTO);

        List<WBHAValidDtlDTO>  wBGAValidDtlDTO = wbhaCalibrationMapper.selectExamValidDtlList(wBHACalibrationSearchDTO);
        if(wBGAValidDtlDTO.size() > 0){
            wBHAValidDTO.setDtlList(wBGAValidDtlDTO);
        }

        return wBHAValidDTO;
    }

    /**
     * 옵션 수정
     */
    public int examValidUpdate(WBHAValidDTO wBHAValidDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int appctnValidSeqIdgen;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();


        if(wBHAValidDTO.getValidSeq() != null && !"".equals(wBHAValidDTO.getValidSeq())){
            appctnValidSeqIdgen = wBHAValidDTO.getValidSeq();
            wBHAValidDTO.setModId(coaAdmDTO.getId());
            wBHAValidDTO.setModIp(coaAdmDTO.getLoginIp());
            respCnt = wbhaCalibrationMapper.examValidUpdate(wBHAValidDTO);
        }else{
            appctnValidSeqIdgen = cxAppctnValidMstIdgen.getNextIntegerId();
            wBHAValidDTO.setValidSeq(appctnValidSeqIdgen);
            wBHAValidDTO.setRegId(coaAdmDTO.getId());
            wBHAValidDTO.setRegIp(coaAdmDTO.getLoginIp());
            respCnt = wbhaCalibrationMapper.examValidInsert(wBHAValidDTO);
        }


        wbhaCalibrationMapper.deleteValidDtl(wBHAValidDTO);

        for(int i=0; i < wBHAValidDTO.getDtlList().size(); i++){
            WBHAValidDtlDTO wBHAValidDtlDTO = wBHAValidDTO.getDtlList().get(i);
            wBHAValidDtlDTO.setValidSeq(appctnValidSeqIdgen);
            wBHAValidDtlDTO.setRegId(coaAdmDTO.getId());
            wBHAValidDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wbhaCalibrationMapper.examValidDtlInsert(wBHAValidDtlDTO);
        }

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBHACalibrationSearchDTO wBHACalibrationSearchDTO, HttpServletResponse response) throws Exception {
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

        /*cell = row.createCell(18);
        cell.setCellValue("관리자등록일");
        cell.setCellStyle(style_header);*/

        cell = row.createCell(19);
        cell.setCellValue("사용자수정일");
        cell.setCellStyle(style_header);


        // Body
        List<WBHACalibrationSearchDTO> list = wBHACalibrationSearchDTO.getList();

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBHACalibrationSearchDTO.getTotalCount() - i);
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

            /*//관리자 등록일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getRegDtm());
            cell.setCellStyle(style_body);*/

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
    public WBHAApplyMstDTO selectCompanyDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        WBHAApplyMstDTO wbhaApplyMstDTO = wbhaCalibrationMapper.selectApplyMst(wbhaCalibrationSearchDTO);

        List<WBHAApplyDtlDTO> selectApplyDtlList = wbhaCalibrationMapper.selectApplyDtlList(wbhaCalibrationSearchDTO);
        List<WBHAEuipmentDTO> selectEuipemtDtlList = wbhaCalibrationMapper.selectEuipemtDtlList(wbhaCalibrationSearchDTO);

        wbhaApplyMstDTO.setEuipmentList(selectEuipemtDtlList);

        wbhaApplyMstDTO.setApplyList(new ArrayList<WBHAApplyDtlDTO>());

        for (WBHAApplyDtlDTO dtlDto : selectApplyDtlList) {

            List<WBHAApplyDtlDTO> fileList = wbhaCalibrationMapper.selectFileList(dtlDto);
            List<WBHAMsEuipmentDTO> msEuipmentList = wbhaCalibrationMapper.selectMsEuipmentList(dtlDto);

            dtlDto.setFileInfoList(new ArrayList<WBHAApplyDtlDTO>());
            dtlDto.setMsEquipmentList(new ArrayList<WBHAMsEuipmentDTO>());

            //단계별 파일정보 넣기
            for(WBHAApplyDtlDTO fileDTO : fileList) {
                if (fileDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && fileDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getFileInfoList().add(fileDTO);
                }
            }

            //단계별 계측장비정보 넣기
            for(WBHAMsEuipmentDTO msEuipmentDTO : msEuipmentList) {
                if (msEuipmentDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && msEuipmentDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getMsEquipmentList().add(msEuipmentDTO);
                }
            }

            wbhaApplyMstDTO.getApplyList().add(dtlDto);
        }

        wbhaApplyMstDTO.setRsumeSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeSttsNm());
        wbhaApplyMstDTO.setMngSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getMngSttsNm());
        wbhaApplyMstDTO.setStageOrd(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeOrd());

        return wbhaApplyMstDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBHACompanyDTO selectCompanyUserDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        WBHACompanyDTO wbhaCompanyDTO = new WBHACompanyDTO();

        wbhaCompanyDTO = wbhaCalibrationMapper.getCompanyInfo(wbhaCalibrationSearchDTO);
        List<WBHACompanyDTO> sqList = wbhaCalibrationMapper.selectSqList(wbhaCalibrationSearchDTO);
        wbhaCompanyDTO.setSqInfoList(sqList);
        wbhaCompanyDTO.setMemSeq(wbhaCalibrationSearchDTO.getMemSeq());

        return wbhaCompanyDTO;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insert(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {

            //신청시간 년도 구하기
            Date now = new Date();
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

            wbhaApplyMstDTO.setYear(yyyy.format(now));

            WBRoundMstSearchDTO round = wbhaCalibrationMapper.getExisdDtl(wbhaApplyMstDTO);

            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            if (round == null) {
                //해당년도 회차 미 존재시 신규회차 생성
                int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                roundDtl.setEpisdSeq(exipsdSeq);
                roundDtl.setBsnCd(wbhaApplyMstDTO.getBsnCd());
                roundDtl.setYear(Integer.valueOf(wbhaApplyMstDTO.getYear()));
                roundDtl.setEpisd(1); //회차는 1회차 고정
                roundDtl.setAccsStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setAccsEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setBsnStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setBsnEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setExpsYn("Y");
                roundDtl.setRegId(regId);
                roundDtl.setRegIp(regIp);

                wbhaCalibrationMapper.insetRound(roundDtl);

                wbhaApplyMstDTO.setEpisdSeq(exipsdSeq);
            } else {
                wbhaApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
            }

            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            wbhaApplyMstDTO.setAppctnSeq(appctnSeq);
            wbhaApplyMstDTO.setAppctnBsnmNo(wbhaCompanyDTO.getBsnmNo());
            wbhaApplyMstDTO.setRegId(regId);
            wbhaApplyMstDTO.setRegIp(regIp);

            rtnCnt = wbhaCalibrationMapper.insertApply(wbhaApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                WBHAApplyDtlDTO wbhaApplyDtlDTO = new WBHAApplyDtlDTO();
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setAppctnSeq(wbhaApplyMstDTO.getAppctnSeq());
                wbhaApplyDtlDTO.setRsumeOrd(1);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                wbhaApplyDtlDTO.setRegId(regId);
                wbhaApplyDtlDTO.setRegIp(regIp);

                wbhaCalibrationMapper.insertApplyDtl(wbhaApplyDtlDTO);


                //신청대상장비 생성
                for(int j=0; j<wbhaApplyMstDTO.getEuipmentList().size(); j++) {
                    WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wbhaEuipmentDTO.setAppctnSeq(appctnSeq);
                    wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wbhaEuipmentDTO.setTchlgOrd(j+1);
                    wbhaEuipmentDTO.setTchlgNm(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wbhaEuipmentDTO.setTchlgCnt(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wbhaEuipmentDTO.setRegId(regId);
                    wbhaEuipmentDTO.setRegIp(regIp);

                    wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
                }

                //신청파일 넣기
                for (int i = 0; i < wbhaApplyMstDTO.getFileList().size(); i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    fileList.add(wbhaApplyMstDTO.getFileList().get(i));

                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                    wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    wbhaApplyDtlDTO.setFileCd(wbhaApplyMstDTO.getFileCdList().get(i));

                    wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
                }

                // 부품사 정보수정 Star
                wbhaCompanyDTO.setRegId(regId);
                wbhaCompanyDTO.setRegIp(regIp);
                wbhaCompanyDTO.setModId(regId);
                wbhaCompanyDTO.setModIp(regIp);

                //부품사 회원정보 수정
                wbhaCalibrationMapper.updatePartUser(wbhaCompanyDTO);

                String seq = "";

                if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for (int t = 0; t < wbhaCompanyDTO.getSqInfoList().size(); t++) {
                        seq = String.valueOf(wbhaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());

                        wbhaCompanyDTO.setYear(wbhaCompanyDTO.getSqInfoList().get(t).getYear());
                        wbhaCompanyDTO.setScore(wbhaCompanyDTO.getSqInfoList().get(t).getScore());
                        wbhaCompanyDTO.setNm(wbhaCompanyDTO.getSqInfoList().get(t).getNm());
                        wbhaCompanyDTO.setCrtfnCmpnNm(wbhaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wbhaCompanyDTO.setTchlg5starCd(null);
                        wbhaCompanyDTO.setPay5starCd(null);
                        wbhaCompanyDTO.setQlty5starCd(null);
                        wbhaCompanyDTO.setTchlg5starYear(null);
                        wbhaCompanyDTO.setPay5starYear(null);
                        wbhaCompanyDTO.setQlty5starYear(null);

                        if (!"null".equals(seq)) {
                            wbhaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wbhaCalibrationMapper.updatePartsComSQInfo(wbhaCompanyDTO);
                        } else {
                            wbhaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wbhaCalibrationMapper.insertPartsComSQInfo(wbhaCompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wbhaCalibrationMapper.deletePartsComSQInfo(wbhaCompanyDTO);
                }

                wbhaCalibrationMapper.updatePartsCompany(wbhaCompanyDTO);
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
    public int update(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            wbhaApplyMstDTO.setAppctnBsnmNo(wbhaCompanyDTO.getBsnmNo());
            wbhaApplyMstDTO.setModId(modId);
            wbhaApplyMstDTO.setModIp(modIp);

            rtnCnt = wbhaCalibrationMapper.updateApply(wbhaApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상태 업데이트
                //관리자상태에 따라 분기처리해야함
                WBHAApplyDtlDTO wbhaApplyDtlDTO = new WBHAApplyDtlDTO();
                wbhaApplyDtlDTO.setRsumeSeq(wbhaApplyMstDTO.getRsumeSeq());
                wbhaApplyDtlDTO.setRsumeOrd(wbhaApplyMstDTO.getRsumeOrd());
                wbhaApplyDtlDTO.setAppctnSeq(Integer.valueOf(wbhaApplyMstDTO.getDetailsKey()));
                wbhaApplyDtlDTO.setMngSttsCd(wbhaApplyMstDTO.getMngSttsCd());
                wbhaApplyDtlDTO.setRegId(modId);
                wbhaApplyDtlDTO.setRegIp(modIp);
                wbhaApplyDtlDTO.setModId(modId);
                wbhaApplyDtlDTO.setModIp(modIp);


                //신청대상장비 생성시작
                wbhaCalibrationMapper.deleteEuipment(wbhaApplyDtlDTO);

                for(int j=0; j<wbhaApplyMstDTO.getEuipmentList().size(); j++) {
                    WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wbhaEuipmentDTO.setAppctnSeq(wbhaApplyDtlDTO.getAppctnSeq());
                    wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wbhaEuipmentDTO.setTchlgOrd(j+1);
                    wbhaEuipmentDTO.setTchlgNm(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wbhaEuipmentDTO.setTchlgCnt(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wbhaEuipmentDTO.setRegId(modId);
                    wbhaEuipmentDTO.setRegIp(modIp);

                    wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
                }

                //단계별 프로세스
                wbhaApplyDtlDTO = stepUpdateProcess(wbhaApplyMstDTO,wbhaApplyDtlDTO, wbhaMsEuipmentDTO);

                //신청자 변경
                if ("Y".equals(wbhaApplyMstDTO.getUserLogYn())) {
                    WBBATransDTO wbbTransDTO = new WBBATransDTO();

                    wbbTransDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
                    wbbTransDTO.setAppctnSeq(Integer.valueOf(wbhaApplyMstDTO.getDetailsKey()));
                    wbbTransDTO.setBfreMemSeq(wbhaApplyMstDTO.getWbbTransDTO().getBfreMemSeq());
                    wbbTransDTO.setAftrMemSeq(wbhaApplyMstDTO.getWbbTransDTO().getAftrMemSeq());
                    wbbTransDTO.setRegId(modId);
                    wbbTransDTO.setRegIp(modIp);

                    //상생참여이관로그 생성
                    wbhaCalibrationMapper.insertTransUserLog(wbbTransDTO);
                }


                //부품사 회원정보 수정 START
                wbhaCompanyDTO.setRegId(modId);
                wbhaCompanyDTO.setRegIp(modIp);
                wbhaCompanyDTO.setModId(modId);
                wbhaCompanyDTO.setModIp(modIp);
                wbhaCalibrationMapper.updatePartUser(wbhaCompanyDTO);

                // 부품사 정보수정 Start
                String seq = "";


                if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for(int t=0; t<wbhaCompanyDTO.getSqInfoList().size();t++) {
                        seq = String.valueOf(wbhaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                        wbhaCompanyDTO.setYear(wbhaCompanyDTO.getSqInfoList().get(t).getYear());
                        wbhaCompanyDTO.setScore(wbhaCompanyDTO.getSqInfoList().get(t).getScore());
                        wbhaCompanyDTO.setNm(wbhaCompanyDTO.getSqInfoList().get(t).getNm());
                        wbhaCompanyDTO.setCrtfnCmpnNm(wbhaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wbhaCompanyDTO.setTchlg5starCd(null);
                        wbhaCompanyDTO.setPay5starCd(null);
                        wbhaCompanyDTO.setQlty5starCd(null);
                        wbhaCompanyDTO.setTchlg5starYear(null);
                        wbhaCompanyDTO.setPay5starYear(null);
                        wbhaCompanyDTO.setQlty5starYear(null);

                        if (!"null".equals(seq)) {
                            wbhaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wbhaCalibrationMapper.updatePartsComSQInfo(wbhaCompanyDTO);
                        } else {
                            wbhaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wbhaCalibrationMapper.insertPartsComSQInfo(wbhaCompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wbhaCalibrationMapper.deletePartsComSQInfo(wbhaCompanyDTO);
                }

                wbhaCalibrationMapper.updatePartsCompany(wbhaCompanyDTO);
                // 부품사 수정 End
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public WBHAApplyDtlDTO stepUpdateProcess(WBHAApplyMstDTO wbhaApplyMstDTO, WBHAApplyDtlDTO wbhaApplyDtlDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO) throws Exception {
        //검교정 단계별 분기 ( 1: 신청,  2: 심사   3:증빙 )
        if (wbhaApplyDtlDTO.getRsumeOrd() == 1) {
            //신청단계
            if ("PRO_TYPE07001_02_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_006");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);

                //PRO_TYPE07001_01_006 선정 다음스텝 생성.
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setRsumeOrd(2);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07002");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_001");

                wbhaCalibrationMapper.insertApplyStep(wbhaApplyDtlDTO);

            } else if ("PRO_TYPE07001_02_002".equals(wbhaApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_02_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE06001_02_002 보완요청, PRO_TYPE07001_02_005 미선정 _ 사유필요
                if ("PRO_TYPE07001_02_002".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_002");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_005");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                }
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            } else if ("PRO_TYPE07001_02_004".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                // PRO_TYPE07001_02_004 사용자취소
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_004");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }
        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 2) {
            //심사단계
            if ("PRO_TYPE07001_04_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_006 적합 다음스텝 생성
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);

                //PRO_TYPE07001_04_006 선정 다음스텝 생성.
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setRsumeOrd(3);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07003");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_001");

                wbhaCalibrationMapper.insertApplyStep(wbhaApplyDtlDTO);

            } else if ("PRO_TYPE07001_04_003".equals(wbhaApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_04_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_003 보완요청, PRO_TYPE07001_04_005 부적합
                if ("PRO_TYPE07001_04_003".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_003");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_005");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                }

                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }

            wbhaMsEuipmentDTO.setRegId(wbhaApplyDtlDTO.getRegId());
            wbhaMsEuipmentDTO.setRegIp(wbhaApplyDtlDTO.getRegIp());

            //심사단계 데이터처리(PK가 없어 삭제후 재등록)
            wbhaCalibrationMapper.deleteMsEuipment(wbhaMsEuipmentDTO);
            wbhaCalibrationMapper.insertMsEuipment(wbhaMsEuipmentDTO);

        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 3) {
            //증빙단계
            if ("PRO_TYPE07001_06_003".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_003 보완요청
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_003");
                wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_005 부적합
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_005");
                wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_006 적합
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
            }
            wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
        }

        //파일처리
        if (wbhaApplyDtlDTO.getRsumeOrd() == 1 || wbhaApplyDtlDTO.getRsumeOrd() == 3) {
            if (wbhaApplyMstDTO.getFileList() != null) {
                wbhaCalibrationMapper.deleteFileInfo(wbhaApplyDtlDTO);

                //신청파일 넣기
                for (int i = 0; i < wbhaApplyMstDTO.getFileList().size() ; i++) {
                    List<COFileDTO> fileList = new ArrayList();
                    COFileDTO fileDto = new COFileDTO();

                    fileDto.setStatus(wbhaApplyMstDTO.getFileList().get(i).getStatus());
                    fileDto.setWidth(wbhaApplyMstDTO.getFileList().get(i).getWidth());
                    fileDto.setHeight(wbhaApplyMstDTO.getFileList().get(i).getHeight());
                    fileDto.setWebPath(wbhaApplyMstDTO.getFileList().get(i).getWebPath());
                    fileDto.setFieldNm(wbhaApplyMstDTO.getFileList().get(i).getFieldNm());
                    fileDto.setOrgnFileNm(wbhaApplyMstDTO.getFileList().get(i).getOrgnFileNm());
                    fileDto.setFileDsc(wbhaApplyMstDTO.getFileList().get(i).getFileDsc());
                    fileDto.setFileOrd(wbhaApplyMstDTO.getFileList().get(i).getFileOrd());
                    fileList.add(fileDto);

                    if ("addedfile".equals(fileDto.getStatus())) {
                        wbhaApplyDtlDTO.setFileSeq(wbhaApplyMstDTO.getFileList().get(i).getFileSeq());
                    } else {
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    }

                    wbhaApplyDtlDTO.setFileCd(wbhaApplyMstDTO.getFileCdList().get(i));
                    wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
                }
            }
        }
        return  wbhaApplyDtlDTO;
    }

    public WBBATransDTO getTrnsfList(WBBATransDTO wbbaTransDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaTransDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaTransDTO.getListRowSize());

        page.setPageSize(wbbaTransDTO.getPageRowSize());

        wbbaTransDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaTransDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaTransDTO.setList(wbhaCalibrationMapper.getTrnsfList(wbbaTransDTO));
        wbbaTransDTO.setTotalCount(wbhaCalibrationMapper.getTrnsfCount(wbbaTransDTO));

        return wbbaTransDTO;
    }

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wbhaCalibrationMapper.getRsumePbsnCnt(wbhaCalibrationSearchDTO);

        return respCnt;
    }

    /**
     * 최신 회차 상세 조회
     */
    public WBHACalibrationSearchDTO getRoundDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        //양식가져오기
        SMJFormDTO smjFormDTO = new SMJFormDTO();
        smjFormDTO.setTypeCd("BUSINESS02");
        smjFormDTO = smjFormMapper.selectFormDtl(smjFormDTO);

        wbhaCalibrationSearchDTO.setFileSeq(smjFormDTO.getClbtnFileSeq());
        wbhaCalibrationSearchDTO.setFileOrd(smjFormDTO.getClbtnFileOrd());

        //신청가능 매출액가져오기
        WBHAValidDTO wbhaValidDTO = new WBHAValidDTO();
        wbhaValidDTO = wbhaCalibrationMapper.selectExamValid(wbhaCalibrationSearchDTO);
        wbhaCalibrationSearchDTO.setStndSlsPmt(wbhaValidDTO.getStndSlsPmt());

        return wbhaCalibrationSearchDTO;
    }

    /**
     * 최신 회차 상세 조회
     */
    public int getApplyChecked(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

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

                WBHACompanyDTO wbhaCompanyDTO = new WBHACompanyDTO();
                wbhaCalibrationSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wbhaCompanyDTO = wbhaCalibrationMapper.getCompanyInfo(wbhaCalibrationSearchDTO);

                if ("COMPANY01001".equals(wbhaCompanyDTO.getCtgryCd()) || "COMPANY01002".equals(wbhaCompanyDTO.getCtgryCd())) {
                    wbhaCalibrationSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                    //신청시간 년도 구하기
                    Calendar cal = Calendar.getInstance();

                    wbhaCalibrationSearchDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));

                    int cnt = wbhaCalibrationMapper.getApplyCount(wbhaCalibrationSearchDTO);

                    if (cnt > 0) {
                        //신청여부 존재 코드 300
                        rtnCode = 300;
                    } else {
                        //참여조건만족 여부 체크
                        //1. 매출액 비교
                        WBHAValidDTO wbhaValidDTO = new WBHAValidDTO();
                        wbhaValidDTO = wbhaCalibrationMapper.selectExamValid(wbhaCalibrationSearchDTO);

                        cal.add(Calendar.YEAR, -1);
                        int yyyy = cal.get(Calendar.YEAR);

                        if ((yyyy != wbhaCompanyDTO.getSlsYear()) || (wbhaCompanyDTO.getSlsPmt() > wbhaValidDTO.getStndSlsPmt())) {
                            //현재 신청년도와 부품사정보에 기입 된 매출년도의 값이 같지 않을떄 및 매출액 금액이 맞지 않는 경우
                            rtnCode = 400;
                        } else {
                            //컨설팅지도 및 기술지도 체크
                            List<WBHAValidDtlDTO> list = wbhaCalibrationMapper.selectExamValidDtlList(wbhaCalibrationSearchDTO);
                            wbhaCalibrationSearchDTO.setValidDtlDTOList(list);

                            int rtnCnt = wbhaCalibrationMapper.getApplyCompanyCnt(wbhaCalibrationSearchDTO);

                            if (rtnCnt > 0) {
                                //신청가능 코드 200
                                rtnCode = 200;
                                RequestContextHolder.getRequestAttributes().setAttribute("contentAuth", "Y", RequestAttributes.SCOPE_SESSION);
                            } else {
                                //컨설팅 내역없음 코드 450
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
    public int insertApply(WBHAApplyMstDTO wbhaApplyMstDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;
            try {

                COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

                //기존 신청자인지 확인,,,
                WBHACalibrationSearchDTO wbhaCalibrationSearchDTO = new WBHACalibrationSearchDTO();
                wbhaCalibrationSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                //신청시간 년도 구하기
                Calendar cal = Calendar.getInstance();
                wbhaCalibrationSearchDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));

                rtnCnt = wbhaCalibrationMapper.getApplyCount(wbhaCalibrationSearchDTO);

                if (rtnCnt > 0) {
                    //기존 신청이력 존재
                    rtnCnt = 999;
                } else {
                    //신청 프로세스 시작
                    String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
                    String regIp = CONetworkUtil.getMyIPaddress(request);

                    wbhaApplyMstDTO.setBsnCd("BSN08");
                    wbhaApplyMstDTO.setYear(wbhaCalibrationSearchDTO.getYear());
                    WBRoundMstSearchDTO round = wbhaCalibrationMapper.getExisdDtl(wbhaApplyMstDTO);

                    if (round == null) {
                        //해당년도 회차 미 존재시 신규회차 생성
                        int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                        WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                        roundDtl.setEpisdSeq(exipsdSeq);
                        roundDtl.setBsnCd(wbhaApplyMstDTO.getBsnCd());
                        roundDtl.setYear(Integer.valueOf(wbhaApplyMstDTO.getYear()));
                        roundDtl.setEpisd(1); //회차는 1회차 고정
                        roundDtl.setAccsStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                        roundDtl.setAccsEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                        roundDtl.setBsnStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                        roundDtl.setBsnEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                        roundDtl.setExpsYn("Y");
                        roundDtl.setRegId(regId);
                        roundDtl.setRegIp(regIp);

                        wbhaCalibrationMapper.insetRound(roundDtl);

                        wbhaApplyMstDTO.setEpisdSeq(exipsdSeq);
                    } else {
                        wbhaApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
                    }

                    int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
                    //마스터 생성
                    wbhaApplyMstDTO.setAppctnSeq(appctnSeq);
                    wbhaApplyMstDTO.setAppctnBsnmNo(cOUserDetailsDTO.getBsnmNo());
                    wbhaApplyMstDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                    wbhaApplyMstDTO.setRegId(regId);
                    wbhaApplyMstDTO.setRegIp(regIp);

                    rtnCnt = wbhaCalibrationMapper.insertApply(wbhaApplyMstDTO);

                    if (rtnCnt > 0) {
                        //상생신청진행 상세 생성
                        WBHAApplyDtlDTO wbhaApplyDtlDTO = new WBHAApplyDtlDTO();
                        wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                        wbhaApplyDtlDTO.setAppctnSeq(wbhaApplyMstDTO.getAppctnSeq());
                        wbhaApplyDtlDTO.setRsumeOrd(1);
                        wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                        wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                        wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                        wbhaApplyDtlDTO.setRegId(regId);
                        wbhaApplyDtlDTO.setRegIp(regIp);

                        wbhaCalibrationMapper.insertApplyDtl(wbhaApplyDtlDTO);


                        //신청대상장비 생성
                        for (int j = 0; j < wbhaApplyMstDTO.getEuipmentList().size(); j++) {
                            WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                            int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                            wbhaEuipmentDTO.setAppctnSeq(appctnSeq);
                            wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                            wbhaEuipmentDTO.setTchlgOrd(j + 1);
                            wbhaEuipmentDTO.setTchlgNm(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                            wbhaEuipmentDTO.setTchlgCnt(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                            wbhaEuipmentDTO.setRegId(regId);
                            wbhaEuipmentDTO.setRegIp(regIp);

                            wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
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
                                wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                                wbhaApplyDtlDTO.setFileCd(wbhaApplyMstDTO.getFileCdList().get(i));

                                wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
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
    public WBHACalibrationSearchDTO getApplyDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        WBHAApplyMstDTO wbhaApplyMstDTO = new WBHAApplyMstDTO();
        Calendar cal = Calendar.getInstance();
        wbhaApplyMstDTO.setYear(String.valueOf(cal.get(Calendar.YEAR)));
        wbhaApplyMstDTO.setBsnCd("BSN08");
        WBRoundMstSearchDTO round = wbhaCalibrationMapper.getExisdDtl(wbhaApplyMstDTO);

        wbhaCalibrationSearchDTO.setEpisdSeq(round.getEpisdSeq());

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wbhaCalibrationSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
        wbhaCalibrationSearchDTO = wbhaCalibrationMapper.getApplyDtl(wbhaCalibrationSearchDTO);

        return wbhaCalibrationSearchDTO;
    }

    /**
     * 사용자페이지 - 신청자를 수정한다.
     */
    @Transactional
    public int updateInfo(WBHAApplyDtlDTO wbhaApplyDtlDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            //상생신청진행 상태 업데이트
            wbhaApplyDtlDTO.setRegId(modId);
            wbhaApplyDtlDTO.setRegIp(modIp);
            wbhaApplyDtlDTO.setModId(modId);
            wbhaApplyDtlDTO.setModIp(modIp);


            if (wbhaApplyDtlDTO.getRsumeOrd() == 1 || wbhaApplyDtlDTO.getRsumeOrd() == 3) {
                wbhaCalibrationMapper.deleteFileInfo(wbhaApplyDtlDTO);

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
                        wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                        wbhaApplyDtlDTO.setFileCd(wbhaApplyDtlDTO.getFileCdList().get(i));

                        wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
                    }
                }
            }

            rtnCnt = stepUpdateUserProcess(wbhaApplyDtlDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public int stepUpdateUserProcess(WBHAApplyDtlDTO wbhaApplyDtlDTO) throws Exception {
        int rsultCnt = 0;
        if (wbhaApplyDtlDTO.getRsumeOrd() == 1) {

            //신청대상장비 생성시작
            wbhaCalibrationMapper.deleteEuipment(wbhaApplyDtlDTO);

            for(int j=0; j<wbhaApplyDtlDTO.getEuipmentList().size(); j++) {
                WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                wbhaEuipmentDTO.setAppctnSeq(wbhaApplyDtlDTO.getAppctnSeq());
                wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                wbhaEuipmentDTO.setTchlgOrd(j+1);
                wbhaEuipmentDTO.setTchlgNm(wbhaApplyDtlDTO.getEuipmentList().get(j).getTchlgNm());
                wbhaEuipmentDTO.setTchlgCnt(wbhaApplyDtlDTO.getEuipmentList().get(j).getTchlgCnt());
                wbhaEuipmentDTO.setRegId(wbhaApplyDtlDTO.getModId());
                wbhaEuipmentDTO.setRegIp(wbhaApplyDtlDTO.getModIp());

                wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
            }

            if ("PRO_TYPE07001_01_002".equals(wbhaApplyDtlDTO.getAppctnSttsCd())) {
                //보안요청 사용자 -> 보완완료 관리자-> 미확인
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_003");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");

                rsultCnt = wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }

        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 2) {
            WBHAMsEuipmentDTO wbhaMsEuipmentDTO = new WBHAMsEuipmentDTO();
            wbhaMsEuipmentDTO.setRsumeSeq(wbhaApplyDtlDTO.getRsumeSeq());
            wbhaMsEuipmentDTO.setRsumeOrd(wbhaApplyDtlDTO.getRsumeOrd());
            wbhaMsEuipmentDTO.setClbtnExpnsPmt(wbhaApplyDtlDTO.getWbhaMsEuipmentDTO().getClbtnExpnsPmt());
            wbhaMsEuipmentDTO.setRegId(wbhaApplyDtlDTO.getModId());
            wbhaMsEuipmentDTO.setRegIp(wbhaApplyDtlDTO.getModIp());
            wbhaMsEuipmentDTO.setModId(wbhaApplyDtlDTO.getModId());
            wbhaMsEuipmentDTO.setModIp(wbhaApplyDtlDTO.getModIp());


            if ("PRO_TYPE07001_03_001".equals(wbhaApplyDtlDTO.getAppctnSttsCd())) {
                //접수전 사용자 -> 접수완료 관리자-> 미확인
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_002");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_002");

                wbhaCalibrationMapper.insertMsEuipment(wbhaMsEuipmentDTO);
                rsultCnt = wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            } else if ("PRO_TYPE07001_03_003".equals(wbhaApplyDtlDTO.getAppctnSttsCd())) {
                //보완요청 사용자 -> 보완완료 관리자-> 미확인
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_004");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_002");

                wbhaCalibrationMapper.updateMsEuipment(wbhaMsEuipmentDTO);
                rsultCnt = wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }
        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 3) {
            if ("PRO_TYPE07001_05_001".equals(wbhaApplyDtlDTO.getAppctnSttsCd())) {
                //접수전 사용자 -> 접수완료 관리자-> 미확인
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_002");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_002");

                rsultCnt = wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            } else if ("PRO_TYPE07001_05_003".equals(wbhaApplyDtlDTO.getAppctnSttsCd())) {
                //보완요청 사용자 -> 보완완료 관리자-> 미확인
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_004");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_002");

                rsultCnt = wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }
        }

        return rsultCnt;
    }
}
