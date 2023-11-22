package com.kap.mngwserc.controller.bd;

import com.kap.core.dto.bd.bde.BDEDisclosureDTO;
import com.kap.service.BDEDisclosureService;
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
 * 경영공시 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: BDEDisclosureController.java
 * @Description		: 경영공시 관리를 위한 Controller
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
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/bd/bde")
public class BDEDisclosureController {

    //경영공시 서비스
    private final BDEDisclosureService bDEDisclosureService;

    /**
     * 경영공시 목록 페이지
     */
    @GetMapping(value="/list")
    public String getDisclosureListPage(BDEDisclosureDTO pBDEDisclosureDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", pBDEDisclosureDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/bd/bde/BDEDisclosureList.admin";
    }

    /**
     * 경영공시 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectDisclosureListPageAjax(BDEDisclosureDTO pBDEDisclosureDTO, ModelMap modelMap) throws Exception
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
        return "mngwserc/bd/bde/BDEDisclosureListAjax";
    }

    /**
     * 경영공시 상세 페이지
     */
    @GetMapping(value="/write")
    public String getDisclosureWritePage(BDEDisclosureDTO pBDEDisclosureDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if(!"".equals(pBDEDisclosureDTO.getDetailsKey())){
                modelMap.addAttribute("rtnInfo", bDEDisclosureService.selectDisclosureDtl(pBDEDisclosureDTO));
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

        return "mngwserc/bd/bde/BDEDisclosureWrite.admin";
    }

    /**
     * @ClassName		: BDEDisclosureRestController.java
     * @Description		: 경영공시 관리를 위한 REST Controller
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
    @RequestMapping(value="/mngwserc/bd/bde")
    public class BDEDisclosureRestController
    {
        /**
         * 경영공시 등록
         */
        @PostMapping(value="/insert")
        public BDEDisclosureDTO insertDisclosure(@Valid BDEDisclosureDTO pBDEDisclosureDTO) throws Exception
        {
            try
            {
                pBDEDisclosureDTO.setRespCnt(bDEDisclosureService.insertDisclosure(pBDEDisclosureDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDEDisclosureDTO;
        }

        /**
         * 경영공시 수정
         */
        @PostMapping(value="/update")
        public BDEDisclosureDTO updateDisclosure(@Valid BDEDisclosureDTO pBDEDisclosureDTO) throws Exception
        {
            try
            {
                pBDEDisclosureDTO.setRespCnt(bDEDisclosureService.updateDisclosure(pBDEDisclosureDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }
            return pBDEDisclosureDTO;
        }

        /**
         * 경영공시 삭제
         */
        @PostMapping(value="/delete")
        public BDEDisclosureDTO deleteDisclosure(BDEDisclosureDTO pBDEDisclosureDTO) throws Exception
        {
            try
            {
                pBDEDisclosureDTO.setRespCnt(bDEDisclosureService.deleteDisclosure(pBDEDisclosureDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pBDEDisclosureDTO;
        }
    }

}