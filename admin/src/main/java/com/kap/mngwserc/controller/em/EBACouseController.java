package com.kap.mngwserc.controller.em;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.EBACouseDTO;
import com.kap.core.dto.SMCPopDTO;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.EBACouseService;
import com.kap.service.SMCPopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 교육과정관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: EBACouseController.java
 * @Description		: 교육과정관리를 위한 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/{langCd}/eb/eba")
public class EBACouseController {

    /** 서비스 **/
    public final EBACouseService eBACouseService;

    /**
     *  교육과정관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCouseList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        return "mngwserc/eb/eba/EBACouseList.admin";
    }

    /**
     * 교육과정관리 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCouseListPageAjax(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/eba/EBACouseListAjax";
    }


    /**
     * 교육과정관리  상세를 조회한다.
     */
    @GetMapping(value="/write")
    public String getCouseDtl(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", eBACouseService.selectCouseDtl(eBACouseDTO));

        return "mngwserc/eb/eba/EBACouseWrite.admin";
    }


    /**
     * 교육과정관리를 등록한다.
     */
    @GetMapping(value="/insert")
    public EBACouseDTO getCouseInsert(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.insertCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }


    /**
     * 교육과정관리를 수정한다.
     */
    @GetMapping(value="/update")
    public EBACouseDTO getCouseUpdate(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.updateCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정관리를  삭제한다.
     */
    @GetMapping(value="/delete")
    public EBACouseDTO getCouseDelete(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.deleteCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }


    /**
     * 교육과정관리를 복사한다.
     */
    @GetMapping(value="/copy")
    public EBACouseDTO getCouseCopy(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            eBACouseDTO.setRegId( coaAdmDTO.getId() );
            eBACouseDTO.setRegName( coaAdmDTO.getName() );
            eBACouseDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            eBACouseDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            eBACouseDTO.setRegIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setModId( coaAdmDTO.getId() );
            eBACouseDTO.setModIp( coaAdmDTO.getLoginIp() );
            eBACouseDTO.setRespCnt( eBACouseService.copyCouse(eBACouseDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }


        return eBACouseDTO;
    }

    /**
     * 교육과정 검색 팝업
     */



    /**
     * 교육과정 회차정보 조회
     */
    @PostMapping(value="/sessionList")
    public String getSessionList(EBACouseDTO eBACouseDTO, ModelMap modelMap, HttpServletRequest request, @PathVariable String langCd) throws Exception
    {
        modelMap.addAttribute("langCd", langCd);
        modelMap.addAttribute("rtnData", eBACouseService.selectCouseList(eBACouseDTO));

        return "mngwserc/eb/eba/EBACouseSessionListAjax";
    }














}

