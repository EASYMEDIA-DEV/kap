package com.kap.service.impl.wb.wbc;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.WBSendDTO;
import com.kap.core.dto.wb.wbc.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBCBSecurityService;
import com.kap.service.dao.wb.wbc.WBCBSecurityMapper;
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
    private final COFileUtil cOFileUtil;
    private final COFileService cOFileService;
    private final WBBBCompanyServiceImpl wbbbCompanyService;

    /**
     * 신청 목록
     */
    public WBCBSecuritySearchDTO selectCarbonCompanyList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBCBSecuritySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBCBSecuritySearchDTO.getListRowSize());

        page.setPageSize(wBCBSecuritySearchDTO.getPageRowSize());

        wBCBSecuritySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBCBSecuritySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBCBSecuritySearchDTO.setList(wBCBSecurityMapper.selectCarbonCompanyList(wBCBSecuritySearchDTO));
        wBCBSecuritySearchDTO.setTotalCount(wBCBSecurityMapper.getCarbonCompanyListTotCnt(wBCBSecuritySearchDTO));

        return wBCBSecuritySearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {
        return wBCBSecurityMapper.selectYearDtl(wBCBSecuritySearchDTO);
    }

    /**
     * 회차 리스트 조회
     */
    public WBCBSecuritySearchDTO getYearSelect(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {
        wBCBSecuritySearchDTO.setEpisdList(wBCBSecurityMapper.getYearSelect(wBCBSecuritySearchDTO));

        return wBCBSecuritySearchDTO;
    }

    /**
     * 신청 상세 조회
     */
    public WBCBSecurityMstInsertDTO selectCarbonCompanyDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {

        //MST
        WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBCBSecuritySearchDTO);

        //DTL
        wBCBSecurityMstInsertDTO.setRsumeDtlList(wBCBSecurityMapper.selectCarbonCompanyDtlDetail(wBCBSecuritySearchDTO));

        wBCBSecuritySearchDTO.setMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
        wBCBSecuritySearchDTO.setBsnmNo(wBCBSecurityMstInsertDTO.getAppctnBsnmNo());

        //MEM
        wBCBSecurityMstInsertDTO.setMemList(wBCBSecurityMapper.selectCarbonCompanyMember(wBCBSecuritySearchDTO));

        //CMSSR
        if(wBCBSecurityMstInsertDTO.getPicCmssrSeq() != null){
            wBCBSecuritySearchDTO.setMemSeq(wBCBSecurityMstInsertDTO.getPicCmssrSeq());

            wBCBSecurityMstInsertDTO.setIsttrDtl(wBCBSecurityMapper.selectIsttrDtl(wBCBSecurityMstInsertDTO));
        }

        //COMPANY
        WBCBCompanyDTO wBCBCompanyList = wBCBSecurityMapper.selectCarbonCompany(wBCBSecuritySearchDTO);
        wBCBCompanyList.setDtlList(new ArrayList<>());

        //SQ
        List<WBCBCompanyDtlDTO> wBCBCompanyDtlList = wBCBSecurityMapper.selectCarbonCompanySQ(wBCBSecuritySearchDTO);

        //RSUME_PBSN_DTL
        wBCBSecurityMstInsertDTO.setPbsnDtlList(wBCBSecurityMapper.selectCarbonCompanyPbsn(wBCBSecuritySearchDTO));
        for(WBCBCompanyDtlDTO wBCBCompanyDtlDTO : wBCBCompanyDtlList){
            wBCBCompanyList.getDtlList().add(wBCBCompanyDtlDTO);
        }
        wBCBSecurityMstInsertDTO.setCompanyDtl(wBCBCompanyList);

        //지원금액 조회
        List<WBCBSecuritySpprtDTO> wBCBSecuritySpprtDTO = wBCBSecurityMapper.selectAppctnSpprt(wBCBSecuritySearchDTO);

        wBCBSecurityMstInsertDTO.setSpprtList(wBCBSecuritySpprtDTO);

        //file
        wBCBSecurityMstInsertDTO.setFileDtlList(wBCBSecurityMapper.selectFileDtl(wBCBSecuritySearchDTO));

        //지급 차수 조회
        wBCBSecurityMstInsertDTO.setGiveOrdList(wBCBSecurityMapper.getGiveOrdList(wBCBSecuritySearchDTO));

        return wBCBSecurityMstInsertDTO;
    }


    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        wBCBSecurityMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBCBSecurityMstInsertDTO));

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBCBSecurityMstInsertDTO.setRegId(coaAdmDTO.getId());
        wBCBSecurityMstInsertDTO.setRegIp(coaAdmDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBCBSecurityMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBCBSecurityMapper.insertAppctnMst(wBCBSecurityMstInsertDTO);

        //신청 DTL
        WBCBSecurityDtlDTO wBCBSecurityDtlDTO = new WBCBSecurityDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
        wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
        wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityDtlDTO.setRsumeOrd(1);
        wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

        WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO= wBCBSecurityMstInsertDTO.getPbsnDtlList().get(0);
        wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityPbsnDtlDTO.setRsumeOrd(1);
        wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
        wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBCBSecurityMstInsertDTO.getOptnFileList());

        WBCBSecurityFileDtlDTO wBCBSecurityFileDtlDTO = new WBCBSecurityFileDtlDTO();
        wBCBSecurityFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityFileDtlDTO.setRsumeOrd(1);
        wBCBSecurityFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBCBSecurityFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        wBCBSecurityFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBCBSecurityFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        wBCBSecurityMapper.insertAppctnFileDtl(wBCBSecurityFileDtlDTO);

        //회원
        MPAUserDto mPAUserDto= wBCBSecurityMstInsertDTO.getMemList().get(0);
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnMember(mPAUserDto);

        //부품사
        WBCBCompanyDTO wBCBCompanyDTO= wBCBSecurityMstInsertDTO.getCompanyDtl();
        wBCBCompanyDTO.setModId(coaAdmDTO.getId());
        wBCBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnCompany(wBCBCompanyDTO);

        //부품사 SQ
        if(wBCBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBCBSecurityMapper.deleteCarbonCompanySQ(wBCBCompanyDTO);
            for(int i=0; i < wBCBCompanyDTO.getDtlList().size(); i++){
                WBCBCompanyDtlDTO wBCBCompanyDtlDTO = wBCBCompanyDTO.getDtlList().get(i);

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();

                wBCBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);

                if(wBCBCompanyDtlDTO.getYear() == null || wBCBCompanyDtlDTO.getYear().equals("")){
                    wBCBCompanyDtlDTO.setYear(null);
                }
                if(wBCBCompanyDtlDTO.getScore() == null || wBCBCompanyDtlDTO.getScore().equals("")){
                    wBCBCompanyDtlDTO.setScore(null);
                }
                if(wBCBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBCBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBCBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                wBCBSecurityMapper.insertCarbonCompanySQ(wBCBCompanyDtlDTO);
            }
        }

        //지원금
        for(int i=0; i < 2; i++){
            WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO = new WBCBSecuritySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBCBSecuritySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBCBSecuritySpprtDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBCBSecuritySpprtDTO.setGiveType("PRO_TYPE03001");
                wBCBSecuritySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
                wBCBSecuritySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBCBSecuritySpprtDTO.setGiveType("PRO_TYPE03002");
                wBCBSecuritySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBCBSecuritySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBCBSecuritySpprtDTO.setRegId(coaAdmDTO.getId());
            wBCBSecuritySpprtDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnSpprt(wBCBSecuritySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBCBSecurityMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 신청 수정
     */
    public int carbonCompanyUpdate(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        int maxRsumeOrd = wBCBSecurityMstInsertDTO.getMaxRsumeOrd();
        int rsumeOrd = maxRsumeOrd - 1;

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBCBSecurityMstInsertDTO.setModId(coaAdmDTO.getId());
        wBCBSecurityMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //참여 이관 추가
        WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
        wBCBSecuritySearchDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
        WBCBSecurityMstInsertDTO wBCBTrnsfDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBCBSecuritySearchDTO);

        if(!wBCBTrnsfDTO.getMemSeq().equals(wBCBSecurityMstInsertDTO.getMemSeq()))
        {
            int firstAppctnTrnsfDtlSeqIdgen = cxAppctnTrnsfDtlIdgen.getNextIntegerId();
            WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO = new WBCBSecurityTrnsfDTO();
            wBCBSecurityTrnsfDTO.setTrnsfSeq(firstAppctnTrnsfDtlSeqIdgen);
            wBCBSecurityTrnsfDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityTrnsfDTO.setBfreMemSeq(wBCBTrnsfDTO.getMemSeq());
            wBCBSecurityTrnsfDTO.setAftrMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
            wBCBSecurityTrnsfDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityTrnsfDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnTrnsf(wBCBSecurityTrnsfDTO);
        }

        //신청 MST ○
        respCnt = wBCBSecurityMapper.updateAppctnMst(wBCBSecurityMstInsertDTO);

        //신청 DTL ○
        WBCBSecurityDtlDTO wBCBSecurityDtlDTO = wBCBSecurityMstInsertDTO.getRsumeDtlList().get(rsumeOrd);

        wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
        wBCBSecurityDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
        wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBCBSecurityDtlDTO.setModId(coaAdmDTO.getId());
        wBCBSecurityDtlDTO.setModIp(coaAdmDTO.getLoginIp());

        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBCBSecurityMstInsertDTO.getEpisdSeq());
        wbSendDTO.setStageNm(wBCBSecurityMstInsertDTO.getStageNm());
        wbSendDTO.setReason(wBCBSecurityDtlDTO.getRtrnRsnCntn());
        wbSendDTO.setRsumeSeq(wBCBSecurityDtlDTO.getRsumeSeq());
        wbSendDTO.setRsumeOrd(wBCBSecurityDtlDTO.getRsumeOrd());
        wbSendDTO.setMngSttdCd(wBCBSecurityDtlDTO.getMngSttsCd());

        //EDM,SMS발송
        if("PRO_TYPE01001_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_003".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_003".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_003".equals(wBCBSecurityDtlDTO.getMngSttsCd())) {
            //선정
            wbbbCompanyService.send(wbSendDTO,"SMS05");
        } else if ("PRO_TYPE01001_02_004".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_004".equals(wBCBSecurityDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_004".equals(wBCBSecurityDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd())) {
            //미선정 및 부적합
            wbbbCompanyService.send(wbSendDTO,"SMS07");
        } else if ("PRO_TYPE01001_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd())
                || "PRO_TYPE01004_02_002".equals(wBCBSecurityDtlDTO.getMngSttsCd())) {
            //보완요청
            wbbbCompanyService.send(wbSendDTO,"SMS06");
        }

        wBCBSecurityMapper.updateAppctnDtl(wBCBSecurityDtlDTO);

        //Pbsn ○
        WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO = wBCBSecurityMstInsertDTO.getPbsnDtlList().get(rsumeOrd);
        if(wBCBSecurityPbsnDtlDTO.getBsnPmt() == null || wBCBSecurityPbsnDtlDTO.getBsnPmt().equals("")){
            wBCBSecurityPbsnDtlDTO.setBsnPmt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getBsnPlanDt() == null || wBCBSecurityPbsnDtlDTO.getBsnPlanDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setBsnPlanDt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getSpprtPmt() == null || wBCBSecurityPbsnDtlDTO.getSpprtPmt().equals("")){
            wBCBSecurityPbsnDtlDTO.setSpprtPmt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getPhswPmt() == null || wBCBSecurityPbsnDtlDTO.getPhswPmt().equals("")){
            wBCBSecurityPbsnDtlDTO.setPhswPmt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getTtlPmt() == null || wBCBSecurityPbsnDtlDTO.getTtlPmt().equals("")){
            wBCBSecurityPbsnDtlDTO.setTtlPmt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getChkDt() == null || wBCBSecurityPbsnDtlDTO.getChkDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setChkDt(null);
        }

        if(wBCBSecurityPbsnDtlDTO.getExamScore() == null || wBCBSecurityPbsnDtlDTO.getExamScore().equals("")){
            wBCBSecurityPbsnDtlDTO.setExamScore(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getPayDt() == null || wBCBSecurityPbsnDtlDTO.getPayDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setPayDt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getNslltSchdlDt() == null || wBCBSecurityPbsnDtlDTO.getNslltSchdlDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setNslltSchdlDt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getAccsDt() == null || wBCBSecurityPbsnDtlDTO.getAccsDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setAccsDt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getGiveSeq() == null || wBCBSecurityPbsnDtlDTO.getGiveSeq().equals("")){
            wBCBSecurityPbsnDtlDTO.setGiveSeq(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getCmpltnBrfngDt() == null || wBCBSecurityPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setCmpltnBrfngDt(null);
        }
        if(wBCBSecurityPbsnDtlDTO.getLastChkDt() == null || wBCBSecurityPbsnDtlDTO.getLastChkDt().equals("")){
            wBCBSecurityPbsnDtlDTO.setLastChkDt(null);
        }

        wBCBSecurityPbsnDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
        wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBCBSecurityPbsnDtlDTO.setModId(coaAdmDTO.getId());
        wBCBSecurityPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);

        //2024-06-04 사업비는 수정 가능하도록 변경
        WBCBSecurityPbsnDtlDTO bsnPmtDTO = wBCBSecurityMstInsertDTO.getPbsnDtlList().get(0);
        bsnPmtDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
        bsnPmtDTO.setModId(coaAdmDTO.getId());
        bsnPmtDTO.setModIp(coaAdmDTO.getLoginIp());
        if(bsnPmtDTO.getBsnPmt() == null || bsnPmtDTO.getBsnPmt().isEmpty()) {
            bsnPmtDTO.setBsnPmt("0");
        }
        wBCBSecurityMapper.updateBsnPmtDtl(bsnPmtDTO);


        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBCBSecurityMstInsertDTO.getFileList());

        WBCBSecurityFileDtlDTO wBCBSecurityFileDtlDTO = wBCBSecurityMstInsertDTO.getFileDtlList().get(rsumeOrd);
        wBCBSecurityFileDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
        wBCBSecurityFileDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBCBSecurityFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"+rsumeOrd));
        wBCBSecurityFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBCBSecurityFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.insertAppctnFileDtl(wBCBSecurityFileDtlDTO);

        //회원○
        MPAUserDto mPAUserDto= wBCBSecurityMstInsertDTO.getMemList().get(0);
        mPAUserDto.setMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnMember(mPAUserDto);

        //부품사○
        WBCBCompanyDTO wBCBCompanyDTO= wBCBSecurityMstInsertDTO.getCompanyDtl();
        wBCBCompanyDTO.setModId(coaAdmDTO.getId());
        wBCBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBCBSecurityMapper.updateAppctnCompany(wBCBCompanyDTO);

        //부품사 SQ○
        if(wBCBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBCBSecurityMapper.deleteCarbonCompanySQ(wBCBCompanyDTO);
            for(int i=0; i < wBCBCompanyDTO.getDtlList().size(); i++){
                WBCBCompanyDtlDTO wBCBCompanyDtlDTO = wBCBCompanyDTO.getDtlList().get(i);

                if(wBCBCompanyDtlDTO.getYear() == null || wBCBCompanyDtlDTO.getYear().equals("")){
                    wBCBCompanyDtlDTO.setYear(null);
                }
                if(wBCBCompanyDtlDTO.getScore() == null || wBCBCompanyDtlDTO.getScore().equals("")){
                    wBCBCompanyDtlDTO.setScore(null);
                }
                if(wBCBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBCBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBCBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();
                wBCBCompanyDtlDTO.setBsnmNo(wBCBCompanyDTO.getBsnmNo());
                wBCBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);
                wBCBSecurityMapper.insertCarbonCompanySQ(wBCBCompanyDtlDTO);
            }
        }


        //지급금 관련
        for(int i=0; i < wBCBSecurityMstInsertDTO.getSpprtList().size(); i++){
            //상생 신청 파일 상세
            WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO = wBCBSecurityMstInsertDTO.getSpprtList().get(i);
            wBCBSecuritySpprtDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            if(i == 0){
                //지원금신청서
                wBCBSecuritySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq"));
                //협약서
                wBCBSecuritySpprtDTO.setAgrmtFileSeq(fileSeqMap.get("agrmtFileSeq"));
                //보증보험증
                wBCBSecuritySpprtDTO.setGrnteInsrncFileSeq(fileSeqMap.get("grnteInsrncFileSeq"));
            }else{
                //지원금신청서
                wBCBSecuritySpprtDTO.setSpprtAppctnFileSeq(fileSeqMap.get("spprtAppctnFileSeq1"));
                //거래명세서
                wBCBSecuritySpprtDTO.setBlingFileSeq(fileSeqMap.get("blingFileSeq"));
                //매출전표
                wBCBSecuritySpprtDTO.setSlsFileSeq(fileSeqMap.get("slsFileSeq"));
                //검수확인서
                wBCBSecuritySpprtDTO.setInsptChkFileSeq(fileSeqMap.get("insptChkFileSeq"));
            }

            wBCBSecuritySpprtDTO.setModId(coaAdmDTO.getId());
            wBCBSecuritySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBCBSecuritySpprtDTO.getGiveDt() == null || wBCBSecuritySpprtDTO.getGiveDt().isEmpty()) {
                wBCBSecuritySpprtDTO.setGiveDt(null);
            }
            if(wBCBSecuritySpprtDTO.getAccsDt() == null || wBCBSecuritySpprtDTO.getAccsDt().isEmpty()) {
                wBCBSecuritySpprtDTO.setAccsDt(null);
            }

            wBCBSecurityMapper.updateAppctnSpprt(wBCBSecuritySpprtDTO);
        }


        //최초점검, 최종점검, 검수보고 경우 추가 입력 확인
        int firstAppctnRsumeDtlSeqIdgen = 0;

        //신청 적합
        if(maxRsumeOrd == 1 && "PRO_TYPE01001_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01002");
            wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01002_01_001");
            wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01002_02_006");
            wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

            wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);
        }
        //사업계획 적합
        else if(maxRsumeOrd == 2 && "PRO_TYPE01002_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01003");
            wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01003_01_001");
            wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01003_02_001");
            wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

            wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);
        }
        //최초점검 적합
        else if(maxRsumeOrd == 3 && "PRO_TYPE01003_02_003".equals(wBCBSecurityDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01004");
            wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01004_01_001");
            wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01004_02_006");
            wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

            wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);
        }
        //완료 보고 적합
        else if(maxRsumeOrd == 4 && "PRO_TYPE01004_02_005".equals(wBCBSecurityDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01005");
            wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01005_01_001");
            wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01005_02_001");
            wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

            wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);
        }
        //최종점검 적합
        else if(maxRsumeOrd == 5 && "PRO_TYPE01005_02_003".equals(wBCBSecurityDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01006");
            wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01006_01_001");
            wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01006_02_004");
            wBCBSecurityDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

            wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBCBSecurityPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);
        }

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {


        List<String> appctnSeqList = wBCBSecuritySearchDTO.getDelValueList();

        wBCBSecurityMapper.carbonCompanyDeleteTrnsf(wBCBSecuritySearchDTO);
        wBCBSecurityMapper.carbonCompanyDeleteSpprt(wBCBSecuritySearchDTO);

        wBCBSecuritySearchDTO.setDelValueList(wBCBSecurityMapper.selectRsumeSeq(wBCBSecuritySearchDTO));

        wBCBSecurityMapper.carbonCompanyDeletePbsn(wBCBSecuritySearchDTO);
        wBCBSecurityMapper.carbonCompanyDeleteRsumeFile(wBCBSecuritySearchDTO);

        wBCBSecuritySearchDTO.setDelValueList(appctnSeqList);

        wBCBSecurityMapper.carbonCompanyDeleteRsume(wBCBSecuritySearchDTO);
        int respCnt = wBCBSecurityMapper.carbonCompanyDeleteMst(wBCBSecuritySearchDTO);

        return respCnt;

    }

    /**
     * 이관 로그 조회
     */
    public WBCBSecurityTrnsfDTO getTrnsfList(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBCBSecurityTrnsfDTO.getPageIndex());
        page.setRecordCountPerPage(wBCBSecurityTrnsfDTO.getListRowSize());

        page.setPageSize(wBCBSecurityTrnsfDTO.getPageRowSize());

        wBCBSecurityTrnsfDTO.setFirstIndex(page.getFirstRecordIndex());
        wBCBSecurityTrnsfDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBCBSecurityTrnsfDTO.setList(wBCBSecurityMapper.getTrnsfList(wBCBSecurityTrnsfDTO));
        wBCBSecurityTrnsfDTO.setTotalCount(wBCBSecurityMapper.getTrnsfListTotCnt(wBCBSecurityTrnsfDTO));

        return wBCBSecurityTrnsfDTO;
    }

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBCBSecurityMapper.getRsumePbsnCnt(wBCBSecuritySearchDTO);

        return respCnt;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("사업비");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("지원금");
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
        List<WBCBSecuritySearchDTO> list = wBCBSecuritySearchDTO.getList();

        List<WBCBSecurityPbsnDtlDTO> pbsnList = null;

        int maxOrd = 0;
        int length = 0;

        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);
            pbsnList = wBCBSecurityMapper.selectExcelPbsn(wBCBSecuritySearchDTO.getList().get(i));

            length = pbsnList.size();

            maxOrd = pbsnList.get(length-1).getMaxRsumeOrd();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wBCBSecuritySearchDTO.getTotalCount() - i);
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


            //사업비
            cell = row.createCell(18);
            if(maxOrd >= 1){
                cell.setCellValue(pbsnList.get(0).getBsnPmt());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(style_body);

            //지원금
            cell = row.createCell(19);
            if(maxOrd >= 2){
                cell.setCellValue(pbsnList.get(1).getSpprtPmt());
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
    public WBCBCompanyDTO selectCompanyUserDtl(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {

        WBCBCompanyDTO wBCBCompanyDTO = new WBCBCompanyDTO();

        wBCBCompanyDTO = wBCBSecurityMapper.getCompanyInfo(wBCBSecuritySearchDTO);
        List<WBCBCompanyDtlDTO> sqList = wBCBSecurityMapper.selectSqList(wBCBSecuritySearchDTO);
        wBCBCompanyDTO.setDtlList(sqList);
        wBCBCompanyDTO.setMemSeq(wBCBSecuritySearchDTO.getMemSeq());
        wBCBCompanyDTO.setEpisdSeq(wBCBSecuritySearchDTO.getEpisdSeq());

        return wBCBCompanyDTO;
    }


    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;
        cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        wBCBSecurityMstInsertDTO.setRegId(cOUserDetailsDTO.getId());
        wBCBSecurityMstInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBCBSecurityMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBCBSecurityMapper.insertAppctnMst(wBCBSecurityMstInsertDTO);

        //신청 DTL
        WBCBSecurityDtlDTO wBCBSecurityDtlDTO = new WBCBSecurityDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
        wBCBSecurityDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBCBSecurityDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBCBSecurityDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBCBSecurityDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBCBSecurityDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBCBSecurityDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityDtlDTO.setRsumeOrd(1);
        wBCBSecurityMapper.insertAppctnDtl(wBCBSecurityDtlDTO);

        WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO= wBCBSecurityMstInsertDTO.getPbsnDtlList().get(0);
        wBCBSecurityPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityPbsnDtlDTO.setRsumeOrd(1);
        wBCBSecurityPbsnDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBCBSecurityPbsnDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        wBCBSecurityMapper.insertAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBCBSecurityMstInsertDTO.getFileList());

        WBCBSecurityFileDtlDTO wBCBSecurityFileDtlDTO = new WBCBSecurityFileDtlDTO();
        wBCBSecurityFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBCBSecurityFileDtlDTO.setRsumeOrd(1);
        wBCBSecurityFileDtlDTO.setFileSeq(fileSeqMap.get("atchFile"));
        wBCBSecurityFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBCBSecurityFileDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBCBSecurityFileDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        wBCBSecurityMapper.insertAppctnFileDtl(wBCBSecurityFileDtlDTO);

        //지원금
        for(int i=0; i < 2; i++){
            WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO = new WBCBSecuritySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBCBSecuritySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBCBSecuritySpprtDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBCBSecuritySpprtDTO.setGiveType("PRO_TYPE03001");
                wBCBSecuritySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
                wBCBSecuritySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBCBSecuritySpprtDTO.setGiveType("PRO_TYPE03002");
                wBCBSecuritySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBCBSecuritySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBCBSecuritySpprtDTO.setRegId(cOUserDetailsDTO.getId());
            wBCBSecuritySpprtDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            wBCBSecurityMapper.insertAppctnSpprt(wBCBSecuritySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBCBSecurityMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBCBSecurityMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 사용자 신청 수정
     */
    public int carbonUserUpdate(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int respCnt = 0;
        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBCBSecurityMstInsertDTO.setModId(coaAdmDTO.getId());
        wBCBSecurityMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //선급금 여부
        if(wBCBSecurityMstInsertDTO.getSpprtList() != null){
            WBCBSecuritySpprtDTO wBCBSecuritySpprtDTO = wBCBSecurityMstInsertDTO.getSpprtList().get(0);
            wBCBSecuritySpprtDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
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
                        fileSeq = wBCBSecurityMstInsertDTO.getFileSeqList().get(i);
                    } else {
                        rtnList.get(i).setStatus("success");
                        rtnList.get(i).setFieldNm("fileSeq");
                        fileList.add(rtnList.get(i));
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                        fileSeq = fileSeqMap.get("fileSeq");
                    }
                    //선급금 지급
                    if(wBCBSecuritySpprtDTO.getGiveType().equals("PRO_TYPE03001")){
                        //지원금신청서
                        if(i == 0)wBCBSecuritySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //협약서
                        if(i == 1)wBCBSecuritySpprtDTO.setAgrmtFileSeq(fileSeq);
                        //보증보험증
                        if(i == 2)wBCBSecuritySpprtDTO.setGrnteInsrncFileSeq(fileSeq);
                    }
                    else if(wBCBSecuritySpprtDTO.getGiveType().equals("PRO_TYPE03002")){
                        //지원금신청서
                        if(i == 0)wBCBSecuritySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //거래명세서
                        if(i == 1)wBCBSecuritySpprtDTO.setBlingFileSeq(fileSeq);
                        //매출전표
                        if(i == 2)wBCBSecuritySpprtDTO.setSlsFileSeq(fileSeq);
                        //검수확인서
                        if(i == 3)wBCBSecuritySpprtDTO.setInsptChkFileSeq(fileSeq);
                    }
                }
            }
            wBCBSecuritySpprtDTO.setModId(coaAdmDTO.getId());
            wBCBSecuritySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBCBSecuritySpprtDTO.getGiveDt() != "" ){
                wBCBSecurityMapper.updateAppctnSpprt(wBCBSecuritySpprtDTO);
            }

        }else{
            int maxRsumeOrd = wBCBSecurityMstInsertDTO.getMaxRsumeOrd();
            int rsumeOrd = maxRsumeOrd - 1;

            //신청 MST ○
            respCnt = wBCBSecurityMapper.updateAppctnMst(wBCBSecurityMstInsertDTO);

            //신청 DTL ○
            WBCBSecurityDtlDTO wBCBSecurityDtlDTO = new WBCBSecurityDtlDTO();

            wBCBSecurityDtlDTO = wBCBSecurityMstInsertDTO.getRsumeDtlList().get(0);

            wBCBSecurityDtlDTO.setAppctnSeq(wBCBSecurityMstInsertDTO.getAppctnSeq());
            wBCBSecurityDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
            wBCBSecurityDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBCBSecurityDtlDTO.setModId(coaAdmDTO.getId());
            wBCBSecurityDtlDTO.setModIp(coaAdmDTO.getLoginIp());

            wBCBSecurityMapper.updateAppctnDtl(wBCBSecurityDtlDTO);

            WBCBSecurityPbsnDtlDTO wBCBSecurityPbsnDtlDTO = new WBCBSecurityPbsnDtlDTO();
            //Pbsn ○
            wBCBSecurityPbsnDtlDTO = wBCBSecurityMstInsertDTO.getPbsnDtlList().get(0);

            if(wBCBSecurityPbsnDtlDTO.getBsnPmt() == null || wBCBSecurityPbsnDtlDTO.getBsnPmt().equals("")){
                wBCBSecurityPbsnDtlDTO.setBsnPmt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getBsnPlanDt() == null || wBCBSecurityPbsnDtlDTO.getBsnPlanDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setBsnPlanDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getSpprtPmt() == null || wBCBSecurityPbsnDtlDTO.getSpprtPmt().equals("")){
                wBCBSecurityPbsnDtlDTO.setSpprtPmt(null);
            }else{
                wBCBSecurityPbsnDtlDTO.setSpprtPmt(wBCBSecurityPbsnDtlDTO.getSpprtPmt().replaceAll(",",""));
            }

            if(wBCBSecurityPbsnDtlDTO.getPhswPmt() == null || wBCBSecurityPbsnDtlDTO.getPhswPmt().equals("")){
                wBCBSecurityPbsnDtlDTO.setPhswPmt(null);
            }else{
                wBCBSecurityPbsnDtlDTO.setPhswPmt(wBCBSecurityPbsnDtlDTO.getPhswPmt().replaceAll(",",""));
            }

            if(wBCBSecurityPbsnDtlDTO.getTtlPmt() == null || wBCBSecurityPbsnDtlDTO.getTtlPmt().equals("")){
                wBCBSecurityPbsnDtlDTO.setTtlPmt(null);
            }else{
                wBCBSecurityPbsnDtlDTO.setTtlPmt(wBCBSecurityPbsnDtlDTO.getTtlPmt().replaceAll(",",""));
            }

            if(wBCBSecurityPbsnDtlDTO.getChkDt() == null || wBCBSecurityPbsnDtlDTO.getChkDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setChkDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getExamScore() == null || wBCBSecurityPbsnDtlDTO.getExamScore().equals("")){
                wBCBSecurityPbsnDtlDTO.setExamScore(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getPayDt() == null || wBCBSecurityPbsnDtlDTO.getPayDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setPayDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getNslltSchdlDt() == null || wBCBSecurityPbsnDtlDTO.getNslltSchdlDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setNslltSchdlDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getAccsDt() == null || wBCBSecurityPbsnDtlDTO.getAccsDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setAccsDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getGiveSeq() == null || wBCBSecurityPbsnDtlDTO.getGiveSeq().equals("")){
                wBCBSecurityPbsnDtlDTO.setGiveSeq(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getCmpltnBrfngDt() == null || wBCBSecurityPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setCmpltnBrfngDt(null);
            }
            if(wBCBSecurityPbsnDtlDTO.getLastChkDt() == null || wBCBSecurityPbsnDtlDTO.getLastChkDt().equals("")){
                wBCBSecurityPbsnDtlDTO.setLastChkDt(null);
            }

            wBCBSecurityPbsnDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
            wBCBSecurityPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBCBSecurityPbsnDtlDTO.setModId(coaAdmDTO.getId());
            wBCBSecurityPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
            wBCBSecurityMapper.updateAppctnPbsnDtl(wBCBSecurityPbsnDtlDTO);

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
                    fileSeq = wBCBSecurityMstInsertDTO.getFileSeqList().get(0);
                } else {
                    rtnList.get(0).setStatus("success");
                    rtnList.get(0).setFieldNm("fileSeq");
                    fileList.add(rtnList.get(0));
                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                    fileSeq = fileSeqMap.get("fileSeq");
                }

                WBCBSecurityFileDtlDTO wBCBSecurityFileDtlDTO = wBCBSecurityMstInsertDTO.getFileDtlList().get(0);

                wBCBSecurityFileDtlDTO.setRsumeSeq(wBCBSecurityMstInsertDTO.getRsumeSeq());
                wBCBSecurityFileDtlDTO.setRsumeOrd(maxRsumeOrd);
                wBCBSecurityFileDtlDTO.setFileSeq(fileSeq);
                wBCBSecurityFileDtlDTO.setRegId(coaAdmDTO.getId());
                wBCBSecurityFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

                wBCBSecurityMapper.insertAppctnFileDtl(wBCBSecurityFileDtlDTO);
            }
        }

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    public int getInsertBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBCBSecurityMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBCBSecurityMstInsertDTO));

        WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
        wBCBSecuritySearchDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
//        respCnt = wBCBSecurityMapper.getBsnmNoCnt(wBCBSecurityMstInsertDTO);
        respCnt = wBCBSecurityMapper.getSbrdnBsnmNoCnt(wBCBSecurityMstInsertDTO);

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
//            int cnt = wBCBSecurityMapper.getBsnmNoSeqCnt(wBCBSecurityMstInsertDTO);
            int cnt = wBCBSecurityMapper.getSbrdnBsnmNoSeqCnt(wBCBSecurityMstInsertDTO);
            if(cnt > 0 ){
                respCnt = 999;
            }
        }

        return respCnt;
    }

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    public int getInsertSbrdnBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBCBSecurityMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBCBSecurityMstInsertDTO));

        WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
        wBCBSecuritySearchDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
        respCnt = wBCBSecurityMapper.getSbrdnBsnmNoCnt(wBCBSecurityMstInsertDTO);

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
            int cnt = wBCBSecurityMapper.getSbrdnBsnmNoSeqCnt(wBCBSecurityMstInsertDTO);
            if(cnt > 0 ){
                respCnt = 999;
            }
        }

        return respCnt;
    }

    /**
     * 수정 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBCBSecurityMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBCBSecurityMstInsertDTO));

        WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
        wBCBSecuritySearchDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
        WBCBSecurityMstInsertDTO wBCBTrnsfDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBCBSecuritySearchDTO);

        if(!wBCBTrnsfDTO.getMemSeq().equals(wBCBSecurityMstInsertDTO.getMemSeq())) {
            respCnt = wBCBSecurityMapper.getBsnmNoCnt(wBCBSecurityMstInsertDTO);
        }

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBCBSecurityMstInsertDTO.setEpisdSeq(wBCBSecurityMapper.selectEpisdSeq(wBCBSecurityMstInsertDTO));

        WBCBSecuritySearchDTO wBCBSecuritySearchDTO = new WBCBSecuritySearchDTO();
        wBCBSecuritySearchDTO.setDetailsKey(wBCBSecurityMstInsertDTO.getDetailsKey());
        WBCBSecurityMstInsertDTO wBCBTrnsfDTO = wBCBSecurityMapper.selectCarbonCompanyDtl(wBCBSecuritySearchDTO);

        if(!wBCBTrnsfDTO.getMemSeq().equals(wBCBSecurityMstInsertDTO.getMemSeq())) {
            respCnt = wBCBSecurityMapper.getSbrdnBsnmNoCnt(wBCBSecurityMstInsertDTO);
        }

        wBCBSecurityMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(WBCBSecuritySearchDTO wBCBSecuritySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBCBSecurityMapper.updAdmMemo(wBCBSecuritySearchDTO);

        return respCnt;
    }
}
