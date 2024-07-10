package com.kap.front.controller.wb.wbc;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbc.WBCBSecuritySearchDTO;
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
 * 보안황경구축 Controller
 * </pre>
 *
 * @author 김대성
 * @version 1.0
 * @ClassName : WBCSecurityController.java
 * @Description : 보안환경구축 Controller
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김대성				   최초 생성
 * </pre>
 * @see
 * @since 2023.12.20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/coexistence/environment/security")
public class WBCSecurityController {
    /**
     * 서비스
     **/
    private final COCodeService cOCodeService;
    public final WBCASecurityListService wBCASecurityListService;
    public final WBCBSecurityService wBCBSecurityService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbc/WBCSecurityIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            wBRoundMstSearchDTO.setBsnCd("BSN03");

            //CMS
            pCOGCntsDTO.setMenuSeq(716);

            String menuSeq = "716";
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, menuSeq, "N"));
            modelMap.addAttribute("rtnData", wBCASecurityListService.selectCarbonList(wBRoundMstSearchDTO));

            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBCASecurityListService.getRoundDtl(wBRoundMstSearchDTO));

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
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/addRoundMore")
    public String addRoundMore(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wBCASecurityListService.selectCarbonList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbc/WBCSecurityIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wBCASecurityListService.getApplyChecked(wBRoundMstSearchDTO));

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
    public String getStep1Page(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbc/WBCSecurityStep1.front";
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBCBSecuritySearchDTO.getEpisdSeq()))) {
                vwUrl = "redirect:./content";
            }else{
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBCBSecuritySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wBCBSecuritySearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                cdDtlList.add("MEM_CD"); // 신청 진행상태
                modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

                modelMap.addAttribute("episd", wBCBSecuritySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBCBSecurityService.selectCompanyUserDtl(wBCBSecuritySearchDTO));

                RequestContextHolder.getRequestAttributes().setAttribute("step1Auth", wBCBSecuritySearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
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
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbc/WBCSecurityStep2.front";
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBCBSecuritySearchDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                modelMap.addAttribute("msg", "정상적인 접근이 아닙니다.");
            }else{
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBCBSecuritySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("episd", wBCBSecuritySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBCBSecurityService.selectCompanyUserDtl(wBCBSecuritySearchDTO));

                //사업접수 하단플로팅 영역용
                WBRoundMstSearchDTO wBRoundMstSearchDTO = new WBRoundMstSearchDTO();
                wBRoundMstSearchDTO.setBsnCd("BSN03");
                modelMap.addAttribute("rtnRoundDtl", wBCASecurityListService.getRoundDtl(wBRoundMstSearchDTO));

                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().setAttribute("step2Auth", wBCBSecuritySearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
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
    @RequestMapping(value = "/insert")
    public String insert(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, ModelMap modelMap, HttpServletRequest request, MultipartHttpServletRequest multiRequest) throws Exception {
        try {
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBCBSecurityMstInsertDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                return "redirect:/";
            }else{
                modelMap.addAttribute("actCnt", wBCBSecurityService.carbonUserInsert(wBCBSecurityMstInsertDTO,request, multiRequest));
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
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/complete")
    public String getCompletPage(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbc/WBCSecurityComplet.front";
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("complete", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
                return "redirect:./content";
            }else{
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBCBSecuritySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("episd", wBCBSecuritySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBCBSecurityService.selectCompanyUserDtl(wBCBSecuritySearchDTO));
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

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getInsertBsnmNoCnt")
    public String getInsertBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.getInsertBsnmNoCnt(wBCBSecurityMstInsertDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getInsertSbrdnBsnmNoCnt")
    public String getInsertSbrdnBsnmNoCnt(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.getInsertSbrdnBsnmNoCnt(wBCBSecurityMstInsertDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }
}
