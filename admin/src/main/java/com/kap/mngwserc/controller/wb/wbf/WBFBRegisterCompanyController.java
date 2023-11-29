package com.kap.mngwserc.controller.wb.wbf;

import com.kap.core.dto.COAAdmDTO;
import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBCompanyDetailMstDTO;
import com.kap.core.dto.wb.WBRoundMstDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterDTO;
import com.kap.core.dto.wb.wbf.WBFBRegisterSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBFBRegisterCompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_STATE_TYPE"); // 신청 진행상태
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("optYearList", wBFBRegisterCompanyService.getOptYearList(wBFBRegisterSearchDTO));
            modelMap.addAttribute("optEpisdList", wBFBRegisterCompanyService.getOptEpisdList(wBFBRegisterSearchDTO));
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
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();

            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("BGN_REG_INF");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            wBFBRegisterSearchDTO.setBsnCd("INQ07006");
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
            wBFBRegisterDTO.setRegName( coaAdmDTO.getName() );
            wBFBRegisterDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBFBRegisterDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBFBRegisterDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBFBRegisterDTO.setModId( coaAdmDTO.getId() );
            wBFBRegisterDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBFBRegisterDTO.setBsnCd("INQ07006"); /* 스마트 공장 */

            COCodeDTO cOCodeDTO = new COCodeDTO();
            /* 스마트 공장 구축 - 신청 코드 값*/
            cOCodeDTO.setCd("PRO_TYPE02");
            String rsumeSttsCd = cOCodeService.getCdIdList(cOCodeDTO).get(0).getCd();
            wBFBRegisterDTO.setRsumeSttsCd(rsumeSttsCd);

            /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd(rsumeSttsCd + "_01");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBFBRegisterDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 스마트 신청 관리자 최초 상태값 - 미확인 */
            cOCodeDTO.setCd(rsumeSttsCd + "_02");
            getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBFBRegisterDTO.setMngSttsCd(getCode.get(0).getCd());

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
     * 신청부품사 수정
     */
    @GetMapping(value="/edit")
    public String getRegisterCompanyEditPage(WBFBRegisterSearchDTO wBFBRegisterSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
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

            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbfb/WBEBRegisterCompanyEdit.admin";
    }
}
