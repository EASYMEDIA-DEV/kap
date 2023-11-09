package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMDBTmncsDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMDBTmncsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 이용약관 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDBTmncsController.java
 * @Description		: 이용약관 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/sm/smd/smdb")
public class SMDBTmncsController {

    private final SMDBTmncsService smdbTmncsService;

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getTmncsWritePage(SMDBTmncsDTO smdbTmncsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smdbTmncsService.selectTmncsDtl(smdbTmncsDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smd/SMDBTmncsWrite.admin";
    }

    /**
     * 이용약관을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertTmncs(SMDBTmncsDTO smdbTmncsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdbTmncsDTO.setRegId(coaAdmDTO.getId());
            smdbTmncsDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smdbTmncsService.insertTmncs(smdbTmncsDTO);
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
     * 이용약관을 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updateTmncs(SMDBTmncsDTO smdbTmncsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdbTmncsDTO.setModId(coaAdmDTO.getId());
            smdbTmncsDTO.setModIp(coaAdmDTO.getLoginIp());

            smdbTmncsDTO.setTmncsSeq(Integer.valueOf(smdbTmncsDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smdbTmncsService.updateTmncs(smdbTmncsDTO));
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
