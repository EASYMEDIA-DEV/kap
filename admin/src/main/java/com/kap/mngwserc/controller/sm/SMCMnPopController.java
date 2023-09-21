package com.kap.mngwserc.controller.sm;

import com.kap.core.annotation.MapData;
import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COSmpleSrchDTO;
import com.kap.core.dto.EmfMap;
import com.kap.core.dto.SMCPopDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMPopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 사용자 팝업 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: COHMnPopController.java
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
@RequestMapping(value="/mngwserc/{langCd}/sm/smc/{gubun:pc|mobile}")
public class SMCMnPopController {

    /** 서비스 **/
    public final SMPopService smPopService;

    /**
     * 팝업 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd, @PathVariable("gubun") String gubun) throws Exception
    {
        smcPopDTO.setDvcCd(gubun);
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", smPopService.selectMnPopList(smcPopDTO));
        modelMap.addAttribute("gubun", gubun);

        return "mngwserc/sm/smc/SMCMnPopList.admin";
    }

    /**
     * 팝업 목록을 조회한다.
     */
    @PostMapping(value = "/select")
    public String selectPopListPageAjax(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcPopDTO.setDvcCd(gubun);

            if(!"".equals(smcPopDTO.getStrtDt())){
                smcPopDTO.setStrtDtm(smcPopDTO.getStrtDt());
            }

            if(!"".equals(smcPopDTO.getEndDt())){
                smcPopDTO.setEndDtm(smcPopDTO.getEndDt());
            }

            modelMap.addAttribute("rtnData", smPopService.selectMnPopList(smcPopDTO));
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
     * 팝업 등록 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPopWritePage(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcPopDTO.setDvcCd(gubun);
            modelMap.addAttribute("rtnInfo", smPopService.selectMnPopDtl(smcPopDTO));
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
    public String insertMnPop(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcPopDTO.setDvcCd(gubun);
            int actCnt = smPopService.insertMnPop(smcPopDTO);
            modelMap.addAttribute("actCnt", actCnt);
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
    public String deleteMnPop(SMCPopDTO smcPopDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcPopDTO.setDvcCd(gubun);

            if(!"".equals(smcPopDTO.getDetailsKey())){
                smcPopDTO.setSeq(Integer.valueOf(smcPopDTO.getDetailsKey()));
            }
            int respCnt = smPopService.deleteMnPop(smcPopDTO);
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
    public String updateMnPop(SMCPopDTO smcPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {

            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcPopDTO.setModId(coaAdmDTO.getId());
            smcPopDTO.setModIp(coaAdmDTO.getLoginIp());

            smcPopDTO.setDvcCd(gubun);
            smcPopDTO.setSeq(Integer.valueOf(smcPopDTO.getDetailsKey()));
            modelMap.addAttribute("actCnt", smPopService.updateMnPop(smcPopDTO));
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
    public String updateUseYn(SMCPopDTO smcPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcPopDTO.setModId(coaAdmDTO.getId());
            smcPopDTO.setModIp(coaAdmDTO.getLoginIp());
            smcPopDTO.setDvcCd(gubun);

            if(!"".equals(smcPopDTO.getDetailsKey())){
                smcPopDTO.setSeq(Integer.valueOf(smcPopDTO.getDetailsKey()));
            }
            int respCnt = smPopService.updateUseYn(smcPopDTO);
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
    public ModelAndView updateOrder(SMCPopDTO smcPopDTO, COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("gubun") String gubun) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        try
        {
            System.err.println("/sort 컨트롤러로 넘어옴:::::::::::::::::");
            modelMap.addAttribute("rtnData", smPopService.selectMnPopList(smcPopDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcPopDTO.setModId(coaAdmDTO.getId());
            smcPopDTO.setModIp(coaAdmDTO.getLoginIp());
            smcPopDTO.setDvcCd(gubun);
            smPopService.updateOrder(smcPopDTO);
            mav.setViewName("jsonView");

        }
        catch (Exception e)
        {System.err.println("e 메시지::" + e);
            if (log.isErrorEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return mav;
    }
}
