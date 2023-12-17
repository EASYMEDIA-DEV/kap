package com.kap.mngwserc.controller.wb.wbh;

import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbh.*;
import com.kap.service.COCodeService;
import com.kap.service.WBHACalibrationService;
import io.swagger.v3.oas.annotations.Parameter;
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
 * 신청업체관리 위한 Controller
 * </pre>
 *
 * @ClassName		: WBHACalibrationController.java
 * @Description		: 신청업체관리 위한 Controller
 * @author 김태훈
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wbh/wbha")
public class WBHACalibrationController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBHACalibrationService wbhaCalibrationService;

    /**
     *  시험계측장비 신청 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getExamList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_TYPE");
        cdDtlList.add("TEC_GUIDE_INDUS");
        cdDtlList.add("MNGCNSLT_APP_AREA");
        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
        modelMap.addAttribute("classList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        modelMap.addAttribute("rtnYear", wbhaCalibrationService.selectYearDtl(wBHACalibrationSearchDTO));
        modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCalibrationList(wBHACalibrationSearchDTO));

        modelMap.addAttribute("rtnValid", wbhaCalibrationService.selectExamValid(wBHACalibrationSearchDTO));

        return "mngwserc/wb/wbh/WBHACalibrationList.admin";
    }

    /**
     * 시험계측장비 신청 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getExamListPageAjax(WBHACalibrationSearchDTO wBHACalibrationSearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCalibrationList(wBHACalibrationSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbh/WBHACalibrationListAjax";
    }

    /**
     * 시험계측장비 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String examDeleteList(WBHACalibrationSearchDTO wBHACalibrationSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wbhaCalibrationService.carbonCompanyDeleteList(wBHACalibrationSearchDTO));
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
     * 시험계측장비 신청 옵션 수정
     */
    @PostMapping(value="/validUpdate")
    public String validUpdate(@Valid @RequestBody WBHAValidDTO wBHAValidDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wbhaCalibrationService.examValidUpdate(wBHAValidDTO, request));
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
     * 시험계측장비 엑셀
     */
    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(WBHACalibrationSearchDTO wBHACalibrationSearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wbhaCalibrationService.excelDownload(wbhaCalibrationService.selectCalibrationList(wBHACalibrationSearchDTO), response);
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
     * 공통 부품사관리 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCompanyWritePage(WBHACalibrationSearchDTO wbhaCalibrationSearchDTO, ModelMap modelMap
            , @Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("MEM_CD");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("PRO_TYPE");

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnInfo", wbhaCalibrationSearchDTO);

            if (detailsKey != null) {
                modelMap.addAttribute("rtnData", wbhaCalibrationService.selectCompanyDtl(wbhaCalibrationSearchDTO));
                modelMap.addAttribute("userInfo", wbhaCalibrationService.selectCompanyUserDtl(wbhaCalibrationSearchDTO));
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbh/WBHACalibrationWrite.admin";
    }

    /**
     * 부품사 컨설팅 사업 리스트를 가져온다.
     */
    @PostMapping(value="/consultinglist.ajax")
    public String consultinglist(WBHAConsultingDTO wbaConsultingDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbhaCalibrationService.getConsultingList(wbaConsultingDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbh/WBHAConsultingAjax";
    }

    /**
     * 신청자를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insert(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wbhaCalibrationService.insert(wbhaCompanyDTO, wbhaApplyMstDTO, request);
            modelMap.addAttribute("respCnt", respCnt);
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
     * 신청자를 수정한다.
     */
    @RequestMapping(value="/update", method= RequestMethod.POST)
    public String update(WBHACompanyDTO wbhaCompanyDTO, WBHAApplyMstDTO wbhaApplyMstDTO, WBHAMsEuipmentDTO wbhaMsEuipmentDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wbhaCalibrationService.update(wbhaCompanyDTO, wbhaApplyMstDTO, wbhaMsEuipmentDTO, request);
            modelMap.addAttribute("respCnt", respCnt);
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
    public String getTrnsfList(WBBATransDTO wbbTransDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbhaCalibrationService.getTrnsfList(wbbTransDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbb/WBBBCompanyTrnsfAjax";
    }
}
