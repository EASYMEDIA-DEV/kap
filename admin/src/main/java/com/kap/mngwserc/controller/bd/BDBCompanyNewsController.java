package com.kap.mngwserc.controller.bd;

import com.kap.core.dto.bd.bdb.BDBCompanyNewsDTO;
import com.kap.service.BDBCompanyNewsService;
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
 * 재단소식 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDBCompanyNewsController.java
 * @Description		: 재단소식 관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/bd/bdb")
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
            modelMap.addAttribute("rtnData", pBDBCompanyNewsDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/bd/bdb/BDBCompanyNewsList.admin";
    }

    /**
     * 재단소식 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectCompanyNewsListPageAjax(BDBCompanyNewsDTO pBDBCompanyNewsDTO, ModelMap modelMap) throws Exception
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
        return "mngwserc/bd/bdb/BDBCompanyNewsListAjax";
    }

    /**
     * 재단소식 상세 페이지
     */
    @GetMapping(value="/write")
    public String getCompanyNewsWritePage(BDBCompanyNewsDTO pBDBCompanyNewsDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDBCompanyNewsDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDBCompanyNewsService.selectCompanyNewsDtl(pBDBCompanyNewsDTO));
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

        return "mngwserc/bd/bdb/BDBCompanyNewsWrite.admin";
    }

    /**
     * @ClassName		: BDBCompanyNewsRestController.java
     * @Description		: 재단소식 관리를 위한 REST Controller
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
    @RequestMapping(value="/mngwserc/bd/bdb")
    public class BDBCompanyNewsRestController
    {
        /**
         * 재단소식 등록
         */
        @PostMapping(value="/insert")
        public BDBCompanyNewsDTO insertCompanyNews(@Valid BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception
        {
            try
            {
                pBDBCompanyNewsDTO.setRespCnt(bDBCompanyNewsService.insertCompanyNews(pBDBCompanyNewsDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDBCompanyNewsDTO;
        }

        /**
         * 재단소식 글을 수정한다.
         */
        @PostMapping(value="/update")
        public BDBCompanyNewsDTO updateCompanyNews(@Valid BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception
        {
            try
            {
                pBDBCompanyNewsDTO.setRespCnt(bDBCompanyNewsService.updateCompanyNews(pBDBCompanyNewsDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDBCompanyNewsDTO;
        }

        /**
         * 재단소식 글을 삭제한다.
         */
        @PostMapping(value="/delete")
        public BDBCompanyNewsDTO deleteCompanyNews(BDBCompanyNewsDTO pBDBCompanyNewsDTO) throws Exception
        {
            try
            {
                pBDBCompanyNewsDTO.setRespCnt(bDBCompanyNewsService.deleteCompanyNews(pBDBCompanyNewsDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pBDBCompanyNewsDTO;
        }
    }

}