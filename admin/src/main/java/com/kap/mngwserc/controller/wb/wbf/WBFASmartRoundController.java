package com.kap.mngwserc.controller.wb.wbf;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFASmartRoundService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * 스마트공장구축 회차 관리 Controller
 * </pre>
 *
 * @ClassName		: WBFASmartRoundController.java
 * @Description		: 스마트공장구축 회차 관리 Controller
 * @author 김동현
 * @since 2023.11.16
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김동현				   최초 생성
 * </pre>
 */
@Tag(name = "스마트공장구축-회차 관리 페이지", description = "스마트공장구축 회차 관리 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbfa")
public class WBFASmartRoundController {

    /* Service */
    private final WBFASmartRoundService wBFASmartRoundService;

    /* 공통 코드 service DI */
    private final COCodeService cOCodeService;

    /**
     * 회차 관리 List 페이지 이동
     */
    @GetMapping(value = "/list")
    public String getSmartRoundListPage(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("STATE_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", wBRoundMstSearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfa/WBFASmartRoundList.admin";
    }

    /**
     * 리스트 Select Ajax
     */
    @RequestMapping(value = "/select")
    public String getSmartRoundListAjax(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wBFASmartRoundService.selApplyCompanyList(wBRoundMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfa/WBFASmartRoundListAjax";
    }

    /**
     * 회차 관리 Write 페이지 이동 
     */
    @RequestMapping(value = "/write")
    public String getSmartRoundWritePage(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap
            ,@Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("ROUND_CD");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wBFASmartRoundService.selectYearDtl(wBRoundMstSearchDTO));

            if(wBRoundMstSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBFASmartRoundService.selRoundDetail(wBRoundMstSearchDTO));
            }

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbfa/WBFASmartRoundWrite.admin";
    }

    /**
     * 스마트공장구축 - 회차 관리 등록
     */
    @PostMapping(value="/insert")
    @ResponseBody
    public WBRoundMstDTO SmartRoundInsert(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(coaAdmDTO.getId());
            wBRoundMstDTO.setRegIp(coaAdmDTO.getLoginIp());

            wBFASmartRoundService.insertRound(wBRoundMstDTO, request);
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
     * 스마트공장구축 - 회차 수정
     */
    @PostMapping(value="/update")
    @ResponseBody
    public WBRoundMstDTO SmartRoundUpdate(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(coaAdmDTO.getId());
            wBRoundMstDTO.setModIp(coaAdmDTO.getLoginIp());

            wBFASmartRoundService.updateRound(wBRoundMstDTO, request);
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
     * 스마트공장구축 - 회차 삭제
     */
    @PostMapping(value="/delete")
    public String SmartRoundDelete(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(coaAdmDTO.getId());
            wBRoundMstDTO.setModIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBFASmartRoundService.deleteRound(wBRoundMstDTO));
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
