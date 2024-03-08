package com.kap.mngwserc.controller.wb.wbe;



import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyMstInsertDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanyTrnsfDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.service.COCodeService;
import com.kap.service.WBEBCarbonCompanyService;
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
 * 탄소배출저감 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBEBCarbonCompanyController.java
 * @Description		: 탄소배출저감 신청관리를 위한 Controller
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
    public final WBEBCarbonCompanyService wBEBCarbonCompanyService;
    public final COCodeService cOCodeService;

    /**
     *  탄소배출저감 신청 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getCarbonList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));

        cdDtlList.add("ROUND_CD");
        modelMap.addAttribute("classCityTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        modelMap.addAttribute("rtnYear", wBEBCarbonCompanyService.selectYearDtl(wBEBCarbonCompanySearchDTO));
        modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCarbonCompanyList(wBEBCarbonCompanySearchDTO));


        return "mngwserc/wb/wbe/WBEBCarbonCompanyList.admin";
    }

    /**
     * 탄소배출저감 신청 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCarbonListPageAjax(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCarbonCompanyList(wBEBCarbonCompanySearchDTO));
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
     * 탄소배출저감 회차 등록 페이지
     */
    @RequestMapping(value = "/write")
    public String getCarbonWritePage(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap) throws Exception
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

            modelMap.addAttribute("rtnYear", wBEBCarbonCompanyService.selectYearDtl(wBEBCarbonCompanySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbe/WBEBCarbonCompanyWrite.admin";
    }

    /**
     * 탄소배출저감 신청 등록
     */
    @PostMapping(value="/insert")
    public String carbonCompanyInsert(@Valid @RequestBody WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.carbonCompanyInsert(wBEBCarbonCompanyMstInsertDTO, request));
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
     * 탄소배출저감 신청 수정 페이지
     */
    @RequestMapping(value = "/writeUpdate")
    public String getCarbonUpdateWritePage(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap) throws Exception
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

            /*modelMap.addAttribute("rtnYear", wBEBCarbonCompanyService.selectYearDtl(wBEBCarbonCompanySearchDTO));*/

            modelMap.addAttribute("rtnEpisd", wBEBCarbonCompanyService.selectYearDtl(wBEBCarbonCompanySearchDTO));

            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.selectCarbonCompanyDtl(wBEBCarbonCompanySearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbe/WBEBCarbonCompanyUpdateWrite.admin";
    }

    /**
     * 탄소배출저감 신청 수정
     */
    @PostMapping(value="/update")
    public String carbonCompanyUpdate(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.carbonCompanyUpdate(wBEBCarbonCompanyMstInsertDTO, request));
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
     * 탄소배출저감 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/getEpisdSelect")
    @ResponseBody
    public WBEBCarbonCompanySearchDTO getEpisdSelect(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            if(wBEBCarbonCompanySearchDTO.getYearSearch() != null && !wBEBCarbonCompanySearchDTO.getYearSearch().equals(""))
            {
                wBEBCarbonCompanySearchDTO.setYear(Integer.parseInt(wBEBCarbonCompanySearchDTO.getYearSearch()));
            }
            wBEBCarbonCompanySearchDTO = wBEBCarbonCompanyService.getYearSelect(wBEBCarbonCompanySearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBEBCarbonCompanySearchDTO;
    }

    /**
     * 회차 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String carbonCompanyDeleteList(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.carbonCompanyDeleteList(wBEBCarbonCompanySearchDTO));
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
    public String getTrnsfList(WBEBCarbonCompanyTrnsfDTO wBEBCarbonCompanyTrnsfDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBEBCarbonCompanyService.getTrnsfList(wBEBCarbonCompanyTrnsfDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbe/WBEBCarbonCompanyTrnsfAjax";
    }

    /**
     * 신청 진행단계 확인
     */
    @PostMapping(value="/getRsumePbsnCnt")
    public String getRsumePbsnCnt(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.getRsumePbsnCnt(wBEBCarbonCompanySearchDTO));
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
    public void selectUserListExcel(WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO ,HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBEBCarbonCompanySearchDTO.setExcelYn("Y");
            wBEBCarbonCompanyService.excelDownload(wBEBCarbonCompanyService.selectCarbonCompanyList(wBEBCarbonCompanySearchDTO), response);
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
    public String getInsertBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.getInsertBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO));
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
    public String getInsertSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.getInsertSbrdnBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO));
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
    public String getBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.getBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO));
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
    public String getSbrdnBsnmNoCnt(WBEBCarbonCompanyMstInsertDTO wBEBCarbonCompanyMstInsertDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBEBCarbonCompanyService.getSbrdnBsnmNoCnt(wBEBCarbonCompanyMstInsertDTO));
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
    public WBEBCarbonCompanySearchDTO updAdmMemo(@Valid @RequestBody WBEBCarbonCompanySearchDTO wBEBCarbonCompanySearchDTO, HttpServletRequest request) throws Exception
    {
        try {
            wBEBCarbonCompanySearchDTO.setRespCnt(wBEBCarbonCompanyService.updAdmMemo(wBEBCarbonCompanySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBEBCarbonCompanySearchDTO;
    }
}
