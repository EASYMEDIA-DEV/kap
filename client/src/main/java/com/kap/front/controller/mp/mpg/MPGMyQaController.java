package com.kap.front.controller.mp.mpg;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.im.ima.IMAQaDTO;
import com.kap.service.COCodeService;
import com.kap.service.COMessageService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <pre>
 * 마이페이지 나의 1:1 문의 Controller
 * </pre>
 *
 * @ClassName		: MPGMyQaController.java
 * @Description		: 마이페이지 나의 1:1 문의 Controller
 * @author 장두석
 * @since 2024.01.18
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.18		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/my-page/member/qa")
public class MPGMyQaController {

//    private final MPAUserService mpaUserService;

    private final COCodeService cOCodeService;

    private final COMessageService cOMessageService;

    @Value("${app.site.name}")
    private String siteName;

    /**
     * 나의 1:1문의 페이지
     */
    @GetMapping(value = "/list")
    public String getPartUserListPage(IMAQaDTO pIMAQaDTO, ModelMap modelMap) throws Exception {
        COUserDetailsDTO lgnData = COUserDetailsHelperService.getAuthenticatedUser();

        if(lgnData != null) {
//            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
//            cdDtlList.add("INQUIRY_TYPE");
//            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", pIMAQaDTO);

            return "/front/mp/mpg/MPGMyQaList.front";
        }
        else {
            return "redirect:/login";
        }
    }

}
