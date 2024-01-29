package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bde.BDEDisclosureDTO;
import com.kap.service.BDEDisclosureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 경영공시를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDEDisclosureController.java
 * @Description		: 경영공시를 위한 Controller
 * @author 구은희
 * @since 2024.01.25
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.25		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/intro/disclosure")
public class BDEDisclosureController {

    //경영공시 서비스
    private final BDEDisclosureService bDEDisclosureService;

    /**
     * 경영공시 목록 페이지
     */
    @GetMapping(value="/index")
    public String getDisclosureListPage(BDEDisclosureDTO pBDEDisclosureDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDEDisclosureService.selectDisclosureList(pBDEDisclosureDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "/front/bd/bde/BDEDisclosureView.front";
    }

    /**
     * 셀렉트 박스로 선택한 상세 페이지
     */
    @PostMapping(value = "/search")
    public String searchDisclosureViewPage(@RequestBody BDEDisclosureDTO pBDEDisclosureDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnDtl", bDEDisclosureService.selectDisclosureDtl(pBDEDisclosureDTO));
            modelMap.addAttribute("fileList", bDEDisclosureService.selectDisclosureFileList(pBDEDisclosureDTO).getList());
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