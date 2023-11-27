package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.SMISmsCntnDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.SMISmsCntnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * SMS 내용 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMISmsCntnController.java
 * @Description		: SMS 내용 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.15
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.16		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smi")
public class SMISmsCntnController {

    private final SMISmsCntnService smiSmsCntnService;

    //코드 서비스
    private final COCodeService cOCodeService;

    /**
     * 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getSmsCntnListPage(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("SMS_CD");

        // 정의된 코드id값들의 상세 코드 맵 반환
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
        modelMap.addAttribute("rtnData", smiSmsCntnService.selectSmsCntnList(smiSmsCntnDTO));

        return "mngwserc/sm/smi/SMISmsCntnList.admin";
    }

    /**
     * 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectSmsCntnListPageAjax(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", smiSmsCntnService.selectSmsCntnList(smiSmsCntnDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/sm/smi/SMISmsCntnListAjax";
    }

    /**
     * 상세 페이지로 이동한다.
     */
    @RequestMapping(value="/write")
    public String getSmsCntnWritePage(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("SMS_CD");

            // 정의된 코드id값들의 상세 코드 맵 반환
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnInfo", smiSmsCntnService.selectSmsCntnDtl(smiSmsCntnDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smi/SMISmsCntnWrite.admin";
    }

    /**
     * SMS 내용 등록 시 구분 코드 중복을 확인한다.
     */
    @RequestMapping(value="/codeDupCheck")
    public String selectSmsCodeDupCheck(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            String dupChk;
            if(smiSmsCntnService.selectSmsCodeDupCheck(smiSmsCntnDTO) >= 1) {
                dupChk = "Y";
            } else {
                dupChk = "N";
            }
            modelMap.addAttribute("dupChk", dupChk);
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
     * SMS 내용을 등록한다.
     */
    @PostMapping (value="/insert")
    public String insertSmsCntn(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO =COUserDetailsHelperService.getAuthenticatedUser();
            smiSmsCntnDTO.setRegId(cOUserDetailsDTO.getId());
            smiSmsCntnDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            int respCnt = smiSmsCntnService.insertSmsCntn(smiSmsCntnDTO);
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
     * SMS 내용을 수정한다.
     *
     */
    @PostMapping(value="/update")
    public String updateSmsCntn(SMISmsCntnDTO smiSmsCntnDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            smiSmsCntnDTO.setModId(cOUserDetailsDTO.getId());
            smiSmsCntnDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", smiSmsCntnService.updateSmsCntn(smiSmsCntnDTO));
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
