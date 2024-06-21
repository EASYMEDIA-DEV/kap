package com.kap.front.controller.wb.wbd;

import com.kap.core.dto.COGCntsDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetyMstInsertDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetySearchDTO;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 보안황경구축 Controller
 * </pre>
 *
 * @author 김대성
 * @version 1.0
 * @ClassName : WBDSafetyController.java
 * @Description : 안전설비구축 Controller
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
@RequestMapping(value = "/coexistence/facilities/safety")
public class WBDSafetyController {
    /**
     * 서비스
     **/
    private final COCodeService cOCodeService;
    public final WBDASafetyListService wBDASafetyListService;
    public final WBDBSafetyService wBDBSafetyService;
    public final COGCntsService pCOGCntsService;

    /**
     * 메인
     */
    @GetMapping(value = "/content")
    public String getMnagementIndex(WBRoundMstSearchDTO wBRoundMstSearchDTO, COGCntsDTO pCOGCntsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbd/WBDSafetyIndex.front";
        try {
            wBRoundMstSearchDTO.setOrdFlag(1);
            wBRoundMstSearchDTO.setExpsYn("Y");
            wBRoundMstSearchDTO.setFirstIndex(0);
            wBRoundMstSearchDTO.setRecordCountPerPage(3);
            wBRoundMstSearchDTO.setBsnCd("BSN04");

            //CMS
            pCOGCntsDTO.setMenuSeq(717);

            String menuSeq = "717";
            modelMap.addAttribute("rtnCms", pCOGCntsService.getCmsDtl(pCOGCntsDTO, menuSeq, "N"));
            modelMap.addAttribute("rtnData", wBDASafetyListService.selectCarbonList(wBRoundMstSearchDTO));

            //사업접수 하단플로팅 영역용
            modelMap.addAttribute("rtnRoundDtl", wBDASafetyListService.getRoundDtl(wBRoundMstSearchDTO));

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
            modelMap.addAttribute("rtnData", wBDASafetyListService.selectCarbonList(wBRoundMstSearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/wb/wbd/WBDSafetyIndexAjax";
    }

    /**
     * 회차정보를 가져온다.
     */
    @RequestMapping(value = "/applyChecked")
    public String applyChecked(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("resultCode", wBDASafetyListService.getApplyChecked(wBRoundMstSearchDTO));

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
    public String getStep1Page(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbd/WBDSafetyStep1.front";
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("contentAuth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBDBSafetySearchDTO.getEpisdSeq()))) {
                vwUrl = "redirect:./content";
            }else {

                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBDBSafetySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());
                wBDBSafetySearchDTO.setMemSeq(cOUserDetailsDTO.getSeq());

                // 공통코드 배열 셋팅
                ArrayList<String> cdDtlList = new ArrayList<String>();
                cdDtlList.add("MEM_CD"); // 신청 진행상태
                modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

                modelMap.addAttribute("episd", wBDBSafetySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBDBSafetyService.selectCompanyUserDtl(wBDBSafetySearchDTO));

                RequestContextHolder.getRequestAttributes().setAttribute("step1Auth", wBDBSafetySearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return vwUrl;
    }

    /**
     * 사업신청 기본정보
     */
    @RequestMapping(value = "/step2")
    public String getStep2Page(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbd/WBDSafetyStep2.front";
        try {
            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step1Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBDBSafetySearchDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                modelMap.addAttribute("msg", "정상적인 접근이 아닙니다.");
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBDBSafetySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("episd", wBDBSafetySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBDBSafetyService.selectCompanyUserDtl(wBDBSafetySearchDTO));

                //사업접수 하단플로팅 영역용
                WBRoundMstSearchDTO wBRoundMstSearchDTO = new WBRoundMstSearchDTO();
                wBRoundMstSearchDTO.setBsnCd("BSN04");
                modelMap.addAttribute("rtnRoundDtl", wBDASafetyListService.getRoundDtl(wBRoundMstSearchDTO));

                RequestContextHolder.getRequestAttributes().removeAttribute("step1Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().setAttribute("step2Auth", wBDBSafetySearchDTO.getEpisdSeq(), RequestAttributes.SCOPE_SESSION);
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
    public String insert(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {

            String contentAuth = String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION));

            if (RequestContextHolder.getRequestAttributes().getAttribute("step2Auth", RequestAttributes.SCOPE_SESSION) == null || !contentAuth.equals(String.valueOf(wBDBSafetyMstInsertDTO.getEpisdSeq()))) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                return "redirect:/";
            }else{
                modelMap.addAttribute("actCnt", wBDBSafetyService.carbonUserInsert(wBDBSafetyMstInsertDTO,request));
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
    public String getCompletPage(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        String vwUrl = "front/wb/wbd/WBDSafetyComplet.front";
        try {

            if (RequestContextHolder.getRequestAttributes().getAttribute("complete", RequestAttributes.SCOPE_SESSION) == null) {
                RequestContextHolder.getRequestAttributes().removeAttribute("step2Auth", RequestAttributes.SCOPE_SESSION);
                RequestContextHolder.getRequestAttributes().removeAttribute("complete", RequestAttributes.SCOPE_SESSION);
                return "redirect:./content";
            } else {
                COUserDetailsDTO cOUserDetailsDTO = null;
                cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
                wBDBSafetySearchDTO.setBsnmNo(cOUserDetailsDTO.getBsnmNo());

                modelMap.addAttribute("episd", wBDBSafetySearchDTO.getEpisdSeq());
                modelMap.addAttribute("rtnUser", cOUserDetailsDTO);
                modelMap.addAttribute("rtnData", wBDBSafetyService.selectCompanyUserDtl(wBDBSafetySearchDTO));

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
    public String getInsertBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getInsertBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
    public String getInsertSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getInsertSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
