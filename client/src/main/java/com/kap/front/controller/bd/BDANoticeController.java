package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.service.BDANoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 공지사항을 위한 Controller
 * </pre>
 *
 * @ClassName		: BDANoticeController.java
 * @Description		: 공지사항을 위한 Controller
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
@RequestMapping(value="/foundation/board/notice")
public class BDANoticeController {

    //공지사항 서비스
    private final BDANoticeService bDANoticeService;

    /**
     * 공지사항 목록 페이지
     */
    @GetMapping(value="/list")
    public String getNoticeListPage(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {

            modelMap.addAttribute("rtnData", bDANoticeService.selectNoticeList(pBDANoticeDTO));
            modelMap.addAttribute("mainPostData", bDANoticeService.selectMainPostList(pBDANoticeDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bda/BDANoticeList.front";
    }


    /**
     * 공지사항 상세 페이지
     */
    @GetMapping(value="/view")
    public String getNoticeViewPage(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDANoticeDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDANoticeService.selectNoticeDtl(pBDANoticeDTO));
                modelMap.addAttribute("fileList", bDANoticeService.selectNoticeFileList(pBDANoticeDTO));

                pBDANoticeDTO.setRownum(bDANoticeService.selectNoticeRowNum(pBDANoticeDTO).getRownum());
                modelMap.addAttribute("nextInfo", bDANoticeService.selectNextRowNumInfo(pBDANoticeDTO));
                modelMap.addAttribute("prevInfo", bDANoticeService.selectPrevRowNumInfo(pBDANoticeDTO));

                //modelMap.addAttribute("nextPrevInfo", bDANoticeService.selectNextAndPrevSeqVal(pBDANoticeDTO));
                bDANoticeService.updateNoticeReadCnt(pBDANoticeDTO);
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

        return "front/bd/bda/BDANoticeView.front";
    }

    /**
     * 통합검색 공지사항 조회
     */
    @GetMapping(value="/select")
    public String getTotalSearchNoticeListPage(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDANoticeService.selectNoticeTotalList(pBDANoticeDTO));
            modelMap.addAttribute("mainPostData", bDANoticeService.selectMainPostTotalList(pBDANoticeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bda/BDANoticeListAjax";
    }

    /**
     * 통합검색 공지사항 탭 조회
     */
    @GetMapping(value="/tab/list")
    public String getTotalSearchNoticeTabListPage(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDANoticeService.selectNoticeTabList(pBDANoticeDTO));
            //modelMap.addAttribute("mainPostData", bDANoticeService.selectMainPostTotalList(pBDANoticeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bda/BDANoticeTabAjax";
    }



}