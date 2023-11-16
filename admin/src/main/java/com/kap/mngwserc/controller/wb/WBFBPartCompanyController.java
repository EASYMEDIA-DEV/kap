package com.kap.mngwserc.controller.wb;

import com.kap.core.dto.wb.wbf.WBFASmartRoundDTO;
import com.kap.service.COCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * <pre>
 * 스마트공장구축 회차 관리 Controller
 * </pre>
 *
 * @ClassName		: WBFASmartRoundController.java
 * @Description		: 스마트공장구축 업체 관리 Controller
 * @author 김동현
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김동현				   최초 생성
 * </pre>
 */
@Tag(name = "스마트공장구축-신청업체관리페이지", description = "신청업체관리 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbfb")
public class WBFBPartCompanyController {

    /* 공통 코드 service DI */
    private final COCodeService cOCodeService;

    /**
     * 회차 목록 페이지
     */
    @GetMapping(value="/list")
    public String getPartCompanyList(WBFASmartRoundDTO wbfaSmartRoundDTO, ModelMap modelMap) throws Exception
    {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_STATE_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));



            modelMap.addAttribute("rtnData", wbfaSmartRoundDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBPartCompanyList.admin";
    }

    /**
     * 회차 등록 페이지
     */
    @GetMapping(value="/write")
    public String putPartCompany(WBFASmartRoundDTO wbfaSmartRoundDTO, ModelMap modelMap) throws Exception
    {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            cdDtlList.add("PRO_STATE_TYPE");
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("rtnData", wbfaSmartRoundDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBPartCompanyWrite.admin";
    }

    /**
     * 회차 수정 페이지
     */
    @GetMapping(value="/edit")
    public String udtPartCompany(WBFASmartRoundDTO wbfaSmartRoundDTO, ModelMap modelMap) throws Exception
    {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("rtnData", wbfaSmartRoundDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBPartCompanyEdit.admin";
    }

}
