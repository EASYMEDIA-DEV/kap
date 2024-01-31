package com.kap.front.controller.wb.wbg;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.wbg.WBGAApplyMstDTO;
import com.kap.core.dto.wb.wbg.WBGAExamSearchDTO;
import com.kap.core.dto.wb.wbh.WBHAApplyMstDTO;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import com.kap.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 시험계측장비 Controller
 * </pre>
 *
 * @author 김대성
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 시험계측장비 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.01.03		김대성				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/equipment")
public class WBGAEaxmController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBGAExamService wbgaExamService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getExamIndex(WBGAExamSearchDTO wbgaExamSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbg/WBGAExamIndex.front";
        try {
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, "721", "N"));
            modelMap.addAttribute("rtnRoundDtl", wbgaExamService.getRoundDtl(wbgaExamSearchDTO));
            RequestContextHolder.getRequestAttributes().removeAttribute("contentAuth", RequestAttributes.SCOPE_SESSION);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 신청가능 여부 코드를가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBGAExamSearchDTO wbgaExamSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wbgaExamService.getApplyChecked(wbgaExamSearchDTO));

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
    public String getStep1Page(WBGAExamSearchDTO wbgaExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbg/WBGAEaxmStep1.front";
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION) == null) {
                vwUrl = "redirect:./content";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbgaExamSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wbgaExamService.selectCompanyUserDtl(wbgaExamSearchDTO));

                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                cdDtlList.add("MEM_CD"); // 신청 진행상태
                modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

                RequestContextHolder.getRequestAttributes().setAttribute("step1Auth", "Y", RequestAttributes.SCOPE_SESSION);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 정보입력
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBGAExamSearchDTO wbgaExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbg/WBGAEaxmStep2.front";
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                modelMap.addAttribute("msg", "정상적인 접근이 아닙니다.");
            } else {
                modelMap.addAttribute("rtnData", wbgaExamService.getRoundDtl(wbgaExamSearchDTO));
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().setAttribute("step2Auth", wbgaExamSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
            }
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
    @PostMapping(value = "/insert")
    public String insert(WBGAApplyMstDTO wbgaApplyMstDTO, ModelMap modelMap, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                return "redirect:/";
            } else {
                modelMap.addAttribute("actCnt", wbgaExamService.insertApply(wbgaApplyMstDTO,multiRequest,request));
                RequestContextHolder.getRequestAttributes().setAttribute("complete", "Y", RequestAttributes.SCOPE_SESSION);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }

    /**
     * 사업신청 완료
     */
    @RequestMapping(value = "/complete")
    public String complete(WBGAExamSearchDTO wbgaExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbg/WBGAExamComplete.front";
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("complete", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
                return "redirect:./content";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbgaExamSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wbgaExamService.getApplyDtl(wbgaExamSearchDTO));
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }
}
