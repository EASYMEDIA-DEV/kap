package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMFWebAccDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMFWebAccService;
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
 * 웹접근성 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMFWebAccController.java
 * @Description		: 웹접근성 관리를 위한 Controller
 * @author 구은희
 * @since 2023.10.05
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.05		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/smf/webAcc")
public class SMFWebAccController {

    private final SMFWebAccService smfWebAccService;

    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getWebAccListPage(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("rtnData", smfWebAccService.selectWebAccList(smfWebAccDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smf/SMFWebAccList.admin";
    }

    /**
     * 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectWebAccListPageAjax(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(!"".equals(smfWebAccDTO.getStrtDt())){
                smfWebAccDTO.setStrtDtm(smfWebAccDTO.getStrtDt());
            }

            if(!"".equals(smfWebAccDTO.getEndDt())){
                smfWebAccDTO.setEndDtm(smfWebAccDTO.getEndDt());
            }

            modelMap.addAttribute("rtnData", smfWebAccService.selectWebAccList(smfWebAccDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smf/SMFWebAccListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getWebAccfWritePage(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smfWebAccService.selectWebAccDtl(smfWebAccDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smf/SMFWebAccWrite.admin";
    }

    /**
     * 게시물을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertWebAcc(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smfWebAccDTO.setRegId(coaAdmDTO.getId());
            smfWebAccDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smfWebAccService.insertWebAcc(smfWebAccDTO);
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
    public String updateWebAcc(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smfWebAccDTO.setModId(coaAdmDTO.getId());
            smfWebAccDTO.setModIp(coaAdmDTO.getLoginIp());

            smfWebAccDTO.setSeq(Integer.valueOf(smfWebAccDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smfWebAccService.updateWebAcc(smfWebAccDTO));
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
    public String deleteWebAcc(SMFWebAccDTO smfWebAccDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(smfWebAccDTO.getDetailsKey())){
                smfWebAccDTO.setSeq(Integer.valueOf(smfWebAccDTO.getDetailsKey()));
            }
            int respCnt = smfWebAccService.deleteWebAcc(smfWebAccDTO);
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
