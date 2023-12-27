package com.kap.front.controller.wb.wbb;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbl.WBLSurveyMstSearchDTO;
import com.kap.service.COGCntsService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBBARoundService;
import com.kap.service.WBBBCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <pre>
 * 공통사업 Controller
 * </pre>
 *
 * @author 박준희
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 공통사업 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김태훈				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/business/{bsnCd}")
public class WBBManagementController {
    /**
     * 서비스
     **/
    public final WBBARoundService wbbaRoundService;
    public final WBBBCompanyService wbbbCompanyService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbb/WBBManagementIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, wBRoundMstSearchDTO.getBsnCd()));
            modelMap.addAttribute("rtnData", wbbaRoundService.selectRoundList(wBRoundMstSearchDTO));
            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wbbaRoundService.getRoundDtl(wBRoundMstSearchDTO));
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
            modelMap.addAttribute("rtnData", wbbaRoundService.selectRoundList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbb/WBBManagementIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wbbaRoundService.getApplyChecked(wBRoundMstSearchDTO));

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
    public String getStep1Page(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbb/WBBManagementStep1.front";
        try {
            COUserDetailsDTO cOUserDetailsDTO = null;
            cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wbbCompanySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

            modelMap.addAttribute("episd", wbbCompanySearchDTO.getEpisdSeq());
            modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
            modelMap.addAttribute("rtnData", wbbbCompanyService.selectCompanyUserDtl(wbbCompanySearchDTO));
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
        String vwUrl = "front/wb/wbb/WBBManagementStep2.front";
        try {
            wbRoundMstSearchDTO.setStageOrd(1);
            modelMap.addAttribute("rtnData", wbbaRoundService.getRoundDtl(wbRoundMstSearchDTO));
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
    public String insert(WBBAApplyMstDTO wbbApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
           modelMap.addAttribute("actCnt", wbbbCompanyService.insertApply(wbbApplyMstDTO,request));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }
}
