package com.kap.service.impl.wb.wbb;

import com.kap.common.utility.CODateUtil;
import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.common.utility.COStringUtil;
import com.kap.core.dto.*;
import com.kap.core.dto.sm.smi.SMISmsCntnDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.WBSendDTO;
import com.kap.core.dto.wb.wbb.*;
import com.kap.core.utility.COFileUtil;
import com.kap.service.*;
import com.kap.service.dao.wb.wbb.WBBARoundMapper;
import com.kap.service.dao.wb.wbb.WBBBCompanyMapper;
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

/**
 * <pre>
 * 공통 부품사관리 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBBBCompanyServiceImpl.java
 * @Description		: 공통 회차관리 ServiceImpl
 * @author 김태훈
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.27		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBBBCompanyServiceImpl implements WBBBCompanyService {

    //Mapper
    private final WBBBCompanyMapper wbbbCompanyMapper;
    private final WBBARoundMapper wBBARoundMapper;

    //파일 업로드 유틸
    private final COFileUtil cOFileUtil;
    private final COFileService cOFileService;

    //이메일 발송
    private final COMessageService cOMessageService;
    // SMS 내용 관리 서비스
    private final SMISmsCntnService smiSmsCntnService;

    /* 시퀀스 */
    //상생신청 마스터 시퀀스
    private final EgovIdGnrService cxAppctnMstSeqIdgen;
    //상생신청 상세 시퀀스
    private final EgovIdGnrService cxAppctnRsumeDtlSeqIdgen;
    private final EgovIdGnrService fileApplyIdgen;
    private final EgovIdGnrService cxAppctnTrnsfDtlIdgen;

    //부품사관리 시퀀스
    private final EgovIdGnrService mpePartsCompanyDtlIdgen;

    /**
     * 신청부품사 목록 List Get
     */
    public WBBACompanySearchDTO selectCompanyList(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaCompanySearchDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaCompanySearchDTO.getListRowSize());

        page.setPageSize(wbbaCompanySearchDTO.getPageRowSize());

        wbbaCompanySearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaCompanySearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaCompanySearchDTO.setList(wbbbCompanyMapper.getRegisterCompanyList(wbbaCompanySearchDTO));
        wbbaCompanySearchDTO.setTotalCount(wbbbCompanyMapper.getRegisterCompanyCount(wbbaCompanySearchDTO));

        return wbbaCompanySearchDTO;
    }

    /**
     * 진행상태 목록을 조회한다.
     *
     * @return
     */
    public WBBAApplyMstDTO selectCompanyDtl(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {

        WBBAApplyMstDTO wbbApplyMstDTO = wbbbCompanyMapper.selectApplyMst(wbbaCompanySearchDTO);

        List<WBBAApplyDtlDTO> selectStepDtlList = wbbbCompanyMapper.selectStepDtlList(wbbaCompanySearchDTO);

        for (WBBAApplyDtlDTO stpDtlDto : selectStepDtlList) {
            stpDtlDto.setApplyList(new ArrayList<WBBAApplyDtlDTO>());

            List<WBBAApplyDtlDTO> wbbApplyDtlDTOList = wbbbCompanyMapper.selectApplyDtlList(wbbaCompanySearchDTO);

            for (WBBAApplyDtlDTO dtlDTO : wbbApplyDtlDTOList) {
                if (stpDtlDto.getStageOrd() == dtlDTO.getRsumeOrd()) {
                    stpDtlDto.setApplyDtl(dtlDTO);
                    List<WBBAApplyDtlDTO> wbbApplyOptnList = wbbbCompanyMapper.selectApplyOptnList(dtlDTO);

                    dtlDTO.setApplyOptnList(new ArrayList<WBBAApplyDtlDTO>());
                    for (WBBAApplyDtlDTO wbbApplyOptnDTO : wbbApplyOptnList) {
                        if (dtlDTO.getRsumeSeq().equals(wbbApplyOptnDTO.getRsumeSeq()) && dtlDTO.getRsumeOrd().equals(wbbApplyOptnDTO.getRsumeOrd())) {
                            dtlDTO.getApplyOptnList().add(wbbApplyOptnDTO);
                        }
                    }
                    stpDtlDto.setApplyOptnList(dtlDTO.getApplyOptnList());
                }

            }

            List<WBBAApplyDtlDTO> optnDTOList = wbbbCompanyMapper.selectOptnList(stpDtlDto.getStageSeq());
            stpDtlDto.setApplyTempOptnList(optnDTOList);

            wbbApplyMstDTO.setStageOrd(wbbApplyDtlDTOList.get(wbbApplyDtlDTOList.size() - 1).getRsumeOrd());
            wbbApplyMstDTO.setRsumeSttsNm(wbbApplyDtlDTOList.get(wbbApplyDtlDTOList.size() - 1).getRsumeSttsCd());
            wbbApplyMstDTO.setMngSttsNm(wbbApplyDtlDTOList.get(wbbApplyDtlDTOList.size() - 1).getMngSttsNm());
        }

        wbbApplyMstDTO.setMaxStage(selectStepDtlList.get(selectStepDtlList.size() - 1).getStageOrd());
        wbbApplyMstDTO.setApplyList(selectStepDtlList);

        return wbbApplyMstDTO;
    }

    /**
     * 부품사 회원 정보를 조회한다.
     *
     * @return
     */
    public WBBACompanyDTO selectCompanyUserDtl(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {

        WBBACompanyDTO wbbCompanyDTO = new WBBACompanyDTO();

        wbbCompanyDTO = wbbbCompanyMapper.getCompanyInfo(wbbaCompanySearchDTO);
        List<WBBACompanyDTO> sqList = wbbbCompanyMapper.selectSqList(wbbaCompanySearchDTO);
        wbbCompanyDTO.setSqInfoList(sqList);
        wbbCompanyDTO.setMemSeq(wbbaCompanySearchDTO.getMemSeq());

        return wbbCompanyDTO;
    }

    /**
     * 진행상태 목록 조회을 조회한다.
     *
     * @return
     */
    public List<WBBACompanySearchDTO> selectProgressList(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {

        return wbbbCompanyMapper.selectProgressList(wbbaCompanySearchDTO);
    }

    /**
     * 연도 상세 조회
     *
     * @return
     */
    public List<WBBACompanySearchDTO> selectYearDtl(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {

        return wbbbCompanyMapper.selectYearDtl(wbbaCompanySearchDTO);
    }

    public List<WBBACompanySearchDTO> getEplisdsList(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {
        return wbbbCompanyMapper.getEplisdsList(wbbaCompanySearchDTO);
    }

    public String getFileYn(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {
        return wbbbCompanyMapper.getFileYn(wbbaCompanySearchDTO);
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insert(WBBACompanyDTO wbbaCompanyDTO, WBBAApplyMstDTO wbbaApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {

            int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
            //마스터 생성
            String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String regIp = CONetworkUtil.getMyIPaddress(request);

            wbbaApplyMstDTO.setAppctnSeq(appctnSeq);
            wbbaApplyMstDTO.setAppctnBsnmNo(wbbaCompanyDTO.getBsnmNo());
            wbbaApplyMstDTO.setRegId(regId);
            wbbaApplyMstDTO.setRegIp(regIp);

            rtnCnt = wbbbCompanyMapper.insertApply(wbbaApplyMstDTO);

            if (rtnCnt > 0) {
                //상생신청진행 상세 생성
                WBBAApplyDtlDTO wbbApplyDtlDTO = new WBBAApplyDtlDTO();
                wbbApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                wbbApplyDtlDTO.setAppctnSeq(wbbaApplyMstDTO.getAppctnSeq());
                wbbApplyDtlDTO.setRsumeOrd(1);
                wbbApplyDtlDTO.setRsumeSttsCd("신청");
                wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_2");
                wbbApplyDtlDTO.setMngSttsCd("PRO_TYPE04_1_2");
                wbbApplyDtlDTO.setRegId(regId);
                wbbApplyDtlDTO.setRegIp(regIp);
                wbbbCompanyMapper.insertApplyDtl(wbbApplyDtlDTO);

                //신청파일 넣기
                for (int i = 0; i < wbbaApplyMstDTO.getFileList().size(); i++) {

                    List<COFileDTO> fileList = new ArrayList();
                    fileList.add(wbbaApplyMstDTO.getFileList().get(i));

                    HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                    wbbApplyDtlDTO.setSbmsnSeq(fileApplyIdgen.getNextIntegerId());
                    wbbApplyDtlDTO.setOptnSeq(Integer.valueOf(wbbaApplyMstDTO.getOptnSeq().get(i)));
                    wbbApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));

                    wbbbCompanyMapper.insertFileInfo(wbbApplyDtlDTO);
                }

                //EDM,SMS발송
                WBSendDTO wbSendDTO = new WBSendDTO();
                wbSendDTO.setMemSeq(wbbaApplyMstDTO.getMemSeq());
                wbSendDTO.setEpisdSeq(wbbaApplyMstDTO.getEpisdSeq());

                send(wbSendDTO,"SMS04");
                
                wbbaCompanyDTO.setRegId(regId);
                wbbaCompanyDTO.setRegIp(regIp);
                wbbaCompanyDTO.setModId(regId);
                wbbaCompanyDTO.setModIp(regIp);

                //부품사 회원정보 수정
                wbbbCompanyMapper.updatePartUser(wbbaCompanyDTO);

                // 부품사 정보수정 Start
                String seq = "";

                if (wbbaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                    int index = 1;

                    for (int t = 0; t < wbbaCompanyDTO.getSqInfoList().size(); t++) {
                        seq = String.valueOf(wbbaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());

                        wbbaCompanyDTO.setYear(wbbaCompanyDTO.getSqInfoList().get(t).getYear());
                        wbbaCompanyDTO.setScore(wbbaCompanyDTO.getSqInfoList().get(t).getScore());
                        wbbaCompanyDTO.setNm(wbbaCompanyDTO.getSqInfoList().get(t).getNm());
                        wbbaCompanyDTO.setCrtfnCmpnNm(wbbaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                        // 2차인 경우, 스타등급 빈 값 처리
                        wbbaCompanyDTO.setTchlg5starCd(null);
                        wbbaCompanyDTO.setPay5starCd(null);
                        wbbaCompanyDTO.setQlty5starCd(null);
                        wbbaCompanyDTO.setTchlg5starYear(null);
                        wbbaCompanyDTO.setPay5starYear(null);
                        wbbaCompanyDTO.setQlty5starYear(null);

                        if (!seq.isEmpty()) {
                            wbbaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                            wbbbCompanyMapper.updatePartsComSQInfo(wbbaCompanyDTO);
                        } else {
                            wbbaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                            wbbbCompanyMapper.insertPartsComSQInfo(wbbaCompanyDTO);
                        }
                        index += 1;
                    }
                } else if (wbbaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                    // 1차인 경우, SQ정보 빈 값 처리
                    wbbbCompanyMapper.deletePartsComSQInfo(wbbaCompanyDTO);
                }

                wbbbCompanyMapper.updatePartsCompany(wbbaCompanyDTO);
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
    public int update(WBBACompanyDTO wbbaCompanyDTO, WBBAApplyMstDTO wbbaApplyMstDTO, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            wbbaApplyMstDTO.setAppctnBsnmNo(wbbaCompanyDTO.getBsnmNo());
            wbbaApplyMstDTO.setModId(modId);
            wbbaApplyMstDTO.setModIp(modIp);

            rtnCnt = wbbbCompanyMapper.updateApply(wbbaApplyMstDTO);

            /* 2024-08-29 수정 s - 이전 단계 첨부파일 수정 가능하도록 */
            if (rtnCnt > 0) {
                //상생신청진행 상태 업데이트
                //관리자상태에 따라 분기처리해야함
                WBBAApplyDtlDTO wbbApplyDtlDTO = new WBBAApplyDtlDTO();
                wbbApplyDtlDTO.setRsumeSeq(wbbaApplyMstDTO.getRsumeSeq());
                wbbApplyDtlDTO.setRsumeOrd(wbbaApplyMstDTO.getRsumeOrd());
                wbbApplyDtlDTO.setAppctnSeq(Integer.valueOf(wbbaApplyMstDTO.getDetailsKey()));
                wbbApplyDtlDTO.setMngSttsCd(wbbaApplyMstDTO.getMngSttsCd());
                wbbApplyDtlDTO.setRegId(modId);
                wbbApplyDtlDTO.setRegIp(modIp);
                wbbApplyDtlDTO.setModId(modId);
                wbbApplyDtlDTO.setModIp(modIp);

                if (wbbaApplyMstDTO.getFileList() != null) {
                    wbbbCompanyMapper.deleteFileInfo(wbbApplyDtlDTO);

                    //신청파일 넣기
                    int i = 0;
                    for (COFileDTO file : wbbaApplyMstDTO.getFileList()) {
                        if("delfile".equals(file.getStatus())) {
                            continue;
                        }

                        List<COFileDTO> fileList = new ArrayList();
                        COFileDTO fileDto = new COFileDTO();

                        fileDto.setStatus(file.getStatus());
                        fileDto.setWidth(file.getWidth());
                        fileDto.setHeight(file.getHeight());
                        fileDto.setWebPath(file.getWebPath());
                        fileDto.setFieldNm(file.getFieldNm());
                        fileDto.setOrgnFileNm(file.getOrgnFileNm());
                        fileDto.setFileDsc(file.getFileDsc());
                        fileDto.setFileOrd(file.getFileOrd());
                        fileList.add(fileDto);

                        if ("addedfile".equals(fileDto.getStatus())) {
                            wbbApplyDtlDTO.setFileSeq(file.getFileSeq());
                        } else {
                            System.out.println("FILE@@@@@@@@@@@@@@@@@@@@@@");
                            System.out.println(fileDto.toString());
                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                            if(file.getFileSeq() != null) {
                                wbbApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq" + file.getFileSeq()));
                            }
                            else {
                                wbbApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                            }
                        }

                        wbbApplyDtlDTO.setSbmsnSeq(fileApplyIdgen.getNextIntegerId());
                        wbbApplyDtlDTO.setOptnSeq(wbbaApplyMstDTO.getOptnSeq().get(i));

                        wbbbCompanyMapper.insertFileInfo(wbbApplyDtlDTO);
                        i++;
                    }
                    /*for (int i = 0; i < wbbaApplyMstDTO.getFileList().size(); i++) {
                        if("delfile".equals(wbbaApplyMstDTO.getFileList().get(i).getStatus())) {
                            if(i > 0) {
                                i--;
                            }
                            continue;
                        }

                        List<COFileDTO> fileList = new ArrayList();
                        COFileDTO fileDto = new COFileDTO();

                        fileDto.setStatus(wbbaApplyMstDTO.getFileList().get(i).getStatus());
                        fileDto.setWidth(wbbaApplyMstDTO.getFileList().get(i).getWidth());
                        fileDto.setHeight(wbbaApplyMstDTO.getFileList().get(i).getHeight());
                        fileDto.setWebPath(wbbaApplyMstDTO.getFileList().get(i).getWebPath());
                        fileDto.setFieldNm(wbbaApplyMstDTO.getFileList().get(i).getFieldNm());
                        fileDto.setOrgnFileNm(wbbaApplyMstDTO.getFileList().get(i).getOrgnFileNm());
                        fileDto.setFileDsc(wbbaApplyMstDTO.getFileList().get(i).getFileDsc());
                        fileDto.setFileOrd(wbbaApplyMstDTO.getFileList().get(i).getFileOrd());
                        fileList.add(fileDto);

                        if ("addedfile".equals(fileDto.getStatus())) {
                            wbbApplyDtlDTO.setFileSeq(wbbaApplyMstDTO.getFileList().get(i).getFileSeq());
                        } else {
                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                            wbbApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                        }

                        wbbApplyDtlDTO.setSbmsnSeq(fileApplyIdgen.getNextIntegerId());
                        wbbApplyDtlDTO.setOptnSeq(wbbaApplyMstDTO.getOptnSeq().get(i));

                        wbbbCompanyMapper.insertFileInfo(wbbApplyDtlDTO);
                    }*/
                }

                if(Objects.equals(wbbaApplyMstDTO.getTabIndex(), wbbaApplyMstDTO.getNowIndex())) {
                    //EDM & SMS 셋팅
                    WBSendDTO wbSendDTO = new WBSendDTO();
                    wbSendDTO.setMemSeq(wbbaApplyMstDTO.getMemSeq());
                    wbSendDTO.setEpisdSeq(wbbaApplyMstDTO.getEpisdSeq());
                    wbSendDTO.setStageNm(wbbaApplyMstDTO.getStageNm());
                    wbSendDTO.setReason(wbbaApplyMstDTO.getRtrnRsnCntn());
                    wbSendDTO.setRsumeSeq(wbbaApplyMstDTO.getRsumeSeq());
                    wbSendDTO.setRsumeOrd(wbbaApplyMstDTO.getRsumeOrd());
                    wbSendDTO.setMngSttdCd(wbbaApplyMstDTO.getMngSttsCd());

                    if ("PRO_TYPE04_1_6".equals(wbbaApplyMstDTO.getMngSttsCd()) || "PRO_TYPE04_1_8".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                        //적합(PRO_TYPE04_1_6), 선정(PRO_TYPE04_1_8)의 경우 다음단계 생성
                        if ("PRO_TYPE04_1_6".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_7");
                        } else if ("PRO_TYPE04_1_8".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_9");
                        }

                        send(wbSendDTO, "SMS05");

                        wbbbCompanyMapper.updateApplyStatus(wbbApplyDtlDTO);

                        //다음 스텝 생성
                        if (wbbaApplyMstDTO.getMaxStage() > wbbApplyDtlDTO.getRsumeOrd()) {
                            wbbApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                            wbbApplyDtlDTO.setRsumeOrd(wbbApplyDtlDTO.getRsumeOrd() + 1);
                            wbbApplyDtlDTO.setRsumeSttsCd(wbbaApplyMstDTO.getNextStageNm());
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_1");
                            wbbApplyDtlDTO.setMngSttsCd("PRO_TYPE04_1_2"); //2024-07-23 접수전 코드 사용 안 하므로, 미확인 코드로 변경
                            wbbApplyDtlDTO.setRegId(modId);
                            wbbApplyDtlDTO.setRegIp(modIp);

                            wbbbCompanyMapper.insertApplyStep(wbbApplyDtlDTO);
                        }

                    } else if ("PRO_TYPE04_1_4".equals(wbbaApplyMstDTO.getMngSttsCd()) || "PRO_TYPE04_1_5".equals(wbbaApplyMstDTO.getMngSttsCd())
                            || "PRO_TYPE04_1_7".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                        //사용자취소(PRO_TYPE04_1_4), 부적합(PRO_TYPE04_1_5), 미선정(PRO_TYPE04_1_7) 의 경우 종료

                        if ("PRO_TYPE04_1_4".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_5");
                        } else if ("PRO_TYPE04_1_5".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                            wbbApplyDtlDTO.setRtrnRsnCntn(wbbaApplyMstDTO.getRtrnRsnCntn());
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_6");

                            send(wbSendDTO, "SMS07");

                        } else if ("PRO_TYPE04_1_7".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                            wbbApplyDtlDTO.setRtrnRsnCntn(wbbaApplyMstDTO.getRtrnRsnCntn());
                            wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_8");

                            send(wbSendDTO, "SMS07");
                        }
                        wbbbCompanyMapper.updateApplyStatus(wbbApplyDtlDTO);

                    } else if ("PRO_TYPE04_1_3".equals(wbbaApplyMstDTO.getMngSttsCd())) {
                        //보안요청(PRO_TYPE04_1_2)의 경우 사용자 보안요청 코드 삽입
                        wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_3");
                        wbbApplyDtlDTO.setRtrnRsnCntn(wbbaApplyMstDTO.getRtrnRsnCntn());

                        send(wbSendDTO, "SMS06");

                        wbbbCompanyMapper.updateApplyStatus(wbbApplyDtlDTO);
                    }

                    //신청자 변경
                    if ("Y".equals(wbbaApplyMstDTO.getUserLogYn())) {
                        WBBATransDTO wbbTransDTO = new WBBATransDTO();

                        wbbTransDTO.setTrnsfSeq(cxAppctnTrnsfDtlIdgen.getNextIntegerId());
                        wbbTransDTO.setAppctnSeq(Integer.valueOf(wbbaApplyMstDTO.getDetailsKey()));
                        wbbTransDTO.setBfreMemSeq(wbbaApplyMstDTO.getWbbTransDTO().getBfreMemSeq());
                        wbbTransDTO.setAftrMemSeq(wbbaApplyMstDTO.getWbbTransDTO().getAftrMemSeq());
                        wbbTransDTO.setRegId(modId);
                        wbbTransDTO.setRegIp(modIp);

                        //상생참여이관로그 생성
                        wbbbCompanyMapper.insertTransUserLog(wbbTransDTO);
                    }

                    wbbaCompanyDTO.setRegId(modId);
                    wbbaCompanyDTO.setRegIp(modIp);
                    wbbaCompanyDTO.setModId(modId);
                    wbbaCompanyDTO.setModIp(modIp);

                    //부품사 회원정보 수정
                    wbbbCompanyMapper.updatePartUser(wbbaCompanyDTO);

                    // 부품사 정보수정 Start
                    String seq = "";


                    if (wbbaCompanyDTO.getCtgryCd().equals("COMPANY01002")) {

                        int index = 1;

                        for (int t = 0; t < wbbaCompanyDTO.getSqInfoList().size(); t++) {
                            seq = String.valueOf(wbbaCompanyDTO.getSqInfoList().get(t).getCbsnSeq());


                            wbbaCompanyDTO.setYear(wbbaCompanyDTO.getSqInfoList().get(t).getYear());
                            wbbaCompanyDTO.setScore(wbbaCompanyDTO.getSqInfoList().get(t).getScore());
                            wbbaCompanyDTO.setNm(wbbaCompanyDTO.getSqInfoList().get(t).getNm());
                            wbbaCompanyDTO.setCrtfnCmpnNm(wbbaCompanyDTO.getSqInfoList().get(t).getCrtfnCmpnNm());

                            // 2차인 경우, 스타등급 빈 값 처리
                            wbbaCompanyDTO.setTchlg5starCd(null);
                            wbbaCompanyDTO.setPay5starCd(null);
                            wbbaCompanyDTO.setQlty5starCd(null);
                            wbbaCompanyDTO.setTchlg5starYear(null);
                            wbbaCompanyDTO.setPay5starYear(null);
                            wbbaCompanyDTO.setQlty5starYear(null);

                            if (!seq.isEmpty()) {
                                wbbaCompanyDTO.setCbsnSeq(Integer.valueOf(seq));
                                wbbbCompanyMapper.updatePartsComSQInfo(wbbaCompanyDTO);
                            } else {
                                wbbaCompanyDTO.setCbsnSeq(mpePartsCompanyDtlIdgen.getNextIntegerId());
                                wbbbCompanyMapper.insertPartsComSQInfo(wbbaCompanyDTO);
                            }
                            index += 1;
                        }
                    } else if (wbbaCompanyDTO.getCtgryCd().equals("COMPANY01001")) {
                        // 1차인 경우, SQ정보 빈 값 처리
                        wbbbCompanyMapper.deletePartsComSQInfo(wbbaCompanyDTO);
                    }

                    wbbbCompanyMapper.updatePartsCompany(wbbaCompanyDTO);
                    // 부품사 수정 End
                }
            }
            /* 2024-08-29 수정 e - 이전 단계 첨부파일 수정 가능하도록 */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    @Transactional
    public int delete(WBBACompanySearchDTO wbbaCompanySearchDTO) throws Exception {
        int rtnCnt = 0;

        //마스터 삭제
        rtnCnt = wbbbCompanyMapper.deleteApplyMst(wbbaCompanySearchDTO);

        if (rtnCnt > 0) {
            for (int i = 0; i < wbbaCompanySearchDTO.getDelValueList().size(); i++) {
                wbbaCompanySearchDTO.setDetailsKey(wbbaCompanySearchDTO.getDelValueList().get(i));
                List<WBBAApplyDtlDTO> deleteDtlList = wbbbCompanyMapper.selectApplyDtlList(wbbaCompanySearchDTO);

                for (int j = 0; j < deleteDtlList.size(); j++) {
                    wbbaCompanySearchDTO.setRsumeSeq(deleteDtlList.get(j).getRsumeSeq());
                    wbbbCompanyMapper.deleteOptnDtl(wbbaCompanySearchDTO);
                }
            }
            wbbbCompanyMapper.deleteApplyDtl(wbbaCompanySearchDTO);
            wbbbCompanyMapper.deleteTrans(wbbaCompanySearchDTO);
        }
        return rtnCnt;
    }

    public WBBATransDTO getTrnsfList(WBBATransDTO wbbaTransDTO) throws Exception {
        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbbaTransDTO.getPageIndex());
        page.setRecordCountPerPage(wbbaTransDTO.getListRowSize());

        page.setPageSize(wbbaTransDTO.getPageRowSize());

        wbbaTransDTO.setFirstIndex(page.getFirstRecordIndex());
        wbbaTransDTO.setRecordCountPerPage(page.getRecordCountPerPage());


        wbbaTransDTO.setList(wbbbCompanyMapper.getTrnsfList(wbbaTransDTO));
        wbbaTransDTO.setTotalCount(wbbbCompanyMapper.getTrnsfCount(wbbaTransDTO));

        return wbbaTransDTO;
    }

    /**
     * 엑셀 다운로드
     */
    @Override
    public void excelDownload(WBBACompanySearchDTO wbbaCompanySearchDTO, HttpServletResponse response) throws Exception {
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
        cell.setCellValue("진행상태");
        cell.setCellStyle(style_header);

        cell = row.createCell(4);
        cell.setCellValue("신청자상태값");
        cell.setCellStyle(style_header);

        cell = row.createCell(5);
        cell.setCellValue("관리자상태값");
        cell.setCellStyle(style_header);


        cell = row.createCell(6);
        cell.setCellValue("부품사명");
        cell.setCellStyle(style_header);

        cell = row.createCell(7);
        cell.setCellValue("사업자등록번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(8);
        cell.setCellValue("구분");
        cell.setCellStyle(style_header);

        cell = row.createCell(9);
        cell.setCellValue("규모");
        cell.setCellStyle(style_header);

        cell = row.createCell(10);
        cell.setCellValue("신청자(아이디)");
        cell.setCellStyle(style_header);

        cell = row.createCell(11);
        cell.setCellValue("휴대폰번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(12);
        cell.setCellValue("이메일");
        cell.setCellStyle(style_header);

        cell = row.createCell(13);
        cell.setCellValue("신청일시");
        cell.setCellStyle(style_header);

        cell = row.createCell(14);
        cell.setCellValue("사용자수정일");
        cell.setCellStyle(style_header);

        cell = row.createCell(15);
        cell.setCellValue("대표자명");
        cell.setCellStyle(style_header);

        cell = row.createCell(16);
        cell.setCellValue("설립일자");
        cell.setCellStyle(style_header);

        cell = row.createCell(17);
        cell.setCellValue("본사주소");
        cell.setCellStyle(style_header);

        cell = row.createCell(18);
        cell.setCellValue("우편번호");
        cell.setCellStyle(style_header);

        cell = row.createCell(19);
        cell.setCellValue("매출액(연도)");
        cell.setCellStyle(style_header);

        cell = row.createCell(20);
        cell.setCellValue("직원수");
        cell.setCellStyle(style_header);

        cell = row.createCell(21);
        cell.setCellValue("주생산품");
        cell.setCellStyle(style_header);

        // Body
        List<WBBACompanySearchDTO> list = wbbaCompanySearchDTO.getList();

        int maxOrd = 0;
        int length = 0;

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(rowNum++);

            length = list.size();
            maxOrd = list.size();

            //번호
            cell = row.createCell(0);
            cell.setCellValue(wbbaCompanySearchDTO.getTotalCount() - i);
            cell.setCellStyle(style_body);

            //사업연도
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getYear());
            cell.setCellStyle(style_body);

            //회차
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getEpisd());
            cell.setCellStyle(style_body);

            //진행상태
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getRsumeSttsCd());
            cell.setCellStyle(style_body);

            //신청자상태값
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getAppctnSttsCd());
            cell.setCellStyle(style_body);

            //관리자상태값
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getMngSttsCd());
            cell.setCellStyle(style_body);

            //부품사명
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getCmpnNm());
            cell.setCellStyle(style_body);

            //사업장번호
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getBsnmNo().substring(0, 3) + "-" + list.get(i).getBsnmNo().substring(3, 5) + "-" + list.get(i).getBsnmNo().substring(5));
            cell.setCellStyle(style_body);

            //구분
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getCtgryCdNm());
            cell.setCellStyle(style_body);

            //규모
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getSizeCdNm());
            cell.setCellStyle(style_body);

            //신청자(아이디)
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getName() + "(" + list.get(i).getId() + ")");
            cell.setCellStyle(style_body);

            //휴대폰번호
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getHpNo());
            cell.setCellStyle(style_body);

            //이메일
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(style_body);

            //등록일시
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getMngSttsChngDtm());
            cell.setCellStyle(style_body);

            //사용자수정일
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getAppctnSttsChngDtm());
            cell.setCellStyle(style_body);

            //대표자명
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getRprsntNm());
            cell.setCellStyle(style_body);

            //설립일자
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getStbsmDt());
            cell.setCellStyle(style_body);

            //본사주소
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getBscAddr() + " " + list.get(i).getDtlAddr());
            cell.setCellStyle(style_body);

            //우편번호
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getZipcode());
            cell.setCellStyle(style_body);

            //매출액
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getSlsPmt() + "억(" + list.get(i).getSlsYear() + "년)");
            cell.setCellStyle(style_body);

            //직원수
            cell = row.createCell(20);
            cell.setCellValue(list.get(i).getMpleCnt() + "명");
            cell.setCellStyle(style_body);

            //주요상품
            cell = row.createCell(21);
            cell.setCellValue(list.get(i).getMjrPrdct1() + "/" + list.get(i).getMjrPrdct2() + "/" + list.get(i).getMjrPrdct3());
            cell.setCellStyle(style_body);

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Timestamp(System.currentTimeMillis()));

        //컨텐츠 타입 및 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("KAP_신청부품사관리_", "UTF-8") + timeStamp + ".xlsx");

        // Excel File Output
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 부품사 신청자를 등록한다.
     */
    @Transactional
    public int insertApply(WBBAApplyMstDTO wbbaApplyMstDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();

            //기존 신청자인지 확인,,,
            WBRoundMstSearchDTO wBRoundMstSearchDTO = new WBRoundMstSearchDTO();
            wBRoundMstSearchDTO.setEpisdSeq(wbbaApplyMstDTO.getEpisdSeq());
            wBRoundMstSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

            rtnCnt = wBBARoundMapper.getApplyCount(wBRoundMstSearchDTO);

            if (rtnCnt > 0) {
                //기존 신청이력 존재
                rtnCnt = 999;
            } else {
                //마스터 생성
                String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
                String regIp = CONetworkUtil.getMyIPaddress(request);

                int appctnSeq = cxAppctnMstSeqIdgen.getNextIntegerId();
                wbbaApplyMstDTO.setAppctnSeq(appctnSeq);
                wbbaApplyMstDTO.setAppctnBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wbbaApplyMstDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                wbbaApplyMstDTO.setRegId(regId);
                wbbaApplyMstDTO.setRegIp(regIp);

                rtnCnt = wbbbCompanyMapper.insertApply(wbbaApplyMstDTO);

                if (rtnCnt > 0) {
                    //상생신청진행 상세 생성
                    WBBAApplyDtlDTO wbbApplyDtlDTO = new WBBAApplyDtlDTO();
                    wbbApplyDtlDTO.setRsumeSeq(cxAppctnRsumeDtlSeqIdgen.getNextIntegerId());
                    wbbApplyDtlDTO.setAppctnSeq(wbbaApplyMstDTO.getAppctnSeq());
                    wbbApplyDtlDTO.setRsumeOrd(1);
                    wbbApplyDtlDTO.setRsumeSttsCd("신청");
                    wbbApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_2");
                    wbbApplyDtlDTO.setMngSttsCd("PRO_TYPE04_1_2");
                    wbbApplyDtlDTO.setRegId(regId);
                    wbbApplyDtlDTO.setRegIp(regIp);
                    wbbbCompanyMapper.insertApplyDtl(wbbApplyDtlDTO);

                    //신청파일 넣기
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
                            rtnList.get(i).setStatus("success");
                            rtnList.get(i).setFieldNm("fileSeq");
                            fileList.add(rtnList.get(i));

                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);

                            wbbApplyDtlDTO.setSbmsnSeq(fileApplyIdgen.getNextIntegerId());
                            wbbApplyDtlDTO.setOptnSeq(Integer.valueOf(wbbaApplyMstDTO.getOptnSeq().get(i)));
                            wbbApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));

                            wbbbCompanyMapper.insertFileInfo(wbbApplyDtlDTO);
                        }
                    }

                    //EDM,SMS발송
                    WBSendDTO wbSendDTO = new WBSendDTO();
                    wbSendDTO.setMemSeq(wbbaApplyMstDTO.getMemSeq());
                    wbSendDTO.setEpisdSeq(wbbaApplyMstDTO.getEpisdSeq());

                    send(wbSendDTO,"SMS04");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 부품사 신청자를 조회한다.
     */
    public WBBACompanySearchDTO getApplyDtl(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception {

        wbbCompanySearchDTO = wbbbCompanyMapper.getApplyDtl(wbbCompanySearchDTO);

        return wbbCompanySearchDTO;
    }

    /**
     * 부품사 신청자를 수정한다.
     */
    @Transactional
    public int updateInfo(WBBAApplyDtlDTO wbbaApplyDtlDTO, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {

        int rtnCnt = 0;

        try {
            //마스터 생성
            String modId = COUserDetailsHelperService.getAuthenticatedUser().getId();
            String modIp = CONetworkUtil.getMyIPaddress(request);

            //상생신청진행 상태 업데이트
            //관리자상태에 따라 분기처리해야함
            wbbaApplyDtlDTO.setRegId(modId);
            wbbaApplyDtlDTO.setRegIp(modIp);
            wbbaApplyDtlDTO.setModId(modId);
            wbbaApplyDtlDTO.setModIp(modIp);

            wbbbCompanyMapper.deleteFileInfo(wbbaApplyDtlDTO);

            //신청파일 넣기
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
                List<WBBAApplyDtlDTO> optinList = null;
                rtnList = cOFileUtil.parseFileInf(files, "", atchFileCnt, "", "file", 0);

                if (rtnList.size() > 0) {
                    WBBACompanySearchDTO wbbaCompanySearchDTO = new WBBACompanySearchDTO();
                    int stageSeq = 0;

                    wbbaCompanySearchDTO.setBsnCd(wbbaApplyDtlDTO.getBsnCd());
                    List<WBBAApplyDtlDTO> stepDtlList = wbbbCompanyMapper.selectStepDtlList(wbbaCompanySearchDTO);

                    for (WBBAApplyDtlDTO dtlDTO : stepDtlList) {
                        if (dtlDTO.getStageOrd() == wbbaApplyDtlDTO.getRsumeOrd()) {
                            stageSeq = dtlDTO.getStageSeq();
                        }
                    }
                    optinList = wbbbCompanyMapper.selectOptnList(stageSeq);
                }

                for (int i = 0; i < rtnList.size(); i++) {
                    List<COFileDTO> fileList = new ArrayList();

                    if ("99".equals(rtnList.get(i).getRespCd())) {
                        wbbaApplyDtlDTO.setFileSeq(wbbaApplyDtlDTO.getFileSeqList().get(i));
                    } else {
                        rtnList.get(i).setStatus("success");
                        rtnList.get(i).setFieldNm("fileSeq");
                        fileList.add(rtnList.get(i));
                        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                        wbbaApplyDtlDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                    }

                    wbbaApplyDtlDTO.setSbmsnSeq(fileApplyIdgen.getNextIntegerId());
                    wbbaApplyDtlDTO.setOptnSeq(optinList.get(i).getOptnSeq());

                    wbbbCompanyMapper.insertFileInfo(wbbaApplyDtlDTO);
                }
            }

            if ("PRO_TYPE04_2_1".equals(wbbaApplyDtlDTO.getAppctnSttsCd())) {
                //접수전 사용자->접수완료, 관리자 -> 미확인
                wbbaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_2");
                wbbaApplyDtlDTO.setMngSttsCd("PRO_TYPE04_1_2");

                wbbbCompanyMapper.updateApplyStatus(wbbaApplyDtlDTO);
            } else if ("PRO_TYPE04_2_3".equals(wbbaApplyDtlDTO.getAppctnSttsCd())) {
                //보완요청 사용자->보완완료, 관리자 -> 미확인
                wbbaApplyDtlDTO.setAppctnSttsCd("PRO_TYPE04_2_4");
                wbbaApplyDtlDTO.setMngSttsCd("PRO_TYPE04_1_2");
                wbbbCompanyMapper.updateApplyStatus(wbbaApplyDtlDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnCnt;
    }

    /**
     * 사업자번호 매핑 여부 확인
     */
    public int getBsnmNoCnt(WBBAApplyMstDTO wBBAApplyMstDTO) throws Exception {

        int respCnt = 0;

        respCnt = wbbbCompanyMapper.getBsnmNoCnt(wBBAApplyMstDTO);

        wBBAApplyMstDTO.setRespCnt(respCnt);

        return respCnt;
    }

    /**
     * Edit Page
     * 관리자 메모 수정
     */
    @Transactional
    public int updAdmMemo(WBBACompanySearchDTO wBBACompanySearchDTO) throws Exception {

        int respCnt = 0;

        respCnt = wbbbCompanyMapper.updAdmMemo(wBBACompanySearchDTO);

        return respCnt;
    }

    public void send(WBSendDTO sendDto, String typeCode) throws Exception {

        String beforeMngSttdCd = "";
        //이전 상태값과 현재 상태값을 비교하여 발송, 값이 같을 시 미발송
        if(!"SMS04".equals(typeCode)) {
            beforeMngSttdCd = wbbbCompanyMapper.getMngSttdCd(sendDto);
        }

        if (!beforeMngSttdCd.equals(sendDto.getMngSttdCd()) || "SMS04".equals(typeCode)) {
            //EDM & SMS setting
            COMailDTO cOMailDTO = new COMailDTO();
            COSmsDTO smsDto = new COSmsDTO();
            COMessageReceiverDTO receiverDto = new COMessageReceiverDTO();
            String templitFile = "";

            WBSendDTO wbSendDTO = new WBSendDTO();
            wbSendDTO = wbbbCompanyMapper.getReceiverInfo(sendDto);

            receiverDto.setEmail(wbSendDTO.getEmail());
            receiverDto.setName(wbSendDTO.getName());
            receiverDto.setMobile(wbSendDTO.getHpNo());

            wbSendDTO.setStageNm(sendDto.getStageNm());
            wbSendDTO.setReason(sendDto.getReason());

            if ("SMS04".equals(typeCode)) {
                //신청완료
                cOMailDTO.setSubject("[KAP] 상생사업 신청 완료 안내");
                smsDto.setTitle("[KAP] 상생사업 신청 완료 안내");

                receiverDto.setNote1(wbSendDTO.getCmpnNm());
                receiverDto.setNote2(wbSendDTO.getTitle());
                receiverDto.setNote3(CODateUtil.convertDate(wbSendDTO.getBsnStrtDtm(),"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "-"));
                receiverDto.setNote4(CODateUtil.convertDate(wbSendDTO.getBsnEndDtm(),"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "-"));
                receiverDto.setNote5(CODateUtil.convertDate(wbSendDTO.getRegDtm(),"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "-"));

                templitFile = "WinBusinessApply.html";
            } else if("SMS05".equals(typeCode)) {
                //승인안내
                cOMailDTO.setSubject("[KAP] 상생사업 승인 완료 안내");
                smsDto.setTitle("[KAP] 상생사업 승인 완료 안내");
                //사업명, 단계, 사업명, 단계
                receiverDto.setNote1(wbSendDTO.getTitle());
                receiverDto.setNote2(wbSendDTO.getStageNm());

                templitFile = "WinBusinessApproval.html";
            } else if ("SMS06".equals(typeCode)) {
                //반려안내
                //사업명, 단계, 사업명, 단계, 사유
                cOMailDTO.setSubject("[KAP] 상생사업 보완요청 안내");
                smsDto.setTitle("[KAP] 상생사업 보완요청 안내");

                receiverDto.setNote1(wbSendDTO.getTitle());
                receiverDto.setNote2(wbSendDTO.getStageNm());
                receiverDto.setNote3(wbSendDTO.getReason());

                templitFile = "WinBusinessRejection.html";
            } else if ("SMS07".equals(typeCode)) {
                //탈락안내
                //사업명, 단계 사업명, 단계, 사유
                cOMailDTO.setSubject("[KAP] 상생사업 탈락 안내");
                smsDto.setTitle("[KAP] 상생사업 탈락 안내");

                receiverDto.setNote1(wbSendDTO.getTitle());
                receiverDto.setNote2(wbSendDTO.getStageNm());
                receiverDto.setNote3(wbSendDTO.getReason());

                templitFile = "WinBusinessFail.html";
            }

            cOMailDTO.getReceiver().add(receiverDto);


            cOMessageService.sendMail(cOMailDTO, templitFile);
            //EMD 발송 끝

            //SMS발송 시작
            SMISmsCntnDTO smiSmsCntnDTO = new SMISmsCntnDTO();
            smiSmsCntnDTO.setSmsCntnCd(typeCode);
            smiSmsCntnDTO.setSiteGubun("front");
            smsDto.getReceiver().add(receiverDto);

            smsDto.setMessage(COStringUtil.replaceHTML(smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO).getCntn()));

            cOMessageService.sendSms(smsDto, "");
            //SMS발송 끝
        }
    }
}
