package com.kap.mngwserc.controller.bd;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.bd.bda.BDANoticeDTO;
import com.kap.service.BDANoticeService;
import com.kap.service.COUserDetailsHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <pre>
 * 공지사항 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDANoticeController.java
 * @Description		: 공지사항 관리를 위한 Controller
 * @author 장두석
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/bd/bda")
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
            modelMap.addAttribute("rtnData", pBDANoticeDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/bd/bda/BDANoticeList.admin";
    }

    /**
     * 공지사항 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectNoticeListPageAjax(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDANoticeService.selectNoticeList(pBDANoticeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/bd/bda/BDANoticeListAjax";
    }

    /**
     * 공지사항 상세 페이지
     */
    @GetMapping(value="/write")
    public String getNoticeWritePage(BDANoticeDTO pBDANoticeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDANoticeDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDANoticeService.selectNoticeDtl(pBDANoticeDTO));
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

        return "mngwserc/bd/bda/BDANoticeWrite.admin";
    }

    /**
     * @ClassName		: BDANoticeRestController.java
     * @Description		: 공지사항 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.20
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.20		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/bd/bda")
    public class BDANoticeRestController
    {
        /**
         * 공지사항 등록
         */
        @PostMapping(value="/insert")
        public BDANoticeDTO insertNotice(@Valid BDANoticeDTO pBDANoticeDTO) throws Exception
        {
            try
            {
                pBDANoticeDTO.setRespCnt(bDANoticeService.insertNotice(pBDANoticeDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDANoticeDTO;
        }

        /**
         * 공지사항 글을 수정한다.
         */
        @PostMapping(value="/update")
        public BDANoticeDTO updateNotice(@Valid BDANoticeDTO pBDANoticeDTO) throws Exception
        {
            try
            {
                pBDANoticeDTO.setRespCnt(bDANoticeService.updateNotice(pBDANoticeDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDANoticeDTO;
        }

        /**
         * 공지사항 글을 삭제한다.
         */
        @PostMapping(value="/delete")
        public BDANoticeDTO deleteNotice(BDANoticeDTO pBDANoticeDTO) throws Exception
        {
            try
            {
                pBDANoticeDTO.setRespCnt(bDANoticeService.deleteNotice(pBDANoticeDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pBDANoticeDTO;
        }
    }

}