package com.kap.mngwserc.controller.eb;

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
    public String getMnPopListPage(ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        return "mngwserc/em/ema/EMAEduCouseList.admin";
    }

    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(ModelMap modelMap, HttpServletRequest request) throws Exception
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

}

