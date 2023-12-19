package com.kap.front.controller.sm;

import com.kap.core.dto.sm.smd.SMDBTmncsDTO;
import com.kap.service.SMDBTmncsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 이용약관을 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDBTmncsController.java
 * @Description		: 이용약관을 위한 Controller
 * @author 구은희
 * @since 2023.11.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.06		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/utility/terms-of-use")
public class SMDBTmncsController {

    private final SMDBTmncsService smdbTmncsService;

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/index")
    public String getTmncsPage(SMDBTmncsDTO smdbTmncsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", smdbTmncsService.selectTmncsDtl(smdbTmncsDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/sm/smd/SMDBTmncsView.front";
    }
}
