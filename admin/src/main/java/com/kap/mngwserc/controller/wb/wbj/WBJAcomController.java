package com.kap.mngwserc.controller.wb.wbj;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.COUserDetailsDTO;
import com.kap.core.dto.wb.WBPartCompanyDTO;
import com.kap.core.dto.wb.wbj.WBJBAcomChangeDTO;
import com.kap.core.dto.wb.wbj.WBJAcomSearchDTO;
import com.kap.core.dto.wb.wbj.WBJAcomDTO;
import com.kap.core.dto.wb.wbj.WBJBAcomMstDTO;
import com.kap.service.*;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;

/**
 * <pre>
 * 자동차부품산업대상 신청업체관리를 위한 Controller
 * </pre>
 *
 * @ClassName		: WBJAcomController.java
 * @Description		: 자동차부품산업대상 신청업체관리를 위한 Controller
 * @author 오병호
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		오병호				   최초 생성
 * </pre>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/wb/wbjb")
public class WBJAcomController {

    /** 서비스 **/
    public final COCodeService cOCodeService;
    public final WBJBAcomListService wBJBAcomListService;
    /* 공통 코드 service DI */
    private final WBPartCompanyService wBPartCompanyService;
    private final WBFBRegisterCompanyService wBFBRegisterCompanyService;

    /**
     *  신청업체관리 회차관리 목록으로 이동한다.
     */
    @GetMapping(value="/list")
    public String getAcomList(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {

        // 공통코드 배열 셋팅
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
        cdDtlList.add("STATE_TYPE");
        cdDtlList.add("MNGCNSLT_DIS");
        cdDtlList.add("MNGCNSLT_REW");
        cdDtlList.add("COMPANY_TYPE");
        cdDtlList.add("PRO_TYPE");

        modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));
        modelMap.addAttribute("typeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "3"));
        modelMap.addAttribute("resultList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "5"));
        modelMap.addAttribute("rtnData", wBJBAcomListService.getRegisterCompanyList(wBJAcomSearchDTO));
        wBJAcomSearchDTO.setBsnCd("BSN10");
        modelMap.addAttribute("optYearList", wBJBAcomListService.getOptYearList(wBJAcomSearchDTO));

        return "mngwserc/wb/wbj/WBJAcomList.admin";
    }

    /**
     * 신청업체관리 회차 목록을 조회한다.
     */
    @RequestMapping(value = "/select")
    public String getAcomListPageAjax(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            wBJAcomSearchDTO.setBsnCd("BSN10");
            modelMap.addAttribute("rtnData", wBJBAcomListService.getRegisterCompanyList(wBJAcomSearchDTO));

        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            /*throw new Exception(e.getMessage());*/
            e.printStackTrace();
        }
        return "mngwserc/wb/wbj/WBJBAcomListAjax";
    }

    /**
     * 선택 연도 값에 따른
     * 회차 SELECT Ajax
     */
    @PostMapping(value="/getEplisds")
    public String getEpisdListAjax(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBJAcomSearchDTO.setBsnCd("BSN10");
            modelMap.addAttribute("optEpisdList", wBJBAcomListService.getOptEpisdList(wBJAcomSearchDTO));
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
     * 선택 연도 값에 따른
     * 포상 SELECT Ajax
     */
    @PostMapping(value="/getPrizes")
    public String getPrizeListAjax(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap) throws Exception
    {
        try {
            wBJAcomSearchDTO.setBsnCd("BSN10");
            modelMap.addAttribute("optPrizeList", wBJBAcomListService.getOptPrizeList(wBJAcomSearchDTO));
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
     * 신청업체관리 회차 등록
     */
    @GetMapping(value="/write")
    public String getWrite(ModelMap modelMap, WBJAcomSearchDTO wBJAcomSearchDTO
            , @Parameter(required = false) @RequestParam(required = false) String detailsKey) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EXG");
            cdDtlList.add("MNGCNSLT_REW");
            cdDtlList.add("MNGCNSLT_DIS");
            cdDtlList.add("MEM_CD");
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("CO_YEAR_CD");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

            wBJAcomSearchDTO.setBsnCd("BSN10");
            modelMap.addAttribute("optYearList", wBJBAcomListService.getOptYearList(wBJAcomSearchDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbj/WBJAcomWrite.admin";
    }

    /**
     * 부품사회원 / 담당위원 modal 목록 Ajax
     */
    @PostMapping(value = "/setModalData")
    public String selectPartUserListPageAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        wBPartCompanyDTO.setMemCd("CP");
        wBPartCompanyDTO.setExcelYn("N");
        modelMap.addAttribute("rtnData", wBPartCompanyService.selPartCompanyUserList(wBPartCompanyDTO));

        return "mngwserc/wb/WBFBPartUserModalAjax";
    }

    /**
     * 부품사회원 / 담당위원 Detail Ajax
     */
    @PostMapping(value = "/setModalDetail")
    public String selectPartUserDetailAjax(WBPartCompanyDTO wBPartCompanyDTO , ModelMap modelMap ) throws Exception {
        try
        {
            wBPartCompanyDTO.setMemCd("CP");
            modelMap.addAttribute("rtnData", wBPartCompanyService.selPartUserDetail(wBPartCompanyDTO));
            wBPartCompanyDTO.setWorkBsnmNo(((WBPartCompanyDTO)modelMap.getAttribute("rtnData")).getWorkBsnmNo());
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
        return "jsonView";
    }

    @PostMapping(value="/insert")
    public String putRegisterCompany(WBJAcomDTO wBJAcomDTO, ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBJAcomDTO.setRegId( coaAdmDTO.getId() );
            wBJAcomDTO.setRegName( coaAdmDTO.getName() );
            wBJAcomDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBJAcomDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBJAcomDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBJAcomDTO.setModId( coaAdmDTO.getId() );
            wBJAcomDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBJAcomDTO.setBsnCd("BSN10"); /* 자동차부품산업대상 코드 */

            COCodeDTO cOCodeDTO = new COCodeDTO();
            /* 자동차부품산업대상 구축 - 신청 코드 값*/
            cOCodeDTO.setCd("PRO_TYPE05");
            String rsumeSttsCd = cOCodeService.getCdIdList(cOCodeDTO).get(0).getCd();
            wBJAcomDTO.setRsumeSttsCd(rsumeSttsCd);

            /* 자동차부품산업대상 신청자 최초 상태값 - 접수완료 */
            cOCodeDTO.setCd(rsumeSttsCd + "_01");
            List<COCodeDTO> getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBJAcomDTO.setAppctnSttsCd(getCode.get(0).getCd());

            /* 자동차부품산업대상 신청 관리자 최초 상태값 - 미확인 */
            cOCodeDTO.setCd(rsumeSttsCd + "_02");
            getCode = cOCodeService.getCdIdList(cOCodeDTO);
            wBJAcomDTO.setMngSttsCd(getCode.get(0).getCd());

            /* 신청진행상세 진행 정렬 초기 값 */
            wBJAcomDTO.setRsumeOrd(1);


            modelMap.addAttribute("respCnt", wBJBAcomListService.putRegisterCompany(wBJAcomDTO));
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
    public String getAcomEditPage(WBJAcomSearchDTO wBJAcomSearchDTO, WBPartCompanyDTO wBPartCompanyDTO, ModelMap modelMap) throws Exception
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
            wBJAcomSearchDTO.setBsnCd("BSN10");

            WBJBAcomMstDTO wBJBAcomMstDTO;

            wBJAcomSearchDTO.setEditYn("Y");
            wBJBAcomMstDTO = wBJBAcomListService.selectAppctnRsumeDtl(wBJAcomSearchDTO);
            modelMap.addAttribute("rtnAppctnRsume", wBJBAcomMstDTO);
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnInfo", wBJBAcomListService.selectAcomDtl(wBJAcomSearchDTO));
            modelMap.addAttribute("optYearList", wBJBAcomListService.getOptYearList(wBJAcomSearchDTO));
            modelMap.addAttribute("optEpisdList", wBJBAcomListService.getOptEpisdList(wBJAcomSearchDTO));
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
        return "mngwserc/wb/wbj/WBJBAcomEdit.admin";
    }

    @PostMapping(value="/update")
    public String updateAcom(WBJAcomDTO wBJAcomDTO,  WBJBAcomMstDTO wbjbAcomMstDTO, HttpServletRequest request ,ModelMap modelMap) throws Exception
    {
        try {
            COUserDetailsDTO coaAdmDTO = (COUserDetailsDTO) COUserDetailsHelperService.getAuthenticatedUser();
            wBJAcomDTO.setRegId( coaAdmDTO.getId() );
            wBJAcomDTO.setRegName( coaAdmDTO.getName() );
            wBJAcomDTO.setRegDeptCd( coaAdmDTO.getDeptCd() );
            wBJAcomDTO.setRegDeptNm( coaAdmDTO.getDeptNm() );
            wBJAcomDTO.setRegIp( coaAdmDTO.getLoginIp() );
            wBJAcomDTO.setModId( coaAdmDTO.getId() );
            wBJAcomDTO.setModIp( coaAdmDTO.getLoginIp() );

            wBJAcomDTO.setBsnCd("BSN10"); /* 자동차 */

            modelMap.addAttribute("respCnt", wBJBAcomListService.updateAcom(wBJAcomDTO, wbjbAcomMstDTO, request));
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
    public String delete(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            int respCnt = wBJBAcomListService.delete(wBJAcomSearchDTO);
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
    public String getChangeList(WBJBAcomChangeDTO wBJBAcomChangeDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", wBJBAcomListService.getChangeList(wBJBAcomChangeDTO));
        }
        catch (Exception e)
        {
            if (log.isDebugEnabled())
            {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/wb/wbj/WBJBAcomChangeAjax";
    }

    @GetMapping(value = "/excelDown")
    public void selectUserListExcel(WBJAcomSearchDTO wBJAcomSearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            //엑셀 생성
            wBJBAcomListService.excelDownload(wBJBAcomListService.getRegisterCompanyList(wBJAcomSearchDTO), response);
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
    public String getCnt(WBJAcomSearchDTO wBJAcomSearchDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            int respCnt = wBJBAcomListService.getCnt(wBJAcomSearchDTO);
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
}
