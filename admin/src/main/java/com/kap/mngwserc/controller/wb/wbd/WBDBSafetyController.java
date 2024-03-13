package com.kap.mngwserc.controller.wb.wbd;


import com.kap.core.dto.wb.wbd.WBDBSafetyMstInsertDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetySearchDTO;
import com.kap.core.dto.wb.wbd.WBDBSafetyTrnsfDTO;
import com.kap.service.COCodeService;
import com.kap.service.WBDBSafetyService;
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
 * 안전설비구축 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBDBSafetyController.java
 * @Description		: 안전설비구축 신청관리를 위한 Controller
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
@RequestMapping(value="/mngwserc/wb/wbdb")
public class WBDBSafetyController {

    /** 서비스 **/
    public final WBDBSafetyService wBDBSafetyService;
    public final COCodeService cOCodeService;

    /**
     *  안전설비구축 신청 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
        modelMap.addAttribute("proTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "5"));

        cdDtlList.add("ROUND_CD");
        modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        modelMap.addAttribute("rtnYear", wBDBSafetyService.selectYearDtl(wBDBSafetySearchDTO));
        modelMap.addAttribute("rtnData", wBDBSafetyService.selectCarbonCompanyList(wBDBSafetySearchDTO));


        return "mngwserc/wb/wbd/WBDBSafetyList.admin";
    }

    /**
     * 안전설비구축 신청 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBDBSafetyService.selectCarbonCompanyList(wBDBSafetySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbd/WBDBSafetyListAjax";
    }

    /**
     * 안전설비구축 회차 등록 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("ROUND_CD");
            modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            modelMap.addAttribute("rtnYear", wBDBSafetyService.selectYearDtl(wBDBSafetySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbd/WBDBSafetyWrite.admin";
    }

    /**
     * 안전설비구축 신청 등록
     */
    @PostMapping(value="/insert")
    public String carbonCompanyInsert(@Valid @RequestBody WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.carbonCompanyInsert(wBDBSafetyMstInsertDTO, request));
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
     * 안전설비구축 신청 수정 페이지
     */
    @RequestMapping(value = "/writeUpdate")
    public String getCarbonUpdateWritePage(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("ROUND_CD");
            modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            cdDtlList = new ArrayList<String>();
            cdDtlList.add("PRO_TYPE");
            modelMap.addAttribute("proTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

            /*modelMap.addAttribute("rtnYear", wBDBSafetyService.selectYearDtl(wBDBSafetySearchDTO));*/

            modelMap.addAttribute("rtnEpisd", wBDBSafetyService.selectYearDtl(wBDBSafetySearchDTO));

            modelMap.addAttribute("rtnData", wBDBSafetyService.selectCarbonCompanyDtl(wBDBSafetySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbd/WBDBSafetyUpdateWrite.admin";
    }

    /**
     * 안전설비구축 신청 수정
     */
    @PostMapping(value="/update")
    public String carbonCompanyUpdate(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.carbonCompanyUpdate(wBDBSafetyMstInsertDTO, request));
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
     * 안전설비구축 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/getEpisdSelect")
    @ResponseBody
    public WBDBSafetySearchDTO getEpisdSelect(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(wBDBSafetySearchDTO.getYearSearch() != null && !wBDBSafetySearchDTO.getYearSearch().equals(""))
            {
                wBDBSafetySearchDTO.setYear(Integer.parseInt(wBDBSafetySearchDTO.getYearSearch()));
            }
            wBDBSafetySearchDTO = wBDBSafetyService.getYearSelect(wBDBSafetySearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBDBSafetySearchDTO;
    }

    /**
     * 회차 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String carbonCompanyDeleteList(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.carbonCompanyDeleteList(wBDBSafetySearchDTO));
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
    public String getTrnsfList(WBDBSafetyTrnsfDTO wBDBSafetyTrnsfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBDBSafetyService.getTrnsfList(wBDBSafetyTrnsfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbd/WBDBSafetyTrnsfAjax";
    }

    /**
     * 신청 진행단계 확인
     */
    @PostMapping(value="/getRsumePbsnCnt")
    public String getRsumePbsnCnt(WBDBSafetySearchDTO wBDBSafetySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getRsumePbsnCnt(wBDBSafetySearchDTO));
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
    public void selectUserListExcel(WBDBSafetySearchDTO wBDBSafetySearchDTO ,HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBDBSafetySearchDTO.setExcelYn("Y");
            wBDBSafetyService.excelDownload(wBDBSafetyService.selectCarbonCompanyList(wBDBSafetySearchDTO), response);
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

    /**
     * 등록 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getInsertBsnmNoCnt")
    public String getInsertBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getInsertBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
     * 등록 종된 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getInsertSbrdnBsnmNoCnt")
    public String getInsertSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getInsertSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
     * 수정 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getBsnmNoCnt")
    public String getBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
     * 수정 종된 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getSbrdnBsnmNoCnt")
    public String getSbrdnBsnmNoCnt(WBDBSafetyMstInsertDTO wBDBSafetyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBDBSafetyService.getSbrdnBsnmNoCnt(wBDBSafetyMstInsertDTO));
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
     * Edit 페이지 - 부품사 등록 사업자등록번호 인증
     */
    @PostMapping(value = "/updAdmMemo")
    @ResponseBody
    public WBDBSafetySearchDTO updAdmMemo(@Valid @RequestBody WBDBSafetySearchDTO wBDBSafetySearchDTO, HttpServletRequest request) throws Exception
    {
        try {
            wBDBSafetySearchDTO.setRespCnt(wBDBSafetyService.updAdmMemo(wBDBSafetySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBDBSafetySearchDTO;
    }
}
