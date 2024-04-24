package com.kap.service.impl.wb.wba;

import com.kap.common.utility.CONetworkUtil;
import com.kap.common.utility.COPaginationUtil;
import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COMenuDTO;
import com.kap.core.dto.wb.wba.WBAManageInsertDTO;
import com.kap.core.dto.wb.wba.WBAManageSearchDTO;
import com.kap.core.dto.wb.wba.WBAManagementDtlDTO;
import com.kap.core.dto.wb.wba.WBAManagementOptnDTO;
import com.kap.service.COFileService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBAManagementService;
import com.kap.service.dao.COBUserMenuMapper;
import com.kap.service.dao.wb.wba.WBAManagementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 상생사업 관리를 위한 ServiceImpl
 * </pre>
 *
 * @ClassName		: WBAManagementServiceImpl.java
 * @Description		: 상생사업 관리를 위한 ServiceImpl
 * @author 김태훈
 * @since 2023.11.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.10		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WBAManagementServiceImpl implements WBAManagementService {

    //DAO
    private final WBAManagementMapper wbaManagementMapper;

    /* 파일 서비 스*/
    private final COFileService cOFileService;

    /* 메뉴 서비스 */
    private final COBUserMenuMapper cOBUserMenuMapper;

    /* 상생사업 상세 시퀀스 */
    private final EgovIdGnrService stepDtlSeqIdgen;

    /* 상생사업 옵션 시퀀스 */
    private final EgovIdGnrService stepOptnSeqIdgen;

    //파일 서비스

    /**
     * 상생사업 목록을 조회한다.
     */
    public WBAManageSearchDTO selectManagementList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {

        COPaginationUtil page = new COPaginationUtil();

        page.setCurrentPageNo(wbaManageSearchDTO.getPageIndex());
        page.setRecordCountPerPage(wbaManageSearchDTO.getListRowSize());

        page.setPageSize(wbaManageSearchDTO.getPageRowSize());

        wbaManageSearchDTO.setFirstIndex(page.getFirstRecordIndex());
        wbaManageSearchDTO.setRecordCountPerPage(page.getRecordCountPerPage());

        wbaManageSearchDTO.setList(wbaManagementMapper.selectManagementList(wbaManageSearchDTO));
        wbaManageSearchDTO.setTotalCount(wbaManagementMapper.selectManagementCnt(wbaManageSearchDTO));

        return wbaManageSearchDTO;
    }

    /**
     * 상생사업 개수를 조회한다.
     */
    @Override
    public int selectManagementCnt(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {
        return wbaManagementMapper.selectManagementCnt(wbaManageSearchDTO);
    }

    /**
     * 상생사업 신청자수를 조회한다.
     */
    @Override
    public int selecApplyCnt(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {
        return wbaManagementMapper.selecApplyCnt(wbaManageSearchDTO);
    }
    
    /**
     * 상세를 조회한다.
     */
    public WBAManageInsertDTO selectManagementDtl(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {

        WBAManageInsertDTO wBAManageInsertDTO = wbaManagementMapper.selectManagementMst(wbaManageSearchDTO);
        List<WBAManagementDtlDTO> wbaStepDtlList = wbaManagementMapper.selectStepDtlList(wbaManageSearchDTO);

        if(wbaStepDtlList != null && !wbaStepDtlList.isEmpty()) {
            for (WBAManagementDtlDTO wbaManagementDtlDTO : wbaStepDtlList) {
                wbaManagementDtlDTO.setManagementOptnList(new ArrayList<WBAManagementOptnDTO>());

                List<WBAManagementOptnDTO> wbaOptnDtlList = wbaManagementMapper.selectOptnDtlList(wbaManagementDtlDTO);

                if(wbaOptnDtlList != null && !wbaOptnDtlList.isEmpty()) {
                    for (WBAManagementOptnDTO wbaManagementOptnDTO : wbaOptnDtlList) {
                        if(wbaManagementDtlDTO.getStageSeq() != null && wbaManagementOptnDTO.getStageSeq() != null) {
                            if (wbaManagementDtlDTO.getStageSeq().equals(wbaManagementOptnDTO.getStageSeq())) {
                                wbaManagementDtlDTO.getManagementOptnList().add(wbaManagementOptnDTO);
                            }
                        }
                    }
                }
            }
            wBAManageInsertDTO.setManagementDtlList(wbaStepDtlList);
        }

        return wBAManageInsertDTO;
    }


    /**
     * 상생사업 상세 등록
     */
    public WBAManageInsertDTO insertManagement(WBAManageInsertDTO wbaManageInsertDTO, HttpServletRequest request) throws Exception {
        String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
        String regIp = CONetworkUtil.getMyIPaddress(request);

        wbaManageInsertDTO.setRegIp(regIp);
        wbaManageInsertDTO.setRegId(regId);
        wbaManageInsertDTO.setModIp(regIp);
        wbaManageInsertDTO.setModId(regId);

        //상생 마스터 등
        wbaManagementMapper.insertManagementMst(wbaManageInsertDTO);

        //관리자,사용자 메뉴 URL 생성
        COMenuDTO coMenuDTO = new COMenuDTO();
        //사용자 메뉴
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getUserMenuSeq());
        coMenuDTO.setUserUrl("/coexistence/business/"+wbaManageInsertDTO.getBsnCd()+"/content");
        coMenuDTO.setAdmUrl("/mngwserc/contentsid/"+coMenuDTO.getMenuSeq()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //관리자 메뉴(회차관리)
        coMenuDTO.setUserUrl(null);
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmEpisdMenuSeq());
        coMenuDTO.setAdmUrl("/mngwserc/wbb/wbba/"+wbaManageInsertDTO.getBsnCd()+"/list");
        coMenuDTO.setUserUrl("/wbb/wbba/"+wbaManageInsertDTO.getBsnCd()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //관리자 메뉴(부품사 관리)
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmAppctnMenuSeq());
        coMenuDTO.setAdmUrl("/mngwserc/wbb/wbbb/"+wbaManageInsertDTO.getBsnCd()+"/list");
        coMenuDTO.setUserUrl("/wbb/wbbb/"+wbaManageInsertDTO.getBsnCd()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //상생단계 상세 등록
        if (wbaManageInsertDTO.getManagementDtlList() != null && wbaManageInsertDTO.getManagementDtlList().size() > 0) {
            int firstStepDtlIdgen = 0;

            for (int i = 0; i < wbaManageInsertDTO.getManagementDtlList().size(); i++) {
                WBAManagementDtlDTO wbaManagementDtlDTO = wbaManageInsertDTO.getManagementDtlList().get(i);

                int firstStepDtlSeqIdgen = stepDtlSeqIdgen.getNextIntegerId();

                wbaManagementDtlDTO.setStageSeq(firstStepDtlSeqIdgen);
                wbaManagementDtlDTO.setRegId(regId);
                wbaManagementDtlDTO.setRegIp(regIp);
                wbaManagementDtlDTO.setModId(regId);
                wbaManagementDtlDTO.setModIp(regIp);

                wbaManagementMapper.insertManagementDtl(wbaManagementDtlDTO);

                if ("Y".equals(wbaManagementDtlDTO.getFileYn())) {
                    for (int j = 0; j < wbaManagementDtlDTO.getManagementOptnList().size(); j++) {
                        WBAManagementOptnDTO wbaManagementOptnDTO = wbaManagementDtlDTO.getManagementOptnList().get(j);
                        int firstStepOptnSeqIdgen = stepOptnSeqIdgen.getNextIntegerId();

                        wbaManagementOptnDTO.setOptnSeq(firstStepOptnSeqIdgen);
                        wbaManagementOptnDTO.setStageSeq(wbaManagementDtlDTO.getStageSeq());
                        wbaManagementOptnDTO.setRegId(regId);
                        wbaManagementOptnDTO.setRegIp(regIp);
                        wbaManagementOptnDTO.setModId(regId);
                        wbaManagementOptnDTO.setModIp(regIp);

                        for(int k = 0; k < wbaManagementOptnDTO.getOptnFileList().size(); k++) {

                            List<COFileDTO> fileList = new ArrayList();

                            COFileDTO fileDto = new COFileDTO();
                            fileDto.setStatus(wbaManagementOptnDTO.getOptnFileList().get(k).getStatus());
                            fileDto.setWidth(wbaManagementOptnDTO.getOptnFileList().get(k).getWidth());
                            fileDto.setHeight(wbaManagementOptnDTO.getOptnFileList().get(k).getHeight());
                            fileDto.setWebPath(wbaManagementOptnDTO.getOptnFileList().get(k).getWebPath());
                            fileDto.setFieldNm(wbaManagementOptnDTO.getOptnFileList().get(k).getFieldNm());
                            fileDto.setOrgnFileNm(wbaManagementOptnDTO.getOptnFileList().get(k).getOrgnFileNm());
                            fileDto.setFileDsc(wbaManagementOptnDTO.getOptnFileList().get(k).getFileDsc());
                            fileList.add(fileDto);

                            HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                            wbaManagementOptnDTO.setFileSeq(fileSeqMap.get("fileSeq"));

                            wbaManagementMapper.insertManagementOptn(wbaManagementOptnDTO);
                        }
                    }
                }
            }
        }

        return wbaManageInsertDTO;
    }

    /**
     * 상생사업 상세 수정
     */
    public WBAManageInsertDTO updateManagement(WBAManageInsertDTO wbaManageInsertDTO, HttpServletRequest request) throws Exception {
        String regId = COUserDetailsHelperService.getAuthenticatedUser().getId();
        String regIp = CONetworkUtil.getMyIPaddress(request);

        wbaManageInsertDTO.setRegIp(regIp);
        wbaManageInsertDTO.setRegId(regId);
        wbaManageInsertDTO.setModIp(regIp);
        wbaManageInsertDTO.setModId(regId);

        //상생 마스터 수정
        wbaManagementMapper.updateManagementMst(wbaManageInsertDTO);

        //관리자,사용자 메뉴 URL 생성
        COMenuDTO coMenuDTO = new COMenuDTO();
        //사용자 메뉴
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getUserMenuSeq());
        coMenuDTO.setUserUrl("/coexistence/business/"+wbaManageInsertDTO.getBsnCd()+"/content");
        coMenuDTO.setAdmUrl("/mngwserc/contentsid/"+coMenuDTO.getMenuSeq()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //관리자 메뉴(회차관리)
        coMenuDTO.setUserUrl(null);
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmEpisdMenuSeq());
        coMenuDTO.setAdmUrl("/mngwserc/wbb/wbba/"+wbaManageInsertDTO.getBsnCd()+"/list");
        coMenuDTO.setUserUrl("/wbb/wbba/"+wbaManageInsertDTO.getBsnCd()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //관리자 메뉴(부품사 관리)
        coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmAppctnMenuSeq());
        coMenuDTO.setAdmUrl("/mngwserc/wbb/wbbb/"+wbaManageInsertDTO.getBsnCd()+"/list");
        coMenuDTO.setUserUrl("/wbb/wbbb/"+wbaManageInsertDTO.getBsnCd()+"/list");
        cOBUserMenuMapper.updateMenuUrl(coMenuDTO);

        //옵셥삭제를 위한 단계 Key 가져오기
        WBAManageSearchDTO wbaSearchDTO = new WBAManageSearchDTO();
        wbaSearchDTO.setDetailsKey(wbaManageInsertDTO.getDetailsKey());
        List<WBAManagementDtlDTO> wbaStepDtlList = wbaManagementMapper.selectStepDtlList(wbaSearchDTO);

        WBAManageInsertDTO wbaStepDto = new WBAManageInsertDTO();
        wbaStepDto.setManagementDtlList(wbaStepDtlList);

        //상생 단계 및 옵션 삭제
        wbaManagementMapper.deleteManagementDtl(wbaManageInsertDTO);
        wbaManagementMapper.deleteManagementOptn(wbaStepDto);

        //상생단계 상세 등록
        if (wbaManageInsertDTO.getManagementDtlList() != null && wbaManageInsertDTO.getManagementDtlList().size() > 0) {
            int firstStepDtlIdgen = 0;

            for (int i = 0; i < wbaManageInsertDTO.getManagementDtlList().size(); i++) {
                WBAManagementDtlDTO wbaManagementDtlDTO = wbaManageInsertDTO.getManagementDtlList().get(i);

                int firstStepDtlSeqIdgen = stepDtlSeqIdgen.getNextIntegerId();

                wbaManagementDtlDTO.setStageSeq(firstStepDtlSeqIdgen);
                wbaManagementDtlDTO.setRegId(regId);
                wbaManagementDtlDTO.setRegIp(regIp);
                wbaManagementDtlDTO.setModId(regId);
                wbaManagementDtlDTO.setModIp(regIp);

                wbaManagementMapper.insertManagementDtl(wbaManagementDtlDTO);


                if ("Y".equals(wbaManagementDtlDTO.getFileYn())) {
                    for (int j = 0; j < wbaManagementDtlDTO.getManagementOptnList().size(); j++) {
                        WBAManagementOptnDTO wbaManagementOptnDTO = wbaManagementDtlDTO.getManagementOptnList().get(j);
                        int firstStepOptnSeqIdgen = stepOptnSeqIdgen.getNextIntegerId();

                        wbaManagementOptnDTO.setOptnSeq(firstStepOptnSeqIdgen);
                        wbaManagementOptnDTO.setStageSeq(wbaManagementDtlDTO.getStageSeq());
                        wbaManagementOptnDTO.setRegId(regId);
                        wbaManagementOptnDTO.setRegIp(regIp);
                        wbaManagementOptnDTO.setModId(regId);
                        wbaManagementOptnDTO.setModIp(regIp);

                        for(int k = 0; k < wbaManagementOptnDTO.getOptnFileList().size(); k++) {

                            List<COFileDTO> fileList = new ArrayList();

                            COFileDTO fileDto = new COFileDTO();
                            fileDto.setStatus(wbaManagementOptnDTO.getOptnFileList().get(k).getStatus());
                            fileDto.setWidth(wbaManagementOptnDTO.getOptnFileList().get(k).getWidth());
                            fileDto.setHeight(wbaManagementOptnDTO.getOptnFileList().get(k).getHeight());
                            fileDto.setWebPath(wbaManagementOptnDTO.getOptnFileList().get(k).getWebPath());
                            fileDto.setFieldNm(wbaManagementOptnDTO.getOptnFileList().get(k).getFieldNm());
                            fileDto.setOrgnFileNm(wbaManagementOptnDTO.getOptnFileList().get(k).getOrgnFileNm());
                            fileDto.setFileDsc(wbaManagementOptnDTO.getOptnFileList().get(k).getFileDsc());
                            fileDto.setFileOrd(wbaManagementOptnDTO.getOptnFileList().get(k).getFileOrd());
                            fileList.add(fileDto);

                            if ("addedfile".equals(fileDto.getStatus())) {
                                wbaManagementOptnDTO.setFileSeq(wbaManagementOptnDTO.getOptnFileList().get(k).getFileSeq());
                            } else {
                                HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(fileList);
                                wbaManagementOptnDTO.setFileSeq(fileSeqMap.get("fileSeq"));
                            }

                            wbaManagementMapper.insertManagementOptn(wbaManagementOptnDTO);
                        }
                    }
                }
            }
        }

        return wbaManageInsertDTO;
    }

    /**
     * 상생사업 삭제
     */
    public int deleteManagement(WBAManageInsertDTO wbaManageInsertDTO) throws Exception
    {
        //해당 사업에 참여자가 있는 확인
        int respCnt = wbaManagementMapper.getApplyUserCount(wbaManageInsertDTO);
        
        if (respCnt > 0) {
            //참여자 존재
            respCnt = -1;
        } else {
            //마스터 삭제
            respCnt = wbaManagementMapper.deleteManagementMst(wbaManageInsertDTO);

            //옵셥삭제를 위한 단계 Key 가져오기
            WBAManageSearchDTO wbaSearchDTO = new WBAManageSearchDTO();
            wbaSearchDTO.setDetailsKey(wbaManageInsertDTO.getDetailsKey());
            List<WBAManagementDtlDTO> wbaStepDtlList = wbaManagementMapper.selectStepDtlList(wbaSearchDTO);

            WBAManageInsertDTO wbaStepDto = new WBAManageInsertDTO();
            wbaStepDto.setManagementDtlList(wbaStepDtlList);

            //상생 단계 및 옵션 삭제
            wbaManagementMapper.deleteManagementDtl(wbaManageInsertDTO);
            wbaManagementMapper.deleteManagementOptn(wbaStepDto);

            //회차 삭제
            wbaManagementMapper.deleteRound(wbaManageInsertDTO);

            //메뉴 미노출
            COMenuDTO coMenuDTO = new COMenuDTO();
            //관리자 메뉴 미노출
            coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmAppctnMenuSeq());
            cOBUserMenuMapper.updateMenuUseYn(coMenuDTO);

            coMenuDTO.setMenuSeq(wbaManageInsertDTO.getAdmEpisdMenuSeq());
            cOBUserMenuMapper.updateMenuUseYn(coMenuDTO);

            coMenuDTO.setMenuSeq(wbaManageInsertDTO.getUserMenuSeq());
            cOBUserMenuMapper.updateMenuUseYn(coMenuDTO);

            respCnt = 1;
        }

        return respCnt;
    }

    /**
     * 첨부파일 업데이트
     */
    public int updateFileInfo(WBAManageInsertDTO wbaManageInsertDTO) throws Exception{

        HashMap<String, Integer> fileSeqMap = cOFileService.setFileInfo(wbaManageInsertDTO.getFileList());

        wbaManageInsertDTO.setFileSeq(fileSeqMap.get("fileSeq"));
        int respCnt = wbaManagementMapper.updateFileInfo(wbaManageInsertDTO);

        return respCnt;
    }


    public List<WBAManagementOptnDTO> selectOptnList(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {
        WBAManagementDtlDTO wbaManagementDtlDTO =  wbaManagementMapper.getStepDtl(wbaManageSearchDTO);

        return wbaManagementMapper.selectOptnDtlList(wbaManagementDtlDTO);
    }

    /**
     * 상생공통사업 마스터를 조회한다.
     */
    public WBAManageInsertDTO selectManagementMst(WBAManageSearchDTO wbaManageSearchDTO) throws Exception {
        return wbaManagementMapper.selectManagementMst(wbaManageSearchDTO);
    }
}
