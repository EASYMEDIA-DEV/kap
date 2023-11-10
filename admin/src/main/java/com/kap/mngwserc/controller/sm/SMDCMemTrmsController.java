package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMDCMemTrmsDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMDCMemTrmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 회원약관 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMDCMemTrmsController.java
 * @Description		: 회원약관 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.07
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.07		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smd/smdc")
public class SMDCMemTrmsController {

    private final SMDCMemTrmsService smdcMemTrmsService;

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getMemTrmsWritePage(SMDCMemTrmsDTO smdcMemTrmsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnInfo", smdcMemTrmsService.selectMemTrmsDtl(smdcMemTrmsDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smd/SMDCMemTrmsWrite.admin";
    }

    /**
     * 회원약관을 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insertMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdcMemTrmsDTO.setRegId(coaAdmDTO.getId());
            smdcMemTrmsDTO.setRegIp(coaAdmDTO.getLoginIp());

            int respCnt = smdcMemTrmsService.insertMemTrms(smdcMemTrmsDTO);
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
     * 회원약관을 수정한다.
     *
     */
    @RequestMapping(value="/update")
    public String updateMemTrms(SMDCMemTrmsDTO smdcMemTrmsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smdcMemTrmsDTO.setModId(coaAdmDTO.getId());
            smdcMemTrmsDTO.setModIp(coaAdmDTO.getLoginIp());

            smdcMemTrmsDTO.setMemTrmsSeq(Integer.valueOf(smdcMemTrmsDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smdcMemTrmsService.updateMemTrms(smdcMemTrmsDTO));
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
