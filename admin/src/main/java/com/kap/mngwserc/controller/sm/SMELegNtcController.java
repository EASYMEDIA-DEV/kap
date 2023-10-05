package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMELegNtcDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMELegNtcService;
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
 * 법적고지 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMELegNtcController.java
 * @Description		: 법적고지 관리를 위한 Controller
 * @author 구은희
 * @since 2023.10.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.10.04		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/sm/sme/legNtc")
public class SMELegNtcController {

    private final SMELegNtcService smeLegNtcService;

    /**
     * 목록 페이지
     */
    @GetMapping(value="/list")
    public String getLegNtcListPage(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            modelMap.addAttribute("langCd", langCd);
            modelMap.addAttribute("rtnData", smeLegNtcService.selectLegNtcList(smeLegNtcDTO));
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
    public String selectLegNtcListPageAjax(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(!"".equals(smeLegNtcDTO.getStrtDt())){
                smeLegNtcDTO.setStrtDtm(smeLegNtcDTO.getStrtDt());
            }

            if(!"".equals(smeLegNtcDTO.getEndDt())){
                smeLegNtcDTO.setEndDtm(smeLegNtcDTO.getEndDt());
            }

            modelMap.addAttribute("rtnData", smeLegNtcService.selectLegNtcList(smeLegNtcDTO));
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
    public String getLegNtcfWritePage(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smeLegNtcService.selectLegNtcDtl(smeLegNtcDTO));
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
    public String insertLegNtc(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smeLegNtcDTO.setRegId(coaAdmDTO.getId());
            smeLegNtcDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smeLegNtcService.insertLegNtc(smeLegNtcDTO);
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
    public String updateLegNtc(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smeLegNtcDTO.setModId(coaAdmDTO.getId());
            smeLegNtcDTO.setModIp(coaAdmDTO.getLoginIp());

            smeLegNtcDTO.setSeq(Integer.valueOf(smeLegNtcDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smeLegNtcService.updateLegNtc(smeLegNtcDTO));
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
    public String deleteLegNtc(SMELegNtcDTO smeLegNtcDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(smeLegNtcDTO.getDetailsKey())){
                smeLegNtcDTO.setSeq(Integer.valueOf(smeLegNtcDTO.getDetailsKey()));
            }
            int respCnt = smeLegNtcService.deleteLegNtc(smeLegNtcDTO);
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
