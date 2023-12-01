package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.eb.ebd.EBDSqCertiSearchDTO;
import com.kap.service.COCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <pre>
 * 홈 > 교육사업관리 > 교육 신청자 관리 Controller
 * </pre>
 *
 * @ClassName		: EBHEduApplicantController.java
 * @Description		: 교육 신청자 관리 위한 Controller
 * @author 장두석
 * @since 2023.12.01
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		since			author				  description
 *    ==========    ==============    =============================
 *    2023.11.02		장두석				   최초 생성
 * </pre>
 */
@Tag(name = "교육 신청자 관리", description = "교육 신청자 관리")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/mngwserc/eb/ebh")
public class EBHEduApplicantController {
    /** 코드 서비스 **/
    private final COCodeService cOCodeService;

    /** 서비스 **/
//    private final EBHEduApplicantService eBHEduApplicantService;

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
        return "mngwserc/eb/ebh/EBHEduApplicantList.admin";
    }

    /**
     * 목록 AJAX
     */
    @GetMapping(value="/select")
    public String getSqAjaxList(EBDSqCertiSearchDTO eBDSqCertiSearchDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
//            modelMap.addAttribute("rtnData", eBHEduApplicantService.selectList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("searchDto", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebh/EBHEduApplicantListAjax";
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
//            modelMap.addAttribute("rtnData", eBHEduApplicantService.selectExamAppctnMst(eBDSqCertiSearchDTO));
//            modelMap.addAttribute("rtnDataSqReqData", eBHEduApplicantService.selectView(eBDSqCertiSearchDTO));
//            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
//            modelMap.addAttribute("rtnPrePrcsList", eBHEduApplicantService.getPrePrcsList(eBDSqCertiSearchDTO));
            modelMap.addAttribute("srchData", eBDSqCertiSearchDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebh/EBHEduApplicantWrite.admin";
    }
    /**
     * @ClassName		: EXGExamRestController.java
     * @Description		: 홈 > 교육사업관리 > SQ평가원 자격증 신청관리 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.11.10			장두석			 		최초생성
     * </pre>
     */
    @Tag(name = "교육 신청자관리", description = "SQ평가원")
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/mngwserc/eb/ebh")
    public class EBHEduApplicantRestController  {
        /** 코드 서비스 **/
        private final COCodeService cOCodeService;
        /** 서비스 **/
//        private final EBHEduApplicantService eBHEduApplicantService;

        /*@Operation(summary = "신청자, 부품사 정보 조회", tags = "회원", description = "신청자, 부품사 정보 조회")
        @PostMapping(value="/update")
        public BaseDTO updateSqCertiConfrim(@Valid COUserCmpnDto cOUserCmpnDto, @Valid EBGExamAppctnMstDTO pEBGExamAppctnMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
        {
            log.error("COUserCmpnDto : {}", cOUserCmpnDto.toString());
            log.error("EBDEdctnEdisdDTO : {}", eBGExamAppctnMstDTO.toString());
            BaseDTO baseDTO = new BaseDTO();
            try
            {
                baseDTO.setRespCnt( eBHEduApplicantService.updateConfirmInfo(cOUserCmpnDto, eBGExamAppctnMstDTO, request));
            }
            catch (Exception e)
            {
                throw new Exception(e.getMessage());
            }
            return baseDTO;
        }*/
    }
}

