package com.kap.service.impl.wb.wbh;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.*;
import com.kap.core.dto.wb.wbh.*;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBHACalibrationService;
import com.kap.service.dao.wb.wbh.WBHACalibrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 신청업체관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBHACalibrationServiceImpl.java
 * @Description		: 신청업체관리 ServiceImpl
 * @author 김태훈
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBHACalibrationServiceImpl implements WBHACalibrationService {
    //Mapper
    private final WBHACalibrationMapper wbhaCalibrationMapper;
    private final COFileService cOFileService;

    /* 회차관리 마스터 시퀀스 */
    private final EgovIdGnrService cxEpisdSeqIdgen;
    //상생신청 마스터 시퀀스
    private final EgovIdGnrService cxAppctnMstSeqIdgen;
    //대상장비 상세 시퀀스
    private final EgovIdGnrService cxAppctnTchlgSeqIdgen;
    //부품사관리 시퀀스
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;
    //상생신청 상세 시퀀스
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;
    //참여이관 시퀀스
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;

    @Override
    public WBHAConsultingDTO getConsultingList(WBHAConsultingDTO wbaConsultingDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbaConsultingDTO.getPageIndex());
        page.setRecordCountPerPage(wbaConsultingDTO.getListRowSize());

        page.setPageSize(wbaConsultingDTO.getPageRowSize());

        wbaConsultingDTO.setFirstIndex(page.getFirstRecordIndex());
        wbaConsultingDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wbaConsultingDTO.setList(wbhaCalibrationMapper.getConsultingList(wbaConsultingDTO));
        wbaConsultingDTO.setTotalCount(wbhaCalibrationMapper.getConsultingCount(wbaConsultingDTO));

        return wbaConsultingDTO;
    }

    /**
     * 진행상태 목록을 조회한다.
     * @return
     */
    public WBHAApplyMstDTO selectCompanyDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        WBHAApplyMstDTO wbhaApplyMstDTO = wbhaCalibrationMapper.selectApplyMst(wbhaCalibrationSearchDTO);

        List<WBHAApplyDtlDTO> selectApplyDtlList = wbhaCalibrationMapper.selectApplyDtlList(wbhaCalibrationSearchDTO);
        List<WBHAEuipmentDTO> selectEuipemtDtlList = wbhaCalibrationMapper.selectEuipemtDtlList(wbhaCalibrationSearchDTO);

        wbhaApplyMstDTO.setEuipmentList(selectEuipemtDtlList);

        wbhaApplyMstDTO.setApplyList(new ArrayList<WBHAApplyDtlDTO>());

        for (WBHAApplyDtlDTO dtlDto : selectApplyDtlList) {

            List<WBHAApplyDtlDTO> fileList = wbhaCalibrationMapper.selectFileList(dtlDto);
            List<WBHAMsEuipmentDTO> msEuipmentList = wbhaCalibrationMapper.selectMsEuipmentList(dtlDto);

            dtlDto.setFileInfoList(new ArrayList<WBHAApplyDtlDTO>());
            dtlDto.setMsEquipmentList(new ArrayList<WBHAMsEuipmentDTO>());

            //단계별 파일정보 넣기
            for(WBHAApplyDtlDTO fileDTO : fileList) {
                if (fileDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && fileDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getFileInfoList().add(fileDTO);
                }
            }

            //단계별 계측장비정보 넣기
            for(WBHAMsEuipmentDTO msEuipmentDTO : msEuipmentList) {
                if (msEuipmentDTO.getRsumeSeq().equals(dtlDto.getRsumeSeq()) && msEuipmentDTO.getRsumeOrd().equals(dtlDto.getRsumeOrd())) {
                    dtlDto.getMsEquipmentList().add(msEuipmentDTO);
                }
            }

            wbhaApplyMstDTO.getApplyList().add(dtlDto);
        }

        wbhaApplyMstDTO.setRsumeSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeSttsNm());
        wbhaApplyMstDTO.setMngSttsNm(selectApplyDtlList.get(selectApplyDtlList.size()-1).getMngSttsNm());
        wbhaApplyMstDTO.setStageOrd(selectApplyDtlList.get(selectApplyDtlList.size()-1).getRsumeOrd());

        return wbhaApplyMstDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     * @return
     */
    public WBHACompanyDTO selectCompanyUserDtl(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO) throws Exception {

        WBHACompanyDTO wbhaCompanyDTO = new WBHACompanyDTO();

        wbhaCompanyDTO = wbhaCalibrationMapper.getCompanyInfo(wbhaCalibrationSearchDTO);
        List<WBHACompanyDTO> sqList = wbhaCalibrationMapper.selectSqList(wbhaCalibrationSearchDTO);
        wbhaCompanyDTO.setSqInfoList(sqList);
        wbhaCompanyDTO.setMemSeq(wbhaCalibrationSearchDTO.getMemSeq());

        return wbhaCompanyDTO;
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insert(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {

            //신청시간 년도 구하기
            Date now = new Date();
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

            wbhaApplyMstDTO.setYear(yyyy.format(now));

            WBRoundMstSearchDTO round = wbhaCalibrationMapper.getExisdDtl(wbhaApplyMstDTO);

            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            if (round == null) {
                //해당년도 회차 미 존재시 신규회차 생성
                int exipsdSeq = cxEpisdSeqIdgen.getNextIntegerId();

                WBRoundMstSearchDTO roundDtl = new WBRoundMstSearchDTO();

                roundDtl.setEpisdSeq(exipsdSeq);
                roundDtl.setBsnCd(wbhaApplyMstDTO.getBsnCd());
                roundDtl.setYear(Integer.valueOf(wbhaApplyMstDTO.getYear()));
                roundDtl.setEpisd(1); //회차는 1회차 고정
                roundDtl.setAccsStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setAccsEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setBsnStrtDtm(wbhaApplyMstDTO.getYear()+"-01-01 00:00:00");
                roundDtl.setBsnEndDtm(wbhaApplyMstDTO.getYear()+"-12-31 23:59:59");
                roundDtl.setExpsYn("Y");
                roundDtl.setRegId(regId);
                roundDtl.setRegIp(regIp);

                wbhaCalibrationMapper.insetRound(roundDtl);

                wbhaApplyMstDTO.setEpisdSeq(exipsdSeq);
            } else {
                wbhaApplyMstDTO.setEpisdSeq(round.getEpisdSeq());
            }

            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            wbhaApplyMstDTO.setAppctnSeq(appctnSeq);
            wbhaApplyMstDTO.setAppctnBsnmNo(wbhaCompanyDTO.getBsnmNo());
            wbhaApplyMstDTO.setRegId(regId);
            wbhaApplyMstDTO.setRegIp(regIp);

            rtnCnt = wbhaCalibrationMapper.insertApply(wbhaApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                WBHAApplyDtlDTO wbhaApplyDtlDTO = new WBHAApplyDtlDTO();
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setAppctnSeq(wbhaApplyMstDTO.getAppctnSeq());
                wbhaApplyDtlDTO.setRsumeOrd(1);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07001");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_02_001");
                wbhaApplyDtlDTO.setRegId(regId);
                wbhaApplyDtlDTO.setRegIp(regIp);

                wbhaCalibrationMapper.insertApplyDtl(wbhaApplyDtlDTO);


                //신청대상장비 생성
                for(int j=0; j<wbhaApplyMstDTO.getEuipmentList().size(); j++) {
                    WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wbhaEuipmentDTO.setAppctnSeq(appctnSeq);
                    wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wbhaEuipmentDTO.setTchlgOrd(j+1);
                    wbhaEuipmentDTO.setTchlgNm(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wbhaEuipmentDTO.setTchlgCnt(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wbhaEuipmentDTO.setRegId(regId);
                    wbhaEuipmentDTO.setRegIp(regIp);

                    wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
                }

                //신청파일 넣기
                for (int i = 0; i < wbhaApplyMstDTO.getFileList().size(); i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    fileList.add(wbhaApplyMstDTO.getFileList().get(i));

                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                    wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    wbhaApplyDtlDTO.setFileCd(wbhaApplyMstDTO.getFileCdList().get(i));

                    wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
                }

                // 부품사 정보수정 Star
                wbhaCompanyDTO.setRegId(regId);
                wbhaCompanyDTO.setRegIp(regIp);
                wbhaCompanyDTO.setModId(regId);
                wbhaCompanyDTO.setModIp(regIp);

                //부품사 회원정보 수정
                wbhaCalibrationMapper.updatePartUser(wbhaCompanyDTO);

                String seq = "";

                if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for (int t = 0; t < wbhaCompanyDTO.getSqInfoList().size(); t++) {
                        seq = String.valueOf(wbhaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());

                        wbhaCompanyDTO.setYear(wbhaCompanyDTO.getSqInfoList().get(t).getYear());
                        wbhaCompanyDTO.setScore(wbhaCompanyDTO.getSqInfoList().get(t).getScore());
                        wbhaCompanyDTO.setNm(wbhaCompanyDTO.getSqInfoList().get(t).getNm());
                        wbhaCompanyDTO.setCrtfnCmpnNm(wbhaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wbhaCompanyDTO.setTchlg5StarCd(null);
                        wbhaCompanyDTO.setPay5StarCd(null);
                        wbhaCompanyDTO.setQlty5StarCd(null);
                        wbhaCompanyDTO.setTchlg5StarYear(null);
                        wbhaCompanyDTO.setPay5StarYear(null);
                        wbhaCompanyDTO.setQlty5StarYear(null);

                        if (!seq.isEmpty()) {
                            wbhaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wbhaCalibrationMapper.updatePartsComSQInfo(wbhaCompanyDTO);
                        } else {
                            wbhaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wbhaCalibrationMapper.insertPartsComSQInfo(wbhaCompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wbhaCalibrationMapper.deletePartsComSQInfo(wbhaCompanyDTO);
                }

                wbhaCalibrationMapper.updatePartsCompany(wbhaCompanyDTO);
                // 부품사 수정 End
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 부품사 신청자를 수정한다.
     */
    @Transactional
    public int update(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            wbhaApplyMstDTO.setAppctnBsnmNo(wbhaCompanyDTO.getBsnmNo());
            wbhaApplyMstDTO.setModId(modId);
            wbhaApplyMstDTO.setModIp(modIp);

            rtnCnt = wbhaCalibrationMapper.updateApply(wbhaApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상태 업데이트
                //관리자상태에 따라 분기처리해야함
                WBHAApplyDtlDTO wbhaApplyDtlDTO = new WBHAApplyDtlDTO();
                wbhaApplyDtlDTO.setRsumeSeq(wbhaApplyMstDTO.getRsumeSeq());
                wbhaApplyDtlDTO.setRsumeOrd(wbhaApplyMstDTO.getRsumeOrd());
                wbhaApplyDtlDTO.setAppctnSeq(Integer.valueOf(wbhaApplyMstDTO.getDetailsKey()));
                wbhaApplyDtlDTO.setMngSttsCd(wbhaApplyMstDTO.getMngSttsCd());
                wbhaApplyDtlDTO.setRegId(modId);
                wbhaApplyDtlDTO.setRegIp(modIp);
                wbhaApplyDtlDTO.setModId(modId);
                wbhaApplyDtlDTO.setModIp(modIp);


                //신청대상장비 생성시작
                wbhaCalibrationMapper.deleteEuipment(wbhaApplyDtlDTO);

                for(int j=0; j<wbhaApplyMstDTO.getEuipmentList().size(); j++) {
                    WBHAEuipmentDTO wbhaEuipmentDTO = new WBHAEuipmentDTO();
                    int tchlgSeq = cxAppctnTchlgSeqIdgen.getNextIntegerId();
                    wbhaEuipmentDTO.setAppctnSeq(wbhaApplyDtlDTO.getAppctnSeq());
                    wbhaEuipmentDTO.setTchlgSeq(tchlgSeq);
                    wbhaEuipmentDTO.setTchlgOrd(j+1);
                    wbhaEuipmentDTO.setTchlgNm(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgNm());
                    wbhaEuipmentDTO.setTchlgCnt(wbhaApplyMstDTO.getEuipmentList().get(j).getTchlgCnt());
                    wbhaEuipmentDTO.setRegId(modId);
                    wbhaEuipmentDTO.setRegIp(modIp);

                    wbhaCalibrationMapper.insertEuipment(wbhaEuipmentDTO);
                }

                //단계별 프로세스
                wbhaApplyDtlDTO = stepUpdateProcess(wbhaApplyMstDTO,wbhaApplyDtlDTO, wbhaMsEuipmentDTO);

                //신청자 변경
                if ("Y".equals(wbhaApplyMstDTO.getUserLogYn())) {
                    WBBATransDTO wbbTransDTO = new WBBATransDTO();

                    wbbTransDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
                    wbbTransDTO.setAppctnSeq(Integer.valueOf(wbhaApplyMstDTO.getDetailsKey()));
                    wbbTransDTO.setBfreMemSeq(wbhaApplyMstDTO.getWbbTransDTO().getBfreMemSeq());
                    wbbTransDTO.setAftrMemSeq(wbhaApplyMstDTO.getWbbTransDTO().getAftrMemSeq());
                    wbbTransDTO.setRegId(modId);
                    wbbTransDTO.setRegIp(modIp);

                    //상생참여이관로그 생성
                    wbhaCalibrationMapper.insertTransUserLog(wbbTransDTO);
                }


                //부품사 회원정보 수정 START
                wbhaCompanyDTO.setRegId(modId);
                wbhaCompanyDTO.setRegIp(modIp);
                wbhaCompanyDTO.setModId(modId);
                wbhaCompanyDTO.setModIp(modIp);
                wbhaCalibrationMapper.updatePartUser(wbhaCompanyDTO);

                // 부품사 정보수정 Start
                String seq = "";


                if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for(int t=0; t<wbhaCompanyDTO.getSqInfoList().size();t++) {
                        seq = String.valueOf(wbhaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                        wbhaCompanyDTO.setYear(wbhaCompanyDTO.getSqInfoList().get(t).getYear());
                        wbhaCompanyDTO.setScore(wbhaCompanyDTO.getSqInfoList().get(t).getScore());
                        wbhaCompanyDTO.setNm(wbhaCompanyDTO.getSqInfoList().get(t).getNm());
                        wbhaCompanyDTO.setCrtfnCmpnNm(wbhaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wbhaCompanyDTO.setTchlg5StarCd(null);
                        wbhaCompanyDTO.setPay5StarCd(null);
                        wbhaCompanyDTO.setQlty5StarCd(null);
                        wbhaCompanyDTO.setTchlg5StarYear(null);
                        wbhaCompanyDTO.setPay5StarYear(null);
                        wbhaCompanyDTO.setQlty5StarYear(null);

                        if (!seq.isEmpty()) {
                            wbhaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wbhaCalibrationMapper.updatePartsComSQInfo(wbhaCompanyDTO);
                        } else {
                            wbhaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wbhaCalibrationMapper.insertPartsComSQInfo(wbhaCompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wbhaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wbhaCalibrationMapper.deletePartsComSQInfo(wbhaCompanyDTO);
                }

                wbhaCalibrationMapper.updatePartsCompany(wbhaCompanyDTO);
                // 부품사 수정 End
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    public WBHAApplyDtlDTO stepUpdateProcess(WBHAApplyMstDTO wbhaApplyMstDTO, WBHAApplyDtlDTO wbhaApplyDtlDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO) throws Exception {
        //검교정 단계별 분기 ( 1: 신청,  2: 심사   3:증빙 )
        if (wbhaApplyDtlDTO.getRsumeOrd() == 1) {
            //신청단계
            if ("PRO_TYPE07001_02_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_006");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);

                //PRO_TYPE07001_01_006 선정 다음스텝 생성.
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setRsumeOrd(2);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07002");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_04_001");

                wbhaCalibrationMapper.insertApplyDtl(wbhaApplyDtlDTO);

            } else if ("PRO_TYPE07001_02_002".equals(wbhaApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_02_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE06001_02_002 보완요청, PRO_TYPE07001_02_005 미선정 _ 사유필요
                if ("PRO_TYPE07001_02_002".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_002");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_005");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                }
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            } else if ("PRO_TYPE07001_02_004".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                // PRO_TYPE07001_02_004 사용자취소
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_01_004");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }
        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 2) {
            //심사단계
            if ("PRO_TYPE07001_04_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_006 적합 다음스텝 생성
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);

                //PRO_TYPE07001_04_006 선정 다음스텝 생성.
                wbhaApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbhaApplyDtlDTO.setRsumeOrd(3);
                wbhaApplyDtlDTO.setRsumeSttsCd("PRO_TYPE07003");
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_001");
                wbhaApplyDtlDTO.setMngSttsCd("PRO_TYPE07001_06_001");

                wbhaCalibrationMapper.insertApplyDtl(wbhaApplyDtlDTO);

            } else if ("PRO_TYPE07001_04_003".equals(wbhaApplyDtlDTO.getMngSttsCd()) || "PRO_TYPE07001_04_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_04_003 보완요청, PRO_TYPE07001_04_005 부적합
                if ("PRO_TYPE07001_04_003".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_003");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                } else {
                    wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_03_005");
                    wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
                }

                wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
            }

            wbhaMsEuipmentDTO.setRegId(wbhaApplyDtlDTO.getRegId());
            wbhaMsEuipmentDTO.setRegIp(wbhaApplyDtlDTO.getRegIp());

            //심사단계 데이터처리(PK가 없어 삭제후 재등록)
            wbhaCalibrationMapper.deleteMsEuipment(wbhaMsEuipmentDTO);
            wbhaCalibrationMapper.insertMsEuipment(wbhaMsEuipmentDTO);

        } else if (wbhaApplyDtlDTO.getRsumeOrd() == 3) {
            //증빙단계
            if ("PRO_TYPE07001_06_003".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_003 보완요청
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_003");
                wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_005".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_005 부적합
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_005");
                wbhaApplyDtlDTO.setRtrnRsnCntn(wbhaApplyMstDTO.getRtrnRsnCntn());
            } else if ("PRO_TYPE07001_06_006".equals(wbhaApplyDtlDTO.getMngSttsCd())) {
                //PRO_TYPE07001_06_006 적합
                wbhaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE07001_05_006");
            }
            wbhaCalibrationMapper.updateApplyStatus(wbhaApplyDtlDTO);
        }

        //파일처리
        if (wbhaApplyDtlDTO.getRsumeOrd() == 1 || wbhaApplyDtlDTO.getRsumeOrd() == 3) {
            if (wbhaApplyMstDTO.getFileList() != null) {
                wbhaCalibrationMapper.deleteFileInfo(wbhaApplyDtlDTO);

                //신청파일 넣기
                for (int i = 0; i < wbhaApplyMstDTO.getFileList().size() ; i++) {
                    List<COFileDTO> fileList = new ArrayList();
                    COFileDTO fileDto = new COFileDTO();

                    fileDto.setStatus(wbhaApplyMstDTO.getFileList().get(i).getStatus());
                    fileDto.setWidth(wbhaApplyMstDTO.getFileList().get(i).getWidth());
                    fileDto.setHeight(wbhaApplyMstDTO.getFileList().get(i).getHeight());
                    fileDto.setWebPath(wbhaApplyMstDTO.getFileList().get(i).getWebPath());
                    fileDto.setFieldNm(wbhaApplyMstDTO.getFileList().get(i).getFieldNm());
                    fileDto.setOrgnFileNm(wbhaApplyMstDTO.getFileList().get(i).getOrgnFileNm());
                    fileDto.setFileDsc(wbhaApplyMstDTO.getFileList().get(i).getFileDsc());
                    fileDto.setFileOrd(wbhaApplyMstDTO.getFileList().get(i).getFileOrd());
                    fileList.add(fileDto);

                    if ("addedfile".equals(fileDto.getStatus())) {
                        wbhaApplyDtlDTO.setFileSeq(wbhaApplyMstDTO.getFileList().get(i).getFileSeq());
                    } else {
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wbhaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    }

                    wbhaApplyDtlDTO.setFileCd(wbhaApplyMstDTO.getFileCdList().get(i));
                    wbhaCalibrationMapper.insertFileInfo(wbhaApplyDtlDTO);
                }
            }
        }
        return  wbhaApplyDtlDTO;
    }

    public WBBATransDTO getTrnsfList(WBBATransDTO wbbaTransDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaTransDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaTransDTO.getListRowSize());

        page.setPageSize(wbbaTransDTO.getPageRowSize());

        wbbaTransDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaTransDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaTransDTO.setList(wbhaCalibrationMapper.getTrnsfList(wbbaTransDTO));
        wbbaTransDTO.setTotalCount(wbhaCalibrationMapper.getTrnsfCount(wbbaTransDTO));

        return wbbaTransDTO;
    }
}
