package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import com.kap.service.BDDNewsletterService;
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
 * 뉴스레터 구독을 위한 Controller
 * </pre>
 *
 * @ClassName		: BDDNewsletterController.java
 * @Description		: 뉴스레터 구독을 위한 Controller
 * @author 구은희
 * @since 2024.01.18
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2024.01.18		구은희				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/foundation/board/newsletter")
public class BDDNewsletterController {

    //뉴스레터 서비스
    private final BDDNewsletterService bDDNewsletterService;

    /**
     * 뉴스레터 목록 페이지
     */
    @GetMapping(value="/list")
    public String getNewsletterListPage(BDDNewsletterDTO pBDDNewsletterDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", pBDDNewsletterDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "front/bd/bdd/BDDNewsletterList.front";
    }

    /**
     * 뉴스레터 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectNewsletterListAjax(BDDNewsletterDTO pBDDNewsletterDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", bDDNewsletterService.selectNewsletterList(pBDDNewsletterDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "front/bd/bdd/BDDNewsletterListAjax";
    }

    /**
     * 뉴스레터 상세 페이지
     */
    @GetMapping(value="/write")
    public String getNewsletterWritePage(BDDNewsletterDTO pBDDNewsletterDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDDNewsletterDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDDNewsletterService.selectNewsletterDtl(pBDDNewsletterDTO));
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

        return "mngwserc/bd/bdd/BDDNewsletterWrite.front";
    }

    /**
     * @ClassName		: BDDNewsletterRestController.java
     * @Description		: 뉴스레터 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.22
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.22		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/bd/bdd")
    public class BDDNewsletterRestController
    {
        /**
         * 뉴스레터 등록
         */
        @PostMapping(value="/insert")
        public BDDNewsletterDTO insertNewsletter(@Valid BDDNewsletterDTO pBDDNewsletterDTO) throws Exception
        {
            try
            {
                pBDDNewsletterDTO.setRespCnt(bDDNewsletterService.insertNewsletter(pBDDNewsletterDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDDNewsletterDTO;
        }

        /**
         * 뉴스레터 수정
         */
        @PostMapping(value="/update")
        public BDDNewsletterDTO updateNewsletter(@Valid BDDNewsletterDTO pBDDNewsletterDTO) throws Exception
        {
            try
            {
                pBDDNewsletterDTO.setRespCnt(bDDNewsletterService.updateNewsletter(pBDDNewsletterDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDDNewsletterDTO;
        }

        /**
         * 뉴스레터 삭제
         */
        @PostMapping(value="/delete")
        public BDDNewsletterDTO deleteNewsletter(BDDNewsletterDTO pBDDNewsletterDTO) throws Exception
        {
            try
            {
                pBDDNewsletterDTO.setRespCnt(bDDNewsletterService.deleteNewsletter(pBDDNewsletterDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pBDDNewsletterDTO;
        }
    }

}