package com.kap.mngwserc.controller.cb.cbb;

import com.kap.core.dto.cb.cbb.CBBConsultSuveyRsltListDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultInsertDTO;
import com.kap.core.dto.cb.cbb.CBBManageConsultSearchDTO;
import com.kap.service.CBBManageConsultService;
import com.kap.service.COCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * <pre>
 * 컨설팅 경영컨설팅을 위한 Controller
 * </pre>
 *
 * @ClassName		: CBBManageCosultController.java
 * @Description		: 컨설팅 경영컨설팅을 위한 Controller
 * @author 이옥정
 * @since 2023.11.20
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.20		이옥정				   최초 생성
 * </pre>
 */
@Tag(name = "경영컨설팅 신청부품사 관리", description = "경영컨설팅 신청부품사 관리")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/cb/cbb")
public class CBBManageConsultController {
    /** 서비스 **/
    private final CBBManageConsultService cBBManageConsultService;

    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /**
     * 컨설팅 사업 경영컨설팅 목록 페이지
     */
    @GetMapping(value = "/list")
    public String getManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            //공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            //코드 set
            cdDtlList.add("COMPANY_TYPE");
            cdDtlList.add("MNGCNSLT_APP_AREA");
            cdDtlList.add("MNGCNSLT_STATUS");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", cBBManageConsultSearchDTO);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
        return "mngwserc/cb/cbb/CBBManageConsultList.admin";
    }
    /**
     * 컨설팅 사업 경영컨설팅  상세 페이지
     */
    @GetMapping(value = "/write")
    public String getManageConsultWritePage(CBBManageConsultInsertDTO cBBManageConsultInsertDTO, ModelMap modelMap) throws Exception {
        /*try
        {*/
        ArrayList<String> cdDtlList = new ArrayList<String>();
        // 코드 set
            cdDtlList.add("TEC_GUIDE_INDUS"); // 업종
            cdDtlList.add("TEC_GUIDE_APPCTN"); // 직종 코드
            cdDtlList.add("MEM_CD"); // 직급, 부서 코드
            cdDtlList.add("COMPANY_TYPE"); //부품사 구분 코드
            cdDtlList.add("CO_YEAR_CD"); // 연도 코드 (2021년 ~ 2024년)
            cdDtlList.add("COMPANY_TYPE"); // 스타등급
            cdDtlList.add("APPCTN_RSN_CD"); // 신청사유 코드
            cdDtlList.add("ADDR_CD"); // 소재지 코드
            cdDtlList.add("BF_JDGMT_RSLT"); // 사전심사결과 코드
            cdDtlList.add("GUIDE_TYPE_CD"); // 지도 구분 코드
            cdDtlList.add("GUIDE_PSCND"); // 지도 현황 코드
            cdDtlList.add("MNG_CONS_CD"); // 신청 분야 코드
            cdDtlList.add("INIT_VST_RSLT"); // 초도방문결과
            cdDtlList.add("CNSTG_PSCND"); // 컨설팅 현황 코드
        modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));

        if (!"".equals(cBBManageConsultInsertDTO.getDetailsKey()) && cBBManageConsultInsertDTO.getDetailsKey() != null) {
            modelMap.addAttribute("rtnData", cBBManageConsultService.selectManageConsultDtl(cBBManageConsultInsertDTO));
        }
        /*} catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }*/


        return "mngwserc/cb/cbb/CBBManageConsultWrite.admin";
    }

    /**
     * 컨설팅 사업 경영컨설팅 목록 조회
     */
    @GetMapping(value = "/select")
    public String selectManageConsultList(CBBManageConsultSearchDTO cBBManageConsultSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            modelMap.addAttribute("rtnData", cBBManageConsultService.selectManageConsultList(cBBManageConsultSearchDTO));
            modelMap.addAttribute("searchDto", cBBManageConsultSearchDTO);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/cb/cbb/CBBManageConsultListAjax";
    }

    /**
     * 컨설팅 사업 경영컨설팅 엑셀다운로드 관련
     */
    @GetMapping(value = "/excel-down")
    public void selectManageConsultListExcel(CBBManageConsultSearchDTO cBBManageConsultSearchDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            cBBManageConsultSearchDTO.setExcelYn("Y");
            //엑셀 생성
            cBBManageConsultService.excelDownload(cBBManageConsultService.selectManageConsultList(cBBManageConsultSearchDTO), response);
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
     * 컨설팅 사업 경영컨설팅 만족도 종합결과 목록 조회
     */
    @GetMapping(value = "/selectSuveyRslt")
    public String selectConsultSuveyRsltList(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO, ModelMap modelMap, HttpServletRequest request) throws Exception {
        try {
            cBBConsultSuveyRsltListDTO.setRtnBsnGubun("CONSULT_GB02");
            modelMap.addAttribute("rtnData", cBBManageConsultService.selectConsultSuveyRsltList(cBBConsultSuveyRsltListDTO));
            modelMap.addAttribute("searchDto", cBBConsultSuveyRsltListDTO);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.debug(e.getMessage());
            }
            throw new Exception(e.getMessage());
        }

        return "mngwserc/cb/cbb/CBBConsultSuveyRsltLayerAjax";
    }
    /**
     * 컨설팅 사업 만족도 종합 결과 엑셀다운로드 관련
     */
    @GetMapping(value = "/srvRsltexcel-down")
    public void selectConsultSrvRsltExcel(CBBConsultSuveyRsltListDTO cBBConsultSuveyRsltListDTO , HttpServletResponse response) throws Exception
    {
        try
        {
            cBBConsultSuveyRsltListDTO.setRtnBsnGubun("CONSULT_GB02");
            //엑셀 생성
            cBBManageConsultService.srvRsltExcelDownload(cBBManageConsultService.selectConsultSuveyRsltDtlExcel(cBBConsultSuveyRsltListDTO), response);
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