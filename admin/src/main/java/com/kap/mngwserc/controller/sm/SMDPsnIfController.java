package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMDPsnIfDTO;
import com.kap.service.SMDPsnIfService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 개인정보처리방침 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDPsnIfController.java
 * @Description		: 개인정보처리방침 관리를 위한 Controller
 * @author 구은희
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.26		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/smd/prsn")
public class SMDPsnIfController {

    private final SMDPsnIfService smdPsnIfService;

    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getPsnIfListPage(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("rtnData", smdPsnIfService.selectPsnIfList(smdPsnIfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smd/SMDPsnIfList.admin";
    }

    /**
     * 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPsnIfListPageAjax(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(!"".equals(smdPsnIfDTO.getStrtDt())){
                smdPsnIfDTO.setStrtDtm(smdPsnIfDTO.getStrtDt());
            }

            if(!"".equals(smdPsnIfDTO.getEndDt())){
                smdPsnIfDTO.setEndDtm(smdPsnIfDTO.getEndDt());
            }

            modelMap.addAttribute("rtnData", smdPsnIfService.selectPsnIfList(smdPsnIfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smd/SMDPsnIfListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPsnIfWritePage(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smdPsnIfService.selectPsnIfDtl(smdPsnIfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smd/SMDPsnIfWrite.admin";
    }

    /**
     * 게시물을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertPsnIf(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdPsnIfDTO.setRegId(coaAdmDTO.getId());
            smdPsnIfDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smdPsnIfService.insertPsnIf(smdPsnIfDTO);
            modelMap.addAttribute("respCnt", respCnt);
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
     * 게시물을 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updatePsnIf(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdPsnIfDTO.setModId(coaAdmDTO.getId());
            smdPsnIfDTO.setModIp(coaAdmDTO.getLoginIp());

            smdPsnIfDTO.setSeq(Integer.valueOf(smdPsnIfDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smdPsnIfService.updatePsnIf(smdPsnIfDTO));
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "jsonView";
    }/**
     * 게시물을 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deletePsnIf(SMDPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(smdPsnIfDTO.getDetailsKey())){
                smdPsnIfDTO.setSeq(Integer.valueOf(smdPsnIfDTO.getDetailsKey()));
            }
            int respCnt = smdPsnIfService.deletePsnIf(smdPsnIfDTO);
            modelMap.addAttribute("respCnt", respCnt);
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
