package com.kap.mngwserc.controller.mp;

import com.kap.core.dto.MPHNewsLetterDTO;
import com.kap.service.MPHNewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 뉴스레터 신청자 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: MPHNewsLetterController.java
 * @Description		: 뉴스레터 신청자 관리를 위한 Controller
 * @author 구은희
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/mp/mph")
public class MPHNewsLetterController {

    /** 서비스 **/
    public final MPHNewsLetterService mphNewsLetterService;

    /**
     * 신청자 목록 페이지로 이동한다.
     */
    @GetMapping(value="/list")
    public String getNewsLetterListPage(MPHNewsLetterDTO mphNewsLetterDTO, ModelMap modelMap) throws Exception
    {
        modelMap.addAttribute("rtnData", mphNewsLetterService.selectNewsLetterList(mphNewsLetterDTO));
        return "mngwserc/mp/mph/MPHNewsLetterList.admin";
    }

    /**
     * 신청자 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String selectNewsLetterListPageAjax(MPHNewsLetterDTO mphNewsLetterDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", mphNewsLetterService.selectNewsLetterList(mphNewsLetterDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/mp/mph/MPHNewsLetterListAjax";
    }

}
