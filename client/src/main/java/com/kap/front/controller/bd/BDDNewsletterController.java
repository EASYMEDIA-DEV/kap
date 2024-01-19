package com.kap.front.controller.bd;

import com.kap.core.dto.bd.bdd.BDDNewsletterDTO;
import com.kap.core.dto.mp.mph.MPHNewsLetterDTO;
import com.kap.service.BDDNewsletterService;
import com.kap.service.MPHNewsLetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /** 뉴스레터 구독 서비스 **/
    public final MPHNewsLetterService mphNewsLetterService;

    /**
     * 뉴스레터 목록 페이지
     */
    @GetMapping(value="/list")
    public String getNewsletterListPage(BDDNewsletterDTO pBDDNewsletterDTO, ModelMap modelMap) throws Exception
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

        return "front/bd/bdd/BDDNewsletterList.front";
    }

    /**
     * 뉴스레터 상세 페이지
     */
    @GetMapping(value="/view")
    public String getNewsletterViewPage(BDDNewsletterDTO pBDDNewsletterDTO, ModelMap modelMap) throws Exception
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

        return "front/bd/bdd/BDDNewsletterView.front";
    }

    /**
     * @ClassName		: BDDNewsletterRestController.java
     * @Description		: 뉴스레터 구독을 위한 REST Controller
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
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/foundation/board/newsletter")
    public class BDDNewsletterRestController
    {

        /**
         * 뉴스레터 이메일 중복체크
         */
        @PostMapping(value = "/dup-email")
        public MPHNewsLetterDTO selectDupEmail(@Valid @RequestBody MPHNewsLetterDTO mPHNewsLetterDTO ) throws Exception {

            try {

                mPHNewsLetterDTO.setRespCnt(mphNewsLetterService.selectDupEmail(mPHNewsLetterDTO));

            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return mPHNewsLetterDTO;
        }

        /**
         * 뉴스레터 구독
         */
        @PostMapping(value = "/insert")
        public MPHNewsLetterDTO insertNewsletter(@Valid @RequestBody MPHNewsLetterDTO mPHNewsLetterDTO, HttpServletRequest request) throws Exception {

            try {
                mphNewsLetterService.insertNewsletter(mPHNewsLetterDTO, request);

            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return mPHNewsLetterDTO;
        }
    }

}