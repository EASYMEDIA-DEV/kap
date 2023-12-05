package com.kap.service.impl.cb.cba;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.cb.cba.CBATechGuidanceDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceUpdateDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.CBATechGuidanceService;
import com.kap.service.COFileService;
import com.kap.service.COSystemLogService;
import com.kap.service.MPEPartsCompanyService;
import com.kap.service.dao.cb.cba.CBATechGuidanceMapper;
import com.kap.service.dao.mp.MPAUserMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import com.kap.service.mp.mpa.MPAUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 컨설팅 기술 지도를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: CBATechGuidanceServiceImpl.java
 * @Description		: 컨설팅 기술 지도를 위한 ServiceImpl
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CBATechGuidanceServiceImpl implements CBATechGuidanceService {

    private final COFileService cOFileService;

    private final MPEPartsCompanyService mPEPartsCompanyService;

    private final MPAUserService mpaUserService;

    /* 컨설팅 기술 지도 시퀀스 */
    private final EgovIdGnrService cosultSeqIdgen;
    private final EgovIdGnrService dpndnSeqIdgen;
    private final EgovIdGnrService dlvrySeqIdgen;
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;
    private final EgovIdGnrService consTrnsfSeqIdgen;

    private final CBATechGuidanceMapper cBATechGuidanceMapper;
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;
    private final MPAUserMapper mpaUserMapper;

    // 로그인 상태값 시스템 등록
    private final COSystemLogService cOSystemLogService;

    // 확장자
    @Value("${app.file.imageExtns}")
    private String imageExtns;

    @Value("${app.file.videoExtns}")
    private String videoExtns;

    /**
     * 컨설팅 기술 지도 목록 조회
     */
    public CBATechGuidanceDTO selectTechGuidanceList(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCBATechGuidanceDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceDTO.getPageRowSize());

        pCBATechGuidanceDTO.setFirstIndex(page.getFirstRecordIndex());
        pCBATechGuidanceDTO.setRecordCountPerPage(page.getRecordCountPerPage());
        pCBATechGuidanceDTO.setTotalCount(cBATechGuidanceMapper.selectTechGuidanceTotCnt(pCBATechGuidanceDTO));
        pCBATechGuidanceDTO.setList(cBATechGuidanceMapper.selectTechGuidanceList(pCBATechGuidanceDTO));

        return pCBATechGuidanceDTO;
    }

    /**
     * 컨설팅 기술 지도 상세 조회
     */
    public CBATechGuidanceInsertDTO selectTechGuidanceDtl(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {
        MPEPartsCompanyDTO companyInfo = new MPEPartsCompanyDTO();

        if (!"".equals(pCBATechGuidanceInsertDTO.getDetailsKey()))
        {
            pCBATechGuidanceInsertDTO = cBATechGuidanceMapper.selectTechGuidanceDtl(pCBATechGuidanceInsertDTO);
            MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
            mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replace("-", ""));
            companyInfo = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);

            for(int i =0; i<1; i++){
                pCBATechGuidanceInsertDTO.setCmpnNm(companyInfo.getList().get(i).getCmpnNm());
                pCBATechGuidanceInsertDTO.setRprsntNm(companyInfo.getList().get(i).getRprsntNm());
                pCBATechGuidanceInsertDTO.setStbsmDt(companyInfo.getList().get(i).getStbsmDt());
                pCBATechGuidanceInsertDTO.setTelNo(companyInfo.getList().get(i).getTelNo());
                pCBATechGuidanceInsertDTO.setCtgryCd(companyInfo.getList().get(i).getCtgryCd());
                pCBATechGuidanceInsertDTO.setBscAddr(companyInfo.getList().get(i).getBscAddr());
                pCBATechGuidanceInsertDTO.setDtlAddr(companyInfo.getList().get(i).getDtlAddr());
                pCBATechGuidanceInsertDTO.setZipcode(companyInfo.getList().get(i).getZipcode());
                pCBATechGuidanceInsertDTO.setSlsPmt(companyInfo.getList().get(i).getSlsPmt());
                pCBATechGuidanceInsertDTO.setSlsYear(companyInfo.getList().get(i).getSlsYear());
                pCBATechGuidanceInsertDTO.setMpleCnt(companyInfo.getList().get(i).getMpleCnt());
                pCBATechGuidanceInsertDTO.setMjrPrdct1(companyInfo.getList().get(i).getMjrPrdct1());
                pCBATechGuidanceInsertDTO.setMjrPrdct2(companyInfo.getList().get(i).getMjrPrdct2());
                pCBATechGuidanceInsertDTO.setMjrPrdct3(companyInfo.getList().get(i).getMjrPrdct3());
                pCBATechGuidanceInsertDTO.setCmpnTelNo(companyInfo.getList().get(i).getTelNo());
            }

            System.err.println("companyInfo:::"+companyInfo.getList().size());
            for(int j=0; j<companyInfo.getList().size(); j++){
                List sqlInfoList = new ArrayList();
                sqlInfoList.add(0,companyInfo.getList().get(j).getNm());
                sqlInfoList.add(1,String.valueOf(companyInfo.getList().get(j).getScore()));
                sqlInfoList.add(2,String.valueOf(companyInfo.getList().get(j).getYear()));
                sqlInfoList.add(3,companyInfo.getList().get(j).getCrtfnCmpnNm());
                sqlInfoList.add(4,companyInfo.getList().get(j).getCbsnSeq());
                if(j==0){
                    pCBATechGuidanceInsertDTO.setSqInfoList(sqlInfoList);
                }else if(j==1){
                    pCBATechGuidanceInsertDTO.setSqInfoList1(sqlInfoList);
                }else{
                    pCBATechGuidanceInsertDTO.setSqInfoList2(sqlInfoList);
                }
            }
            int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

            List<CBATechGuidanceInsertDTO> dlvryInfo = cBATechGuidanceMapper.selectCnstgDlvryInfo(cnstgSeq);
            List dlvryInfoList = new ArrayList();
            for(int i=0; i<dlvryInfo.size(); i++){
                CBATechGuidanceInsertDTO dlvryDto = new CBATechGuidanceInsertDTO();
                dlvryDto.setDlvryCmpnNm(dlvryInfo.get(i).getDlvryCmpnNm());
                dlvryDto.setDlvryRate(dlvryInfo.get(i).getDlvryRate());
                dlvryDto.setCmpnDlvrySeq(dlvryInfo.get(i).getCmpnDlvrySeq());
                dlvryInfoList.add(dlvryDto);
            }
            pCBATechGuidanceInsertDTO.setDlvryCmpnList(dlvryInfoList);

            List<CBATechGuidanceInsertDTO> dpndnInfo = cBATechGuidanceMapper.selectCnstgDpndnInfo(cnstgSeq);
            List dpndnInfoList = new ArrayList();
            for(int i=0; i<dpndnInfo.size(); i++){
                CBATechGuidanceInsertDTO dpndnDto = new CBATechGuidanceInsertDTO();
                dpndnDto.setDpndnCmpnNm(dpndnInfo.get(i).getDpndnCmpnNm());
                dpndnDto.setDpndnRate(dpndnInfo.get(i).getDpndnRate());
                dpndnDto.setDpndnSeq(dpndnInfo.get(i).getDpndnSeq());
                dpndnInfoList.add(dpndnDto);
            }
            pCBATechGuidanceInsertDTO.setDpndCmpnList(dpndnInfoList);

            List<CBATechGuidanceInsertDTO> appctnTypeInfo = cBATechGuidanceMapper.selectCnstgAppctnType(cnstgSeq);
            List appctnTypeList = new ArrayList();
            for(int i=0; i<appctnTypeInfo.size(); i++){
                CBATechGuidanceInsertDTO appctnDto = new CBATechGuidanceInsertDTO();
                appctnDto.setAppctnTypeCd(appctnTypeInfo.get(i).getAppctnTypeCd());
                appctnTypeList.add(appctnDto);
            }
            pCBATechGuidanceInsertDTO.setAppctnTypeList(appctnTypeList);

        }
        CBATechGuidanceUpdateDTO CBATechGuidanceUpdateDTO = new CBATechGuidanceUpdateDTO();
        pCBATechGuidanceInsertDTO.setRsumeList(cBATechGuidanceMapper.selectTechGuidanceRsume(pCBATechGuidanceInsertDTO));

        System.err.println("pCBATechGuidanceInsertDTO"+pCBATechGuidanceInsertDTO.getRsumeList());

        return pCBATechGuidanceInsertDTO;
    }

    /**
     * 컨설팅 기술 지도 등록
     */
    public int insertTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));
        pCBATechGuidanceInsertDTO.setCnstgSeq(cosultSeqIdgen.getNextIntegerId());

        // 신청 회원 정보
        updateTechMemberInfo(pCBATechGuidanceInsertDTO);

        // 부품사 정보
        updateTechCompanyInfo(pCBATechGuidanceInsertDTO);

        // 컨설팅 서브 정보 수정
        updateSubTechGuidanceInfo(pCBATechGuidanceInsertDTO);

        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.insertTechGuidance(pCBATechGuidanceInsertDTO));
        return pCBATechGuidanceInsertDTO.getRespCnt();
    }

    void updateSubTechGuidanceInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        // 고객사 비율 상세 정보 등록 있으면 수정
        String[] dlvryCmpnNm =  pCBATechGuidanceInsertDTO.getDlvryCmpnNm().split(",");
        String[] dlvryRate = pCBATechGuidanceInsertDTO.getDlvryRate().split(",");
        HashMap cnstgDlyvMap = new HashMap();
        for(int i=0; i < dlvryCmpnNm.length; i++) {
            cnstgDlyvMap.put("cnstgSeq", pCBATechGuidanceInsertDTO.getCnstgSeq());
            cnstgDlyvMap.put("cmpnDlvrySeq",dlvrySeqIdgen.getNextIntegerId());
            cnstgDlyvMap.put("dlvryCmpnNm", dlvryCmpnNm[i]);
            cnstgDlyvMap.put("dlvryRate", dlvryRate[i]);
            cnstgDlyvMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            cnstgDlyvMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

            List dlvryInfoList = cBATechGuidanceMapper.selectCnstgDlvryInfo(pCBATechGuidanceInsertDTO.getCnstgSeq());
            if(dlvryInfoList.size() > 0){
                cBATechGuidanceMapper.updateCnstgDlvryInfo(cnstgDlyvMap);
            }else{
                cBATechGuidanceMapper.insertCnstgDlvryInfo(cnstgDlyvMap);
            }

        }
        // 완성차 의존율 상세 정보 등록 있으면 수정
        String[] dpbdnCmpnNm =  pCBATechGuidanceInsertDTO.getDpndnCmpnNm().split(",");
        String[] dpbdnRate = pCBATechGuidanceInsertDTO.getDpndnRate().split(",");
        HashMap dpndnMap = new HashMap();
        for(int i=0; i < dpbdnCmpnNm.length; i++){
            dpndnMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
            dpndnMap.put("dpndnSeq",dpndnSeqIdgen.getNextIntegerId());
            dpndnMap.put("cmpnNm", dpbdnCmpnNm[i]);
            dpndnMap.put("dpndnRate", dpbdnRate[i]);
            dpndnMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            dpndnMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

            List dpndnInfoList = cBATechGuidanceMapper.selectCnstgDpndnInfo(pCBATechGuidanceInsertDTO.getCnstgSeq());
            if(dpndnInfoList.size() > 0){
                cBATechGuidanceMapper.updateCnstgDpndnInfo(dpndnMap);
            }else{
                cBATechGuidanceMapper.insertCnstgDpndnInfo(dpndnMap);
            }
        }
        // 부품사 업종 상세 등록 있으면 수정
        HashMap cbsnCdMap = new HashMap();
        cbsnCdMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
        cbsnCdMap.put("cbsnCd",  pCBATechGuidanceInsertDTO.getCbsnCd());
        cbsnCdMap.put("etcNm", pCBATechGuidanceInsertDTO.getEtcNm());
        cbsnCdMap.put("regIp", pCBATechGuidanceInsertDTO.getRegId());
        cbsnCdMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
        cbsnCdMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());

        int cnt = cBATechGuidanceMapper.selectCnstgDpndnInfoCnt(cbsnCdMap);
        if(cnt > 0){
            cBATechGuidanceMapper.updateCbsnDtl(cbsnCdMap);
        }else{
            cBATechGuidanceMapper.insertCbsnDtl(cbsnCdMap);
        }

        // 신청 분야 상세
        String[] appctnTypeCd =  pCBATechGuidanceInsertDTO.getAppctnTypeCd().split(",");
        HashMap appctnTypeMap = new HashMap();
        appctnTypeMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
        cBATechGuidanceMapper.deleteCnstgAppctnType(appctnTypeMap);

        for(int i=0; i < appctnTypeCd.length; i++){
            appctnTypeMap.put("cnstgSeq",pCBATechGuidanceInsertDTO.getCnstgSeq());
            appctnTypeMap.put("regIp", pCBATechGuidanceInsertDTO.getRegId());
            appctnTypeMap.put("regId", pCBATechGuidanceInsertDTO.getRegId());
            appctnTypeMap.put("regIp", pCBATechGuidanceInsertDTO.getRegIp());
            appctnTypeMap.put("appctnTypeCd", appctnTypeCd[i]);

            int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

            cBATechGuidanceMapper.insertCnstgAppctnType(appctnTypeMap);
        }
    }

    /**
     * 부품사 회원 정보 수정
     */
    void updateTechMemberInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        MPAUserDto mPAUserDto = new MPAUserDto();
        mPAUserDto.setDetailsKey(pCBATechGuidanceInsertDTO.getMemSeq());

        mPAUserDto = mpaUserService.selectUserDtlTab(mPAUserDto);

        if(mPAUserDto != null){
            mPAUserDto.setRegId(pCBATechGuidanceInsertDTO.getRegId());
            mPAUserDto.setRegIp(pCBATechGuidanceInsertDTO.getRegIp());
            mPAUserDto.setMemSeq(Integer.valueOf(pCBATechGuidanceInsertDTO.getMemSeq()));
            mPAUserDto.setDeptCd(pCBATechGuidanceInsertDTO.getDeptCd());
            mPAUserDto.setDeptDtlNm(pCBATechGuidanceInsertDTO.getDeptDtlNm());
            mPAUserDto.setPstnCd(pCBATechGuidanceInsertDTO.getPstnCd());
            mPAUserDto.setTelNo(pCBATechGuidanceInsertDTO.getTelNo());
            mPAUserDto.setHpNo(pCBATechGuidanceInsertDTO.getHpNo());
            mpaUserService.updateUserDtl(mPAUserDto);
        }
    }
    /**
     * 부품사 정보 수정
     */
    void updateTechCompanyInfo(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception {

        MPEPartsCompanyDTO mPEPartsCompanyDTO = new MPEPartsCompanyDTO();
        mPEPartsCompanyDTO.setBsnmNo(pCBATechGuidanceInsertDTO.getBsnmNo().replace("-", ""));
        mPEPartsCompanyDTO = mPEPartsCompanyService.selectPartsCompanyDtl(mPEPartsCompanyDTO);

        // 회사 업종 상세 등록
        String[] cbsnSeq =  pCBATechGuidanceInsertDTO.getCbsnSeq().split(",");
        String[] nm =  pCBATechGuidanceInsertDTO.getNm().split(",");
        String[] score = pCBATechGuidanceInsertDTO.getScore().split(",");
        String[] year = pCBATechGuidanceInsertDTO.getYear().split(",");
        String[] crtfnCmpnNm = pCBATechGuidanceInsertDTO.getCrtfnCmpnNm().split(",");

        MPEPartsCompanyDTO mpePartsCompanyDTO = new MPEPartsCompanyDTO();

        String ctgryCd = pCBATechGuidanceInsertDTO.getCtgryCd();
        // 1차사 - 5스타
        if(ctgryCd.equals("COMPANY01001")){
            mPEPartsCompanyDTO.setQlty5StarCd(pCBATechGuidanceInsertDTO.getQlty5StarCd());
            mPEPartsCompanyDTO.setQlty5StarYear(pCBATechGuidanceInsertDTO.getQlty5StarYear());
            mPEPartsCompanyDTO.setPay5StarCd(pCBATechGuidanceInsertDTO.getPay5StarCd());
            mPEPartsCompanyDTO.setPay5StarYear(pCBATechGuidanceInsertDTO.getPay5StarYear());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBATechGuidanceInsertDTO.getTchlg5StarCd());
            mPEPartsCompanyDTO.setTchlg5StarCd(pCBATechGuidanceInsertDTO.getTchlg5StarCd());
        } else if(ctgryCd.equals("COMPANY01002")){ // 2차사 - SQ 정보

            HashMap cbsnCdMap = new HashMap();

            for(int i=0; i < cbsnCdMap.size(); i++) {
                mpePartsCompanyDTO.setNm(nm[i]);
                mpePartsCompanyDTO.setCbsnSeq(Integer.valueOf(cbsnSeq[i]));
                mpePartsCompanyDTO.setScore(Integer.valueOf(score[i]));
                mpePartsCompanyDTO.setYear(Integer.valueOf(year[i]));
                mpePartsCompanyDTO.setCrtfnCmpnNm(crtfnCmpnNm[i]);
                mpePartsCompanyDTO.setBsnmNo(mPEPartsCompanyDTO.getBsnmNo());

                int cnt = cBATechGuidanceMapper.selectCmpnCbsnInfoCnt(Integer.valueOf(cbsnSeq[i]));

                if(cnt > 1){
                    mpePartsCompanyDTO.setModId(pCBATechGuidanceInsertDTO.getRegId());
                    mpePartsCompanyDTO.setModId(pCBATechGuidanceInsertDTO.getRegIp());
                    mpePartsCompanyMapper.updatePartsComSQInfo(mpePartsCompanyDTO);
                }else if(cnt == 0){
                    mpePartsCompanyDtlIdgen.getNextIntegerId();
                    mpePartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());
                    mpePartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegIp());
                    mpePartsCompanyMapper.insertPartsComSQInfo(mpePartsCompanyDTO);
                }
            }
        }
        mPEPartsCompanyDTO.setModId(pCBATechGuidanceInsertDTO.getRegId());
        mPEPartsCompanyDTO.setModIp(pCBATechGuidanceInsertDTO.getRegIp());
        mPEPartsCompanyDTO.setRprsntNm(pCBATechGuidanceInsertDTO.getRprsntNm());
        mPEPartsCompanyDTO.setCmpnNm(pCBATechGuidanceInsertDTO.getCmpnNm());
        mPEPartsCompanyDTO.setCmpnNfrmlNm(pCBATechGuidanceInsertDTO.getCmpnNfrmlNm());
        mPEPartsCompanyDTO.setCmpnCd(pCBATechGuidanceInsertDTO.getCmpnCd());
        mPEPartsCompanyDTO.setTelNo(pCBATechGuidanceInsertDTO.getCmpnTelNo()); // 부품사 일반 전화번호
        mPEPartsCompanyDTO.setCtgryCd(pCBATechGuidanceInsertDTO.getCtgryCd());
        mPEPartsCompanyDTO.setSizeCd(pCBATechGuidanceInsertDTO.getSizeCd());
        mPEPartsCompanyDTO.setStbsmDt(pCBATechGuidanceInsertDTO.getStbsmDt());
        mPEPartsCompanyDTO.setZipcode(pCBATechGuidanceInsertDTO.getZipcode());
        mPEPartsCompanyDTO.setBscAddr(pCBATechGuidanceInsertDTO.getBscAddr());
        mPEPartsCompanyDTO.setDtlAddr(pCBATechGuidanceInsertDTO.getDtlAddr());
        mPEPartsCompanyDTO.setSlsPmt(pCBATechGuidanceInsertDTO.getSlsPmt());
        mPEPartsCompanyDTO.setSlsYear(pCBATechGuidanceInsertDTO.getSlsYear());
        mPEPartsCompanyDTO.setMpleCnt(pCBATechGuidanceInsertDTO.getMpleCnt());
        mPEPartsCompanyDTO.setMjrPrdct1(pCBATechGuidanceInsertDTO.getMjrPrdct1());
        mPEPartsCompanyDTO.setMjrPrdct2(pCBATechGuidanceInsertDTO.getMjrPrdct2());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setMjrPrdct3(pCBATechGuidanceInsertDTO.getMjrPrdct3());
        mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());
        mPEPartsCompanyDTO.setRegId(pCBATechGuidanceInsertDTO.getRegIp());

        mpePartsCompanyMapper.updatePartsCompany(mPEPartsCompanyDTO);
    }
    /**
     * 컨설팅 기술 지도 삭제
     */
    public int deleteTechGuidance(CBATechGuidanceDTO pCBATechGuidanceDTO) throws Exception {
        return cBATechGuidanceMapper.deleteTechGuidance(pCBATechGuidanceDTO);
    }

    /**
     * 컨설팅 기술 지도 수정
     */
    public int updateTechGuidance(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO, CBATechGuidanceUpdateDTO pCBATechGuidanceupUpdateDTO ) throws Exception {
        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(pCBATechGuidanceInsertDTO.getFileList());
        pCBATechGuidanceInsertDTO.setItrdcFileSeq(fileSeqMap.get("itrdcFileSeq"));
        pCBATechGuidanceInsertDTO.setImpvmFileSeq(fileSeqMap.get("impvmFileSeq"));

        pCBATechGuidanceupUpdateDTO.setInitVstFileSeq(fileSeqMap.get("initVstFileSeq")); // 초도방문자료
        pCBATechGuidanceupUpdateDTO.setKickfFileSeq(fileSeqMap.get("kickfFileSeq")); // 킥오프자료
        pCBATechGuidanceupUpdateDTO.setLvlupFileSeq(fileSeqMap.get("lvlupFileSeq")); // 랩업자료
        pCBATechGuidanceupUpdateDTO.setEtcFileSeq(fileSeqMap.get("etcFileSeq")); // 기타사업자료

        pCBATechGuidanceupUpdateDTO.setRegIp(pCBATechGuidanceInsertDTO.getRegIp());
        pCBATechGuidanceupUpdateDTO.setRegId(pCBATechGuidanceInsertDTO.getRegId());

        pCBATechGuidanceInsertDTO.setCnstgSeq(Integer.valueOf(pCBATechGuidanceInsertDTO.getDetailsKey()));

        // 부품사 회원 정보 수정
        updateTechMemberInfo(pCBATechGuidanceInsertDTO);
        // 부품사 정보 수정
        updateTechCompanyInfo(pCBATechGuidanceInsertDTO);
        // 신청 서브 정보 수정
        updateSubTechGuidanceInfo(pCBATechGuidanceInsertDTO);

        cBATechGuidanceMapper.updateCbsnDtl(pCBATechGuidanceInsertDTO);

        int cnstgSeq = pCBATechGuidanceInsertDTO.getCnstgSeq();

        int totCnt = cBATechGuidanceMapper.selectRsumeTotCnt(cnstgSeq);
        pCBATechGuidanceupUpdateDTO.setCnstgSeq(cnstgSeq);
        if(totCnt>0){
            cBATechGuidanceMapper.updateTechGuidanceRsume(pCBATechGuidanceupUpdateDTO);
        }else if(totCnt == 0){
            cBATechGuidanceMapper.insertTechGuidanceRsume(pCBATechGuidanceupUpdateDTO);
        }

        String bfreMemSeq = pCBATechGuidanceInsertDTO.getBfreMemSeq();
        String aftrMemSeq = pCBATechGuidanceInsertDTO.getMemSeq();
        System.err.println("pCBATechGuidanceInsertDTO"+pCBATechGuidanceInsertDTO);
        System.err.println("bfreMemSeq"+bfreMemSeq);
        System.err.println("aftrMemSeq"+aftrMemSeq);
        if(bfreMemSeq != aftrMemSeq){
            pCBATechGuidanceInsertDTO.setTrnsfSeq(consTrnsfSeqIdgen.getNextIntegerId());
            pCBATechGuidanceInsertDTO.setAftrMemSeq(aftrMemSeq);
            cBATechGuidanceMapper.insertTrsfGuidanceList(pCBATechGuidanceInsertDTO);
        }
        pCBATechGuidanceInsertDTO.setRespCnt(cBATechGuidanceMapper.updateTechGuidance(pCBATechGuidanceInsertDTO));

        return pCBATechGuidanceInsertDTO.getRespCnt();
    }

    public List<MPEPartsCompanyDTO> selectPartsCompanyDtl(MPEPartsCompanyDTO mpePartsCompanyDTO) throws Exception {
        return mpePartsCompanyMapper.selectPartsCompanyDtl(mpePartsCompanyDTO);
    }

    public CBATechGuidanceInsertDTO selectTrsfGuidanceList(CBATechGuidanceInsertDTO pCBATechGuidanceInsertDTO) throws Exception
    {

        List<CBATechGuidanceInsertDTO> trsfGuidanceList = new ArrayList();
        CBATechGuidanceInsertDTO trsfDto = new CBATechGuidanceInsertDTO();

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pCBATechGuidanceInsertDTO.getPageIndex());
        page.setRecordCountPerPage(pCBATechGuidanceInsertDTO.getListRowSize());

        page.setPageSize(pCBATechGuidanceInsertDTO.getPageRowSize());

        trsfDto.setFirstIndex( page.getFirstRecordIndex() );
        trsfDto.setRecordCountPerPage( page.getRecordCountPerPage() );
        trsfGuidanceList = cBATechGuidanceMapper.selectTrsfGuidanceList(pCBATechGuidanceInsertDTO);
        int trsfCnt = cBATechGuidanceMapper.selectTrsfGuidanceCnt(pCBATechGuidanceInsertDTO);

        trsfDto.setTrsfGuidanceList(trsfGuidanceList);
        trsfDto.setTotalCount(trsfCnt);

        return trsfDto;
    }

}

