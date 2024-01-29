package com.kap.mngwserc.controller.wb.wbj;

import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.WBRoundMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBJARoundListService;
import io.swagger.v3.oas.annotations.Operation;
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
 * 자동차부품산업대상 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBJRoundController.java
 * @Description		: 자동차부품산업대상 회차관리를 위한 Controller
 * @author 오병호
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		오병호				   최초 생성
 * </pre>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbja")
public class WBJRoundController {

    /** 서비스 **/
    public final COCodeService cOCodeService;
    public final WBJARoundListService wBJARoundListService;

    /**
     *  자동차부품산업대상 회차관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getRoundList(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");
        cdDtlList.add("SYSTEM_HOUR");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("rtnData", wBJARoundListService.selectRoundList(wBRoundMstSearchDTO));

        return "mngwserc/wb/wbj/WBJRoundList.admin";
    }

    /**
     * 자동차부품산업대상 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getRoundListPageAjax(WBRoundMstSearchDTO wBRoundMstSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBJARoundListService.selectRoundList(wBRoundMstSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            /*throw new Exception(e.getMessage());*/
            e.printStackTrace();
        }
        return "mngwserc/wb/wbj/WBJARoundListAjax";
    }

    /**
     * 자동차부품산업대상 회차 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getRoundWritePage(WBRoundMstSearchDTO wBRoundMstSearchDTO, WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap
            ,@Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MNGCNSLT_DIS");
            cdDtlList.add("MNGCNSLT_REW");
            cdDtlList.add("SYSTEM_HOUR");

            modelMap.addAttribute("classTypeList", cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
            modelMap.addAttribute("cdList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wBJARoundListService.selectYearDtl(wBRoundMstSearchDTO));

            if(wBRoundMstSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBJARoundListService.selectRoundDtl(wBRoundMstSearchDTO));
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
        return "mngwserc/wb/wbj/WBJRoundWrite.admin";
    }

    /**
     * 자동차부품산업대상 회차 등록
     */
    @PostMapping(value="/insert")
    @ResponseBody
    public WBRoundMstDTO roundInsert(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            wBJARoundListService.insertRound(wBRoundMstDTO, request);
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
     * 자동차부품산업대상 회차 수정
     */
    @PostMapping(value="/update")
    @ResponseBody
    public WBRoundMstDTO roundUpdate(@Valid @RequestBody WBRoundMstDTO wBRoundMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(cOUserDetailsDTO.getId());
            wBRoundMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            wBJARoundListService.updateRound(wBRoundMstDTO, request);
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
     * 자동차부품산업대상 회차 삭제
     */
    @Operation(summary = "자동차부품산업대상 회사 삭제", tags = "회차", description = "상생 마스터, 상세, 보기 상세 삭제")
    @PostMapping(value="/delete")
    @ResponseBody
    public WBRoundMstDTO roundDelete(WBRoundMstDTO WBRoundMstDTO) throws Exception
    {
        try
        {
            WBRoundMstDTO.setRespCnt( wBJARoundListService.deleteRound(WBRoundMstDTO) );
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return WBRoundMstDTO;
    }

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getEpisdCnt")
    public String getEpisdAjax(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBRoundMstDTO.setBsnCd("BSN10");
            modelMap.addAttribute("optEpisdCnt", wBJARoundListService.roundCnt(wBRoundMstDTO));
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
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getRsumeCnt")
    public String getRsumeAjax(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap) throws Exception
    {
        try {
            modelMap.addAttribute("optEpisdCnt", wBJARoundListService.episdCnt(wBRoundMstDTO));
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
