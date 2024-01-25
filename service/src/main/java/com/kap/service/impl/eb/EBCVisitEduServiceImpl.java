package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduDTO;
import com.kap.core.dto.eb.ebc.EBCVisitEduExcelDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.*;
import com.kap.service.dao.eb.EBCVisitEduMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // 회원관리 DAO
    private final MPAUserMapper mpaUserMapper;

    // 파일 서비스
    private final COFileService cOFileService;

    // 부품사관리 서비스
    public final MPEPartsCompanyService mpePartsCompanyService;

    /* 방문교육 신청 시퀀스 */
    private final EgovIdGnrService edctnVstMstSeqIdgen;

    /* 방문교육 결과 상세 시퀀스 */
    private final EgovIdGnrService edctnVstRsltSeqIdgen;

    /* 방문교육 결과옵션 상세 시퀀스 */
    private final EgovIdGnrService edctnVstOptnSeqIdgen;

    /* 방문교육 신청 이관이력 시퀀스 */
    private final EgovIdGnrService edctnVstTrnsfSeqIdgen;

    /* 회원 수정이력 로그 시퀀스 */
    private final EgovIdGnrService memModSeqIdgen;

    //로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;

    COStringUtil stringUtil;

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
     * 방문교육 신청 이관이력을 조회한다.
     */
    public EBCVisitEduDTO selectTrsfVisitEduList(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduDTO.getListRowSize());

        page.setPageSize(ebcVisitEduDTO.getPageRowSize());

        ebcVisitEduDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduDTO.setTotalCount(ebcVisitEduMapper.selectTrsfVisitEduListCnt(ebcVisitEduDTO));
        ebcVisitEduDTO.setList(ebcVisitEduMapper.selectTrsfVisitEduList(ebcVisitEduDTO));

        return ebcVisitEduDTO;
    }

    /**
     * 방문교육 신청분야 목록을 조회한다.
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

        // 신청자 이관 여부
        String trnsfYn = "N";

        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        ebcVisitEduDTO.setRegId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setRegIp( cOUserDetailsDTO.getLoginIp() );
        ebcVisitEduDTO.setModId( cOUserDetailsDTO.getId() );
        ebcVisitEduDTO.setModIp( cOUserDetailsDTO.getLoginIp() );

        if(!ebcVisitEduDTO.getMemSeq().equals(ebcVisitEduDTO.getBfreMemSeq())) {
            ebcVisitEduDTO.setAftrMemSeq(ebcVisitEduDTO.getMemSeq());
            ebcVisitEduDTO.setMemSeq(ebcVisitEduDTO.getBfreMemSeq());

            trnsfYn = "Y";

            // 이관이력 등록
            ebcVisitEduDTO.setTrnsfSeq(edctnVstTrnsfSeqIdgen.getNextIntegerId());
            ebcVisitEduMapper.insertEdctnTrnsfInfo(ebcVisitEduDTO);
        }

        HashMap<String, Integer> itrdcFileSeqMap = cOFileService.setFileInfo(ebcVisitEduDTO.getItrdcFileList());
        HashMap<String, Integer> lctrFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getLctrFileList());
        HashMap<String, Integer> etcMatlsFileMap = cOFileService.setFileInfo(ebcVisitEduDTO.getEtcMatlsFileList());
        ebcVisitEduDTO.setItrdcFileSeq(itrdcFileSeqMap.get("itrdcFileSeq"));
        ebcVisitEduDTO.setLctrFileSeq(lctrFileMap.get("lctrFileSeq"));
        ebcVisitEduDTO.setEtcMatlsFileSeq(etcMatlsFileMap.get("etcMatlsFileSeq"));

        int respCnt = 0;
        List<EBCVisitEduDTO> resultOpList = ebcVisitEduDTO.getResultOpList();

        // 방문교육 결과정보 insert, 시퀀스 생성
        if(ebcVisitEduDTO.getVstRsltSeq() == null) {
            ebcVisitEduDTO.setVstRsltSeq(edctnVstRsltSeqIdgen.getNextIntegerId());
            ebcVisitEduMapper.insertEdctnVstRslt(ebcVisitEduDTO);
        }

        //신청자 정보 수정
        MPAUserDto mpaUserDto = new MPAUserDto();
        mpaUserDto.setRegId(cOUserDetailsDTO.getId());
        mpaUserDto.setRegIp(cOUserDetailsDTO.getLoginIp());
        mpaUserDto.setId(ebcVisitEduDTO.getId());

        if(trnsfYn.equals("Y")) {
            ebcVisitEduDTO.setMemSeq(ebcVisitEduDTO.getAftrMemSeq());
            ebcVisitEduMapper.updatePartsMemInfo(ebcVisitEduDTO);
            mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
            mpaUserMapper.insertUserDtlHistory(mpaUserDto);
            ebcVisitEduDTO.setMemSeq(ebcVisitEduDTO.getBfreMemSeq());
        } else {
            ebcVisitEduMapper.updatePartsMemInfo(ebcVisitEduDTO);
            mpaUserDto.setModSeq(memModSeqIdgen.getNextIntegerId());
            mpaUserMapper.insertUserDtlHistory(mpaUserDto);
        }

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
            mpePartsCompanyDTO.setPay5StarYear(ebcVisitEduDTO.getPay5StarYear());
            mpePartsCompanyDTO.setTchlg5StarCd(ebcVisitEduDTO.getTchlg5StarCd());
            mpePartsCompanyDTO.setTchlg5StarYear(ebcVisitEduDTO.getTchlg5StarYear());

        } else if (ebcVisitEduDTO.getCtgryCd().equals("COMPANY01002")) {
            mpePartsCompanyDTO.setSqInfoList1(ebcVisitEduDTO.getSqInfoList1());
            mpePartsCompanyDTO.setSqInfoList2(ebcVisitEduDTO.getSqInfoList2());
            mpePartsCompanyDTO.setSqInfoList3(ebcVisitEduDTO.getSqInfoList3());
        }

        mpePartsCompanyDTO.setRegId(cOUserDetailsDTO.getId());
        mpePartsCompanyDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        mpePartsCompanyService.updatePartsCompany(mpePartsCompanyDTO);

        //강사 삭제
        ebcVisitEduMapper.deleteIsttrRel(ebcVisitEduDTO);
        //신청분야 상세 삭제
        ebcVisitEduMapper.deleteAppctnType(ebcVisitEduDTO);
        //교육실적 삭제
        ebcVisitEduMapper.deleteResultOp(ebcVisitEduDTO);

        //신청내용 수정
        ebcVisitEduMapper.updateEdctnVst(ebcVisitEduDTO);
        ebcVisitEduMapper.updateEdctnVstRslt(ebcVisitEduDTO);

        if(trnsfYn.equals("Y")) {
            ebcVisitEduDTO.setMemSeq(ebcVisitEduDTO.getAftrMemSeq());
        }

        ebcVisitEduMapper.insertIsttrRel(ebcVisitEduDTO);
        ebcVisitEduMapper.insertAppctnType(ebcVisitEduDTO);
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
    public void excelDownload(EBCVisitEduExcelDTO ebcVisitEduExcelDTO, HttpServletResponse response) throws Exception{

        XSSFWorkbook workbook = new XSSFWorkbook();

        // 폰트 설정
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);

        XSSFCellStyle style_header = workbook.createCellStyle();
        XSSFCellStyle style_body = workbook.createCellStyle();
        style_header.setFont(font);
        style_body.setFont(font);

        Sheet sheet = workbook.createSheet();

        Row row = null;
        Cell cell = null;
        int rowNum = 3;

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


        //0: 교육상태
        //1: 최종수정자
        //2: 최종수정일시
        //3: 신청일시
        //4~15 : 신청정보
        //16~28 : 기본정보
        //29~66 : 교육실적

        //셀 병합 관련해서 만든 리스트
        List<CellRangeAddress> cellList = new ArrayList<>();

        //헤더 1층 신청정보, 기본정보, 교육실적
        row = sheet.createRow(0);


        //헤더 2층 부품사 정보, 신청내용, 신청자 정보, 과정명, 학습방식, 교육일, 교육기간, 교육장소, 정원(명), 강사1, 강사2, 강사3, 강사4, 강사5, 강사6,
        // 회사별 수료인원(명), 분야별 수료인원(명), 직급별 수료 인원(명), 출석/평가, 만족도, 교육 시간
        cell = row.createCell(0); cell.setCellValue("교육상태"); cell.setCellStyle(style_header);
        cell = row.createCell(1); cell.setCellValue("최종수정자"); cell.setCellStyle(style_header);
        cell = row.createCell(2); cell.setCellValue("최종수정일시"); cell.setCellStyle(style_header);
        cell = row.createCell(3); cell.setCellValue("신청일시"); cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("신청정보");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("기본정보");
        cell.setCellStyle(style_header);

        cell = row.createCell(29);
        cell.setCellValue("교육실적");
        cell.setCellStyle(style_header);

        // 셀 병합
        CellRangeAddress row0cols1 = new CellRangeAddress(0, 2, 0, 0);//교육상태
        CellRangeAddress row0cols2 = new CellRangeAddress(0, 2, 1, 1);//최종수정자
        CellRangeAddress row0cols3 = new CellRangeAddress(0, 2, 2, 2);//최종수정일시
        CellRangeAddress row0cols4 = new CellRangeAddress(0, 2, 3, 3);//신청일시
        CellRangeAddress row1cols4 = new CellRangeAddress(0, 0, 4, 15);//신청정보 병합 4~15
        CellRangeAddress row1cols16 = new CellRangeAddress(0, 0, 16, 28);//기본정보 병합 병합 16~28
        CellRangeAddress row1cols29 = new CellRangeAddress(0, 0, 29, 66);//교육실적 병합 29~66

        sheet.addMergedRegion(row0cols1);
        sheet.addMergedRegion(row0cols2);
        sheet.addMergedRegion(row0cols3);
        sheet.addMergedRegion(row0cols4);
        sheet.addMergedRegion(row1cols4);
        sheet.addMergedRegion(row1cols16);
        sheet.addMergedRegion(row1cols29);

        cellList.add(row0cols1);
        cellList.add(row0cols2);
        cellList.add(row0cols3);
        cellList.add(row0cols4);
        cellList.add(row1cols4);
        cellList.add(row1cols16);
        cellList.add(row1cols29);

        row = sheet.createRow(1);
        cell = row.createCell(4); cell.setCellValue("부품사 정보"); cell.setCellStyle(style_header);
        cell = row.createCell(8); cell.setCellValue("신청내용"); cell.setCellStyle(style_header);
        cell = row.createCell(13); cell.setCellValue("신청자 정보"); cell.setCellStyle(style_header);
        cell = row.createCell(16); cell.setCellValue("과정명"); cell.setCellStyle(style_header);
        cell = row.createCell(17); cell.setCellValue("학습방식"); cell.setCellStyle(style_header);
        cell = row.createCell(18); cell.setCellValue("년도"); cell.setCellStyle(style_header);
        cell = row.createCell(19); cell.setCellValue("교육일"); cell.setCellStyle(style_header);
        cell = row.createCell(20); cell.setCellValue("교육기간"); cell.setCellStyle(style_header);
        cell = row.createCell(21); cell.setCellValue("교육장소"); cell.setCellStyle(style_header);
        cell = row.createCell(22); cell.setCellValue("정원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(23); cell.setCellValue("강사1"); cell.setCellStyle(style_header);
        cell = row.createCell(24); cell.setCellValue("강사2"); cell.setCellStyle(style_header);
        cell = row.createCell(25); cell.setCellValue("강사3"); cell.setCellStyle(style_header);
        cell = row.createCell(26); cell.setCellValue("강사4"); cell.setCellStyle(style_header);
        cell = row.createCell(27); cell.setCellValue("강사5"); cell.setCellStyle(style_header);
        cell = row.createCell(28); cell.setCellValue("강사6"); cell.setCellStyle(style_header);
        cell = row.createCell(29); cell.setCellValue("개요"); cell.setCellStyle(style_header);
        cell = row.createCell(34); cell.setCellValue("회사별 수료인원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(38); cell.setCellValue("분야별 수료인원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(47); cell.setCellValue("직급별 수료 인원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(53); cell.setCellValue("출석/평가"); cell.setCellStyle(style_header);
        cell = row.createCell(55); cell.setCellValue("만족도"); cell.setCellStyle(style_header);
        cell = row.createCell(61); cell.setCellValue("교육 시간"); cell.setCellStyle(style_header);

        //2층 기본정보 시작
        CellRangeAddress row2cols4 = new CellRangeAddress(1, 1, 4, 7); //부품사 정보
        CellRangeAddress row2cols8 = new CellRangeAddress(1, 1, 8, 12); //신청내용
        CellRangeAddress row2cols13 = new CellRangeAddress(1, 1, 13, 15); //신청자 정보

        sheet.addMergedRegion(row2cols4); sheet.addMergedRegion(row2cols8); sheet.addMergedRegion(row2cols13);

        CellRangeAddress row2cols16 = new CellRangeAddress(1, 2, 16, 16); //과정명
        CellRangeAddress row2cols17 = new CellRangeAddress(1, 2, 17, 17); //학습방식
        CellRangeAddress row2cols18 = new CellRangeAddress(1, 2, 18, 18); //년도
        CellRangeAddress row2cols19 = new CellRangeAddress(1, 2, 19, 19); //교육일
        CellRangeAddress row2cols20 = new CellRangeAddress(1, 2, 20, 20); //교육기간
        CellRangeAddress row2cols21 = new CellRangeAddress(1, 2, 21, 21); //교육장소
        CellRangeAddress row2cols22 = new CellRangeAddress(1, 2, 22, 22); //정원(명)
        CellRangeAddress row2cols23 = new CellRangeAddress(1, 2, 23, 23); //강사1
        CellRangeAddress row2cols24 = new CellRangeAddress(1, 2, 24, 24); //강사2
        CellRangeAddress row2cols25 = new CellRangeAddress(1, 2, 25, 25); //강사3
        CellRangeAddress row2cols26 = new CellRangeAddress(1, 2, 26, 26); //강사4
        CellRangeAddress row2cols27 = new CellRangeAddress(1, 2, 27, 27); //강사5
        CellRangeAddress row2cols28 = new CellRangeAddress(1, 2, 28, 28); //강사6

        sheet.addMergedRegion(row2cols16); sheet.addMergedRegion(row2cols17); sheet.addMergedRegion(row2cols18);
        sheet.addMergedRegion(row2cols19); sheet.addMergedRegion(row2cols20); sheet.addMergedRegion(row2cols21);
        sheet.addMergedRegion(row2cols22); sheet.addMergedRegion(row2cols23); sheet.addMergedRegion(row2cols24);
        sheet.addMergedRegion(row2cols25); sheet.addMergedRegion(row2cols26); sheet.addMergedRegion(row2cols27);
        sheet.addMergedRegion(row2cols28);

        CellRangeAddress row2cols29 = new CellRangeAddress(1, 1, 29, 33); //개요
        CellRangeAddress row2cols34 = new CellRangeAddress(1, 1, 34, 37); //회사별 수료인원(명)
        CellRangeAddress row2cols38 = new CellRangeAddress(1, 1, 38, 46); //분야별 수료인원(명)
        CellRangeAddress row2cols47 = new CellRangeAddress(1, 1, 47, 52); //직급별 수료 인원(명)
        CellRangeAddress row2cols53 = new CellRangeAddress(1, 1, 53, 54); //출석/평가
        CellRangeAddress row2cols55 = new CellRangeAddress(1, 1, 55, 60); //만족도
        CellRangeAddress row2cols61 = new CellRangeAddress(1, 1, 61, 66); //교육 시간

        sheet.addMergedRegion(row2cols29); sheet.addMergedRegion(row2cols34); sheet.addMergedRegion(row2cols38);
        sheet.addMergedRegion(row2cols47); sheet.addMergedRegion(row2cols53); sheet.addMergedRegion(row2cols55);
        sheet.addMergedRegion(row2cols61);

        cellList.add(row2cols16); cellList.add(row2cols17); cellList.add(row2cols18); cellList.add(row2cols19);
        cellList.add(row2cols20); cellList.add(row2cols21); cellList.add(row2cols22); cellList.add(row2cols23);
        cellList.add(row2cols24); cellList.add(row2cols25); cellList.add(row2cols26); cellList.add(row2cols27);
        cellList.add(row2cols28); cellList.add(row2cols29); cellList.add(row2cols34); cellList.add(row2cols38);
        cellList.add(row2cols47); cellList.add(row2cols53); cellList.add(row2cols55); cellList.add(row2cols61);


        row = sheet.createRow(2);

        cell = row.createCell(4); cell.setCellValue("부품사명"); cell.setCellStyle(style_header);
        cell = row.createCell(5); cell.setCellValue("구분"); cell.setCellStyle(style_header);
        cell = row.createCell(6); cell.setCellValue("사업자등록번호"); cell.setCellStyle(style_header);
        cell = row.createCell(7); cell.setCellValue("규모"); cell.setCellStyle(style_header);

        cell = row.createCell(8); cell.setCellValue("신청분야"); cell.setCellStyle(style_header);
        cell = row.createCell(9); cell.setCellValue("교육희망일"); cell.setCellStyle(style_header);
        cell = row.createCell(10); cell.setCellValue("교육장소"); cell.setCellStyle(style_header);
        cell = row.createCell(11); cell.setCellValue("교육인원"); cell.setCellStyle(style_header);
        cell = row.createCell(12); cell.setCellValue("교육시간"); cell.setCellStyle(style_header);

        cell = row.createCell(13); cell.setCellValue("이름(아이디)"); cell.setCellStyle(style_header);
        cell = row.createCell(14); cell.setCellValue("휴대폰번호"); cell.setCellStyle(style_header);
        cell = row.createCell(15); cell.setCellValue("이메일"); cell.setCellStyle(style_header);


        cell = row.createCell(29); cell.setCellValue("실적마감여부"); cell.setCellStyle(style_header);
        cell = row.createCell(30); cell.setCellValue("확정주제"); cell.setCellStyle(style_header);
        cell = row.createCell(31); cell.setCellValue("신청인원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(32); cell.setCellValue("수료인원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(33); cell.setCellValue("참석율(%)"); cell.setCellStyle(style_header);

        cell = row.createCell(34); cell.setCellValue("완성차(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(35); cell.setCellValue("1차사(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(36); cell.setCellValue("2차사(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(37); cell.setCellValue("기타(명)"); cell.setCellStyle(style_header);

        cell = row.createCell(38); cell.setCellValue("품질(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(39); cell.setCellValue("R&D(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(40); cell.setCellValue("생산(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(41); cell.setCellValue("구매(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(42); cell.setCellValue("경영지원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(43); cell.setCellValue("업체평가(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(44); cell.setCellValue("안전(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(45); cell.setCellValue("ESG(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(46); cell.setCellValue("기타(명)"); cell.setCellStyle(style_header);

        cell = row.createCell(47); cell.setCellValue("대표(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(48); cell.setCellValue("임원(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(49); cell.setCellValue("부장(팀장)(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(50); cell.setCellValue("과장/차장(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(51); cell.setCellValue("사원/대리(명)"); cell.setCellStyle(style_header);
        cell = row.createCell(52); cell.setCellValue("조장/반장(명)"); cell.setCellStyle(style_header);

        cell = row.createCell(53); cell.setCellValue("출석률(%)"); cell.setCellStyle(style_header);
        cell = row.createCell(54); cell.setCellValue("평가점수(점)"); cell.setCellStyle(style_header);

        cell = row.createCell(55); cell.setCellValue("종합 평균"); cell.setCellStyle(style_header);
        cell = row.createCell(56); cell.setCellValue("전체(공통)"); cell.setCellStyle(style_header);
        cell = row.createCell(57); cell.setCellValue("교육환경"); cell.setCellStyle(style_header);
        cell = row.createCell(58); cell.setCellValue("교육지원"); cell.setCellStyle(style_header);
        cell = row.createCell(59); cell.setCellValue("교육내용"); cell.setCellStyle(style_header);
        cell = row.createCell(60); cell.setCellValue("강사"); cell.setCellStyle(style_header);

        cell = row.createCell(61); cell.setCellValue("강사1(시간)"); cell.setCellStyle(style_header);
        cell = row.createCell(62); cell.setCellValue("강사2(시간)"); cell.setCellStyle(style_header);
        cell = row.createCell(63); cell.setCellValue("강사3(시간)"); cell.setCellStyle(style_header);
        cell = row.createCell(64); cell.setCellValue("강사4(시간)"); cell.setCellStyle(style_header);
        cell = row.createCell(65); cell.setCellValue("강사5(시간)"); cell.setCellStyle(style_header);
        cell = row.createCell(66); cell.setCellValue("강사6(시간)"); cell.setCellStyle(style_header);

        // 병합된 영역의 각 셀에 스타일을 적용
        for(CellRangeAddress call : cellList){
            for (int i = call.getFirstRow(); i <= call.getLastRow(); i++) {
                Row tempRow = sheet.getRow(i);
                for (int colNum = call.getFirstColumn(); colNum <= call.getLastColumn(); colNum++) {
                    Cell tempCel = tempRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    tempCel.setCellStyle(style_header);
                }
            }
        }

        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Body
        List<EBCVisitEduExcelDTO> list = ebcVisitEduExcelDTO.getList();
        for(EBCVisitEduExcelDTO dto: list){
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0); cell.setCellValue(dto.getEdctnSttsName()); cell.setCellStyle(style_body);//교육상태

            if (dto.getModName() == null) {
                cell = row.createCell(1); cell.setCellValue("-"); cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(1); cell.setCellValue(dto.getModName() + "(" + dto.getModId() + ")"); cell.setCellStyle(style_body);//최종수정자
            }

            if (dto.getModDtm() == null) {
                cell = row.createCell(2); cell.setCellValue("-"); cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(2); cell.setCellValue(dto.getModDtm().substring(0, dto.getModDtm().lastIndexOf(":"))); cell.setCellStyle(style_body);//최종수정일시
            }

            cell = row.createCell(3); cell.setCellValue(dto.getRegDtm().substring(0, dto.getRegDtm().lastIndexOf(":"))); cell.setCellStyle(style_body);//신청일시
            cell = row.createCell(4); cell.setCellValue(dto.getCmpnNm()); cell.setCellStyle(style_body);//부품사명
            cell = row.createCell(5); cell.setCellValue(dto.getCtgryName()); cell.setCellStyle(style_body);//구분

            String bsnmNo = dto.getAppctnBsnmNo();
            StringBuilder newBsnmNo = new StringBuilder();

            // '-' 추가
            int length = bsnmNo.length();
            for (int j = 0; j < length; j++) {
                newBsnmNo.append(bsnmNo.charAt(j));
                if (j == 2 || j == 4) {
                    newBsnmNo.append("-");
                }
            }
            cell = row.createCell(6); cell.setCellValue(newBsnmNo.toString()); cell.setCellStyle(style_body);//사업자등록번호
            cell = row.createCell(7); cell.setCellValue(dto.getSizeName()); cell.setCellStyle(style_body);//규모
            cell = row.createCell(8); cell.setCellValue(dto.getAppctnFldName()); cell.setCellStyle(style_body);//신청분야
            cell = row.createCell(9); cell.setCellValue(dto.getHopeDt()); cell.setCellStyle(style_body);//교육희망일
            cell = row.createCell(10); cell.setCellValue(dto.getPlaceBscAddr() + dto.getPlaceDtlAddr()); cell.setCellStyle(style_body);//교육장소
            cell = row.createCell(11); cell.setCellValue(dto.getPtcptCnt()); cell.setCellStyle(style_body);//교육인원
            cell = row.createCell(12); cell.setCellValue(dto.getPtcptHhNum()); cell.setCellStyle(style_body);//교육시간
            cell = row.createCell(13); cell.setCellValue(dto.getName() + "(" + dto.getId() + ")"); cell.setCellStyle(style_body);//이름(아이디)
            cell = row.createCell(14); cell.setCellValue(dto.getHpNo()); cell.setCellStyle(style_body);//휴대폰번호
            cell = row.createCell(15); cell.setCellValue(dto.getEmail()); cell.setCellStyle(style_body);//이메일
            cell = row.createCell(16); cell.setCellValue("방문교육"); cell.setCellStyle(style_body);//과정명
            cell = row.createCell(17); cell.setCellValue("집체교육"); cell.setCellStyle(style_body);//학습방식
            cell = row.createCell(18); cell.setCellValue(dto.getEdctnYear()); cell.setCellStyle(style_body);//년도

            if(dto.getEdctnStrtDtm() == null) {
                cell = row.createCell(19); cell.setCellValue("-"); cell.setCellStyle(style_body);
                cell = row.createCell(20); cell.setCellValue("-"); cell.setCellStyle(style_body);
            } else {

                String originStrtDtm = dto.getEdctnStrtDtm();
                String originEndDtm = dto.getEdctnEndDtm();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date strtDtm = dateFormat.parse(originStrtDtm);
                Date endDtm = dateFormat.parse(originEndDtm);

                long timeDiff = endDtm.getTime() - strtDtm.getTime();
                long daysDiff = timeDiff / (1000 * 60 * 60 * 24);

                cell = row.createCell(19); cell.setCellValue(daysDiff + 1); cell.setCellStyle(style_body);//교육일
                cell = row.createCell(20); cell.setCellValue(dto.getEdctnStrtDtm().substring(0, dto.getEdctnStrtDtm().lastIndexOf(":"))
                        + "~" + dto.getEdctnEndDtm().substring(0, dto.getEdctnEndDtm().lastIndexOf(":"))); cell.setCellStyle(style_body);//교육기간
            }

            cell = row.createCell(21); cell.setCellValue(dto.getEdctnPlace()); cell.setCellStyle(style_body);//교육장소
            cell = row.createCell(22); cell.setCellValue("-"); cell.setCellStyle(style_body);//정원(명)

            ebcVisitEduExcelDTO.setVstSeq(dto.getVstSeq());
            EBCVisitEduExcelDTO isttrDto = selectIsttrExcelList(ebcVisitEduExcelDTO);
            List<EBCVisitEduExcelDTO> isttrList = isttrDto.getList();
            String isttrNm1 = "";
            String isttrNm2 = "";
            String isttrNm3 = "";
            String isttrNm4 = "";
            String isttrNm5 = "";
            String isttrNm6 = "";

            //강사 반복문 시작
            for(EBCVisitEduExcelDTO excelIsttrDto : isttrList){
                isttrNm1 = COStringUtil.nvl(excelIsttrDto.getIsttr1(), "-");
                isttrNm2 = COStringUtil.nvl(excelIsttrDto.getIsttr2(), "-");
                isttrNm3 = COStringUtil.nvl(excelIsttrDto.getIsttr3(), "-");
                isttrNm4 = COStringUtil.nvl(excelIsttrDto.getIsttr4(), "-");
                isttrNm5 = COStringUtil.nvl(excelIsttrDto.getIsttr5(), "-");
                isttrNm6 = COStringUtil.nvl(excelIsttrDto.getIsttr6(), "-");

            }
            cell = row.createCell(23); cell.setCellValue(isttrNm1); cell.setCellStyle(style_body);//강사1
            cell = row.createCell(24); cell.setCellValue(isttrNm2); cell.setCellStyle(style_body);//강사2
            cell = row.createCell(25); cell.setCellValue(isttrNm3); cell.setCellStyle(style_body);//강사3
            cell = row.createCell(26); cell.setCellValue(isttrNm4); cell.setCellStyle(style_body);//강사4
            cell = row.createCell(27); cell.setCellValue(isttrNm5); cell.setCellStyle(style_body);//강사5
            cell = row.createCell(28); cell.setCellValue(isttrNm6); cell.setCellStyle(style_body);//강사6

            cell = row.createCell(29); cell.setCellValue(dto.getRsltEndYnNm()); cell.setCellStyle(style_body);//실적마감여부
            cell = row.createCell(30); cell.setCellValue(dto.getCnfrmdTheme()); cell.setCellStyle(style_body);//확정주제
            cell = row.createCell(31); cell.setCellValue(dto.getPtcptCnt()); cell.setCellStyle(style_body);//신청인원(명)
            cell = row.createCell(32); cell.setCellValue(dto.getCmptnCnt()); cell.setCellStyle(style_body);//수료인원(명)

            if(dto.getPtcptRate() == null) {
                cell = row.createCell(33); cell.setCellValue("-"); cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(33); cell.setCellValue(dto.getPtcptRate() * 100); cell.setCellStyle(style_body);//참석률(%)
            }

            cell = row.createCell(34); cell.setCellValue(dto.getEducnt1()); cell.setCellStyle(style_body);//완성차(명)
            cell = row.createCell(35); cell.setCellValue(dto.getEducnt2()); cell.setCellStyle(style_body);//1차사(명)
            cell = row.createCell(36); cell.setCellValue(dto.getEducnt3()); cell.setCellStyle(style_body);//2차사(명)
            cell = row.createCell(37); cell.setCellValue(dto.getEducnt4()); cell.setCellStyle(style_body);//기타(명)

            cell = row.createCell(38); cell.setCellValue(dto.getFieldcnt1()); cell.setCellStyle(style_body);//품질(명)
            cell = row.createCell(39); cell.setCellValue(dto.getFieldcnt2()); cell.setCellStyle(style_body);//R&D(명)
            cell = row.createCell(40); cell.setCellValue(dto.getFieldcnt3()); cell.setCellStyle(style_body);//생산(명)
            cell = row.createCell(41); cell.setCellValue(dto.getFieldcnt4()); cell.setCellStyle(style_body);//구매(명)
            cell = row.createCell(42); cell.setCellValue(dto.getFieldcnt5()); cell.setCellStyle(style_body);//경영지원(명)
            cell = row.createCell(43); cell.setCellValue(dto.getFieldcnt6()); cell.setCellStyle(style_body);//업체평가(명)
            cell = row.createCell(44); cell.setCellValue(dto.getFieldcnt7()); cell.setCellStyle(style_body);//안전(명)
            cell = row.createCell(45); cell.setCellValue(dto.getFieldcnt8()); cell.setCellStyle(style_body);//ESG(명)
            cell = row.createCell(46); cell.setCellValue(dto.getFieldcnt9()); cell.setCellStyle(style_body);//기타(명)

            cell = row.createCell(47); cell.setCellValue(dto.getPositioncnt1()); cell.setCellStyle(style_body);//대표(명)
            cell = row.createCell(48); cell.setCellValue(dto.getPositioncnt2()); cell.setCellStyle(style_body);//임원(명)
            cell = row.createCell(49); cell.setCellValue(dto.getPositioncnt3()); cell.setCellStyle(style_body);//부장(팀장)(명)
            cell = row.createCell(50); cell.setCellValue(dto.getPositioncnt4()); cell.setCellStyle(style_body);//과장/차장(명)
            cell = row.createCell(51); cell.setCellValue(dto.getPositioncnt5()); cell.setCellStyle(style_body);//사원/대리(명)
            cell = row.createCell(52); cell.setCellValue(dto.getPositioncnt6()); cell.setCellStyle(style_body);//조장/반장(명)
            cell = row.createCell(53); cell.setCellValue(dto.getAttendcnt1()); cell.setCellStyle(style_body);//출석률(%)
            cell = row.createCell(54); cell.setCellValue(dto.getAttendcnt2()); cell.setCellStyle(style_body);//평가점수(점)

            cell = row.createCell(55); cell.setCellValue(dto.getSaticnt1()); cell.setCellStyle(style_body);//종합 평균
            cell = row.createCell(56); cell.setCellValue(dto.getSaticnt2()); cell.setCellStyle(style_body);//전체(공통)
            cell = row.createCell(57); cell.setCellValue(dto.getSaticnt3()); cell.setCellStyle(style_body);//교육환경
            cell = row.createCell(58); cell.setCellValue(dto.getSaticnt4()); cell.setCellStyle(style_body);//교육지원
            cell = row.createCell(59); cell.setCellValue(dto.getSaticnt5()); cell.setCellStyle(style_body);//교육내용
            cell = row.createCell(60); cell.setCellValue(dto.getSaticnt1()); cell.setCellStyle(style_body);//강사
            cell = row.createCell(61); cell.setCellValue(dto.getEdutimecnt1()); cell.setCellStyle(style_body);//강사1(시간)
            cell = row.createCell(62); cell.setCellValue(dto.getEdutimecnt2()); cell.setCellStyle(style_body);//강사2(시간)
            cell = row.createCell(63); cell.setCellValue(dto.getEdutimecnt3()); cell.setCellStyle(style_body);//강사3(시간)
            cell = row.createCell(64); cell.setCellValue(dto.getEdutimecnt4()); cell.setCellStyle(style_body);//강사4(시간)
            cell = row.createCell(65); cell.setCellValue(dto.getEdutimecnt5()); cell.setCellStyle(style_body);//강사5(시간)
            cell = row.createCell(66); cell.setCellValue(dto.getEdutimecnt6()); cell.setCellStyle(style_body);//강사6(시간)

            // 열 크기 조정
            sheet.setColumnWidth(0, 2000); sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 4500); sheet.setColumnWidth(3, 4500);
            sheet.setColumnWidth(4, 8000); sheet.setColumnWidth(5, 1800);
            sheet.setColumnWidth(6, 5000); sheet.setColumnWidth(7, 2000);
            sheet.setColumnWidth(8, 2000); sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 10000); sheet.setColumnWidth(11, 1800);
            sheet.setColumnWidth(12, 1800); sheet.setColumnWidth(13, 8000);
            sheet.setColumnWidth(14, 5000); sheet.setColumnWidth(15, 8000);
            sheet.setColumnWidth(16, 2000); sheet.setColumnWidth(17, 2000);
            sheet.setColumnWidth(18, 2000); sheet.setColumnWidth(19, 1800);
            sheet.setColumnWidth(20, 8000); sheet.setColumnWidth(21, 6000);
            sheet.setColumnWidth(22, 1800); sheet.setColumnWidth(23, 2000);
            sheet.setColumnWidth(24, 2000); sheet.setColumnWidth(25, 2000);
            sheet.setColumnWidth(26, 2000); sheet.setColumnWidth(27, 2000);
            sheet.setColumnWidth(28, 2000); sheet.setColumnWidth(29, 2500);
            sheet.setColumnWidth(30, 6000); sheet.setColumnWidth(31, 2200);
            sheet.setColumnWidth(32, 2200); sheet.setColumnWidth(33, 2200);
            sheet.setColumnWidth(34, 2200); sheet.setColumnWidth(35, 2200);
            sheet.setColumnWidth(36, 2200); sheet.setColumnWidth(37, 2200);
            sheet.setColumnWidth(38, 2200); sheet.setColumnWidth(39, 2200);
            sheet.setColumnWidth(40, 2200); sheet.setColumnWidth(41, 2200);
            sheet.setColumnWidth(42, 2200); sheet.setColumnWidth(43, 2200);
            sheet.setColumnWidth(44, 2200); sheet.setColumnWidth(45, 2200);
            sheet.setColumnWidth(46, 2200); sheet.setColumnWidth(47, 2200);
            sheet.setColumnWidth(48, 2200); sheet.setColumnWidth(49, 2400);
            sheet.setColumnWidth(50, 2400); sheet.setColumnWidth(51, 2400);
            sheet.setColumnWidth(52, 2400); sheet.setColumnWidth(53, 2200);
            sheet.setColumnWidth(54, 2200); sheet.setColumnWidth(55, 2200);
            sheet.setColumnWidth(56, 2200); sheet.setColumnWidth(57, 2200);
            sheet.setColumnWidth(58, 2200); sheet.setColumnWidth(59, 2200);
            sheet.setColumnWidth(60, 2200); sheet.setColumnWidth(61, 2200);
            sheet.setColumnWidth(62, 2200); sheet.setColumnWidth(63, 2200);
            sheet.setColumnWidth(64, 2200); sheet.setColumnWidth(65, 2200);
            sheet.setColumnWidth(66, 2200);



        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("KAP_방문교육_관리_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("교육사업 관리 > 방문교육 관리");
        pCoSystemLogDTO.setSrvcNm("com.kap.service.impl.eb.EBCVisitEduServiceImpl");
        pCoSystemLogDTO.setFncNm("selectPartsCompanyList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(ebcVisitEduExcelDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 엑셀 목록을 조회한다.
     */
    public EBCVisitEduExcelDTO selectExcelList(EBCVisitEduExcelDTO ebcVisitEduExcelDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduExcelDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduExcelDTO.getListRowSize());

        page.setPageSize(ebcVisitEduExcelDTO.getPageRowSize());

        ebcVisitEduExcelDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduExcelDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduExcelDTO.setList(ebcVisitEduMapper.selectExcelList(ebcVisitEduExcelDTO));

        return ebcVisitEduExcelDTO;
    }

    /**
     * 방문교육 엑셀 강사 목록을 조회
     */
    public EBCVisitEduExcelDTO selectIsttrExcelList(EBCVisitEduExcelDTO ebcVisitEduExcelDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(ebcVisitEduExcelDTO.getPageIndex());
        page.setRecordCountPerPage(ebcVisitEduExcelDTO.getListRowSize());

        page.setPageSize(ebcVisitEduExcelDTO.getPageRowSize());

        ebcVisitEduExcelDTO.setFirstIndex(page.getFirstRecordIndex());
        ebcVisitEduExcelDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        ebcVisitEduExcelDTO.setList(ebcVisitEduMapper.selectIsttrExcelList(ebcVisitEduExcelDTO));

        return ebcVisitEduExcelDTO;
    }

    /**
     * 방문교육 신청정보 등록
     */
    public int applyVisitEduInfo(EBCVisitEduDTO ebcVisitEduDTO, MultipartHttpServletRequest multiRequest) throws Exception {
        ebcVisitEduDTO.setVstSeq(edctnVstMstSeqIdgen.getNextIntegerId());
        //신청분야 상세 insert
        ebcVisitEduMapper.insertAppctnType(ebcVisitEduDTO);

        // 회사소개서 파일 넣기
        List<COFileDTO> rtnList = null;
        Map<String, MultipartFile> files = multiRequest.getFileMap();
        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;

        rtnList = cOFileUtil.parseFileInf(files, "", 1, "", "file", 0);

        List<COFileDTO> fileList = new ArrayList();
        rtnList.get(0).setStatus("success");
        rtnList.get(0).setFieldNm("fileSeq");
        fileList.add(rtnList.get(0));

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
        ebcVisitEduDTO.setItrdcFileSeq(fileSeqMap.get("fileSeq"));
        return ebcVisitEduMapper.applyVisitEduInfo(ebcVisitEduDTO);

    }

    /**
     * 방문교육 신청일시 값 조회
     */
    public EBCVisitEduDTO selectVisitEduApplyRegDtm(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectVisitEduApplyRegDtm(ebcVisitEduDTO);
    }

    /**
     * 사용자 방문교육 신청자정보를 조회
     */
    public EBCVisitEduDTO selectApplicantInfo(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        return ebcVisitEduMapper.selectApplicantInfo(ebcVisitEduDTO);
    }

    /**
     * 신청한 방문교육을 교육취소로 변경
     */
    @Transactional
    public void updateVisitEduApplyCancel(EBCVisitEduDTO ebcVisitEduDTO) throws Exception {
        ebcVisitEduMapper.updateVisitEduApplyCancel(ebcVisitEduDTO);
    }
}