package com.kap.mngwserc.controller.wb.wbh;

import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbh.*;
import com.kap.service.COCodeService;
import com.kap.service.WBHACalibrationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
