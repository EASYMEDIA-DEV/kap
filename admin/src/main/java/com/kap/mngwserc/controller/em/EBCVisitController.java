package com.kap.mngwserc.controller.em;

import com.kap.core.dto.COAAdmDTO;
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
 * 교육과정관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.09.21
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
@RequestMapping(value="/mngwserc/eb/ebc")
public class EBCVisitController {

    /** 서비스 **/
    public final SMCMnPopService smPopService;

    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception
    {
        return "mngwserc/em/ema/EMAEduCouseList.admin";
    }

    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {

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
    public String getPopWritePage(ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {
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
    public String insertMnPop(ModelMap modelMap, HttpServletRequest request, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {
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
    public String deleteMnPop(ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {

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
    public String updateMnPop( COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {


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
    public String updateUseYn(COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception
    {
        try
        {

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
    public ModelAndView updateOrder(COAAdmDTO pCOAAdmDTO, ModelMap modelMap, @PathVariable("mdCd") String mdCd) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        try
        {


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

