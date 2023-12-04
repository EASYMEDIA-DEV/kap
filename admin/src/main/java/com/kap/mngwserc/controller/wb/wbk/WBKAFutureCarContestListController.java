package com.kap.mngwserc.controller.wb.wbk;


import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestMstDTO;
import com.kap.core.dto.wb.wbk.WBFutureCarContestSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBKAFutureCarContestListService;
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
 * @ClassName		: WBKAFutureCarContestListController.java
 * @Description		: 미래차공모전 회차관리를 위한 Controller
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
 *
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbka")
public class WBKAFutureCarContestListController {

    /** 1서비스 **/
    public final WBKAFutureCarContestListService wbkaFutureCarContestListService;
    public final COCodeService cOCodeService;

    /**
     *  미래차공모전 회차 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getFutureCarContestList(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("rtnData", wbkaFutureCarContestListService.selectFutureCarContestList(wBFutureCarContestSearchDTO));

        System.out.println("rtnData ===" + modelMap.getAttribute("rtnData") );

        return "mngwserc/wb/wbk/WBKAFutureCarContestList.admin";
    }

    /**
     * 탄소배출저감 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getFutureCarContestListPageAjax( WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbkaFutureCarContestListService.selectFutureCarContestList(wBFutureCarContestSearchDTO));

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
        return "mngwserc/wb/wbk/WBKAFutureCarContestListAjax";
    }

    /**
     * 탄소배출저감 회차 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBFutureCarContestSearchDTO wBFutureCarContestSearchDTO, ModelMap modelMap
            ,@Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("WBK_AWD");
            cdDtlList.add("WBK_PTN");

            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wbkaFutureCarContestListService.selectYearDtl(wBFutureCarContestSearchDTO));



            if(wBFutureCarContestSearchDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wbkaFutureCarContestListService.selectFutureCarContestDtl(wBFutureCarContestSearchDTO));
            }

            System.out.printf("rtnInfo=====" +  modelMap.getAttribute("rtnInfo"));


        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbk/WBKAFutureCarContestWrite.admin";
    }

    /**
     * 탄소배출저감 회차 등록
     */
    @PostMapping(value="/insert")
    @ResponseBody
    public WBFutureCarContestMstDTO FutureCarContestInsert(@Valid @RequestBody WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            /*COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setRegId(coaAdmDTO.getId());
            wBFutureCarContestMstDTO.setRegIp(coaAdmDTO.getLoginIp());*/

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setRegId(cOUserDetailsDTO.getId());
            wBFutureCarContestMstDTO.setRegIp(cOUserDetailsDTO.getLoginIp());

            wbkaFutureCarContestListService.insertFuturCarContest(wBFutureCarContestMstDTO, request);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBFutureCarContestMstDTO;
    }

    /**
     * 탄소배출저감 회차 수정
     */
    @PostMapping(value="/update")
    @ResponseBody
    public WBFutureCarContestMstDTO FutureCarContestUpdate(@Valid @RequestBody WBFutureCarContestMstDTO wBFutureCarContestMstDTO, HttpServletRequest request) throws Exception
    {
        try
        {
            /*COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setModId(coaAdmDTO.getId());
            wBFutureCarContestMstDTO.setModIp(coaAdmDTO.getLoginIp());*/

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setModId(cOUserDetailsDTO.getId());
            wBFutureCarContestMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            wbkaFutureCarContestListService.updateFutureCarContest(wBFutureCarContestMstDTO, request);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return wBFutureCarContestMstDTO;
    }

    /**
     * 탄소배출저감 회차 삭제
     */
    @PostMapping(value="/delete")
    public String carbonDelete(WBFutureCarContestMstDTO wBFutureCarContestMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {

            /*COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setModId(coaAdmDTO.getId());
            wBFutureCarContestMstDTO.setModIp(coaAdmDTO.getLoginIp());*/

            COUserDetailsDTO cOUserDetailsDTO = COUserDetailsHelperService.getAuthenticatedUser();
            wBFutureCarContestMstDTO.setModId(cOUserDetailsDTO.getId());
            wBFutureCarContestMstDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wbkaFutureCarContestListService.deleteFutureCarContest(wBFutureCarContestMstDTO));
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
