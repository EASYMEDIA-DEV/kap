package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smd.SMDAPsnIfDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMDAPsnIfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 개인정보처리방침 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDAPsnIfController.java
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
@RequestMapping(value="/mngwserc/sm/smd/smda")
public class SMDAPsnIfController {

    private final SMDAPsnIfService smdPsnIfService;

    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getPsnIfListPage(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
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

        return "mngwserc/sm/smd/SMDAPsnIfList.admin";
    }

    /**
     * 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPsnIfListPageAjax(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(!"".equals(smdPsnIfDTO.getStrtDt())){
                smdPsnIfDTO.setDStrDt(smdPsnIfDTO.getStrtDt());
            }

            if(!"".equals(smdPsnIfDTO.getEndDt())){
                smdPsnIfDTO.setDEndDt(smdPsnIfDTO.getEndDt());
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
        return "mngwserc/sm/smd/SMDAPsnIfListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPsnIfWritePage(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
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

        return "mngwserc/sm/smd/SMDAPsnIfWrite.admin";
    }

    /**
     * 게시물을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertPsnIf(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smdPsnIfDTO.setRegId(cOUserDetailsDTO.getId());
            smdPsnIfDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

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
    public String updatePsnIf(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smdPsnIfDTO.setModId(cOUserDetailsDTO.getId());
            smdPsnIfDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            smdPsnIfDTO.setPsnifSeq(Integer.valueOf(smdPsnIfDTO.getDetailsKey()));
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
    }
    /**
     * 게시물을 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deletePsnIf(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(smdPsnIfDTO.getDetailsKey())){
                smdPsnIfDTO.setPsnifSeq(Integer.valueOf(smdPsnIfDTO.getDetailsKey()));
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
