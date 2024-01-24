package com.kap.mngwserc.controller.wb.wbe;


import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBEACarbonListService;
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
 * 탄소배출저감 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBEACarbonListController.java
 * @Description		: 탄소배출저감 회차관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/wb/wbea")
public class WBEACarbonListController {

    /** 서비스 **/
    public final WBEACarbonListService wBEACarbonListService;
    public final COCodeService cOCodeService;

    /**
     *  탄소배출저감 회차 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("rtnData", wBEACarbonListService.selectCarbonList(wBRoundMstSearchDTO));


        return "mngwserc/wb/wbe/WBEACarbonList.admin";
    }

    /**
     * 탄소배출저감 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBEACarbonListService.selectCarbonList(wBRoundMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbe/WBEACarbonListAjax";
    }

    /**
     * 탄소배출저감 회차 상세 페이지
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

            modelMap.addAttribute("rtnYear", wBEACarbonListService.selectYearDtl(wBRoundMstSearchDTO));

            if(wBRoundMstSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBEACarbonListService.selectCarbonDtl(wBRoundMstSearchDTO));
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
        return "mngwserc/wb/wbe/WBEACarbonWrite.admin";
    }

    /**
     * 탄소배출저감 회차 등록
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

            wBEACarbonListService.insertCarbon(wBRoundMstDTO, request);
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
     * 탄소배출저감 회차 수정
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

            wBEACarbonListService.updateCarbon(wBRoundMstDTO, request);
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
     * 탄소배출저감 회차 삭제
     */
    @PostMapping(value="/delete")
    public String carbonDelete(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBEACarbonListService.deleteCarbon(wBRoundMstDTO));
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
            modelMap.addAttribute("respCnt", wBEACarbonListService.getAppctnCnt(wBRoundMstDTO));
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
            modelMap.addAttribute("respCnt", wBEACarbonListService.carbonDeleteList(wBRoundMstDTO));
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
            modelMap.addAttribute("respCnt", wBEACarbonListService.episdChk(wBRoundMstDTO));
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
