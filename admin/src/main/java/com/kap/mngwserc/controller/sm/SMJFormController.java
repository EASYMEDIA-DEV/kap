package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.SMJFormDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMJFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 양식 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMJFormController.java
 * @Description		: 양식 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/sm/smj")
public class SMJFormController {

    private final SMJFormService smjFormService;

    /**
     * 상세 페이지로 이동한다.
     */
    @GetMapping (value="/write")
    public String getFormWritePage(SMJFormDTO smjFormDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        String typeName = "Consult";

        try
        {
            if (smjFormDTO.getTypeCd() == null) {
                smjFormDTO.setTypeCd("BUSINESS01");
            } else {
                if (smjFormDTO.getTypeCd().equals("BUSINESS01")) {
                    typeName = "Consult";
                } else if (smjFormDTO.getTypeCd().equals("BUSINESS02")) {
                    typeName = "WinBusiness";
                } else if (smjFormDTO.getTypeCd().equals("BUSINESS03")) {
                    typeName = "Education";
                }
            }
            modelMap.addAttribute("rtnInfo", smjFormService.selectFormDtl(smjFormDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smj/SMJForm" + typeName + "Write.admin";
    }

    /**
     * 양식을 수정한다.
     *
     */
    @PostMapping(value="/update")
    public String updateFormDtl(SMJFormDTO smjFormDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            smjFormDTO.setModId(coaAdmDTO.getId());
            smjFormDTO.setModIp(coaAdmDTO.getLoginIp());

            smjFormDTO.setStySeq(Integer.valueOf(smjFormDTO.getDetailsKey()));
            modelMap.addAttribute("respCnt", smjFormService.updateFormDtl(smjFormDTO));
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