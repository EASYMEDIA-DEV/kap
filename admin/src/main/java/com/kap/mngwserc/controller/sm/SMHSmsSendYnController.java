package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMHSmsSendYnDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMHSmsSendYnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * SMS 발송 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMHhmpgeController.java
 * @Description		: SMS 발송 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.15		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smh")
public class SMHSmsSendYnController {

    private final SMHSmsSendYnService smhSmsSendYnService;

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getHmpgeWritePage(SMHSmsSendYnDTO smhSmsSendYnDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smhSmsSendYnService.selectSmsSendYnDtl(smhSmsSendYnDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smh/SMHSmsSendYnWrite.admin";
    }

    /**
     * SMS 발송 여부를 수정한다.
     *
     */
    @PostMapping(value="/update")
    public String updateHmpge(SMHSmsSendYnDTO smhSmsSendYnDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smhSmsSendYnDTO.setModId(coaAdmDTO.getId());
            smhSmsSendYnDTO.setModIp(coaAdmDTO.getLoginIp());

            smhSmsSendYnDTO.setCfgSeq(smhSmsSendYnDTO.getCfgSeq());
            modelMap.addAttribute("respCnt", smhSmsSendYnService.updateSmsSendYn(smhSmsSendYnDTO));
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
}
