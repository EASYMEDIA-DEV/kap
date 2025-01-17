package com.kap.front.controller.im.imb;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.IMAQaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * ESG종합지원 컨트롤러
 * </pre>
 *
 * @ClassName		: IMAQaController.java
 * @Description		: ESG종합지원을 위한 Controller
 * @author 장두석
 * @since 2025.01.17
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2025.01.17		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/cs/esg")
public class IMBEsgController {

    /** Service **/
    private final IMAQaService iMAQaService;
    private final COCodeService cOCodeService;

    /**
     * ESG종합지원 페이지
     */
    @GetMapping(value = "/index")
    public String getPartUserListPage(IMAQaDTO pIMAQaDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();

        if(lgnData != null) {
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("INQUIRY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", pIMAQaDTO);

            return "/front/im/imb/IMBEsgWrite.front";
        }
        else {
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            return "redirect:/login?rtnUrl=" + uri + "?" + queryString;
        }
    }


}
