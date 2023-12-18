package com.kap.service.impl.wb.wbc;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.wbe.*;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBCBSecurityService;
import com.kap.service.dao.wb.wbc.WBCBSecurityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WBCBSecurityServiceImpl implements WBCBSecurityService {


    //Mapper
    private final WBCBSecurityMapper wBCBSecurityMapper;

    /* 상생사업관리 마스터 시퀀스 */
    private final EgovIdGnrService cxAppctnMstSeqIdgen;

    /* 상생사업관리 상세 시퀀스 */
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;

    /* SQ 시퀀스 */
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /* 지원금액 시퀀스 */
    private final EgovIdGnrService cxAppctnSpprtDtlIdgen;

    /* 참여이관로그 시퀀스 */
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;
    
    //파일 서비스
    private final COFileService cOFileService;

    /**
     * 신청 목록
     */
    public WBEBCarbonCompanySearchDTO selectCarbonCompanyList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBEBCarbonCompanySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBEBCarbonCompanySearchDTO.getListRowSize());

        page.setPageSize(wBEBCarbonCompanySearchDTO.getPageRowSize());

        wBEBCarbonCompanySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBEBCarbonCompanySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBEBCarbonCompanySearchDTO.setList(wBCBSecurityMapper.selectCarbonCompanyList(wBEBCarbonCompanySearchDTO));
        wBEBCarbonCompanySearchDTO.setTotalCount(wBCBSecurityMapper.getCarbonCompanyListTotCnt(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanySearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        return wBCBSecurityMapper.selectYearDtl(wBEBCarbonCompanySearchDTO);
    }

    /**
     * 회차 리스트 조회
     */
    public WBEBCarbonCompanySearchDTO getYearSelect(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        wBEBCarbonCompanySearchDTO.setEpisdList(wBCBSecurityMapper.getYearSelect(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanySearchDTO;
    }

    /**
     * 신청 상세 조회
     */
    public WBEBCarbonCompanyMstInsertDTO selectCarbonCompanyDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {

        //MST
        WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);

        //DTL
        wBEBCarbonCompanyMstInsertDTO.setRsumeDtlList(wBCBSecurityMapper.selectCarbonCompanyDtlDetail(wBEBCarbonCompanySearchDTO));

        wBEBCarbonCompanySearchDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        wBEBCarbonCompanySearchDTO.setBsnmNo(wBEBCarbonCompanyMstInsertDTO.getAppctnBsnmNo());

        //MEM
        wBEBCarbonCompanyMstInsertDTO.setMemList(wBCBSecurityMapper.selectCarbonCompanyMember(wBEBCarbonCompanySearchDTO));

        //CMSSR
        if(wBEBCarbonCompanyMstInsertDTO.getPicCmssrSeq() != null){
            wBEBCarbonCompanySearchDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getPicCmssrSeq());
            wBEBCarbonCompanyMstInsertDTO.getMemList().add(wBCBSecurityMapper.selectCarbonCompanyMember(wBEBCarbonCompanySearchDTO).get(0));
        }

        //COMPANY
        WBEBCompanyDTO wBEBCompanyList = wBCBSecurityMapper.selectCarbonCompany(wBEBCarbonCompanySearchDTO);
        wBEBCompanyList.setDtlList(new ArrayList<>());

        //SQ
        List<WBEBCompanyDtlDTO> wBEBCompanyDtlList = wBCBSecurityMapper.selectCarbonCompanySQ(wBEBCarbonCompanySearchDTO);

        //RSUME_PBSN_DTL
        wBEBCarbonCompanyMstInsertDTO.setPbsnDtlList(wBCBSecurityMapper.selectCarbonCompanyPbsn(wBEBCarbonCompanySearchDTO));
        for(WBEBCompanyDtlDTO wBEBCompanyDtlDTO : wBEBCompanyDtlList){
            wBEBCompanyList.getDtlList().add(wBEBCompanyDtlDTO);
        }
        wBEBCarbonCompanyMstInsertDTO.setCompanyDtl(wBEBCompanyList);

        //지원금액 조회
        List<WBEBCarbonCompanySpprtDTO> wBEBCarbonCompanySpprtDTO = wBCBSecurityMapper.selectAppctnSpprt(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanyMstInsertDTO.setSpprtList(wBEBCarbonCompanySpprtDTO);

        //file
        wBEBCarbonCompanyMstInsertDTO.setFileDtlList(wBCBSecurityMapper.selectFileDtl(wBEBCarbonCompanySearchDTO));

        //지급 차수 조회
        wBEBCarbonCompanyMstInsertDTO.setGiveOrdList(wBCBSecurityMapper.getGiveOrdList(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanyMstInsertDTO;
    }


    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBEBCarbonCompanyMstInsertDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyMstInsertDTO.setRegIp(coaAdmDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBEBCarbonCompanyMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBCBSecurityMapper.insertAppctnMst(wBEBCarbonCompanyMstInsertDTO);

        //신청 DTL
        WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO = new WBEBCarbonCompanyDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyDtlDTO.setRsumeOrd(1);
        wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

        WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO= wBEBCarbonCompanyMstInsertDTO.getPbsnDtlList().get(0);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBEBCarbonCompanyMstInsertDTO.getOptnFileList());

        WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = new WBEBCarbonCompanyFileDtlDTO();
        wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        wBEBCarbonCompanyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        wBCBSecurityMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);

        //회원
        MPAUserDto mPAUserDto= wBEBCarbonCompanyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnMember(mPAUserDto);

        //부품사
        WBEBCompanyDTO wBEBCompanyDTO= wBEBCarbonCompanyMstInsertDTO.getCompanyDtl();
        wBEBCompanyDTO.setModId(coaAdmDTO.getId());
        wBEBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnCompany(wBEBCompanyDTO);

        //부품사 SQ

        if(wBEBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBCBSecurityMapper.deleteCarbonCompanySQ(wBEBCompanyDTO);
            for(int i=0; i < wBEBCompanyDTO.getDtlList().size(); i++){
                WBEBCompanyDtlDTO wBEBCompanyDtlDTO = wBEBCompanyDTO.getDtlList().get(i);

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();

                wBEBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);

                wBCBSecurityMapper.insertCarbonCompanySQ(wBEBCompanyDtlDTO);
            }
        }

        //지원금
        for(int i=0; i < 2; i++){
            WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO = new WBEBCarbonCompanySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBEBCarbonCompanySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBEBCarbonCompanySpprtDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBEBCarbonCompanySpprtDTO.setGiveType("PRO_TYPE03001");
                wBEBCarbonCompanySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
            }else{
                wBEBCarbonCompanySpprtDTO.setGiveType("PRO_TYPE03003");
                wBEBCarbonCompanySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
            }

            wBEBCarbonCompanySpprtDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanySpprtDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnSpprt(wBEBCarbonCompanySpprtDTO);
        }

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int maxRsumeOrd = wBEBCarbonCompanyMstInsertDTO.getMaxRsumeOrd();
        int rsumeOrd = maxRsumeOrd - 1;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBEBCarbonCompanyMstInsertDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //참여 이관 추가
        WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO = new WBEBCarbonCompanySearchDTO();
        wBEBCarbonCompanySearchDTO.setDetailsKey(wBEBCarbonCompanyMstInsertDTO.getDetailsKey());
        WBEBCarbonCompanyMstInsertDTO wBEBTrnsfDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);

        if(wBEBTrnsfDTO.getMemSeq() != wBEBCarbonCompanyMstInsertDTO.getMemSeq())
        {
            int firstAppctnTrnsfDtlSeqIdgen = cxAppctnTrnsfDtlIdgen.getNextIntegerId();
            WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO = new WBEBCarbonCompanyTrnsfDTO();
            wBEBCarbonCompanyTrnsfDTO.setTrnsfSeq(firstAppctnTrnsfDtlSeqIdgen);
            wBEBCarbonCompanyTrnsfDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyTrnsfDTO.setBfreMemSeq(wBEBTrnsfDTO.getMemSeq());
            wBEBCarbonCompanyTrnsfDTO.setAftrMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
            wBEBCarbonCompanyTrnsfDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyTrnsfDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnTrnsf(wBEBCarbonCompanyTrnsfDTO);
        }

        //신청 MST ○
        respCnt = wBCBSecurityMapper.updateAppctnMst(wBEBCarbonCompanyMstInsertDTO);

        //신청 DTL ○
        WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO = wBEBCarbonCompanyMstInsertDTO.getRsumeDtlList().get(rsumeOrd);

        wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyDtlDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyDtlDTO.setModIp(coaAdmDTO.getLoginIp());

        wBCBSecurityMapper.updateAppctnDtl(wBEBCarbonCompanyDtlDTO);

        //Pbsn ○
        WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO = wBEBCarbonCompanyMstInsertDTO.getPbsnDtlList().get(rsumeOrd);
        if(wBEBCarbonCompanyPbsnDtlDTO.getBsnPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getBsnPmt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setBsnPmt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getBsnPlanDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getBsnPlanDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setBsnPlanDt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getSpprtPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getSpprtPmt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setSpprtPmt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getPhswPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getPhswPmt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setPhswPmt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getTtlPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getTtlPmt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setTtlPmt(null);
        }

        wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyPbsnDtlDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBEBCarbonCompanyMstInsertDTO.getFileList());

        WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = wBEBCarbonCompanyMstInsertDTO.getFileDtlList().get(rsumeOrd);
        wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"+rsumeOrd));
        wBEBCarbonCompanyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);

        //회원○
        MPAUserDto mPAUserDto= wBEBCarbonCompanyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnMember(mPAUserDto);

        //부품사○
        WBEBCompanyDTO wBEBCompanyDTO= wBEBCarbonCompanyMstInsertDTO.getCompanyDtl();
        wBEBCompanyDTO.setModId(coaAdmDTO.getId());
        wBEBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnCompany(wBEBCompanyDTO);

        //부품사 SQ○
        if(wBEBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBCBSecurityMapper.deleteCarbonCompanySQ(wBEBCompanyDTO);
            for(int i=0; i < wBEBCompanyDTO.getDtlList().size(); i++){
                WBEBCompanyDtlDTO wBEBCompanyDtlDTO = wBEBCompanyDTO.getDtlList().get(i);

                if(wBEBCompanyDtlDTO.getYear() == null || wBEBCompanyDtlDTO.getYear().equals("")){
                    wBEBCompanyDtlDTO.setYear(null);
                }

                if(wBEBCompanyDtlDTO.getScore() == null || wBEBCompanyDtlDTO.getScore().equals("")){
                    wBEBCompanyDtlDTO.setScore(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();
                wBEBCompanyDtlDTO.setBsnmNo(wBEBCompanyDTO.getBsnmNo());
                wBEBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);
                wBCBSecurityMapper.insertCarbonCompanySQ(wBEBCompanyDtlDTO);
            }
        }


        //지급금 관련
        for(int i=0; i < wBEBCarbonCompanyMstInsertDTO.getSpprtList().size(); i++){
            //상생 신청 파일 상세
            WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO = wBEBCarbonCompanyMstInsertDTO.getSpprtList().get(i);
            wBEBCarbonCompanySpprtDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            if(i == 0){
                //지원금신청서
                wBEBCarbonCompanySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq"));
                //협약서
                wBEBCarbonCompanySpprtDTO.setAgrmtFileSeq(fileSeqMap.get("agrmtFileSeq"));
                //보증보험증
                wBEBCarbonCompanySpprtDTO.setGrnteInsrncFileSeq(fileSeqMap.get("grnteInsrncFileSeq"));
            }else{
                //지원금신청서
                wBEBCarbonCompanySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq1"));
                //거래명세서
                wBEBCarbonCompanySpprtDTO.setBlingFileSeq(fileSeqMap.get("blingFileSeq"));
                //매출전표
                wBEBCarbonCompanySpprtDTO.setSlsFileSeq(fileSeqMap.get("slsFileSeq"));
                //검수확인서
                wBEBCarbonCompanySpprtDTO.setInsptChkFileSeq(fileSeqMap.get("insptChkFileSeq"));
            }

            wBEBCarbonCompanySpprtDTO.setModId(coaAdmDTO.getId());
            wBEBCarbonCompanySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBEBCarbonCompanySpprtDTO.getAccsDt() != "" ){
                wBCBSecurityMapper.updateAppctnSpprt(wBEBCarbonCompanySpprtDTO);
            }
        }


        //최초점검, 최종점검, 검수보고 경우 추가 입력 확인
        int firstAppctnRsumeDtlSeqIdgen = 0;

        //신청 적합
        if(maxRsumeOrd == 1 && "PRO_TYPE01001_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01002");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01002_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01002_02_001");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //사업계획 적합
        else if(maxRsumeOrd == 2 && "PRO_TYPE01002_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01003");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01003_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01003_02_001");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //최초점검 적합
        else if(maxRsumeOrd == 3 && "PRO_TYPE01003_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01004");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01004_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01004_02_001");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //완료 보고 적합
        else if(maxRsumeOrd == 4 && "PRO_TYPE01004_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01005");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01005_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01005_02_001");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //최종점검 적합
        else if(maxRsumeOrd == 5 && "PRO_TYPE01005_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01006");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01006_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01006_02_001");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }


        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {


        List<String> appctnSeqList = wBEBCarbonCompanySearchDTO.getDelValueList();

        wBCBSecurityMapper.carbonCompanyDeleteTrnsf(wBEBCarbonCompanySearchDTO);
        wBCBSecurityMapper.carbonCompanyDeleteSpprt(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanySearchDTO.setDelValueList(wBCBSecurityMapper.selectRsumeSeq(wBEBCarbonCompanySearchDTO));

        wBCBSecurityMapper.carbonCompanyDeletePbsn(wBEBCarbonCompanySearchDTO);
        wBCBSecurityMapper.carbonCompanyDeleteRsumeFile(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanySearchDTO.setDelValueList(appctnSeqList);

        wBCBSecurityMapper.carbonCompanyDeleteRsume(wBEBCarbonCompanySearchDTO);
        int respCnt = wBCBSecurityMapper.carbonCompanyDeleteMst(wBEBCarbonCompanySearchDTO);

        return respCnt;

    }

    /**
     * 이관 로그 조회
     */
    public WBEBCarbonCompanyTrnsfDTO getTrnsfList(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBEBCarbonCompanyTrnsfDTO.getPageIndex());
        page.setRecordCountPerPage(wBEBCarbonCompanyTrnsfDTO.getListRowSize());

        page.setPageSize(wBEBCarbonCompanyTrnsfDTO.getPageRowSize());

        wBEBCarbonCompanyTrnsfDTO.setFirstIndex(page.getFirstRecordIndex());
        wBEBCarbonCompanyTrnsfDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBEBCarbonCompanyTrnsfDTO.setList(wBCBSecurityMapper.getTrnsfList(wBEBCarbonCompanyTrnsfDTO));
        wBEBCarbonCompanyTrnsfDTO.setTotalCount(wBCBSecurityMapper.getTrnsfListTotCnt(wBEBCarbonCompanyTrnsfDTO));

        return wBEBCarbonCompanyTrnsfDTO;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("사업구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);


        cell = row.createCell(5);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("종된사업장번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("대표자명");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("담당자명");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("진행상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("관리자상태값");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("최초점검");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("최종점검");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("지원금");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("신청사업비");
        cell.setCellStyle(style_header);

        cell = row.createCell(20);
        cell.setCellValue("자부담");
        cell.setCellStyle(style_header);

        cell = row.createCell(21);
        cell.setCellValue("총계");
        cell.setCellStyle(style_header);

        cell = row.createCell(22);
        cell.setCellValue("계좌번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(23);
        cell.setCellValue("은행");
        cell.setCellStyle(style_header);

        cell = row.createCell(24);
        cell.setCellValue("예금주");
        cell.setCellStyle(style_header);

        cell = row.createCell(25);
        cell.setCellValue("사업비 지급일");
        cell.setCellStyle(style_header);

        cell = row.createCell(26);
        cell.setCellValue("관리자 메모");
        cell.setCellStyle(style_header);

        cell = row.createCell(27);
        cell.setCellValue("사용자 수정일");
        cell.setCellStyle(style_header);

        // Body
        List<WBEBCarbonCompanySearchDTO> list = wBEBCarbonCompanySearchDTO.getList();

        List<WBEBCarbonCompanyPbsnDtlDTO> pbsnList = null;

        int maxOrd = 0;
        int length = 0;

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);
            pbsnList = wBCBSecurityMapper.selectExcelPbsn(wBEBCarbonCompanySearchDTO.getList().get(i));
            length = pbsnList.size();
            maxOrd = pbsnList.get(length-1).getMaxRsumeOrd();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBEBCarbonCompanySearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //회차
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getEpisd());
            cell.setCellStyle(style_body);

            //사업구분
            cell = row.createCell(3);
            cell.setCellValue("보안환경구축");
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getBsnmNo().substring(0,3)+"-"+list.get(i).getBsnmNo().substring(3,5)+"-"+list.get(i).getBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //종된 사업장번호
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getSbrdnBsnmNo().substring(0,3)+"-"+list.get(i).getSbrdnBsnmNo().substring(3,5)+"-"+list.get(i).getSbrdnBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //대표자명
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getRprsntNm());
            cell.setCellStyle(style_body);

            //담당자명
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getName());
            cell.setCellStyle(style_body);

            //휴대폰번호
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getPicCmssrNm());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getRsumeSttsNm());
            cell.setCellStyle(style_body);

            //관리자 상태값
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getMngSttsNm());
            cell.setCellStyle(style_body);

            //최초점검
            cell = row.createCell(16);
            if(maxOrd >= 3){
                cell.setCellValue(pbsnList.get(2).getChkDt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //최종점검
            cell = row.createCell(17);
            if(maxOrd >= 5){
                cell.setCellValue(pbsnList.get(4).getLastChkDt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);



            //지원금
            cell = row.createCell(18);
            if(maxOrd >= 2){
                cell.setCellValue(pbsnList.get(1).getSpprtPmt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //신청사업비
            cell = row.createCell(19);
            if(maxOrd >= 1){
                cell.setCellValue(pbsnList.get(0).getBsnPmt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //자부담
            cell = row.createCell(20);
            if(maxOrd >= 2){
                cell.setCellValue(pbsnList.get(1).getPhswPmt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //총계
            cell = row.createCell(21);
            if(maxOrd >= 2){
                cell.setCellValue(pbsnList.get(1).getTtlPmt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //계좌번호
            cell = row.createCell(22);
            cell.setCellValue(list.get(i).getAcntNo());
            cell.setCellStyle(style_body);

            //은행
            cell = row.createCell(23);
            cell.setCellValue(list.get(i).getBankNm());
            cell.setCellStyle(style_body);

            //예금주
            cell = row.createCell(24);
            cell.setCellValue(list.get(i).getDpsitNm());
            cell.setCellStyle(style_body);

            //사업비 지급일
            cell = row.createCell(25);
            cell.setCellValue(list.get(i).getGiveDt());
            cell.setCellStyle(style_body);

            //관리자 메모
            cell = row.createCell(26);
            cell.setCellValue(list.get(i).getAdmMemo());
            cell.setCellStyle(style_body);

            //사용자 수정일
            cell = row.createCell(27);
            cell.setCellValue(list.get(i).getAppctnSttsChngDtm());
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
}