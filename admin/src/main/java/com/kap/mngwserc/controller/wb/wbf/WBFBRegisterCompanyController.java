package com.kap.mngwserc.controller.wb.wbf;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.cb.cba.CBATechGuidanceInsertDTO;
import com.kap.core.dto.wb.WBAppctnTrnsfDtlDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbe.WBEBCarbonCompanySearchDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFBRegisterCompanyService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 스마트공장구축 회차 관리 Controller
 * </pre>
 *
 * @ClassName		: WBFASmartRoundController.java
 * @Description		: 스마트공장구축 업체 관리 Controller
 * @author 김동현
 * @since 2023.11.02
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김동현				   최초 생성
 * </pre>
 */
@Tag(name = "스마트공장구축-신청업체관리페이지", description = "신청업체관리 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbfb")
public class WBFBRegisterCompanyController {

    /* 공통 코드 service DI */
    private final WBFBRegisterCompanyService wBFBRegisterCompanyService;

    /* 공통 코드 service DI */
    private final COCodeService cOCodeService;

    /**
     * 신청부품사 목록 페이지
     */
    @GetMapping(value="/list")
    public String getRegisterCompanyListPage(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_TYPE"); // 신청 진행상태
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
            modelMap.addAttribute("optYearList", wBFBRegisterCompanyService.getOptYearList(wBFBRegisterSearchDTO));
            modelMap.addAttribute("rtnData", wBFBRegisterSearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBRegisterCompanyList.admin";
    }

    /**
     * 회차 목록 페이지
     */
    @PostMapping(value="/select")
    public String getRegisterCompanyListAjax(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getRegisterCompanyList(wBFBRegisterSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBRegisterCompanyListAjax";
    }

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getEplisds")
    public String getEpisdListAjax(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
            modelMap.addAttribute("optEpisdList", wBFBRegisterCompanyService.getOptEpisdList(wBFBRegisterSearchDTO));
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
     * 선택 연도/회차 값에 따른
     * 과제명/사업유형 SELECT Ajax
     */
    @PostMapping(value="/getOptns")
    public String getOptnListAjax(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
            // 공통코드 배열 셋팅
            modelMap.addAttribute("optnCategList", wBFBRegisterCompanyService.getOptnList(wBFBRegisterSearchDTO));
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
     * 신청부품사 등록 페이지
     */
    @GetMapping(value="/write")
    public String getRegisterCompanyWritePage(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("BGN_REG_INF");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("optYearList", wBFBRegisterCompanyService.getOptYearList(wBFBRegisterSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBRegisterCompanyWrite.admin";
    }

    @PostMapping(value="/insert")
    public String putRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO, ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterDTO.setRegId( coaAdmDTO.getId() );
            wBFBRegisterDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBFBRegisterDTO.setRegName( coaAdmDTO.getName() );
            wBFBRegisterDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBFBRegisterDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBFBRegisterDTO.setModId( coaAdmDTO.getId() );
            wBFBRegisterDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBFBRegisterDTO.setBsnCd("INQ07006"); /* 스마트 공장 */
            /* 스마트 공장 구축 - 신청 코드 값*/
            wBFBRegisterDTO.setRsumeSttsCd("PRO_TYPE02001");
            /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
            wBFBRegisterDTO.setAppctnSttsCd("PRO_TYPE02001_01_001");
            /* 스마트 신청 관리자 최초 상태값 - 미확인 */
            wBFBRegisterDTO.setMngSttsCd("PRO_TYPE02001_02_001");
            /* 신청진행상세 진행 정렬 초기 값 */
            wBFBRegisterDTO.setRsumeOrd(1);

            modelMap.addAttribute("respCnt", wBFBRegisterCompanyService.putRegisterCompany(wBFBRegisterDTO));
        }catch (Exception e)
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
     * 신청부품사 수정 페이지
     */
    @GetMapping(value="/edit")
    public String getRegisterCompanyEditPage(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap
            ,@Parameter(description = "회차 순번", required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try {

            wBFBRegisterSearchDTO.setBsnCd("INQ07006"); /* 스마트 공장 */
            wBFBRegisterSearchDTO.setAppctnSeq(Integer.parseInt(detailsKey));

            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_STATE_TYPE"); // 신청 진행상태
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("BGN_REG_INF");
            cdDtlList.add("PRO_TYPE"); /* 상생 관련 코드 */
            cdDtlList.add("APRNC_CMPN_CD"); /* 출연기업 */
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("optYearList", wBFBRegisterCompanyService.getOptYearList(wBFBRegisterSearchDTO));
            modelMap.addAttribute("rtnBasicData", wBFBRegisterCompanyService.getRegisterDtl(wBFBRegisterSearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBRegisterCompanyEdit.admin";
    }
    /**
     * 컨설팅 이관 내역을 조회한다.
     */
    @GetMapping(value = "/getTrnsf")
    public String getTrnsf(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getAppctnTrnsfDtl(wBFBRegisterSearchDTO));
            modelMap.addAttribute("wBFBRegisterSearchDTO", wBFBRegisterSearchDTO);
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBFBRegisterCompanyTrsfListAjax";
    }

    /**
     * Edit 페이지 등록 정보 Get Ajax
     *
     * @param wBFBRegisterSearchDTO - appctnSeq == detailKey
     */
    @PostMapping(value = "/getEditInfo")
    public String getEditPageInfoAjax(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {

            wBFBRegisterSearchDTO.setBsnCd("INQ07006"); /* 스마트 공장 */
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getEditInfo(wBFBRegisterSearchDTO));
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
     * Edit 페이지 등록 정보 Get Ajax
     *
     * @param wBFBRegisterSearchDTO - appctnSeq == detailKey
     */
    @PostMapping(value = "/getBsnmNoCheck")
    public String getBsnmNoCheck(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {

            wBFBRegisterSearchDTO.setBsnCd("INQ07006"); /* 스마트 공장 */
            modelMap.addAttribute("rtnData", wBFBRegisterCompanyService.getBsnmNoCheck(wBFBRegisterSearchDTO));
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
     * 신청부품사 수정 기능
     */
    @PostMapping(value="/update")
    public String getRegisterCompanyEditPage(WBFBRegisterDTO wBFBRegisterDTO, ModelMap modelMap) throws Exception
    {
        try {

            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterDTO.setBsnCd("INQ07006"); /* 스마트 공장 */

            wBFBRegisterDTO.setRegId( coaAdmDTO.getId() );
            wBFBRegisterDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBFBRegisterDTO.setRegName( coaAdmDTO.getName() );
            wBFBRegisterDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBFBRegisterDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBFBRegisterDTO.setModId( coaAdmDTO.getId() );
            wBFBRegisterDTO.setModIp( coaAdmDTO.getLoginIp() );

            modelMap.addAttribute("respCnt", wBFBRegisterCompanyService.updRegisterCompany(wBFBRegisterDTO));
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
     * 신청부품사 삭제
     */
    @PostMapping(value="/delete")
    public String DeletedeleteRegisterCompany(WBFBRegisterDTO wBFBRegisterDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            COUserDetailsDTO cOUserDetailsDTO  = COUserDetailsHelperService.getAuthenticatedUser();
            wBFBRegisterDTO.setModId(cOUserDetailsDTO.getId());
            wBFBRegisterDTO.setModIp(cOUserDetailsDTO.getLoginIp());

            modelMap.addAttribute("respCnt", wBFBRegisterCompanyService.deleteRegisterCompany(wBFBRegisterDTO));
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
    public void getListExcel(WBFBRegisterSearchDTO wBFBRegisterSearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
            //엑셀 생성
            wBFBRegisterCompanyService.excelDownload(wBFBRegisterCompanyService.getRegisterCompanyList(wBFBRegisterSearchDTO), response);
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
