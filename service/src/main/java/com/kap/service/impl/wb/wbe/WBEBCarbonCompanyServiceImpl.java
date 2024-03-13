package com.kap.service.impl.wb.wbe;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.wb.WBSendDTO;
import com.kap.core.dto.wb.wbe.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBEBCarbonCompanyService;
import com.kap.service.dao.wb.wbe.WBEBCarbonCompanyMapper;
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
public class WBEBCarbonCompanyServiceImpl implements WBEBCarbonCompanyService {


    //Mapper
    private final WBEBCarbonCompanyMapper wBEBCarbonCompanyMapper;

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
    public WBEBCarbonCompanySearchDTO selectCarbonCompanyList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wBEBCarbonCompanySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wBEBCarbonCompanySearchDTO.getListRowSize());

        page.setPageSize(wBEBCarbonCompanySearchDTO.getPageRowSize());

        wBEBCarbonCompanySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wBEBCarbonCompanySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wBEBCarbonCompanySearchDTO.setList(wBEBCarbonCompanyMapper.selectCarbonCompanyList(wBEBCarbonCompanySearchDTO));
        wBEBCarbonCompanySearchDTO.setTotalCount(wBEBCarbonCompanyMapper.getCarbonCompanyListTotCnt(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanySearchDTO;
    }

    /**
     * 연도 상세 조회
     */
    public List<String> selectYearDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        return wBEBCarbonCompanyMapper.selectYearDtl(wBEBCarbonCompanySearchDTO);
    }

    /**
     * 회차 리스트 조회
     */
    public WBEBCarbonCompanySearchDTO getYearSelect(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {
        wBEBCarbonCompanySearchDTO.setEpisdList(wBEBCarbonCompanyMapper.getYearSelect(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanySearchDTO;
    }

    /**
     * 신청 상세 조회
     */
    public WBEBCarbonCompanyMstInsertDTO selectCarbonCompanyDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {

        //MST
        WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO = wBEBCarbonCompanyMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);

        //DTL
        wBEBCarbonCompanyMstInsertDTO.setRsumeDtlList(wBEBCarbonCompanyMapper.selectCarbonCompanyDtlDetail(wBEBCarbonCompanySearchDTO));

        wBEBCarbonCompanySearchDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        wBEBCarbonCompanySearchDTO.setBsnmNo(wBEBCarbonCompanyMstInsertDTO.getAppctnBsnmNo());

        //MEM
        wBEBCarbonCompanyMstInsertDTO.setMemList(wBEBCarbonCompanyMapper.selectCarbonCompanyMember(wBEBCarbonCompanySearchDTO));

        //CMSSR
        if(wBEBCarbonCompanyMstInsertDTO.getPicCmssrSeq() != null){
            wBEBCarbonCompanySearchDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getPicCmssrSeq());

            wBEBCarbonCompanyMstInsertDTO.setIsttrDtl(wBEBCarbonCompanyMapper.selectIsttrDtl(wBEBCarbonCompanyMstInsertDTO));
        }

        //COMPANY
        WBEBCompanyDTO wBEBCompanyList = wBEBCarbonCompanyMapper.selectCarbonCompany(wBEBCarbonCompanySearchDTO);
        wBEBCompanyList.setDtlList(new ArrayList<>());

        //SQ
        List<WBEBCompanyDtlDTO> wBEBCompanyDtlList = wBEBCarbonCompanyMapper.selectCarbonCompanySQ(wBEBCarbonCompanySearchDTO);

        //RSUME_PBSN_DTL
        wBEBCarbonCompanyMstInsertDTO.setPbsnDtlList(wBEBCarbonCompanyMapper.selectCarbonCompanyPbsn(wBEBCarbonCompanySearchDTO));
        for(WBEBCompanyDtlDTO wBEBCompanyDtlDTO : wBEBCompanyDtlList){
            wBEBCompanyList.getDtlList().add(wBEBCompanyDtlDTO);
        }
        wBEBCarbonCompanyMstInsertDTO.setCompanyDtl(wBEBCompanyList);

        //지원금액 조회
        List<WBEBCarbonCompanySpprtDTO> wBEBCarbonCompanySpprtDTO = wBEBCarbonCompanyMapper.selectAppctnSpprt(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanyMstInsertDTO.setSpprtList(wBEBCarbonCompanySpprtDTO);

        //file
        wBEBCarbonCompanyMstInsertDTO.setFileDtlList(wBEBCarbonCompanyMapper.selectFileDtl(wBEBCarbonCompanySearchDTO));

        //지급 차수 조회
        wBEBCarbonCompanyMstInsertDTO.setGiveOrdList(wBEBCarbonCompanyMapper.getGiveOrdList(wBEBCarbonCompanySearchDTO));

        return wBEBCarbonCompanyMstInsertDTO;
    }


    /**
     * 신청 등록
     */
    public int carbonCompanyInsert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBEBCarbonCompanyMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));

        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBEBCarbonCompanyMstInsertDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyMstInsertDTO.setRegIp(coaAdmDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBEBCarbonCompanyMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBEBCarbonCompanyMapper.insertAppctnMst(wBEBCarbonCompanyMstInsertDTO);

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
        wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

        WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO= wBEBCarbonCompanyMstInsertDTO.getPbsnDtlList().get(0);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBEBCarbonCompanyMstInsertDTO.getOptnFileList());

        WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = new WBEBCarbonCompanyFileDtlDTO();
        wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        wBEBCarbonCompanyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

        wBEBCarbonCompanyMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);

        //회원
        MPAUserDto mPAUserDto= wBEBCarbonCompanyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.updateAppctnMember(mPAUserDto);

        //부품사
        WBEBCompanyDTO wBEBCompanyDTO= wBEBCarbonCompanyMstInsertDTO.getCompanyDtl();
        wBEBCompanyDTO.setModId(coaAdmDTO.getId());
        wBEBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.updateAppctnCompany(wBEBCompanyDTO);

        //부품사 SQ

        if(wBEBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBEBCarbonCompanyMapper.deleteCarbonCompanySQ(wBEBCompanyDTO);
            for(int i=0; i < wBEBCompanyDTO.getDtlList().size(); i++){
                WBEBCompanyDtlDTO wBEBCompanyDtlDTO = wBEBCompanyDTO.getDtlList().get(i);

                if(wBEBCompanyDtlDTO.getYear() == null || wBEBCompanyDtlDTO.getYear().equals("")){
                    wBEBCompanyDtlDTO.setYear(null);
                }
                if(wBEBCompanyDtlDTO.getScore() == null || wBEBCompanyDtlDTO.getScore().equals("")){
                    wBEBCompanyDtlDTO.setScore(null);
                }
                if(wBEBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBEBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBEBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();

                wBEBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);

                wBEBCarbonCompanyMapper.insertCarbonCompanySQ(wBEBCompanyDtlDTO);
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
                wBEBCarbonCompanySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBEBCarbonCompanySpprtDTO.setGiveType("PRO_TYPE03002");
                wBEBCarbonCompanySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBEBCarbonCompanySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBEBCarbonCompanySpprtDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanySpprtDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBEBCarbonCompanyMapper.insertAppctnSpprt(wBEBCarbonCompanySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBEBCarbonCompanyMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

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
        WBEBCarbonCompanyMstInsertDTO wBEBTrnsfDTO = wBEBCarbonCompanyMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBEBCarbonCompanyMstInsertDTO.getMemSeq()))
        {
            int firstAppctnTrnsfDtlSeqIdgen = cxAppctnTrnsfDtlIdgen.getNextIntegerId();
            WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO = new WBEBCarbonCompanyTrnsfDTO();
            wBEBCarbonCompanyTrnsfDTO.setTrnsfSeq(firstAppctnTrnsfDtlSeqIdgen);
            wBEBCarbonCompanyTrnsfDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyTrnsfDTO.setBfreMemSeq(wBEBTrnsfDTO.getMemSeq());
            wBEBCarbonCompanyTrnsfDTO.setAftrMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
            wBEBCarbonCompanyTrnsfDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyTrnsfDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBEBCarbonCompanyMapper.insertAppctnTrnsf(wBEBCarbonCompanyTrnsfDTO);
        }

        //신청 MST ○
        respCnt = wBEBCarbonCompanyMapper.updateAppctnMst(wBEBCarbonCompanyMstInsertDTO);

        //신청 DTL ○
        WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO = wBEBCarbonCompanyMstInsertDTO.getRsumeDtlList().get(rsumeOrd);

        wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyDtlDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyDtlDTO.setModIp(coaAdmDTO.getLoginIp());

        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBEBCarbonCompanyMstInsertDTO.getEpisdSeq());
        wbSendDTO.setStageNm(wBEBCarbonCompanyMstInsertDTO.getStageNm());
        wbSendDTO.setReason(wBEBCarbonCompanyDtlDTO.getRtrnRsnCntn());
        wbSendDTO.setRsumeSeq(wBEBCarbonCompanyDtlDTO.getRsumeSeq());
        wbSendDTO.setRsumeOrd(wBEBCarbonCompanyDtlDTO.getRsumeOrd());
        wbSendDTO.setMngSttdCd(wBEBCarbonCompanyDtlDTO.getMngSttsCd());

        //EDM,SMS발송
        if("PRO_TYPE01001_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())) {
            //선정
            wbbbCompanyService.send(wbSendDTO,"SMS05");
        } else if ("PRO_TYPE01001_02_004".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_004".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01003_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01004_02_004".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01005_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01006_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())) {
            //미선정 및 부적합
            wbbbCompanyService.send(wbSendDTO,"SMS07");
        } else if ("PRO_TYPE01001_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd()) || "PRO_TYPE01002_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())
                || "PRO_TYPE01004_02_002".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())) {
            //보완요청
            wbbbCompanyService.send(wbSendDTO,"SMS06");
        }

        wBEBCarbonCompanyMapper.updateAppctnDtl(wBEBCarbonCompanyDtlDTO);

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
        if(wBEBCarbonCompanyPbsnDtlDTO.getChkDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getChkDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setChkDt(null);
        }

        if(wBEBCarbonCompanyPbsnDtlDTO.getExamScore() == null || wBEBCarbonCompanyPbsnDtlDTO.getExamScore().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setExamScore(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getPayDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getPayDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setPayDt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getNslltSchdlDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getNslltSchdlDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setNslltSchdlDt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getAccsDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getAccsDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setAccsDt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getGiveSeq() == null || wBEBCarbonCompanyPbsnDtlDTO.getGiveSeq().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setGiveSeq(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getCmpltnBrfngDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setCmpltnBrfngDt(null);
        }
        if(wBEBCarbonCompanyPbsnDtlDTO.getLastChkDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getLastChkDt().equals("")){
            wBEBCarbonCompanyPbsnDtlDTO.setLastChkDt(null);
        }

        wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyPbsnDtlDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.updateAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBEBCarbonCompanyMstInsertDTO.getFileList());

        WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = wBEBCarbonCompanyMstInsertDTO.getFileDtlList().get(rsumeOrd);
        wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
        wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(maxRsumeOrd);
        wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"+rsumeOrd));
        wBEBCarbonCompanyFileDtlDTO.setRegId(coaAdmDTO.getId());
        wBEBCarbonCompanyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);

        //회원○
        MPAUserDto mPAUserDto= wBEBCarbonCompanyMstInsertDTO.getMemList().get(0);
        mPAUserDto.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        mPAUserDto.setModId(coaAdmDTO.getId());
        mPAUserDto.setModIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.updateAppctnMember(mPAUserDto);

        //부품사○
        WBEBCompanyDTO wBEBCompanyDTO= wBEBCarbonCompanyMstInsertDTO.getCompanyDtl();
        wBEBCompanyDTO.setModId(coaAdmDTO.getId());
        wBEBCompanyDTO.setModIp(coaAdmDTO.getLoginIp());
        wBEBCarbonCompanyMapper.updateAppctnCompany(wBEBCompanyDTO);

        //부품사 SQ○
        if(wBEBCompanyDTO.getCtgryCd().equals("COMPANY01002")){
            wBEBCarbonCompanyMapper.deleteCarbonCompanySQ(wBEBCompanyDTO);
            for(int i=0; i < wBEBCompanyDTO.getDtlList().size(); i++){
                WBEBCompanyDtlDTO wBEBCompanyDtlDTO = wBEBCompanyDTO.getDtlList().get(i);

                if(wBEBCompanyDtlDTO.getYear() == null || wBEBCompanyDtlDTO.getYear().equals("")){
                    wBEBCompanyDtlDTO.setYear(null);
                }
                if(wBEBCompanyDtlDTO.getScore() == null || wBEBCompanyDtlDTO.getScore().equals("")){
                    wBEBCompanyDtlDTO.setScore(null);
                }
                if(wBEBCompanyDtlDTO.getCrtfnCmpnNm() == null || wBEBCompanyDtlDTO.getCrtfnCmpnNm().equals("")){
                    wBEBCompanyDtlDTO.setCrtfnCmpnNm(null);
                }

                int firstMpePartsCompanyDtlSeqIdgen = mpePartsCompanyDtlIdgen.getNextIntegerId();
                wBEBCompanyDtlDTO.setBsnmNo(wBEBCompanyDTO.getBsnmNo());
                wBEBCompanyDtlDTO.setCbsnSeq(firstMpePartsCompanyDtlSeqIdgen);
                wBEBCarbonCompanyMapper.insertCarbonCompanySQ(wBEBCompanyDtlDTO);
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

            if(wBEBCarbonCompanySpprtDTO.getGiveDt() == null || wBEBCarbonCompanySpprtDTO.getGiveDt().isEmpty()) {
                wBEBCarbonCompanySpprtDTO.setGiveDt(null);
            }
            if(wBEBCarbonCompanySpprtDTO.getAccsDt() == null || wBEBCarbonCompanySpprtDTO.getAccsDt().isEmpty()) {
                wBEBCarbonCompanySpprtDTO.setAccsDt(null);
            }

            wBEBCarbonCompanyMapper.updateAppctnSpprt(wBEBCarbonCompanySpprtDTO);
        }


        //최초점검, 최종점검, 검수보고 경우 추가 입력 확인
        int firstAppctnRsumeDtlSeqIdgen = 0;

        //신청 적합
        if(maxRsumeOrd == 1 && "PRO_TYPE01001_02_005".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01002");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01002_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01002_02_006");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
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
            wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //최초점검 적합
        else if(maxRsumeOrd == 3 && "PRO_TYPE01003_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01004");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01004_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01004_02_006");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
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
            wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }
        //최종점검 적합
        else if(maxRsumeOrd == 5 && "PRO_TYPE01005_02_003".equals(wBEBCarbonCompanyDtlDTO.getMngSttsCd())){
            firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01006");
            wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01006_01_001");
            wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01006_02_004");
            wBEBCarbonCompanyDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

            wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd+1);
            wBEBCarbonCompanyPbsnDtlDTO.setRegId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setRegIp(coaAdmDTO.getLoginIp());
            wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);
        }


        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 신청 리스트 삭제
     */
    public int carbonCompanyDeleteList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {


        List<String> appctnSeqList = wBEBCarbonCompanySearchDTO.getDelValueList();

        wBEBCarbonCompanyMapper.carbonCompanyDeleteTrnsf(wBEBCarbonCompanySearchDTO);
        wBEBCarbonCompanyMapper.carbonCompanyDeleteSpprt(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanySearchDTO.setDelValueList(wBEBCarbonCompanyMapper.selectRsumeSeq(wBEBCarbonCompanySearchDTO));

        wBEBCarbonCompanyMapper.carbonCompanyDeletePbsn(wBEBCarbonCompanySearchDTO);
        wBEBCarbonCompanyMapper.carbonCompanyDeleteRsumeFile(wBEBCarbonCompanySearchDTO);

        wBEBCarbonCompanySearchDTO.setDelValueList(appctnSeqList);

        wBEBCarbonCompanyMapper.carbonCompanyDeleteRsume(wBEBCarbonCompanySearchDTO);
        int respCnt = wBEBCarbonCompanyMapper.carbonCompanyDeleteMst(wBEBCarbonCompanySearchDTO);

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

        wBEBCarbonCompanyTrnsfDTO.setList(wBEBCarbonCompanyMapper.getTrnsfList(wBEBCarbonCompanyTrnsfDTO));
        wBEBCarbonCompanyTrnsfDTO.setTotalCount(wBEBCarbonCompanyMapper.getTrnsfListTotCnt(wBEBCarbonCompanyTrnsfDTO));

        return wBEBCarbonCompanyTrnsfDTO;
    }

    /**
     * 신청 진행단계 확인
     */
    public int getRsumePbsnCnt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBEBCarbonCompanyMapper.getRsumePbsnCnt(wBEBCarbonCompanySearchDTO);

        return respCnt;
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
            pbsnList = wBEBCarbonCompanyMapper.selectExcelPbsn(wBEBCarbonCompanySearchDTO.getList().get(i));

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
    public WBEBCompanyDTO selectCompanyUserDtl(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {

        WBEBCompanyDTO wBEBCompanyDTO = new WBEBCompanyDTO();

        wBEBCompanyDTO = wBEBCarbonCompanyMapper.getCompanyInfo(wBEBCarbonCompanySearchDTO);
        List<WBEBCompanyDtlDTO> sqList = wBEBCarbonCompanyMapper.selectSqList(wBEBCarbonCompanySearchDTO);
        wBEBCompanyDTO.setDtlList(sqList);
        wBEBCompanyDTO.setMemSeq(wBEBCarbonCompanySearchDTO.getMemSeq());
        wBEBCompanyDTO.setEpisdSeq(wBEBCarbonCompanySearchDTO.getEpisdSeq());

        return wBEBCompanyDTO;
    }


    /**
     * 사용자 신청 등록
     */
    public int carbonUserInsert(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request) throws Exception {

        int respCnt = 0;

        COUserDetailsDTO cOUserDetailsDTO = null;
        cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

        wBEBCarbonCompanyMstInsertDTO.setRegId(cOUserDetailsDTO.getId());
        wBEBCarbonCompanyMstInsertDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        int firstAppctnMstSeqIdgen = cxAppctnMstSeqIdgen.getNextIntegerId();
        wBEBCarbonCompanyMstInsertDTO.setAppctnSeq(firstAppctnMstSeqIdgen);

        //신청 MST
        respCnt = wBEBCarbonCompanyMapper.insertAppctnMst(wBEBCarbonCompanyMstInsertDTO);

        //신청 DTL
        WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO = new WBEBCarbonCompanyDtlDTO();

        int firstAppctnRsumeDtlSeqIdgen = 0;

        wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
        wBEBCarbonCompanyDtlDTO.setRsumeSttsCd("PRO_TYPE01001");
        wBEBCarbonCompanyDtlDTO.setAppctnSttsCd("PRO_TYPE01001_01_001");
        wBEBCarbonCompanyDtlDTO.setMngSttsCd("PRO_TYPE01001_02_001");
        wBEBCarbonCompanyDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBEBCarbonCompanyDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        firstAppctnRsumeDtlSeqIdgen = cxAppctnRsumeDtlSeqIdgen.getNextIntegerId();
        wBEBCarbonCompanyDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyMapper.insertAppctnDtl(wBEBCarbonCompanyDtlDTO);

        WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO= wBEBCarbonCompanyMstInsertDTO.getPbsnDtlList().get(0);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyPbsnDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBEBCarbonCompanyPbsnDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        wBEBCarbonCompanyMapper.insertAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

        //상생 신청 파일 상세
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wBEBCarbonCompanyMstInsertDTO.getFileList());

        WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = new WBEBCarbonCompanyFileDtlDTO();
        wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(firstAppctnRsumeDtlSeqIdgen);
        wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(1);
        wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeqMap.get("atchFile"));
        wBEBCarbonCompanyFileDtlDTO.setFileCd("ATTACH_FILE_TYPE01");
        wBEBCarbonCompanyFileDtlDTO.setRegId(cOUserDetailsDTO.getId());
        wBEBCarbonCompanyFileDtlDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

        wBEBCarbonCompanyMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);

        //지원금
        for(int i=0; i < 2; i++){
            WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO = new WBEBCarbonCompanySpprtDTO();
            int firstMpePartsCompanyDtlSeqIdgen = cxAppctnSpprtDtlIdgen.getNextIntegerId();
            wBEBCarbonCompanySpprtDTO.setAppctnSpprtSeq(firstMpePartsCompanyDtlSeqIdgen);
            wBEBCarbonCompanySpprtDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());

            if(i==0){
                wBEBCarbonCompanySpprtDTO.setGiveType("PRO_TYPE03001");
                wBEBCarbonCompanySpprtDTO.setAppctnSttsCd("PRO_TYPE03001_01_001");
                wBEBCarbonCompanySpprtDTO.setMngSttsCd("PRO_TYPE03001_02_001");
            }else{
                wBEBCarbonCompanySpprtDTO.setGiveType("PRO_TYPE03002");
                wBEBCarbonCompanySpprtDTO.setAppctnSttsCd("PRO_TYPE03002_01_001");
                wBEBCarbonCompanySpprtDTO.setMngSttsCd("PRO_TYPE03002_02_001");
            }

            wBEBCarbonCompanySpprtDTO.setRegId(cOUserDetailsDTO.getId());
            wBEBCarbonCompanySpprtDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
            wBEBCarbonCompanyMapper.insertAppctnSpprt(wBEBCarbonCompanySpprtDTO);
        }

        //EDM,SMS발송
        WBSendDTO wbSendDTO = new WBSendDTO();
        wbSendDTO.setMemSeq(wBEBCarbonCompanyMstInsertDTO.getMemSeq());
        wbSendDTO.setEpisdSeq(wBEBCarbonCompanyMstInsertDTO.getEpisdSeq());

        wbbbCompanyService.send(wbSendDTO,"SMS04");

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 사용자 신청 수정
     */
    public int carbonUserUpdate(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int respCnt = 0;
        COUserDetailsDTO coaAdmDTO = COUserDetailsHelperService.getAuthenticatedUser();
        wBEBCarbonCompanyMstInsertDTO.setModId(coaAdmDTO.getId());
        wBEBCarbonCompanyMstInsertDTO.setModIp(coaAdmDTO.getLoginIp());

        //선급금 여부
        if(wBEBCarbonCompanyMstInsertDTO.getSpprtList() != null){
            WBEBCarbonCompanySpprtDTO wBEBCarbonCompanySpprtDTO = wBEBCarbonCompanyMstInsertDTO.getSpprtList().get(0);
            wBEBCarbonCompanySpprtDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
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
                        fileSeq = wBEBCarbonCompanyMstInsertDTO.getFileSeqList().get(i);
                    } else {
                        rtnList.get(i).setStatus("success");
                        rtnList.get(i).setFieldNm("fileSeq");
                        fileList.add(rtnList.get(i));
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                        fileSeq = fileSeqMap.get("fileSeq");
                    }

                    //선급금 지급
                    if(wBEBCarbonCompanySpprtDTO.getGiveType().equals("PRO_TYPE03001")){
                        //지원금신청서
                        if(i == 0)wBEBCarbonCompanySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //협약서
                        if(i == 1)wBEBCarbonCompanySpprtDTO.setAgrmtFileSeq(fileSeq);
                        //보증보험증
                        if(i == 2)wBEBCarbonCompanySpprtDTO.setGrnteInsrncFileSeq(fileSeq);
                    }
                    else if(wBEBCarbonCompanySpprtDTO.getGiveType().equals("PRO_TYPE03002")){
                        //지원금신청서
                        if(i == 0)wBEBCarbonCompanySpprtDTO.setSpprtAppctnFileSeq(fileSeq);
                        //거래명세서
                        if(i == 1)wBEBCarbonCompanySpprtDTO.setBlingFileSeq(fileSeq);
                        //매출전표
                        if(i == 2)wBEBCarbonCompanySpprtDTO.setSlsFileSeq(fileSeq);
                        //검수확인서
                        if(i == 3)wBEBCarbonCompanySpprtDTO.setInsptChkFileSeq(fileSeq);
                    }
                }
            }
            wBEBCarbonCompanySpprtDTO.setModId(coaAdmDTO.getId());
            wBEBCarbonCompanySpprtDTO.setModIp(coaAdmDTO.getLoginIp());

            if(wBEBCarbonCompanySpprtDTO.getGiveDt() != "" ){
                wBEBCarbonCompanyMapper.updateAppctnSpprt(wBEBCarbonCompanySpprtDTO);
            }

        }else{
            int maxRsumeOrd = wBEBCarbonCompanyMstInsertDTO.getMaxRsumeOrd();
            int rsumeOrd = maxRsumeOrd - 1;

            //신청 MST ○
            respCnt = wBEBCarbonCompanyMapper.updateAppctnMst(wBEBCarbonCompanyMstInsertDTO);

            //신청 DTL ○
            WBEBCarbonCompanyDtlDTO wBEBCarbonCompanyDtlDTO = new WBEBCarbonCompanyDtlDTO();

            wBEBCarbonCompanyDtlDTO = wBEBCarbonCompanyMstInsertDTO.getRsumeDtlList().get(0);

            wBEBCarbonCompanyDtlDTO.setAppctnSeq(wBEBCarbonCompanyMstInsertDTO.getAppctnSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
            wBEBCarbonCompanyDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBEBCarbonCompanyDtlDTO.setModId(coaAdmDTO.getId());
            wBEBCarbonCompanyDtlDTO.setModIp(coaAdmDTO.getLoginIp());

            wBEBCarbonCompanyMapper.updateAppctnDtl(wBEBCarbonCompanyDtlDTO);

            WBEBCarbonCompanyPbsnDtlDTO wBEBCarbonCompanyPbsnDtlDTO = new WBEBCarbonCompanyPbsnDtlDTO();
            //Pbsn ○
            wBEBCarbonCompanyPbsnDtlDTO = wBEBCarbonCompanyMstInsertDTO.getPbsnDtlList().get(0);

            if(wBEBCarbonCompanyPbsnDtlDTO.getBsnPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getBsnPmt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setBsnPmt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getBsnPlanDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getBsnPlanDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setBsnPlanDt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getSpprtPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getSpprtPmt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setSpprtPmt(null);
            }else{
                wBEBCarbonCompanyPbsnDtlDTO.setSpprtPmt(wBEBCarbonCompanyPbsnDtlDTO.getSpprtPmt().replaceAll(",",""));
            }

            if(wBEBCarbonCompanyPbsnDtlDTO.getPhswPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getPhswPmt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setPhswPmt(null);
            }else{
                wBEBCarbonCompanyPbsnDtlDTO.setPhswPmt(wBEBCarbonCompanyPbsnDtlDTO.getPhswPmt().replaceAll(",",""));
            }

            if(wBEBCarbonCompanyPbsnDtlDTO.getTtlPmt() == null || wBEBCarbonCompanyPbsnDtlDTO.getTtlPmt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setTtlPmt(null);
            }else{
                wBEBCarbonCompanyPbsnDtlDTO.setTtlPmt(wBEBCarbonCompanyPbsnDtlDTO.getTtlPmt().replaceAll(",",""));
            }

            if(wBEBCarbonCompanyPbsnDtlDTO.getChkDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getChkDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setChkDt(null);
            }

            if(wBEBCarbonCompanyPbsnDtlDTO.getExamScore() == null || wBEBCarbonCompanyPbsnDtlDTO.getExamScore().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setExamScore(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getPayDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getPayDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setPayDt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getNslltSchdlDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getNslltSchdlDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setNslltSchdlDt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getAccsDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getAccsDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setAccsDt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getGiveSeq() == null || wBEBCarbonCompanyPbsnDtlDTO.getGiveSeq().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setGiveSeq(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getCmpltnBrfngDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getCmpltnBrfngDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setCmpltnBrfngDt(null);
            }
            if(wBEBCarbonCompanyPbsnDtlDTO.getLastChkDt() == null || wBEBCarbonCompanyPbsnDtlDTO.getLastChkDt().equals("")){
                wBEBCarbonCompanyPbsnDtlDTO.setLastChkDt(null);
            }

            wBEBCarbonCompanyPbsnDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
            wBEBCarbonCompanyPbsnDtlDTO.setRsumeOrd(maxRsumeOrd);
            wBEBCarbonCompanyPbsnDtlDTO.setModId(coaAdmDTO.getId());
            wBEBCarbonCompanyPbsnDtlDTO.setModIp(coaAdmDTO.getLoginIp());
            wBEBCarbonCompanyMapper.updateAppctnPbsnDtl(wBEBCarbonCompanyPbsnDtlDTO);

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
                    fileSeq = wBEBCarbonCompanyMstInsertDTO.getFileSeqList().get(0);
                } else {
                    rtnList.get(0).setStatus("success");
                    rtnList.get(0).setFieldNm("fileSeq");
                    fileList.add(rtnList.get(0));
                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                    fileSeq = fileSeqMap.get("fileSeq");
                }

                WBEBCarbonCompanyFileDtlDTO wBEBCarbonCompanyFileDtlDTO = wBEBCarbonCompanyMstInsertDTO.getFileDtlList().get(0);

                wBEBCarbonCompanyFileDtlDTO.setRsumeSeq(wBEBCarbonCompanyMstInsertDTO.getRsumeSeq());
                wBEBCarbonCompanyFileDtlDTO.setRsumeOrd(maxRsumeOrd);
                wBEBCarbonCompanyFileDtlDTO.setFileSeq(fileSeq);
                wBEBCarbonCompanyFileDtlDTO.setRegId(coaAdmDTO.getId());
                wBEBCarbonCompanyFileDtlDTO.setRegIp(coaAdmDTO.getLoginIp());

                wBEBCarbonCompanyMapper.insertAppctnFileDtl(wBEBCarbonCompanyFileDtlDTO);
            }
        }

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);


        return respCnt;
    }

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    public int getInsertBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBEBCarbonCompanyMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));

        WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO = new WBEBCarbonCompanySearchDTO();
        wBEBCarbonCompanySearchDTO.setDetailsKey(wBEBCarbonCompanyMstInsertDTO.getDetailsKey());
//        respCnt = wBEBCarbonCompanyMapper.getBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO);
        respCnt = wBEBCarbonCompanyMapper.getSbrdnBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO);

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
//          int cnt = wBEBCarbonCompanyMapper.getBsnmNoSeqCnt(wBEBCarbonCompanyMstInsertDTO);
            int cnt = wBEBCarbonCompanyMapper.getSbrdnBsnmNoSeqCnt(wBEBCarbonCompanyMstInsertDTO);
           if(cnt > 0 ){
               respCnt = 999;
           }
        }

        return respCnt;
    }

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    public int getInsertSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception {

        int respCnt = 0;

//        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBEBCarbonCompanyMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));
        WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO = new WBEBCarbonCompanySearchDTO();
        wBEBCarbonCompanySearchDTO.setDetailsKey(wBEBCarbonCompanyMstInsertDTO.getDetailsKey());
        respCnt = wBEBCarbonCompanyMapper.getSbrdnBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO);

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        if(respCnt > 0 ){
            int cnt = wBEBCarbonCompanyMapper.getSbrdnBsnmNoSeqCnt(wBEBCarbonCompanyMstInsertDTO);
            if(cnt > 0 ){
                respCnt = 999;
            }
        }

        return respCnt;
    }

    /**
     * 수정 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBEBCarbonCompanyMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));

        WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO = new WBEBCarbonCompanySearchDTO();
        wBEBCarbonCompanySearchDTO.setDetailsKey(wBEBCarbonCompanyMstInsertDTO.getDetailsKey());
        WBEBCarbonCompanyMstInsertDTO wBEBTrnsfDTO = wBEBCarbonCompanyMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);

        System.out.println("11wBEBTrnsfDTO.getMemSeq()  " + wBEBTrnsfDTO.getMemSeq());

        System.out.println("11wBEBCarbonCompanyMstInsertDTO.getMemSeq()   " + wBEBCarbonCompanyMstInsertDTO.getMemSeq());

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBEBCarbonCompanyMstInsertDTO.getMemSeq()))
        {
            respCnt = wBEBCarbonCompanyMapper.getBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO);
        }

        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    public int getSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO) throws Exception {

        int respCnt = 0;

        wBEBCarbonCompanyMstInsertDTO.setEpisdSeq(wBEBCarbonCompanyMapper.selectEpisdSeq(wBEBCarbonCompanyMstInsertDTO));
        WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO = new WBEBCarbonCompanySearchDTO();
        wBEBCarbonCompanySearchDTO.setDetailsKey(wBEBCarbonCompanyMstInsertDTO.getDetailsKey());
        WBEBCarbonCompanyMstInsertDTO wBEBTrnsfDTO = wBEBCarbonCompanyMapper.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO);


        System.out.println("22wBEBTrnsfDTO.getMemSeq()  " + wBEBTrnsfDTO.getMemSeq());

        System.out.println("22wBEBCarbonCompanyMstInsertDTO.getMemSeq()   " + wBEBCarbonCompanyMstInsertDTO.getMemSeq());

        if(!wBEBTrnsfDTO.getMemSeq().equals(wBEBCarbonCompanyMstInsertDTO.getMemSeq()))
        {
            respCnt = wBEBCarbonCompanyMapper.getSbrdnBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO);
        }
        wBEBCarbonCompanyMstInsertDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     *  Edit Page
     *  관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wBEBCarbonCompanyMapper.updAdmMemo(wBEBCarbonCompanySearchDTO);

        return respCnt;
    }
}
