package com.kap.mngwserc.controller.wb.wbi;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.core.dto.wb.wbb.WBBAApplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyChangeDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplyMstDTO;
import com.kap.core.dto.wb.wbi.WBIBSupplySearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.COUserDetailsHelperService;
import com.kap.service.WBIBSupplyCompanyService;
import com.kap.service.WBPartCompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 공급망 업체 관리 Controller
 * </pre>
 *
 * @ClassName		: WBFASmartRoundController.java
 * @Description		: 공급망 업체 관리 Controller
 * @author 오병호
 * @since 2023.12.04
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.12.04		오병호				   최초 생성
 * </pre>
 */
@Tag(name = "공급망안정화기금-신청업체관리페이지", description = "신청업체관리 생성,수정,삭제")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbib")
public class WBIBSupplyCompanyController {

    private final WBIBSupplyCompanyService wBIBSupplyCompanyService;


    /* 공통 코드 service DI */
    private final WBPartCompanyService wBPartCompanyService;
    private final COCodeService cOCodeService;

    /**
     * 신청부품사 목록 페이지
     */
    @GetMapping(value="/list")
    public String getSupplyCompanyListPage(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_TYPE06"); // 신청 진행상태
            wBIBSupplySearchDTO.setBsnCd("BSN09");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            modelMap.addAttribute("optYearList", wBIBSupplyCompanyService.getOptYearList(wBIBSupplySearchDTO));
            modelMap.addAttribute("optEpisdList", wBIBSupplyCompanyService.getOptEpisdList(wBIBSupplySearchDTO));
            modelMap.addAttribute("rtnData", wBIBSupplySearchDTO);

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbi/WBIBSupplyCompanyList.admin";
    }

    /**
     * 회차 목록 페이지
     */
    @PostMapping(value="/select")
    public String getSupplyCompanyListAjax(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBIBSupplySearchDTO.setBsnCd("BSN09");
            modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.getSupplyCompanyList(wBIBSupplySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbi/WBIBSupplyCompanyListAjax";
    }

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getEplisds")
    public String getEpisdListAjax(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBIBSupplySearchDTO.setBsnCd("BSN09");
            modelMap.addAttribute("optEpisdList", wBIBSupplyCompanyService.getOptEpisdList(wBIBSupplySearchDTO));
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
     * episdSeq 값 가져오기
     */
    @PostMapping(value="/getEpisdSeq")
    public String getEpisdSeq(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBIBSupplySearchDTO.setBsnCd("BSN09");
            // 공통코드 배열 셋팅
            modelMap.addAttribute("optnCategList", wBIBSupplyCompanyService.getEpisdSeq(wBIBSupplySearchDTO));
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
    public String getSupplyCompanyWritePage(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
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

            wBIBSupplySearchDTO.setBsnCd("BSN09");
            modelMap.addAttribute("optYearList", wBIBSupplyCompanyService.getOptYearList(wBIBSupplySearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbi/WBIBSupplyCompanyWrite.admin";
    }

    @PostMapping(value="/insert")
    public String putSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBIBSupplyDTO.setRegId( coaAdmDTO.getId() );
            wBIBSupplyDTO.setRegName( coaAdmDTO.getName() );
            wBIBSupplyDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBIBSupplyDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBIBSupplyDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBIBSupplyDTO.setModId( coaAdmDTO.getId() );
            wBIBSupplyDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBIBSupplyDTO.setBsnCd("BSN09"); /* 공급망 */

            COCodeDTO cOCodeDTO = new COCodeDTO();
            /* 스마트 공장 구축 - 신청 코드 값*/
            cOCodeDTO.setCd("PRO_TYPE06");
            String rsumeSttsCd = cOCodeService.getCdIdList(cOCodeDTO).get(0).getCd();
            wBIBSupplyDTO.setRsumeSttsCd(rsumeSttsCd);

            /* 스마트 신청 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd(rsumeSttsCd + "_01");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBIBSupplyDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 스마트 신청 관리자 최초 상태값 - 미확인 */
            cOCodeDTO.setCd(rsumeSttsCd + "_02");
            getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBIBSupplyDTO.setMngSttsCd(getCode.get(0).getCd());

            /* 신청진행상세 진행 정렬 초기 값 */
            wBIBSupplyDTO.setRsumeOrd(1);


            modelMap.addAttribute("respCnt", wBIBSupplyCompanyService.putSupplyCompany(wBIBSupplyDTO, wBIBSupplyMstDTO, request));
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
    public String getSupplyCompanyEditPage(WBIBSupplySearchDTO wBIBSupplySearchDTO, WBPartCompanyDTO wBPartCompanyDTO, ModelMap modelMap) throws Exception
    {
        try {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("PRO_TYPE"); // 신청 진행상태
            // 코드 set
            cdDtlList.add("MEM_CD");
            cdDtlList.add("ED_CITY_CODE");
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            cdDtlList.add("BGN_REG_INF");
            cdDtlList.add("ROUND_CD");
            wBIBSupplySearchDTO.setBsnCd("BSN09");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnInfo", wBIBSupplyCompanyService.selectSupplyDtl(wBIBSupplySearchDTO));
            modelMap.addAttribute("optYearList", wBIBSupplyCompanyService.getOptYearList(wBIBSupplySearchDTO));
            modelMap.addAttribute("optEpisdList", wBIBSupplyCompanyService.getOptEpisdList(wBIBSupplySearchDTO));
            modelMap.addAttribute("rtnDataCompDetail", wBPartCompanyService.selectPartUserCompDetailAjax(wBPartCompanyDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbi/WBIBSupplyCompanyEdit.admin";
    }

    @PostMapping(value="/update")
    public String updateSupplyCompany(WBIBSupplyDTO wBIBSupplyDTO, WBIBSupplyMstDTO wBIBSupplyMstDTO, HttpServletRequest request, ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBIBSupplyDTO.setRegId( coaAdmDTO.getId() );
            wBIBSupplyDTO.setRegName( coaAdmDTO.getName() );
            wBIBSupplyDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBIBSupplyDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBIBSupplyDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBIBSupplyDTO.setModId( coaAdmDTO.getId() );
            wBIBSupplyDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBIBSupplyDTO.setBsnCd("BSN09"); /* 스마트 공장 */

            modelMap.addAttribute("respCnt", wBIBSupplyCompanyService.updateSupplyCompany(wBIBSupplyDTO, wBIBSupplyMstDTO, request));
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
     * 신청자를 삭제한다.
     */
    @RequestMapping(value="/delete")
    public String delete(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            int respCnt = wBIBSupplyCompanyService.delete(wBIBSupplySearchDTO);
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
    @PostMapping(value="/changeList.ajax")
    public String getChangeList(WBIBSupplyChangeDTO wBIBSupplyChangeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBIBSupplyCompanyService.getChangeList(wBIBSupplyChangeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbi/WBIBSupplyChangeAjax";
    }

    @GetMapping(value = "/excelDown")
    public void selectUserListExcel(WBIBSupplySearchDTO wBIBSupplySearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBIBSupplyCompanyService.excelDownload(wBIBSupplyCompanyService.getSupplyCompanyList(wBIBSupplySearchDTO), response);
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
     * 관리자 상태값 미확인 갯수 조회
     */
    @RequestMapping(value="/getCnt")
    public String getCnt(WBIBSupplySearchDTO wBIBSupplySearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            int respCnt = wBIBSupplyCompanyService.getCnt(wBIBSupplySearchDTO);
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
     * 사업자번호 매핑 여부 확인
     */
    @PostMapping(value="/getBsnmNoCnt")
    public String getBsnmNoCnt(WBIBSupplyMstDTO wBIBSupplyMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("respCnt", wBIBSupplyCompanyService.getBsnmNoCnt(wBIBSupplyMstDTO));
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
}
