package com.kap.mngwserc.controller.wb.wbc;



import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbc.WBCBSecuritySearchDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityTrnsfDTO;
import com.kap.service.COCodeService;
import com.kap.service.WBCBSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;


/**
 * <pre>
 * 보안환경구축 신청관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBCBSecurityController.java
 * @Description		: 보안환경구축 신청관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/wb/wbcb")
public class WBCBSecurityController {

    /** 서비스 **/
    public final WBCBSecurityService wBCBSecurityService;
    public final COCodeService cOCodeService;

    /**
     *  보안환경구축 신청 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

        cdDtlList.add("ROUND_CD");
        modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        modelMap.addAttribute("rtnYear", wBCBSecurityService.selectYearDtl(wBCBSecuritySearchDTO));
        modelMap.addAttribute("rtnData", wBCBSecurityService.selectCarbonCompanyList(wBCBSecuritySearchDTO));


        return "mngwserc/wb/wbc/WBCBSecurityList.admin";
    }

    /**
     * 보안환경구축 신청 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBCBSecurityService.selectCarbonCompanyList(wBCBSecuritySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbc/WBCBSecurityListAjax";
    }

    /**
     * 보안환경구축 회차 등록 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("ROUND_CD");
            modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wBCBSecurityService.selectYearDtl(wBCBSecuritySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbc/WBCBSecurityWrite.admin";
    }

    /**
     * 보안환경구축 신청 등록
     */
    @PostMapping(value="/insert")
    public String carbonCompanyInsert(@Valid @RequestBody WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.carbonCompanyInsert(wBCBSecurityMstInsertDTO, request));
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
     * 보안환경구축 신청 수정 페이지
     */
    @RequestMapping(value = "/writeUpdate")
    public String getCarbonUpdateWritePage(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("ROUND_CD");
            modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("PRO_TYPE");
            modelMap.addAttribute("proTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "5"));

            /*modelMap.addAttribute("rtnYear", wBCBSecurityService.selectYearDtl(wBCBSecuritySearchDTO));*/

            modelMap.addAttribute("rtnEpisd", wBCBSecurityService.selectYearDtl(wBCBSecuritySearchDTO));

            modelMap.addAttribute("rtnData", wBCBSecurityService.selectCarbonCompanyDtl(wBCBSecuritySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbc/WBCBSecurityUpdateWrite.admin";
    }

    /**
     * 보안환경구축 신청 수정
     */
    @PostMapping(value="/update")
    public String carbonCompanyUpdate(WBCBSecurityMstInsertDTO wBCBSecurityMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.carbonCompanyUpdate(wBCBSecurityMstInsertDTO, request));
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
     * 보안환경구축 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/getEpisdSelect")
    @ResponseBody
    public WBCBSecuritySearchDTO getEpisdSelect( WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(wBCBSecuritySearchDTO.getYearSearch() != null && !wBCBSecuritySearchDTO.getYearSearch().equals(""))
            {
                wBCBSecuritySearchDTO.setYear(Integer.parseInt(wBCBSecuritySearchDTO.getYearSearch()));
            }
            wBCBSecuritySearchDTO = wBCBSecurityService.getYearSelect(wBCBSecuritySearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBCBSecuritySearchDTO;
    }

    /**
     * 회차 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String carbonCompanyDeleteList(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.carbonCompanyDeleteList(wBCBSecuritySearchDTO));
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
     * 참여 이관 로그를 가져온다.
     */
    @PostMapping(value="/log-list.ajax")
    public String getTrnsfList(WBCBSecurityTrnsfDTO wBCBSecurityTrnsfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBCBSecurityService.getTrnsfList(wBCBSecurityTrnsfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbc/WBCBSecurityTrnsfAjax";
    }

    /**
     * 신청 진행단계 확인
     */
    @PostMapping(value="/getRsumePbsnCnt")
    public String getRsumePbsnCnt(WBCBSecuritySearchDTO wBCBSecuritySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBCBSecurityService.getRsumePbsnCnt(wBCBSecuritySearchDTO));
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


    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(WBCBSecuritySearchDTO wBCBSecuritySearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBCBSecurityService.excelDownload(wBCBSecurityService.selectCarbonCompanyList(wBCBSecuritySearchDTO), response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }
}
