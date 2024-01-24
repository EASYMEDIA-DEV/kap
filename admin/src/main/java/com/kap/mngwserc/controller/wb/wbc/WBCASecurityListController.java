package com.kap.mngwserc.controller.wb.wbc;


import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBCASecurityListService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;


/**
 * <pre>
 * 보안환경구축 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBCASecurityListController.java
 * @Description		: 보안환경구축 회차관리를 위한 Controller
 * @author 김대성
 * @since 2023.11.08
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.08		김대성				   최초 생성
 * </pre>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbca")
public class WBCASecurityListController {

    /** 서비스 **/
    public final WBCASecurityListService wBCASecurityListService;
    public final COCodeService cOCodeService;

    /**
     *  보안환경구축 회차 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("rtnData", wBCASecurityListService.selectCarbonList(wBRoundMstSearchDTO));


        return "mngwserc/wb/wbc/WBCASecurityList.admin";
    }

    /**
     * 보안환경구축 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBCASecurityListService.selectCarbonList(wBRoundMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbc/WBCASecurityListAjax";
    }

    /**
     * 보안환경구축 회차 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap
            ,@Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("ROUND_CD");
            cdDtlList.add("SYSTEM_HOUR");

            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wBCASecurityListService.selectYearDtl(wBRoundMstSearchDTO));

            if(wBRoundMstSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBCASecurityListService.selectCarbonDtl(wBRoundMstSearchDTO));
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
        return "mngwserc/wb/wbc/WBCASecurityWrite.admin";
    }

    /**
     * 보안환경구축 회차 등록
     */
    @PostMapping(value="/insert")
    @ResponseBody
    public WBRoundMstDTO carbonInsert(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            wBCASecurityListService.insertCarbon(wBRoundMstDTO, request);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBRoundMstDTO;
    }

    /**
     * 보안환경구축 회차 수정
     */
    @PostMapping(value="/update")
    @ResponseBody
    public WBRoundMstDTO carbonUpdate(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            wBCASecurityListService.updateCarbon(wBRoundMstDTO, request);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return wBRoundMstDTO;
    }

    /**
     * 보안환경구축 회차 삭제
     */
    @PostMapping(value="/delete")
    public String carbonDelete(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBCASecurityListService.deleteCarbon(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 회차 매핑 여부 확인
     */
    @PostMapping(value="/getAppctnCnt")
    public String getAppctnCnt(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCASecurityListService.getAppctnCnt(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 회차 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String carbonDeleteList(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCASecurityListService.carbonDeleteList(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

    /**
     * 회차 중복 체크
     */
    @PostMapping(value="/episdChk")
    public String episdChk(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCASecurityListService.episdChk(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "jsonView";
    }

}
