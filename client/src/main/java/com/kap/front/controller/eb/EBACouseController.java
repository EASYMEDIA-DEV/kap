package com.kap.front.controller.eb;

import com.kap.core.dto.eb.ebb.EBBEpisdDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBBEpisdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <pre>
 * 교육 과정 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육 과정 Controller
 * @author 김학규
 * @since 2023.12.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.20		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "교육사업 > 교육신청")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/education")
public class EBACouseController {

    /** 서비스 **/
    private final EBBEpisdService eBBEpisdService;
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /**
     * 교육과정 신청 목록
     */
    @GetMapping(value="/apply/list")
    public String getEducationApplyList(ModelMap modelMap, EBBEpisdDTO eBBEpisdDTO) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseList.front";
        try
        {
            EBBEpisdDTO rtnList  = eBBEpisdService.selectEpisdList(eBBEpisdDTO);

            modelMap.addAttribute("rtnData", rtnList);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

    /**
     * 교육과정 목록을 조회한다.
     */
    @RequestMapping(value = "/apply/select")
    public String getEpisdPageAjax(EBBEpisdDTO eBBEpisdDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBBEpisdService.selectEpisdList(eBBEpisdDTO));
            modelMap.addAttribute("eBBEpisdDTO", eBBEpisdDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/eb/eba/EBACouseListAjax";
    }



    /**
     * 교육과정 신청 상세
     */
    @PostMapping(value="/apply/detail")
    public String getEducationApplyDtl(ModelMap modelMap) throws Exception
    {
        String vwUrl = "front/eb/eba/EBACouseDtl.front";
        try
        {
            System.out.println("@@@@@ 온닷!!! 교육과정 신청 상세");
            modelMap.addAttribute("rtnData", "");
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return vwUrl;
    }

}

