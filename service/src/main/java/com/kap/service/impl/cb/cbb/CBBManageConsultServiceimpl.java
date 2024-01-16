package com.kap.service.impl.cb.cbb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COSystemLogDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cbb.*;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.*;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.cb.cbb.CBBManageConsultMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 경영컨설팅 서비스 구현 클래스
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.11.21  이옥정         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CBBManageConsultServiceimpl implements CBBManageConsultService {

    private final CBBManageConsultMapper cBBManageConsultMapper;

    /** 공통 서비스 **/
    private final COCommService cOCommService;

    /* 파일 서비스 */
    private final COFileService cOFileService;

    private final MPEPartsCompanyService mPEPartsCompanyService;

    private final MPAUserService mpaUserService;

    /* 경영 컨설팅 시퀀스 */
    private final EgovIdGnrService cosultSeqIdgen;
    private final EgovIdGnrService dpndnSeqIdgen;
    private final EgovIdGnrService dlvrySeqIdgen;
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;
    private final EgovIdGnrService consTrnsfSeqIdgen;
    private final EgovIdGnrService picSeqIdgen;

    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;
    private final MPAUserMapper mpaUserMapper;


    /*로그 서비스*/
    private final COSystemLogService cOSystemLogService;

    /**
     * 리스트 조회
     */
    public CBBManageConsultSearchDTO selectManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(cBBManageConsultSearchDTO.getPageIndex());
        if("Y".equals(cBBManageConsultSearchDTO.getExcelYn())) {
            page.setRecordCountPerPage(cBBManageConsultMapper.getManageConsultListCnt(cBBManageConsultSearchDTO));
        }else{
            page.setRecordCountPerPage(cBBManageConsultSearchDTO.getListRowSize());
        }
        page.setPageSize(cBBManageConsultSearchDTO.getPageRowSize());
        cBBManageConsultSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        cBBManageConsultSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        cBBManageConsultSearchDTO.setTotalCount(cBBManageConsultMapper.getManageConsultListCnt(cBBManageConsultSearchDTO));
        cBBManageConsultSearchDTO.setList(cBBManageConsultMapper.selectManageConsultList(cBBManageConsultSearchDTO));
        return cBBManageConsultSearchDTO;
    }

    /**
     * 만족도 종합 결과 리스트 조회
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(cBBConsultSuveyRsltListDTO.getPageIndex());
        page.setRecordCountPerPage(cBBConsultSuveyRsltListDTO.getListRowSize());
        page.setPageSize(cBBConsultSuveyRsltListDTO.getPageRowSize());
        cBBConsultSuveyRsltListDTO.setFirstIndex(page.getFirstRecordIndex());
        cBBConsultSuveyRsltListDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        cBBConsultSuveyRsltListDTO.setTotalCount(cBBManageConsultMapper.getConsultSuveyRsltCnt(cBBConsultSuveyRsltListDTO));
        cBBConsultSuveyRsltListDTO.setList(cBBManageConsultMapper.selectConsultSuveyRsltList(cBBConsultSuveyRsltListDTO));
        return cBBConsultSuveyRsltListDTO;
    }
    /**
     * 컨설팅 경영 컨설팅 상세 조회
     */
    public CBBManageConsultInsertDTO selectManageConsultDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception {
        MPEPartsCompanyDTO companyInfo = new MPEPartsCompanyDTO();

        if (!"".equals(pCBBManageConsultInsertDTO.getDetailsKey()))
        {
            pCBBManageConsultInsertDTO = cBBManageConsultMapper.selectManageConsultDtl(pCBBManageConsultInsertDTO);
            MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
            mPEPartsCompanyDTO.setBsnmNo(pCBBManageConsultInsertDTO.getBsnmNo().replace("-", ""));
            companyInfo = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);

            for(int i =0; i<1; i++){
                pCBBManageConsultInsertDTO.setCmpnNm(companyInfo.getList().get(i).getCmpnNm());
                pCBBManageConsultInsertDTO.setRprsntNm(companyInfo.getList().get(i).getRprsntNm());
                pCBBManageConsultInsertDTO.setStbsmDt(companyInfo.getList().get(i).getStbsmDt());
                pCBBManageConsultInsertDTO.setCtgryCd(companyInfo.getList().get(i).getCtgryCd());
                pCBBManageConsultInsertDTO.setBscAddr(companyInfo.getList().get(i).getBscAddr());
                pCBBManageConsultInsertDTO.setDtlAddr(companyInfo.getList().get(i).getDtlAddr());
                pCBBManageConsultInsertDTO.setZipcode(companyInfo.getList().get(i).getZipcode());
                pCBBManageConsultInsertDTO.setSlsPmt(companyInfo.getList().get(i).getSlsPmt());
                pCBBManageConsultInsertDTO.setSlsYear(companyInfo.getList().get(i).getSlsYear());
                pCBBManageConsultInsertDTO.setMpleCnt(companyInfo.getList().get(i).getMpleCnt());
                pCBBManageConsultInsertDTO.setMjrPrdct1(companyInfo.getList().get(i).getMjrPrdct1());
                pCBBManageConsultInsertDTO.setMjrPrdct2(companyInfo.getList().get(i).getMjrPrdct2());
                pCBBManageConsultInsertDTO.setMjrPrdct3(companyInfo.getList().get(i).getMjrPrdct3());
                pCBBManageConsultInsertDTO.setCmpnTelNo(companyInfo.getList().get(i).getTelNo());
            }

            for(int j=0; j<companyInfo.getList().size(); j++){
                List sqlInfoList = new ArrayList();
                sqlInfoList.add(0,companyInfo.getList().get(j).getNm());
                sqlInfoList.add(1,String.valueOf(companyInfo.getList().get(j).getScore()));
                sqlInfoList.add(2,String.valueOf(companyInfo.getList().get(j).getYear()));
                sqlInfoList.add(3,companyInfo.getList().get(j).getCrtfnCmpnNm());
                sqlInfoList.add(4,companyInfo.getList().get(j).getCbsnSeq());
                if(j==0){
                    pCBBManageConsultInsertDTO.setSqInfoList(sqlInfoList);
                }else if(j==1){
                    pCBBManageConsultInsertDTO.setSqInfoList1(sqlInfoList);
                }else{
                    pCBBManageConsultInsertDTO.setSqInfoList2(sqlInfoList);
                }
            }
            int cnstgSeq = pCBBManageConsultInsertDTO.getCnstgSeq();

            List<CBBManageConsultInsertDTO> dlvryInfo = cBBManageConsultMapper.selectCnstgDlvryInfo(cnstgSeq);
            List dlvryInfoList = new ArrayList();
            for(int i=0; i<dlvryInfo.size(); i++){
                CBBManageConsultInsertDTO dlvryDto = new CBBManageConsultInsertDTO();
                dlvryDto.setDlvryCmpnNm(dlvryInfo.get(i).getDlvryCmpnNm());
                dlvryDto.setDlvryRate(dlvryInfo.get(i).getDlvryRate());
                dlvryDto.setCmpnDlvrySeq(dlvryInfo.get(i).getCmpnDlvrySeq());
                dlvryInfoList.add(dlvryDto);
            }
            pCBBManageConsultInsertDTO.setDlvryCmpnList(dlvryInfoList);

            List<CBBManageConsultInsertDTO> dpndnInfo = cBBManageConsultMapper.selectCnstgDpndnInfo(cnstgSeq);
            List dpndnInfoList = new ArrayList();
            for(int i=0; i<dpndnInfo.size(); i++){
                CBBManageConsultInsertDTO dpndnDto = new CBBManageConsultInsertDTO();
                dpndnDto.setDpndnCmpnNm(dpndnInfo.get(i).getDpndnCmpnNm());
                dpndnDto.setDpndnRate(dpndnInfo.get(i).getDpndnRate());
                dpndnDto.setDpndnSeq(dpndnInfo.get(i).getDpndnSeq());
                dpndnInfoList.add(dpndnDto);
            }
            pCBBManageConsultInsertDTO.setDpndCmpnList(dpndnInfoList);

            List<CBBManageConsultInsertDTO> picInfo = cBBManageConsultMapper.selectCnstgPicInfo(cnstgSeq);
            List picInfoList = new ArrayList();

            for(int i=0; i<picInfo.size(); i++){
                CBBManageConsultInsertDTO picInfoDto = new CBBManageConsultInsertDTO();
                picInfoDto.setPicSeq(picInfo.get(i).getPicSeq());
                picInfoDto.setPicName(picInfo.get(i).getPicName());
                picInfoDto.setPicEmail(picInfo.get(i).getPicEmail());
                picInfoDto.setPicDeptNm(picInfo.get(i).getPicDeptNm());
                picInfoDto.setPicPstnNm(picInfo.get(i).getPicPstnNm());
                picInfoDto.setCmssrHpNo(picInfo.get(i).getCmssrHpNo());
                picInfoDto.setCmssrCmpnTelNo(picInfo.get(i).getCmssrCmpnTelNo());
                picInfoList.add(picInfoDto);
            }
            pCBBManageConsultInsertDTO.setPicInfoLIst(picInfoList);

            pCBBManageConsultInsertDTO.setSurveyInfoList(cBBManageConsultMapper.selectTechGuidanceSurvey(cnstgSeq));
        }
        pCBBManageConsultInsertDTO.setRsumeList(cBBManageConsultMapper.selectTechGuidanceRsume(pCBBManageConsultInsertDTO));


        return pCBBManageConsultInsertDTO;
    }

    /**
     * 경영 컨설팅 등록
     */
    public int insertManageConsult(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, MultipartHttpServletRequest multiRequest) throws Exception {

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBBManageConsultInsertDTO.getFileList());
        pCBBManageConsultInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBBManageConsultInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));
        pCBBManageConsultInsertDTO.setCnstgSeq(cosultSeqIdgen.getNextIntegerId());

        pCBBManageConsultInsertDTO.setBsnmNo(pCBBManageConsultInsertDTO.getBsnmNo().replaceAll("-", ""));

        // 신청 회원 정보
        updateMngConMemberInfo(pCBBManageConsultInsertDTO);

        // 부품사 정보
        updateTechCompanyInfo(pCBBManageConsultInsertDTO);

        // 컨설팅 서브 정보 수정
        updateSubMngConInfo(pCBBManageConsultInsertDTO);

        // 담당임원상세
        updateConsultingPicInfo(pCBBManageConsultInsertDTO);

        pCBBManageConsultInsertDTO.setRespCnt(cBBManageConsultMapper.insertManageConsult(pCBBManageConsultInsertDTO));
        pCBBManageConsultInsertDTO.getRespCnt();

        return  pCBBManageConsultInsertDTO.getRespCnt();
    }

    /**
     * 부품사 회원 정보 수정
     */
    void updateMngConMemberInfo(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception {

        MPAUserDto mPAUserDto = new MPAUserDto();
        mPAUserDto.setDetailsKey(pCBBManageConsultInsertDTO.getMemSeq());

        mPAUserDto = mpaUserService.selectUserDtlTab(mPAUserDto);

        if(mPAUserDto != null){
            mPAUserDto.setRegId(pCBBManageConsultInsertDTO.getRegId());
            mPAUserDto.setRegIp(pCBBManageConsultInsertDTO.getRegIp());
            mPAUserDto.setMemSeq(Integer.valueOf(pCBBManageConsultInsertDTO.getMemSeq()));
            mPAUserDto.setDeptCd(pCBBManageConsultInsertDTO.getDeptCd());
            mPAUserDto.setDeptDtlNm(pCBBManageConsultInsertDTO.getDeptDtlNm());
            mPAUserDto.setPstnCd(pCBBManageConsultInsertDTO.getPstnCd());
            mPAUserDto.setTelNo(pCBBManageConsultInsertDTO.getTelNo());
            mPAUserDto.setHpNo(pCBBManageConsultInsertDTO.getHpNo());
            mPAUserDto.setChngFndn("N");
            mpaUserService.updateUserDtl(mPAUserDto);
        }
    }

    /**
     * 부품사 정보 수정
     */
    void updateTechCompanyInfo(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception {

        MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
        mPEPartsCompanyDTO.setBsnmNo(pCBBManageConsultInsertDTO.getBsnmNo().replace("-", ""));
        mPEPartsCompanyDTO = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);

        String[] cbsnSeq = {};
        String[] nm = {};
        String[] score = {};
        String[] year = {};
        String[] crtfnCmpnNm = {};

        String ctgryCd = pCBBManageConsultInsertDTO.getCtgryCd();
        // 회사 업종 상세 등록
        if(ctgryCd.equals("COMPANY01002")){
            cbsnSeq =  pCBBManageConsultInsertDTO.getCbsnSeq().split(",");
            nm =  pCBBManageConsultInsertDTO.getNm().split(",");
            score = pCBBManageConsultInsertDTO.getScore().split(",");
            year = pCBBManageConsultInsertDTO.getYear().split(",");
            crtfnCmpnNm = pCBBManageConsultInsertDTO.getCrtfnCmpnNm().split(",");
        }
        MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();

        // 1차사 - 5스타
        if(ctgryCd.equals("COMPANY01001")){
            mPEPartsCompanyDTO.setQlty5StarCd(pCBBManageConsultInsertDTO.getQlty5StarCd());
            mPEPartsCompanyDTO.setQlty5StarYear(pCBBManageConsultInsertDTO.getQlty5StarYear());
            mPEPartsCompanyDTO.setPay5StarCd(pCBBManageConsultInsertDTO.getPay5StarCd());
            mPEPartsCompanyDTO.setPay5StarYear(pCBBManageConsultInsertDTO.getPay5StarYear());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBBManageConsultInsertDTO.getTchlg5StarCd());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBBManageConsultInsertDTO.getTchlg5StarCd());
        } else if(ctgryCd.equals("COMPANY01002")){ // 2차사 - SQ 정보

            HashMap cbsnCdMap = new HashMap();

            for(int i=0; i < cbsnCdMap.size(); i++) {
                mpePartsCompanyDTO.setNm(nm[i]);
                mpePartsCompanyDTO.setCbsnSeq(Integer.valueOf(cbsnSeq[i]));
                mpePartsCompanyDTO.setScore(Integer.valueOf(score[i]));
                mpePartsCompanyDTO.setYear(Integer.valueOf(year[i]));
                mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmpnNm[i]);
                mpePartsCompanyDTO.setBsnmNo(mPEPartsCompanyDTO.getBsnmNo());

                int cnt = cBBManageConsultMapper.selectCmpnCbsnInfoCnt(Integer.valueOf(cbsnSeq[i]));

                if(cnt >= 1){
                    mpePartsCompanyDTO.setModId(pCBBManageConsultInsertDTO.getRegId());
                    mpePartsCompanyDTO.setModId(pCBBManageConsultInsertDTO.getRegIp());
                    mpePartsCompanyMapper.updatePartsComSQInfo(mpePartsCompanyDTO);
                }else if(cnt == 0){
                    mpePartsCompanyDtlIdgen.getNextIntegerId();
                    mpePartsCompanyDTO.setRegId(pCBBManageConsultInsertDTO.getRegId());
                    mpePartsCompanyDTO.setRegId(pCBBManageConsultInsertDTO.getRegIp());
                    mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                }
            }
        }
        mPEPartsCompanyDTO.setModId(pCBBManageConsultInsertDTO.getRegId());
        mPEPartsCompanyDTO.setModIp(pCBBManageConsultInsertDTO.getRegIp());
        mPEPartsCompanyDTO.setRprsntNm(pCBBManageConsultInsertDTO.getRprsntNm());
        mPEPartsCompanyDTO.setCmpnNm(pCBBManageConsultInsertDTO.getCmpnNm());
        mPEPartsCompanyDTO.setCmpnNfrmlNm(pCBBManageConsultInsertDTO.getCmpnNfrmlNm());
        mPEPartsCompanyDTO.setCmpnCd(pCBBManageConsultInsertDTO.getCmpnCd());
        mPEPartsCompanyDTO.setTelNo(pCBBManageConsultInsertDTO.getCmpnTelNo()); // 부품사 일반 전화번호
        mPEPartsCompanyDTO.setCtgryCd(pCBBManageConsultInsertDTO.getCtgryCd());
        mPEPartsCompanyDTO.setSizeCd(pCBBManageConsultInsertDTO.getSizeCd());
        mPEPartsCompanyDTO.setStbsmDt(pCBBManageConsultInsertDTO.getStbsmDt());
        mPEPartsCompanyDTO.setZipcode(pCBBManageConsultInsertDTO.getZipcode());
        mPEPartsCompanyDTO.setBscAddr(pCBBManageConsultInsertDTO.getBscAddr());
        mPEPartsCompanyDTO.setDtlAddr(pCBBManageConsultInsertDTO.getDtlAddr());
        mPEPartsCompanyDTO.setSlsPmt(pCBBManageConsultInsertDTO.getSlsPmt());
        mPEPartsCompanyDTO.setSlsYear(pCBBManageConsultInsertDTO.getSlsYear());
        mPEPartsCompanyDTO.setMpleCnt(pCBBManageConsultInsertDTO.getMpleCnt());
        mPEPartsCompanyDTO.setMjrPrdct1(pCBBManageConsultInsertDTO.getMjrPrdct1());
        mPEPartsCompanyDTO.setMjrPrdct2(pCBBManageConsultInsertDTO.getMjrPrdct2());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBBManageConsultInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBBManageConsultInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBBManageConsultInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setRegId(pCBBManageConsultInsertDTO.getRegId());
        mPEPartsCompanyDTO.setRegId(pCBBManageConsultInsertDTO.getRegIp());

        mpePartsCompanyMapper.updatePartsCompany(mPEPartsCompanyDTO);
    }

    /**
     * 컨설팅 서브 정보 수정
     */
    void updateSubMngConInfo(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception {

        // 고객사 비율 상세 정보 등록, 있으면 수정
        String[] dlvryCmpnNm =  pCBBManageConsultInsertDTO.getDlvryCmpnNm().split(",");
        String[] dlvryRate = pCBBManageConsultInsertDTO.getDlvryRate().split(",");
        HashMap cnstgDlyvMap = new HashMap();

        CBBManageConsultSearchDTO cBBManageConsultSearchDTO = new CBBManageConsultSearchDTO();
        List delValueList = new ArrayList();
        delValueList.add(pCBBManageConsultInsertDTO.getCnstgSeq());
        cBBManageConsultSearchDTO.setDelValueList(delValueList);

        cBBManageConsultMapper.deleteConsultDlvryDtl(cBBManageConsultSearchDTO);

        for(int i=0; i < dlvryCmpnNm.length; i++) {
            cnstgDlyvMap.put("cnstgSeq", pCBBManageConsultInsertDTO.getCnstgSeq());
            cnstgDlyvMap.put("cmpnDlvrySeq",dlvrySeqIdgen.getNextIntegerId());
            cnstgDlyvMap.put("dlvryCmpnNm", dlvryCmpnNm[i]);
            cnstgDlyvMap.put("dlvryRate", dlvryRate[i]);
            cnstgDlyvMap.put("regId", pCBBManageConsultInsertDTO.getRegId());
            cnstgDlyvMap.put("regIp", pCBBManageConsultInsertDTO.getRegIp());


            cBBManageConsultMapper.insertCnstgDlvryInfo(cnstgDlyvMap);

        }
        // 완성차 의존율 상세 정보 등록 있으면 수정
        String[] dpbdnCmpnNm =  pCBBManageConsultInsertDTO.getDpndnCmpnNm().split(",");
        String[] dpbdnRate = pCBBManageConsultInsertDTO.getDpndnRate().split(",");
        HashMap dpndnMap = new HashMap();

        cBBManageConsultMapper.deleteConsultDpndnDtl(cBBManageConsultSearchDTO);

        for(int i=0; i < dpbdnCmpnNm.length; i++){
            dpndnMap.put("cnstgSeq",pCBBManageConsultInsertDTO.getCnstgSeq());
            dpndnMap.put("dpndnSeq",dpndnSeqIdgen.getNextIntegerId());
            dpndnMap.put("cmpnNm", dpbdnCmpnNm[i]);
            dpndnMap.put("dpndnRate", dpbdnRate[i]);
            dpndnMap.put("regId", pCBBManageConsultInsertDTO.getRegId());
            dpndnMap.put("regIp", pCBBManageConsultInsertDTO.getRegIp());

            cBBManageConsultMapper.insertCnstgDpndnInfo(dpndnMap);
        }

    }

    /**
     * 컨설팅 담당 임원 정보
     */
    void updateConsultingPicInfo(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO) throws Exception {
        HashMap picInfoMap = new HashMap();
        picInfoMap.put("cnstgSeq", pCBBManageConsultInsertDTO.getCnstgSeq());
        picInfoMap.put("picSeq", picSeqIdgen.getNextIntegerId());
        picInfoMap.put("name", pCBBManageConsultInsertDTO.getPicName());
        picInfoMap.put("email", pCBBManageConsultInsertDTO.getPicEmail());
        picInfoMap.put("deptNm", pCBBManageConsultInsertDTO.getPicDeptNm());
        picInfoMap.put("pstnNm", pCBBManageConsultInsertDTO.getPicPstnNm());
        picInfoMap.put("hpNo", pCBBManageConsultInsertDTO.getCmssrHpNo());
        picInfoMap.put("cmpnTelNo", pCBBManageConsultInsertDTO.getCmssrCmpnTelNo());
        picInfoMap.put("regId", pCBBManageConsultInsertDTO.getRegId());
        picInfoMap.put("regIp", pCBBManageConsultInsertDTO.getRegIp());

        List picInfoList = cBBManageConsultMapper.selectCnstgPicInfo(pCBBManageConsultInsertDTO.getCnstgSeq());
        if(picInfoList.size() > 0){
            cBBManageConsultMapper.deleteCnstgPicInfo(picInfoMap);
            cBBManageConsultMapper.insertCnstgPicInfo(picInfoMap);
        }else{
            cBBManageConsultMapper.insertCnstgPicInfo(picInfoMap);
        }
    }

    /**
     * 경영 컨설팅 수정
     */
    public int updateManageConsultDtl(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, CBBManageConsultUpdateDTO pCBBManageConsultUpdateDTO) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBBManageConsultInsertDTO.getFileList());
        pCBBManageConsultInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBBManageConsultInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));

        pCBBManageConsultUpdateDTO.setInitVstFileSeq(fileSeqMap.get("initVstFileSeq")); // 초도방문자료
        pCBBManageConsultUpdateDTO.setKickfFileSeq(fileSeqMap.get("kickfFileSeq")); // 킥오프자료
        pCBBManageConsultUpdateDTO.setLvlupFileSeq(fileSeqMap.get("lvlupFileSeq")); // 랩업자료
        pCBBManageConsultUpdateDTO.setEtcFileSeq(fileSeqMap.get("etcFileSeq")); // 기타사업자료

        pCBBManageConsultInsertDTO.setRegIp(pCBBManageConsultInsertDTO.getRegIp());
        pCBBManageConsultInsertDTO.setRegId(pCBBManageConsultInsertDTO.getRegId());

        pCBBManageConsultInsertDTO.setCnstgSeq(Integer.valueOf(pCBBManageConsultInsertDTO.getDetailsKey()));
        pCBBManageConsultInsertDTO.setBsnmNo(pCBBManageConsultInsertDTO.getBsnmNo().replaceAll("-", ""));
        
        // 신청 회원 정보
        updateMngConMemberInfo(pCBBManageConsultInsertDTO);

        // 부품사 정보
        updateTechCompanyInfo(pCBBManageConsultInsertDTO);

        // 컨설팅 서브 정보 수정
        updateSubMngConInfo(pCBBManageConsultInsertDTO);

        // 담당임원상세
        updateConsultingPicInfo(pCBBManageConsultInsertDTO);

        int cnstgSeq = pCBBManageConsultInsertDTO.getCnstgSeq();

        pCBBManageConsultInsertDTO.setRespCnt(cBBManageConsultMapper.updateManageConsult(pCBBManageConsultInsertDTO));


        int totCnt = cBBManageConsultMapper.selectRsumeTotCnt(cnstgSeq);
        pCBBManageConsultUpdateDTO.setCnstgSeq(cnstgSeq);
        if(totCnt>0){
            pCBBManageConsultInsertDTO.setModIp(pCBBManageConsultInsertDTO.getRegIp());
            pCBBManageConsultInsertDTO.setModId(pCBBManageConsultInsertDTO.getRegId());
            cBBManageConsultMapper.updateManageConsultRsume(pCBBManageConsultUpdateDTO);
        }else if(totCnt == 0){
            cBBManageConsultMapper.insertManageConsultRsume(pCBBManageConsultUpdateDTO);
        }

        String bfreMemSeq = pCBBManageConsultInsertDTO.getBfreMemSeq();
        String aftrMemSeq = pCBBManageConsultInsertDTO.getMemSeq();
        if(!bfreMemSeq.equals(aftrMemSeq)){
            pCBBManageConsultInsertDTO.setTrnsfSeq(consTrnsfSeqIdgen.getNextIntegerId());
            pCBBManageConsultInsertDTO.setAftrMemSeq(aftrMemSeq);
            cBBManageConsultMapper.insertTrsfGuidanceList(pCBBManageConsultInsertDTO);
        }

        return pCBBManageConsultInsertDTO.getRespCnt();
    }

    /**
     * 컨설팅사업 경영컨설팅 삭제
     */
    @Transactional
    public int deleteManageConsult(CBBManageConsultSearchDTO cBBManageConsultSearchDTO) throws Exception
    {
        int actCnt = 0;

        // 참여이관로그 테이블 삭제
        cBBManageConsultMapper.deleteConsultAppctnTrnsfDtl(cBBManageConsultSearchDTO);
        // 만족도 결과 상세 테이블 삭제
        cBBManageConsultMapper.deleteConsultSrvRsltDtl(cBBManageConsultSearchDTO);
        // 완성차의존율 상세 테이블 삭제
        cBBManageConsultMapper.deleteConsultDpndnDtl(cBBManageConsultSearchDTO);
        // 고객사비율 상세 테이블 삭제
        cBBManageConsultMapper.deleteConsultDlvryDtl(cBBManageConsultSearchDTO);
        // 담당임원 테이블 삭제
        cBBManageConsultMapper.deleteConsultPicDtl(cBBManageConsultSearchDTO);
        // 진행마스터 테이블 삭제
        cBBManageConsultMapper.deleteConsultRsumeMst(cBBManageConsultSearchDTO);

        actCnt = cBBManageConsultMapper.deleteManageConsult(cBBManageConsultSearchDTO);

        return actCnt;
    }

    /**
     * 엑셀 생성
     */
    public void excelDownload(CBBManageConsultSearchDTO cBBManageConsultSearchDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("매출액(억원)");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("직원수");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("주샌상품");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("신청분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("신청 소재지");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("SQ 인증 주관사");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("주고객사");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("방문일");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("담당위원 업종/분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("킥오프일");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("신청일");
        cell.setCellStyle(style_header);

        // Body
        List<CBBManageConsultListDTO> list = cBBManageConsultSearchDTO.getList();
        System.err.println("list.size():::"+list.size());
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(cBBManageConsultSearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getRsumeSttsNm());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업자등록번호
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getAppctnBsnmNo());
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getCtgryNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getSizeNm());
            cell.setCellStyle(style_body);

            //매출액(억원)
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSlsPmt() == null ? 0 : list.get(i).getSlsPmt());
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getMpleCnt() == null ? 0 : list.get(i).getMpleCnt());
            cell.setCellStyle(style_body);

            //주생산품
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getMjrPrdct1() == null ? "-" : list.get(i).getMjrPrdct1() +"/"+ list.get(i).getMjrPrdct2() +"/"+ list.get(i).getMjrPrdct3());
            cell.setCellStyle(style_body);

            //신청분야
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getAppctnFldNm());
            cell.setCellStyle(style_body);

            //신청소재지
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getFirstRgnsNm() +" "+ list.get(i).getScndRgnsNm());
            cell.setCellStyle(style_body);

            //SQ 인증 주관사
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getCrtfnCmpnNm());
            cell.setCellStyle(style_body);

            //주고객사
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getMnCmpnNm());
            cell.setCellStyle(style_body);

            //방문일
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getVstDt() == null ? "-" : list.get(i).getVstDt());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getCmssrNm() == null ? "-" : list.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //담당위원 업종/분야
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getCmssrCbsnNm() == null ? "-" : list.get(i).getCmssrCbsnNm());
            cell.setCellStyle(style_body);

            //킥오프일
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getCnstgKickfDt() == null ? "-" : list.get(i).getCnstgKickfDt());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getLvlupDt() == null ? "-" : list.get(i).getLvlupDt());
            cell.setCellStyle(style_body);

            //신청일
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getAppctnDt());
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("경영컨설팅_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 경영컨설팅");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cbb.service.impl.CBBManageConsultServiceImpl");
        pCoSystemLogDTO.setFncNm("selectManageConsultList");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(cBBManageConsultSearchDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 만족도 종합 결과 엑셀다운로드 상세
     */
    public CBBConsultSuveyRsltListDTO selectConsultSuveyRsltDtlExcel(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO) throws Exception{
        /*COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(cBBConsultSuveyRsltListDTO.getPageIndex());
        page.setRecordCountPerPage(cBBManageConsultMapper.getConsultSuveyRsltDtlCnt(cBBConsultSuveyRsltListDTO));
        page.setPageSize(cBBConsultSuveyRsltListDTO.getPageRowSize());
        cBBConsultSuveyRsltListDTO.setFirstIndex(page.getFirstRecordIndex());
        cBBConsultSuveyRsltListDTO.setRecordCountPerPage(page.getRecordCountPerPage());*/
        /*cBBConsultSuveyRsltListDTO.setTotalCount(cBBManageConsultMapper.getConsultSuveyRsltDtlCnt(cBBConsultSuveyRsltListDTO));*/
        cBBConsultSuveyRsltListDTO.setList(cBBManageConsultMapper.selectConsultSuveyRsltDtlExcel(cBBConsultSuveyRsltListDTO));
        return cBBConsultSuveyRsltListDTO;
    }

    /**
     * 만족도 종합 결과 상세 엑셀 생성
     */
    public void srvRsltExcelDownload(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO, HttpServletResponse response) throws Exception{

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
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(3);
        cell.setCellValue("담당위원");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("참여자");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("신청업종/분야");
        cell.setCellStyle(style_header);

        cell = row.createCell(6);
        cell.setCellValue("지도구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("렙업일");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("총점(100)");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("지도실적(50)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("의사소동(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("기획력(10)");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("실행력(15)");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("마인드(5)");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("전문지식(15)");
        cell.setCellStyle(style_header);


        // Body
        List<CBBConsultSuveyRsltListDTO> list = cBBConsultSuveyRsltListDTO.getList();
        for (int i=0; i<list.size(); i++) {
            row = sheet.createRow(rowNum++);

            //번호
            cell = row.createCell(0);
            cell.setCellValue(cBBConsultSuveyRsltListDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getBsnYear());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //담당위원
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getCmssrNm() == null ? "-" : list.get(i).getCmssrNm());
            cell.setCellStyle(style_body);

            //참여자
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getPtcptName() == null ? "-" : list.get(i).getPtcptName());
            cell.setCellStyle(style_body);

            //신청업종/분야
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getAppctnFldNm());
            cell.setCellStyle(style_body);

            //지도구분
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getGuideTypeNm());
            cell.setCellStyle(style_body);

            //상태
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getSrvStts());
            cell.setCellStyle(style_body);

            //렙업일
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getLvlupDt() == null ? "-" : list.get(i).getLvlupDt());
            cell.setCellStyle(style_body);

            //총점(100)
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getTtlScore());
            cell.setCellStyle(style_body);

            //지도실적(50)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getGuideRsltScore());
            cell.setCellStyle(style_body);

            //의사소통(5)
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getCmmnctnScore());
            cell.setCellStyle(style_body);

            //기획력(10)
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getPlnngabltScore());
            cell.setCellStyle(style_body);

            //실행력(15)
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getExctvabltScore());
            cell.setCellStyle(style_body);

            //마인드(5)
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getMndScore());
            cell.setCellStyle(style_body);

            //전문지식(15)
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getExprtsScore());
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
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("경영컨설팅_만족도종합결과_", "UTF-8") + timeStamp +".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();

        //다운로드 사유 입력
        COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
        COSystemLogDTO pCoSystemLogDTO = new COSystemLogDTO();
        pCoSystemLogDTO.setTrgtMenuNm("컨설팅사업관리 > 경영컨설팅");
        pCoSystemLogDTO.setSrvcNm("mngwserc.cb.cbb.service.impl.CBBManageConsultServiceImpl");
        pCoSystemLogDTO.setFncNm("selectConsultSuveyRsltDtlExcel");
        pCoSystemLogDTO.setPrcsCd("DL");
        pCoSystemLogDTO.setRsn(cBBConsultSuveyRsltListDTO.getRsn());
        pCoSystemLogDTO.setRegId(cOUserDetailsDTO.getId());
        pCoSystemLogDTO.setRegIp(cOUserDetailsDTO.getLoginIp());
        cOSystemLogService.logInsertSysLog(pCoSystemLogDTO);
    }

    /**
     * 컨설팅 이관 내역 조회
     */
    public CBBManageConsultInsertDTO selectTrsfGuidanceList(CBBManageConsultInsertDTO pCBBManageConsultInsertDTO, CBBManageConsultSearchDTO pCBBManageConsultSearchDTO) throws Exception
    {

        List<CBBManageConsultInsertDTO> trsfGuidanceList = new ArrayList();
        CBBManageConsultInsertDTO trsfDto = new CBBManageConsultInsertDTO();

        COPaginationUtil page = new COPaginationUtil();
        page.setCurrentPageNo(pCBBManageConsultInsertDTO.getPageIndex());
        page.setRecordCountPerPage(pCBBManageConsultInsertDTO.getListRowSize());

        page.setPageSize(pCBBManageConsultInsertDTO.getPageRowSize());

        pCBBManageConsultInsertDTO.setFirstIndex( page.getFirstRecordIndex() );
        pCBBManageConsultInsertDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        page.setRecordCountPerPage(cBBManageConsultMapper.getManageConsultListCnt(pCBBManageConsultSearchDTO));
        trsfGuidanceList = cBBManageConsultMapper.selectTrsfGuidanceList(pCBBManageConsultInsertDTO);
        int trsfCnt = cBBManageConsultMapper.selectTrsfGuidanceCnt(pCBBManageConsultInsertDTO);

        trsfDto.setTrsfGuidanceList(trsfGuidanceList);
        trsfDto.setTotalCount(trsfCnt);

        return trsfDto;
    }

}
