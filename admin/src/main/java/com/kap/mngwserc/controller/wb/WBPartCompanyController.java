package com.kap.mngwserc.controller.wb;

import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.service.WBPartCompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * <pre>
 * 스마트공장구축 회차 관리 Controller
 * </pre>
 *
 * @ClassName		: CompanyController.java
 * @Description		: 상생 - 공통 부품사 회원 정보 Controller
 * @author 김동현
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		김동현				   최초 생성
 * </pre>
 */
@Tag(name = "상생-공통 부품사 회원", description = "상생-공통 부품사 회원 정보")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/{wbSubUrl}")
public class WBPartCompanyController {

    /* 공통 코드 service DI */
    private final WBPartCompanyService wBPartCompanyService;

    /**
     * 부품사회원 / 담당위원 modal 목록 Ajax
     */
    @PostMapping(value = "/selModalData")
    public String selectPartUserListPageAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        wBPartCompanyDTO.setExcelYn("N");
        modelMap.addAttribute("rtnData", wBPartCompanyService.selPartCompanyUserList(wBPartCompanyDTO));
        return "mngwserc/wb/WBFBPartUserModalAjax";
    }

    /**
     * 부품사회원 / 담당위원 Detail Ajax
     */
    @PostMapping(value = "/selModalDetail")
    public String selectPartUserDetailAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        try
        {
            modelMap.addAttribute("rtnData", wBPartCompanyService.selPartUserDetail(wBPartCompanyDTO));
            wBPartCompanyDTO.setWorkBsnmNo(((WBPartCompanyDTO)modelMap.getAttribute("rtnData")).getWorkBsnmNo());
            modelMap.addAttribute("rtnDataCompDetail", wBPartCompanyService.selectPartUserCompDetailAjax(wBPartCompanyDTO));
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
     * 부품사회원 / 담당위원 Detail Ajax
     */
    @RequestMapping(value = "/partUserChk")
    @ResponseBody
    public WBPartCompanyDTO selectPartUserChkAjax(@Valid @RequestBody WBPartCompanyDTO wBPartCompanyDTO, ModelMap modelMap ) throws Exception {
        WBPartCompanyDTO partCompanyDTO = new WBPartCompanyDTO();
        try
        {
            partCompanyDTO.setRespCnt(wBPartCompanyService.selectPartUserChkAjax(wBPartCompanyDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return partCompanyDTO;
    }

}
