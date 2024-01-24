package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.service.BDBCompanyNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 재단소식을 위한 Controller
 * </pre>
 *
 * @ClassName		: BDBCompanyNewsController.java
 * @Description		: 재단소식을 위한 Controller
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
@RequestMapping(value="/foundation/board/company-news")
public class BDBCompanyNewsController {

    //재단소식 서비스
    private final BDBCompanyNewsService bDBCompanyNewsService;

    /**
     * 재단소식 목록 페이지
     */
    @GetMapping(value="/list")
    public String getCompanyNewsListPage(BDBCompanyNewsDTO pBDBCompanyNewsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDBCompanyNewsService.selectCompanyNewsList(pBDBCompanyNewsDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bdb/BDBCompanyNewsList.front";
    }


    /**
     * 재단소식 상세 페이지
     */
    @GetMapping(value="/view")
    public String getCompanyNewsViewPage(BDBCompanyNewsDTO pBDBCompanyNewsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDBCompanyNewsDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDBCompanyNewsService.selectCompanyNewsDtl(pBDBCompanyNewsDTO));
                modelMap.addAttribute("fileList", bDBCompanyNewsService.selectCompanyNewsFileList(pBDBCompanyNewsDTO));
                modelMap.addAttribute("nextPrevInfo", bDBCompanyNewsService.selectNextAndPrevSeqVal(pBDBCompanyNewsDTO));
                bDBCompanyNewsService.updateCompanyNewsReadCnt(pBDBCompanyNewsDTO);
            }
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bdb/BDBCompanyNewsView.front";
    }

}