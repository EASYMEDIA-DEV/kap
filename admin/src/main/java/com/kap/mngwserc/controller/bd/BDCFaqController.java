package com.kap.mngwserc.controller.bd;

import com.kap.core.dto.bd.bdc.BDCFaqDTO;
import com.kap.service.BDCFaqService;
import com.kap.service.COCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * <pre>
 * FAQ 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDCFaqController.java
 * @Description		: FAQ 관리를 위한 Controller
 * @author 장두석
 * @since 2023.11.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.21		장두석				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/bd/bdc")
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

            modelMap.addAttribute("rtnData", pBDCFaqDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/bd/bdc/BDCFaqList.admin";
    }

    /**
     * FAQ 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectFaqListPageAjax(BDCFaqDTO pBDCFaqDTO, ModelMap modelMap) throws Exception
    {
        try
        {
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
        return "mngwserc/bd/bdc/BDCFaqListAjax";
    }

    /**
     * FAQ 상세 페이지
     */
    @GetMapping(value="/write")
    public String getFaqWritePage(BDCFaqDTO pBDCFaqDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 코드 set
            ArrayList<String> cdDtlList = new ArrayList<String>();
            cdDtlList.add("BOARD_TYPE_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            if(!"".equals(pBDCFaqDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDCFaqService.selectFaqDtl(pBDCFaqDTO));
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

        return "mngwserc/bd/bdc/BDCFaqWrite.admin";
    }

    /**
     * @ClassName		: BDCFaqRestController.java
     * @Description		: FAQ 관리를 위한 REST Controller
     * @author 장두석
     * @since 2023.11.21
     * @version 1.0
     * @see
     * @Modification Information
     * <pre>
     * 		since			author				  description
     *    ==========    ==============    =============================
     *    2023.11.21		장두석				   최초 생성
     * </pre>
     */
    @RestController
    @RequiredArgsConstructor
    @RequestMapping(value="/mngwserc/bd/bdc")
    public class BDCFaqRestController
    {
        /**
         * FAQ 등록
         */
        @PostMapping(value="/insert")
        public BDCFaqDTO insertFaq(@Valid BDCFaqDTO pBDCFaqDTO) throws Exception
        {
            try
            {
                pBDCFaqDTO.setRespCnt(bDCFaqService.insertFaq(pBDCFaqDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDCFaqDTO;
        }

        /**
         * FAQ 글을 수정한다.
         */
        @PostMapping(value="/update")
        public BDCFaqDTO updateFaq(@Valid BDCFaqDTO pBDCFaqDTO) throws Exception
        {
            try
            {
                pBDCFaqDTO.setRespCnt(bDCFaqService.updateFaq(pBDCFaqDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDCFaqDTO;
        }

        /**
         * FAQ 글을 삭제한다.
         */
        @PostMapping(value="/delete")
        public BDCFaqDTO deleteFaq(BDCFaqDTO pBDCFaqDTO) throws Exception
        {
            try
            {
                pBDCFaqDTO.setRespCnt(bDCFaqService.deleteFaq(pBDCFaqDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pBDCFaqDTO;
        }
    }

}