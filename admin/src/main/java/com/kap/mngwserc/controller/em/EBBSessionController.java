package com.kap.mngwserc.controller.em;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMCPopDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMCPopService;
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
 * 교육차수관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBBSessionController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/eb/ebb")
public class EBBSessionController {

    /** 서비스 **/
    public final SMCPopService smPopService;

    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd, @PathVariable("gubun") String gubun) throws Exception
    {
        smcPopDTO.setDvcCd(gubun);
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", smPopService.selectMnPopList(smcPopDTO));
        modelMap.addAttribute("gubun", gubun);

        return "mngwserc/em/emb/EMBSessionList.admin";
    }

    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
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
        return "mngwserc/eb/ebb/EBBSessionListAjax";
    }























    /**
     * 팝업 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getPopWritePage(SMCPopDTO smcPopDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable("gubun") String gubun) throws Exception
    {
        try
        {
            smcPopDTO.setDvcCd(gubun);
            modelMap.addAttribute("gubun", smcPopDTO.getDvcCd());
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
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcPopDTO.setRegId(coaAdmDTO.getId());
            smcPopDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smPopService.insertMnPop(smcPopDTO);
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
            modelMap.addAttribute("respCnt", smPopService.updateMnPop(smcPopDTO));
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
            modelMap.addAttribute("rtnData", smPopService.selectMnPopList(smcPopDTO));
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smcPopDTO.setModId(coaAdmDTO.getId());
            smcPopDTO.setModIp(coaAdmDTO.getLoginIp());
            smcPopDTO.setDvcCd(gubun);

            smPopService.updateOrder(smcPopDTO);
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

