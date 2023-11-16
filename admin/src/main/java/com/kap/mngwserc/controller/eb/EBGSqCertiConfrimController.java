package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.BaseDTO;
import com.kap.core.dto.COUserCmpnDto;
import com.kap.core.dto.eb.ebd.EBDEdctnEdisdDTO;
import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.core.dto.eb.ebg.EBGExamAppctnMstDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBDSqCertiReqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * <pre>
 * 홈 > 교육사업관리 > SQ평가원 자격증 회원 관리 Controller
 * </pre>
 *
 * @ClassName		: EBGSqCertiConfrimController.java
 * @Description		: SQ평가원 자격증 회원 관리 위한 Controller
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
@Tag(name = "SQ평가원 자격증 회원 관리", description = "SQ평가원 자격증 회원 관리")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebg")
public class EBGSqCertiConfrimController {
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
            cdDtlList.add("EBD_SQ_TP");
            cdDtlList.add("COMPANY_TYPE");
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebg/EBGSqCertiConfrimList.admin";
    }

    /**
     * 목록 AJAX
     */
    @GetMapping(value="/select")
    public String getSqAjaxList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
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
        return "mngwserc/eb/ebg/EBGSqCertiConfrimListAjax";
    }

    /**
     *  상세
     */
    @GetMapping(value="/write")
    public String getSqView(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EBD_SQ_TP");
            cdDtlList.add("EBD_SQ");
            modelMap.addAttribute("rtnData", eBDSqCertiReqService.selectExamAppctnMst(eBDSqCertiSearchDTO));
            modelMap.addAttribute("rtnDataSqReqData", eBDSqCertiReqService.selectView(eBDSqCertiSearchDTO));
            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
            modelMap.addAttribute("rtnPrePrcsList", eBDSqCertiReqService.getPrePrcsList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("srchData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebg/EBGSqCertiConfrimWrite.admin";
    }
    /**
     * @ClassName		: EXGExamRestController.java
     * @Description		: 홈 > 교육사업관리 > SQ평가원 자격증 신청관리 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.11.10			김학규			 		최초생성
     * </pre>
     */
    @Tag(name = "SQ평가원 자격증 회원관리", description = "SQ평가원")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/mngwserc/eb/ebg")
    public class EBGSqCertiConfrimRestController  {
        /** 코드 서비스 **/
        private final COCodeService cOCodeService;
        /** 서비스 **/
        private final EBDSqCertiReqService eBDSqCertiReqService;
        @Operation(summary = "신청자, 부품사 정보 조회", tags = "회원", description = "신청자, 부품사 정보 조회")
        @PostMapping(value="/update")
        public BaseDTO updateSqCertiConfrim(@Valid COUserCmpnDto cOUserCmpnDto, @Valid EBGExamAppctnMstDTO eBGExamAppctnMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
        {
            log.error("COUserCmpnDto : {}", cOUserCmpnDto.toString());
            log.error("EBDEdctnEdisdDTO : {}", eBGExamAppctnMstDTO.toString());
            BaseDTO baseDTO = new BaseDTO();
            try
            {
                baseDTO.setRespCnt( eBDSqCertiReqService.updateConfirmInfo(cOUserCmpnDto, eBGExamAppctnMstDTO, request));
            }
            catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
            return baseDTO;
        }
    }
}

