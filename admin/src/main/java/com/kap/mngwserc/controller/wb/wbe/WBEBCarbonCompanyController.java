package com.kap.mngwserc.controller.wb.wbe;


import com.kap.core.dto.WBRoundMstDTO;
import com.kap.service.COCodeService;
import com.kap.service.WBEACarbonListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 탄소배출저감 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBEBCarbonCompanyController.java
 * @Description		: 탄소배출저감 신청업체관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/wb/wbeb")

public class WBEBCarbonCompanyController {

    /** 서비스 **/
    public final WBEACarbonListService wBEACarbonListService;
    public final COCodeService cOCodeService;

    /**
     *  탄소배출저감 회차 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_STATE_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        return "mngwserc/wb/wbe/WBEBCarbonCompanyList.admin";
    }

    /**
     * 탄소배출저감 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            System.out.println("test1213213");
            /*modelMap.addAttribute("rtnData", wBEACarbonListService.selectCarbonList(wBRoundMstDTO));*/
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbe/WBEBCarbonCompanyListAjax";
    }

    /**
     * 탄소배출저감 회차 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {
            modelMap.addAttribute("rtnYear", wBEACarbonListService.selectYearDtl(wBRoundMstDTO));

            if(wBRoundMstDTO.getDetailsKey() != null){
                modelMap.addAttribute("rtnInfo", wBEACarbonListService.selectCarbonDtl(wBRoundMstDTO));
            }

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }*/
        return "mngwserc/wb/wbe/WBEACarbonWrite.admin";
    }

    /**
     * 탄소배출저감 회차 등록
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String carbonInsert(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setRegId(coaAdmDTO.getId());
            wBRoundMstDTO.setRegIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBEACarbonListService.insertCarbon(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/
        return "jsonView";
    }

    /**
     * 탄소배출저감 회차 수정
     */
    @PostMapping(value="/update")
    public String carbonUpdate(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(coaAdmDTO.getId());
            wBRoundMstDTO.setModIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBEACarbonListService.updateCarbon(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/
        return "jsonView";
    }

    /**
     * 탄소배출저감 회차 삭제
     */
    @PostMapping(value="/delete")
    public String carbonDelete(WBRoundMstDTO wBRoundMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        /*try
        {
            COAAdmDTO coaAdmDTO = (COAAdmDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBRoundMstDTO.setModId(coaAdmDTO.getId());
            wBRoundMstDTO.setModIp(coaAdmDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBEACarbonListService.deleteCarbon(wBRoundMstDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/
        return "jsonView";
    }
}
