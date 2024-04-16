package com.kap.service.impl.eb;

import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.eb.ebh.EBHEduApplicantMstDTO;
import com.kap.core.dto.mp.mpa.MPAUserDto;
import com.kap.core.dto.mp.mpe.MPEPartsCompanyDTO;
import com.kap.service.COCommService;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBHEduApplicantService;
import com.kap.service.dao.eb.EBHEduApplicantMapper;
import com.kap.service.dao.mp.MPEPartsCompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 교육 신청자 관리 ServiceImpl
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *   2023.12.01  장두석         최초 생성
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EBHEduApplicantServiceImpl implements EBHEduApplicantService {

    /** Mapper **/
    private final EBHEduApplicantMapper pEBHEduApplicantMapper;
    private final MPEPartsCompanyMapper mpePartsCompanyMapper;

    /** Service **/
    private final COCommService cOCommService;
    private final COFileService cOFileService;

    /** Sequence **/
    private final EgovIdGnrService mpePartsCompanySqInfoDtlIdgen;

    /**
     * 리스트 조회
     */
    public EBHEduApplicantMstDTO selectList(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(pEBHEduApplicantMstDTO.getPageIndex());
        page.setRecordCountPerPage(pEBHEduApplicantMstDTO.getListRowSize());

        page.setPageSize(pEBHEduApplicantMstDTO.getPageRowSize());

        pEBHEduApplicantMstDTO.setFirstIndex( page.getFirstRecordIndex() );
        pEBHEduApplicantMstDTO.setRecordCountPerPage( page.getRecordCountPerPage() );

        pEBHEduApplicantMstDTO.setTotalCount( pEBHEduApplicantMapper.getListCnt(pEBHEduApplicantMstDTO));
        pEBHEduApplicantMstDTO.setList( pEBHEduApplicantMapper.selectList(pEBHEduApplicantMstDTO) );

        return pEBHEduApplicantMstDTO;
    }

    /**
     * 상세 조회
     */
    public EBHEduApplicantMstDTO selectView(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{
        EBHEduApplicantMstDTO rtnData = pEBHEduApplicantMapper.selectView(pEBHEduApplicantMstDTO);

        rtnData.setInsNameList(pEBHEduApplicantMapper.selectViewIns(rtnData));

        rtnData.setSqList(pEBHEduApplicantMapper.selectViewSq(rtnData));

        return rtnData;
    }

    /**
     * 수정
     */
    @Transactional
    public int update(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO, MPEPartsCompanyDTO pMPEPartsCompanyDTO, MPAUserDto pMPAUserDto) throws Exception{
        if(pEBHEduApplicantMstDTO.getDetailsKey() != null && !pEBHEduApplicantMstDTO.getDetailsKey().isEmpty()) {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            pEBHEduApplicantMstDTO.setModId(cOUserDetailsDTO.getId());
            pEBHEduApplicantMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());
            pMPEPartsCompanyDTO.setModId(cOUserDetailsDTO.getId());
            pMPEPartsCompanyDTO.setModIp(cOUserDetailsDTO.getLoginIp());
            pMPAUserDto.setModId(cOUserDetailsDTO.getId());
            pMPAUserDto.setModIp(cOUserDetailsDTO.getLoginIp());

            pMPEPartsCompanyDTO.setBsnmNo(pEBHEduApplicantMstDTO.getPtcptBsnmNo());
            pMPEPartsCompanyDTO.setCtgryCd(pEBHEduApplicantMstDTO.getPartsCtgryCd());
            pMPEPartsCompanyDTO.setTelNo(pEBHEduApplicantMstDTO.getCmpnTelNo());

            if("COMPANY01002".equals(pEBHEduApplicantMstDTO.getPartsCtgryCd())) {
                // 2차인 경우
                String cbsnSeq = "";
                String nm = "";
                String score = "";
                String year = "";
                String crtfnCmpnNm = "";
                List<List<String>> sqList = new ArrayList();
                List<String> getSqInfoList1 = pMPEPartsCompanyDTO.getSqInfoList1();
                List<String> getSqInfoList2 = pMPEPartsCompanyDTO.getSqInfoList2();
                List<String> getSqInfoList3 = pMPEPartsCompanyDTO.getSqInfoList3();
                sqList.add(getSqInfoList1);
                sqList.add(getSqInfoList2);
                sqList.add(getSqInfoList3);
                for(List<String> sqData : sqList) {
                    cbsnSeq = sqData.get(0);
                    nm = sqData.get(1);
                    score = sqData.get(2);
                    year = sqData.get(3);
                    crtfnCmpnNm = sqData.get(4);

                    if(year.equals("")|| year.isEmpty()) {
                        pMPEPartsCompanyDTO.setYear(null);
                    } else {
                        pMPEPartsCompanyDTO.setYear(Integer.valueOf(year));
                    }

                    if(score.equals("")|| score.isEmpty()) {
                        pMPEPartsCompanyDTO.setScore(null);
                    } else {
                        pMPEPartsCompanyDTO.setScore(Double.valueOf(score));
                    }

                    pMPEPartsCompanyDTO.setNm(nm);
                    pMPEPartsCompanyDTO.setCrtfnCmpnNm(crtfnCmpnNm);

                    if (!cbsnSeq.isEmpty()) {
                        pMPEPartsCompanyDTO.setCbsnSeq(Integer.valueOf(cbsnSeq));
                        mpePartsCompanyMapper.updatePartsComSQInfo(pMPEPartsCompanyDTO);
                    } else {
                        pMPEPartsCompanyDTO.setCbsnSeq(mpePartsCompanySqInfoDtlIdgen.getNextIntegerId());
                        mpePartsCompanyMapper.insertPartsComSQInfo(pMPEPartsCompanyDTO);
                    }
                }

                pMPEPartsCompanyDTO.setQlty5StarCd(null);
                pMPEPartsCompanyDTO.setQlty5StarYear(null);
                pMPEPartsCompanyDTO.setPay5StarCd(null);
                pMPEPartsCompanyDTO.setPay5StarYear(null);
                pMPEPartsCompanyDTO.setTchlg5StarCd(null);
                pMPEPartsCompanyDTO.setTchlg5StarYear(null);
            }
            else if ("COMPANY01001".equals(pEBHEduApplicantMstDTO.getPartsCtgryCd())) {
                // 1차인 경우, SQ정보 빈 값 처리
                mpePartsCompanyMapper.deletePartsComSQInfo(pMPEPartsCompanyDTO);
            }
            else {
                throw new Exception("부품사 구분을 선택해 주세요.");
            }

            int userUpdate = pEBHEduApplicantMapper.updateUser(pMPAUserDto);
            int cmpnUpdate = pEBHEduApplicantMapper.updateCmpn(pMPEPartsCompanyDTO);
            int applicantUpdate = pEBHEduApplicantMapper.updateApplicant(pEBHEduApplicantMstDTO);

            if(userUpdate > 0 && cmpnUpdate > 0 && applicantUpdate > 0) {
                return userUpdate + cmpnUpdate + applicantUpdate;
            }
        }

        return 0;
    }

    /**
     * 선발 상태 수정
     */
    public int updateStts(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception{
        if(pEBHEduApplicantMstDTO.getDelValueList() != null && !pEBHEduApplicantMstDTO.getDelValueList().isEmpty()) {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            pEBHEduApplicantMstDTO.setModId(cOUserDetailsDTO.getId());
            pEBHEduApplicantMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            if("Y".equals(pEBHEduApplicantMstDTO.getStts())) {
                pEBHEduApplicantMstDTO.setSttsCd("EDU_STTS_CD01");
            }
            else if("N".equals(pEBHEduApplicantMstDTO.getStts())) {
                pEBHEduApplicantMstDTO.setSttsCd("EDU_STTS_CD05");
            }

            return pEBHEduApplicantMapper.updateStts(pEBHEduApplicantMstDTO);
        }
        else {
            return 0;
        }
    }

    /**
     * 신청자 정원 체크
     */
    @Transactional
    public EBHEduApplicantMstDTO selectFxnumChk(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO) throws Exception
    {
        EBHEduApplicantMstDTO tempDto = new EBHEduApplicantMstDTO();
        EBHEduApplicantMstDTO rtnDto = new EBHEduApplicantMstDTO();
        rtnDto.setFxnumStta("S");
        List<String> checkList = new ArrayList<>();
        if(pEBHEduApplicantMstDTO.getFxnumCheckList() != null) {
            checkList = pEBHEduApplicantMstDTO.getFxnumCheckList();
        }
        int cnt = -1;
        int fxCnt = -1;

        if(!checkList.isEmpty()) {
            for (String key : checkList) {
                pEBHEduApplicantMstDTO.setEpisdSeq(Integer.parseInt(key));
                tempDto = pEBHEduApplicantMapper.selectFxnumChk(pEBHEduApplicantMstDTO);
                cnt = Integer.parseInt(tempDto.getCnt());
                fxCnt = Integer.parseInt(tempDto.getFxnumCnt());
                if ((fxCnt - cnt) <= 0 || (fxCnt - cnt) < checkList.size()) {
                    rtnDto.setFxnumStta("F");
                    break;
                }
            }
        }
        else {
            rtnDto = pEBHEduApplicantMapper.selectFxnumChk(pEBHEduApplicantMstDTO);
        }

        return rtnDto;
    }

}
