package com.kap.mngwserc.controller.wb.wbg;

import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbg.*;
import com.kap.service.COCodeService;
import com.kap.service.WBGAExamService;
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
 * @ClassName		: WBACalibrationController.java
 * @Description		: 신청업체관리 위한 Controller
 * @author 김대성
 * @since 2023.12.11
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.11		김대성				   최초 생성
 * </pre>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wbg/wbga")
public class WBGAExamController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBGAExamService wBGAExamService;

    /**
     *  시험계측장비 신청 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getExamList(WBGAExamSearchDTO wBGAExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("PRO_TYPE");
        cdDtlList.add("TEC_GUIDE_INDUS");
        cdDtlList.add("MNGCNSLT_APP_AREA");
        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
        modelMap.addAttribute("classList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

        modelMap.addAttribute("rtnYear", wBGAExamService.selectYearDtl(wBGAExamSearchDTO));
        modelMap.addAttribute("rtnData", wBGAExamService.selectCalibrationList(wBGAExamSearchDTO));

        modelMap.addAttribute("rtnValid", wBGAExamService.selectExamValid(wBGAExamSearchDTO));

        return "mngwserc/wb/wbg/WBGAExamList.admin";
    }

    /**
     * 시험계측장비 신청 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getExamListPageAjax(WBGAExamSearchDTO wBGAExamSearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBGAExamService.selectCalibrationList(wBGAExamSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbg/WBGAExamListAjax";
    }

    /**
     * 시험계측장비 리스트 삭제
     */
    @PostMapping(value="/deleteList")
    public String examDeleteList(WBGAExamSearchDTO wBGAExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBGAExamService.carbonCompanyDeleteList(wBGAExamSearchDTO));
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
    public String validUpdate(@Valid @RequestBody WBGAValidDTO wBGAValidDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBGAExamService.examValidUpdate(wBGAValidDTO, request));
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
    public void selectUserListExcel(WBGAExamSearchDTO wBGAExamSearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBGAExamService.excelDownload(wBGAExamService.selectCalibrationList(wBGAExamSearchDTO), response);
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
    public String getCompanyWritePage(WBGAExamSearchDTO wBGAExamSearchDTO, ModelMap modelMap
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
            modelMap.addAttribute("rtnInfo", wBGAExamSearchDTO);

            if (detailsKey != null) {
                modelMap.addAttribute("rtnData", wBGAExamService.selectCompanyDtl(wBGAExamSearchDTO));
                modelMap.addAttribute("userInfo", wBGAExamService.selectCompanyUserDtl(wBGAExamSearchDTO));
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
        }
        return "mngwserc/wb/wbg/WBGAExamWrite.admin";
    }

    /**
     * 부품사 컨설팅 사업 리스트를 가져온다.
     */
    @PostMapping(value="/consultinglist.ajax")
    public String consultinglist(WBGAConsultingDTO wBGAConsultingDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBGAExamService.getConsultingList(wBGAConsultingDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbg/WBGAExamAjax";
    }

    /**
     * 신청자를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insert(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wBGAExamService.insert(wBGACompanyDTO, wBGAApplyMstDTO, request);
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
    public String update(WBGACompanyDTO wBGACompanyDTO, WBGAApplyMstDTO wBGAApplyMstDTO, WBGAMsEuipmentDTO wBGAMsEuipmentDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wBGAExamService.update(wBGACompanyDTO, wBGAApplyMstDTO, wBGAMsEuipmentDTO, request);
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
            modelMap.addAttribute("rtnData", wBGAExamService.getTrnsfList(wbbTransDTO));
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

    /**
     * 신청 진행단계 확인
     */
    @PostMapping(value="/getRsumePbsnCnt")
    public String getRsumePbsnCnt(WBGAExamSearchDTO wBGAExamSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBGAExamService.getRsumePbsnCnt(wBGAExamSearchDTO));
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
    public WBGAExamSearchDTO updAdmMemo(@Valid @RequestBody WBGAExamSearchDTO wBGAExamSearchDTO, HttpServletRequest request) throws Exception
    {
        try {

            wBGAExamSearchDTO.setRespCnt(wBGAExamService.updAdmMemo(wBGAExamSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBGAExamSearchDTO;
    }
}
