package com.kap.mngwserc.controller.eb;

import com.kap.core.dto.COCodeDTO;
import com.kap.core.dto.eb.ebh.EBHEduApplicantMstDTO;
import com.kap.service.COCodeService;
import com.kap.service.EBHEduApplicantService;
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
 *    2023.12.01		장두석				   최초 생성
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
    private final EBHEduApplicantService eBHEduApplicantService;

    /**
     *  목록 패이지
     */
    @GetMapping(value="/list")
    public String getListPage(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO, ModelMap modelMap) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("CLASS_TYPE");
            cdDtlList.add("STDUY_MTHD"); //학습방식
            cdDtlList.add("STDUY_DD"); //학습시간 - 학습일
            cdDtlList.add("STDUY_TIME"); //학습시간 - 학습시간
//            cdDtlList.add(""); //선발 구분
            modelMap.addAttribute("classTypeList",  cOCodeService.getCmmCodeBindAll(cdDtlList, "2"));

            cdDtlList.clear();
            cdDtlList.add("COMPANY_TYPE"); //부품사 구분
            cdDtlList.add("EDU_STTS_CD"); //선발 상태(교육참여 교육상태) 구분
            modelMap.addAttribute("cdDtlList",  cOCodeService.getCmmCodeBindAll(cdDtlList));

            COCodeDTO cOCodeDTO = new COCodeDTO();
            cOCodeDTO.setCd("CLASS01");
            modelMap.addAttribute("cdList1", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS02");
            modelMap.addAttribute("cdList2", cOCodeService.getCdIdList(cOCodeDTO));

            cOCodeDTO.setCd("CLASS03");
            modelMap.addAttribute("cdList3", cOCodeService.getCdIdList(cOCodeDTO));

            modelMap.addAttribute("rtnData", pEBHEduApplicantMstDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }

        return "mngwserc/eb/ebh/EBHEduApplicantList.admin";
    }

    /**
     * 목록 조회
     */
    @GetMapping(value="/select")
    public String getEduApplicantListAjax(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            modelMap.addAttribute("rtnData", eBHEduApplicantService.selectList(pEBHEduApplicantMstDTO));
//            modelMap.addAttribute("searchDto", pEBHEduApplicantMstDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebh/EBHEduApplicantListAjax";
    }

    /**
     *  상세 조회
     */
    @GetMapping(value="/write")
    public String getSqView(EBHEduApplicantMstDTO pEBHEduApplicantMstDTO, ModelMap modelMap, HttpServletRequest request) throws Exception
    {
        try
        {
            // 공통코드 배열 셋팅
            ArrayList<String> cdDtlList = new ArrayList<String>();
            // 코드 set
            cdDtlList.add("EBD_SQ_TP");
            cdDtlList.add("EBD_SQ");
//            modelMap.addAttribute("rtnData", eBHEduApplicantService.selectExamAppctnMst(pEBHEduApplicantMstDTO));
//            modelMap.addAttribute("rtnDataSqReqData", eBHEduApplicantService.selectView(pEBHEduApplicantMstDTO));
//            modelMap.addAttribute("cdDtlList", cOCodeService.getCmmCodeBindAll(cdDtlList));
//            modelMap.addAttribute("rtnPrePrcsList", eBHEduApplicantService.getPrePrcsList(pEBHEduApplicantMstDTO));
            modelMap.addAttribute("srchData", pEBHEduApplicantMstDTO);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return "mngwserc/eb/ebh/EBHEduApplicantWrite.admin";
    }
    
    /**
     * @ClassName		: EBHEduApplicantRestController.java
     * @Description		: 홈 > 교육사업관리 > 교육 신청자 관리 REST CONTROLLER
     * @Modification Information
     * <pre>
     * 		since			author				   description
     *   ===========    ==============    =============================
     *   2023.12.01			장두석			 		최초생성
     * </pre>
     */
    @Tag(name = "교육 신청자 관리", description = "교육 신청자 관리")
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

