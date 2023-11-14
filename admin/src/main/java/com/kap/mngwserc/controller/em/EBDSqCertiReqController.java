package com.kap.mngwserc.controller.em;

import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.ex.exg.EXGExamMstSearchDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBDSqCertiReqService;
import com.kap.service.EBEExamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 홈 > 교육사업관리 > SQ평가원 자격증 신청관리 Controller
 * </pre>
 *
 * @ClassName		: EBDSqCertiReqController.java
 * @Description		: SQ평가원 자격증 신청관리 위한 Controller
 * @author 김학규
 * @since 2023.09.21
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		김학규				   최초 생성
 * </pre>
 */
@Tag(name = "SQ평가원 자격증 신청관리", description = "SQ평가원 자격증 신청관리")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebd")
public class EBDSqCertiReqController {
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;
    /** 서비스 **/
    private final EBDSqCertiReqService eBDSqCertiReqService;

    /**
     *  목록
     */
    @GetMapping(value="/list")
    public String getSqList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EBD_SQ");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebd/EBDSqCertiReqList.admin";
    }

    /**
     * 목록 AJAX
     */
    @GetMapping(value="/select")
    public String getExamAjaxList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBDSqCertiReqService.selectList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("searchDto", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebd/EBDSqCertiReqListAjax";
    }

    /**
     *  상세
     */
    @GetMapping(value="/view")
    public String getSqView(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EBD_SQ");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebd/EBDSqCertiReqWrite.admin";
    }

}

