package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMCMnPopDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMCMnPopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 사용자 팝업 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMCMnPopController.java
 * @Description		: 사용자 팝업 관리를 위한 Controller
 * @author 구은희
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.21		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smc/{gubun:pc|mbl}")
public class SMCMnPopController {

    /** 서비스 **/
    public final SMCMnPopService smcMnPopService;

    /**
     * 팝업 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(SMCMnPopDTO smcMnPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        smcMnPopDTO.setMdCd(gubun);
        modelMap.addAttribute("rtnData", smcMnPopService.selectMnPopList(smcMnPopDTO));
        modelMap.addAttribute("gubun", gubun);

        return "mngwserc/sm/smc/SMCMnPopList.admin";
    }

    /**
     * 팝업 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(SMCMnPopDTO smcMnPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcMnPopDTO.setMdCd(gubun);

            if(!"".equals(smcMnPopDTO.getStrtDt())){
                smcMnPopDTO.setExpsStrtDtm(smcMnPopDTO.getStrtDt());
            }

            if(!"".equals(smcMnPopDTO.getEndDt())){
                smcMnPopDTO.setExpsEndDtm(smcMnPopDTO.getEndDt());
            }

            modelMap.addAttribute("rtnData", smcMnPopService.selectMnPopList(smcMnPopDTO));
            modelMap.addAttribute("gubun", gubun);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smc/SMCMnPopListAjax";
    }

    /**
     * 팝업 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPopWritePage(SMCMnPopDTO smcMnPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcMnPopDTO.setMdCd(gubun);
            modelMap.addAttribute("gubun", smcMnPopDTO.getMdCd());
            modelMap.addAttribute("rtnInfo", smcMnPopService.selectMnPopDtl(smcMnPopDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smc/SMCMnPopWrite.admin";
    }

    /**
     * 팝업을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertMnPop(SMCMnPopDTO smcMnPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcMnPopDTO.setMdCd(gubun);
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcMnPopDTO.setRegId(coaAdmDTO.getId());
            smcMnPopDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smcMnPopService.insertMnPop(smcMnPopDTO);
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
     * 팝업을 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deleteMnPop(SMCMnPopDTO smcMnPopDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcMnPopDTO.setMdCd(gubun);

            if(!"".equals(smcMnPopDTO.getDetailsKey())){
                smcMnPopDTO.setPopupSeq(Integer.valueOf(smcMnPopDTO.getDetailsKey()));
            }
            int respCnt = smcMnPopService.deleteMnPop(smcMnPopDTO);
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
     * 팝업을 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updateMnPop(SMCMnPopDTO smcMnPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {

            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcMnPopDTO.setModId(coaAdmDTO.getId());
            smcMnPopDTO.setModIp(coaAdmDTO.getLoginIp());

            smcMnPopDTO.setMdCd(gubun);
            smcMnPopDTO.setPopupSeq(Integer.valueOf(smcMnPopDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smcMnPopService.updateMnPop(smcMnPopDTO));
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
     * 팝업 미노출 여부를 수정한다.
     *
     */
    @RequestMapping(value="/use-yn-update")
    public String updateUseYn(SMCMnPopDTO smcMnPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcMnPopDTO.setModId(coaAdmDTO.getId());
            smcMnPopDTO.setModIp(coaAdmDTO.getLoginIp());
            smcMnPopDTO.setMdCd(gubun);

            if(!"".equals(smcMnPopDTO.getDetailsKey())){
                smcMnPopDTO.setPopupSeq(Integer.valueOf(smcMnPopDTO.getDetailsKey()));
            }
            int respCnt = smcMnPopService.updateUseYn(smcMnPopDTO);
            modelMap.addAttribute("respCnt", respCnt);
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
     * 팝업 정렬을 수정한다.
     *
     */
    @RequestMapping(value="/sort", method=RequestMethod.POST)
    public ModelAndView updateOrder(SMCMnPopDTO smcMnPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        try
        {
            modelMap.addAttribute("rtnData", smcMnPopService.selectMnPopList(smcMnPopDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcMnPopDTO.setModId(coaAdmDTO.getId());
            smcMnPopDTO.setModIp(coaAdmDTO.getLoginIp());
            smcMnPopDTO.setMdCd(gubun);

            smcMnPopService.updateOrder(smcMnPopDTO);
            mav.setViewName("jsonView");

        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return mav;
    }
}

