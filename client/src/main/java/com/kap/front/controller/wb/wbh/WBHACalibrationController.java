package com.kap.front.controller.wb.wbh;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbh.WBHAApplyMstDTO;
import com.kap.core.dto.wb.wbh.WBHACalibrationSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COGCntsService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBHACalibrationService;
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

/**
 * <pre>
 * 검교정 Controller
 * </pre>
 *
 * @author 김태훈
 * @version 1.0
 * @ClassName : WBBManagementController.java
 * @Description : 검교정 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.01.03		김태훈				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/calibration")
public class WBHACalibrationController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBHACalibrationService wbhaCalibrationService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getCalibrationIndex(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbh/WBHACalibrationIndex.front";
        try {
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, "722", "N"));
            modelMap.addAttribute("rtnRoundDtl", wbhaCalibrationService.getRoundDtl(wbhaCalibrationSearchDTO));
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
    public String applyChecked(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wbhaCalibrationService.getApplyChecked(wbhaCalibrationSearchDTO));

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
    public String getStep1Page(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbh/WBHACalibrationStep1.front";
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wbhaCalibrationSearchDTO.getEpisdSeq()))) {
                vwUrl = "redirect:./content";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbhaCalibrationSearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCompanyUserDtl(wbhaCalibrationSearchDTO));

                RequestContextHolder.getRequestAttributes().setAttribute("step1Auth", wbhaCalibrationSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
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
    public String getStep2Page(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbh/WBHACalibrationStep2.front";
        try {
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wbhaCalibrationSearchDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                vwUrl = "redirect:./content";
            } else {
                modelMap.addAttribute("rtnData", wbhaCalibrationService.getRoundDtl(wbhaCalibrationSearchDTO));
                RequestContextHolder.getRequestAttributes().setAttribute("step12uth", wbhaCalibrationSearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
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
    public String insert(WBHAApplyMstDTO wbhaApplyMstDTO, ModelMap modelMap, MultipartHttpServletRequest multiRequest, HttpServletRequest request) throws Exception {
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wbhaApplyMstDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                return "redirect:./content";
            } else {
                modelMap.addAttribute("actCnt", wbhaCalibrationService.insertApply(wbhaApplyMstDTO,multiRequest,request));
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
    public String complete(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbh/WBHACalibrationComplete.front";
        try {
            if (RequestContextHolder.getRequestAttributes().getAttribute("complete", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
                return "redirect:./content";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wbhaCalibrationSearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wbhaCalibrationService.getApplyDtl(wbhaCalibrationSearchDTO));
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
