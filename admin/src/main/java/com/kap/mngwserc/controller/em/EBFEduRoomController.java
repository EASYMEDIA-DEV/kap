package com.kap.mngwserc.controller.em;

import com.kap.core.dto.SMCMnPopDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping(value="/mngwserc/eb/ebf")
public class EBFEduRoomController {


    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getMnPopListPage(SMCMnPopDTO SMCMnPopDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        return "mngwserc/em/ema/EMAEduCouseList.admin";
    }

    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectPopListPageAjax(SMCMnPopDTO SMCMnPopDTO, ModelMap modelMap) throws Exception
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

