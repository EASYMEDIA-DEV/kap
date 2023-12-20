package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.sm.smk.SMKTrendDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMKTrendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * TREND 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMKTrendController.java
 * @Description		: TREND 관리를 위한 Controller
 * @author 구은희
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smk")
public class SMKTrendController {

    /** 서비스 **/
    public final SMKTrendService smkTrendService;

    /**
     * TREND 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", smkTrendService.selectTrendList(smkTrendDTO));

        return "mngwserc/sm/smk/SMKTrendList.admin";
    }

    /**
     * TREND 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", smkTrendService.selectTrendList(smkTrendDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smk/SMKTrendListAjax";
    }

    /**
     * TREND 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPopWritePage(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smkTrendService.selectTrendDtl(smkTrendDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smk/SMKTrendWrite.admin";
    }

    /**
     * TREND를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertMnPop(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smkTrendDTO.setRegId(cOUserDetailsDTO.getId());
            smkTrendDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            int respCnt = smkTrendService.insertTrend(smkTrendDTO);
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
     * TREND를 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String deleteMnPop(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(smkTrendDTO.getDetailsKey())){
                smkTrendDTO.setTrndSeq(Integer.valueOf(smkTrendDTO.getDetailsKey()));
            }
            int respCnt = smkTrendService.deleteTrend(smkTrendDTO);
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
     * TREND를 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updateMnPop(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smkTrendDTO.setModId(cOUserDetailsDTO.getId());
            smkTrendDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            smkTrendDTO.setTrndSeq(Integer.valueOf(smkTrendDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smkTrendService.updateTrend(smkTrendDTO));
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
     * TREND 미노출 여부를 수정한다.
     *
     */
    @RequestMapping(value="/use-yn-update")
    public String updateUseYn(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smkTrendDTO.setModId(cOUserDetailsDTO.getId());
            smkTrendDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            if(!"".equals(smkTrendDTO.getDetailsKey())){
                smkTrendDTO.setTrndSeq(Integer.valueOf(smkTrendDTO.getDetailsKey()));
            }
            int respCnt = smkTrendService.updateUseYn(smkTrendDTO);
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
     * TREND 정렬을 수정한다.
     *
     */
    @RequestMapping(value="/sort", method=RequestMethod.POST)
    public ModelAndView updateOrder(SMKTrendDTO smkTrendDTO, ModelMap modelMap) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        try
        {
            modelMap.addAttribute("rtnData", smkTrendService.selectTrendList(smkTrendDTO));
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smkTrendDTO.setModId(cOUserDetailsDTO.getId());
            smkTrendDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            smkTrendService.updateOrder(smkTrendDTO);
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

