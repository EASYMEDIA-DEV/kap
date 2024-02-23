package com.kap.mngwserc.controller.wb.wbb;

import com.kap.core.dto.wb.wba.WBAManageSearchDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbb.WBBACompanyDTO;
import com.kap.core.dto.wb.wbb.WBBACompanySearchDTO;
import com.kap.core.dto.wb.wbb.WBBATransDTO;
import com.kap.core.dto.wb.wbc.WBCBSecurityMstInsertDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.service.COCodeService;
import com.kap.service.WBAManagementService;
import com.kap.service.WBBBCompanyService;
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
import java.util.List;

/**
 * <pre>
 * 공통 회차관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBBBCompanyController.java
 * @Description		: 공통 부품사관리를 위한 Controller
 * @author 김태훈
 * @since 2023.11.27
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.27		김태훈				   최초 생성
 * </pre>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wbb/wbbb/{bsnCd}")
public class WBBBCompanyController {

    /**
     * 서비스
     **/
    public final COCodeService cOCodeService;
    public final WBBBCompanyService wbbbCompanyService;
    public final WBAManagementService wbaManagementService;

    /**
     * 공통 부품사관리 목록으로 이동한다.
     */
    @GetMapping(value = "/list")
    public String getCompanyList(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");

        modelMap.addAttribute("progressList", wbbbCompanyService.selectProgressList(wbbCompanySearchDTO));
        modelMap.addAttribute("rtnYear", wbbbCompanyService.selectYearDtl(wbbCompanySearchDTO));
        modelMap.addAttribute("rtnData", wbbCompanySearchDTO);
        modelMap.addAttribute("bsnCd", wbbCompanySearchDTO.getBsnCd());

        return "mngwserc/wb/wbb/WBBBCompanyList.admin";
    }

    /**
     * 공통 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getCompanyListPageAjax(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", wbbbCompanyService.selectCompanyList(wbbCompanySearchDTO));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbb/WBBBCompanyListAjax";
    }

    /**
     * 공통 부품사관리 상세 페이지
     */
    @RequestMapping(value = "/write")
    public String getCompanyWritePage(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap
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
            modelMap.addAttribute("rtnYear", wbbbCompanyService.selectYearDtl(wbbCompanySearchDTO));
            modelMap.addAttribute("bsnCd", wbbCompanySearchDTO.getBsnCd());

            WBAManageSearchDTO wbaManageSearchDTO = new WBAManageSearchDTO();
            wbaManageSearchDTO.setBsnCd(wbbCompanySearchDTO.getBsnCd());
            wbaManageSearchDTO.setSearchOrd(1);

            if (wbbCompanySearchDTO.getDetailsKey() != null)
            {
                WBBAApplyMstDTO wbbApplyMstDTO = new WBBAApplyMstDTO();
                wbbApplyMstDTO = wbbbCompanyService.selectCompanyDtl(wbbCompanySearchDTO);
                modelMap.addAttribute("rtnInfo", wbbApplyMstDTO);

                modelMap.addAttribute("userInfo", wbbbCompanyService.selectCompanyUserDtl(wbbCompanySearchDTO));
                WBBACompanyDTO wbbCompany = new WBBACompanyDTO();
                wbbCompanySearchDTO.setYear(String.valueOf(wbbApplyMstDTO.getYear()));
                modelMap.addAttribute("rtnEpisdList", wbbbCompanyService.getEplisdsList(wbbCompanySearchDTO));
            } else {
                modelMap.addAttribute("optionList",wbaManagementService.selectOptnList(wbaManageSearchDTO));
            }

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
        }
        return "mngwserc/wb/wbb/WBBBCompanyWrite.admin";
    }

    @PostMapping(value="/getEplisds")
    @ResponseBody
    public List<WBBACompanySearchDTO> getEplisds(WBBACompanySearchDTO wbbCompanySearchDTO) throws Exception {

        List<WBBACompanySearchDTO> roundList;

        try {

            roundList = wbbbCompanyService.getEplisdsList(wbbCompanySearchDTO);

        } catch (Exception e) {

            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return roundList;
    }

    /**
     * 참여 이관 로그를 가져온다.
     */
    @PostMapping(value="/log-list.ajax")
    public String getTrnsfList(WBBATransDTO wbbTransDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wbbbCompanyService.getTrnsfList(wbbTransDTO));
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
     * 신청자를 등록한다.
     */
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public String insert(WBBACompanyDTO wbbCompanyDTO, WBBAApplyMstDTO wbbApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wbbbCompanyService.insert(wbbCompanyDTO, wbbApplyMstDTO, request);
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
    public String update(WBBACompanyDTO wbbCompanyDTO, WBBAApplyMstDTO wbbApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            int respCnt = wbbbCompanyService.update(wbbCompanyDTO, wbbApplyMstDTO, request);
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
     * 신청자를 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String delete(WBBACompanySearchDTO wbbCompanySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            int respCnt = wbbbCompanyService.delete(wbbCompanySearchDTO);
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

    @GetMapping(value = "/excel-down")
    public void selectUserListExcel(WBBACompanySearchDTO wbbCompanySearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wbbbCompanyService.excelDownload(wbbbCompanyService.selectCompanyList(wbbCompanySearchDTO), response);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getBsnmNoCnt")
    public String getBsnmNoCnt(WBBAApplyMstDTO wBBAApplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wbbbCompanyService.getBsnmNoCnt(wBBAApplyMstDTO));
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
    public WBBACompanySearchDTO updAdmMemo(@Valid @RequestBody WBBACompanySearchDTO wBBACompanySearchDTO, HttpServletRequest request) throws Exception
    {
        try {

            wBBACompanySearchDTO.setRespCnt(wbbbCompanyService.updAdmMemo(wBBACompanySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return wBBACompanySearchDTO;
    }
}
