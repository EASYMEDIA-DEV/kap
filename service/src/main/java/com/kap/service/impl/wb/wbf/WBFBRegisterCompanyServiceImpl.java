package com.kap.service.impl.wb.wbf;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.*;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRsumeTaskDtlDTO;
import com.kap.service.COCodeService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFBRegisterCompanyService;
import com.kap.service.dao.wb.wbf.WBFBRegisterCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.applet.Main;

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
     *  신청부품사 등록
     */
    @Transactional
    public int putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception
    {

        int respCnt = 1;

        /* 회원 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCoMemMst(wBFBRegisterDTO);

        /* 회사 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnMst(wBFBRegisterDTO);

        /* 회사 상세 Update or Insert */
        for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : wBFBRegisterDTO.getSqInfoList()) {
            wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
            if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                /* cbsnSeq is null */
                wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                respCnt *= wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
            } else {
                /* cbsnSeq is not null */
                wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
            }
        }

        /* 상생신청 마스터 */
        int firstAppctnMstIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setAppctnSeq(firstAppctnMstIdgen); /* 신청순번 */
        respCnt *= wBFBRegisterCompanyMapper.putAppctnMst(wBFBRegisterDTO);

        /* 상생신청지원금액상세 - 선급금 초기값 */
        /* 스마트 공장 구축 - 선급금지급/지원금지급 코드 값*/
        List<String> giveTypes = new ArrayList<>(Arrays.asList("PRO_TYPE03001", "PRO_TYPE03002"));
        for(String type : giveTypes) {
            wBFBRegisterDTO.setGiveType(type);
            int firstAppctnSpprtDtlIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBFBRegisterDTO.setAppctnSpprtSeq(firstAppctnSpprtDtlIdgen);
            respCnt *= wBFBRegisterCompanyMapper.putAppctnSpprtDtl(wBFBRegisterDTO);
        }

        /* 상생신청 진행상세 */
        int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
        respCnt *= wBFBRegisterCompanyMapper.putAppctnRsumeDtl(wBFBRegisterDTO);

        /* 상생신청파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBFBRegisterDTO.getFileList());
        WBRsumeFileDtlDTO fileInfo = WBRsumeFileDtlDTO.builder()
                .rsumeSeq(wBFBRegisterDTO.getRsumeSeq())
                .rsumeOrd(wBFBRegisterDTO.getRsumeOrd())
                .fileCd("ATTACH_FILE_TYPE01")
                .fileSeq(fileSeqMap.get("appctnFileSeq"))
                .regId(wBFBRegisterDTO.getRegId())
                .regIp(wBFBRegisterDTO.getRegIp())
                .build();
        respCnt *= wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);

        /* 상생신청 스마트 상세 */
        wBFBRegisterDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
        respCnt *= wBFBRegisterCompanyMapper.putAppctnRsumeTaskDtl(wBFBRegisterDTO);

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
        wBFBRegisterSearchDTO.setSearchSqInfoList(wBFBRegisterCompanyMapper.getRegisterDtlSQ(wBFBRegisterSearchDTO));

        return wBFBRegisterSearchDTO;
    }

    /**
     *  Edit Page
     *  회차, 신청자, 부품사 정보 Get - 회차 등록 기본 등록 정보 조회
     */
    public WBFBRegisterSearchDTO getEditInfo(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {

        /* 선급금 관련 정보 - 지원금액상세 Table 조회 */
        wBFBRegisterSearchDTO.setSpprtDtlList(wBFBRegisterCompanyMapper.getSpprtDtlList(wBFBRegisterSearchDTO));

        /* 부품사 관리 등록 정보 - 진행 상세 Table 조회 */
        List<WBFBRsumeTaskDtlDTO> rsumeTaskDtlList = wBFBRegisterCompanyMapper.getRsumeTaskDtlList(wBFBRegisterSearchDTO);

        /* 상생 진행 스마트 상세 파일 정보 - 신청단계 첨부파일 Get */
        WBFBRsumeTaskDtlDTO rsumeTaskDTO = rsumeTaskDtlList.get(0);
        rsumeTaskDTO.setAppctnFileInfo(wBFBRegisterCompanyMapper.getDtlFileList(rsumeTaskDTO));
        rsumeTaskDtlList.set(0, rsumeTaskDTO);

        /* ReBuilding */
        wBFBRegisterSearchDTO.setRsumeTaskDtlList(rsumeTaskDtlList);

        return wBFBRegisterSearchDTO;
    }

    /**
     *  사업자등록번호 Check
     */
    public WBFBRegisterSearchDTO getBsnmNoCheck(WBFBRegisterSearchDTO wBFBRegisterSearchDTO) throws Exception {
        return wBFBRegisterCompanyMapper.getBsnmNoCheck(wBFBRegisterSearchDTO.getRsumeTaskDtl().getOfferBsnmNo());
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
     *  신청 부품사 정보 Update
     */
    @Transactional
    public int updRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {
        int respCnt = 1;

        /* 파일 상세 */
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBFBRegisterDTO.getFileList());

        if(wBFBRegisterDTO.getCtgryCd().equals("COMPANY01001")) {
            wBFBRegisterDTO.setQlty5starCd(null);
            wBFBRegisterDTO.setQlty5starYear(null);
            wBFBRegisterDTO.setPay5starCd(null);
            wBFBRegisterDTO.setPay5starYear(null);
            wBFBRegisterDTO.setTchlg5starCd(null);
            wBFBRegisterDTO.setTchlg5starYear(null);
        } else {
            /* 회사 상세 Update or Insert */
            for(WBCompanyDetailMstDTO wBCompanyDetailMstDTO : wBFBRegisterDTO.getSqInfoList()) {
                wBCompanyDetailMstDTO.setBsnmNo(wBFBRegisterDTO.getBsnmNo());
                if(!Objects.nonNull(wBCompanyDetailMstDTO.getCbsnSeq())){
                    /* cbsnSeq is null */
                    wBCompanyDetailMstDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                    wBCompanyDetailMstDTO.setRegId(wBFBRegisterDTO.getRegId());
                    wBCompanyDetailMstDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                    respCnt *= wBFBRegisterCompanyMapper.insCmpnCbsnDtl(wBCompanyDetailMstDTO);
                } else {
                    /* cbsnSeq is not null */
                    wBCompanyDetailMstDTO.setModId(wBFBRegisterDTO.getModId());
                    wBCompanyDetailMstDTO.setModIp(wBFBRegisterDTO.getModIp());
                    respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnDtl(wBCompanyDetailMstDTO);
                }
            }
        }

        /* 회사 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCmpnCbsnMst(wBFBRegisterDTO);

        /* 회원 마스터 Update */
        respCnt *= wBFBRegisterCompanyMapper.updCoMemMst(wBFBRegisterDTO);

        /* 신청자 정보 변경시 */
        if(!wBFBRegisterDTO.getBeforeMemSeq().equals(wBFBRegisterDTO.getMemSeq())) {
            WBAppctnTrnsfDtlDTO trnsfDtlDTO = new WBAppctnTrnsfDtlDTO();
            trnsfDtlDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
            trnsfDtlDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            trnsfDtlDTO.setBfreMemSeq(wBFBRegisterDTO.getBeforeMemSeq());
            trnsfDtlDTO.setAftrMemSeq(wBFBRegisterDTO.getMemSeq());
            trnsfDtlDTO.setRegId(wBFBRegisterDTO.getRegId());
            trnsfDtlDTO.setRegIp(wBFBRegisterDTO.getRegIp());
            respCnt *= wBFBRegisterCompanyMapper.insAppctnTrnsfDtl(trnsfDtlDTO);
        }


        // 진행 상태 코드
        COCodeDTO cOCodeDTO = new COCodeDTO();

        /* 지급 관리 DTO*/
        WBSpprtDtlDTO spprDTO = wBFBRegisterDTO.getSpprtDtl();

        /* 관리자 상태 값 List */
        cOCodeDTO.setCd(wBFBRegisterDTO.getNowSpprtCd() + "_02");
        List<COCodeDTO> mngCodeList = cOCodeService.getCdIdList(cOCodeDTO);
        /* 최종 관리자 최종 값 */
        String lastMngSttsCd = mngCodeList.get(mngCodeList.size()-1).getCd();
        /* 관리자 현재 값 Idx */
        int MngSttsCdIdx = 0;

        for(int idx=0; idx< mngCodeList.size(); idx++) {
            if(mngCodeList.get(idx).getCd().equals(spprDTO.getMngSttsCd())) {
                MngSttsCdIdx = idx;
            }
        }

        /* 사용자 상태 값 List */
        cOCodeDTO.setCd(wBFBRegisterDTO.getNowSpprtCd() + "_01");
        List<COCodeDTO> userCodeList = cOCodeService.getCdIdList(cOCodeDTO);
        /* 사용자 상태 매칭 값 */
        String appctnSttsCd = userCodeList.get(MngSttsCdIdx).getCd();
        /*사용자 상태 순서 관리자와 매칭*/
        spprDTO.setAppctnSttsCd(appctnSttsCd);

        /* 지급 관련 */
        /* 선급금 지급 전용 */
        if(wBFBRegisterDTO.getPmndvPmtYn().equals("Y")) {
            WBSpprtDtlDTO detailtSpprDTO = wBFBRegisterDTO.getDefaultSpprtDtl();
            detailtSpprDTO.setAppctnSttsCd(appctnSttsCd);
            detailtSpprDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
            detailtSpprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile1")); /*지원금신청서*/
            detailtSpprDTO.setAcntFileSeq(fileSeqMap.get("spprtAppctnFile2")); /*계좌이체약정서*/
            detailtSpprDTO.setBnkbkFileSeq(fileSeqMap.get("spprtAppctnFile3")); /*통장사본*/
            detailtSpprDTO.setModId(wBFBRegisterDTO.getModId());
            detailtSpprDTO.setModIp(wBFBRegisterDTO.getModIp());
            respCnt *= wBFBRegisterCompanyMapper.updSpprtDtl(detailtSpprDTO);
        }

        spprDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
        spprDTO.setModId(wBFBRegisterDTO.getModId());
        spprDTO.setModIp(wBFBRegisterDTO.getModIp());

        /* 지원금지급 */
        if(wBFBRegisterDTO.getNowSpprtCd().equals("PRO_TYPE03002")) {
            spprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile4")); /*지원금신청서*/
            spprDTO.setAcntFileSeq(fileSeqMap.get("spprtAppctnFile5")); /*계좌이체약정서*/
            spprDTO.setBnkbkFileSeq(fileSeqMap.get("spprtAppctnFile6")); /*통장사본*/

            /* 다음 단계 생성 */
            if(lastMngSttsCd.equals(spprDTO.getMngSttsCd())) {
                WBFBRegisterDTO putRegisterDto = new WBFBRegisterDTO();

                putRegisterDto.setGiveType("PRO_TYPE03003");
                putRegisterDto.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
                int firstAppctnSpprtDtlIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
                putRegisterDto.setAppctnSpprtSeq(firstAppctnSpprtDtlIdgen);
                putRegisterDto.setRegId(wBFBRegisterDTO.getRegId());
                putRegisterDto.setRegIp(wBFBRegisterDTO.getRegIp());
                respCnt *= wBFBRegisterCompanyMapper.putAppctnSpprtDtl(putRegisterDto);
            }
        }
        /* 기술임치 */
        if(wBFBRegisterDTO.getNowSpprtCd().equals("PRO_TYPE03003")) {
            spprDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFile7")); /*지원금신청서*/
            spprDTO.setTchlgLseFileSeq(fileSeqMap.get("spprtAppctnFile8")); /*기술임치증*/
            spprDTO.setLsePayFileSeq(fileSeqMap.get("spprtAppctnFile9")); /*임치료납입영수증*/
        }

        System.err.println(spprDTO);
        /* 지급관리 수정 */
        respCnt *= wBFBRegisterCompanyMapper.updSpprtDtl(spprDTO);

        /* -부품사 관리- */
        /* 부품사 DTO */
        WBFBRsumeTaskDtlDTO rsumeTaskDTO = wBFBRegisterDTO.getRsumeTaskDtl();

        /* 위원 값 신청 마스터 Update */
        if(rsumeTaskDTO.getChkCmssrSeq() != null && !rsumeTaskDTO.getChkCmssrSeq().toString().isEmpty()) {
            wBFBRegisterDTO.setPicCmssrSeq(rsumeTaskDTO.getChkCmssrSeq().toString());
        }
        /*상생신청 마스터*/
        respCnt *= wBFBRegisterCompanyMapper.updAppctnMst(wBFBRegisterDTO);

        /* 관리자 코드 리스트 */
        cOCodeDTO.setCd(wBFBRegisterDTO.getNowRsumeTaskCd() + "_02");
        mngCodeList = cOCodeService.getCdIdList(cOCodeDTO);
        /* 최종 관리자 최종 값 */
        lastMngSttsCd = mngCodeList.get(mngCodeList.size()-1).getCd();
        /* 관리자 현재 값 Idx */
        MngSttsCdIdx = 0;

        for(int idx=0; idx< mngCodeList.size(); idx++) {
            if(mngCodeList.get(idx).getCd().equals(rsumeTaskDTO.getMngSttsCd())) {
                MngSttsCdIdx = idx;
            }
        }

        /* 사용자 상태 값 List */
        cOCodeDTO.setCd(wBFBRegisterDTO.getNowRsumeTaskCd() + "_01");
        userCodeList = cOCodeService.getCdIdList(cOCodeDTO);
        /* 사용자 상태 매칭 값 */
        appctnSttsCd = userCodeList.get(MngSttsCdIdx).getCd();

        /*사용자 상태 순서 관리자와 매칭*/
        rsumeTaskDTO.setAppctnSttsCd(appctnSttsCd);
        rsumeTaskDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
        rsumeTaskDTO.setModIp(wBFBRegisterDTO.getModIp());
        rsumeTaskDTO.setModId(wBFBRegisterDTO.getModId());
        /* 신청진행상세 */
        respCnt *= wBFBRegisterCompanyMapper.updRsumeDtl(rsumeTaskDTO);

        /* 관리자 최종 상태 값인지 확인 */
        if(lastMngSttsCd.equals(rsumeTaskDTO.getMngSttsCd())) {
            cOCodeDTO.setCd("PRO_TYPE02"); // 스마트 공장 코드
            /* 단계 코드 리스트 */
            List<COCodeDTO> stateList = cOCodeService.getCdIdList(cOCodeDTO);
            List<COCodeDTO> selStatelist = stateList.stream().filter(ele -> ele.getDpth() == 3).collect(Collectors.toList());

            /* 다음 진행 상태 */
            String nextRsumeSttsCd = "";
            for (int codeIdx=0; codeIdx<selStatelist.size(); codeIdx++) {
                if (selStatelist.get(codeIdx).getCd().equals(wBFBRegisterDTO.getNowRsumeTaskCd())
                        && codeIdx != (selStatelist.size()-1)) {
                    nextRsumeSttsCd = selStatelist.get((codeIdx + 1)).getCd();
                }
            }

            /* 다음 진행 상태 값이 있을 경우*/
            if(!nextRsumeSttsCd.equals("")){

                /* 상생신청 진행상세 */
                WBFBRegisterDTO registerDTO = new WBFBRegisterDTO();
                int firstAppctnRsumeDtlIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen); /* 진행순번 */
                registerDTO.setRsumeOrd(rsumeTaskDTO.getRsumeOrd() + 1);
                registerDTO.setAppctnSeq(wBFBRegisterDTO.getAppctnSeq());
                registerDTO.setRsumeSttsCd(nextRsumeSttsCd);
                registerDTO.setAppctnSttsCd(nextRsumeSttsCd + "_01_001");
                registerDTO.setMngSttsCd(nextRsumeSttsCd + "_02_001");
                registerDTO.setRegId(wBFBRegisterDTO.getRegId());
                registerDTO.setRegIp(wBFBRegisterDTO.getRegIp());
                respCnt *= wBFBRegisterCompanyMapper.putAppctnRsumeDtl(registerDTO);

                /* 상생신청 스마트 상세 */
                registerDTO.setRsumeSeq(firstAppctnRsumeDtlIdgen);
                respCnt *= wBFBRegisterCompanyMapper.putInitNextAppctnRsumeTask(registerDTO);
            }
        }

        wBFBRegisterCompanyMapper.updRsumeTaskDtlSub(wBFBRegisterDTO);

        if(wBFBRegisterDTO.getNowRsumeTaskCd().equals("PRO_TYPE02001")){
            /* File Delect / Insert */
            respCnt *= wBFBRegisterCompanyMapper.delAppctnFileDtl(rsumeTaskDTO);
            List<WBRsumeFileDtlDTO> fileList = rsumeTaskDTO.getAppctnFileInfo();
            for(int fileIdx=0; fileIdx< fileList.size(); fileIdx++) {
                WBRsumeFileDtlDTO fileInfo = fileList.get(fileIdx);
                fileInfo.setRsumeSeq(rsumeTaskDTO.getRsumeSeq());
                fileInfo.setRsumeOrd(rsumeTaskDTO.getRsumeOrd());
                fileInfo.setFileCd(fileInfo.getType()); /*파일타입*/
                fileInfo.setFileSeq(fileSeqMap.get(fileInfo.getSeqNm())); /*파일 시퀀스*/
                fileInfo.setRegId(wBFBRegisterDTO.getRegId());
                fileInfo.setRegIp(wBFBRegisterDTO.getRegIp());
                respCnt *= wBFBRegisterCompanyMapper.putAppctnFileDtl(fileInfo);
            }
        }

        /* 상생신청진행스마트상세 Update*/
        respCnt *= wBFBRegisterCompanyMapper.updRsumeTaskDtl(rsumeTaskDTO);

        return respCnt;
    }

    /**
     *  신청 부품사 삭제
     */
    @Transactional
    public int deleteRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO) throws Exception {
        int respCnt = 1;

        respCnt *= wBFBRegisterCompanyMapper.delAppctnRsumeFileDtl(wBFBRegisterDTO);

        respCnt *= wBFBRegisterCompanyMapper.delAppctnRsumeTaskDtl(wBFBRegisterDTO);

        respCnt *= wBFBRegisterCompanyMapper.delAppctnTrnsfDtl(wBFBRegisterDTO);

        respCnt *= wBFBRegisterCompanyMapper.delAppctnSpprtDtl(wBFBRegisterDTO);

        respCnt *= wBFBRegisterCompanyMapper.delAppctnRsumeDtl(wBFBRegisterDTO);

        respCnt *= wBFBRegisterCompanyMapper.delAppctnMdst(wBFBRegisterDTO);

        return respCnt;
    }


}
