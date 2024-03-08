package com.kap.service.impl.wb.wbd;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.WBSendDTO;
import com.kap.core.dto.wb.wbd.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBDBSafetyService;
import com.kap.service.dao.wb.wbd.WBDBSafetyMapper;
import com.kap.service.impl.wb.wbb.WBBBCompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class WBDBSafetyServiceImpl implements WBDBSafetyService {


    //Mapper
    private final WBDBSafetyMapper wBDBSafetyMapper;

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
    private final COFileUtil cOFileUtil;
    private final COFileService cOFileService;
    private final WBBBCompanyServiceImpl wbbbCompanyService;

    /**
     * 신청 목록
     */
    public WBDBSafetySearchDTO selectCarbonCompanyList(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBDBSafetySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBDBSafetySearchDTO.getListRowSize());

        page.setPageSize(wBDBSafetySearchDTO.getPageRowSize());

        wBDBSafetySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBDBSafetySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBDBSafetySearchDTO.setList(wBDBSafetyMapper.selectCarbonCompanyList(wBDBSafetySearchDTO));
        wBDBSafetySearchDTO.setTotalCount(wBDBSafetyMapper.getCarbonCompanyListTotCnt(wBDBSafetySearchDTO));

        return wBDBSafetySearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {
        return wBDBSafetyMapper.selectYearDtl(wBDBSafetySearchDTO);
    }

    /**
     * 회차 리스트 조회
     */
    public WBDBSafetySearchDTO getYearSelect(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {
        wBDBSafetySearchDTO.setEpisdList(wBDBSafetyMapper.getYearSelect(wBDBSafetySearchDTO));

        return wBDBSafetySearchDTO;
    }

    /**
     * 신청 상세 조회
     */
    public WBDBSafetyMstInsertDTO selectCarbonCompanyDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {

        //MST
        WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO = wBDBSafetyMapper.selectCarbonCompanyDtl(wBDBSafetySearchDTO);

        //DTL
        wBDBSafetyMstInsertDTO.setRsumeDtlList(wBDBSafetyMapper.selectCarbonCompanyDtlDetail(wBDBSafetySearchDTO));

        wBDBSafetySearchDTO.setMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
        wBDBSafetySearchDTO.setBsnmNo(wBDBSafetyMstInsertDTO.getAppctnBsnmNo());

        //MEM
        wBDBSafetyMstInsertDTO.setMemList(wBDBSafetyMapper.selectCarbonCompanyMember(wBDBSafetySearchDTO));

        //CMSSR
        if(wBDBSafetyMstInsertDTO.getPicCmssrSeq() != null){
            wBDBSafetySearchDTO.setMemSeq(wBDBSafetyMstInsertDTO.getPicCmssrSeq());

            wBDBSafetyMstInsertDTO.setIsttrDtl(wBDBSafetyMapper.selectIsttrDtl(wBDBSafetyMstInsertDTO));
        }

        //COMPANY
        WBDBCompanyDTO wBEBCompanyList = wBDBSafetyMapper.selectCarbonCompany(wBDBSafetySearchDTO);
        wBEBCompanyList.setDtlList(new ArrayList<>());

        //SQ
        List<WBDBCompanyDtlDTO> wBEBCompanyDtlList = wBDBSafetyMapper.selectCarbonCompanySQ(wBDBSafetySearchDTO);

        //RSUME_PBSN_DTL
        wBDBSafetyMstInsertDTO.setPbsnDtlList(wBDBSafetyMapper.selectCarbonCompanyPbsn(wBDBSafetySearchDTO));
        for(WBDBCompanyDtlDTO wBDBCompanyDtlDTO : wBEBCompanyDtlList){
            wBEBCompanyList.getDtlList().add(wBDBCompanyDtlDTO);
        }
        wBDBSafetyMstInsertDTO.setCompanyDtl(wBEBCompanyList);

        //지원금액 조회
        List<WBDBSafetySpprtDTO> wBDBSafetySpprtDTO = wBDBSafetyMapper.selectAppctnSpprt(wBDBSafetySearchDTO);

        wBDBSafetyMstInsertDTO.setSpprtList(wBDBSafetySpprtDTO);

        //file
        wBDBSafetyMstInsertDTO.setFileDtlList(wBDBSafetyMapper.selectFileDtl(wBDBSafetySearchDTO));

        //지급 차수 조회
        wBDBSafetyMstInsertDTO.setGiveOrdList(wBDBSafetyMapper.getGiveOrdList(wBDBSafetySearchDTO));

        return wBDBSafetyMstInsertDTO;
    }


    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        wBDBSafetyMstInsertDTO.setEpisdSeq(wBDBSafetyMapper.selectEpisdSeq(wBDBSafetyMstInsertDTO));

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBDBSafetyMstInsertDTO.setRegId(coaAdmDTO.getId());
        wBDBSafetyMstInsertDTO.setRegIp(coaAdmDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBDBSafetyMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBDBSafetyMapper.insertAppctnMst(wBDBSafetyMstInsertDTO);

        //신청 DTL
        WBDBSafetyDtlDTO wBDBSafetyDtlDTO = new WBDBSafetyDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
        wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
        wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyDtlDTO.setRsumeOrd(1);
        wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

        WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO= wBDBSafetyMstInsertDTO.getPbsnDtlList().get(0);
        wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyPbsnDtlDTO.setRsumeOrd(1);
        wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
        wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBDBSafetyMstInsertDTO.getOptnFileList());

        WBDBSafetyFileDtlDTO wBDBSafetyFileDtlDTO = new WBDBSafetyFileDtlDTO();
        wBDBSafetyFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyFileDtlDTO.setRsumeOrd(1);
        wBDBSafetyFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBDBSafetyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        wBDBSafetyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBDBSafetyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        wBDBSafetyMapper.insertAppctnFileDtl(wBDBSafetyFileDtlDTO);

        //회원
        MPAUserDto mPAUserDto= wBDBSafetyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.updateAppctnMember(mPAUserDto);

        //부품사
        WBDBCompanyDTO wBDBCompanyDTO= wBDBSafetyMstInsertDTO.getCompanyDtl();
        wBDBCompanyDTO.setModId(coaAdmDTO.getId());
        wBDBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.updateAppctnCompany(wBDBCompanyDTO);

        //부품사 SQ

        if(wBDBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBDBSafetyMapper.deleteCarbonCompanySQ(wBDBCompanyDTO);
            for(int i=0; i < wBDBCompanyDTO.getDtlList().size(); i++){
                WBDBCompanyDtlDTO wBDBCompanyDtlDTO = wBDBCompanyDTO.getDtlList().get(i);

                if(wBDBCompanyDtlDTO.getYear() == null || wBDBCompanyDtlDTO.getYear().equals("")){
                    wBDBCompanyDtlDTO.setYear(null);
                }
                if(wBDBCompanyDtlDTO.getScore() == null || wBDBCompanyDtlDTO.getScore().equals("")){
                    wBDBCompanyDtlDTO.setScore(null);
                }
                if(wBDBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBDBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBDBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();

                wBDBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);

                wBDBSafetyMapper.insertCarbonCompanySQ(wBDBCompanyDtlDTO);
            }
        }

        //지원금
        for(int i=0; i < 2; i++){
            WBDBSafetySpprtDTO wBDBSafetySpprtDTO = new WBDBSafetySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBDBSafetySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBDBSafetySpprtDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBDBSafetySpprtDTO.setGiveType("PRO_TYPE03001");
                wBDBSafetySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
                wBDBSafetySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBDBSafetySpprtDTO.setGiveType("PRO_TYPE03002");
                wBDBSafetySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBDBSafetySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBDBSafetySpprtDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetySpprtDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBDBSafetyMapper.insertAppctnSpprt(wBDBSafetySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBDBSafetyMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int maxRsumeOrd = wBDBSafetyMstInsertDTO.getMaxRsumeOrd();
        int rsumeOrd = maxRsumeOrd - 1;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBDBSafetyMstInsertDTO.setModId(coaAdmDTO.getId());
        wBDBSafetyMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //참여 이관 추가
        WBDBSafetySearchDTO wBDBSafetySearchDTO = new WBDBSafetySearchDTO();
        wBDBSafetySearchDTO.setDetailsKey(wBDBSafetyMstInsertDTO.getDetailsKey());
        WBDBSafetyMstInsertDTO wBEBTrnsfDTO = wBDBSafetyMapper.selectCarbonCompanyDtl(wBDBSafetySearchDTO);

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBDBSafetyMstInsertDTO.getMemSeq()))
        {
            int firstAppctnTrnsfDtlSeqIdgen = cxAppctnTrnsfDtlIdgen.getNextIntegerId();
            WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO = new WBDBSafetyTrnsfDTO();
            wBDBSafetyTrnsfDTO.setTrnsfSeq(firstAppctnTrnsfDtlSeqIdgen);
            wBDBSafetyTrnsfDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyTrnsfDTO.setBfreMemSeq(wBEBTrnsfDTO.getMemSeq());
            wBDBSafetyTrnsfDTO.setAftrMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
            wBDBSafetyTrnsfDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyTrnsfDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBDBSafetyMapper.insertAppctnTrnsf(wBDBSafetyTrnsfDTO);
        }

        //신청 MST ○
        respCnt = wBDBSafetyMapper.updateAppctnMst(wBDBSafetyMstInsertDTO);

        //신청 DTL ○
        WBDBSafetyDtlDTO wBDBSafetyDtlDTO = wBDBSafetyMstInsertDTO.getRsumeDtlList().get(rsumeOrd);

        wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
        wBDBSafetyDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
        wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBDBSafetyDtlDTO.setModId(coaAdmDTO.getId());
        wBDBSafetyDtlDTO.setModIp(coaAdmDTO.getLoginIp());

        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBDBSafetyMstInsertDTO.getEpisdSeq());
        wbSendDTO.setStageNm(wBDBSafetyMstInsertDTO.getStageNm());
        wbSendDTO.setReason(wBDBSafetyDtlDTO.getRtrnRsnCntn());
        wbSendDTO.setRsumeSeq(wBDBSafetyDtlDTO.getRsumeSeq());
        wbSendDTO.setRsumeOrd(wBDBSafetyDtlDTO.getRsumeOrd());
        wbSendDTO.setMngSttdCd(wBDBSafetyDtlDTO.getMngSttsCd());

        //EDM,SMS발송
        if("PRO_TYPE01001_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_003".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_003".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_003".equals(wBDBSafetyDtlDTO.getMngSttsCd())) {
            //선정
            wbbbCompanyService.send(wbSendDTO,"SMS05");
        } else if ("PRO_TYPE01001_02_004".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_004".equals(wBDBSafetyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_004".equals(wBDBSafetyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd())) {
            //미선정 및 부적합
            wbbbCompanyService.send(wbSendDTO,"SMS07");
        } else if ("PRO_TYPE01001_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01004_02_002".equals(wBDBSafetyDtlDTO.getMngSttsCd())) {
            //보완요청
            wbbbCompanyService.send(wbSendDTO,"SMS06");
        }

        wBDBSafetyMapper.updateAppctnDtl(wBDBSafetyDtlDTO);

        //Pbsn ○
        WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO = wBDBSafetyMstInsertDTO.getPbsnDtlList().get(rsumeOrd);
        if(wBDBSafetyPbsnDtlDTO.getBsnPmt() == null || wBDBSafetyPbsnDtlDTO.getBsnPmt().equals("")){
            wBDBSafetyPbsnDtlDTO.setBsnPmt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getBsnPlanDt() == null || wBDBSafetyPbsnDtlDTO.getBsnPlanDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setBsnPlanDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getSpprtPmt() == null || wBDBSafetyPbsnDtlDTO.getSpprtPmt().equals("")){
            wBDBSafetyPbsnDtlDTO.setSpprtPmt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getPhswPmt() == null || wBDBSafetyPbsnDtlDTO.getPhswPmt().equals("")){
            wBDBSafetyPbsnDtlDTO.setPhswPmt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getTtlPmt() == null || wBDBSafetyPbsnDtlDTO.getTtlPmt().equals("")){
            wBDBSafetyPbsnDtlDTO.setTtlPmt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getChkDt() == null || wBDBSafetyPbsnDtlDTO.getChkDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setChkDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getExamScore() == null || wBDBSafetyPbsnDtlDTO.getExamScore().equals("")){
            wBDBSafetyPbsnDtlDTO.setExamScore(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getPayDt() == null || wBDBSafetyPbsnDtlDTO.getPayDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setPayDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getNslltSchdlDt() == null || wBDBSafetyPbsnDtlDTO.getNslltSchdlDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setNslltSchdlDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getAccsDt() == null || wBDBSafetyPbsnDtlDTO.getAccsDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setAccsDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getGiveSeq() == null || wBDBSafetyPbsnDtlDTO.getGiveSeq().equals("")){
            wBDBSafetyPbsnDtlDTO.setGiveSeq(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getCmpltnBrfngDt() == null || wBDBSafetyPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setCmpltnBrfngDt(null);
        }
        if(wBDBSafetyPbsnDtlDTO.getLastChkDt() == null || wBDBSafetyPbsnDtlDTO.getLastChkDt().equals("")){
            wBDBSafetyPbsnDtlDTO.setLastChkDt(null);
        }

        wBDBSafetyPbsnDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
        wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBDBSafetyPbsnDtlDTO.setModId(coaAdmDTO.getId());
        wBDBSafetyPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.updateAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBDBSafetyMstInsertDTO.getFileList());

        WBDBSafetyFileDtlDTO wBDBSafetyFileDtlDTO = wBDBSafetyMstInsertDTO.getFileDtlList().get(rsumeOrd);
        wBDBSafetyFileDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
        wBDBSafetyFileDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBDBSafetyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"+rsumeOrd));
        wBDBSafetyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBDBSafetyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.insertAppctnFileDtl(wBDBSafetyFileDtlDTO);

        //회원○
        MPAUserDto mPAUserDto= wBDBSafetyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.updateAppctnMember(mPAUserDto);

        //부품사○
        WBDBCompanyDTO wBDBCompanyDTO= wBDBSafetyMstInsertDTO.getCompanyDtl();
        wBDBCompanyDTO.setModId(coaAdmDTO.getId());
        wBDBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBDBSafetyMapper.updateAppctnCompany(wBDBCompanyDTO);

        //부품사 SQ○
        if(wBDBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBDBSafetyMapper.deleteCarbonCompanySQ(wBDBCompanyDTO);
            for(int i=0; i < wBDBCompanyDTO.getDtlList().size(); i++){
                WBDBCompanyDtlDTO wBDBCompanyDtlDTO = wBDBCompanyDTO.getDtlList().get(i);

                if(wBDBCompanyDtlDTO.getYear() == null || wBDBCompanyDtlDTO.getYear().equals("")){
                    wBDBCompanyDtlDTO.setYear(null);
                }
                if(wBDBCompanyDtlDTO.getScore() == null || wBDBCompanyDtlDTO.getScore().equals("")){
                    wBDBCompanyDtlDTO.setScore(null);
                }
                if(wBDBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBDBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBDBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();
                wBDBCompanyDtlDTO.setBsnmNo(wBDBCompanyDTO.getBsnmNo());
                wBDBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);
                wBDBSafetyMapper.insertCarbonCompanySQ(wBDBCompanyDtlDTO);
            }
        }


        //지급금 관련
        for(int i=0; i < wBDBSafetyMstInsertDTO.getSpprtList().size(); i++){
            //상생 신청 파일 상세
            WBDBSafetySpprtDTO wBDBSafetySpprtDTO = wBDBSafetyMstInsertDTO.getSpprtList().get(i);
            wBDBSafetySpprtDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            if(i == 0){
                //지원금신청서
                wBDBSafetySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq"));
                //협약서
                wBDBSafetySpprtDTO.setAgrmtFileSeq(fileSeqMap.get("agrmtFileSeq"));
                //보증보험증
                wBDBSafetySpprtDTO.setGrnteInsrncFileSeq(fileSeqMap.get("grnteInsrncFileSeq"));
            }else{
                //지원금신청서
                wBDBSafetySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq1"));
                //거래명세서
                wBDBSafetySpprtDTO.setBlingFileSeq(fileSeqMap.get("blingFileSeq"));
                //매출전표
                wBDBSafetySpprtDTO.setSlsFileSeq(fileSeqMap.get("slsFileSeq"));
                //검수확인서
                wBDBSafetySpprtDTO.setInsptChkFileSeq(fileSeqMap.get("insptChkFileSeq"));
            }

            wBDBSafetySpprtDTO.setModId(coaAdmDTO.getId());
            wBDBSafetySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBDBSafetySpprtDTO.getGiveDt() != "" ){
                wBDBSafetyMapper.updateAppctnSpprt(wBDBSafetySpprtDTO);
            }
        }


        //최초점검, 최종점검, 검수보고 경우 추가 입력 확인
        int firstAppctnRsumeDtlSeqIdgen = 0;

        //신청 적합
        if(maxRsumeOrd == 1 && "PRO_TYPE01001_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01002");
            wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01002_01_001");
            wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01002_02_006");
            wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

            wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);
        }
        //사업계획 적합
        else if(maxRsumeOrd == 2 && "PRO_TYPE01002_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01003");
            wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01003_01_001");
            wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01003_02_001");
            wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

            wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);
        }
        //최초점검 적합
        else if(maxRsumeOrd == 3 && "PRO_TYPE01003_02_003".equals(wBDBSafetyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01004");
            wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01004_01_001");
            wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01004_02_006");
            wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

            wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);
        }
        //완료 보고 적합
        else if(maxRsumeOrd == 4 && "PRO_TYPE01004_02_005".equals(wBDBSafetyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01005");
            wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01005_01_001");
            wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01005_02_001");
            wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

            wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);
        }
        //최종점검 적합
        else if(maxRsumeOrd == 5 && "PRO_TYPE01005_02_003".equals(wBDBSafetyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01006");
            wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01006_01_001");
            wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01006_02_004");
            wBDBSafetyDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

            wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBDBSafetyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);
        }


        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {


        List<String> appctnSeqList = wBDBSafetySearchDTO.getDelValueList();

        wBDBSafetyMapper.carbonCompanyDeleteTrnsf(wBDBSafetySearchDTO);
        wBDBSafetyMapper.carbonCompanyDeleteSpprt(wBDBSafetySearchDTO);

        wBDBSafetySearchDTO.setDelValueList(wBDBSafetyMapper.selectRsumeSeq(wBDBSafetySearchDTO));

        wBDBSafetyMapper.carbonCompanyDeletePbsn(wBDBSafetySearchDTO);
        wBDBSafetyMapper.carbonCompanyDeleteRsumeFile(wBDBSafetySearchDTO);

        wBDBSafetySearchDTO.setDelValueList(appctnSeqList);

        wBDBSafetyMapper.carbonCompanyDeleteRsume(wBDBSafetySearchDTO);
        int respCnt = wBDBSafetyMapper.carbonCompanyDeleteMst(wBDBSafetySearchDTO);

        return respCnt;

    }

    /**
     * 이관 로그 조회
     */
    public WBDBSafetyTrnsfDTO getTrnsfList(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBDBSafetyTrnsfDTO.getPageIndex());
        page.setRecordCountPerPage(wBDBSafetyTrnsfDTO.getListRowSize());

        page.setPageSize(wBDBSafetyTrnsfDTO.getPageRowSize());

        wBDBSafetyTrnsfDTO.setFirstIndex(page.getFirstRecordIndex());
        wBDBSafetyTrnsfDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBDBSafetyTrnsfDTO.setList(wBDBSafetyMapper.getTrnsfList(wBDBSafetyTrnsfDTO));
        wBDBSafetyTrnsfDTO.setTotalCount(wBDBSafetyMapper.getTrnsfListTotCnt(wBDBSafetyTrnsfDTO));

        return wBDBSafetyTrnsfDTO;
    }

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBDBSafetyMapper.getRsumePbsnCnt(wBDBSafetySearchDTO);

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBDBSafetySearchDTO wBDBSafetySearchDTO, HttpServletResponse response) throws Exception {
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
        List<WBDBSafetySearchDTO> list = wBDBSafetySearchDTO.getList();

        List<WBDBSafetyPbsnDtlDTO> pbsnList = null;

        int maxOrd = 0;
        int length = 0;

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);
            pbsnList = wBDBSafetyMapper.selectExcelPbsn(wBDBSafetySearchDTO.getList().get(i));

            length = pbsnList.size();

            maxOrd = pbsnList.get(length-1).getMaxRsumeOrd();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBDBSafetySearchDTO.getTotalCount() - i);
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
            cell.setCellValue("탄소배출저감");
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
            cell.setCellValue(list.get(i).getSbrdnBsnmNo());
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


    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBDBCompanyDTO selectCompanyUserDtl(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {

        WBDBCompanyDTO wBDBCompanyDTO = new WBDBCompanyDTO();

        wBDBCompanyDTO = wBDBSafetyMapper.getCompanyInfo(wBDBSafetySearchDTO);
        List<WBDBCompanyDtlDTO> sqList = wBDBSafetyMapper.selectSqList(wBDBSafetySearchDTO);
        wBDBCompanyDTO.setDtlList(sqList);
        wBDBCompanyDTO.setMemSeq(wBDBSafetySearchDTO.getMemSeq());
        wBDBCompanyDTO.setEpisdSeq(wBDBSafetySearchDTO.getEpisdSeq());

        return wBDBCompanyDTO;
    }


    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;
        cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        wBDBSafetyMstInsertDTO.setRegId(cOUserDetailsDTO.getId());
        wBDBSafetyMstInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBDBSafetyMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBDBSafetyMapper.insertAppctnMst(wBDBSafetyMstInsertDTO);

        //신청 DTL
        WBDBSafetyDtlDTO wBDBSafetyDtlDTO = new WBDBSafetyDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
        wBDBSafetyDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBDBSafetyDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBDBSafetyDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBDBSafetyDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBDBSafetyDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBDBSafetyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyDtlDTO.setRsumeOrd(1);
        wBDBSafetyMapper.insertAppctnDtl(wBDBSafetyDtlDTO);

        WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO= wBDBSafetyMstInsertDTO.getPbsnDtlList().get(0);
        wBDBSafetyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyPbsnDtlDTO.setRsumeOrd(1);
        wBDBSafetyPbsnDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBDBSafetyPbsnDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        wBDBSafetyMapper.insertAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBDBSafetyMstInsertDTO.getFileList());

        WBDBSafetyFileDtlDTO wBDBSafetyFileDtlDTO = new WBDBSafetyFileDtlDTO();
        wBDBSafetyFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBDBSafetyFileDtlDTO.setRsumeOrd(1);
        wBDBSafetyFileDtlDTO.setFileSeq(fileSeqMap.get("atchFile"));
        wBDBSafetyFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBDBSafetyFileDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBDBSafetyFileDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        wBDBSafetyMapper.insertAppctnFileDtl(wBDBSafetyFileDtlDTO);

        //지원금
        for(int i=0; i < 2; i++){
            WBDBSafetySpprtDTO wBDBSafetySpprtDTO = new WBDBSafetySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBDBSafetySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBDBSafetySpprtDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBDBSafetySpprtDTO.setGiveType("PRO_TYPE03001");
                wBDBSafetySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
                wBDBSafetySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBDBSafetySpprtDTO.setGiveType("PRO_TYPE03002");
                wBDBSafetySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBDBSafetySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBDBSafetySpprtDTO.setRegId(cOUserDetailsDTO.getId());
            wBDBSafetySpprtDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            wBDBSafetyMapper.insertAppctnSpprt(wBDBSafetySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBDBSafetyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBDBSafetyMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 사용자 신청 수정
     */
    public int carbonUserUpdate(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int respCnt = 0;
        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBDBSafetyMstInsertDTO.setModId(coaAdmDTO.getId());
        wBDBSafetyMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //선급금 여부
        if(wBDBSafetyMstInsertDTO.getSpprtList() != null){
            WBDBSafetySpprtDTO wBDBSafetySpprtDTO = wBDBSafetyMstInsertDTO.getSpprtList().get(0);
            wBDBSafetySpprtDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            //신청파일 넣기
            List<COFileDTO> rtnList = null;
            Map<String, MultipartFile> files = multiRequest.getFileMap();
            Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
            MultipartFile file;
            int atchFileCnt = 0;

            while (itr.hasNext()) {
                Map.Entry<String, MultipartFile> entry = itr.next();
                file = entry.getValue();

                if (file.getName().indexOf("FileSeq") > -1  && file.getSize() > 0) {
                    atchFileCnt++;
                }
            }

            if (!files.isEmpty()) {
                rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                for (int i = 0; i < rtnList.size() ; i++) {
                    List<COFileDTO> fileList = new ArrayList();
                    Integer fileSeq;

                    if ("99".equals(rtnList.get(i).getRespCd())) {
                        fileSeq = wBDBSafetyMstInsertDTO.getFileSeqList().get(i);
                    } else {
                        rtnList.get(i).setStatus("success");
                        rtnList.get(i).setFieldNm("fileSeq");
                        fileList.add(rtnList.get(i));
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                        fileSeq = fileSeqMap.get("fileSeq");
                    }

                    //선급금 지급
                    if(wBDBSafetySpprtDTO.getGiveType().equals("PRO_TYPE03001")){
                        //지원금신청서
                        if(i == 0)wBDBSafetySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //협약서
                        if(i == 1)wBDBSafetySpprtDTO.setAgrmtFileSeq(fileSeq);
                        //보증보험증
                        if(i == 2)wBDBSafetySpprtDTO.setGrnteInsrncFileSeq(fileSeq);
                    }
                    else if(wBDBSafetySpprtDTO.getGiveType().equals("PRO_TYPE03002")){
                        //지원금신청서
                        if(i == 0)wBDBSafetySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //거래명세서
                        if(i == 1)wBDBSafetySpprtDTO.setBlingFileSeq(fileSeq);
                        //매출전표
                        if(i == 2)wBDBSafetySpprtDTO.setSlsFileSeq(fileSeq);
                        //검수확인서
                        if(i == 3)wBDBSafetySpprtDTO.setInsptChkFileSeq(fileSeq);
                    }
                }
            }
            wBDBSafetySpprtDTO.setModId(coaAdmDTO.getId());
            wBDBSafetySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBDBSafetySpprtDTO.getGiveDt() != "" ){
                wBDBSafetyMapper.updateAppctnSpprt(wBDBSafetySpprtDTO);
            }

        }else{
            int maxRsumeOrd = wBDBSafetyMstInsertDTO.getMaxRsumeOrd();
            int rsumeOrd = maxRsumeOrd - 1;

            //신청 MST ○
            respCnt = wBDBSafetyMapper.updateAppctnMst(wBDBSafetyMstInsertDTO);

            //신청 DTL ○
            WBDBSafetyDtlDTO wBDBSafetyDtlDTO = new WBDBSafetyDtlDTO();

            wBDBSafetyDtlDTO = wBDBSafetyMstInsertDTO.getRsumeDtlList().get(0);

            wBDBSafetyDtlDTO.setAppctnSeq(wBDBSafetyMstInsertDTO.getAppctnSeq());
            wBDBSafetyDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
            wBDBSafetyDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBDBSafetyDtlDTO.setModId(coaAdmDTO.getId());
            wBDBSafetyDtlDTO.setModIp(coaAdmDTO.getLoginIp());

            wBDBSafetyMapper.updateAppctnDtl(wBDBSafetyDtlDTO);

            WBDBSafetyPbsnDtlDTO wBDBSafetyPbsnDtlDTO = new WBDBSafetyPbsnDtlDTO();
            //Pbsn ○
            wBDBSafetyPbsnDtlDTO = wBDBSafetyMstInsertDTO.getPbsnDtlList().get(0);

            if(wBDBSafetyPbsnDtlDTO.getBsnPmt() == null || wBDBSafetyPbsnDtlDTO.getBsnPmt().equals("")){
                wBDBSafetyPbsnDtlDTO.setBsnPmt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getBsnPlanDt() == null || wBDBSafetyPbsnDtlDTO.getBsnPlanDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setBsnPlanDt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getSpprtPmt() == null || wBDBSafetyPbsnDtlDTO.getSpprtPmt().equals("")){
                wBDBSafetyPbsnDtlDTO.setSpprtPmt(null);
            }else{
                wBDBSafetyPbsnDtlDTO.setSpprtPmt(wBDBSafetyPbsnDtlDTO.getSpprtPmt().replaceAll(",",""));
            }

            if(wBDBSafetyPbsnDtlDTO.getPhswPmt() == null || wBDBSafetyPbsnDtlDTO.getPhswPmt().equals("")){
                wBDBSafetyPbsnDtlDTO.setPhswPmt(null);
            }else{
                wBDBSafetyPbsnDtlDTO.setPhswPmt(wBDBSafetyPbsnDtlDTO.getPhswPmt().replaceAll(",",""));
            }

            if(wBDBSafetyPbsnDtlDTO.getTtlPmt() == null || wBDBSafetyPbsnDtlDTO.getTtlPmt().equals("")){
                wBDBSafetyPbsnDtlDTO.setTtlPmt(null);
            }else{
                wBDBSafetyPbsnDtlDTO.setTtlPmt(wBDBSafetyPbsnDtlDTO.getTtlPmt().replaceAll(",",""));
            }

            if(wBDBSafetyPbsnDtlDTO.getChkDt() == null || wBDBSafetyPbsnDtlDTO.getChkDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setChkDt(null);
            }

            if(wBDBSafetyPbsnDtlDTO.getExamScore() == null || wBDBSafetyPbsnDtlDTO.getExamScore().equals("")){
                wBDBSafetyPbsnDtlDTO.setExamScore(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getPayDt() == null || wBDBSafetyPbsnDtlDTO.getPayDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setPayDt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getNslltSchdlDt() == null || wBDBSafetyPbsnDtlDTO.getNslltSchdlDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setNslltSchdlDt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getAccsDt() == null || wBDBSafetyPbsnDtlDTO.getAccsDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setAccsDt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getGiveSeq() == null || wBDBSafetyPbsnDtlDTO.getGiveSeq().equals("")){
                wBDBSafetyPbsnDtlDTO.setGiveSeq(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getCmpltnBrfngDt() == null || wBDBSafetyPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setCmpltnBrfngDt(null);
            }
            if(wBDBSafetyPbsnDtlDTO.getLastChkDt() == null || wBDBSafetyPbsnDtlDTO.getLastChkDt().equals("")){
                wBDBSafetyPbsnDtlDTO.setLastChkDt(null);
            }

            wBDBSafetyPbsnDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
            wBDBSafetyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBDBSafetyPbsnDtlDTO.setModId(coaAdmDTO.getId());
            wBDBSafetyPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
            wBDBSafetyMapper.updateAppctnPbsnDtl(wBDBSafetyPbsnDtlDTO);

            //상생 신청 파일 상세
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
                List<COFileDTO> fileList = new ArrayList();
                Integer fileSeq;

                if ("99".equals(rtnList.get(0).getRespCd())) {
                    fileSeq = wBDBSafetyMstInsertDTO.getFileSeqList().get(0);
                } else {
                    rtnList.get(0).setStatus("success");
                    rtnList.get(0).setFieldNm("fileSeq");
                    fileList.add(rtnList.get(0));
                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                    fileSeq = fileSeqMap.get("fileSeq");
                }

                WBDBSafetyFileDtlDTO wBDBSafetyFileDtlDTO = wBDBSafetyMstInsertDTO.getFileDtlList().get(0);

                wBDBSafetyFileDtlDTO.setRsumeSeq(wBDBSafetyMstInsertDTO.getRsumeSeq());
                wBDBSafetyFileDtlDTO.setRsumeOrd(maxRsumeOrd);
                wBDBSafetyFileDtlDTO.setFileSeq(fileSeq);
                wBDBSafetyFileDtlDTO.setRegId(coaAdmDTO.getId());
                wBDBSafetyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

                wBDBSafetyMapper.insertAppctnFileDtl(wBDBSafetyFileDtlDTO);
            }
        }

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    public int getInsertBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBDBSafetyMstInsertDTO.setEpisdSeq(wBDBSafetyMapper.selectEpisdSeq(wBDBSafetyMstInsertDTO));

        WBDBSafetySearchDTO wBDBSafetySearchDTO = new WBDBSafetySearchDTO();
        wBDBSafetySearchDTO.setDetailsKey(wBDBSafetyMstInsertDTO.getDetailsKey());
//        respCnt = wBDBSafetyMapper.getBsnmNoCnt(wBDBSafetyMstInsertDTO);
        respCnt = wBDBSafetyMapper.getSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO);

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
//            int cnt = wBDBSafetyMapper.getBsnmNoSeqCnt(wBDBSafetyMstInsertDTO);
            int cnt = wBDBSafetyMapper.getSbrdnBsnmNoSeqCnt(wBDBSafetyMstInsertDTO);
            if(cnt > 0 ){
                respCnt = 999;
            }
        }

        return respCnt;
    }

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    public int getInsertSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBDBSafetyMstInsertDTO.setEpisdSeq(wBDBSafetyMapper.selectEpisdSeq(wBDBSafetyMstInsertDTO));

        WBDBSafetySearchDTO wBDBSafetySearchDTO = new WBDBSafetySearchDTO();
        wBDBSafetySearchDTO.setDetailsKey(wBDBSafetyMstInsertDTO.getDetailsKey());
        respCnt = wBDBSafetyMapper.getSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO);

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
            int cnt = wBDBSafetyMapper.getSbrdnBsnmNoSeqCnt(wBDBSafetyMstInsertDTO);
            if(cnt > 0 ){
                respCnt = 999;
            }
        }

        return respCnt;
    }

    /**
     * 수정 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBDBSafetyMstInsertDTO.setEpisdSeq(wBDBSafetyMapper.selectEpisdSeq(wBDBSafetyMstInsertDTO));

        WBDBSafetySearchDTO wBDBSafetySearchDTO = new WBDBSafetySearchDTO();
        wBDBSafetySearchDTO.setDetailsKey(wBDBSafetyMstInsertDTO.getDetailsKey());
        WBDBSafetyMstInsertDTO wBEBTrnsfDTO = wBDBSafetyMapper.selectCarbonCompanyDtl(wBDBSafetySearchDTO);

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBDBSafetyMstInsertDTO.getMemSeq()))
        {
            respCnt = wBDBSafetyMapper.getBsnmNoCnt(wBDBSafetyMstInsertDTO);
        }

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBDBSafetyMstInsertDTO.setEpisdSeq(wBDBSafetyMapper.selectEpisdSeq(wBDBSafetyMstInsertDTO));

        WBDBSafetySearchDTO wBDBSafetySearchDTO = new WBDBSafetySearchDTO();
        wBDBSafetySearchDTO.setDetailsKey(wBDBSafetyMstInsertDTO.getDetailsKey());
        WBDBSafetyMstInsertDTO wBEBTrnsfDTO = wBDBSafetyMapper.selectCarbonCompanyDtl(wBDBSafetySearchDTO);

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBDBSafetyMstInsertDTO.getMemSeq())) {
            respCnt = wBDBSafetyMapper.getSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO);
        }

        wBDBSafetyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(WBDBSafetySearchDTO wBDBSafetySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBDBSafetyMapper.updAdmMemo(wBDBSafetySearchDTO);

        return respCnt;
    }
}
