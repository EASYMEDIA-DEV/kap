package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.service.BDCFaqService;
import com.kap.service.COCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * <pre>
 * FAQ를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDCFaqController.java
 * @Description		: FAQ를 위한 Controller
 * @author 구은희
 * @since 2024.01.24
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.24		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/board/faq")
public class BDCFaqController {

    //FAQ 서비스
    private final BDCFaqService bDCFaqService;
    //코드 서비스
    private final COCodeService cOCodeService;

    /**
     * FAQ 목록 페이지
     */
    @GetMapping(value="/list")
    public String getFaqListPage(BDCFaqDTO pBDCFaqDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 코드 set
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("BOARD_TYPE_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", bDCFaqService.selectFaqList(pBDCFaqDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bdc/BDCFaqList.front";
    }

    /**
     * FAQ 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectFaqListPageAjax(BDCFaqDTO pBDCFaqDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            pBDCFaqDTO.setCtgryCd(pBDCFaqDTO.getDetailsKey());
            modelMap.addAttribute("fileList", bDCFaqService.selectFaqFileList(pBDCFaqDTO));
            modelMap.addAttribute("rtnData", bDCFaqService.selectFaqList(pBDCFaqDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/bd/bdc/BDCFaqListAjax";
    }
}
