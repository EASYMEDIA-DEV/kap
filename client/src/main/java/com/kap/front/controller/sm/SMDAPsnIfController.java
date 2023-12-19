package com.kap.front.controller.sm;

import com.kap.core.dto.sm.smd.SMDAPsnIfDTO;
import com.kap.service.SMDAPsnIfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 개인정보처리방침을 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDAPsnIfController.java
 * @Description		: 개인정보처리방침을 위한 Controller
 * @author 구은희
 * @since 2023.09.26
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.09.26		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/utility/privacy-policy")
public class SMDAPsnIfController {

    private final SMDAPsnIfService smdPsnIfService;

    /**
     * 개인정보처리방침 목록 페이지
     */
    @GetMapping(value="/index")
    public String getPsnIfViewPage(SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", smdPsnIfService.selectPsnIfList(smdPsnIfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "/front/sm/smd/SMDAPsnIfView.front";
    }

    /**
     * 셀렉트 박스로 선택한 상세 페이지
     */
    @PostMapping(value="/search")
    public String searchPsnIfViewPage(@RequestBody SMDAPsnIfDTO smdPsnIfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("prsnDtl", smdPsnIfService.selectPsnIfDtl(smdPsnIfDTO));
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
}
