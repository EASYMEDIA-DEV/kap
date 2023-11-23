package com.kap.mngwserc.controller.sm;

import com.kap.core.dto.sm.smg.SMGWinBusinessDTO;
import com.kap.service.SMGWinBusinessService;
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
 * 상생 사업 관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: SMGWinBusinessController.java
 * @Description		: 상생 사업 관리를 위한 Controller
 * @author 임서화
 * @since 2023.11.14
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.14		임서화				   최초 생성
 *    2023.11.22        장두석                  최신화
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/sm/smg")
public class SMGWinBusinessController
{

    private final SMGWinBusinessService sMGWinBusinessService;

    /**
     * 상생사업 관리 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getWinBusinessListPage(SMGWinBusinessDTO pSMGWinBusinessDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", pSMGWinBusinessDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smg/SMGWinBusinessList.admin";
    }

    /**
     * 상생사업 관리 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectWinBusinessListAjax(SMGWinBusinessDTO pSMGWinBusinessDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessList(pSMGWinBusinessDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/sm/smg/SMGWinBusinessListAjax";
    }

    /**
     * 상생사업 관리 상세 페이지
     */
    @GetMapping(value = "/write")
    public String getWinBusinessWritePage(SMGWinBusinessDTO pSMGWinBusinessDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            if (!"".equals(pSMGWinBusinessDTO.getDetailsKey()) && pSMGWinBusinessDTO.getDetailsKey() != null)
            {
                modelMap.addAttribute("rtnData", sMGWinBusinessService.selectWinBusinessDtl(pSMGWinBusinessDTO));
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

        return "mngwserc/sm/smg/SMGWinBusinessWrite.admin";
    }

    /**
     * @ClassName		: SMGWinBusinessRestController.java
     * @Description		: 상생사업 관리를 위한 REST Controller
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
    @RequestMapping(value="/mngwserc/sm/smg")
    public class SMGWinBusinessRestController
    {

        /**
         * 상생사업 관리 등록
         */
        @PostMapping(value = "/insert")
        public SMGWinBusinessDTO insertWinBusiness(@Valid SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
        {
            try
            {
                pSMGWinBusinessDTO.setRespCnt(sMGWinBusinessService.insertWinBusiness(pSMGWinBusinessDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMGWinBusinessDTO;
        }

        @PostMapping(value = "/update")
        public SMGWinBusinessDTO updateWinBusiness(@Valid SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
        {
            try
            {
                pSMGWinBusinessDTO.setRespCnt(sMGWinBusinessService.updateWinBusiness(pSMGWinBusinessDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMGWinBusinessDTO;
        }

        /**
         * 상생사업 관리 삭제
         */
        @PostMapping(value = "/delete")
        public SMGWinBusinessDTO deleteWinBusiness(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
        {
            try
            {
                pSMGWinBusinessDTO.setRespCnt(sMGWinBusinessService.deleteWinBusiness(pSMGWinBusinessDTO));
            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMGWinBusinessDTO;
        }

        /**
         * 상생사업 관리 정렬 수정
         */
        @PostMapping(value="/sort")
        public SMGWinBusinessDTO updateOrder(SMGWinBusinessDTO pSMGWinBusinessDTO) throws Exception
        {
            try
            {
                sMGWinBusinessService.updateOrder(pSMGWinBusinessDTO);

            }
            catch (Exception e)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(e.getMessage());
                }
                throw new Exception(e.getMessage());
            }

            return pSMGWinBusinessDTO;
        }

    }

}