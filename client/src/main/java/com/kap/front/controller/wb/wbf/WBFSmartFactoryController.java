package com.kap.front.controller.wb.wbf;

import com.kap.core.dto.COFileDTO;
import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smj.SMJFormDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 상생 - 스마트공장 Controller
 * </pre>
 *
 * @author 김동현
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 상생 - 스마트공장 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김동현				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/smartFactory")
public class WBFSmartFactoryController {
    /**
     * 서비스
     **/
    /* 회차 관리 */
    public final WBFASmartRoundService wBFASmartRoundService;
    /* 부품사 관리*/
    public final WBFBRegisterCompanyService wBFBRegisterCompanyService;
    /* CMS */
    public final COGCntsService pCOGCntsService;
    /* 양식 파일 */
    public final SMJFormService smjFormService;
    /* 부품사 검색 */
    public final WBPartCompanyService wBPartCompanyService;
    /* 파일 공통 */
    public final COFileService cOFileService;
    /* 공통 코드 service DI */
    private final COCodeService cOCodeService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbf/WBFSmartFactoryIndex.front";
        try {
            wBRoundMstSearchDTO.setBsnCd("BSN06");

            /*wBRoundMstSearchDTO.setOrdFlag(1);*/
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);

            /*스마트공장구축 CMS 메뉴 코드*/
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, "719", "N"));
            // 회차
            modelMap.addAttribute("rtnData",wBFASmartRoundService.selRoundList(wBRoundMstSearchDTO));
            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBFASmartRoundService.getRecentRoundDtl(wBRoundMstSearchDTO));

            SMJFormDTO smjFormDTO = new SMJFormDTO();
            smjFormDTO.setTypeCd("BUSINESS02");
            modelMap.addAttribute("rtnRoundForm", smjFormService.selectFormDtl(smjFormDTO));

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            wBRoundMstSearchDTO.setBsnCd("BSN06");
            modelMap.addAttribute("rtnData", wBFASmartRoundService.selRoundList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb//wbf/WBFSmartFactoryIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            wBRoundMstSearchDTO.setBsnCd("BSN06");
            modelMap.addAttribute("resultCode", wBFASmartRoundService.getApplyChecked(wBRoundMstSearchDTO));

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step1")
    public String getStep1Page(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbf/WBFSmartFactoryStep1.front";
        try {
            wBFBRegisterSearchDTO.setBsnCd("BSN06");
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
            wBFBRegisterSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq().toString());

            modelMap.addAttribute("episdSeq", wBFBRegisterSearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getCompanyUserDtl(wBFBRegisterSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBRoundMstSearchDTO wbRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbf/WBFSmartFactoryStep2.front";
        try {
            /* 회차 정보 */
            wbRoundMstSearchDTO.setBsnCd("BSN06");
            wbRoundMstSearchDTO.setDetailsKey(wbRoundMstSearchDTO.getEpisdSeq().toString());
            modelMap.addAttribute("rtnData", wBFASmartRoundService.selRoundDetail(wbRoundMstSearchDTO));

            SMJFormDTO smjFormDTO = new SMJFormDTO();
            smjFormDTO.setTypeCd("BUSINESS02");
            modelMap.addAttribute("rtnRoundForm", smjFormService.selectFormDtl(smjFormDTO));

            /* 스마트 상태 코드 값 */
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("BGN_REG_INF");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 신청
     */
    @RequestMapping(value = "/insert")
    public String insert(WBFBRegisterDTO wBFBRegisterDTO, ModelMap modelMap, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        try {
            wBFBRegisterDTO.setBsnCd("BSN06");
            modelMap.addAttribute("actCnt", wBFBRegisterCompanyService.insertApply(wBFBRegisterDTO, multiRequest, request));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }


    /**
     * 사업신청 완료페이지
     */
    @RequestMapping(value = "/complete")
    public String getCompletePage(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbf/WBFSmartFactoryComplete.front";
        try {
            wBFBRegisterSearchDTO.setBsnCd("BSN06");
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
            wBFBRegisterSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq().toString());

            modelMap.addAttribute("episdSeq", wBFBRegisterSearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getApplyDtl(wBFBRegisterSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 파일 정보 조회한다.
     */
    @RequestMapping(value="/file/list")
    public String getFileList(COFileDTO coFileDTO, ModelMap modelMap) throws Exception
    {
        List<COFileDTO> rtnList = null;
        try
        {
            if(coFileDTO != null && coFileDTO.getFileSeq() != null)
            {
                modelMap.addAttribute("rtnData", cOFileService.getFileInfs(coFileDTO.getFileSeq()));
            }
        }
        catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

}
