package com.kap.mngwserc.controller.wb.wbk;

import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.service.WBKBPartService;
import com.kap.service.WBPartCompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 미래차 공모전 신청팀 관리 Controller
 * </pre>
 *
 * @ClassName		: CompanyController.java
 * @Description		: 상생 - 미래차 공모전 신청 팀 Controller
 * @author 민윤기
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
@Tag(name = "상생-공모전 일반 회원", description = "상생 - 미래차 공모전 일반 회원 정보")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbkb")
public class WBKBPartController {

    /* 공통 코드 service DI */
    private final WBKBPartService wBKBPartService;

    /**
     * 공모전 일반회원 modal 목록 Ajax
     */
    @PostMapping(value = "/selCoModalData")
    public String selectCoPartUserListPageAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        wBPartCompanyDTO.setMemCd("CO");
        wBPartCompanyDTO.setExcelYn("N");
        modelMap.addAttribute("rtnData", wBKBPartService.selPartCompanyUserList(wBPartCompanyDTO));

        return "mngwserc/wb/WBKBCoUserModalAjax";
    }

    /**
    * 공모전 일반 회원 정보
     */
    @PostMapping(value = "/selCoModalDetail")
    public String selectCoPartUserDetailAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        try
        {
            wBPartCompanyDTO.setMemCd("CO");
            modelMap.addAttribute("rtnData", wBKBPartService.selPartUserDetail(wBPartCompanyDTO));
            wBPartCompanyDTO.setWorkBsnmNo(((WBPartCompanyDTO)modelMap.getAttribute("rtnData")).getWorkBsnmNo());
            //modelMap.addAttribute("rtnDataCompDetail", wBPartCompanyService.selectPartUserCompDetailAjax(wBPartCompanyDTO));
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
