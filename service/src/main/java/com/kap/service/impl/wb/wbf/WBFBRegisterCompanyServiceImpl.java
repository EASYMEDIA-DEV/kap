package com.kap.service.impl.wb.wbf;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.*;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRsumeTaskDtlDTO;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COCodeService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFBRegisterCompanyService;
import com.kap.service.dao.wb.wbf.WBFBRegisterCompanyMapper;
import com.kap.service.impl.wb.wbb.WBBBCompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * 스마트공장구축 > 신청업체관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBFBRegisterCompanyServiceImpl.java
 * @Description		: 신청업체관리를 위한 ServiceImpl
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		김동현				   최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WBFBRegisterCompanyServiceImpl implements WBFBRegisterCompanyService {

    //파일 업로드 위치
    @Value("${app.file.upload-path}")
    private String fileUploadPath;

    /* Mapper */
    private final WBFBRegisterCompanyMapper wBFBRegisterCompanyMapper;

    //파일 서비스
    private final COFileService cOFileService;
    private final COFileUtil cOFileUtil;
    private final WBBBCompanyServiceImpl wbbbCompanyService;

    /* 회사 상세 시퀀스 - SQ */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /* 상생 신청 마스터 시퀀스 */
    private final EgovIdGnrService cxAppctnMstSeqIdgen;

    /* 상생 신청 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;

    /* 상생 신청 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnSpprtDtlIdgen;

    /* 상생 신청 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;

    /* 공통 코드 service DI */
    private final COCodeService cOCodeService;

    /**
     * 신청 단계
     * 신청-사업계획-최초점검-원가계산-협약-중간점검-완료보고-최종점검
     */
    private final static LinkedList<String> appctnList = new LinkedList<>(Arrays.asList(
            "PRO_TYPE02001", "PRO_TYPE02002", "PRO_TYPE02003", "PRO_TYPE02005", "PRO_TYPE02006", "PRO_TYPE02004","PRO_TYPE02008", "PRO_TYPE02007"
    ));

    /**
     *  사업회차 연도 검색
     */
    public List<String> getOptYearList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) {
        return wBFBRegisterCompanyMapper.getOptYearList(wBFBRegisterSearchDTO);
    }

    /**
     *  사업회차 회차 검색
     */
    public List<String> getOptEpisdList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO)  throws Exception
    {
        return wBFBRegisterCompanyMapper.getOptEpisdList(wBFBRegisterSearchDTO);
    }

    /**
     *  사업 연도/회차에 맞는 사업 유형(사업유형 / 과제) SELECT
     */
    public WBRoundMstDTO getOptnList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO)  throws Exception
    {
        WBRoundMstDTO wBRoundMstDTO = new WBRoundMstDTO();

        int episdSeq = wBFBRegisterCompanyMapper.getEpisdSeq(wBFBRegisterSearchDTO);
        wBFBRegisterSearchDTO.setEpisdSeq(episdSeq);
        wBRoundMstDTO.setEpisdSeq(episdSeq);

        /* 사업 유형 */
        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01001");
        wBRoundMstDTO.setBsinList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));

        /* 과제명 */
        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01002");
        wBRoundMstDTO.setAsigtList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));

        return wBRoundMstDTO;
    }

    /**
     *   신청부품사 목록 List Get
     */
    public WBFBRegisterSearchDTO getRegisterCompanyList(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception
    {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBFBRegisterSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBFBRegisterSearchDTO.getListRowSize());

        page.setPageSize(wBFBRegisterSearchDTO.getPageRowSize());

        wBFBRegisterSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBFBRegisterSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBFBRegisterSearchDTO.setList(wBFBRegisterCompanyMapper.getRegisterCompanyList(wBFBRegisterSearchDTO));
        wBFBRegisterSearchDTO.setTotalCount(wBFBRegisterCompanyMapper.getRegisterCompanyCount(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     *  Write Page
     *  신청부품사 등록 - 관리자
     */
    @Transactional
    public int putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception
    {

        int respCnt = 0;

        /* 회원 마스터 Update */
        respCnt = wBFBRegisterCompanyMapper.updCoMemMst(wBFBRegisterDTO);

        /* 구분에 따른 분기 */
        if(wBFBRegisterDTO.getCtgryCd().equals("COMPANY01002")) {
            wBFBRegisterDTO.setQlty5starCd(null);
            wBFBRegisterDTO.setQlty5starYear(null);
            wBFBRegisterDTO.setPay5starCd(null);
            wBFBRegisterDTO.setPay5starYear(null);
            wBFBRegisterDTO.setTchlg5starCd(null);
            wBFBRegisterDTO.setTchlg5starYear(null);

            /* 회사 상세 Update or Insert */
            List<WBCompanyDetailMstDTO> sqInfoList = wBFBRegisterDTO.getSqInfoList();
            if(sqInfoList != null) {
                for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : sqInfoList) {
                    wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
                    if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                        wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                        respCnt = wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                        wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                        respCnt = wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    }
                }
            }
        } else {
            List<WBCompanyDetailMstDTO> sqInfoList = wBFBRegisterDTO.getSqInfoList();
            if(sqInfoList != null ) {
                for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : sqInfoList) {
                    wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
                    wBCompanyDetailMstDTO.setNm(null);
                    wBCompanyDetailMstDTO.setScore(null);
                    wBCompanyDetailMstDTO.setYear(null);
                    wBCompanyDetailMstDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                        wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                        respCnt = wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                        wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                        respCnt = wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    }
                }
            }
        }

        /* 회사 업종상세 Update */
        respCnt = wBFBRegisterCompanyMapper.updCmpnCbsnMst(wBFBRegisterDTO);

        /* 상생신청 마스터 */
        int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
        respCnt = wBFBRegisterCompanyMapper.putAppctnMst(wBFBRegisterDTO);

        /* 상생신청지원금액상세 - 선급금 초기값 */
        /* 스마트 공장 구축 - 선급금지급/지원금지급 코드 값*/
        List<String> giveTypes = new ArrayList<>(Arrays.asList("PRO_TYPE03001", "PRO_TYPE03002", "PRO_TYPE03003"));
        for(String type : giveTypes) {
            int firstAppctnSpprtDtlIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBFBRegisterDTO.setAppctnSpprtSeq(firstAppctnSpprtDtlIdgen);
            wBFBRegisterDTO.setGiveType(type);
            wBFBRegisterDTO.setAppctnSttsCd((type + "_01_001"));
            wBFBRegisterDTO.setMngSttsCd((type + "_02_001"));
            wBFBRegisterCompanyMapper.putAppctnSpprtDtl(wBFBRegisterDTO);
        }

        /* 스마트 공장 구축 - 신청 코드 값*/
        wBFBRegisterDTO.setRsumeSttsCd("PRO_TYPE02001");
        /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02001_01_001");
        /* 스마트 신청 관리자 최초 상태값 - 미확인 */
        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02001_02_001");
        /* 신청진행상세 진행 정렬 초기 값 */
        wBFBRegisterDTO.setRsumeOrd(1);

        /* 상생신청 진행상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        respCnt = wBFBRegisterCompanyMapper.putAppctnRsumeDtl(wBFBRegisterDTO);

        /* 상생신청파일 상세 */
        WBFBRsumeTaskDtlDTO rsumeTaskDTO = wBFBRegisterDTO.getRsumeTaskDtl();
        List<WBRsumeFileDtlDTO> fileList = rsumeTaskDTO.getAppctnFileInfo();

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBFBRegisterDTO.getFileList());
        for(int fileIdx=0; fileIdx< fileList.size(); fileIdx++) {
            WBRsumeFileDtlDTO fileInfo = fileList.get(fileIdx);
            fileInfo.setRsumeSeq(wBFBRegisterDTO.getRsumeSeq());
            fileInfo.setRsumeOrd(wBFBRegisterDTO.getRsumeOrd());
            fileInfo.setFileCd(fileInfo.getType()); /*파일타입*/
            fileInfo.setFileSeq(fileSeqMap.get(fileInfo.getSeqNm())); /*파일 시퀀스*/
            fileInfo.setRegId(wBFBRegisterDTO.getRegId());
            fileInfo.setRegIp(wBFBRegisterDTO.getRegIp());
            wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
        }

        /* 상생신청 스마트 상세 */
        wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
        respCnt = wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(wBFBRegisterDTO);

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(Integer.valueOf(wBFBRegisterDTO.getMemSeq()));
        wbSendDTO.setEpisdSeq(wBFBRegisterDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        return respCnt;
    }


    /**
     *  Edit Page
     *  회차, 신청자, 부품사 정보 Get - 회차 등록 기본 등록 정보 조회
     */
    public WBFBRegisterSearchDTO getRegisterDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {
        /* 회차 기본 정보 - 회차,신청자,부품사 */
        WBFBRegisterDTO wBFBRegisterDTO = wBFBRegisterCompanyMapper.getRegisterDtl(wBFBRegisterSearchDTO);
        wBFBRegisterSearchDTO.setRegisterDtl(wBFBRegisterDTO);
        /* 회차 등록 지급차수 정보 */
        wBFBRegisterSearchDTO.setOrderList(wBFBRegisterCompanyMapper.getGiveList(wBFBRegisterSearchDTO));

        /* 년도 전체 회차 정보 Get */
        wBFBRegisterSearchDTO.setYear(wBFBRegisterDTO.getYear());
        wBFBRegisterSearchDTO.setEpisdList(wBFBRegisterCompanyMapper.getOptEpisdList(wBFBRegisterSearchDTO));

        /* 회차 옵션 값 Get */
        WBRoundMstDTO roundDTO = new WBRoundMstDTO();
        wBFBRegisterSearchDTO.setEpisdSeq(wBFBRegisterDTO.getEpisdSeq());

        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01001");
        roundDTO.setBsinList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));
        wBFBRegisterSearchDTO.setOptnCd("BGN_REG_INF01002");
        roundDTO.setAsigtList(wBFBRegisterCompanyMapper.getOptnList(wBFBRegisterSearchDTO));
        wBFBRegisterSearchDTO.setRoundMstDTO(roundDTO);

        /* 등록 신청자 회사 - SQ 정보 Get */
        wBFBRegisterSearchDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
        wBFBRegisterSearchDTO.setSqInfoList(wBFBRegisterCompanyMapper.getRegisterDtlSQ(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     *  Edit Page
     *  선급금, 부품사, 상생 진행 정보 Get - 회차 등록 기본 등록 정보 조회
     */
    public WBFBRegisterSearchDTO getEditInfo(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {

        /* 선급금 관련 정보 - 지원금액상세 Table 조회 */
        wBFBRegisterSearchDTO.setSpprtDtl(wBFBRegisterCompanyMapper.getSpprtDtlList(wBFBRegisterSearchDTO));

        /* 부품사 관리 등록 정보 - 진행 상세 Table 조회 */
        List<WBFBRsumeTaskDtlDTO> rsumeTaskDtlList = wBFBRegisterCompanyMapper.getRsumeTaskDtlList(wBFBRegisterSearchDTO);

        /* 상생 진행 스마트 상세 파일 정보 - 신청단계 첨부파일 Get */
        WBFBRsumeTaskDtlDTO rsumeTaskDTO = rsumeTaskDtlList.get(0);
        rsumeTaskDTO.setAppctnFileInfo(wBFBRegisterCompanyMapper.getDtlFileList(rsumeTaskDTO));
        rsumeTaskDtlList.set(0, rsumeTaskDTO);

        if(rsumeTaskDtlList.size() > 6) {
            rsumeTaskDTO = rsumeTaskDtlList.get(6);
            rsumeTaskDTO.setAppctnFileInfo(wBFBRegisterCompanyMapper.getDtlFileList(rsumeTaskDTO));
            rsumeTaskDtlList.set(6, rsumeTaskDTO);
        }

        /* ReBuilding */
        wBFBRegisterSearchDTO.setRsumeTaskDtl(rsumeTaskDtlList);

        return wBFBRegisterSearchDTO;
    }

    /**
     *  사업자등록번호 Check
     */
    public WBFBRegisterSearchDTO getBsnmNoCheck(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {
        return wBFBRegisterCompanyMapper.getBsnmNoCheck(wBFBRegisterSearchDTO.getOfferBsnmNo());
    }

    /**
     *  Edit Page
     *  신청자 변경 이력 Get
     */
    public WBFBRegisterSearchDTO getAppctnTrnsfDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBFBRegisterSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBFBRegisterSearchDTO.getListRowSize());

        page.setPageSize(wBFBRegisterSearchDTO.getPageRowSize());

        wBFBRegisterSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBFBRegisterSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBFBRegisterSearchDTO.setList(wBFBRegisterCompanyMapper.getAppctnTrnsfDtl(wBFBRegisterSearchDTO));
        wBFBRegisterSearchDTO.setTotalCount(wBFBRegisterCompanyMapper.getAppctnTrnsfDtlCnt(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBFBRegisterCompanyMapper.updAdmMemo(wBFBRegisterDTO);

        return respCnt;
    }

    /**
     *  Edit Page
     *  신청 부품사 정보 Update
     */
    @Transactional
    public int updRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {
        int respCnt = 0;

        /* 파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBFBRegisterDTO.getFileList());

        /* 구분 값에 따른 분기 */
        if(wBFBRegisterDTO.getCtgryCd().equals("COMPANY01002")) {
            wBFBRegisterDTO.setQlty5starCd(null);
            wBFBRegisterDTO.setQlty5starYear(null);
            wBFBRegisterDTO.setPay5starCd(null);
            wBFBRegisterDTO.setPay5starYear(null);
            wBFBRegisterDTO.setTchlg5starCd(null);
            wBFBRegisterDTO.setTchlg5starYear(null);

            /* 회사 상세 Update or Insert */
            List<WBCompanyDetailMstDTO> sqInfoList = wBFBRegisterDTO.getSqInfoList();
            if(sqInfoList != null ) {
                for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : sqInfoList) {
                    wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
                    if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                        wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                        respCnt = wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                        wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                        respCnt = wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    }
                }
            }
        } else {
            List<WBCompanyDetailMstDTO> sqInfoList = wBFBRegisterDTO.getSqInfoList();
            if(sqInfoList != null ) {
                for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : sqInfoList) {
                    wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
                    wBCompanyDetailMstDTO.setNm(null);
                    wBCompanyDetailMstDTO.setScore(null);
                    wBCompanyDetailMstDTO.setYear(null);
                    wBCompanyDetailMstDTO.setCrtfnCmpnNm(null);
                    if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                        /* cbsnSeq is null */
                        wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                        wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                        wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                        respCnt = wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    } else {
                        /* cbsnSeq is not null */
                        wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                        wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                        respCnt = wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
                    }
                }
            }
        }

        /* 회사 마스터 Update */
        wBFBRegisterCompanyMapper.updCmpnCbsnMst(wBFBRegisterDTO);

        /* 회원 마스터 Update */
        wBFBRegisterCompanyMapper.updCoMemMst(wBFBRegisterDTO);

        /* 신청자 정보 변경시 */
        if(!wBFBRegisterDTO.getBfreMemSeq().equals(wBFBRegisterDTO.getMemSeq())) {
            WBAppctnTrnsfDtlDTO trnsfDtlDTO = new WBAppctnTrnsfDtlDTO();
            trnsfDtlDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
            trnsfDtlDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            trnsfDtlDTO.setBfreMemSeq(wBFBRegisterDTO.getBfreMemSeq());
            trnsfDtlDTO.setAftrMemSeq(wBFBRegisterDTO.getMemSeq());
            trnsfDtlDTO.setRegId(wBFBRegisterDTO.getRegId());
            trnsfDtlDTO.setRegIp(wBFBRegisterDTO.getRegIp());
            wBFBRegisterCompanyMapper.insAppctnTrnsfDtl(trnsfDtlDTO);
        }

        /* -지급- */
        /* 지급 관리 DTO*/
        WBSpprtDtlDTO spprDTO;
        /* 선급금 지급 */
        if(wBFBRegisterDTO.getPmndvPmtYn().equals("Y")) {
            spprDTO = wBFBRegisterDTO.getSpprtDtlList().get(0);
            spprDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            spprDTO.setModId(wBFBRegisterDTO.getModId());
            spprDTO.setModIp(wBFBRegisterDTO.getModIp());
            spprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile1")); /*지원금신청서*/
            spprDTO.setAcntFileSeq(fileSeqMap.get("spprtAppctnFile2")); /*계좌이체약정서*/
            spprDTO.setBnkbkFileSeq(fileSeqMap.get("spprtAppctnFile3")); /*통장사본*/
            /* 사용자 상태 값 변경 처리 */
            switch (spprDTO.getMngSttsCd()) {
                case "PRO_TYPE03001_02_003":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03001_01_003"); break;
                case "PRO_TYPE03001_02_004":
                    spprDTO.setAppctnSttsCd("PRO_TYPE02001_01_005"); break;
            }
            wBFBRegisterCompanyMapper.updSpprtDtl(spprDTO);
        }
        /* 지원금지급 */
        spprDTO = wBFBRegisterDTO.getSpprtDtlList().get(1);
        if(spprDTO.getAppctnSpprtSeq() != null){
            spprDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            spprDTO.setModId(wBFBRegisterDTO.getModId());
            spprDTO.setModIp(wBFBRegisterDTO.getModIp());
            spprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile4")); /*지원금신청서*/
            spprDTO.setAcntFileSeq(fileSeqMap.get("spprtAppctnFile5")); /*계좌이체약정서*/
            spprDTO.setBnkbkFileSeq(fileSeqMap.get("spprtAppctnFile6")); /*통장사본*/
            /* 사용자 상태 값 변경 처리 */
            switch (spprDTO.getMngSttsCd()) {
                case "PRO_TYPE03002_02_003":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03002_01_003"); break;
                case "PRO_TYPE03002_02_004":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03002_01_005"); break;
            }
            wBFBRegisterCompanyMapper.updSpprtDtl(spprDTO);
        }
        /* 기술임치 */
        spprDTO = wBFBRegisterDTO.getSpprtDtlList().get(2);
        if(spprDTO.getAppctnSpprtSeq() != null) {
            spprDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            spprDTO.setModId(wBFBRegisterDTO.getModId());
            spprDTO.setModIp(wBFBRegisterDTO.getModIp());
            spprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile7")); /*지원금신청서*/
            spprDTO.setTchlgLseFileSeq(fileSeqMap.get("spprtAppctnFile8")); /*기술임치증*/
            spprDTO.setLsePayFileSeq(fileSeqMap.get("spprtAppctnFile9")); /*임치료납입영수증*/
            /* 사용자 상태 값 변경 처리 */
            switch (spprDTO.getMngSttsCd()) {
                case "PRO_TYPE03003_02_003":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03003_01_003");
                    break;
                case "PRO_TYPE03003_02_004":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03003_01_005");
                    break;
                case "PRO_TYPE03003_02_005":
                    spprDTO.setAppctnSttsCd("PRO_TYPE03003_01_006");
                    break;
            }
            wBFBRegisterCompanyMapper.updSpprtDtl(spprDTO);
        }

        /* -부품사- */
        /* 부품사 DTO */
        WBFBRsumeTaskDtlDTO rsumeTaskDTO = wBFBRegisterDTO.getRsumeTaskDtl();

        /* 위원 값 신청 마스터 Update */
        if(rsumeTaskDTO.getChkCmssrSeq() != null && !rsumeTaskDTO.getChkCmssrSeq().toString().isEmpty()) {
            wBFBRegisterDTO.setPicCmssrSeq(rsumeTaskDTO.getChkCmssrSeq().toString());
        }
        /*상생신청 마스터*/
        respCnt =  wBFBRegisterCompanyMapper.updAppctnMst(wBFBRegisterDTO);

        /* 과제/유형/스마트화 코드 수정 */
        wBFBRegisterCompanyMapper.updRsumeTaskDtlSub(wBFBRegisterDTO);

        rsumeTaskDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
        rsumeTaskDTO.setModIp(wBFBRegisterDTO.getModIp());
        rsumeTaskDTO.setModId(wBFBRegisterDTO.getModId());

        if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02001")){ /* 신청 */
            /* File Delect / Insert */
            wBFBRegisterCompanyMapper.delAppctnFileDtl(rsumeTaskDTO);
            List<WBRsumeFileDtlDTO> fileList = rsumeTaskDTO.getAppctnFileInfo();

            for(int fileIdx=0; fileIdx< fileList.size(); fileIdx++) {
                WBRsumeFileDtlDTO fileInfo = fileList.get(fileIdx);
                fileInfo.setRsumeSeq(rsumeTaskDTO.getRsumeSeq());
                fileInfo.setRsumeOrd(rsumeTaskDTO.getRsumeOrd());
                fileInfo.setFileCd(fileInfo.getType()); /*파일타입*/
                fileInfo.setFileSeq(fileSeqMap.get(fileInfo.getSeqNm())); /*파일 시퀀스*/
                fileInfo.setRegId(wBFBRegisterDTO.getRegId());
                fileInfo.setRegIp(wBFBRegisterDTO.getRegIp());
                wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
            }

            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02001_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02001_01_002"); break;
                case "PRO_TYPE02001_02_004":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02001_01_005"); break;
                case "PRO_TYPE02001_02_005":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02001_01_006"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02002")){  /* 사업 계획 */
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02002_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02002_01_003"); break;
                case "PRO_TYPE02002_02_004":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02002_01_005"); break;
                case "PRO_TYPE02002_02_005":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02002_01_006"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02003")) { /* 최초 점검*/
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02003_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02003_01_002"); break;
                case "PRO_TYPE02003_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02003_01_003"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02004")) { /* 중간 점검 */
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02004_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02004_01_002"); break;
                case "PRO_TYPE02004_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02004_01_003"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02005")) { /* 원가 계산 */
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02005_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02005_01_002"); break;
                case "PRO_TYPE02005_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02005_01_003"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02006")) { /* 협약 */
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02006_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02006_01_002"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02007")) { /* 최종점검 */
            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02007_02_002":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02007_01_002"); break;
                case "PRO_TYPE02007_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02007_01_003"); break;
            }
        } else if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02008")) { /* 완료보고 */

            wBFBRegisterCompanyMapper.delAppctnFileDtl(rsumeTaskDTO);
            List<WBRsumeFileDtlDTO> fileList = rsumeTaskDTO.getAppctnFileInfo();

            for(int fileIdx=0; fileIdx< fileList.size(); fileIdx++) {
                WBRsumeFileDtlDTO fileInfo = fileList.get(fileIdx);
                fileInfo.setRsumeSeq(rsumeTaskDTO.getRsumeSeq());
                fileInfo.setRsumeOrd(rsumeTaskDTO.getRsumeOrd());
                fileInfo.setFileCd(fileInfo.getType()); /*파일타입*/
                fileInfo.setFileSeq(fileSeqMap.get(fileInfo.getSeqNm())); /*파일 시퀀스*/
                fileInfo.setRegId(wBFBRegisterDTO.getRegId());
                fileInfo.setRegIp(wBFBRegisterDTO.getRegIp());
                wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
            }

            switch (rsumeTaskDTO.getMngSttsCd()) {
                case "PRO_TYPE02008_02_003":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02008_01_003"); break;
                case "PRO_TYPE02008_02_004":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02008_01_005"); break;
                case "PRO_TYPE02008_02_005":
                    rsumeTaskDTO.setAppctnSttsCd("PRO_TYPE02008_01_006"); break;
            }
        }

        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(Integer.valueOf(wBFBRegisterDTO.getMemSeq()));
        wbSendDTO.setEpisdSeq(wBFBRegisterDTO.getEpisdSeq());
        wbSendDTO.setStageNm(wBFBRegisterDTO.getStageNm());
        wbSendDTO.setReason(rsumeTaskDTO.getRtrnRsnCntn());
        wbSendDTO.setRsumeSeq(rsumeTaskDTO.getRsumeSeq());
        wbSendDTO.setRsumeOrd(rsumeTaskDTO.getRsumeOrd());
        wbSendDTO.setMngSttdCd(rsumeTaskDTO.getMngSttsCd());

        //EDM,SMS발송
        if("PRO_TYPE02001_02_005".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02002_02_005".equals(rsumeTaskDTO.getMngSttsCd())
                || "PRO_TYPE02003_02_003".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02004_02_003".equals(rsumeTaskDTO.getMngSttsCd())
                || "PRO_TYPE02005_02_003".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02006_02_002".equals(rsumeTaskDTO.getMngSttsCd())
                || "PRO_TYPE02007_02_003".equals(rsumeTaskDTO.getMngSttsCd())) {
            //선정
            wbbbCompanyService.send(wbSendDTO,"SMS05");
        } else if ("PRO_TYPE02001_02_004".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02002_02_004".equals(rsumeTaskDTO.getMngSttsCd())
                || "PRO_TYPE02003_02_002".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02004_02_002".equals(rsumeTaskDTO.getMngSttsCd())
                || "PRO_TYPE02005_02_002".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02007_02_002".equals(rsumeTaskDTO.getMngSttsCd())) {
            //미선정 및 부적합
            wbbbCompanyService.send(wbSendDTO,"SMS07");
        } else if ("PRO_TYPE02001_02_002".equals(rsumeTaskDTO.getMngSttsCd()) || "PRO_TYPE02002_02_003".equals(rsumeTaskDTO.getMngSttsCd())) {
            //보완요청
            wbbbCompanyService.send(wbSendDTO,"SMS06");
        }

        /* 상생신청진행상세 */
        wBFBRegisterCompanyMapper.updRsumeDtl(rsumeTaskDTO);

        /* 상생신청진행스마트상세 */
        wBFBRegisterCompanyMapper.updRsumeTaskDtl(rsumeTaskDTO);

        // 진행 상태 코드
        COCodeDTO cOCodeDTO = new COCodeDTO();
        /* 관리자 코드 리스트 */
        cOCodeDTO.setCd(wBFBRegisterDTO.getNowRsumeTaskCd() + "_02");
        List<COCodeDTO> mngCodeList = cOCodeService.getCdIdList(cOCodeDTO);
        /* 최종 관리자 최종 값 */
        String lastMngSttsCd = mngCodeList.get(mngCodeList.size()-1).getCd();

        /* 관리자 최종 상태 값인지 확인 */
        if(lastMngSttsCd.equals(rsumeTaskDTO.getMngSttsCd())) {

            int nowIdx = appctnList.indexOf(wBFBRegisterDTO.getNowRsumeTaskCd());

            if(appctnList.size()-1 > nowIdx) {
                String nextRsumeSttsCd = appctnList.get(nowIdx+1);
                /* 상생신청 진행상세 */
                WBFBRegisterDTO registerDTO = new WBFBRegisterDTO();
                int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
                /* 진행순번 */
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
                registerDTO.setRsumeOrd(rsumeTaskDTO.getRsumeOrd() + 1);
                registerDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
                registerDTO.setRsumeSttsCd(nextRsumeSttsCd);
                registerDTO.setAppctnSttsCd(nextRsumeSttsCd + "_01_001");
                registerDTO.setMngSttsCd(nextRsumeSttsCd + "_02_001");
                registerDTO.setRegId(wBFBRegisterDTO.getRegId());
                registerDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                wBFBRegisterCompanyMapper.putStepAppctnRsumeDtl(registerDTO);

                /* 상생신청 스마트 상세 */
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
                wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(registerDTO);
            }


            /*cOCodeDTO.setCd("PRO_TYPE02"); // 스마트 공장 코드
             *//* 단계 코드 리스트 *//*
            List<COCodeDTO> stateList = cOCodeService.getCdIdList(cOCodeDTO);
            List<COCodeDTO> selStatelist = stateList.stream()
                    .filter(ele -> ele.getDpth() == 3)
                    .sorted(Comparator.comparing(COCodeDTO::getLftVal))
                    .collect(Collectors.toList());

            *//* 다음 진행 상태 *//*
            String nextRsumeSttsCd = "";
            for (int codeIdx=0; codeIdx<selStatelist.size(); codeIdx++) {
                if (selStatelist.get(codeIdx).getCd().equals(wBFBRegisterDTO.getNowRsumeTaskCd())
                        && codeIdx != (selStatelist.size()-1)) {
                    nextRsumeSttsCd = selStatelist.get((codeIdx + 1)).getCd();
                }
            }

            *//* 다음 진행 상태 값이 있을 경우 단계 생성*//*
            if(!nextRsumeSttsCd.equals("")){

                *//* 상생신청 진행상세 *//*
                WBFBRegisterDTO registerDTO = new WBFBRegisterDTO();
                int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); *//* 진행순번 *//*
                registerDTO.setRsumeOrd(rsumeTaskDTO.getRsumeOrd() + 1);
                registerDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
                registerDTO.setRsumeSttsCd(nextRsumeSttsCd);
                registerDTO.setAppctnSttsCd(nextRsumeSttsCd + "_01_001");
                registerDTO.setMngSttsCd(nextRsumeSttsCd + "_02_001");
                registerDTO.setRegId(wBFBRegisterDTO.getRegId());
                registerDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                wBFBRegisterCompanyMapper.putStepAppctnRsumeDtl(registerDTO);

                *//* 상생신청 스마트 상세 *//*
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
                wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(registerDTO);
            }*/
        }

        return respCnt;
    }

    /**
     *  신청 부품사 삭제
     */
    @Transactional
    public int deleteRegister(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {
        int respCnt = 0;

        int cofDeleteCnt = wBFBRegisterCompanyMapper.confDeleteRegister(wBFBRegisterDTO);
        if(cofDeleteCnt > 0) {
            respCnt = -1;
        } else {
            wBFBRegisterCompanyMapper.delAppctnRsumeFileDtl(wBFBRegisterDTO);
            wBFBRegisterCompanyMapper.delAppctnRsumeTaskDtl(wBFBRegisterDTO);
            wBFBRegisterCompanyMapper.delAppctnTrnsfDtl(wBFBRegisterDTO);
            wBFBRegisterCompanyMapper.delAppctnSpprtDtl(wBFBRegisterDTO);
            wBFBRegisterCompanyMapper.delAppctnRsumeDtl(wBFBRegisterDTO);

            respCnt = wBFBRegisterCompanyMapper.delAppctnMst(wBFBRegisterDTO);
        }

        return respCnt;
    }

    /**
     *  신청 부품사 삭제전 - 사용자 / 관리자 상태 확인
     */
    public int confDeleteRegister(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {
        int respCnt = 0;

        respCnt = wBFBRegisterCompanyMapper.confDeleteRegister(wBFBRegisterDTO);

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, HttpServletResponse response) throws Exception {
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
        /* 병합 */
        CellRangeAddress mergedRegion;
        row = sheet.createRow(rowNum++);

        List<String> cellVal = new ArrayList<>(Arrays.asList(
                "번호","사업연도","회차","부품사명","사업자등록번호(종사업장번호)","구분","규모","선정결과","시스템","현재수준","목표수준","(최종)수준확인"
                ,"평가위원","계획","실적","평점","결과","원가계산기","의뢰","회신","총사업비(예산)","원가계산금액","차이","정부지원","대기업출연","도입기업"
                ,"총시스템구축비","시스템구축지원(정부지원+대기업출연)","선정","협약","기간","차수","접수","정부지원금","대기업출연금"
                ,"금액계","지급","차수","접수","정부지원금","대기업출연금","금액계","지급","차수","접수","수수료-지급","지급","은행","계좌번호","예금주"

        ));

        for(int cellIdx=0; cellIdx<cellVal.size(); cellIdx++) {
            mergedRegion = new CellRangeAddress(0, 1, cellIdx, cellIdx);
            sheet.addMergedRegion(mergedRegion);
            cell = row.createCell(cellIdx);
            cell.setCellValue(cellVal.get(cellIdx));
            cell.setCellStyle(style_header);
        }

        mergedRegion = new CellRangeAddress(0, 0, 50, 53);
        sheet.addMergedRegion(mergedRegion);
        cell = row.createCell(50);
        cell.setCellValue("도입기업 담당자");
        cell.setCellStyle(style_header);

        mergedRegion = new CellRangeAddress(0, 0, 54, 56);
        sheet.addMergedRegion(mergedRegion);
        cell = row.createCell(54);
        cell.setCellValue("공급기업 담당자");
        cell.setCellStyle(style_header);

        List<String> cellValSub = new ArrayList<>(Arrays.asList(
                "성명","직급","HP/사무실번호","이메일","성명","HP","이메일"
        ));

        row = sheet.createRow(rowNum++);

        for(int cellIdx=0; cellIdx<(cellValSub.size()+cellVal.size()); cellIdx++) {
            cell = row.createCell(cellIdx);
            cell.setCellStyle(style_header);
            if(cellIdx >= 50){
                cell.setCellValue(cellValSub.get(cellIdx-cellVal.size()));
            }
        }

        // Body
        List<WBFBRegisterSearchDTO> registerList = wBFBRegisterSearchDTO.getList();

        List<WBFBRegisterSearchDTO> pbsnList = null;

        int maxOrd = 0;
        int length = 0;

        for(int listIdx=0; listIdx<registerList.size(); listIdx++) {
            row = sheet.createRow(rowNum++);

            WBFBRegisterSearchDTO registerDTO = registerList.get(listIdx);
            /* 신청지원상세 */
            List<WBSpprtDtlDTO> spprList = wBFBRegisterCompanyMapper.getSpprtDtlList(registerDTO);
            /* 신청진행상세 */
            List<WBFBRsumeTaskDtlDTO> rsumeTaskList = wBFBRegisterCompanyMapper.getRsumeTaskDtlList(registerDTO);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(listIdx+1);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(registerDTO.getYear());
            cell.setCellStyle(style_body);

            //회차
            cell = row.createCell(2);
            cell.setCellValue(registerDTO.getEpisd());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(3);
            cell.setCellValue(registerDTO.getCmpnNm());
            cell.setCellStyle(style_body);

            //부품사명(종된사업장번호)
            cell = row.createCell(4);
            String sbrdnBsnmNo = registerDTO.getSbrdnBsnmNo().isEmpty() ? "": "(" + registerDTO.getSbrdnBsnmNo() + ")";
            cell.setCellValue(registerDTO.getBsnmNo() + sbrdnBsnmNo);
            cell.setCellStyle(style_body);

            //부품사명(종된사업장번호)
            cell = row.createCell(5);
            cell.setCellValue(registerDTO.getCtgryCdNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(6);
            cell.setCellValue(registerDTO.getSizeCdNm());
            cell.setCellStyle(style_body);

            /* 신청단계 default */
            WBFBRsumeTaskDtlDTO defaultRsumeTaskDtlDTO = rsumeTaskList.get(0);

            //선정결과 - 선택 사업 유형
            cell = row.createCell(7);
            cell.setCellValue(defaultRsumeTaskDtlDTO.getBsnTypeCdNm());
            cell.setCellStyle(style_body);

            //시스템 - 선택 과제
            cell = row.createCell(8);
            cell.setCellValue(defaultRsumeTaskDtlDTO.getTaskCdNm());
            cell.setCellStyle(style_body);

            //현재수준 - 선택 스마트화 현재 수준
            cell = row.createCell(9);
            cell.setCellValue(defaultRsumeTaskDtlDTO.getSmtfnPrsntCdNm());
            cell.setCellStyle(style_body);

            //현재수준 - 선택 스마트화 목표 수준
            cell = row.createCell(10);
            cell.setCellValue(defaultRsumeTaskDtlDTO.getSmtfnTrgtCdNm());
            cell.setCellStyle(style_body);

            /* 단계 별 공통 DTO */
            WBFBRsumeTaskDtlDTO rsumeTaskDtlDTO;
            if(rsumeTaskList.size() > 6) {
                rsumeTaskDtlDTO = rsumeTaskList.get(6);
                //최종수준 - 최종점검 단계의 스마트화 수준
                cell = row.createCell(11);
                cell.setCellValue(rsumeTaskDtlDTO.getSmtfnLvlChkCdNm());
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(11);
                cell.setCellStyle(style_body);
            }

            if(rsumeTaskList.size() > 2) {
                rsumeTaskDtlDTO = rsumeTaskList.get(2);

                //평가위원 - 최초점검 단계의 평가위원
                cell = row.createCell(12);
                cell.setCellValue(rsumeTaskDtlDTO.getChkCmssrNm());
                cell.setCellStyle(style_body);

                //계획 - 최초점검 단계의 점검계획일
                cell = row.createCell(13);
                cell.setCellValue(rsumeTaskDtlDTO.getChkPlanDtFmt());
                cell.setCellStyle(style_body);

                //실적 - 최초점검 단계의 점검시행일
                cell = row.createCell(14);
                cell.setCellValue(rsumeTaskDtlDTO.getChkImplmnDtFmt());
                cell.setCellStyle(style_body);

                //평점 - 최초점검 단계의 평점
                cell = row.createCell(15);
                cell.setCellValue(rsumeTaskDtlDTO.getExamScore() != null ? rsumeTaskDtlDTO.getExamScore().toString() : "");
                cell.setCellStyle(style_body);

                //결과 - 최초점검 단계의 관리자 상태값
                cell = row.createCell(16);
                cell.setCellValue(rsumeTaskDtlDTO.getMngSttsCdNm());
                cell.setCellStyle(style_body);
            } else {
                //평가위원 - 최초점검 단계의 평가위원
                cell = row.createCell(12);
                cell.setCellStyle(style_body);

                //계획 - 최초점검 단계의 점검계획일
                cell = row.createCell(13);
                cell.setCellStyle(style_body);

                //실적 - 최초점검 단계의 점검시행일
                cell = row.createCell(14);
                cell.setCellStyle(style_body);

                //평점 - 최초점검 단계의 평점
                cell = row.createCell(15);
                cell.setCellStyle(style_body);

                //결과 - 최초점검 단계의 관리자 상태값
                cell = row.createCell(16);
                cell.setCellStyle(style_body);
            }

            Integer ttlPmt = 0;
            /*원가계산 단계*/
            if(rsumeTaskList.size() > 4) {
                rsumeTaskDtlDTO = rsumeTaskList.get(4);
                /*원가계산 단계 총금액*/
                if(rsumeTaskDtlDTO.getTtlPmt() != null) {
                    ttlPmt = rsumeTaskDtlDTO.getTtlPmt();
                }

                //원가계산기관 - 원가계산 단계의 원가계산기관
                cell = row.createCell(17);
                cell.setCellValue(rsumeTaskDtlDTO.getPrmcstClcltnInstt());
                cell.setCellStyle(style_body);

                //의뢰 - 원가계산 단계의 의뢰일
                cell = row.createCell(18);
                cell.setCellValue(rsumeTaskDtlDTO.getRqstDtFmt());
                cell.setCellStyle(style_body);

                //회신 - 원가계산 단계의 회신일
                cell = row.createCell(19);
                cell.setCellValue(rsumeTaskDtlDTO.getRplyDtFmt());
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(17);
                cell.setCellStyle(style_body);

                //의뢰 - 원가계산 단계의 의뢰일
                cell = row.createCell(18);
                cell.setCellStyle(style_body);

                //회신 - 원가계산 단계의 회신일
                cell = row.createCell(19);
                cell.setCellStyle(style_body);
            }

            Integer ttlBsnPmt = 0;
            if(rsumeTaskList.size() > 1) {
                rsumeTaskDtlDTO = rsumeTaskList.get(1);

                if(rsumeTaskDtlDTO.getTtlBsnPmt() != null ) {
                    ttlBsnPmt = rsumeTaskDtlDTO.getTtlBsnPmt();
                }

                //총사업비 - 사업계획 단계의 총사업비
                cell = row.createCell(20);
                cell.setCellValue(rsumeTaskDtlDTO.getTtlBsnPmt() != null ? rsumeTaskDtlDTO.getTtlBsnPmt().toString() : "");
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(20);
                cell.setCellStyle(style_body);
            }

            //원가계산금액 - 원가계산 단계의 총금액
            cell = row.createCell(21);
            cell.setCellValue(ttlPmt);
            cell.setCellStyle(style_body);

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            int diffTtl = ttlBsnPmt - ttlPmt;

            cell = row.createCell(22);
            cell.setCellValue(diffTtl == 0 ? "-" : decimalFormat.format(diffTtl));
            cell.setCellStyle(style_body);

            if(rsumeTaskList.size() > 4) {
                rsumeTaskDtlDTO = rsumeTaskList.get(4);

                //정부지원 - 원가계산 단계의 정부지원금
                cell = row.createCell(23);
                cell.setCellValue(rsumeTaskDtlDTO.getGvmntSpprtPmt() != null ? rsumeTaskDtlDTO.getGvmntSpprtPmt().toString() : "");
                cell.setCellStyle(style_body);

                //대기업출연 - 원가계산 단계의 대기업출연금
                Integer mjcmnAprncPmt = 0;
                if(rsumeTaskDtlDTO.getMjcmnAprncPmt() != null) {
                    mjcmnAprncPmt = rsumeTaskDtlDTO.getMjcmnAprncPmt();
                }
                cell = row.createCell(24);
                cell.setCellValue(rsumeTaskDtlDTO.getMjcmnAprncPmt() != null ? rsumeTaskDtlDTO.getNtdcnCmpnBrdnPmt().toString() : "");
                cell.setCellStyle(style_body);

                //도입기업 - 원가계산 단계의 도입기업부담금
                Integer ntdcnCmpnBrdnPmt = 0;
                if(rsumeTaskDtlDTO.getNtdcnCmpnBrdnPmt() != null) {
                    ntdcnCmpnBrdnPmt = rsumeTaskDtlDTO.getNtdcnCmpnBrdnPmt();
                }
                cell = row.createCell(25);
                cell.setCellValue(rsumeTaskDtlDTO.getNtdcnCmpnBrdnPmt() != null ? rsumeTaskDtlDTO.getNtdcnCmpnBrdnPmt().toString() : "" );
                cell.setCellStyle(style_body);

                //총시스템구축비 - 원가계산 단계의 총금액
                cell = row.createCell(26);
                cell.setCellValue(rsumeTaskDtlDTO.getTtlPmt() != null ? rsumeTaskDtlDTO.getTtlPmt().toString() : "");
                cell.setCellStyle(style_body);

                int sumPmt = mjcmnAprncPmt + ntdcnCmpnBrdnPmt;
                //시스템구축지원 - (정부지원+대기업출연)
                cell = row.createCell(27);
                cell.setCellValue(sumPmt);
                cell.setCellStyle(style_body);
            } else {
                //정부지원 - 원가계산 단계의 정부지원금
                cell = row.createCell(23);
                cell.setCellStyle(style_body);

                //대기업출연 - 원가계산 단계의 대기업출연금
                cell = row.createCell(24);
                cell.setCellStyle(style_body);

                //도입기업 - 원가계산 단계의 도입기업부담금
                cell = row.createCell(25);
                cell.setCellStyle(style_body);

                //총시스템구축비 - 원가계산 단계의 총금액
                cell = row.createCell(26);
                cell.setCellStyle(style_body);

                //시스템구축지원 - (정부지원+대기업출연)
                cell = row.createCell(27);
                cell.setCellStyle(style_body);
            }

            //선정 - 신청 단계의 관리자상태값
            cell = row.createCell(28);
            cell.setCellValue(defaultRsumeTaskDtlDTO.getMngSttsCdNm());
            cell.setCellStyle(style_body);

            if(rsumeTaskList.size() > 5) {
                rsumeTaskDtlDTO = rsumeTaskList.get(5);

                //협약 - 협약 단계의 협약일
                cell = row.createCell(29);
                cell.setCellValue(rsumeTaskDtlDTO.getAgrmtDtFmt());
                cell.setCellStyle(style_body);

                //기간 - 협약 단계의 기간
                cell = row.createCell(30);
                cell.setCellValue(rsumeTaskDtlDTO.getAgrmtTermDtFmt());
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(29);
                cell.setCellStyle(style_body);

                //기간 - 협약 단계의 기간
                cell = row.createCell(30);
                cell.setCellStyle(style_body);
            }
            /*지급 단계 별 공통 DTO */
            WBSpprtDtlDTO SpprtDtlDTO = spprList.get(0);

            //차수 - 선급금지급 영역의 지급차수
            cell = row.createCell(31);
            cell.setCellValue(SpprtDtlDTO.getGiveOrd() != null ? SpprtDtlDTO.getGiveOrd().toString() : "");
            cell.setCellStyle(style_body);

            //접수 - 선급금지급 영역의 접수일
            cell = row.createCell(32);
            cell.setCellValue(SpprtDtlDTO.getAccsDtFmt());
            cell.setCellStyle(style_body);

            //정부지원금 - 선급금지급 영역의 정부지원금
            cell = row.createCell(33);
            cell.setCellValue(SpprtDtlDTO.getGvmntSpprtPmt() != null ? SpprtDtlDTO.getGvmntSpprtPmt().toString() : "");
            cell.setCellStyle(style_body);

            //대기업출연금 - 선급금지급 영역의 대기업출연금
            cell = row.createCell(34);
            cell.setCellValue(SpprtDtlDTO.getMjcmnAprncPmt() != null ? SpprtDtlDTO.getMjcmnAprncPmt().toString() : "");
            cell.setCellStyle(style_body);

            //금액계 - 선급금지급 영역의 총금액
            cell = row.createCell(35);
            cell.setCellValue(SpprtDtlDTO.getTtlPmt() != null ? SpprtDtlDTO.getTtlPmt().toString() : "");
            cell.setCellStyle(style_body);

            //지급 - 선급금지급 영역의 지급일
            cell = row.createCell(36);
            cell.setCellValue(SpprtDtlDTO.getGiveDtFmt());
            cell.setCellStyle(style_body);


            if(spprList.size() > 1)  {
                SpprtDtlDTO = spprList.get(1);

                //차수 - 지원금지급 영역의 지급차수
                cell = row.createCell(37);
                cell.setCellValue(SpprtDtlDTO.getGiveOrd() != null ? SpprtDtlDTO.getGiveOrd().toString() : "");
                cell.setCellStyle(style_body);

                //접수 - 지원금지급 영역의 접수일
                cell = row.createCell(38);
                cell.setCellValue(SpprtDtlDTO.getAccsDtFmt());
                cell.setCellStyle(style_body);

                //정부지원금 - 지원금지급 영역의 정부지원금
                cell = row.createCell(39);
                cell.setCellValue(SpprtDtlDTO.getGvmntSpprtPmt() != null ? SpprtDtlDTO.getGvmntSpprtPmt().toString() : "");
                cell.setCellStyle(style_body);

                //대기업출연금 - 지원금지급 영역의 대기업출연금
                cell = row.createCell(40);
                cell.setCellValue(SpprtDtlDTO.getMjcmnAprncPmt() != null ? SpprtDtlDTO.getMjcmnAprncPmt().toString() : "");
                cell.setCellStyle(style_body);

                //금액계 - 지원금지급 영역의 총금액
                cell = row.createCell(41);
                cell.setCellValue(SpprtDtlDTO.getTtlPmt() != null ? SpprtDtlDTO.getTtlPmt().toString() : "");
                cell.setCellStyle(style_body);

                //지급 - 지원금지급 영역의 지급일
                cell = row.createCell(42);
                cell.setCellValue(SpprtDtlDTO.getGiveDtFmt());
                cell.setCellStyle(style_body);
            } else {
                //차수 - 지원금지급 영역의 지급차수
                cell = row.createCell(37);
                cell.setCellStyle(style_body);

                //접수 - 지원금지급 영역의 접수일
                cell = row.createCell(38);
                cell.setCellStyle(style_body);

                //정부지원금 - 지원금지급 영역의 정부지원금
                cell = row.createCell(39);
                cell.setCellStyle(style_body);

                //대기업출연금 - 지원금지급 영역의 대기업출연금
                cell = row.createCell(40);
                cell.setCellStyle(style_body);

                //금액계 - 지원금지급 영역의 총금액
                cell = row.createCell(41);
                cell.setCellStyle(style_body);

                //지급 - 지원금지급 영역의 지급일
                cell = row.createCell(42);
                cell.setCellStyle(style_body);
            }

            if(spprList.size() > 2)  {
                SpprtDtlDTO = spprList.get(2);

                //차수 - 기술임치 영역의 지급차수
                cell = row.createCell(43);
                cell.setCellValue(SpprtDtlDTO.getGiveOrd() != null ? SpprtDtlDTO.getGiveOrd().toString() : "" );
                cell.setCellStyle(style_body);

                //접수 - 기술임치 영역의 접수일
                cell = row.createCell(44);
                cell.setCellValue(SpprtDtlDTO.getAccsDtFmt());
                cell.setCellStyle(style_body);

                //수수료 - 기술임치 영역의 수수료
                cell = row.createCell(45);
                cell.setCellValue(SpprtDtlDTO.getCmssnPmt() != null ? SpprtDtlDTO.getCmssnPmt().toString() : "");
                cell.setCellStyle(style_body);

                //지급 - 기술임치 영역의 지급일
                cell = row.createCell(46);
                cell.setCellValue(SpprtDtlDTO.getGiveDtFmt());
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(43);
                cell.setCellStyle(style_body);

                //접수 - 기술임치 영역의 접수일
                cell = row.createCell(44);
                cell.setCellStyle(style_body);

                //수수료 - 기술임치 영역의 수수료
                cell = row.createCell(45);
                cell.setCellStyle(style_body);

                //지급 - 기술임치 영역의 지급일
                cell = row.createCell(46);
                cell.setCellStyle(style_body);
            }

            if(spprList.size() > 1) {
                SpprtDtlDTO = spprList.get(1);

                //은행 - 지원금지급 영역의 은행명
                cell = row.createCell(47);
                cell.setCellValue(SpprtDtlDTO.getBankNm());
                cell.setCellStyle(style_body);

                //계좌번호 - 지원금지급 영역의 계좌번호
                cell = row.createCell(48);
                cell.setCellValue(SpprtDtlDTO.getAcntNo());
                cell.setCellStyle(style_body);

                //예금주 - 지원금지급 영역의 예금주
                cell = row.createCell(49);
                cell.setCellValue(SpprtDtlDTO.getDpsitNm());
                cell.setCellStyle(style_body);
            } else {
                //은행 - 지원금지급 영역의 은행명
                cell = row.createCell(47);
                cell.setCellStyle(style_body);

                //계좌번호 - 지원금지급 영역의 계좌번호
                cell = row.createCell(48);
                cell.setCellStyle(style_body);

                //예금주 - 지원금지급 영역의 예금주
                cell = row.createCell(49);
                cell.setCellStyle(style_body);
            }

            //신청자 - 신청자이름
            cell = row.createCell(50);
            cell.setCellValue(registerDTO.getName());
            cell.setCellStyle(style_body);

            //직급 - 신청자직급
            cell = row.createCell(51);
            cell.setCellValue(registerDTO.getPstnCdNm());
            cell.setCellStyle(style_body);

            // HP/사무실번호 - 신청자 휴대폰번호/일반전화번호
            cell = row.createCell(52);
            cell.setCellValue(registerDTO.getTelNo() + "/" + registerDTO.getHpNo());
            cell.setCellStyle(style_body);

            // 이메일 - 신청자 이메일
            cell = row.createCell(53);
            cell.setCellValue(registerDTO.getEmail());
            cell.setCellStyle(style_body);

            if(rsumeTaskList.size() > 1) {
                rsumeTaskDtlDTO = rsumeTaskList.get(1);

                // 성명 - 사업계획 단계의 공급기업 담당자명
                cell = row.createCell(54);
                cell.setCellValue(rsumeTaskDtlDTO.getOfferPicNm());
                cell.setCellStyle(style_body);

                // HP - 사업계획 단계의 공급기업 담당자 휴대폰
                cell = row.createCell(55);
                cell.setCellValue(rsumeTaskDtlDTO.getOfferPicHpNo());
                cell.setCellStyle(style_body);

                // 이메일 - 사업계획 단계의 공급기업 담당자 이메일
                cell = row.createCell(56);
                cell.setCellValue(rsumeTaskDtlDTO.getOfferPicEmail());
                cell.setCellStyle(style_body);
            } else {
                cell = row.createCell(54);
                cell.setCellStyle(style_body);

                // HP - 사업계획 단계의 공급기업 담당자 휴대폰
                cell = row.createCell(55);
                cell.setCellStyle(style_body);

                // 이메일 - 사업계획 단계의 공급기업 담당자 이메일
                cell = row.createCell(56);
                cell.setCellStyle(style_body);
            }
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
     * Page - 사용자
     * 사업 신청 등록
     */
    @Transactional
    public WBFBRegisterDTO insertApply(WBFBRegisterDTO wBFBRegisterDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            String regId = cOUserDetailsDTO.getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            wBFBRegisterDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
            wBFBRegisterDTO.setMemSeq(cOUserDetailsDTO.getSeq().toString());
            wBFBRegisterDTO.setRegId(regId);
            wBFBRegisterDTO.setRegIp(regIp);

            WBFBRegisterSearchDTO wBFBRegisterSearchDTO = new WBFBRegisterSearchDTO();
            wBFBRegisterSearchDTO.setEpisdSeq(wBFBRegisterDTO.getEpisdSeq());
            wBFBRegisterSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
            wBFBRegisterSearchDTO.setSbrdnBsnmNo(wBFBRegisterDTO.getSbrdnBsnmNo().trim());
            wBFBRegisterSearchDTO.setRegisterDtl(wBFBRegisterCompanyMapper.getCompanyInfo(wBFBRegisterSearchDTO));

            /* 상생신청 마스터 */
            int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
            wBFBRegisterDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
            respCnt = wBFBRegisterCompanyMapper.putAppctnMst(wBFBRegisterDTO);

            /* 상생신청지원금액상세 - 선급금 초기값 */
            /* 스마트 공장 구축 - 선급금지급/지원금지급 코드 값*/
            List<String> giveTypes = new ArrayList<>(Arrays.asList("PRO_TYPE03001", "PRO_TYPE03002", "PRO_TYPE03003"));
            for(String type : giveTypes) {
                int firstAppctnSpprtDtlIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
                wBFBRegisterDTO.setAppctnSpprtSeq(firstAppctnSpprtDtlIdgen);
                wBFBRegisterDTO.setGiveType(type);
                wBFBRegisterDTO.setAppctnSttsCd((type + "_01_001"));
                wBFBRegisterDTO.setMngSttsCd((type + "_02_001"));
                wBFBRegisterCompanyMapper.putAppctnSpprtDtl(wBFBRegisterDTO);
            }

            /* 스마트 공장 구축 - 신청 코드 값*/
            wBFBRegisterDTO.setRsumeSttsCd("PRO_TYPE02001");
            /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
            wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02001_01_001");
            /* 스마트 신청 관리자 최초 상태값 - 미확인 */
            wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02001_02_001");
            /* 신청진행상세 진행 정렬 초기 값 */
            wBFBRegisterDTO.setRsumeOrd(1);

            /* 상생신청 진행상세 */
            int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
            wBFBRegisterCompanyMapper.putAppctnRsumeDtl(wBFBRegisterDTO);

            // 신청파일 등록
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

                    WBRsumeFileDtlDTO fileInfo = new WBRsumeFileDtlDTO();
                    fileInfo.setRsumeSeq(wBFBRegisterDTO.getRsumeSeq());
                    fileInfo.setRsumeOrd(wBFBRegisterDTO.getRsumeOrd());
                    if(i == 0) { fileInfo.setFileCd("ATTACH_FILE_TYPE01"); }
                    else if(i == 1) { fileInfo.setFileCd("ATTACH_FILE_TYPE02"); }
                    else if(i == 2) { fileInfo.setFileCd("ATTACH_FILE_TYPE03"); }
                    else if(i == 3) { fileInfo.setFileCd("ATTACH_FILE_TYPE04"); }
                    fileInfo.setFileSeq(fileSeqMap.get("fileSeq"));
                    fileInfo.setRegId(regId);
                    fileInfo.setRegIp(regIp);

                    wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
                }
            }

            /* 상생신청 스마트 상세 */
            wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(wBFBRegisterDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        wBFBRegisterDTO.setRespCnt(respCnt);
        return wBFBRegisterDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBFBRegisterSearchDTO getCompanyUserDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {

        /* 부품사 회원 정보 Get */
        wBFBRegisterSearchDTO.setRegisterDtl(wBFBRegisterCompanyMapper.getCompanyInfo(wBFBRegisterSearchDTO));
        /* 등록 신청자 회사 - SQ 정보 Get */
        wBFBRegisterSearchDTO.setSqInfoList(wBFBRegisterCompanyMapper.getRegisterDtlSQ(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     * 등록된 부품사 신청 내역 조회
     * @return
     */
    public WBFBRegisterSearchDTO getApplyDtl(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {
        return wBFBRegisterCompanyMapper.getApplyDtl(wBFBRegisterSearchDTO);
    }

    /**
     * 부품사 신청 정보를 수정한다.
     * @return
     */
    @Transactional
    public int updInfoUser(WBFBRegisterDTO wBFBRegisterDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        int respCnt = 0;

        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            String modId = cOUserDetailsDTO.getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            WBFBRsumeTaskDtlDTO wbfbRsumeTaskDtlDTO = wBFBRegisterDTO.getRsumeTaskDtl();

            if(wbfbRsumeTaskDtlDTO != null && wbfbRsumeTaskDtlDTO.getRsumeSttsCd() != null){

                wbfbRsumeTaskDtlDTO.setModId(modId);
                wbfbRsumeTaskDtlDTO.setModIp(modIp);

                switch (wBFBRegisterDTO.getAppctnSttsCd()) {
                    case "PRO_TYPE02001_01_002":
                        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02001_01_003");
                        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02001_02_001");
                        break;
                    case "PRO_TYPE02002_01_001":
                        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02002_01_002");
                        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02002_02_002");
                        break;
                    case "PRO_TYPE02002_01_003":
                        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02002_01_004");
                        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02002_02_002");
                        break;
                    case "PRO_TYPE02008_01_001":
                        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02008_01_002");
                        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02008_02_002");
                        break;
                    case "PRO_TYPE02008_01_003":
                        wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02008_01_004");
                        wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02008_02_002");
                        break;
                }

                /* 공통 - 상태값 변경*/
                wBFBRegisterCompanyMapper.updRsumeDtl(
                        WBFBRsumeTaskDtlDTO.builder()
                        .appctnSttsCd(wBFBRegisterDTO.getAppctnSttsCd())
                        .mngSttsCd(wBFBRegisterDTO.getMngSttsCd())
                        .modId(modId)
                        .modIp(modIp)
                        .rsumeSeq(wbfbRsumeTaskDtlDTO.getRsumeSeq())
                        .rsumeOrd(wbfbRsumeTaskDtlDTO.getRsumeOrd())
                        .appctnSeq(wBFBRegisterDTO.getAppctnSeq())
                        .build()
                );

                if(wbfbRsumeTaskDtlDTO.getRsumeSttsCd().equals("PRO_TYPE02001")) {
                    WBFBRegisterSearchDTO wBFBRegisterSearchDTO = WBFBRegisterSearchDTO.builder()
                            .bsnmNo(wBFBRegisterDTO.getBsnmNo())
                            .sbrdnBsnmNo(wBFBRegisterDTO.getSbrdnBsnmNo())
                            .build();

                    int Overlap = wBFBRegisterCompanyMapper.getSbrdmNoCheck(wBFBRegisterSearchDTO);

                    if(Overlap > 0){
                        respCnt = 300;
                    } else {
                        /* 신청 */
                        /* 사업, 과제, 스마트 만 수정 */
                        respCnt = wBFBRegisterCompanyMapper.updRsumeTaskDtlSub(
                                WBFBRegisterDTO.builder()
                                        .appctnSeq(wBFBRegisterDTO.getAppctnSeq())
                                        .taskCd(wbfbRsumeTaskDtlDTO.getTaskCd())
                                        .bsnTypeCd(wbfbRsumeTaskDtlDTO.getBsnTypeCd())
                                        .smtfnPrsntCd(wbfbRsumeTaskDtlDTO.getSmtfnPrsntCd())
                                        .smtfnTrgtCd(wbfbRsumeTaskDtlDTO.getSmtfnTrgtCd())
                                        .modId(modId)
                                        .modIp(modIp)
                                        .build()
                        );

                        /* 신청 파일 삭제 */
                        wBFBRegisterCompanyMapper.delAppctnFileDtl(wbfbRsumeTaskDtlDTO);
                        // 신청파일 등록
                        List<COFileDTO> rtnList = null;
                        Map<String, MultipartFile> files = multiRequest.getFileMap();
                        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
                        MultipartFile file;
                        int atchFileCnt = 0;

                        while (itr.hasNext()) {
                            Map.Entry<String, MultipartFile> entry = itr.next();
                            file = entry.getValue();

                            if (file.getName().indexOf("atchFile") > -1 && file.getSize() > 0) {
                                atchFileCnt++;
                            }
                        }

                        if (!files.isEmpty()) {
                            rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                            for (int i = 0; i < rtnList.size(); i++) {

                                List<COFileDTO> fileList = new ArrayList();
                                Integer fileSeq;

                                if ("99".equals(rtnList.get(i).getRespCd())) {
                                    fileSeq = wBFBRegisterDTO.getFileSeqList().get(i);
                                } else {
                                    rtnList.get(i).setStatus("success");
                                    rtnList.get(i).setFieldNm("fileSeq");
                                    fileList.add(rtnList.get(i));
                                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                                    fileSeq = fileSeqMap.get("fileSeq");
                                }

                                WBRsumeFileDtlDTO fileInfo = new WBRsumeFileDtlDTO();
                                fileInfo.setRsumeSeq(wbfbRsumeTaskDtlDTO.getRsumeSeq());
                                fileInfo.setRsumeOrd(wbfbRsumeTaskDtlDTO.getRsumeOrd());
                                if (i == 0) {
                                    fileInfo.setFileCd("ATTACH_FILE_TYPE01");
                                } else if (i == 1) {
                                    fileInfo.setFileCd("ATTACH_FILE_TYPE02");
                                } else if (i == 2) {
                                    fileInfo.setFileCd("ATTACH_FILE_TYPE03");
                                } else if (i == 3) {
                                    fileInfo.setFileCd("ATTACH_FILE_TYPE04");
                                }
                                fileInfo.setFileSeq(fileSeq);
                                fileInfo.setRegId(modId);
                                fileInfo.setRegIp(modIp);

                                wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
                            }
                        }
                    }
                } else if(wbfbRsumeTaskDtlDTO.getRsumeSttsCd().equals("PRO_TYPE02002")) {
                    /* 사업계획 */
                    respCnt = wBFBRegisterCompanyMapper.updRsumeTaskDtl(wbfbRsumeTaskDtlDTO);
                } else if(wbfbRsumeTaskDtlDTO.getRsumeSttsCd().equals("PRO_TYPE02008")) {
                    /* 신청 파일 삭제 */
                    wBFBRegisterCompanyMapper.delAppctnFileDtl(wbfbRsumeTaskDtlDTO);
                    // 신청파일 등록
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
                                fileSeq = wBFBRegisterDTO.getFileSeqList().get(i);
                            } else {
                                rtnList.get(i).setStatus("success");
                                rtnList.get(i).setFieldNm("fileSeq");
                                fileList.add(rtnList.get(i));
                                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                                fileSeq = fileSeqMap.get("fileSeq");
                            }

                            WBRsumeFileDtlDTO fileInfo = new WBRsumeFileDtlDTO();
                            fileInfo.setRsumeSeq(wbfbRsumeTaskDtlDTO.getRsumeSeq());
                            fileInfo.setRsumeOrd(wbfbRsumeTaskDtlDTO.getRsumeOrd());
                            if(i == 0) { fileInfo.setFileCd("ATTACH_FILE_TYPE10"); }
                            fileInfo.setFileSeq(fileSeq);
                            fileInfo.setRegId(modId);
                            fileInfo.setRegIp(modIp);

                            wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
                        }
                    }
                }
            }

            WBSpprtDtlDTO spprtDtl = wBFBRegisterDTO.getSpprtDtlList() != null ? wBFBRegisterDTO.getSpprtDtlList().get(0) : null;

            if(spprtDtl != null && spprtDtl.getGiveType() != null){

                spprtDtl.setModId(modId);
                spprtDtl.setModIp(modIp);

                // 신청파일 등록
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
                            fileSeq = wBFBRegisterDTO.getFileSeqList().get(i);
                        } else {
                            rtnList.get(i).setStatus("success");
                            rtnList.get(i).setFieldNm("fileSeq");
                            fileList.add(rtnList.get(i));
                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                            fileSeq = fileSeqMap.get("fileSeq");
                        }

                        if(spprtDtl.getGiveType().equals("PRO_TYPE03001")) {
                            /* 선급금 */
                            if(i == 0) {
                                spprtDtl.setSpprtAppctnFileSeq(fileSeq); /*지원금신청서*/
                            } else if (i == 1) {
                                spprtDtl.setAcntFileSeq(fileSeq); /*계좌이체약정서*/
                            } else if (i == 2) {
                                spprtDtl.setBnkbkFileSeq(fileSeq); /*통장사본*/
                            }
                        } else if(spprtDtl.getGiveType().equals("PRO_TYPE03002")) {
                            /* 지원금 */
                            if(i == 0) {
                                spprtDtl.setSpprtAppctnFileSeq(fileSeq); /*지원금신청서*/
                            } else if (i == 1) {
                                spprtDtl.setAcntFileSeq(fileSeq); /*계좌이체약정서*/
                            } else if (i == 2) {
                                spprtDtl.setBnkbkFileSeq(fileSeq); /*통장사본*/
                            }
                        } else if(spprtDtl.getGiveType().equals("PRO_TYPE03003")) {
                            /* 기울임치 */
                            if(i == 0) {
                                spprtDtl.setSpprtAppctnFileSeq(fileSeq); /*지원금신청서*/
                            } else if (i == 1) {
                                spprtDtl.setTchlgLseFileSeq(fileSeq); /*기술임치증*/
                            } else if (i == 2) {
                                spprtDtl.setLsePayFileSeq(fileSeq); /*임치료납입영수증*/
                            }
                        }
                    }
                }

                switch (spprtDtl.getAppctnSttsCd()) {
                    case "PRO_TYPE03001_01_003":
                        spprtDtl.setAppctnSttsCd("PRO_TYPE03001_01_004");
                        spprtDtl.setMngSttsCd("PRO_TYPE03001_02_002");
                        break;
                    case "PRO_TYPE03002_01_003":
                        spprtDtl.setAppctnSttsCd("PRO_TYPE03002_01_004");
                        spprtDtl.setMngSttsCd("PRO_TYPE03002_02_002");
                        break;
                    case "PRO_TYPE03003_01_003":
                        spprtDtl.setAppctnSttsCd("PRO_TYPE03003_01_004");
                        spprtDtl.setMngSttsCd("PRO_TYPE03003_02_002");
                        break;
                }

                /* 상생신청지원금액상세 */
                respCnt = wBFBRegisterCompanyMapper.updSpprtDtl(spprtDtl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respCnt;
    }

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBFBRegisterCompanyMapper.getBsnmNoCnt(wBFBRegisterDTO);

        wBFBRegisterDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBFBRegisterCompanyMapper.getSbrdnBsnmNoCnt(wBFBRegisterDTO);

        wBFBRegisterDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 사업자번호/종된사업자번호 중복 확인
     * @return
     */
    public int getSbrdmNoCheck(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {

        int respCnt = 0;

        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
            wBFBRegisterSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

            /* 회원 순번 기준 신청 조회 */
            int overLapMemSeq = wBFBRegisterCompanyMapper.getMemSeqCheck(wBFBRegisterSearchDTO);

            if(overLapMemSeq == 0) {
                int overlap = wBFBRegisterCompanyMapper.getSbrdmNoCheck(wBFBRegisterSearchDTO);

                /* 종된 사업장번호 미 입력 */
                if(overlap > 0 && wBFBRegisterSearchDTO.getSbrdnBsnmNo().isEmpty()) {
                    respCnt = 200;
                }

                /* 종된 사업장번호 입력 */
                if(overlap > 0 && !wBFBRegisterSearchDTO.getSbrdnBsnmNo().isEmpty()) {
                    respCnt = 300;
                }
            } else {
                respCnt = 100;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respCnt;
    }
}
